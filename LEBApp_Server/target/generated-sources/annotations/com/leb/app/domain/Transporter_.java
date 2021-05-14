package com.leb.app.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transporter.class)
public abstract class Transporter_ {

	public static volatile SingularAttribute<Transporter, LocalDate> birthday;
	public static volatile SingularAttribute<Transporter, String> address;
	public static volatile SetAttribute<Transporter, RidePath> ridePaths;
	public static volatile SingularAttribute<Transporter, String> nib;
	public static volatile SingularAttribute<Transporter, String> photo;
	public static volatile SingularAttribute<Transporter, Double> receivedValue;
	public static volatile SingularAttribute<Transporter, Integer> nif;
	public static volatile SetAttribute<Transporter, Zone> zones;
	public static volatile SingularAttribute<Transporter, String> phoneNumber;
	public static volatile SingularAttribute<Transporter, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<Transporter, Double> valueToReceive;
	public static volatile SingularAttribute<Transporter, String> name;
	public static volatile SingularAttribute<Transporter, Double> ranking;
	public static volatile SingularAttribute<Transporter, Long> id;
	public static volatile SingularAttribute<Transporter, String> email;
	public static volatile SingularAttribute<Transporter, String> favouriteTransport;
	public static volatile SingularAttribute<Transporter, Double> numberOfKm;

	public static final String BIRTHDAY = "birthday";
	public static final String ADDRESS = "address";
	public static final String RIDE_PATHS = "ridePaths";
	public static final String NIB = "nib";
	public static final String PHOTO = "photo";
	public static final String RECEIVED_VALUE = "receivedValue";
	public static final String NIF = "nif";
	public static final String ZONES = "zones";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String VALUE_TO_RECEIVE = "valueToReceive";
	public static final String NAME = "name";
	public static final String RANKING = "ranking";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String FAVOURITE_TRANSPORT = "favouriteTransport";
	public static final String NUMBER_OF_KM = "numberOfKm";

}

