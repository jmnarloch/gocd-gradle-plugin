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
import com.thoughtworks.go.plugin.api.response.DefaultGoApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.PluginException;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskView;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ViewCommand extends BaseCommand implements ApiCommand {

    private final TaskView taskView;

    public ViewCommand(TaskView taskView) {
        this.taskView = taskView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GoPluginApiResponse execute(GoPluginApiRequest request) {

        int responseCode = DefaultGoApiResponse.SUCCESS_RESPONSE_CODE;
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("displayValue", taskView.displayValue());
            response.put("template", taskView.template());
        } catch(PluginException ex) {

            responseCode = DefaultGoApiResponse.INTERNAL_ERROR;
            response.put("exception", ex.getMessage());
        }
        return createResponse(responseCode, response);
    }
}
