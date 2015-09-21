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
package io.jmnarloch.cd.go.plugin.gradle.api.exception;

/**
 * The base exception used by the plugin API used to trigger any error situations.
 *
 * @author Jakub Narloch
 */
public class PluginException extends RuntimeException {

    /**
     * Creates new instance of {@link PluginException} class with detailed error message.
     *
     * @param message the detailed error message
     */
    public PluginException(String message) {
        super(message);
    }

    /**
     * Creates new instance of {@link PluginException} class with detailed error message and inner cause.
     *
     * @param message the detailed error message
     * @param cause the inner cause
     */
    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
