/*****************************************************************c******************o*******v******id********
 * File: OrderPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// TODO: Auto-generated Javadoc
/**
 * Description: model for the Product object.
 */

@Entity(name = "Product")
@Table(name = "PRODUCT")
@Access(AccessType.PROPERTY)
@AttributeOverride(name = "id", column = @Column(name="PRODUCT_ID"))
public class ProductPojo extends PojoBase implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The description. */
    protected String description;
    
    /** The serial no. */
    protected String serialNo;
    
    /** The stores. */
    protected Set<StorePojo> stores = new HashSet<>();

    /**
     * Instantiates a new product pojo.
     */
    // JPA requires each @Entity class have a default constructor
    public ProductPojo() {
    }
    
    /**
     * Gets the description.
     *
     * @return the value for firstName
     */
    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description.
     *
     * @param description new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the serial no.
     *
     * @return the serial no
     */
    @Column(name="SERIALNUMBER")
    public String getSerialNo() {
        return serialNo;
    }
    
    /**
     * Sets the serial no.
     *
     * @param serialNo the new serial no
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    
    /**
     * Gets the stores.
     *
     * @return the stores
     */
    @JsonInclude(Include.NON_NULL)
    @ManyToMany
    @JoinTable(name="STORES_PRODUCTS",
      joinColumns=@JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID"),
      inverseJoinColumns=@JoinColumn(name="STORE_ID", referencedColumnName="STORE_ID"))
    public Set<StorePojo> getStores() {
        return stores;
    }
    
    /**
     * Sets the stores.
     *
     * @param stores the new stores
     */
    public void setStores(Set<StorePojo> stores) {
        this.stores = stores;
    }

}