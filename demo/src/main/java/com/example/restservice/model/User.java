package com.example.restservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "users")
public class User implements Serializable {
 
	// private static final long serialVersionUID = -2343243243242432341L;
	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private long id;
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "pword")
    private String password;

	@Column(name = "firstname")
	private String firstName;
 
	@Column(name = "lastname")
	private String lastName;
 
	protected User() {
	}
 
	public User(String email, String password,String firstName, String lastName) {
        this.email = email;
        this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
 
	@Override
	public String toString() {
        //TODO remove password
		return String.format("User[email=%s, password=%s firstName='%s', lastName='%s']", email, password, firstName, lastName);
	}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
	public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}