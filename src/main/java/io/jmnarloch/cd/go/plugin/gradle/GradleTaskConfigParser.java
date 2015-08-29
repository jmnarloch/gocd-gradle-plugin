package io.jmnarloch.cd.go.plugin.gradle;

import com.thoughtworks.go.plugin.api.task.TaskConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

/**
 * @author Jakub Narloch
 */
class GradleTaskConfigParser {

    private final TaskConfig taskConfig;
    private final List<String> tasks = new ArrayList<String>();
    private final List<String> options = new ArrayList<String>();
    private Map<String, String> environment = new HashMap<String, String>();
    private boolean useWrapper = false;
    private String gradleHome;

    private GradleTaskConfigParser(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }

    GradleTaskConfigParser withEnvironment(Map<String, String> environment) {
        this.environment = environment;
        return this;
    }

    GradleTaskConfigParser useWrapper(String propertyKey) {
        useWrapper = isPropertySet(propertyKey);
        return this;
    }

    GradleTaskConfigParser withGradleHome(String propertyKey) {
        gradleHome = taskConfig.getValue(propertyKey);
        return this;
    }

    GradleTaskConfigParser withTaks(String propertyKey) {
        final String tasks = taskConfig.getValue(propertyKey);
        if (!StringUtils.isBlank(tasks)) {
            this.tasks.addAll(Arrays.asList(tasks.split("\\s+")));
        }
        return this;
    }

    GradleTaskConfigParser withOption(String propertyKey, String option) {
        if (isPropertySet(propertyKey)) {
            options.add(option);
        }
        return this;
    }

    GradleTaskConfigParser withAdditionalOptions(String propertyKey) {
        final String additional = taskConfig.getValue(propertyKey);
        if (!StringUtils.isBlank(additional)) {
            options.addAll(Arrays.asList(additional.split("\\s+")));
        }
        return this;
    }

    List<String> build() {
        final List<String> command = new ArrayList<String>();

        if (useWrapper) {
            if (isWindows()) {
                command.add("./gradlew.bat");
            } else {
                command.add("./gradlew");
            }
        } else {
            if (!StringUtils.isBlank(gradleHome)) {
                command.add(new File(gradleHome, "gradle").getAbsolutePath());
            } else {
                command.add("gradle");
            }
        }
        command.addAll(options);
        command.addAll(tasks);
        return command;
    }

    public static GradleTaskConfigParser fromConfig(TaskConfig taskConfig) {
        return new GradleTaskConfigParser(taskConfig);
    }

    private boolean isPropertySet(String propertyKey) {
        return isSet(taskConfig.getValue(propertyKey));
    }

    private boolean isWindows() {
        final String os = environment.get("os.name");
        return !StringUtils.isBlank(os) && os.toLowerCase().contains("win");
    }

    private static boolean isSet(String value) {
        return !StringUtils.isBlank(value) && Boolean.valueOf(value);
    }
}
