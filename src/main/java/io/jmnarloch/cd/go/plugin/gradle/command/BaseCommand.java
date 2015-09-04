package io.jmnarloch.cd.go.plugin.gradle.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Map;

/**
 *
 */
public abstract class BaseCommand {

    private final Gson gson;

    public BaseCommand() {
        gson = new GsonBuilder().serializeNulls().create();
    }

    protected Map parseRequest(GoPluginApiRequest request) {

        return gson.fromJson(request.requestBody(), Map.class);
    }

    protected GoPluginApiResponse createResponse(int responseCode, Map body) {

        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(gson.toJson(body));
        return response;
    }
}
