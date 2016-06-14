
package com.prueba.modelo.entidades.entry;

import java.util.HashMap;
import java.util.Map;

public class Attributes_category {

    private String imId;
    private String term;
    private String scheme;
    private String label;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Attributes_category() {
    }

    /**
     * @param imId
     * @param scheme
     * @param term
     * @param label
     */
    public Attributes_category(String imId, String term, String scheme, String label) {
        this.imId = imId;
        this.term = term;
        this.scheme = scheme;
        this.label = label;
    }

    /**
     * @return The imId
     */
    public String getImId() {
        return imId;
    }

    /**
     * @param imId The im:id
     */
    public void setImId(String imId) {
        this.imId = imId;
    }

    /**
     * @return The term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term The term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return The scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @param scheme The scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label The label
     */
    public void setLabel(String label) {
        this.label = label;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
