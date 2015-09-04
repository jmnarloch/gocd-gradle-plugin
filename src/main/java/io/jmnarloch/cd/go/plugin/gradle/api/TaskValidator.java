package io.jmnarloch.cd.go.plugin.gradle.api;

import java.util.Map;

/**
 *
 */
public interface TaskValidator {

    Map<String, String> validate(Map properties);
}
