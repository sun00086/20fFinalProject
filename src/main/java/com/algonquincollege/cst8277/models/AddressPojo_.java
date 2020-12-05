package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressPojo_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.660-0500")
@StaticMetamodel(AddressPojo.class)
public class AddressPojo_ extends PojoBase_ {
	
	/** The city. */
	public static volatile SingularAttribute<AddressPojo, String> city;
	
	/** The country. */
	public static volatile SingularAttribute<AddressPojo, String> country;
	
	/** The postal. */
	public static volatile SingularAttribute<AddressPojo, String> postal;
	
	/** The state. */
	public static volatile SingularAttribute<AddressPojo, String> state;
	
	/** The street. */
	public static volatile SingularAttribute<AddressPojo, String> street;
}
