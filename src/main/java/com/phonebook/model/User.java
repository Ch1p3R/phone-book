package com.phonebook.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "user_id")
	private String id;
	
	@Column(name = "user_name")
	@NotEmpty(message = "Please provide your user name")
	@Length(min = 3, message = "Your user name must have at least 3 characters")
	@Pattern(regexp="^[A-Za-z0-9]+", message="User names must contain only latin letters and numbers, not special characters")  
	private String userName;
	
	@Column(name = "full_name")
	@NotEmpty(message = "Please provide your full name")
	@Length(min = 5, message = "Your full name must have at least 5 characters")
	private String fullName;
	
	@Transient
	@NotEmpty(message = "Please provide your password")
	@Length(min = 5, message = "Your password must have at least 5 characters")
	private String password;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "user")
	private List<PhoneBookEntry> phoneBook;
		
	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<PhoneBookEntry> getPhoneBook() {
		return phoneBook;
	}

	public void setPhoneBook(List<PhoneBookEntry> phoneBook) {
		this.phoneBook = phoneBook;
	}
	
	
}
