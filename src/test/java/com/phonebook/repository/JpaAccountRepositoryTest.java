package com.phonebook.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.phonebook.model.Account;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan(basePackages = "com.phonebook.repository")
@TestPropertySource(locations = "classpath:application-test.properties", properties = { "spring.profiles.active=rdb-storage" })
public class JpaAccountRepositoryTest {

	@Autowired
	public AccountRepository accountRepository;
	public Account acc1, acc2;

	@Before
	public void setUp() {
		acc1 = new Account();
		acc1.setFullName("FuulNameHere!");
		acc1.setPassword("123111456");
		acc1.setName("Andr11TheBesttt");
		
		acc2 = new Account();
		acc2.setFullName("Иванова Тетьяна");
		acc2.setPassword("qwerty");
		
	}

	@Test
	public void createAccount() {
		Account createdAccount = accountRepository.create(acc1);
		assertThat(createdAccount.getId(), is(notNullValue()));
	}
	
	@Test
	public void createAccountWithExistingName(){
		acc1 = accountRepository.create(acc1);
		
		acc2.setFullName(acc1.getName());
		assertThat(acc2.getId(), is(nullValue()));
	}
	
	@Test
	public void findByName(){
		acc1 = accountRepository.create(acc1);
		
		acc2 = accountRepository.findByName(acc1.getName());
		assertThat(acc2.getId(), is(notNullValue()));
	}
	

	@Test
	public void findAccountById(){
		acc1.setName("AccNameByID2");
		String accId = accountRepository.create(acc1).getId();
		Account foundAcc = accountRepository.findById(accId);
		assertThat(foundAcc, is(notNullValue()));
	}
	
	@Test
	public void accountNotFoundById(){
		Account findedAccount = accountRepository.findById("by-not-existing-id");
		Assert.isNull(findedAccount, "The value must be null");
	}
	
	@Test
	public void accountNotFoundByName(){
		Account findedAccount = accountRepository.findByName("NotExistingName");
		Assert.isNull(findedAccount, "The value must be null");
	}

}