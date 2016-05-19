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
package io.jmnarloch.cd.go.plugin.gradle;

import io.jmnarloch.cd.go.plugin.api.config.ConfigProperty;
import io.jmnarloch.cd.go.plugin.api.config.PropertyName;

/**
 * The Gradle task configuration.
 *
 * @author Jakub Narloch
 */
public enum GradleTaskConfig {

    /**
     * Whether to use the Gradle wrapper.
     */
    @ConfigProperty(defaultValue = "true")
    USE_WRAPPER("UseWrapper"),

    /**
     * Whether to add executable permission to the Gradle wrapper script.
     */
    @ConfigProperty(defaultValue = "true")
    MAKE_WRAPPER_EXECUTABLE("MakeWrapperExecutable"),

    /**
     * The Gradle home directory.
     */
    @ConfigProperty
    GRADLE_HOME("GradleHome"),

    /**
     * The tasks (goals) to execute.
     */
    @ConfigProperty(required = true)
    TASKS("Tasks"),

    /**
     * Whether to use run build through Gradle daemon.
     */
    @ConfigProperty
    DAEMON("Daemon"),

    /**
     * Whether to run the build in offline mode.
     */
    @ConfigProperty
    OFFLINE("Offline"),

    /**
     * Whether to output the build log in debug mode.
     */
    @ConfigProperty
    DEBUG("Debug"),

    /**
     * Additional options to be passed to Gradle process.
     */
    @ConfigProperty
    ADDITIONAL_OPTIONS("AdditionalOptions");

    /**
     * The property name.
     */
    @PropertyName
    private String name;

    /**
     * Creates new instance of {@link GradleTaskConfig} with property name.
     *
     * @param name the property name
     */
    GradleTaskConfig(String name) {
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
}
