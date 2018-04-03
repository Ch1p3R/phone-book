
package com.phonebook.repository.local;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.lang.reflect.Method;

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
import org.springframework.util.Assert;

import com.phonebook.configuration.LocalStorageAppConfig;
import com.phonebook.model.Account;
import com.phonebook.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocalStorageAppConfig.class)
@TestPropertySource(properties = {
		"spring.localstorage.relative-path=/src/test/resources/phone_book_storage/",
		"spring.localstorage.file-name=TestAccountStorage.file",
		"spring.profiles.active=file-storage"})
public class AccountRepositoryTest {
	@Value("${spring.localstorage.base-directory:#{null}}")
	private String baseDir;
	@Value("${spring.localstorage.relative-path:#{'/'}}")
	private String relativePath;
	@Value("${spring.localstorage.file-name:#{'defacctestname.file'}}")
	private String fileName;
	
	private static int totalTests;
	private static int testsRan;
	
	@Autowired
	public AccountRepository accountRepository;
	public Account acc;

	@Before
	public void setUp() {
		acc = new Account();
		acc.setFullName("Шевченко Александр Александрович");
		acc.setPassword("12345");
		acc.setName("Shevaa");
		accountRepository.create(acc);
	}
	@After
	public void after() {
		testsRan++;
		System.out.println(getTestStorageFileAbsolutePath().getAbsolutePath());
		if (testsRan == totalTests) {
			getTestStorageFileAbsolutePath().delete();
		}
	}

	@Test
	public void createAccount() {
		assertThat(acc.getId(), is(notNullValue()));
	}
	
	@Test
	public void createAccountWithExistingName(){
		acc.setId(null);
		Account createdAcc = accountRepository.create(acc);
		assertThat(createdAcc, is(nullValue()));
	}
	
	@Test
	public void findAccountByExistingName(){
		Account findedAccount = accountRepository.findByName(acc.getName());
		assertThat(findedAccount, is(notNullValue()));
	}
	@Test
	public void findAccountById() {
		Account findedAccount = accountRepository.findById(acc.getId());
		assertThat(findedAccount, is(notNullValue()));

	}
	@Test
	public void accountNotFound(){
		Account findedAccount = accountRepository.findById("not-existing-id");
		Assert.isNull(findedAccount, "The value must be null");
	}

	@BeforeClass
	public static void beforeClass() {
		totalTests = 0;
		Method[] methods = AccountRepositoryTest.class.getMethods();
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