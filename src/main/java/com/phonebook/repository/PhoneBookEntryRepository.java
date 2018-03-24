package com.phonebook.repository;

import java.util.List;

import com.phonebook.model.PhoneBookEntry;

public interface PhoneBookEntryRepository {
	
	PhoneBookEntry createOrUpdate(PhoneBookEntry pbEntry);
	void delete(Integer id);
	List<PhoneBookEntry> findAll(Integer userId);
	
}
