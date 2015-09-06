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

import io.jmnarloch.cd.go.plugin.gradle.api.validation.AbstractTaskValidator;
import io.jmnarloch.cd.go.plugin.gradle.api.validation.ValidationErrors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 *
 */
public class GradleTaskValidator extends AbstractTaskValidator {

    @Override
    public void validate(Map<String, Object> properties, ValidationErrors errors) {

        if(StringUtils.isBlank(getProperty(properties, GradleTaskConfig.TASKS.getName()))) {
            errors.addError(GradleTaskConfig.TASKS.getName(), "You need to specify Gradle tasks");
        }
    }
}
