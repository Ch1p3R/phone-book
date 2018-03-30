package com.phonebook.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.phonebook.model.Account;
import com.phonebook.repository.AccountRepository;

@Repository
public class DataJpaAccountRepository implements AccountRepository{
	
	@Autowired
	private CrudAccountRepository repository;
	
	@Override
	public Account create(Account acc) {
		return repository.save(acc);
	}

	@Override
	public Account findById(String accId) {
		return repository.findById(accId);
	}

	@Override
	public Account findByName(String name) {
		return repository.findByName(name);
	}
	
	
	

}
