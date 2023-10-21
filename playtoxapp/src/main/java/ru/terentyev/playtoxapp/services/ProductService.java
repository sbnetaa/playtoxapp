package ru.terentyev.playtoxapp.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ru.terentyev.playtoxapp.entities.Product;
import ru.terentyev.playtoxapp.repositories.ProductRepository;

@Service
public class ProductService {

	
	
	private ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public void add(Product productToAdd) throws IllegalStateException, IOException {
		productRepository.save(productToAdd);
	}
	
	public Product findById(int id) {
		return productRepository.findById(id);
	}
	
	public void update(Product editedProduct, int id) {
		System.out.println("Service started");
		productRepository.update(editedProduct, id);
		System.out.println("Service ended");
	}
	
	public void deleteById(int id) {
		productRepository.deleteById(id);
	}
}
