package com.phonebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
//@EnableAutoConfiguration
public class PhoneBookApplication {
	public static void main(String[] args) {

		SpringApplication.run(PhoneBookApplication.class, args);
	}

	@Autowired
	   public PhoneBookApplication(Environment environment) {
	      // set system properties before the beans are being created.
	      String property = "spring.profiles.active";
	      System.getProperties().setProperty(property, environment.getProperty(property));
	   }

/*	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}*/

}
