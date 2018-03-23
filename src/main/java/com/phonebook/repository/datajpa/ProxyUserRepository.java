package com.phonebook.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phonebook.model.User;


public interface ProxyUserRepository extends JpaRepository<User, Integer>{
	
}
