package com.phonebook.repository;

import java.util.List;

import com.phonebook.model.PhoneBookEntry;

public interface PhoneBookEntryRepository {
	
	PhoneBookEntry createOrUpdate(String accId, PhoneBookEntry pbEntry);
	boolean delete(String accId, PhoneBookEntry pbEntry);
	List<PhoneBookEntry> findAllByAccountId(String accId);
	List<PhoneBookEntry> search(String accId, String firstName, String lastName, String mobileNumber);
}
