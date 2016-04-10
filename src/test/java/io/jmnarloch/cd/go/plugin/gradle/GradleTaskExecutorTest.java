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

import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import io.jmnarloch.cd.go.plugin.api.executor.ExecutionConfiguration;
import io.jmnarloch.cd.go.plugin.api.executor.ExecutionContext;
import io.jmnarloch.cd.go.plugin.api.executor.ExecutionResult;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.jmnarloch.cd.go.plugin.gradle.GradleTaskConfig.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests the {@link GradleTaskExecutor} class.
 *
 * @author Jakub Narloch
 */
public class GradleTaskExecutorTest {

    /**
     * Instance of the tested class.
     */
    private GradleTaskExecutor instance;

    /**
     * Sets up the tests environment.
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new GradleTaskExecutor();
    }

    @Test
    public void shouldBuildGradleProject() throws Exception {

        // given
        final ExecutionContext executionContext = createExecutionContext();
        final ExecutionConfiguration executionConfiguration = createExecutionConfig(Collections.<String, String>emptyMap());
        final JobConsoleLogger jobConsoleLogger = createConsoleLogger();

        // when
        final ExecutionResult result = instance.execute(executionContext, executionConfiguration, jobConsoleLogger);

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldBuildGradleProjectUsingWrapper() throws Exception {

        // given
        final ExecutionContext executionContext = createExecutionContext();
        final ExecutionConfiguration executionConfiguration =
                createExecutionConfig(singletonMap(USE_WRAPPER.getName(), TRUE.toString()));
        final JobConsoleLogger jobConsoleLogger = createConsoleLogger();

        // when
        final ExecutionResult result = instance.execute(executionContext, executionConfiguration, jobConsoleLogger);

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldBuildGradleProjectUsingWrapperAndSetExecutablePermission() throws Exception {

        // given
        final ExecutionContext executionContext = createExecutionContext();
        final Map<String, String> settings = new HashMap<>();
        settings.put(USE_WRAPPER.getName(), TRUE.toString());
        settings.put(MAKE_WRAPPER_EXECUTABLE.getName(), TRUE.toString());
        final ExecutionConfiguration executionConfiguration = createExecutionConfig(settings);
        final JobConsoleLogger jobConsoleLogger = createConsoleLogger();

        // when
        final ExecutionResult result = instance.execute(executionContext, executionConfiguration, jobConsoleLogger);

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldBuildGradleProjectUsingWrapperInWindowsRelativeDirectory() throws Exception {

        // given
        final ExecutionContext executionContext = createExecutionContext("src\\test\\resources\\gradle");
        final Map<String, String> settings = new HashMap<>();
        settings.put(USE_WRAPPER.getName(), TRUE.toString());
        final ExecutionConfiguration executionConfiguration = createExecutionConfig(settings);
        final JobConsoleLogger jobConsoleLogger = createConsoleLogger();

        // when
        final ExecutionResult result = instance.execute(executionContext, executionConfiguration, jobConsoleLogger);

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldBuildGradleProjectUsingWrapperInRelativeDirectory() throws Exception {

        // given
        final ExecutionContext executionContext = createExecutionContext("src\\test\\resources");
        final Map<String, String> settings = new HashMap<>();
        settings.put(USE_WRAPPER.getName(), TRUE.toString());
        settings.put(RELATIVE_PATH.getName(),"gradle");
        final ExecutionConfiguration executionConfiguration = createExecutionConfig(settings);
        final JobConsoleLogger jobConsoleLogger = createConsoleLogger();

        // when
        final ExecutionResult result = instance.execute(executionContext, executionConfiguration, jobConsoleLogger);

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @SuppressWarnings("unchecked")
    private ExecutionContext createExecutionContext() {
        return createExecutionContext(Paths.get("src/test/resources/gradle").toAbsolutePath().toString());
    }

    @SuppressWarnings("unchecked")
    private ExecutionContext createExecutionContext(String workingDirectory) {
        final Map<String, Object> config = new HashMap<>();
        final Map<String, String> env = new LinkedHashMap<>((Map)System.getProperties());
        config.put("workingDirectory", workingDirectory);
        config.put("environmentVariables", env);
        return new ExecutionContext(config);
    }

    private ExecutionConfiguration createExecutionConfig(Map<String, String> overrides) {
        final Map<String, Object> config = new HashMap<>();
        addConfigProperty(config, USE_WRAPPER.getName(), FALSE.toString());
        addConfigProperty(config, TASKS.getName(), "clean build");
        for(Map.Entry<String, String> property : overrides.entrySet()) {
            addConfigProperty(config, property.getKey(), property.getValue());
        }
        return new ExecutionConfiguration(config);
    }

    private void addConfigProperty(Map<String, Object> config, String name, String value) {
        config.put(name, singletonMap("value", value));
    }

    private JobConsoleLogger createConsoleLogger() {
        return mock(JobConsoleLogger.class);
    }
}