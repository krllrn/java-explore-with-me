package ru.practicum.ewm.models.location;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocationConverter implements AttributeConverter<Location, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(Location location) {
        if (location == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (location.getLat() != null) {
            stringBuilder.append(location.getLat());
            stringBuilder.append(SEPARATOR);
        }

        if (location.getLon() != null) {
            stringBuilder.append(location.getLon());
        }
        return stringBuilder.toString();
    }

    @Override
    public Location convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        String[] parts = s.split(SEPARATOR);
        Location location = new Location();
        if (s.contains(SEPARATOR) && parts.length >= 2) {
            location.setLat(Float.valueOf(parts[0]));
            location.setLon(Float.valueOf(parts[1]));
        } else {
            return new Location();
        }
        return location;
    }
}
