package com.phonebook.repository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.phonebook.model.Account;
import com.phonebook.model.PhoneBookEntry;



@RunWith(SpringRunner.class)
@DataJpaTest
/*@AutoConfigureTestDatabase(replace = Replace.NONE)*/
@ComponentScan(basePackages = "com.phonebook.repository")
@TestPropertySource(properties = { "spring.profiles.active=rdb-storage"})
public class JpaPhoneBookEntryRepositoryTest {
	@Autowired
	public PhoneBookEntryRepository entryRepository;

	@Autowired
	public AccountRepository accountRepository;

	public PhoneBookEntry pbe1, pbe2;

	public Account acc;


	@Before
	public void setUp() {

		pbe1 = new PhoneBookEntry();
		pbe1.setFirstName("Ярослав");
		pbe1.setEmail("sokol@mail.bk");
		pbe1.setMobileNumber("+380738335674");
		pbe1.setLastName("Зубровский");
		pbe1.setPatronymicName("Максимович");

		pbe2 = new PhoneBookEntry();
		pbe2.setFirstName("Иван");
		pbe2.setEmail("1one@gmail.com");
		pbe2.setMobileNumber("+380638335484");
		pbe2.setLastName("Заболоцкий");
		pbe2.setPatronymicName("Александрович");

		acc = new Account();
		acc.setFullName("Кирилл Иванович");
		acc.setPassword("123111456");
		acc.setName("Zak11V");
		
		
		acc = accountRepository.create(acc);
		pbe1 = entryRepository.createOrUpdate(acc.getId(), pbe1);


	}
	
	@Test
	public void createdEntry() {
		pbe1 = entryRepository.createOrUpdate(acc.getId(), pbe1);
		assertThat(pbe1.getId(), is(notNullValue()));
	}
	
	@Test
	public void updateEntry() {
		
		pbe1.setAddress("Some Address");
		pbe1.setFirstName("Dimon");
		pbe1.setLastName("NewLastName");
		
		PhoneBookEntry updatedPBE = entryRepository.createOrUpdate(acc.getId(), pbe1);
		
		List<PhoneBookEntry> entries = entryRepository.findAllByAccountId(acc.getId());
				
		assertThat(entries, hasSize(1));

		assertThat(updatedPBE.getLastName(), containsString(pbe1.getLastName()));
	}
	

	@Test
	public void deleteEntry() {
		pbe1 = entryRepository.createOrUpdate(acc.getId(), pbe1);
		boolean isDeleted = entryRepository.delete(acc.getId(), pbe1);
		List<PhoneBookEntry> entries = entryRepository.findAllByAccountId(acc.getId());
		assertThat(entries, is(IsEmptyCollection.empty()));
		assertTrue(isDeleted);
	}

	@Test
	public void findAll() {
		entryRepository.createOrUpdate(acc.getId(), pbe2);
		List<PhoneBookEntry> entries = entryRepository.findAllByAccountId(acc.getId());
		assertThat(entries, hasSize(2));
	}


	@Test
	public void searchBy() {
		entryRepository.createOrUpdate(acc.getId(), pbe2);

		List<PhoneBookEntry> pbeList = entryRepository.search(acc.getId(), "Ярос", "", "");
		assertThat(pbeList, hasSize(1));
		
		pbeList = entryRepository.search(acc.getId(), "", "кий", "");
		assertThat(pbeList, hasSize(2));
		
		pbeList = entryRepository.search(acc.getId(), "", "кий", "8335");
		assertThat(pbeList, hasSize(2));
		
		
		pbeList = entryRepository.search(acc.getId(), "", "кий", "83356");
		assertThat(pbeList, hasSize(1));
		
		pbeList = entryRepository.search(acc.getId(), "dvds", "кdsfs", "d8336");
		assertThat(pbeList, is(IsEmptyCollection.empty()));
	}


}