/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.cd.go.plugin.gradle.api.config;

import io.jmnarloch.cd.go.plugin.gradle.api.configuration.TaskConfiguration;

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

        final Map<String, Object> valueMap = new HashMap<>();
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
