package io.jmnarloch.cd.go.plugin.gradle.command;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskExecutor;

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
        // TODO execute task
        return null;
    }
}
