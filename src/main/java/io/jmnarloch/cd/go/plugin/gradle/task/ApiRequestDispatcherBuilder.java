package io.jmnarloch.cd.go.plugin.gradle.task;

import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.*;
import io.jmnarloch.cd.go.plugin.gradle.command.ConfigurationCommand;
import io.jmnarloch.cd.go.plugin.gradle.command.TaskCommand;
import io.jmnarloch.cd.go.plugin.gradle.command.ValidateCommand;
import io.jmnarloch.cd.go.plugin.gradle.command.ViewCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
class ApiRequestDispatcherBuilder {

    private final Map<String, ApiCommand> commands = new ConcurrentHashMap<String, ApiCommand>();

    public ApiRequestDispatcherBuilder toConfiguration(TaskConfiguration taskConfiguration) {
        return addCommand(ApiRequests.CONFIGURATION, new ConfigurationCommand(taskConfiguration));
    }

    public ApiRequestDispatcherBuilder toValidator(TaskValidator taskValidator) {
        return addCommand(ApiRequests.VALIDATE, new ValidateCommand(taskValidator));
    }

    public ApiRequestDispatcherBuilder toView(TaskView taskView) {
        return addCommand(ApiRequests.VIEW, new ViewCommand(taskView));
    }

    public ApiRequestDispatcherBuilder toExecutor(TaskExecutor taskExecutor) {
        return addCommand(ApiRequests.EXECUTE, new TaskCommand(taskExecutor));
    }

    public ApiRequestDispatcher build() {
        return new ApiRequestDispatcherImpl(commands);
    }

    public static ApiRequestDispatcherBuilder dispatch() {
        return new ApiRequestDispatcherBuilder();
    }

    private ApiRequestDispatcherBuilder addCommand(String name, ApiCommand command) {
        this.commands.put(name, command);
        return this;
    }

    private static class ApiRequestDispatcherImpl implements ApiRequestDispatcher {

        private final Map<String, ApiCommand> commands;

        private ApiRequestDispatcherImpl(Map<String, ApiCommand> commands) {
            this.commands = new ConcurrentHashMap<String, ApiCommand>(commands);
        }

        @Override
        public GoPluginApiResponse dispatch(GoPluginApiRequest request) throws UnhandledRequestTypeException {

            final ApiCommand command = commands.get(request.requestName());
            if(command != null) {
                return command.execute(request);
            }

            throw new UnhandledRequestTypeException(request.requestName());
        }
    }
}
