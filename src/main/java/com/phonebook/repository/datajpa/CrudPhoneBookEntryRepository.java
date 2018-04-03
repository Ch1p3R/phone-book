package com.phonebook.repository.datajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.phonebook.model.PhoneBookEntry;

public interface CrudPhoneBookEntryRepository extends CrudRepository<PhoneBookEntry, Integer> {

	List<PhoneBookEntry> findByAcc_id(String accId);

	@Transactional
	@Modifying
	@Query("DELETE FROM PhoneBookEntry pbe WHERE pbe.id=:entryId AND pbe.acc.id=:accId")
	int delete(@Param("accId")String accId, @Param("entryId")Integer entryId);
	
	// o_O
	List<PhoneBookEntry> findByAcc_idAndFirstNameContainsAndLastNameContainsAndMobileNumberContains(
			String accId, String firstName, String lastName, String mobileNumber);
	
}
