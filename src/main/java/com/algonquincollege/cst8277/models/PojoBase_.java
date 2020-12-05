package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class PojoBase_.
 */
@Generated(value="Dali", date="2020-12-01T12:48:45.842-0500")
@StaticMetamodel(PojoBase.class)
public class PojoBase_ {
	
	/** The id. */
	public static volatile SingularAttribute<PojoBase, Integer> id;
	
	/** The created date. */
	public static volatile SingularAttribute<PojoBase, LocalDateTime> createdDate;
	
	/** The updated date. */
	public static volatile SingularAttribute<PojoBase, LocalDateTime> updatedDate;
	
	/** The version. */
	public static volatile SingularAttribute<PojoBase, Integer> version;
}
