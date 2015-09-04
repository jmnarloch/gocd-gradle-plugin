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

import io.jmnarloch.cd.go.plugin.gradle.api.PluginException;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskView;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @author Jakub Narloch
 */
public class GradleTaskView implements TaskView {

    private static final String NAME = "Gradle";
    private static final String TEMPLATE_PATH = "/views/gradle.task.template.html";
    private static final String UTF_CHARSET = "UTF-8";

    @Override
    public String displayValue() {
        return NAME;
    }

    @Override
    public String template() {

        try {
            return IOUtils.toString(getClass().getResourceAsStream(TEMPLATE_PATH), UTF_CHARSET);
        } catch (IOException e) {
            throw new PluginException("The view template could not be loaded.", e);
        }
    }
}
