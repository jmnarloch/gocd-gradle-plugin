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
package io.jmnarloch.cd.go.plugin.gradle.api.metadata;

import io.jmnarloch.cd.go.plugin.gradle.api.exception.PluginException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class PluginMetadata {

    private static PluginMetadata pluginMetadata;

    private final String id;
    private final String version;

    public PluginMetadata(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public synchronized static PluginMetadata getMetadata() {

        if(pluginMetadata == null) {
            String id = null;
            String version = null;

            try(final InputStream inputStream = PluginMetadata.class.getResourceAsStream("/plugin.xml")) {

                final XMLInputFactory f = XMLInputFactory.newInstance();
                final XMLStreamReader r = f.createXMLStreamReader(inputStream);
                while (r.hasNext()) {
                    final int eventType = r.next();
                    if(eventType == XMLEvent.START_ELEMENT) {
                        if ("name".equals(r.getLocalName())) {
                            id = r.getText();
                        } else if ("version".equals(r.getLocalName())) {
                            version = r.getText();
                        }
                    }
                }
                pluginMetadata = new PluginMetadata(id, version);
            } catch (XMLStreamException | IOException e) {
                throw new PluginException("Could not load plugin manifest", e);
            }
        }
        return pluginMetadata;
    }
}
