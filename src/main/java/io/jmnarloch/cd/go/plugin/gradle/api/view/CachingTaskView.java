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
package io.jmnarloch.cd.go.plugin.gradle.api.view;

/**
 * A cacheable task view.
 *
 * @author Jakub Narloch
 */
public class CachingTaskView implements TaskView {

    /**
     * The delegates task view.
     */
    private final TaskView delegate;

    /**
     * The sychronizaiton object.
     */
    private final Object syncObject = new Object();

    /**
     * The task title.
     */
    private String displayValue;

    /**
     * The task view.
     */
    private String template;

    /**
     * Creates new instance of {@link CachingTaskView} class.
     *
     * @param delegate the delegated task view
     */
    public CachingTaskView(TaskView delegate) {
        // TODO check input
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayValue() {

        synchronized (syncObject) {
            if(displayValue == null) {
                displayValue = delegate.displayValue();
            }
        }
        return displayValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String template() {

        synchronized (syncObject) {
            if(template == null) {
                template = delegate.template();
            }
        }
        return template;
    }
}
