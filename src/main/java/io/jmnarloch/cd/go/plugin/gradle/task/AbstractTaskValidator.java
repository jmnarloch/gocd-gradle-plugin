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
package io.jmnarloch.cd.go.plugin.gradle.task;

import io.jmnarloch.cd.go.plugin.gradle.api.TaskValidator;
import io.jmnarloch.cd.go.plugin.gradle.api.ValidationErrors;

import java.util.Map;

/**
 *
 */
public abstract class AbstractTaskValidator implements TaskValidator {

    public abstract void validate(Map<String, Object> properties, ValidationErrors errors);

    @Override
    public ValidationErrors validate(Map<String, Object> properties) {

        final ValidationErrors errors = new ValidationErrors();
        validate(properties, errors);
        return errors;
    }

    protected String getProperty(Map<String, Object> properties, String propertyName) {

        if (!properties.containsKey(propertyName)) {
            return null;
        }
        return (String) ((Map) properties.get(propertyName)).get("value");
    }
}
