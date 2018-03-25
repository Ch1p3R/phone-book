package com.phonebook.repository.local;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.phonebook.model.User;
import com.phonebook.repository.UserRepository;
@Profile(value = "dev")
@Component
public class FileUserRepository implements UserRepository{
	
	@Autowired
	private UserPhoneBookFileManager fileManager;
	
	@Override
	public User createOrUpdate(User user) {
		User u = null;
		try {
			u = fileManager.save(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public Optional<User> findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public void setFileManager(UserPhoneBookFileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	
}
