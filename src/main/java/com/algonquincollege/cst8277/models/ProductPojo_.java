package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductPojo_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.864-0500")
@StaticMetamodel(ProductPojo.class)
public class ProductPojo_ extends PojoBase_ {
	
	/** The description. */
	public static volatile SingularAttribute<ProductPojo, String> description;
	
	/** The serial no. */
	public static volatile SingularAttribute<ProductPojo, String> serialNo;
	
	/** The stores. */
	public static volatile SetAttribute<ProductPojo, StorePojo> stores;
}
