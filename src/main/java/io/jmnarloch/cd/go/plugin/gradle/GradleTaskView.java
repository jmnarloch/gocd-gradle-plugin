package io.jmnarloch.cd.go.plugin.gradle;

import com.thoughtworks.go.plugin.api.task.TaskView;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @author Jakub Narloch
 */
public class GradleTaskView implements TaskView {

    public static final String NAME = "Gradle";

    private static final String TEMPLATE_PATH = "/views/gradle.task.template.html";
    private static final String UTF_CHARSET = "UTF-8";

    @Override
    public String displayValue() {
        return NAME;
    }

    @Override
    public String template() {

        try {
            return IOUtils.toString(getClass().getResourceAsStream(TEMPLATE_PATH), UTF_CHARSET);
        } catch (IOException e) {
            return "Could not load view template " + e.getMessage();
        }
    }
}
