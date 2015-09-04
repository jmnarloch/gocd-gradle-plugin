package io.jmnarloch.cd.go.plugin.gradle.api;

import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;

/**
 *
 */
public interface TaskExecutor {

    void execute(ExecutionContext context, ExecutionConfiguration configuration, JobConsoleLogger console);
}
