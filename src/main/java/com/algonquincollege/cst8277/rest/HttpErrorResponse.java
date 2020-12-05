/*****************************************************************c******************o*******v******id********
 * File: ErrorResponse.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * Note: students do NOT need to change anything in this class
 */
package com.algonquincollege.cst8277.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The Class HttpErrorResponse.
 */
public class HttpErrorResponse implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The status code. */
    private final int statusCode;
    
    /** The reason phrase. */
    private final String reasonPhrase;

    /**
     * Instantiates a new http error response.
     *
     * @param code the code
     * @param reasonPhrase the reason phrase
     */
    public HttpErrorResponse(int code, String reasonPhrase) {
        this.statusCode = code;
        this.reasonPhrase = reasonPhrase;
    }
    
    /**
     * Gets the status code.
     *
     * @return the status code
     */
    @JsonProperty("status-code")
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the reason phrase.
     *
     * @return the reason phrase
     */
    @JsonProperty("reason-phrase")
    public String getReasonPhrase() {
        return reasonPhrase;
    }

}