package ru.terentyev.playtoxapp.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "people")
public class Person {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private transient String passwordConfirm; 
    @Column(name = "is_admin", nullable = false, columnDefinition = "boolean default false")
    private boolean admin;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE)
    private List<Purchase> purchases;

    public Person() {}

 
    
    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getPasswordConfirm() {
		return passwordConfirm;
	}



	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}



	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}



	public List<Purchase> getPurchases() {
		return purchases;
	}



	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}



	public Collection<? extends GrantedAuthority> getAuthorities() {
       if (admin) return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
       return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }


}
