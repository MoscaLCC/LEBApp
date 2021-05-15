package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producer.class)
public abstract class Producer_ {

	public static volatile SingularAttribute<Producer, String> linkSocial;
	public static volatile SingularAttribute<Producer, Double> valueToPay;
	public static volatile SingularAttribute<Producer, UserInfo> userInfo;
	public static volatile SingularAttribute<Producer, Integer> numberRequests;
	public static volatile SingularAttribute<Producer, Double> ranking;
	public static volatile SingularAttribute<Producer, Long> id;
	public static volatile SingularAttribute<Producer, Double> payedValue;
	public static volatile SetAttribute<Producer, Request> requests;

	public static final String LINK_SOCIAL = "linkSocial";
	public static final String VALUE_TO_PAY = "valueToPay";
	public static final String USER_INFO = "userInfo";
	public static final String NUMBER_REQUESTS = "numberRequests";
	public static final String RANKING = "ranking";
	public static final String ID = "id";
	public static final String PAYED_VALUE = "payedValue";
	public static final String REQUESTS = "requests";

}

