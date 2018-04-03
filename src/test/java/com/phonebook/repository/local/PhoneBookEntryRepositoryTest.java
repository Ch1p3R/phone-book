package com.phonebook.repository.local;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.phonebook.configuration.LocalStorageAppConfig;
import com.phonebook.model.Account;
import com.phonebook.model.PhoneBookEntry;
import com.phonebook.repository.AccountRepository;
import com.phonebook.repository.PhoneBookEntryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocalStorageAppConfig.class)
@TestPropertySource(properties = {
		"spring.localstorage.file-name=TestEntriesStorage.file",
		"spring.profiles.active=file-storage"})
public class PhoneBookEntryRepositoryTest {
	
	@Value("${spring.localstorage.base-directory:#{null}}")
	private String baseDir;
	@Value("${spring.localstorage.relative-path:#{'/'}}")
	private String relativePath;
	@Value("${spring.localstorage.file-name:#{'defaulttestname.file'}}")
	private String fileName;
	
	private static int totalTests;
	private static int testsRan;
	
	@Autowired
	public AccountRepository accountRepository;
	@Autowired
	public PhoneBookEntryRepository pbeRepository;
	
	
	public Account accWithTwoEntries, accEmptyEntries;
	public PhoneBookEntry pbe1, pbe2, pbe3;
	@Before
	public void setUp(){

		List<PhoneBookEntry> list4Acc = new ArrayList<>();

		accWithTwoEntries = new Account();
		accWithTwoEntries.setFullName("Батр Артур Василиевич");
		accWithTwoEntries.setName("Bistrov"+testsRan);	
		accWithTwoEntries.setPassword("qwerty");

		pbe1 = new PhoneBookEntry();
	
		pbe1.setLastName("Бобров");
		pbe1.setFirstName("Антон");
		pbe1.setPatronymicName("Николаевич");
		pbe1.setMobileNumber("+380961235885");
		
		pbe2 = new PhoneBookEntry();
		pbe2.setLastName("Иванова");
		pbe2.setFirstName("Тетьяна");
		pbe2.setPatronymicName("Станиславовна");
		pbe2.setEmail("bagra@gmail.com");
		pbe2.setMobileNumber("+380771231147");
		list4Acc.add(pbe1);
		list4Acc.add(pbe2);
		accWithTwoEntries.setPhoneBook(list4Acc);
		
		
		pbe3 = new PhoneBookEntry();
		pbe3.setFirstName("Георгий");
		pbe3.setLastName("Титов");
		pbe3.setPatronymicName("Валентинович");
		
		accEmptyEntries = new Account();
		accEmptyEntries.setName("Korniev"+testsRan);
		accEmptyEntries.setFullName("Корнилов Альберт Донатович");
		accEmptyEntries.setPassword("12345");
		
		accountRepository.create(accWithTwoEntries);
		accountRepository.create(accEmptyEntries);
	}
	
	@After
	public void after() {
		testsRan++;
		if (testsRan == totalTests) {
			getTestStorageFileAbsolutePath().delete();
		}
	}
	
	@Test
	public void findAllEntries(){
		List<PhoneBookEntry> entries = pbeRepository.findAllByAccountId(accWithTwoEntries.getId());
		assertThat(entries, not(IsEmptyCollection.empty()));
		assertThat(entries, hasSize(2));
	}
	
	@Test
	public void createEntry(){
		
		pbeRepository.createOrUpdate(accEmptyEntries.getId(), pbe3);
		assertThat(pbe3.getId(), is(notNullValue()));

		List<PhoneBookEntry> entries = pbeRepository.findAllByAccountId(accEmptyEntries.getId());
		assertThat(entries.size(), is(notNullValue()));
	}
	
	@Test
	public void updateEntry(){	
		pbe3.setId(2);
		pbe3.setFirstName("Катерина");
		pbe3.setHomeNumber("0441224455");
		pbeRepository.createOrUpdate(accWithTwoEntries.getId(), pbe3);
			
		List<PhoneBookEntry> entries = pbeRepository.findAllByAccountId(accWithTwoEntries.getId());
		assertThat(entries, hasSize(2));
		
	}
	
	@Test
	public void deleteEntry(){
		boolean isDeleted = pbeRepository.delete(accWithTwoEntries.getId(), pbe2);
		assertThat(isDeleted, is(true));
		
		List<PhoneBookEntry> entries = pbeRepository.findAllByAccountId(accWithTwoEntries.getId());
		assertThat(entries, hasSize(1));
	}
	
	@Test
	public void findEntriesByContainFirstName(){
		List<PhoneBookEntry> entries = pbeRepository.search(accWithTwoEntries.getId(), "ант", "", "");
		assertThat(entries, hasSize(1));
	}
	
	@Test
	public void findEntriesByContainsLastNameAndNumber(){
		List<PhoneBookEntry> entries = pbeRepository.search(accWithTwoEntries.getId(), "", "бобр", "+38096");
		assertThat(entries, hasSize(1));
	}
	
	@Test
	public void findEntriesByContainsNumber(){
		List<PhoneBookEntry> entries = pbeRepository.search(accWithTwoEntries.getId(), "", "", "123");
		assertThat(entries, hasSize(2));
	}
	
	@Test
	public void findNotExistingEntries(){
		List<PhoneBookEntry> entries = pbeRepository.search(accWithTwoEntries.getId(), "fgfht", "багр", "+38077");
		assertThat(entries, hasSize(0));
	}

	
	
	@BeforeClass
	public static void beforeClass() {
		totalTests = 0;
		Method[] methods = PhoneBookEntryRepositoryTest.class.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Test.class) != null) {
				totalTests++;
			}
		}
	}
	

	private File getTestStorageFileAbsolutePath() {
		baseDir = (baseDir != null && !baseDir.isEmpty()) ? baseDir : System.getProperty("user.dir");
		return new File(baseDir + relativePath + "/" + fileName);
	}

}