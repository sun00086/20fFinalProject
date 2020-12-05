/**************************************************************************************************
 * File: ConfigureJacksonObjectMapper.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 * Note: students do NOT need to change anything in this class
 */
package com.algonquincollege.cst8277.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.algonquincollege.cst8277.utils.HttpErrorAsJSONServlet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigureJacksonObjectMapper.
 */
@Provider
public class ConfigureJacksonObjectMapper implements ContextResolver<ObjectMapper> {
    
    /** The object mapper. */
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new configure jackson object mapper.
     */
    public ConfigureJacksonObjectMapper() {
        this.objectMapper = createObjectMapper();
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }

    /**
     * Creates the object mapper.
     *
     * @return the object mapper
     */
    //configure JDK 8's new DateTime objects to use proper ISO-8601 timeformat
    protected ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            // lenient parsing of JSON - if a field has a typo, don't fall to pieces
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            ;
        HttpErrorAsJSONServlet.setObjectMapper(mapper);
        return mapper;
    }
}