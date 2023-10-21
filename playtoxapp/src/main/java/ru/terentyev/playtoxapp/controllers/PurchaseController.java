package ru.terentyev.playtoxapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.terentyev.playtoxapp.entities.Purchase;
import ru.terentyev.playtoxapp.security.PersonDetails;
import ru.terentyev.playtoxapp.services.ProductService;
import ru.terentyev.playtoxapp.services.PurchaseService;

@Controller
@RequestMapping
public class PurchaseController {
	private PurchaseService purchaseService;
	private ProductService productService;

	@Autowired
	public PurchaseController(PurchaseService purchaseService
			, ProductService productService) {
		super();
		this.purchaseService = purchaseService;
		this.productService = productService;
	}
	
	@PostMapping("/products/{id}/buy")
	public String buy(@ModelAttribute Purchase purchase, @PathVariable int id
			, BindingResult br, @AuthenticationPrincipal PersonDetails personDetails) {
		purchase.setProduct(productService.findById(id));
		purchase.setOwner(personDetails.getPerson());
		if (purchase.getProduct().getStock() < purchase.getCount()) {
			br.addError(new ObjectError("globalError", "Вы выбрали кол-во товара большее, чем есть в наличии"));
			if (br.hasErrors()) return "redirect:/products/" + String.valueOf(purchase.getProduct().getId());
			return "viewProduct";
		}
		purchaseService.buy(purchase);
		return "redirect:/";
	}

}
