package com.phonebook.repository.datajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.phonebook.PhoneBookApplication;
import com.phonebook.model.User;

@ActiveProfile("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=PhoneBookApplication.class)
@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
public class ProxyUserRepositoryTest {

	@Autowired
	public ProxyUserRepository userRepository;
	
	@Test 
	public void createUser(){
		User u = new User();
		u.setFullName("Sem And Anat");
		u.setPassword("123456");
		u.setUserName("7on");
		userRepository.save(u);
	}

	
	
}
