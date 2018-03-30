package com.phonebook.dto;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

public class AccountDTO {
	private String id;
	@Column(name = "name", unique=true)
	@Size(min=3)
	@NotEmpty(message = "Please provide your user name")
	@Length(min = 3, message = "Your user name must have at least 3 characters")
	@Pattern(regexp="^[A-Za-z0-9]+", message="Account name must contain only latin letters and numbers, not special characters")  
	private String name;
	
	@Column(name = "full_name")
	@NotEmpty(message = "Please provide your full name")
	@Length(min = 5, message = "Your full name must have at least 5 characters")
	private String fullName;
	
	@Transient
	@NotEmpty(message = "Please provide your password")
	@Length(min = 5, message = "Your password must have at least 5 characters")
	private String password;
	
	
	
}
