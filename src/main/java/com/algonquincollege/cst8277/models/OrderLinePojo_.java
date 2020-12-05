package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderLinePojo_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.801-0500")
@StaticMetamodel(OrderLinePojo.class)
public class OrderLinePojo_ {
	
	/** The pk. */
	public static volatile SingularAttribute<OrderLinePojo, OrderLinePk> pk;
	
	/** The owning order. */
	public static volatile SingularAttribute<OrderLinePojo, OrderPojo> owningOrder;
	
	/** The amount. */
	public static volatile SingularAttribute<OrderLinePojo, Double> amount;
	
	/** The product. */
	public static volatile SingularAttribute<OrderLinePojo, ProductPojo> product;
}
