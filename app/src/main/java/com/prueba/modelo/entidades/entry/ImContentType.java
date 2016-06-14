
package com.prueba.modelo.entidades.entry;

import java.util.HashMap;
import java.util.Map;

public class ImContentType {

    private Attributes_contentType attributes;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ImContentType() {
    }

    /**
     * 
     * @param attributes
     */
    public ImContentType(Attributes_contentType attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes_contentType getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes_contentType attributes) {
        this.attributes = attributes;
    }



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
