package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class StorePojo_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.982-0500")
@StaticMetamodel(StorePojo.class)
public class StorePojo_ extends PojoBase_ {
	
	/** The store name. */
	public static volatile SingularAttribute<StorePojo, String> storeName;
	
	/** The products. */
	public static volatile SetAttribute<StorePojo, ProductPojo> products;
}
