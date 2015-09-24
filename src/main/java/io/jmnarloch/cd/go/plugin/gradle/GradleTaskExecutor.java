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
package io.jmnarloch.cd.go.plugin.gradle;

import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import io.jmnarloch.cd.go.plugin.api.executor.ExecutionConfiguration;
import io.jmnarloch.cd.go.plugin.api.executor.ExecutionContext;
import io.jmnarloch.cd.go.plugin.api.executor.ExecutionResult;
import io.jmnarloch.cd.go.plugin.api.executor.TaskExecutor;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The Gradle executor.
 *
 * @author Jakub Narloch
 */
public class GradleTaskExecutor implements TaskExecutor {

    /**
     * The logger instance used by this class.
     */
    private static final Logger logger = Logger.getLoggerFor(GradleTaskExecutor.class);

    /**
     * The build success message.
     */
    private static final String SUCCESS = "Build success";

    /**
     * The build failure message.
     */
    private static final String FAILURE = "Build failure";

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionResult execute(ExecutionContext context, ExecutionConfiguration config, JobConsoleLogger console) {

        try {
            final ProcessBuilder gradle = buildGradleProcess(config, context);

            int result = execute(gradle, console);

            if (!isSuccess(result)) {
                return ExecutionResult.failure(FAILURE);
            }

            return ExecutionResult.success(SUCCESS);
        } catch (Exception e) {
            logger.error("Build failed with error", e);

            console.printLine(e.getMessage());
            console.printLine(ExceptionUtils.getStackTrace(e));

            return ExecutionResult.failure(FAILURE, e);
        }
    }

    /**
     * Builds the Gradle process for later execution, it configures all the build properties based on the current
     * task configuration. It also takes into account the current task execution context.
     *
     * @param config the task configuration
     * @param environment the task execution environment
     * @return the created Gradle build process
     */
    private static ProcessBuilder buildGradleProcess(ExecutionConfiguration config, ExecutionContext environment) {
        final Map<String, String> env = environment.getEnvironmentVariables();

        final List<String> command = parse(config, env);

        logger.debug("Executing command: " + command);

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.environment().putAll(env);
        builder.directory(new File(environment.getWorkingDirectory()));
        return builder;
    }

    /**
     * Executes the actual Gradle build.
     *
     * @param builder the process builder
     * @param console the log output
     * @return the process return value
     * @throws IOException          if any error occurs during I/O operation
     * @throws InterruptedException if any error occurs during process execution
     */
    private static int execute(ProcessBuilder builder, JobConsoleLogger console) throws IOException, InterruptedException {

        Process process = null;
        try {
            process = builder.start();

            console.readOutputOf(process.getInputStream());
            console.readErrorOf(process.getErrorStream());
            return process.waitFor();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * Returns whether the build completed with success.
     *
     * @param result the build execution result
     * @return flag indicating whether the process completed with success
     */
    private static boolean isSuccess(int result) {
        return result == 0;
    }

    /**
     * Utility method that parses the task configuration and builds the list of command line arguments to be passed to
     * the Gradle process.
     *
     * @param config the task configuration
     * @param env the task environment
     * @return the list of Gradle commandline arguments
     */
    private static List<String> parse(ExecutionConfiguration config, Map<String, String> env) {

        return GradleTaskConfigParser.fromConfig(config)
                .withEnvironment(env)
                .useWrapper(GradleTaskConfig.USE_WRAPPER.getName())
                .withGradleHome(GradleTaskConfig.GRADLE_HOME.getName())
                .withTasks(GradleTaskConfig.TASKS.getName())
                .withOption(GradleTaskConfig.DEBUG.getName(), "--debug")
                .withOption(GradleTaskConfig.OFFLINE.getName(), "--offline")
                .withOption(GradleTaskConfig.DAEMON.getName(), "--daemon")
                .withAdditionalOptions(GradleTaskConfig.ADDITIONAL_OPTIONS.getName())
                .build();
    }
}
