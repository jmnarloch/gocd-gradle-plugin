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
package io.jmnarloch.cd.go.plugin.gradle.command;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.command.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.parser.AbstractJsonParser;
import io.jmnarloch.cd.go.plugin.gradle.parser.gson.GsonParser;

import java.util.Map;

/**
 * The convenient base command class. Provides the common functionality for all the commands.
 *
 * @author Jakub Narloch
 */
public abstract class BaseCommand implements ApiCommand {

    /**
     * The JSON parser.
     */
    private final AbstractJsonParser parser;

    /**
     * Creates the new instance of the {@link BaseCommand}.
     */
    public BaseCommand() {
        this(new GsonParser());
    }

    /**
     * Creates the new instance of the {@link BaseCommand} with the specific request parser.
     *
     * @param parser the request parser
     */
    public BaseCommand(AbstractJsonParser parser) {

        // TODO validate the input
        this.parser = parser;
    }

    /**
     * Parses the API request and returns it content as key/value collection
     * @param request the API request
     * @return the key/value request map
     */
    protected Map parseRequest(GoPluginApiRequest request) {

        return parser.fromJson(request.requestBody(), Map.class);
    }

    /**
     * Creates the response with the specific response code and content.
     *
     * @param responseCode the response code
     * @param body         the body content
     * @return the API response
     */
    protected GoPluginApiResponse createResponse(int responseCode, Map body) {

        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(parser.toJson(body));
        return response;
    }
}
