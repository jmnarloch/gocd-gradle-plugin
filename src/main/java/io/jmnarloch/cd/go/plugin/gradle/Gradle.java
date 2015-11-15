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
     * The Gradle command name.
     *
     * @return the Gradle command name
     */
    public static GradleCommand gradle() {
        return GradleCommand.INSTANCE;
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
     * A simple command abstraction.
     */
    public interface Command {

        /**
         * Returns the windows command.
         *
         * @return the windows command
         */
        String windows();

        /**
         * Returns the unix command.
         *
         * @return the unix command
         */
        String unix();
    }

    /**
     * The Gradle command.
     */
    public static final class GradleCommand implements Command {

        /**
         * The Gradle Windows family executable name.
         */
        private static final String GRADLE_WINDOWS = "./gradle.bat";

        /**
         * The Gradle Unix family executable name.
         */
        private static final String GRADLE_UNIX = "./gradle";

        /**
         * The command instance.
         */
        private static final GradleCommand INSTANCE = new GradleCommand();

        /**
         * Returns the Gradle command to be executed on windows os family.
         *
         * @return the Gradle command
         */
        @Override
        public String windows() {
            return GRADLE_WINDOWS;
        }

        /**
         * Returns the Gradle command to be executed on unix os family.
         *
         * @return the Gradle command
         */
        @Override
        public String unix() {
            return GRADLE_UNIX;
        }
    }

    /**
     * The gradle wrapper command.
     */
    public static final class GradlewCommand implements Command {

        /**
         * The Gradle wrapper Windows family bat script command.
         */
        private static final String GRADLEW_WINDOWS = "./gradlew.bat";

        /**
         * The Gradle wrapper Unix script command.
         */
        private static final String GRADLEW_UNIX = "./gradlew";

        /**
         * The command instance.
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
