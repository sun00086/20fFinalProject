/*****************************************************************c******************o*******v******id********
 * File: MyObjectMapperProvider.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 * Note: students do NOT need to change anything in this class
 *
 */
package com.algonquincollege.cst8277;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// TODO: Auto-generated Javadoc
/**
 * The Class MyObjectMapperProvider.
 */
@Provider
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {
    
    /** The default object mapper. */
    final ObjectMapper defaultObjectMapper;
    
    /**
     * Instantiates a new my object mapper provider.
     */
    public MyObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper();
    }
    
    /* (non-Javadoc)
     * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
            return defaultObjectMapper;
        }
    
    /**
     * Creates the default mapper.
     *
     * @return the object mapper
     */
    private static ObjectMapper createDefaultMapper() {
        final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}