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
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import io.jmnarloch.cd.go.plugin.gradle.api.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class TaskCommand extends BaseCommand implements ApiCommand {

    private final TaskExecutor taskExecutor;

    public TaskCommand(TaskExecutor taskExecutor) {
        // TODO validate input
        this.taskExecutor = taskExecutor;
    }

    @Override
    public GoPluginApiResponse execute(GoPluginApiRequest request) {

        final Map req = parseRequest(request);
        final ExecutionContext ctx = new ExecutionContext((Map)req.get("context"));
        final ExecutionConfiguration cfg = new ExecutionConfiguration((Map)req.get("config"));

        final Map<String, Object> response = new HashMap<>();
        try {
            final ExecutionResult result = taskExecutor.execute(ctx, cfg, JobConsoleLogger.getConsoleLogger());
            response.put("success", result.isSuccess());
            response.put("message", result.getMessage());
            return createResponse(result.isSuccess() ? DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE
                    : DefaultGoPluginApiResponse.INTERNAL_ERROR, response);
        } catch(PluginException ex) {

            response.put("exception", ex.getMessage());
            return createResponse(DefaultGoPluginApiResponse.INTERNAL_ERROR, response);
        }
    }
}
