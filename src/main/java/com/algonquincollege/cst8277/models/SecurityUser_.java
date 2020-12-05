package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityUser_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.921-0500")
@StaticMetamodel(SecurityUser.class)
public class SecurityUser_ {
	
	/** The id. */
	public static volatile SingularAttribute<SecurityUser, Integer> id;
	
	/** The username. */
	public static volatile SingularAttribute<SecurityUser, String> username;
	
	/** The pw hash. */
	public static volatile SingularAttribute<SecurityUser, String> pwHash;
	
	/** The roles. */
	public static volatile SetAttribute<SecurityUser, SecurityRole> roles;
	
	/** The customer. */
	public static volatile SingularAttribute<SecurityUser, CustomerPojo> customer;
}
