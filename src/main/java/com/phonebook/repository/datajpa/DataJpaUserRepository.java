package com.phonebook.repository.datajpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.phonebook.model.User;
import com.phonebook.repository.UserRepository;

@Repository
public class DataJpaUserRepository implements UserRepository{
	
	@Autowired
	private CrudUserRepository repository;
	
	@Override
	public User createOrUpdate(User user) {
		return repository.save(user);
	}

	@Override
	public Optional<User> findOne(Integer id) {
		return repository.findById(id);
	}

	public CrudUserRepository getRepository() {
		return repository;
	}

	public void setRepository(CrudUserRepository repository) {
		this.repository = repository;
	}

	
	

}
