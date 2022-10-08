package ru.practicum.ewm.models.event;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean aBoolean) {
        if (aBoolean != null) {
            if (aBoolean) {
                return "1";
            } else {
                return "0";
            }
        }
        return null;
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        if (s != null) {
            return !s.equals("0");
        }
        return null;
    }
}
