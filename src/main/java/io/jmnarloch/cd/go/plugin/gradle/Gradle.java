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
package io.jmnarloch.cd.go.plugin.gradle;

/**
 * The Gradle command.
 *
 * @author Jakub Narloch
 */
final class Gradle {

    /**
     * Creates new instance of {@link Gradle}.
     * <p/>
     * The private constructor prevents from instantiation outside this class.
     */
    private Gradle() {
        // empty constructor
    }

    /**
     * The Gradle command.
     */
    private static final String GRADLE = "gradle";

    /**
     * The Gradle wrapper Windows family bat script command.
     */
    private static final String GRADLEW_WINDOWS = "gradlew.bat";

    /**
     * The Gradle wrapper Unix script command.
     */
    private static final String GRADLEW_UNIX = "gradlew";

    /**
     * The Gradle command name.
     *
     * @return the Gradle command name
     */
    public static String gradle() {
        return GRADLE;
    }

    /**
     * The Gradle wrapper command name.
     *
     * @return the Gradle wrapper command name
     */
    public static GradlewCommand gradlew() {
        return GradlewCommand.INSTANCE;
    }

    /**
     * The gradle wrapper command.
     */
    public static final class GradlewCommand {

        /**
         * The single instance.
         */
        private static final GradlewCommand INSTANCE = new GradlewCommand();

        /**
         * Creates new instance of {@link GradlewCommand}.
         * <p/>
         * The private constructor prevents from instantiation outside this class.
         */
        private GradlewCommand() {
        }

        /**
         * Returns the Gradlew command to be executed on windows os family.
         *
         * @return the Gradlew command
         */
        public String windows() {
            return GRADLEW_WINDOWS;
        }

        /**
         * Returns the Gradlew command to be executed on unix os family.
         *
         * @return the Gradlew command
         */
        public String unix() {
            return GRADLEW_UNIX;
        }
    }
}
