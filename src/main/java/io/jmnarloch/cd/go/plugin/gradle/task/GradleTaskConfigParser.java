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
package io.jmnarloch.cd.go.plugin.gradle.task;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

/**
 * @author Jakub Narloch
 */
class GradleTaskConfigParser {

    private static final String GRADLE_HOME = "GRADLE_HOME";
    private static final String GRADLEW_WINDOWS = "./gradlew.bat";
    private static final String GRADLEW_UNIX = "./gradlew";
    private static final String GRADLE = "gradle";
    private static final String GRADLE_BIN = "bin";
    
    private final Map<String, String> taskConfig;
    private final List<String> tasks = new ArrayList<String>();
    private final List<String> options = new ArrayList<String>();
    private Map<String, String> environment = new HashMap<String, String>();
    private boolean useWrapper = false;
    private String gradleHome;

    private GradleTaskConfigParser(Map<String, String> taskConfig) {
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
        gradleHome = taskConfig.get(propertyKey);
        return this;
    }

    GradleTaskConfigParser withTasks(String propertyKey) {
        final String tasks = taskConfig.get(propertyKey);
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
        final String additional = taskConfig.get(propertyKey);
        if (!StringUtils.isBlank(additional)) {
            options.addAll(Arrays.asList(additional.split("\\s+")));
        }
        return this;
    }

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

    public static GradleTaskConfigParser fromConfig(Map<String, String> taskConfig) {
        return new GradleTaskConfigParser(taskConfig);
    }

    private String getGradleHome() {
        if(!StringUtils.isBlank(gradleHome)) {
            return gradleHome;
        } else if(!StringUtils.isBlank(environment.get(GRADLE_HOME))) {
            return environment.get(GRADLE_HOME);
        }
        return null;
    }

    private boolean isPropertySet(String propertyKey) {
        return isSet(taskConfig.get(propertyKey));
    }

    private boolean isWindows() {
        final String os = environment.get("os.name");
        return !StringUtils.isBlank(os) && os.toLowerCase().contains("win");
    }

    private static boolean isSet(String value) {
        return !StringUtils.isBlank(value) && Boolean.valueOf(value);
    }

    private String gradle() {
        return GRADLE;
    }

    private GradlewCommand gradlew() {
        return GradlewCommand.INSTANCE;
    }

    private static class GradlewCommand {

        public static final GradlewCommand INSTANCE = new GradlewCommand();

        String windows() {
            return GRADLEW_WINDOWS;
        }

        String unix() {
            return GRADLEW_UNIX;
        }
    }
}
