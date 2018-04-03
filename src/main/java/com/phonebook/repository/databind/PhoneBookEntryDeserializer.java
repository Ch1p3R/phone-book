package com.phonebook.repository.databind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.model.PhoneBookEntry;


public class PhoneBookEntryDeserializer extends JsonDeserializer<List<PhoneBookEntry>> {

	@Override
	public List<PhoneBookEntry> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

			ObjectMapper mapper = new ObjectMapper();
	        p.setCodec(mapper);
	        if (p.hasCurrentToken()) {
	            JsonNode dataNode = p.readValueAs(JsonNode.class);
	            List<PhoneBookEntry> entries = new ArrayList<PhoneBookEntry>();
	            for (JsonNode jsonEntry : dataNode) {
	            	PhoneBookEntry pbe = mapper.readerFor(new TypeReference<PhoneBookEntry>() {
	                }).readValue(jsonEntry.get("phoneBook"));
	                entries.add(pbe);
	            }
	            return entries;
	        }

	        return null;
	    }

	}