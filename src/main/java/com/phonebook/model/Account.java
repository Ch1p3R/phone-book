package com.phonebook.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phonebook.repository.databind.PhoneBookEntryDeserializer;
import com.phonebook.repository.databind.PhoneBookEntrySerializer;



@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "acc_id")
	private String id;

	@Column(name = "user_name", unique=true)
	@Length(min = 3, message = "Your user name must have at least 3 characters")
	@Pattern(regexp="^[A-Za-z0-9]+", message="Account name must contain only latin letters and numbers without any special characters")  
	private String name;
	
	@Column(name = "full_name")
	@Length(min = 5, message = "Your full name must have at least 5 characters")
	private String fullName;
	

	@Length(min = 5, message = "Your password must have at least 5 characters")
	private String password;


	@JsonDeserialize(using = PhoneBookEntryDeserializer.class)
	@JsonSerialize(using = PhoneBookEntrySerializer.class)
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="acc")
	private List<PhoneBookEntry> phoneBook;
		
	public Account() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + name + ", fullName=" + fullName + ", password=" + password
				+ ", phoneBook=" + phoneBook + "]";
	}
	
	
}
