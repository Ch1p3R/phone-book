package com.phonebook.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.repository.local.filemanager.PhoneBookFileManager;

@Profile(value = "file-storage")
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class })
@ComponentScan(basePackages = "com.phonebook.repository.local")
public class LocalStorageAppConfig {
	@Value("${spring.localstorage.base-directory:#{null}}")
	String baseDirectory;
	@Value("${spring.localstorage.relative-path:#{'/'}}")
	String relativePath;
	@Value("${spring.localstorage.file-name:#{'defaultname.file'}}")
	String fileName;
	
	@Autowired
	ObjectMapper mapper;

	@Bean
	public PhoneBookFileManager userPhoneBookFileManager() {
		PhoneBookFileManager fileManager = new PhoneBookFileManager(mapper);

		fileManager.setBaseDirectory(baseDirectory);
		fileManager.setRelativePath(relativePath);
		fileManager.setFileName(fileName);
		fileManager.provideFileStorage();
		return fileManager;
	}
}
