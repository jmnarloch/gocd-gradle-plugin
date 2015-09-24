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
package io.jmnarloch.cd.go.plugin.gradle.api.validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores the validation errors.
 *
 * @author Jakub Narloch
 */
public final class ValidationErrors {

    /**
     * Key/value map of the validation errors, where the as key should be registered the property name.
     */
    private final Map<String, String> errors = new HashMap<String, String>();

    /**
     * Registers new validation error with specific key and optional error message.
     *
     * @param key the error key
     * @param message the validation message
     */
    public void addError(String key, String message) {
        errors.put(key, message);
    }

    /**
     * Returns whether any error has been registered.
     *
     * @return the validation errors
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Retrieves map of registered validation errors.
     *
     * @return the validation errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }
}
