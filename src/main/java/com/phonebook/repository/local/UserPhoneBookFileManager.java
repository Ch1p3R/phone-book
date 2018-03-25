package com.phonebook.repository.local;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.model.User;


public class UserPhoneBookFileManager {
	private static final String ID_PREFIX = "\"id\":\"";
	private static final String PROFILE_PREFIX = "\"userName\":"; 
	private File fileStorage;
	private ObjectMapper mapper;
	private String storagePath;
	
	
	

	public String getStoragePath() {
		return storagePath;
	}



	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}



	public UserPhoneBookFileManager(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}



/*	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		FileManager fm = new FileManager();
		fm.init();
		
		User user1 = new User();
		user1.setFullName("FullName");
		user1.setPassword("qwerty");
		user1.setId("9542f11d-caea-4ea0-9949-1bf9f0473dda");
		PhoneBookEntry pbe = new PhoneBookEntry();
		pbe.setAddress("Some Address");
		pbe.setFirstName("Some First Name");
		pbe.setMobileNumber("0735553321");
		
		PhoneBookEntry pbe2 = new PhoneBookEntry();
		pbe2.setAddress("Some Address2");
		pbe2.setFirstName("Some First Name222");
		pbe2.setMobileNumber("0731234321");
		
		
		List<PhoneBookEntry> phoneBook = new ArrayList<>();
		phoneBook.add(pbe);
		phoneBook.add(pbe2);
		user1.setPhoneBook(phoneBook);
		User savedUser = fm.save(user1);
		
		
				
	
	}*/
	
	void init() {
		provideFileStorage();
		mapper = new ObjectMapper();
	}
	
	
	
	public User save(User user) throws IOException{
		if (user.getId() == null){
			user.setId(UUID.randomUUID().toString());
			return create(user);
		}else{
			return update(user);
		}		
	}
	
	public User findById(String userId) throws JsonParseException, JsonMappingException, IOException{
		User foundUser = null;
		String line = "";
		try(BufferedReader reader = new BufferedReader(new FileReader(fileStorage))){
			while((line = reader.readLine()) != null){
				if(line.contains(userId)){
					foundUser = mapper.readValue(line, User.class);
				}
			}
		}
		return foundUser;
	}
	
	
	private User create(User user) throws IOException{
		String userJson = mapper.writeValueAsString(user);
		if(!isUserNameExist(user)){
		Files.write(fileStorage.toPath(), Arrays.asList(userJson), StandardOpenOption.APPEND);
		
		}else{
			throw new RuntimeException();
		}
		return user;
	}
	
	private User update(User user) throws JsonProcessingException, IOException{
		File tempStorage = new File(fileStorage.getParent() + "/temp.json");
		String line = "";
		try(BufferedReader reader = new BufferedReader(new FileReader(fileStorage));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempStorage))){
			while((line = reader.readLine()) != null){
				if(line.contains(ID_PREFIX + user.getId())){
					writer.write(mapper.writeValueAsString(user) + System.getProperty("line.separator"));
					continue;
				}
				writer.write(line + System.getProperty("line.separator"));
			}
		}
		tempStorage.renameTo(fileStorage);
		return user;
	}
	
	public boolean isUserNameExist(User user) throws IOException{
		boolean foundUser = false;
		String line = "";
		try(BufferedReader reader = new BufferedReader(new FileReader(fileStorage))){
			while((line = reader.readLine()) != null){
				if(line.contains(PROFILE_PREFIX + user.getUserName())){
					foundUser = true;
					break;
				}
			}
		}
		return foundUser;
	}
	

	public void provideFileStorage() {
		File file = new File(
				System.getProperty("user.dir") + storagePath + "/storage.json");
		if (file.exists()) {
			System.out.println("Using existed file storage with absolute path: " + file.getAbsolutePath());
			fileStorage = file;
			//return file.getAbsolutePath();
		} else {
			createStorageDir(file.getParentFile());
			createStorageFile(file);
			fileStorage = file;
			//return file.getAbsolutePath();
		}
		 
	}

	private void createStorageDir(File file) {
		try {
			file.mkdirs();
			System.out.println("Created DIR for storage with path: " + file.getPath());
		} catch (SecurityException se) {
			se.printStackTrace();
		}
	}

	private void createStorageFile(File file) {
		try {
			file.createNewFile();
			System.out.println("Created file for storage with path: " + file.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

