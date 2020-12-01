/*****************************************************************c******************o*******v******id********
 * File: CustomerPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
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

/**
*
* Description: model for the Customer object
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
    private static final long serialVersionUID = 1L;

    public static final String ALL_CUSTOMERS_QUERY_NAME = "allCustomers";
    public static final String FIND_CUSTOMERS_QUERY_ID = "findCustomer";

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected AddressPojo shippingAddress;
    protected AddressPojo billingAddress;
    protected List<OrderPojo> orders;
//  TODO  protected List<OrderPojo> orders = new ArrayList<>();
    
//    protected List<OrderPojo> orders = new ArrayList<>();
    // JPA requires each @Entity class have a default constructor
	public CustomerPojo() {
	}
	
    /**
     * @return the value for firstName
     */
    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */
    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name ="EMAIL")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="PHONENUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //dont use CascadeType.All (skipping CascadeType.REMOVE): what if two customers
    //live at the same address and 1 leaves the house but the other does not?
    @OneToOne
    @JoinColumn(name="SHIPPING_ADDR")
    public AddressPojo getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(AddressPojo shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    @OneToOne
    @JoinColumn(name="BILLING_ADDR")
    public AddressPojo getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(AddressPojo billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    @JsonManagedReference
    @OneToMany(mappedBy = "owningCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderPojo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderPojo> orders) {
        this.orders = orders;
    }
    
    public void addOrders(OrderPojo order) {
        getOrders().add(order);
        order.setOwningCustomer(this);
    }

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