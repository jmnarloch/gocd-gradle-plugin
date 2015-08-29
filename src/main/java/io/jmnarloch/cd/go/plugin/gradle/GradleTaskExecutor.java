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
import com.thoughtworks.go.plugin.api.response.execution.ExecutionResult;
import com.thoughtworks.go.plugin.api.task.Console;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutionContext;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jakub Narloch
 */
public class GradleTaskExecutor implements TaskExecutor {

    private static final Logger logger = Logger.getLoggerFor(GradleTaskExecutor.class);

    private static final String SUCCESS = "Build success";

    private static final String FAILURE = "Build failure";

    @Override
    public ExecutionResult execute(TaskConfig config, TaskExecutionContext taskExecutionContext) {

        final Console console = taskExecutionContext.console();
        try {
            final ProcessBuilder gradle = buildGradleProcess(config, taskExecutionContext);

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

    private static ProcessBuilder buildGradleProcess(TaskConfig config, TaskExecutionContext environment) {
        final Map<String, String> env = environment.environment().asMap();

        final List<String> command = parse(config, env);

        logger.debug("Executing command: " + command);

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.environment().putAll(env);
        builder.directory(new File(environment.workingDir()));
        return builder;
    }

    private static int execute(ProcessBuilder builder, Console console) throws IOException, InterruptedException {

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

    private static boolean isSuccess(int result) {
        return result == 0;
    }

    private static List<String> parse(TaskConfig config, Map<String, String> env) {

        return GradleTaskConfigParser.fromConfig(config)
                .withEnvironment(env)
                .useWrapper(GradleTaskOptions.USE_WRAPPER_KEY)
                .withGradleHome(GradleTaskOptions.GRADLE_HOME_KEY)
                .withTasks(GradleTaskOptions.TASKS_KEY)
                .withOption(GradleTaskOptions.DEBUG_KEY, "--debug")
                .withOption(GradleTaskOptions.OFFLINE_KEY, "--offline")
                .withOption(GradleTaskOptions.DAEMON_KEY, "--daemon")
                .withAdditionalOptions(GradleTaskOptions.ADDITIONAL_OPTIONS_KEY)
                .build();
    }
}
