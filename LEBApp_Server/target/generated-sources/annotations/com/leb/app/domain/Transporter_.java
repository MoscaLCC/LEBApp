package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transporter.class)
public abstract class Transporter_ {

	public static volatile SingularAttribute<Transporter, UserInfo> userInfo;
	public static volatile SingularAttribute<Transporter, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<Transporter, Double> valueToReceive;
	public static volatile SetAttribute<Transporter, RidePath> ridePaths;
	public static volatile SingularAttribute<Transporter, Double> receivedValue;
	public static volatile SingularAttribute<Transporter, Double> ranking;
	public static volatile SingularAttribute<Transporter, Long> id;
	public static volatile SetAttribute<Transporter, Zone> zones;
	public static volatile SingularAttribute<Transporter, String> favouriteTransport;
	public static volatile SingularAttribute<Transporter, Double> numberOfKm;

	public static final String USER_INFO = "userInfo";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String VALUE_TO_RECEIVE = "valueToReceive";
	public static final String RIDE_PATHS = "ridePaths";
	public static final String RECEIVED_VALUE = "receivedValue";
	public static final String RANKING = "ranking";
	public static final String ID = "id";
	public static final String ZONES = "zones";
	public static final String FAVOURITE_TRANSPORT = "favouriteTransport";
	public static final String NUMBER_OF_KM = "numberOfKm";

}

