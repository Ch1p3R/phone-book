package com.phonebook.service;

public interface SecurityService {
	
    String findLoggedInAccountId();

    void autologin(String username, String password);
}
