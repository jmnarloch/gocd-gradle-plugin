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
 * The simple utility for reading plugin metadata from {@code plugin.xml} files.
 *
 * @author Jakub Narloch
 */
public class PluginMetadata {

    /**
     * The path to the plugin XML descriptor file.
     */
    private static final String PLUGIN_DESCRIPTOR = "/plugin.xml";

    /**
     * Stores the current plugin metadata information, those are being lazy loaded on first usage.
     */
    private static PluginMetadata pluginMetadata;

    /**
     * The plugin unique id.
     */
    private final String id;

    /**
     * The plugin version.
     */
    private final String version;

    /**
     * The plugin metadata.
     *
     * @param id      the plugin id
     * @param version the plugin version
     */
    private PluginMetadata(String id, String version) {
        this.id = id;
        this.version = version;
    }

    /**
     * Retrieves the plugin id.
     *
     * @return the plugin id
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the plugin version.
     *
     * @return the plugin version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Reads the plugin metadata.
     *
     * @return the plugin metadata
     */
    public synchronized static PluginMetadata getMetadata() {

        if(pluginMetadata == null) {

            String id = null;
            String version = null;

            try(final InputStream inputStream = PluginMetadata.class.getResourceAsStream(PLUGIN_DESCRIPTOR)) {

                final XMLInputFactory factory = XMLInputFactory.newInstance();
                final XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
                while (reader.hasNext()) {
                    final int eventType = reader.next();
                    if(eventType == XMLEvent.START_ELEMENT && "go-plugin".equals(reader.getLocalName())) {

                        for(int attr = 0; attr < reader.getAttributeCount(); attr++) {
                            if("id".equals(reader.getAttributeLocalName(attr))) {
                                id = reader.getAttributeValue(attr);
                            } else if("version".equals(reader.getAttributeLocalName(attr))) {
                                version = reader.getAttributeValue(attr);
                            }
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
