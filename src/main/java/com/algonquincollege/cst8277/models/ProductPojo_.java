package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-11-28T01:07:49.152-0500")
@StaticMetamodel(ProductPojo.class)
public class ProductPojo_ extends PojoBase_ {
	public static volatile SingularAttribute<ProductPojo, String> description;
	public static volatile SingularAttribute<ProductPojo, String> serialNo;
	public static volatile SetAttribute<ProductPojo, StorePojo> stores;
}
