package com.leb.app.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, Instant> birthday;
	public static volatile SetAttribute<UserInfo, Request> transportations;
	public static volatile SingularAttribute<UserInfo, String> address;
	public static volatile SingularAttribute<UserInfo, String> nib;
	public static volatile SingularAttribute<UserInfo, Integer> nif;
	public static volatile SingularAttribute<UserInfo, Double> payedValue;
	public static volatile SetAttribute<UserInfo, Request> requests;
	public static volatile SetAttribute<UserInfo, Point> points;
	public static volatile SingularAttribute<UserInfo, String> linkSocial;
	public static volatile SingularAttribute<UserInfo, Double> valueToPay;
	public static volatile SingularAttribute<UserInfo, String> phoneNumber;
	public static volatile SingularAttribute<UserInfo, Integer> numberOfDeliveries;
	public static volatile SingularAttribute<UserInfo, Integer> numberRequests;
	public static volatile SingularAttribute<UserInfo, Double> ranking;
	public static volatile SingularAttribute<UserInfo, Long> id;
	public static volatile SingularAttribute<UserInfo, Double> numberOfKm;

	public static final String BIRTHDAY = "birthday";
	public static final String TRANSPORTATIONS = "transportations";
	public static final String ADDRESS = "address";
	public static final String NIB = "nib";
	public static final String NIF = "nif";
	public static final String PAYED_VALUE = "payedValue";
	public static final String REQUESTS = "requests";
	public static final String POINTS = "points";
	public static final String LINK_SOCIAL = "linkSocial";
	public static final String VALUE_TO_PAY = "valueToPay";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String NUMBER_OF_DELIVERIES = "numberOfDeliveries";
	public static final String NUMBER_REQUESTS = "numberRequests";
	public static final String RANKING = "ranking";
	public static final String ID = "id";
	public static final String NUMBER_OF_KM = "numberOfKm";

}

