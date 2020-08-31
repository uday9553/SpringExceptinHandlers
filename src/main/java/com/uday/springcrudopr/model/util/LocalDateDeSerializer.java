package com.uday.springcrudopr.model.util;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeSerializer   extends  JsonDeserializer<LocalDate> {
	private static final DateTimeFormatter FORMATTER=new DateTimeFormatterBuilder().parseCaseInsensitive()
						.appendPattern("dd-MMM-yy").toFormatter();
	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return LocalDate.parse(p.getValueAsString(),FORMATTER);
	}

}
