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
package io.jmnarloch.cd.go.plugin.gradle.command;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.command.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.configuration.TaskConfiguration;

/**
 *
 */
public class ConfigurationCommand extends BaseCommand implements ApiCommand {

    private final TaskConfiguration taskConfiguration;

    public ConfigurationCommand(TaskConfiguration taskConfiguration) {
        // TODO validate input
        this.taskConfiguration = taskConfiguration;
    }

    @Override
    public GoPluginApiResponse execute(GoPluginApiRequest request) {

        return createResponse(DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE, taskConfiguration.getTaskConfiguration());
    }
}
