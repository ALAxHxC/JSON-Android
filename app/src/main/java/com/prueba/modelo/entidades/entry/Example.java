
package com.prueba.modelo.entidades.entry;

import java.util.HashMap;
import java.util.Map;
public class Example {

    private Entry entry;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Example() {
    }

    /**
     * 
     * @param entry
     */
    public Example(Entry entry) {
        this.entry = entry;
    }

    /**
     * 
     * @return
     *     The entry
     */
    public Entry getEntry() {
        return entry;
    }

    /**
     * 
     * @param entry
     *     The entry
     */
    public void setEntry(Entry entry) {
        this.entry = entry;
    }



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
