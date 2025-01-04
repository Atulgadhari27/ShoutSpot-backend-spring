package com.example.shoutspot.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Role.valueOf(dbData);
    }
}
