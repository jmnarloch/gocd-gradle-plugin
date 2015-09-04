package io.jmnarloch.cd.go.plugin.gradle.command;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskConfiguration;

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
