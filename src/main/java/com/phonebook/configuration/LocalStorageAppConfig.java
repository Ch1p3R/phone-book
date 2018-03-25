package com.phonebook.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.repository.local.UserPhoneBookFileManager;

//@Profile("dev")
@Configuration
//@ComponentScan(basePackages = "com.phonebook.repository.local")
public class LocalStorageAppConfig {
	@Value("${spring.localstorage.storage-path}")
	String storagePath;

	@Autowired
	ObjectMapper mapper;

	@Bean
	public UserPhoneBookFileManager userPhoneBookFileManager() {
		UserPhoneBookFileManager fileManager = new UserPhoneBookFileManager(mapper);
		fileManager.setStoragePath(storagePath);
		System.out.println("yeah!" + storagePath);
		fileManager.provideFileStorage();
		return fileManager;
	}
}
