package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderPojo_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.818-0500")
@StaticMetamodel(OrderPojo.class)
public class OrderPojo_ extends PojoBase_ {
	
	/** The description. */
	public static volatile SingularAttribute<OrderPojo, String> description;
	
	/** The orderlines. */
	public static volatile ListAttribute<OrderPojo, OrderLinePojo> orderlines;
	
	/** The owning customer. */
	public static volatile SingularAttribute<OrderPojo, CustomerPojo> owningCustomer;
}
