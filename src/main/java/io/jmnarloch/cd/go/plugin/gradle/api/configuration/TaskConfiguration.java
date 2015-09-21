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
package io.jmnarloch.cd.go.plugin.gradle.api.configuration;

import java.util.Map;

/**
 * Provides the configuration for the the plugin. The configuration is being returned in a form of key/value pairs.
 * The property name is used as a key, while the value can be represented as {@code null} or a object representing
 * the default value.
 *
 * @author Jakub Narloch
 */
public interface TaskConfiguration {

    /**
     * Returns the task configuration in a form of key/value pairs.
     *
     * @return the task configuration
     */
    Map getTaskConfiguration();
}
