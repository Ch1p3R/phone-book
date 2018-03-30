package com.phonebook.repository.datajpa;

import org.springframework.data.repository.CrudRepository;

import com.phonebook.model.Account;

public interface CrudAccountRepository extends CrudRepository<Account, Integer>{
	Account findById(String accId);
	Account findByName(String name);
}
