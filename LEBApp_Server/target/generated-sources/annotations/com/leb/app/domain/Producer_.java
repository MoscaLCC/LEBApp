package com.leb.app.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producer.class)
public abstract class Producer_ {

	public static volatile SingularAttribute<Producer, LocalDate> birthday;
	public static volatile SingularAttribute<Producer, String> mail;
	public static volatile SingularAttribute<Producer, String> nib;
	public static volatile SingularAttribute<Producer, String> photo;
	public static volatile SingularAttribute<Producer, Integer> nif;
	public static volatile SingularAttribute<Producer, String> adress;
	public static volatile SingularAttribute<Producer, Double> payedValue;
	public static volatile SetAttribute<Producer, Request> requests;
	public static volatile SingularAttribute<Producer, String> linkSocial;
	public static volatile SingularAttribute<Producer, Double> valueToPay;
	public static volatile SingularAttribute<Producer, String> phoneNumber;
	public static volatile SingularAttribute<Producer, Integer> numberRequests;
	public static volatile SingularAttribute<Producer, String> name;
	public static volatile SingularAttribute<Producer, Double> ranking;
	public static volatile SingularAttribute<Producer, Long> id;

	public static final String BIRTHDAY = "birthday";
	public static final String MAIL = "mail";
	public static final String NIB = "nib";
	public static final String PHOTO = "photo";
	public static final String NIF = "nif";
	public static final String ADRESS = "adress";
	public static final String PAYED_VALUE = "payedValue";
	public static final String REQUESTS = "requests";
	public static final String LINK_SOCIAL = "linkSocial";
	public static final String VALUE_TO_PAY = "valueToPay";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String NUMBER_REQUESTS = "numberRequests";
	public static final String NAME = "name";
	public static final String RANKING = "ranking";
	public static final String ID = "id";

}

