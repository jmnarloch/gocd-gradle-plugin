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
package io.jmnarloch.cd.go.plugin.gradle.api.executor;

/**
 * The task execution result.
 *
 * @author Jakub Narloch
 */
public class ExecutionResult {

    /**
     * Indicates whether task completed with success.
     */
    private final boolean success;

    /**
     * Any additional task execution message.
     */
    private final String message;

    /**
     * The exception that occurred during execution, if any.
     */
    private final Exception exception;

    /**
     * Creates instance of {@link ExecutionResult} with the detailed message.
     *
     * @param success flag indicating whether the task completed with success
     * @param message the additional message
     */
    private ExecutionResult(boolean success, String message) {
        this(success, message, null);
    }

    /**
     * Creates instance of {@link ExecutionResult} with the detailed message and exception.
     * @param success flag indicating whether the task completed with success
     * @param message the additional message
     * @param exc the optional exception
     */
    private ExecutionResult(boolean success, String message, Exception exc) {

        this.success = success;
        this.message = message;
        this.exception = exc;
    }

    /**
     * Retrieves whether the request was successful or not.
     *
     * @return whether the request was successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Retrieves the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves an exception, that could occur during unsuccessful execution.
     *
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Creates new successful execution result.
     *
     * @param message the optional message
     * @return the successful task execution result
     */
    public static ExecutionResult success(String message) {
        return new ExecutionResult(true, message);
    }

    /**
     * Creates new failure execution result.
     *
     * @param message the optional message
     * @return the failed task execution result
     */
    public static ExecutionResult failure(String message) {
        return failure(message, null);
    }

    /**
     * Creates new failure execution result.
     *
     * @param message the optional message
     * @param exc     the optional exception
     * @return the failed task execution result
     */
    public static ExecutionResult failure(String message, Exception exc) {
        return new ExecutionResult(false, message, exc);
    }
}
