package ru.terentyev.playtoxapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.terentyev.playtoxapp.entities.Person;
import ru.terentyev.playtoxapp.repositories.PersonRepository;
import ru.terentyev.playtoxapp.security.PersonDetails;


@Service
public class PersonDetailsService implements UserDetailsService {
	private PersonRepository personRepository;
	
	@Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public PersonDetailsService() {}
	
	@Autowired
	 public PersonDetailsService(PersonRepository personRepository
			 , BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.personRepository = personRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	    public UserDetails loadUserByUsername(String username) {    	
	        Optional<Person> person = personRepository.findByUsername(username); 
	            return new PersonDetails(person.orElseThrow(() -> new UsernameNotFoundException("Username not found")));
	  
	    }
	 
	 public Person registerNewUserAccount(Person person) {
	
	        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
	        return personRepository.save(person);
	    }
	    
	    public Person findById(int Id) {
	    	Optional<Person> oPerson = personRepository.findById(Id); 
	        return oPerson.orElse(null);    
	    }
		
	    public Optional<Person> findByUsername(String name) {
	    	Optional<Person> oPerson = personRepository.findByUsername(name);
	    	return oPerson;
	    	
	    }
	    
	    public void setAdmin(PersonDetails personD, boolean letAdmin) {
	    	personRepository.changeRole(personD.getPerson(), letAdmin);
	    	
	    }
}
