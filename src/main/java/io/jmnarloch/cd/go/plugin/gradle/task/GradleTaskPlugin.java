package io.jmnarloch.cd.go.plugin.gradle.task;

import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiRequestDispatcher;

import java.util.Collections;

/**
 *
 */
@Extension
public class GradleTaskPlugin implements GoPlugin {

    private final ApiRequestDispatcher dispatcher;

    public GradleTaskPlugin() {

        // builds the request dispatcher
        dispatcher = ApiRequestDispatcherBuilder.dispatch()
                .toConfiguration(new AnnotatedEnumConfigurationProvider<GradleTaskConfig>(GradleTaskConfig.class))
                .toValidator(new GradleTaskValidator())
                .toView(new GradleTaskView())
                .toExecutor(new GradleTaskExecutor())
                .build();
    }

    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
        // empty method
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest requestMessage) throws UnhandledRequestTypeException {
        // dispatches the request to configured class
        return dispatcher.dispatch(requestMessage);
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        // TODO move to settings
        return new GoPluginIdentifier("Gradle Task Plugin", Collections.singletonList("1.0.0"));
    }
}
