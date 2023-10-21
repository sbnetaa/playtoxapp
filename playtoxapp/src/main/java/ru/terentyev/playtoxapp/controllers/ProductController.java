package ru.terentyev.playtoxapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.terentyev.playtoxapp.entities.Product;
import ru.terentyev.playtoxapp.entities.Purchase;
import ru.terentyev.playtoxapp.security.PersonDetails;
import ru.terentyev.playtoxapp.services.PersonDetailsService;
import ru.terentyev.playtoxapp.services.ProductService;

@Controller
@RequestMapping
public class ProductController {
	private ProductService productService;
	private PersonDetailsService personDetailsService;

	@Autowired
	public ProductController(ProductService productService
			, PersonDetailsService personDetailsService) {
		super();
		this.productService = productService;
		this.personDetailsService = personDetailsService;
	}

	@GetMapping(path = {"products", "/", ""})
	public String index(Model model) {
		model.addAttribute("products", productService.findAll());
		return "products";
	}
	
	
	@GetMapping("/changeRole")
	public String changeRolePage() {
		return "changeRole";
	}
	
	@PostMapping("/changeRole")
	public String changeRole(@RequestParam(required = false) boolean letAdmin, @AuthenticationPrincipal PersonDetails personD) {
		personDetailsService.setAdmin(personD, letAdmin);
		return "redirect:/";
	}
	
	@GetMapping("/products/{id}")
	public String viewProduct(@PathVariable int id, Model model) {
		model.addAttribute("product", productService.findById(id));		
		model.addAttribute("purchase", new Purchase());
		return "viewProduct";
	}
	

}
