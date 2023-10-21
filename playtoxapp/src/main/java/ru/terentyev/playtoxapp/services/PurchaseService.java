package ru.terentyev.playtoxapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.terentyev.playtoxapp.entities.Purchase;
import ru.terentyev.playtoxapp.repositories.PersonRepository;
import ru.terentyev.playtoxapp.repositories.ProductRepository;
import ru.terentyev.playtoxapp.repositories.PurchaseRepository;

@Service
public class PurchaseService {

	private ProductRepository productRepository;
	private PurchaseRepository purchaseRepository;
	private PersonRepository personRepository;
	
	@Autowired
	public PurchaseService(ProductRepository productRepository
			, PurchaseRepository purchaseRepository
			, PersonRepository personRepository) {
		super();
		this.productRepository = productRepository;
		this.purchaseRepository = purchaseRepository;
		this.personRepository = personRepository;
	}
	
	public void buy(Purchase purchase) {
		System.out.println(purchase + " " + purchase.getProduct()
		+ " " + purchase.getProduct().getId() + " " + purchase.getOwner() + " " 
		+ purchase.getCount());
		productRepository.decreaseStock(purchase.getProduct(), purchase.getCount());
		personRepository.addPurchase(purchase.getOwner(), purchase);
		purchaseRepository.save(purchase);
		


	}
	
	public List<Purchase> findAll(){
		return purchaseRepository.findAll();
	}
}
