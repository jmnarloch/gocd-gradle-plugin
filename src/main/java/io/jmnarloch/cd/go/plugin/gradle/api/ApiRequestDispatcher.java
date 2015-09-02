package io.jmnarloch.cd.go.plugin.gradle.api;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

/**
 *
 */
interface ApiRequestDispatcher {

    GoPluginApiResponse dispatch(GoPluginApiRequest request);
}
