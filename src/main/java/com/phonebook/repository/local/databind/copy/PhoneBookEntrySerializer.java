package com.phonebook.repository.local.databind.copy;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.phonebook.model.PhoneBookEntry;

public class PhoneBookEntrySerializer extends JsonSerializer<List<PhoneBookEntry>> {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Override
    public void serialize(List<PhoneBookEntry> entries, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartArray();            
        
        boolean isNeedNewId = false;
        activeProfile = (activeProfile != null) ? activeProfile : System.getProperty("spring.profiles.active"); 
        if(activeProfile.equalsIgnoreCase("file-storage")){
        	isNeedNewId = true;
        }   
 
        for(int i = 0; i < entries.size(); i++){
            jgen.writeStartObject();
            PhoneBookEntry pbe = entries.get(i);
            
            if(isNeedNewId) pbe.setId(i+1);

            pbe.setAccount(null);
            jgen.writeObjectField("phoneBook", pbe);
            jgen.writeEndObject();
            
        }
        
        jgen.writeEndArray();
    }

}