package ru.terentyev.playtoxapp.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.terentyev.playtoxapp.entities.Person;


public class PersonDetails implements UserDetails {
	private final Person person;

	
	public PersonDetails(Person person) {
		super();
		this.person = person;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (getPerson().isAdmin()) return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {	
		return person.getPassword();
	}

	@Override
	public String getUsername() {
		return person.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Person getPerson() {
		return person;
	}
}
