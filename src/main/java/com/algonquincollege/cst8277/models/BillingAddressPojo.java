/*****************************************************************c******************o*******v******id********
 * File: BillingAddressPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
// TODO: Auto-generated Javadoc

/**
 * The Class BillingAddressPojo.
 */
@Entity(name="billingAddress")
@DiscriminatorValue(value="B")
public class BillingAddressPojo extends AddressPojo implements Serializable {
    
    /**  explicit set serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The is also shipping. */
    protected boolean isAlsoShipping;

    /**
     * Instantiates a new billing address pojo.
     */
    // JPA requires each @Entity class have a default constructor
    public BillingAddressPojo() {
    }
    
    /**
     * Checks if is also shipping.
     *
     * @return true, if is also shipping
     */
    @Column(name="ALSOSHIPPING")
    public boolean isAlsoShipping() {
        return isAlsoShipping;
    }
    
    /**
     * Sets the also shipping.
     *
     * @param isAlsoShipping the new also shipping
     */
    public void setAlsoShipping(boolean isAlsoShipping) {
        this.isAlsoShipping = isAlsoShipping;
    }
    
}