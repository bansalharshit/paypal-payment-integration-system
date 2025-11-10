package com.hulkhiretech.payments.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JsonUtil {

	private final ObjectMapper objectMapper;
	
	public String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("Error converting object to JSON", e);
			throw new RuntimeException("Error converting object to JSON" + e.getMessage());
		}
	}
	
	public <T> T fromJson(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error("Error converting JSON to object", e);
			throw new RuntimeException("Error converting JSON to object" + e.getMessage());
		}
	}
}
