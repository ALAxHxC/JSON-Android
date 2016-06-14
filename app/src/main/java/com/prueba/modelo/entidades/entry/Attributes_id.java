
package com.prueba.modelo.entidades.entry;

import java.util.HashMap;
import java.util.Map;
public class Attributes_id {

    private String imId;
    private String imBundleId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attributes_id() {
    }

    /**
     * 
     * @param imId
     * @param imBundleId
     */
    public Attributes_id(String imId, String imBundleId) {
        this.imId = imId;
        this.imBundleId = imBundleId;
    }

    /**
     * 
     * @return
     *     The imId
     */
    public String getImId() {
        return imId;
    }

    /**
     * 
     * @param imId
     *     The im:id
     */
    public void setImId(String imId) {
        this.imId = imId;
    }

    /**
     * 
     * @return
     *     The imBundleId
     */
    public String getImBundleId() {
        return imBundleId;
    }

    /**
     * 
     * @param imBundleId
     *     The im:bundleId
     */
    public void setImBundleId(String imBundleId) {
        this.imBundleId = imBundleId;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
