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

import io.jmnarloch.cd.go.plugin.api.exception.PluginException;
import io.jmnarloch.cd.go.plugin.api.view.TaskView;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The Gradle task view.
 *
 * @author Jakub Narloch
 */
public class GradleTaskView implements TaskView {

    /**
     * The Gradle task view name.
     */
    private static final String NAME = "Gradle";

    /**
     * The Gradle view template path.
     */
    private static final String TEMPLATE_PATH = "/views/gradle.task.template.html";

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayValue() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String template() {

        try(InputStream inputStream = getClass().getResourceAsStream(TEMPLATE_PATH)) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PluginException("The view template could not be loaded.", e);
        }
    }
}
