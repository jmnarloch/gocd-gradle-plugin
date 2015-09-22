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
package io.jmnarloch.cd.go.plugin.gradle.api.dispatcher;

import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.command.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.configuration.TaskConfiguration;
import io.jmnarloch.cd.go.plugin.gradle.api.executor.TaskExecutor;
import io.jmnarloch.cd.go.plugin.gradle.api.validation.TaskValidator;
import io.jmnarloch.cd.go.plugin.gradle.api.view.TaskView;
import io.jmnarloch.cd.go.plugin.gradle.command.ConfigurationCommand;
import io.jmnarloch.cd.go.plugin.gradle.command.TaskCommand;
import io.jmnarloch.cd.go.plugin.gradle.command.ValidateCommand;
import io.jmnarloch.cd.go.plugin.gradle.command.ViewCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The convenient plugin request dispatcher builder. It creates the new {@link ApiRequestDispatcherBuilder} that will
 * handle incoming request and map those to the registered {@link ApiCommand}.
 *
 * @author Jakub Narloch
 */
public class ApiRequestDispatcherBuilder {

    /**
     * Stores the mapping between the API commands and the registered handlers.
     */
    private final Map<String, ApiCommand> commands = new ConcurrentHashMap<String, ApiCommand>();

    /**
     * Registers the task configuration provider.
     *
     * @param taskConfiguration the task configuration
     * @return the dispatcher builder
     */
    public ApiRequestDispatcherBuilder toConfiguration(TaskConfiguration taskConfiguration) {
        return addCommand(ApiRequests.CONFIGURATION, new ConfigurationCommand(taskConfiguration));
    }

    /**
     * Registers the task configuration validator.
     *
     * @param taskValidator the task configuration validator
     * @return the dispatcher builder
     */
    public ApiRequestDispatcherBuilder toValidator(TaskValidator taskValidator) {
        return addCommand(ApiRequests.VALIDATE, new ValidateCommand(taskValidator));
    }

    /**
     * Registers the task view.
     *
     * @param taskView the task view
     * @return the dispatcher builder
     */
    public ApiRequestDispatcherBuilder toView(TaskView taskView) {
        return addCommand(ApiRequests.VIEW, new ViewCommand(taskView));
    }

    /**
     * Registers the task executor.
     *
     * @param taskExecutor the task executor
     * @return the dispatcher builder
     */
    public ApiRequestDispatcherBuilder toExecutor(TaskExecutor taskExecutor) {
        return addCommand(ApiRequests.EXECUTE, new TaskCommand(taskExecutor));
    }

    /**
     * Builds the api request dispatcher.
     *
     * @return the dispatcher builder
     */
    public ApiRequestDispatcher build() {
        return new ApiRequestDispatcherImpl(commands);
    }

    /**
     * Creates new instance of dispatcher builder.
     *
     * @return the dispatcher builder
     */
    public static ApiRequestDispatcherBuilder dispatch() {
        return new ApiRequestDispatcherBuilder();
    }

    /**
     * Registers the API command.
     *
     * @param name the API request
     * @param command the handler
     * @return the dispatcher builder
     */
    private ApiRequestDispatcherBuilder addCommand(String name, ApiCommand command) {
        this.commands.put(name, command);
        return this;
    }

    /**
     * The base implementation of {@link ApiRequestDispatcher}.
     *
     * @author Jakub Narloch
     */
    private static class ApiRequestDispatcherImpl implements ApiRequestDispatcher {

        /**
         * The logger instance by this class hierarchy.
         */
        private final Logger logger = Logger.getLoggerFor(getClass());

        /**
         * Stores the mapping between the API commands and the registered handlers.
         */
        private final Map<String, ApiCommand> commands;

        /**
         * Creates new instance of {@link ApiRequestDispatcherImpl} class.
         *
         * @param commands the commands mapping
         */
        private ApiRequestDispatcherImpl(Map<String, ApiCommand> commands) {
            this.commands = new ConcurrentHashMap<>(commands);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GoPluginApiResponse dispatch(GoPluginApiRequest request) throws UnhandledRequestTypeException {

            // TODO validate the input

            final ApiCommand command = commands.get(request.requestName());
            if(command != null) {
                logger.info("Found command for request: " + request.requestName());
                return command.execute(request);
            }
            logger.info("No command found for request: " + request.requestName());

            throw new UnhandledRequestTypeException(request.requestName());
        }
    }
}
