package io.jmnarloch.cd.go.plugin.gradle.command;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.PluginException;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskView;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ViewCommand extends BaseCommand implements ApiCommand {

    private final TaskView taskView;

    public ViewCommand(TaskView taskView) {
        this.taskView = taskView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GoPluginApiResponse execute(GoPluginApiRequest request) {

        int responseCode = DefaultGoApiResponse.SUCCESS_RESPONSE_CODE;
        final Map response = new HashMap();
        try {
            response.put("displayValue", taskView.displayValue());
            response.put("template", taskView.template());
        } catch(PluginException ex) {

            responseCode = DefaultGoApiResponse.INTERNAL_ERROR;
            response.put("exception", "Failed to load template: " + ex.getMessage());
        }
        return createResponse(responseCode, response);
    }
}
