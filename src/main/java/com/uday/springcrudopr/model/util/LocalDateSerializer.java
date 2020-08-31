package com.uday.springcrudopr.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateSerializer extends StdConverter<LocalDate,String>  {
	private static final DateTimeFormatter FORMATTER=new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM-yy").toFormatter();


	@Override
	public String convert(LocalDate value) {
		return value.format(FORMATTER);
	}

}
