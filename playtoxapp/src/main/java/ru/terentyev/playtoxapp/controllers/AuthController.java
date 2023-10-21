package ru.terentyev.playtoxapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.terentyev.playtoxapp.entities.Person;
import ru.terentyev.playtoxapp.security.PersonDetails;
import ru.terentyev.playtoxapp.services.PersonDetailsService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PersonDetailsService personDetailsService;

    
    @GetMapping("/login")
    public String index(@AuthenticationPrincipal PersonDetails personDetails) {  	
    	if (personDetails != null) {
    		if (personDetails.getPerson().isAdmin()) {
    			return "redirect:/admin";
    		}
    		return "redirect:/";
    	} else {
    		return "redirect:/";
    	}
    }
    
    
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new Person());
        return "registration";
    }

    
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("registrationForm") Person registringPerson
    		, BindingResult bindingResult, Model model) {
    	
        if (bindingResult.hasErrors()) return "registration";              
        if (!registringPerson.getPassword().equals(registringPerson.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }              
        if (personDetailsService.findByUsername(registringPerson.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }        
        personDetailsService.registerNewUserAccount(registringPerson);


        return "redirect:/";
    }
}
