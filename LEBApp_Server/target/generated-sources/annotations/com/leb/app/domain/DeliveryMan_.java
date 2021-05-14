package com.leb.app.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeliveryMan.class)
public abstract class DeliveryMan_ {

	public static volatile SingularAttribute<DeliveryMan, LocalDate> birthday;
	public static volatile SingularAttribute<DeliveryMan, String> address;
	public static volatile SingularAttribute<DeliveryMan, String> nib;
	public static volatile SingularAttribute<DeliveryMan, String> openingTime;
	public static volatile SingularAttribute<DeliveryMan, String> photo;
	public static volatile SingularAttribute<DeliveryMan, Double> receivedValue;
	public static volatile SingularAttribute<DeliveryMan, Integer> nif;
	public static volatile SingularAttribute<DeliveryMan, Point> point;
	public static volatile SingularAttribute<DeliveryMan, String> phoneNumber;
	public static volatile SingularAttribute<DeliveryMan, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<DeliveryMan, Double> valueToReceive;
	public static volatile SingularAttribute<DeliveryMan, String> name;
	public static volatile SingularAttribute<DeliveryMan, Double> ranking;
	public static volatile SingularAttribute<DeliveryMan, Long> id;
	public static volatile SingularAttribute<DeliveryMan, String> email;
	public static volatile SingularAttribute<DeliveryMan, Double> numberOfKm;

	public static final String BIRTHDAY = "birthday";
	public static final String ADDRESS = "address";
	public static final String NIB = "nib";
	public static final String OPENING_TIME = "openingTime";
	public static final String PHOTO = "photo";
	public static final String RECEIVED_VALUE = "receivedValue";
	public static final String NIF = "nif";
	public static final String POINT = "point";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String VALUE_TO_RECEIVE = "valueToReceive";
	public static final String NAME = "name";
	public static final String RANKING = "ranking";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String NUMBER_OF_KM = "numberOfKm";

}

