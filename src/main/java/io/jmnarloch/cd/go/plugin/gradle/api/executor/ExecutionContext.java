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
package io.jmnarloch.cd.go.plugin.gradle.api.executor;

import java.util.Map;

/**
 * Provides to the task current execution context, environment variables working directory etc.
 *
 * @author Jakub Narloch
 */
public class ExecutionContext {

    /**
     * Environment variables.
     */
    private final Map<String, String> environmentVariables;

    /**
     * Current working directory.
     */
    private final String workingDirectory;

    /**
     * Creates new instance of {@link ExecutionContext} class.
     *
     * @param props the properties
     */
    public ExecutionContext(Map props) {

        // TODO validate the input
        // TODO either cast this or copy the map with casting to string
        environmentVariables = (Map<String, String>) props.get("environmentVariables");
        workingDirectory = (String) props.get("workingDirectory");
    }

    /**
     * Returns the environment variables.
     *
     * @return the environment variables
     */
    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    /**
     * The working directory.
     *
     * @return the path to the working directory
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }
}
