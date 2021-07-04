package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeliveryMan.class)
public abstract class DeliveryMan_ {

	public static volatile SingularAttribute<DeliveryMan, UserInfo> userInfo;
	public static volatile SingularAttribute<DeliveryMan, String> closingTime;
	public static volatile SingularAttribute<DeliveryMan, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<DeliveryMan, Double> valueToReceive;
	public static volatile SingularAttribute<DeliveryMan, String> openingTime;
	public static volatile SingularAttribute<DeliveryMan, Double> receivedValue;
	public static volatile SingularAttribute<DeliveryMan, Double> ranking;
	public static volatile SingularAttribute<DeliveryMan, Long> id;
	public static volatile SingularAttribute<DeliveryMan, Point> point;
	public static volatile SingularAttribute<DeliveryMan, Double> numberOfKm;

	public static final String USER_INFO = "userInfo";
	public static final String CLOSING_TIME = "closingTime";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String VALUE_TO_RECEIVE = "valueToReceive";
	public static final String OPENING_TIME = "openingTime";
	public static final String RECEIVED_VALUE = "receivedValue";
	public static final String RANKING = "ranking";
	public static final String ID = "id";
	public static final String POINT = "point";
	public static final String NUMBER_OF_KM = "numberOfKm";

}

