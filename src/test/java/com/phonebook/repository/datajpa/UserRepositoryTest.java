package com.phonebook.repository.datajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.phonebook.model.User;
import com.phonebook.repository.UserRepository;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ComponentScan(basePackages = "com.phonebook.repository")
@PropertySource("classpath:application.properties")
public class UserRepositoryTest {

	//@Qualifier("qwertyq")
	@Autowired
	public UserRepository userRepository;
	
	@Test 
	public void createUser(){
		User u = new User();
		u.setFullName("SeHnat Fef DDw");
		u.setPassword("123111456");
		u.setUserName("adDasSssa3");
		System.out.println(userRepository);
		userRepository.createOrUpdate(u);
	}

	
	
}
