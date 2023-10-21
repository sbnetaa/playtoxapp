package ru.terentyev.playtoxapp.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.terentyev.playtoxapp.entities.Purchase;

@Repository
@Transactional(readOnly = true)
public class PurchaseRepository {
	private SessionFactory sessionFactory;

	@Autowired
	public PurchaseRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = false)
	public void save(Purchase purchase) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(purchase);
		session.persist(purchase);
		
	}
	
	public List<Purchase> findAll(){
		return sessionFactory.getCurrentSession()
				.createQuery("from Purchase", Purchase.class).getResultList();
	}
}
