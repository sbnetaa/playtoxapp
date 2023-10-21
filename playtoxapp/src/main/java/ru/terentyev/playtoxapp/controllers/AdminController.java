package ru.terentyev.playtoxapp.controllers;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ru.terentyev.playtoxapp.entities.Product;
import ru.terentyev.playtoxapp.services.ProductService;
import ru.terentyev.playtoxapp.services.PurchaseService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private ProductService productService;
	private PurchaseService purchaseService;
	
	
	@Autowired
	public AdminController(ProductService productService, PurchaseService purchaseService) {
		super();
		this.productService = productService;
		this.purchaseService = purchaseService;
	}

	@GetMapping("/addProduct")
	public String addPage(Model model) {
		model.addAttribute("product", new Product());
		return "addProduct";
	}
	
	@PostMapping("/addProduct")
	public String add(@Valid Product productToAdd, BindingResult br) throws IllegalStateException, IOException {
		if (br.hasErrors()) return "addProduct";
		productService.add(productToAdd);
		return "redirect:/products";
	}
	
	@GetMapping("/products/{id}/edit")
	public String editPage(@PathVariable int id, Model model) {
		model.addAttribute("product", productService.findById(id));
		return "editProduct";
	}
	
	@PatchMapping("/products/{id}/edit")
	public String edit(@PathVariable int id, @ModelAttribute Product editedProduct) {
		System.out.println("Entering controller");
		productService.update(editedProduct, id);
		System.out.println("Controller ended");
		return "redirect:/";
	}
	
	@DeleteMapping("/products/{id}/delete")
	public String delete(@PathVariable int id) {
		productService.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/purchases")
	public String viewPurchases(Model model) {
		model.addAttribute("purchases", purchaseService.findAll());
		return "purchases";
	}
	
}
