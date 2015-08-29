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

import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.response.validation.ValidationError;
import com.thoughtworks.go.plugin.api.response.validation.ValidationResult;
import com.thoughtworks.go.plugin.api.task.Task;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import com.thoughtworks.go.plugin.api.task.TaskView;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Jakub Narloch
 */
@Extension
public class GradleTask implements Task {

    private static final Logger logger = Logger.getLoggerFor(GradleTask.class);

    @Override
    public TaskConfig config() {

        final TaskConfig taskConfig = new TaskConfig();
        taskConfig.addProperty(GradleTaskOptions.USE_WRAPPER_KEY).withDefault("true");
        taskConfig.addProperty(GradleTaskOptions.GRADLE_HOME_KEY);
        taskConfig.addProperty(GradleTaskOptions.TASKS_KEY);
        taskConfig.addProperty(GradleTaskOptions.DAEMON_KEY);
        taskConfig.addProperty(GradleTaskOptions.DEBUG_KEY);
        taskConfig.addProperty(GradleTaskOptions.OFFLINE_KEY);
        return taskConfig;
    }

    @Override
    public TaskExecutor executor() {
        return new GradleTaskExecutor();
    }

    @Override
    public TaskView view() {
        return new GradleTaskView();
    }

    @Override
    public ValidationResult validate(TaskConfig configuration) {
        final ValidationResult validationResult = new ValidationResult();
        if(StringUtils.isBlank(configuration.getValue(GradleTaskOptions.TASKS_KEY))) {
            validationResult.addError(new ValidationError("You need to specify Gradle tasks"));
        }
        return validationResult;
    }
}
