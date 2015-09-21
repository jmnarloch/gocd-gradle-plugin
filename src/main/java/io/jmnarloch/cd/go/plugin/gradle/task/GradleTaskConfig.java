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

import io.jmnarloch.cd.go.plugin.gradle.api.config.PropertyName;
import io.jmnarloch.cd.go.plugin.gradle.api.config.DefaultValue;

/**
 * The Gradle task configuration.
 *
 * @author Jakub Narloch
 */
public enum GradleTaskConfig {

    /**
     * Whether to use the Gradle wrapper.
     */
    USE_WRAPPER("UseWrapper", "true"),

    /**
     * The Gradle home directory.
     */
    GRADLE_HOME("GradleHome"),

    /**
     * The tasks (goals) to execute.
     */
    TASKS("Tasks"),

    /**
     * Whether to use run build through Gradle deamon.
     */
    DAEMON("Deamon"),

    /**
     * Whether to run the build in offline mode.
     */
    OFFLINE("Offline"),

    /**
     * Whether to output the build log in debug mode.
     */
    DEBUG("Debug"),

    /**
     * Additional options to be passed to Gradle process.
     */
    ADDITIONAL_OPTIONS("AdditionalOptions");

    /**
     * The property name.
     */
    @PropertyName
    private String name;

    /**
     * The property default value if any.
     */
    @DefaultValue
    private String defaultValue;

    /**
     * Creates new instance of {@link GradleTaskConfig} with property name.
     *
     * @param name the property name
     */
    GradleTaskConfig(String name) {
        this(name, null);
    }

    /**
     * Creates new instance of {@link GradleTaskConfig} with property name and default value.
     *
     * @param name the configuration property name
     * @param defaultValue the configuration property default value, maybe null
     */
    GradleTaskConfig(String name, String defaultValue) {
        this.name = name;
    }

    /**
     * Retrieves the configuration property name.
     *
     * @return the configuration property name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the configuration property default value.
     *
     * @return the configuration property default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }
}
