package com.capstone.ust.utils;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Helper {
	
	//dynamic property setter

    public void setProperty(Object obj, String propertyName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        Class<?> type = obj.getClass().getDeclaredField(propertyName).getType();
        
        if(ClassUtils.isPrimitiveOrWrapper(type)||value instanceof String) {
        	field.set(obj, value);
        }
        else {
        	
        	ObjectMapper mapper = new ObjectMapper();
        	field.set(obj, mapper.convertValue(value,type));
        }
        
	}
}
