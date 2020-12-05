/*****************************************************************c******************o*******v******id********
 * File: ShippingAddressPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class ShippingAddressPojo.
 */
@Entity(name="shippingAddress")
@DiscriminatorValue(value="S")
public class ShippingAddressPojo extends AddressPojo implements Serializable  {
    
    /**  explicit set serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new shipping address pojo.
     */
    // JPA requires each @Entity class have a default constructor
    public ShippingAddressPojo() {
    }

}