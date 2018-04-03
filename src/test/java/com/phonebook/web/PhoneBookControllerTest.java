package com.phonebook.web;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.model.Account;
import com.phonebook.model.PhoneBookEntry;
import com.phonebook.service.PhoneBookEntryService;
import com.phonebook.service.SecurityService;

@RunWith(SpringRunner.class)
public class PhoneBookControllerTest {
	
	
    @Mock
    private PhoneBookEntryService pbService;
    
    @Mock
    private SecurityService securityService;
    
    @InjectMocks
    private PhoneBookController pbController;
    
    private MockMvc mvc;
    
    private Account account;  
    private List<PhoneBookEntry> pbeList;
    private PhoneBookEntry entry1;
    
    @Before
    public void init(){
    	
    	MockitoAnnotations.initMocks(this);
    	mvc = MockMvcBuilders.standaloneSetup(pbController).setViewResolvers(viewResolver()).build();
    	
    	account = new Account();
    	account.setId(UUID.randomUUID().toString());
    	account.setName("test");
    	account.setFullName("testFN");
    	
    	entry1 = new PhoneBookEntry();
    	entry1.setFirstName("TestName1");
    	entry1.setLastName("TestLastName1");
    	entry1.setPatronymicName("TestPatronymicName1");
    	entry1.setMobileNumber("0631112233");
    	
       	PhoneBookEntry entry2 = new PhoneBookEntry();
    	entry1.setFirstName("TestName2");
    	entry1.setLastName("TestLastName2");
    	entry1.setPatronymicName("TestPatronymicName2");
    	entry1.setMobileNumber("0633335533");
    	pbeList = new ArrayList<>();
    	pbeList.add(entry1);
    	pbeList.add(entry2);
    }
    
    @Test
    public void getAllEntriesForAcc() throws Exception {
    	when(securityService.findLoggedInAccountId()).thenReturn(account.getId());
    	when(pbService.findAllByAccountId(anyString())).thenReturn(pbeList);
    	
    	 mvc.perform(get("/phonebook"))
    	 	.andDo(print())
    	 	.andExpect(model().attributeExists("entries"))
    	 	.andExpect(model().attribute("entries", hasSize(2)))
    	 	.andExpect(status().isOk());
    	 
    	 verify(pbService, times(1));
    }
    
    @Test
    public void saveOrUpdateValidEntry() throws Exception{
    	when(securityService.findLoggedInAccountId()).thenReturn(account.getId());
    	
     	
    	mvc.perform(post("/phonebook")
    			.param("firstName", entry1.getFirstName())
    			.param("lastName", entry1.getLastName())
    			.param("patronymicName", entry1.getPatronymicName())
    			.param("mobileNumber", entry1.getMobileNumber()))
    		.andDo(print())
    		.andExpect(model().hasNoErrors())
    		.andExpect(status().is3xxRedirection())
    		.andExpect(redirectedUrl("/phonebook"));
    	
    	verify(pbService, times(1)).createOrUpdate(anyString(), any(PhoneBookEntry.class));
    }
    
    @Test
    public void saveOrUpdateNotValidEntry() throws Exception{
    	when(securityService.findLoggedInAccountId()).thenReturn(account.getId());
    	entry1.setMobileNumber("34535565");
     	
    	mvc.perform(post("/phonebook")
    			.param("firstName", entry1.getFirstName())
    			.param("lastName", entry1.getLastName())
    			.param("patronymicName", entry1.getPatronymicName())
    			.param("mobileNumber", entry1.getMobileNumber()))
    		.andDo(print());

    	
    	verify(pbService, never()).createOrUpdate(anyString(), any(PhoneBookEntry.class));
    }
    
    
    @Test
    public void deleteEntry() throws JsonProcessingException, Exception{
    	when(securityService.findLoggedInAccountId()).thenReturn(account.getId());
    	ObjectMapper mapper = new ObjectMapper();
    	mvc.perform(delete("/phonebook/delete")
    			.content(mapper.writeValueAsString(entry1))
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk());
    	
    	verify(pbService, times(1)).delete(anyString(), any(PhoneBookEntry.class));
    }
    
    @Test
    public void deleteEntryNotLoggerIn() throws JsonProcessingException, Exception{

    	ObjectMapper mapper = new ObjectMapper();
    	mvc.perform(delete("/phonebook/delete")
    			.content(mapper.writeValueAsString(entry1))
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isBadRequest());
    	
    	verify(pbService, never()).delete(anyString(), any(PhoneBookEntry.class));
    }
    	
    
    
    
    
	private InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
    
}
