package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

	private static final ObjectMapper mapper=
			new ObjectMapper();
	
			
    public static String toJson(Object object) {
		
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("格式转化失败");
		  // return null;
		}
	}
	
	public static <T> T toObject(String json,Class<T> targetClass) {
		try {
			return mapper.readValue(json,targetClass);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("格式转化失败");
		 // return null;
		}
	}
}
