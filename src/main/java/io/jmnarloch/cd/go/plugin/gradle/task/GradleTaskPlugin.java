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

import com.thoughtworks.go.plugin.api.annotation.Extension;
import io.jmnarloch.cd.go.plugin.gradle.api.config.AnnotatedEnumConfigurationProvider;
import io.jmnarloch.cd.go.plugin.gradle.api.dispatcher.ApiRequestDispatcher;
import io.jmnarloch.cd.go.plugin.gradle.api.dispatcher.ApiRequestDispatcherBuilder;
import io.jmnarloch.cd.go.plugin.gradle.api.plugin.AbstractGoPlugin;

;

/**
 * The Gradle task plugin.
 *
 * @author Jakub Narloch
 */
@Extension
public class GradleTaskPlugin extends AbstractGoPlugin {

    /**
     * Builds the Gradle task plugin request dispatcher.
     *
     * @return the api dispatcher
     */
    @Override
    protected ApiRequestDispatcher buildDispatcher() {
        return ApiRequestDispatcherBuilder.dispatch()
                .toConfiguration(new AnnotatedEnumConfigurationProvider<>(GradleTaskConfig.class))
                .toValidator(new GradleTaskValidator())
                .toView(new GradleTaskView())
                .toExecutor(new GradleTaskExecutor())
                .build();
    }
}
