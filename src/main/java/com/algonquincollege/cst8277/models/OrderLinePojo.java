/*****************************************************************c******************o*******v******id********
 * File: OrderLinePojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

// TODO: Auto-generated Javadoc
/**
 * Description: model for the OrderLine object.
 */
@Entity(name = "Orderline")
@Table(name = "ORDERLINE")
@Access(AccessType.PROPERTY)
public class OrderLinePojo implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The primary key. */
    protected OrderLinePk primaryKey;
    
    /** The owning order. */
    protected OrderPojo owningOrder;
    
    /** The amount. */
    protected Double amount;
    
    /** The product. */
    protected ProductPojo product;

    /**
     * Instantiates a new order line pojo.
     */
    // JPA requires each @Entity class have a default constructor
    public OrderLinePojo() {
    }
    
    /**
     * Gets the pk.
     *
     * @return the pk
     */
    @EmbeddedId
    public OrderLinePk getPk() {
        return primaryKey;
    }
    
    /**
     * Sets the pk.
     *
     * @param primaryKey the new pk
     */
    public void setPk(OrderLinePk primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    /**
     * Gets the owning order.
     *
     * @return the owning order
     */
    @JsonBackReference
    @MapsId("owningOrderId")
    @ManyToOne
//    @JoinColumn(name="OWNING_ORDER_ID")
    @JoinColumn(name="OWNING_ORDER_ID", referencedColumnName="ORDER_ID")
    public OrderPojo getOwningOrder() {
        return owningOrder;
    }
    
    /**
     * Sets the owning order.
     *
     * @param owningOrder the new owning order
     */
    public void setOwningOrder(OrderPojo owningOrder) {
        this.owningOrder = owningOrder;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }
    
    /**
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    /**
     * Gets the product.
     *
     * @return the product
     */
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name="PRODUCT_ID")
    public ProductPojo getProduct() {
        return product;
    }
    
    /**
     * Sets the product.
     *
     * @param product the new product
     */
    public void setProduct(ProductPojo product) {
        this.product = product;
    }

    
    //TODO missing price
}