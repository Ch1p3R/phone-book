package com.phonebook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonebook.model.PhoneBookEntry;
import com.phonebook.repository.PhoneBookEntryRepository;
import com.phonebook.service.PhoneBookEntryService;
import com.phonebook.service.exceptions.ExceptionUtil;
@Service
public class PhoneBookEntryServiceImpl implements PhoneBookEntryService{
	
	@Autowired
	private PhoneBookEntryRepository pbeRepository;
	
	@Override
	public PhoneBookEntry createOrUpdate(String accId, PhoneBookEntry data) {
		PhoneBookEntry pbEntry = pbeRepository.createOrUpdate(accId, data);
		return ExceptionUtil.checkNotFoundObjWithId(pbEntry, data.getId());
		
	}

	@Override
	public void delete(String accId, PhoneBookEntry data) {
		ExceptionUtil.checkNotFoundBoolWithId(pbeRepository.delete(accId, data), data.getId());
	}

	@Override
	public List<PhoneBookEntry> findAllByAccountId(String accId) {
		List<PhoneBookEntry> entries = pbeRepository.findAllByAccountId(accId);
		return entries != null ? entries : new ArrayList<PhoneBookEntry>();
	}

	@Override
	public List<PhoneBookEntry> search(String accId, String firstName, String lastName, String mobileNumber) {
		firstName = (firstName != null) ? firstName : "";
		lastName = (lastName != null) ? lastName : "";
		mobileNumber = (mobileNumber != null) ? mobileNumber : "";		
		return pbeRepository.search(accId, firstName, lastName, mobileNumber);
	}

}
