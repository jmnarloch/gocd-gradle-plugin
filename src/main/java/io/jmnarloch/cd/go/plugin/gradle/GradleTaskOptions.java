package io.jmnarloch.cd.go.plugin.gradle;

/**
 * @author Jakub Narloch
 */
public interface GradleTaskOptions {

    String USE_WRAPPER_KEY = "UseWrapper";

    String GRADLE_HOME_KEY = "GradleHome";

    String TASKS_KEY = "Tasks";

    String DAEMON_KEY = "Demon";

    String OFFLINE_KEY = "Offline";

    String DEBUG_KEY = "Debug";

    String ADDITIONAL_OPTIONS_KEY = "AdditionalOptions";
}
