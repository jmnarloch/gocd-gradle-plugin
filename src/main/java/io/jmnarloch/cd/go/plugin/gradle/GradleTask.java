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
