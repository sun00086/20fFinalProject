package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityRole_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.899-0500")
@StaticMetamodel(SecurityRole.class)
public class SecurityRole_ {
	
	/** The id. */
	public static volatile SingularAttribute<SecurityRole, Integer> id;
	
	/** The role name. */
	public static volatile SingularAttribute<SecurityRole, String> roleName;
	
	/** The users. */
	public static volatile SetAttribute<SecurityRole, SecurityUser> users;
}
