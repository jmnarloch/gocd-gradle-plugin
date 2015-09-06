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
 *
 */
public class ExecutionResult {

    private final boolean success;

    private final String message;

    private final Exception exception;

    private ExecutionResult(boolean success, String message) {
        this(success, message, null);
    }

    private ExecutionResult(boolean success, String message, Exception exc) {
        this.success = success;
        this.message = message;
        this.exception = exc;
    }

    public static ExecutionResult success(String message) {
        return new ExecutionResult(true, message);
    }

    public static ExecutionResult failure(String message) {
        return failure(message, null);
    }

    public static ExecutionResult failure(String message, Exception exc) {
        return new ExecutionResult(false, message, exc);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }
}
