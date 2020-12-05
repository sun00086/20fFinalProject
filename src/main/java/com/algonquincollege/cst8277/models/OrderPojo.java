/*****************************************************************c******************o*******v******id********
 * File: OrderPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// TODO: Auto-generated Javadoc
/**
 * Description: model for the Order object.
 */
@Entity(name = "Order")
@Table(name = "ORDER_TBL")
@Access(AccessType.PROPERTY)
@AttributeOverride(name = "id", column = @Column(name="ORDER_ID"))
public class OrderPojo extends PojoBase implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The description. */
    protected String description;
    
    /** The orderlines. */
    protected List<OrderLinePojo> orderlines;
    
    /** The owning customer. */
    protected CustomerPojo owningCustomer;
    
    /**
     * Instantiates a new order pojo.
     */
    // JPA requires each @Entity class have a default constructor
	public OrderPojo() {
	}
	
    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the orderlines.
     *
     * @return the orderlines
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "owningOrder", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToOne(mappedBy = "owningOrder") //professor solution
	public List<OrderLinePojo> getOrderlines() {
		return this.orderlines;
	}
	
	/**
	 * Sets the orderlines.
	 *
	 * @param orderlines the new orderlines
	 */
	public void setOrderlines(List<OrderLinePojo> orderlines) {
		this.orderlines = orderlines;
	}
	
	/**
	 * Adds the orderline.
	 *
	 * @param orderline the orderline
	 * @return the order line pojo
	 */
	public OrderLinePojo addOrderline(OrderLinePojo orderline) {
		getOrderlines().add(orderline);
		orderline.setOwningOrder(this);
		return orderline;
	}
	
	/**
	 * Removes the orderline.
	 *
	 * @param orderline the orderline
	 * @return the order line pojo
	 */
	public OrderLinePojo removeOrderline(OrderLinePojo orderline) {
		getOrderlines().remove(orderline);
        orderline.setOwningOrder(null);
		return orderline;
	}
	
	/**
	 * Gets the owning customer.
	 *
	 * @return the owning customer
	 */
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "OWNING_CUST_ID")
	public CustomerPojo getOwningCustomer() {
		return this.owningCustomer;
	}
	
	/**
	 * Sets the owning customer.
	 *
	 * @param owner the new owning customer
	 */
	public void setOwningCustomer(CustomerPojo owner) {
		this.owningCustomer = owner;
	}

}