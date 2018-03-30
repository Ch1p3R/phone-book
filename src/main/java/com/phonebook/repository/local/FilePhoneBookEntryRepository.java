package com.phonebook.repository.local;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.phonebook.model.PhoneBookEntry;
import com.phonebook.repository.PhoneBookEntryRepository;
import com.phonebook.repository.local.filemanager.PhoneBookFileManager;

@Profile(value = "file-storage")
@Primary
@Repository
public class FilePhoneBookEntryRepository implements PhoneBookEntryRepository {
	@Autowired
	private PhoneBookFileManager fileManager;
	
	@Override
	public PhoneBookEntry createOrUpdate(String accId, PhoneBookEntry pbEntry) {
		return fileManager.crudEntry(accId, pbEntry);
		
	}

	@Override
	public boolean delete(String accId, PhoneBookEntry pbEntry) {
		PhoneBookEntry pbe = fileManager.crudEntry(accId, pbEntry, true);
		return (pbe != null) ? true : false;
	}

	@Override
	public List<PhoneBookEntry> findAllByAccountId(String accId) {
		return fileManager.findById(accId).getPhoneBook();
	}

	@Override
	public List<PhoneBookEntry> search(String accId, String firstName, String lastName, String mobileNumber) {
		return fileManager.findBy(accId, firstName, lastName, mobileNumber);
	}
	
	
}
