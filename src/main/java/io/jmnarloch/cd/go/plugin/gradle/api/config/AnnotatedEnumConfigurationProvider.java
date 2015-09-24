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
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Inspects the specified enumeration class in search of annotated fields.
 *
 * @author Jakub Narloch
 */
public class AnnotatedEnumConfigurationProvider<T extends Enum> implements TaskConfiguration {

    /**
     * Stores the configuration.
     */
    private final Map configuration;

    /**
     * Creates new instance of {@link AnnotatedEnumConfigurationProvider} class.
     *
     * @param enumClass the enumeration class
     */
    public AnnotatedEnumConfigurationProvider(Class<T> enumClass) {
        configuration = Collections.unmodifiableMap(introspectEnum(enumClass));
    }

    /**
     * Retrieves the task configuration.
     *
     * @return the task configuration
     */
    @Override
    public Map getTaskConfiguration() {
        return configuration;
    }

    /**
     * Introspects the enumeration in search of annotated fields and builds configuration map out of those properties.
     *
     * @param enumClass the enum class
     * @return the map containing the configuration
     */
    @SuppressWarnings("unchecked")
    private Map introspectEnum(Class<T> enumClass) {
        try {
            final Map<String, Map> cfg = new HashMap<>();

            // introspectField the enum in search for annotated fields
            final Field propertyNameField = getPropertyName(enumClass);

            // builds the property map
            for (T field : enumClass.getEnumConstants()) {
                final Field fieldDeclaration = enumClass.getField(field.name());

                if(fieldDeclaration.isAnnotationPresent(ConfigProperty.class)) {
                    cfg.put(getPropertyName(propertyNameField, field), introspectField(fieldDeclaration));
                }
            }

            return cfg;
        } catch (IllegalAccessException | NoSuchFieldException e) {

            throw new IllegalArgumentException("An unexpected error occurred when retrieving the config property name", e);
        }
    }

    /**
     * Introspects the individual enumeration.
     *
     * @param field the enumeration declaration
     * @return the map containing the field properties
     */
    private Map introspectField(Field field) {

        final ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
        if(configProperty == null) {
            throw new IllegalArgumentException("The inspected property needs to be annotated with @ConfigProperty");
        }

        final Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("required", configProperty.required());
        valueMap.put("secure", configProperty.secure());

        if(!StringUtils.isBlank(configProperty.defaultValue())) {
            valueMap.put("default-value", configProperty.defaultValue());
        }
        return valueMap;
    }

    /**
     * Retrieves the property name.
     *
     * @param propertyNameField the field storing the property name
     * @param field             the actual enum instance
     * @return the name of the property mapped by this enum
     * @throws IllegalAccessException if any error occurs
     */
    private String getPropertyName(Field propertyNameField, T field) throws IllegalAccessException {
        if(!String.class.equals(propertyNameField.getType())) {
            throw new IllegalArgumentException("The @PropertyName annotated field needs to be string");
        }

        return (String) propertyNameField.get(field);
    }

    /**
     * Retrieves the field annotated with {@link PropertyName}.
     *
     * @param enumClass the enumeration class
     * @return the field
     */
    private Field getPropertyName(Class<T> enumClass) {
        Field field = getAnnotatedField(enumClass, PropertyName.class);
        if (field != null) {
            return field;
        }
        throw new IllegalArgumentException(String.format("Enum '%s' does not specify the property name. " +
                "Exactly one field needs to be annotated with @PropertyName", enumClass.getName()));
    }

    /**
     * Retrieves the annotated field.
     *
     * @param enumClass the enum class
     * @param annotationClass the annotation class
     * @return retrieves the annotated field
     */
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
