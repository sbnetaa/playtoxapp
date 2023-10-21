package ru.terentyev.playtoxapp.repositories;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.terentyev.playtoxapp.entities.Product;

@Repository
@Transactional(readOnly = true)
public class ProductRepository {
	private SessionFactory sessionFactory;
	
	@Autowired
	public ProductRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}



	public List<Product> findAll(){
		Session session = sessionFactory.openSession();
	 return (List<Product>)	session.createQuery("from Product order by id desc", Product.class).getResultList();

	}
	
	@Transactional(readOnly = false)
	public void save(Product productToAdd) throws IllegalStateException, IOException {
		MultipartFile[] pictures = productToAdd.getPictures();	
		File imagePath = new File(Product.getPicturesUploadPath());
		if (!imagePath.exists()) imagePath.mkdir();
		int i = 0;
		for (MultipartFile picture : pictures) {
			if (!picture.isEmpty() && picture.getOriginalFilename().indexOf(".") != -1) {
			String resultFileName = UUID.randomUUID().toString()
					+ picture.getOriginalFilename()
					.substring(picture.getOriginalFilename().length() - 4);
			picture.transferTo(new File(Product.getPicturesUploadPath() + "/" + resultFileName));
			productToAdd.getPicturesPath()[i++] = resultFileName;
			}
			
		}
		
		sessionFactory.getCurrentSession().persist(productToAdd);
	}
	
	public Product findById(int id) {
		return sessionFactory.getCurrentSession()
		.createQuery("from Product where id = :id", Product.class)
		.setParameter("id",  id).getSingleResult();
	}
	
	@Transactional(readOnly = false)
	public void update(Product editedProduct, int id) {
		Session session = sessionFactory.getCurrentSession();
		Product oldProduct = findById(id);
		oldProduct.setName(editedProduct.getName());
		oldProduct.setPrice(editedProduct.getPrice());
		oldProduct.setStock(editedProduct.getStock());
		oldProduct.setDescription(editedProduct.getDescription());
		session.persist(oldProduct);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Product productToDelete = findById(id);
		session.remove(productToDelete);
	}
	
	@Transactional(readOnly = false)
	public void decreaseStock(Product product, int sum) {
		sessionFactory.getCurrentSession().get(Product.class, product.getId())
		.setStock(product.getStock() - sum);

	}
}
