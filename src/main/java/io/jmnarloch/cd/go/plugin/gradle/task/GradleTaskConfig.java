package io.jmnarloch.cd.go.plugin.gradle.task;

import io.jmnarloch.cd.go.plugin.gradle.api.PropertyName;
import io.jmnarloch.cd.go.plugin.gradle.api.PropertyValue;

/**
 *
 */
public enum GradleTaskConfig {

    USE_WRAPPER("UseWrapper", "true"),

    GRADLE_HOME("GradleHome"),

    TASKS("Tasks"),

    DAEMON("Deamon"),

    OFFLINE("Offline"),

    DEBUG("Debug"),

    ADDITIONAL_OPTIONS("AdditionalOptions");

    @PropertyName
    private String name;

    @PropertyValue
    private String defaultValue;

    GradleTaskConfig(String name) {
        this(name, null);
    }

    GradleTaskConfig(String name, String defaultValue) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
