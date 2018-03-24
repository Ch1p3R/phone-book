package com.phonebook;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.phonebook.repository.datajpa.DataJpaUserRepository;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@EnableJpaRepositories(basePackages = "com.repository")
public class PhoneBookApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhoneBookApplication.class, args);
	}
	
	
	@Bean
	  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	    return args -> {

	      System.out.println("Let's inspect the beans provided by Spring Boot:");

	      String[] beanNames = ctx.getBeanDefinitionNames();
	      Arrays.sort(beanNames);
	      for (String beanName : beanNames) {
	        System.out.println(beanName);
	      }
	      DataJpaUserRepository repo = (DataJpaUserRepository)ctx.getBean("dataJpaUserRepository");
	      System.out.println("qweqwe" + repo);
	    };
	  }    

}
