package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Point.class)
public abstract class Point_ {

	public static volatile SingularAttribute<Point, String> address;
	public static volatile SingularAttribute<Point, String> closingTime;
	public static volatile SingularAttribute<Point, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<Point, String> openingTime;
	public static volatile SingularAttribute<Point, Long> id;
	public static volatile SingularAttribute<Point, UserInfo> ownerPoint;

	public static final String ADDRESS = "address";
	public static final String CLOSING_TIME = "closingTime";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String OPENING_TIME = "openingTime";
	public static final String ID = "id";
	public static final String OWNER_POINT = "ownerPoint";

}

