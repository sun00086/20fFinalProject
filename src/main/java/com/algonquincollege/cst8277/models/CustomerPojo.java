/*****************************************************************c******************o*******v******id********
 * File: CustomerPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 * 
 * 
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
//import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;
import static com.algonquincollege.cst8277.models.CustomerPojo.FIND_CUSTOMERS_QUERY_ID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

// TODO: Auto-generated Javadoc
/**
 * Description: model for the Customer object.
 */
@Entity(name = "Customer")
@Table(name = "CUSTOMER")
@Access(AccessType.PROPERTY)
@AttributeOverride(name = "id", column = @Column(name="ID"))
//changed
@NamedQueries({
    @NamedQuery(name=ALL_CUSTOMERS_QUERY_NAME,query="select c from Customer c"),
    @NamedQuery(name=FIND_CUSTOMERS_QUERY_ID, query= "select c from Customer c where c.id = :param1")
})
public class CustomerPojo extends PojoBase implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant ALL_CUSTOMERS_QUERY_NAME. */
    public static final String ALL_CUSTOMERS_QUERY_NAME = "allCustomers";
    
    /** The Constant FIND_CUSTOMERS_QUERY_ID. */
    public static final String FIND_CUSTOMERS_QUERY_ID = "findCustomer";

    /** The first name. */
    protected String firstName;
    
    /** The last name. */
    protected String lastName;
    
    /** The email. */
    protected String email;
    
    /** The phone number. */
    protected String phoneNumber;
    
    /** The shipping address. */
    protected AddressPojo shippingAddress;
    
    /** The billing address. */
    protected AddressPojo billingAddress;
    
    /** The orders. */
    protected List<OrderPojo> orders;
//  TODO  protected List<OrderPojo> orders = new ArrayList<>();
    
//    protected List<OrderPojo> orders = new ArrayList<>();
    /**
 * Instantiates a new customer pojo.
 */
// JPA requires each @Entity class have a default constructor
	public CustomerPojo() {
	}
	
    /**
     * Gets the first name.
     *
     * @return the value for firstName
     */
    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Sets the first name.
     *
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the value for lastName
     */
    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Sets the last name.
     *
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Gets the email.
     *
     * @return the email
     */
    @Column(name ="EMAIL")
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    @Column(name="PHONENUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * Sets the phone number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //dont use CascadeType.All (skipping CascadeType.REMOVE): what if two customers
    /**
     * Gets the shipping address.
     *
     * @return the shipping address
     */
    //live at the same address and 1 leaves the house but the other does not?
    @OneToOne
    @JoinColumn(name="SHIPPING_ADDR")
    public AddressPojo getShippingAddress() {
        return shippingAddress;
    }
    
    /**
     * Sets the shipping address.
     *
     * @param shippingAddress the new shipping address
     */
    public void setShippingAddress(AddressPojo shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    /**
     * Gets the billing address.
     *
     * @return the billing address
     */
    @OneToOne
    @JoinColumn(name="BILLING_ADDR")
    public AddressPojo getBillingAddress() {
        return billingAddress;
    }
    
    /**
     * Sets the billing address.
     *
     * @param billingAddress the new billing address
     */
    public void setBillingAddress(AddressPojo billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    /**
     * Gets the orders.
     *
     * @return the orders
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "owningCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderPojo> getOrders() {
        return orders;
    }

    /**
     * Sets the orders.
     *
     * @param orders the new orders
     */
    public void setOrders(List<OrderPojo> orders) {
        this.orders = orders;
    }
    
    /**
     * Adds the orders.
     *
     * @param order the order
     */
    public void addOrders(OrderPojo order) {
        getOrders().add(order);
        order.setOwningCustomer(this);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Customer [id=")
            .append(id)
            .append(", ");
        if (firstName != null) {
            builder
                .append("firstName=")
                .append(firstName)
                .append(", ");
        }
        if (lastName != null) {
            builder
                .append("lastName=")
                .append(lastName)
                .append(", ");
        }
        if (phoneNumber != null) {
            builder
                .append("phoneNumber=")
                .append(phoneNumber)
                .append(", ");
        }
        if (email != null) {
            builder
                .append("email=")
                .append(email)
                .append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

}