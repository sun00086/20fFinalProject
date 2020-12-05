/*****************************************************************c******************o*******v******id********
 * File: StorePojo.java
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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.algonquincollege.cst8277.rest.ProductSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

// TODO: Auto-generated Javadoc
/**
 * Description: model for the Store object.
 */
@Entity(name = "Stores")
@Table(name = "STORES")
@Access(AccessType.PROPERTY)
@AttributeOverride(name = "id", column = @Column(name="STORE_ID"))
public class StorePojo extends PojoBase implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The store name. */
    protected String storeName;
    
    /** The products. */
    protected Set<ProductPojo> products = new HashSet<>();

    /**
     * Instantiates a new store pojo.
     */
    // JPA requires each @Entity class have a default constructor
    public StorePojo() {
    }

    /**
     * Gets the store name.
     *
     * @return the store name
     */
    public String getStoreName() {
        return storeName;
    }
    
    /**
     * Sets the store name.
     *
     * @param storeName the new store name
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    /**
     * Gets the products.
     *
     * @return the products
     */
    @JsonSerialize(using = ProductSerializer.class)
      //Discovered what I think is a bug: you should be able to list them in any order,
      //but it turns out, EclipseLink's JPA implementation needs the @JoinColumn StorePojo's PK
      //first, the 'inverse' to ProductPojo's PK second
    @ManyToMany(mappedBy = "stores", cascade = CascadeType.ALL)
    public Set<ProductPojo> getProducts() {
        return products;
    }
    
    /**
     * Sets the products.
     *
     * @param products the new products
     */
    public void setProducts(Set<ProductPojo> products) {
        this.products = products;
    }

}
