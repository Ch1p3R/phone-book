package com.phonebook;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class PhoneBookApplication {
	public static void main(String[] args) {
	    String prop = System.getProperty("lardi.conf");
	    SpringApplicationBuilder builder = new SpringApplicationBuilder(PhoneBookApplication.class);

	    if (prop != null && !prop.equals("") && new File(prop).exists()) {
	        builder.properties("spring.config.location=" + prop);
	    } 
	    builder.run(args);

	}

		@Autowired
	   public PhoneBookApplication(Environment environment) {
	      // set system properties before the beans are being created.
	      String property = "spring.profiles.active";
	      System.getProperties().setProperty(property, environment.getProperty(property));
	   }

}
