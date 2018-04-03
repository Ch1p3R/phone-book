package com.phonebook.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.phonebook.PhoneBookApplication;
import com.phonebook.configuration.SecurityConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = {PhoneBookApplication.class, SecurityConfiguration.class})
public class AccountControllerTest {

 
	@Autowired
	WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	@Test
	@WithAnonymousUser
	public void authorizationPage() throws Exception{
		mockMvc.perform(get("/authorization"))
		.andExpect(status().isOk());
	}	
	
	@Test
	@WithMockUser
	public void testRedirectNotExistingPageAuthorizedUser() throws Exception {
	   mockMvc.perform(get("/qwerty1"))
	    .andExpect(status().is3xxRedirection())
	    .andExpect(redirectedUrl("/phonebook"));	
	}
	
	@Test
	@WithAnonymousUser
	public void testRedirectNotExistingPageNotAuthorizedUser() throws Exception {
	   mockMvc.perform(get("/qwerty"))
	    .andExpect(status().is3xxRedirection())
	    .andExpect(redirectedUrl("/authorization"));	
	}
	
}
