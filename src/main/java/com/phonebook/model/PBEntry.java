package com.phonebook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "phone_book")
public class PBEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "entry_id")
	private int id;
	
	@Column(name = "first_name")
	@NotEmpty(message = "Please provide your first name")
	@Length(min = 4, message = "Your first name must have at least 4 characters")
	private String firstName;
	
	@Column(name = "last_name")
	@NotEmpty(message = "Please provide your last name")
	@Length(min = 4, message = "Your last name must have at least 4 characters")
	private String lastName;
	
	@Column(name = "patronymic_name")
	@NotEmpty(message = "Please provide your patronymic name")
	@Length(min = 4, message = "Your patronymic name must have at least 4 characters")
	private String patronymicName;
	
	@Column(name = "mobile_number")
	@NotEmpty(message = "Please provide your mobile phone number")
	@Pattern(regexp="^((\\+380|0)([0-9]{9}))?$")
	private String mobileNumber;
	
	@Column(name = "home_number")
	private String homeNumber;
	private String address;
	@Pattern(regexp="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")
	private String email;
	
	
	public PBEntry() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public String getPatronymicName() {
		return patronymicName;
	}


	public void setPatronymicName(String patronymicName) {
		this.patronymicName = patronymicName;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getHomeNumber() {
		return homeNumber;
	}


	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}
