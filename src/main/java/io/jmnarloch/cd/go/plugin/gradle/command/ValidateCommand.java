package io.jmnarloch.cd.go.plugin.gradle.command;

import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import io.jmnarloch.cd.go.plugin.gradle.api.ApiCommand;
import io.jmnarloch.cd.go.plugin.gradle.api.TaskValidator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ValidateCommand extends BaseCommand implements ApiCommand {

    private final TaskValidator taskValidator;

    public ValidateCommand(TaskValidator taskValidator) {
        // TODO validate input
        this.taskValidator = taskValidator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GoPluginApiResponse execute(GoPluginApiRequest request) {

        final Map<String, String> errors = taskValidator.validate(parseRequest(request));

        int responseCode = DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE;
        final Map response = new HashMap();

        if(!errors.isEmpty()) {
            responseCode = DefaultGoPluginApiResponse.VALIDATION_FAILED;
            response.put("errors", errors);
        }

        return createResponse(responseCode, response);
    }
}
