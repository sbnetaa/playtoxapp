package ru.terentyev.playtoxapp.entities;

import java.util.ResourceBundle;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "products")
public class Product {
	
	private static String picturesUploadPath = ResourceBundle.getBundle("upload").getString("upload.path");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty
	private String name;
	private String description;
	private int price;
	private int stock;
	private transient MultipartFile[] pictures = new MultipartFile[5];
	private String[] picturesPath = new String[5];
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "purchase_id", referencedColumnName = "id")
	private Purchase purchase;
	
	public Product() {}

	
	
	public static String getPicturesUploadPath() {
		return picturesUploadPath;
	}



	public static void setPicturesUploadPath(String picturesUploadPath) {
		Product.picturesUploadPath = picturesUploadPath;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}



	public MultipartFile[] getPictures() {
		return pictures;
	}

	public void setPictures(MultipartFile[] pictures) {
		this.pictures = pictures;
	}



	public String[] getPicturesPath() {
		return picturesPath;
	}

	public void setPicturesPath(String[] picturesPath) {
		this.picturesPath = picturesPath;
	}



	public Purchase getPurchase() {
		return purchase;
	}



	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	

}
