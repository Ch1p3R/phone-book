package com.phonebook.repository.local.databind;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.phonebook.model.PhoneBookEntry;


public class PhoneBookEntrySerializer extends JsonSerializer<List<PhoneBookEntry>> {

    @Override
    public void serialize(List<PhoneBookEntry> value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartArray();
        
        for(int i = 0; i < value.size(); i++){
            jgen.writeStartObject();
            PhoneBookEntry pbe = value.get(i);
            pbe.setId(i+1);
            jgen.writeObjectField("phoneBook", pbe);
            jgen.writeEndObject();
        }
        
        jgen.writeEndArray();
    }

}