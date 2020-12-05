package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerPojo_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.761-0500")
@StaticMetamodel(CustomerPojo.class)
public class CustomerPojo_ extends PojoBase_ {
	
	/** The first name. */
	public static volatile SingularAttribute<CustomerPojo, String> firstName;
	
	/** The last name. */
	public static volatile SingularAttribute<CustomerPojo, String> lastName;
	
	/** The email. */
	public static volatile SingularAttribute<CustomerPojo, String> email;
	
	/** The phone number. */
	public static volatile SingularAttribute<CustomerPojo, String> phoneNumber;
	
	/** The shipping address. */
	public static volatile SingularAttribute<CustomerPojo, AddressPojo> shippingAddress;
	
	/** The billing address. */
	public static volatile SingularAttribute<CustomerPojo, AddressPojo> billingAddress;
	
	/** The orders. */
	public static volatile ListAttribute<CustomerPojo, OrderPojo> orders;
}
