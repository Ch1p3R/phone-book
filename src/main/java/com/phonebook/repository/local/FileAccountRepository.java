package com.phonebook.repository.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.phonebook.model.Account;
import com.phonebook.repository.AccountRepository;
import com.phonebook.repository.local.filemanager.PhoneBookFileManager;
@Profile(value = "file-storage")
@Primary
@Repository
public class FileAccountRepository implements AccountRepository{
	
	@Autowired
	private PhoneBookFileManager fileManager;
	
	@Override
	public Account create(Account user) {
		return fileManager.save(user);
	}

	@Override
	public Account findById(String accId) {
		return fileManager.findById(accId);
	}

	@Override
	public Account findByName(String name) {
		return fileManager.findByName(name);
	}
	
	

	
}
