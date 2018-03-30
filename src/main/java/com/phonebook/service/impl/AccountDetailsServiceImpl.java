package com.phonebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phonebook.model.Account;
import com.phonebook.repository.AccountRepository;
import com.phonebook.web.security.User;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private AccountRepository accRepository;
	
	
	public AccountDetailsServiceImpl() {
	        super();
	    }

	@Override
	public UserDetails loadUserByUsername(final String accName) {
		final Account acc = accRepository.findByName(accName);
		if (acc == null) {
			throw new UsernameNotFoundException(accName);
		}
		
		return new User(acc);
	}

}