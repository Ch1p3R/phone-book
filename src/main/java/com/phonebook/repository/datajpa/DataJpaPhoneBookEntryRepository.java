package com.phonebook.repository.datajpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.phonebook.model.Account;
import com.phonebook.model.PhoneBookEntry;
import com.phonebook.repository.PhoneBookEntryRepository;

@Repository
public class DataJpaPhoneBookEntryRepository implements PhoneBookEntryRepository {
	@Autowired
	private CrudPhoneBookEntryRepository entryRepository;
	@Autowired
	private CrudAccountRepository accountRepository;

	@Override
	public PhoneBookEntry createOrUpdate(String accId, PhoneBookEntry pbEntry) {
		Account acc = accountRepository.findById(accId);
		pbEntry.setAccount(acc);
		return entryRepository.save(pbEntry);
	}

	@Override
	public boolean delete(String accId, PhoneBookEntry pbEntry) {
		return entryRepository.delete(accId, pbEntry.getId()) != 0;
	}

	@Override
	public List<PhoneBookEntry> findAllByAccountId(String accId) {
		return entryRepository.findByAccount_id(accId);
	}


	@Override
	public List<PhoneBookEntry> search(String accId, String firstName, String lastName, String mobileNumber) {
		return entryRepository
				.findByAccount_idAndFirstNameContainsAndLastNameContainsAndMobileNumberContains(
						accId, firstName, lastName, mobileNumber);
	}

}
