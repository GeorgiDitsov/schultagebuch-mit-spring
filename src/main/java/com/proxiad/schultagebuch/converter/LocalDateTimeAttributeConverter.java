package com.proxiad.schultagebuch.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime datum) {
		return Objects.isNull(datum) ? null : Timestamp.valueOf(datum);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
		return Objects.isNull(sqlTimestamp) ? null : sqlTimestamp.toLocalDateTime();
	}

}
