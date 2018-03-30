package com.phonebook.service;

import com.phonebook.model.Account;

public interface AccountService {
	Account create(Account acc);
	Account findById(String id);
	Account findByName(String name);
}
