package com.phonebook.repository;

import java.util.Optional;

import com.phonebook.model.User;

public interface UserRepository {
	
	User createOrUpdate(User user);
//	void delete(Integer id);
//	List<User> findAll();
	Optional<User> findOne(Integer id);
}
