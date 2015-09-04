package io.jmnarloch.cd.go.plugin.gradle.task;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 *
 */
public class GradleTaskValidator extends AbstractTaskValidator {

    @Override
    public void validate(Map properties, ValidationErrors errors) {

        if(StringUtils.isBlank(getProperty(properties, GradleTaskConfig.TASKS.getName()))) {
            errors.addError(GradleTaskConfig.TASKS.getName(), "You need to specify Gradle tasks");
        }
    }
}
