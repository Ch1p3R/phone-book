package com.phonebook.repository;

import java.util.List;

import com.phonebook.model.PhoneBookEntry;

public interface PhoneBookEntryRepository {
	
	PhoneBookEntry createOrUpdate(String userId, PhoneBookEntry pbEntry);
	void delete(String userId, Integer id);
	List<PhoneBookEntry> findAll(String userId);
	
}
