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

import io.jmnarloch.cd.go.plugin.api.executor.ExecutionConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

/**
 * The Gradle configuration parser, that converts the flags into form of command line options needed to execute the
 * Gradle task. Provides the mapping between the task configuration and the actual Gradle process command line options.
 *
 * @author Jakub Narloch
 */
class GradleTaskConfigParser {

    /**
     * The Gradle home. Needed when the wrapper is not used and the Gradle is not specified on system Path.
     */
    private static final String GRADLE_HOME = "GRADLE_HOME";

    /**
     * The Gradle command.
     */
    private static final String GRADLE = "gradle";

    /**
     * Path to the Gradle executables within the GRADLE_HOME.
     */
    private static final String GRADLE_BIN = "bin";

    /**
     * The task configuration.
     */
    private final ExecutionConfiguration configuration;

    /**
     * The Gradle tasks.
     */
    private final List<String> tasks = new ArrayList<String>();

    /**
     * The Gradle options.
     */
    private final List<String> options = new ArrayList<String>();

    /**
     * The execution environment.
     */
    private Map<String, String> environment = new HashMap<String, String>();

    /**
     * Whether to use Gradle wrapper.
     */
    private boolean useWrapper = false;

    /**
     * Gradle HOME dir.
     */
    private String gradleHome;

    /**
     * Creates new instance of {@link GradleTaskConfigParser}.
     *
     * @param config the task configuration
     */
    private GradleTaskConfigParser(ExecutionConfiguration config) {
        this.configuration = config;
    }

    /**
     * Specifies the build environment.
     *
     * @param environment the environment
     * @return the config parser
     */
    GradleTaskConfigParser withEnvironment(Map<String, String> environment) {
        this.environment = environment;
        return this;
    }

    /**
     * Specifies whether to use the Gradle wrapper.
     *
     * @param propertyKey the name of the property that specifies this setting
     * @return the config parser
     */
    GradleTaskConfigParser useWrapper(String propertyKey) {
        useWrapper = isPropertySet(propertyKey);
        return this;
    }

    /**
     * Specifies the Gradle home directory.
     *
     * @param propertyKey the name of the property that specifies this setting
     * @return the config parser
     */
    GradleTaskConfigParser withGradleHome(String propertyKey) {
        gradleHome = configuration.getProperty(propertyKey);
        return this;
    }

    /**
     * Specifies the Gradle build file tasks to be executed.
     *
     * @param propertyKey the name of the property that specifies this setting
     * @return the config parser
     */
    GradleTaskConfigParser withTasks(String propertyKey) {
        final String tasks = configuration.getProperty(propertyKey);
        if (!StringUtils.isBlank(tasks)) {
            this.tasks.addAll(Arrays.asList(tasks.split("\\s+")));
        }
        return this;
    }

    /**
     * Registers command line option.
     *
     * @param propertyKey the name of the property that specifies this setting
     * @param option the corresponding Gradle command line option
     * @return the config parser
     */
    GradleTaskConfigParser withOption(String propertyKey, String option) {
        if (isPropertySet(propertyKey)) {
            options.add(option);
        }
        return this;
    }

    /**
     * Specifies the additional Gradle command line options to be passed to the build.
     *
     * @param propertyKey the name of the property that specifies this setting
     * @return the config parser
     */
    GradleTaskConfigParser withAdditionalOptions(String propertyKey) {
        final String additional = configuration.getProperty(propertyKey);
        if (!StringUtils.isBlank(additional)) {
            options.addAll(Arrays.asList(additional.split("\\s+")));
        }
        return this;
    }

    /**
     * Builds the command line process.
     *
     * @return the list of task to be executed
     */
    List<String> build() {
        final List<String> command = new ArrayList<String>();

        if (useWrapper) {
            if (isWindows()) {
                command.add(gradlew().windows());
            } else {
                command.add(gradlew().unix());
            }
        } else {
            final String gradleHome = getGradleHome();

            if (!StringUtils.isBlank(gradleHome)) {
                command.add(new File(new File(gradleHome, GRADLE_BIN), gradle()).getAbsolutePath());
            } else {
                command.add(gradle());
            }
        }
        command.addAll(options);
        command.addAll(tasks);
        return command;
    }

    /**
     * Creates new instance of {@link GradleTaskConfigParser}.
     *
     * @param config the task configuration
     * @return the config parser
     */
    public static GradleTaskConfigParser fromConfig(ExecutionConfiguration config) {
        return new GradleTaskConfigParser(config);
    }

    /**
     * Retrieves the Gradle home directory, which might be either specified as environment variable or overridden for
     * specific task.
     *
     * @return the Gradle home
     */
    private String getGradleHome() {
        if(!StringUtils.isBlank(gradleHome)) {
            return gradleHome;
        } else if(!StringUtils.isBlank(environment.get(GRADLE_HOME))) {
            return environment.get(GRADLE_HOME);
        }
        return null;
    }

    /**
     * Returns whether the given property has been set or not.
     * @param propertyKey the property name
     * @return true if the property value has been specified, false otherwise
     */
    private boolean isPropertySet(String propertyKey) {
        return isSet(configuration.getProperty(propertyKey));
    }

    /**
     * Returns whether the given string property is present.
     *
     * @param value the property value
     * @return true if the property value has been specified, false otherwise
     */
    private static boolean isSet(String value) {
        return !StringUtils.isBlank(value) && Boolean.valueOf(value);
    }

    /**
     * Returns whether current OS family is Windows.
     *
     * @return true if the current task is executed on Windows
     */
    private boolean isWindows() {
        final String os = environment.get("os.name");
        return !StringUtils.isBlank(os) && os.toLowerCase().contains("win");
    }

    /**
     * The Gradle command name.
     * @return the Gradle command name
     */
    private String gradle() {
        return GRADLE;
    }

    /**
     * The Gradle wrapper command name.
     *
     * @return the Gradle wrapper command name
     */
    private GradlewCommand gradlew() {
        return GradlewCommand.INSTANCE;
    }

    /**
     * The gradle wrapper command.
     */
    private static class GradlewCommand {

        /**
         * The Gradle wrapper Windows family bat script command.
         */
        private static final String GRADLEW_WINDOWS = "./gradlew.bat";

        /**
         * The Gradle wrapper Unix script command.
         */
        private static final String GRADLEW_UNIX = "./gradlew";

        /**
         * The single instance.
         */
        public static final GradlewCommand INSTANCE = new GradlewCommand();

        /**
         * Returns the Gradlew command to be executed on windows os family.
         *
         * @return the Gradlew command
         */
        String windows() {
            return GRADLEW_WINDOWS;
        }

        /**
         * Returns the Gradlew command to be executed on unix os family.
         *
         * @return the Gradlew command
         */
        String unix() {
            return GRADLEW_UNIX;
        }
    }
}
