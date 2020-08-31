package com.uday.springcrudopr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class AppConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper(){
		  ObjectMapper mapper=new ObjectMapper();
		  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		  mapper.setSerializationInclusion(Include.NON_NULL);
		  mapper.setSerializationInclusion(Include.NON_EMPTY);
		  JavaTimeModule javaTimeModule=new JavaTimeModule();
		 // javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeSerializer());
		  mapper.registerModule(javaTimeModule);
		  return mapper;
		
	}
	
}
