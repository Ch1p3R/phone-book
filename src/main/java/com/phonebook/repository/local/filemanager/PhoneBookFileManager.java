package com.phonebook.repository.local.filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.model.Account;
import com.phonebook.model.PhoneBookEntry;

public class PhoneBookFileManager extends BaseFileManager<Account> {
	private static final Logger LOGGER = Logger.getLogger(PhoneBookFileManager.class.getName());
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String MOBILE_NUMBER = "mobileNumber";

	public PhoneBookFileManager(ObjectMapper mapper) {
		super(mapper);
	}

	// ConstraintViolationException err;
	@Override
	public Account save(Account acc) {

		// Set<ConstraintViolation<User>> violations = validator.validate(user);

		if (acc.getId() == null) {
			acc.setId(UUID.randomUUID().toString());
			return create(acc);
		} else {
			return update(acc.getId(), acc);
		}
	}

	private Account create(Account acc) {
		try {
			if (acc.getName() != null && findByName(acc.getName()) == null) {
				String accJson = getMapper().writeValueAsString(acc);
				Files.write(getFileStorage().toPath(), Arrays.asList(accJson), StandardOpenOption.APPEND);
			} else {
				return null;
			}
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return acc;
	}

	private Account update(String accId, Account acc) {
		File tempStorage = new File(getFileStorage().getParent() + TEMP_FILE_PREFIX + getFileStorage().getName());

		String line = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(getFileStorage()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempStorage))) {
			while ((line = reader.readLine()) != null) {
				if (line.contains(ID_PREFIX + accId)) {
					if (acc == null)
						continue;
					writer.write(getMapper().writeValueAsString(acc) + System.getProperty("line.separator"));
				} else {
					writer.write(line + System.getProperty("line.separator"));
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		boolean isRenamed = tempStorage.renameTo(getFileStorage());
		return isRenamed ? acc : null;
	}

	public Account findById(String accId) {
		Account foundAccount = null;
		String line = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(getFileStorage()))) {
			while ((line = reader.readLine()) != null) {
				if (line.contains(ID_PREFIX + accId)) {
					foundAccount = getMapper().readValue(line, Account.class);
					return foundAccount;
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (JsonParseException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (JsonMappingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return foundAccount;
	}

	public Account findByName(String accName) {
		Account foundAccount = null;
		String line = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(getFileStorage()))) {
			while ((line = reader.readLine()) != null) {
				if (line.contains(ACCOUNT_NAME_PREFIX + accName)) {
					foundAccount = getMapper().readValue(line, Account.class);
					return foundAccount;
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return foundAccount;
	}

	public PhoneBookEntry crudEntry(String accId, PhoneBookEntry pbEntry) {
		return crudEntry(accId, pbEntry, false);
	}

	public PhoneBookEntry crudEntry(String accId, PhoneBookEntry pbEntry, boolean delete) {
		Account acc = findById(accId);
		if (pbEntry.getId() != null) {
			if (delete) {
				acc.getPhoneBook().remove((int) pbEntry.getId() - 1);
				update(accId, acc);
				LOGGER.info("Deleted entry with id: " + pbEntry.getId());
			} else {
				// Update entry
				System.out.println(pbEntry.getId());
				
				acc.getPhoneBook().set(pbEntry.getId()-1, pbEntry);
				update(accId, acc);
				LOGGER.info("Updated entry with id: " + pbEntry.getId());
			}
		} else {
			if (acc.getPhoneBook() != null) {
				//create
				acc.getPhoneBook().add(pbEntry);
			} else {
				List<PhoneBookEntry> entries = new ArrayList<>();
				entries.add(pbEntry);
				acc.setPhoneBook(entries);
			}
			update(accId, acc);
		}
		pbEntry.setId(acc.getPhoneBook().size());
		return pbEntry;
	}

	public List<PhoneBookEntry> findBy(String accId, String firstName, String lastName, String mobileNumber) {
		if (accId == null)
			return null;
		Map<String, String> searchMap = new HashMap<>();

		if (firstName != null)
			searchMap.put(FIRST_NAME, firstName);
		if (lastName != null)
			searchMap.put(LAST_NAME, lastName);
		if (mobileNumber != null)
			searchMap.put(MOBILE_NUMBER, mobileNumber);

		Account acc = findById(accId);
		List<PhoneBookEntry> list = acc.getPhoneBook();
		JsonNode node = getMapper().convertValue(list, JsonNode.class);
		List<PhoneBookEntry> foundEntries = new ArrayList<>();
		Search: for (JsonNode pbEntryJSON : node) {
			for (Map.Entry<String, String> pair : searchMap.entrySet()) {
				JsonNode value = pbEntryJSON.findValue(pair.getKey());
				if (!value.asText().toLowerCase().contains(pair.getValue().toLowerCase()))
					continue Search;
			}
			try {
				PhoneBookEntry foundEntry = getMapper().treeToValue(pbEntryJSON, PhoneBookEntry.class);
				foundEntries.add(foundEntry);
				LOGGER.info("Found entry with id: " + foundEntry.getId());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		}
		return foundEntries;
	}

}
