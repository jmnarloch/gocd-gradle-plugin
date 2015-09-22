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
package io.jmnarloch.cd.go.plugin.gradle.api.plugin;

import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.dispatcher.ApiRequestDispatcher;
import io.jmnarloch.cd.go.plugin.gradle.api.metadata.PluginMetadata;

import java.util.Collections;

/**
 * A base implementation of {@link GoPlugin} that instantiates the {@link ApiRequestDispatcher} that is being used for
 * dispatching the request and delegating the execution towards configured handlers.
 *
 * @author Jakub Narloch
 */
public abstract class AbstractGoPlugin implements GoPlugin {

    /**
     * The logger instance by this class hierarchy.
     */
    private final Logger logger = Logger.getLoggerFor(getClass());

    /**
     * The request dispatcher.
     */
    private final ApiRequestDispatcher dispatcher;

    /**
     * Creates new instance of {@link AbstractGoPlugin}.
     */
    public AbstractGoPlugin() {

        this.dispatcher = buildDispatcher();
        // TODO validate state
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
        // empty method
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest requestMessage) throws UnhandledRequestTypeException {

        try {
            logger.debug("Dispatching request: " + requestMessage.requestName());

            // dispatches the request to configured class
            return dispatcher.dispatch(requestMessage);
        } catch (UnhandledRequestTypeException e) {
            logger.error("Unexpected error occurred when processing request.", e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoPluginIdentifier pluginIdentifier() {
        final PluginMetadata pluginMetadata = PluginMetadata.getMetadata();
        return new GoPluginIdentifier(pluginMetadata.getId(), Collections.singletonList(pluginMetadata.getVersion()));
    }

    /**
     * The templates method for building the concrete implementation of {@link ApiRequestDispatcher}.
     *
     * @return the request dispatcher instance
     */
    protected abstract ApiRequestDispatcher buildDispatcher();
}
