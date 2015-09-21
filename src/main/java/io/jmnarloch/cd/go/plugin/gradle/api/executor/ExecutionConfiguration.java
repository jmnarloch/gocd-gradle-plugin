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

import java.util.HashMap;
import java.util.Map;

/**
 * The task execution configuration.
 *
 * @author Jakub Narloch
 */
public class ExecutionConfiguration {

    /**
     * The configuration property map.
     */
    private final Map<String, String> configuration;

    /**
     * Creates new instance of {@link ExecutionConfiguration}.
     *
     * @param configuration the task configuration
     */
    public ExecutionConfiguration(Map<String, String> configuration) {

        // TODO validate the input
        this.configuration = new HashMap<>(configuration);
    }

    /**
     * Retrives the task execution configuration.
     *
     * @return the configuration
     */
    public Map<String, String> getConfiguration() {
        return configuration;
    }
}
