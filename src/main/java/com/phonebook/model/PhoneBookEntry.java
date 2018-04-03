package com.phonebook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "phone_book_entry")
public class PhoneBookEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	@Length(min = 4, message = "Contact first name must have at least 4 characters")
	private String firstName;

	@Column(name = "last_name")
	@Length(min = 4, message = "Last name must have at least 4 characters")
	private String lastName;

	@Column(name = "patronymic_name")
	@Length(min = 4, message = "Patronymic name must have at least 4 characters")
	private String patronymicName;

	@Column(name = "mobile_number")
	@Pattern(regexp = "^((\\+?3)?8)?((0\\(\\d{2}\\)?)|(\\(0\\d{2}\\)-?)|(0\\d{2}))(\\d{7}|\\d{3}-?\\d{2}-?\\d{2})$", 
	message = "Format of mobile number must be valid for Ukraine")
	private String mobileNumber;

	@Column(name = "home_number")
	private String homeNumber;

	private String address;
	@Email
	private String email;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "acc_id")
	private Account acc;

	public PhoneBookEntry() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Account getAccount() {
		return acc;
	}

	public void setAccount(Account account) {
		this.acc = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((homeNumber == null) ? 0 : homeNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((patronymicName == null) ? 0 : patronymicName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneBookEntry other = (PhoneBookEntry) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (homeNumber == null) {
			if (other.homeNumber != null)
				return false;
		} else if (!homeNumber.equals(other.homeNumber))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (patronymicName == null) {
			if (other.patronymicName != null)
				return false;
		} else if (!patronymicName.equals(other.patronymicName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PhoneBookEntry [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", patronymicName="
				+ patronymicName + ", mobileNumber=" + mobileNumber + ", homeNumber=" + homeNumber + ", address="
				+ address + ", email=" + email + ", user=" + acc + "]";
	}

}
