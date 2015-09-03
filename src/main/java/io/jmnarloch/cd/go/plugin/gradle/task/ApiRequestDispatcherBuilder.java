package io.jmnarloch.cd.go.plugin.gradle.task;

import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiCommand;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class ApiRequestDispatcherBuilder {

    private final Map<String, ApiCommand> commands = new HashMap<String, ApiCommand>();

    ApiRequestDispatcherBuilder taskExecutor(TaskExecutor taskExecutor) {

        return addCommand("", new ApiCommand(){

        });
    }

    private ApiRequestDispatcherBuilder addCommand(String name, ApiCommand command) {
        return this;
    }

    public ApiRequestDispatcherBuilder dispatcher() {
        return new ApiRequestDispatcherBuilder();
    }
}
