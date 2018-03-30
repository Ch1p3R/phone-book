package com.phonebook.repository;

import com.phonebook.model.Account;

public interface AccountRepository {
	
	Account create(Account acc);
	Account findById(String id);
	Account findByName(String name);
}
