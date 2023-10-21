package ru.terentyev.playtoxapp.repositories;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import ru.terentyev.playtoxapp.entities.Person;
import ru.terentyev.playtoxapp.entities.Purchase;

@Repository
@Transactional(readOnly = true)
public class PersonRepository {
	private SessionFactory sessionFactory;
	
	
	@Autowired
	public PersonRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}


	public Optional<Person> findByUsername(String username) {
	
		try { return Optional.of(sessionFactory.getCurrentSession().createQuery("from Person p where p.username = :username", Person.class).setParameter("username", username).getSingleResult());
		} catch (NoResultException e) {return Optional.empty();}
		}
	
	
	public Optional<Person> findById(int id) {
		return Optional.of(sessionFactory.getCurrentSession().createQuery("from Person p where p.id = :id", Person.class).setParameter("id", id).getSingleResult());
	}
	
	@Transactional(readOnly = false)
	public Person save(Person person) {
		Session session = sessionFactory.getCurrentSession();
			//if (!session.contains(person)) session.merge(person);
			session.persist(person);
		return person;
	}
	
	@Transactional(readOnly = false)
	public void changeRole(Person person, boolean letAdmin) {
		Session session = sessionFactory.getCurrentSession();
		Person oldPerson = findById(person.getId()).get();
		oldPerson.setAdmin(letAdmin);
    	session.persist(oldPerson);
    	
	}
	
	@Transactional(readOnly = false)
	public void addPurchase(Person owner, Purchase purchase) {
		sessionFactory.getCurrentSession()
				.get(Person.class, owner.getId()).getPurchases().add(purchase);
	}
}
