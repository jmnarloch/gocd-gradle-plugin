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
package io.jmnarloch.cd.go.plugin.gradle.api;

/**
 * @author Jakub Narloch
 */
public interface GradleTaskOptions {

    String USE_WRAPPER_KEY = "UseWrapper";

    String GRADLE_HOME_KEY = "GradleHome";

    String TASKS_KEY = "Tasks";

    String DAEMON_KEY = "Deamon";

    String OFFLINE_KEY = "Offline";

    String DEBUG_KEY = "Debug";

    String ADDITIONAL_OPTIONS_KEY = "AdditionalOptions";
}
