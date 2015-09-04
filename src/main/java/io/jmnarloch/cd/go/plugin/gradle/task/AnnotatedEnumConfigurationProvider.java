package io.jmnarloch.cd.go.plugin.gradle.task;

import io.jmnarloch.cd.go.plugin.gradle.api.PropertyName;
import io.jmnarloch.cd.go.plugin.gradle.api.PropertyValue;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AnnotatedEnumConfigurationProvider<T extends Enum> implements TaskConfiguration {

    private final Map configuration;

    public AnnotatedEnumConfigurationProvider(Class<T> enumClass) {
        configuration = Collections.unmodifiableMap(introspectEnum(enumClass));
    }

    @Override
    public Map getTaskConfiguration() {
        return configuration;
    }

    private Map introspectEnum(Class<T> enumClass) {
        try {
            final Map cfg = new HashMap();

            // inspect the enum in search for annotated fields
            final Field propertyNameField = getPropertyName(enumClass);
            final Field propertyValueField = getPropertyValue(enumClass);

            // builds the property map
            for (T field : enumClass.getEnumConstants()) {
                cfg.put(propertyNameField.get(field), getPropertyDefaultValue(propertyValueField, field));
            }

            return cfg;
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("An unexpected error occurred when retrieving the config property name", e);
        }
    }

    private Field getPropertyName(Class<T> enumClass) {
        Field field = getAnnotatedField(enumClass, PropertyName.class);
        if (field != null) {
            return field;
        }
        throw new IllegalArgumentException(String.format("Enum '%s' does not specify the property name. " +
                "Exactly one field needs to be annotated with @PropertyName", enumClass.getName()));
    }

    private Field getPropertyValue(Class<T> enumClass) {
        return getAnnotatedField(enumClass, PropertyValue.class);
    }

    private Map getPropertyDefaultValue(Field propertyValueField, T field) throws IllegalAccessException {
        if(propertyValueField == null) {
            return null;
        }

        final Object value = propertyValueField.get(field);
        if(value == null) {
            return null;
        }

        final Map valueMap = new HashMap();
        valueMap.put("default-value", value);
        return valueMap;
    }

    private Field getAnnotatedField(Class<T> enumClass, Class<? extends Annotation> annotationClass) {
        for (Field field : enumClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }
}
