package com.phonebook.service;

import java.util.List;

import com.phonebook.model.PhoneBookEntry;

public interface PhoneBookEntryService {
	PhoneBookEntry createOrUpdate(String accId, PhoneBookEntry pbEntry);
	void delete(String accId, PhoneBookEntry pbEntry);
	List<PhoneBookEntry> findAllByAccountId(String accId);
	List<PhoneBookEntry> search(String accId, String firstName, String lastName, String mobileNumber);
}
