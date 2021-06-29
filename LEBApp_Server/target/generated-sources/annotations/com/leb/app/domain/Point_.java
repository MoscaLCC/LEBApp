package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Point.class)
public abstract class Point_ {

	public static volatile SingularAttribute<Point, UserInfo> userInfo;
	public static volatile SetAttribute<Point, DeliveryMan> deliveryMen;
	public static volatile SingularAttribute<Point, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<Point, Double> valueToReceive;
	public static volatile SingularAttribute<Point, Zone> zone;
	public static volatile SingularAttribute<Point, String> openingTime;
	public static volatile SingularAttribute<Point, Double> receivedValue;
	public static volatile SingularAttribute<Point, Double> ranking;
	public static volatile SingularAttribute<Point, Long> id;

	public static final String USER_INFO = "userInfo";
	public static final String DELIVERY_MEN = "deliveryMen";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String VALUE_TO_RECEIVE = "valueToReceive";
	public static final String ZONE = "zone";
	public static final String OPENING_TIME = "openingTime";
	public static final String RECEIVED_VALUE = "receivedValue";
	public static final String RANKING = "ranking";
	public static final String ID = "id";

}

