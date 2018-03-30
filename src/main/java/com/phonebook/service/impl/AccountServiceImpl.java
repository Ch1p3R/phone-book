package com.phonebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.phonebook.model.Account;
import com.phonebook.repository.AccountRepository;
import com.phonebook.service.AccountService;
import com.phonebook.service.exceptions.AccountDoesNotExistException;
import com.phonebook.service.exceptions.AccountExistException;
@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accRepository;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Account create(Account acc) {
		acc.setPassword(bCryptPasswordEncoder.encode(acc.getPassword()));
		Account createdAcc = accRepository.create(acc);
		if(createdAcc == null) throw new AccountExistException();
		return createdAcc;
	}

	@Override
	public Account findById(String id) {
		Account acc = accRepository.findById(id);
		if(acc == null) throw new AccountDoesNotExistException();
		else return acc;
	}

	@Override
	public Account findByName(String name) {
		return accRepository.findByName(name);
	}
	

}
