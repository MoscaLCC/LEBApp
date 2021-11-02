package com.leb.app.domain;

import com.leb.app.domain.enumeration.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Request.class)
public abstract class Request_ {

	public static volatile SingularAttribute<Request, String> deliveryTime;
	public static volatile SingularAttribute<Request, String> initDate;
	public static volatile SingularAttribute<Request, String> destination;
	public static volatile SingularAttribute<Request, String> estimatedDate;
	public static volatile SingularAttribute<Request, Double> rating;
	public static volatile SingularAttribute<Request, String> description;
	public static volatile SingularAttribute<Request, Double> weight;
	public static volatile SingularAttribute<Request, UserInfo> tranporter;
	public static volatile SingularAttribute<Request, String> source;
	public static volatile SingularAttribute<Request, UserInfo> ownerRequest;
	public static volatile SingularAttribute<Request, String> productName;
	public static volatile SingularAttribute<Request, Double> shippingCosts;
	public static volatile SingularAttribute<Request, Double> productValue;
	public static volatile SingularAttribute<Request, String> destinationContact;
	public static volatile SingularAttribute<Request, Double> hight;
	public static volatile SingularAttribute<Request, Double> width;
	public static volatile SingularAttribute<Request, String> specialCharacteristics;
	public static volatile SingularAttribute<Request, Long> id;
	public static volatile SingularAttribute<Request, String> expirationDate;
	public static volatile SingularAttribute<Request, Status> status;

	public static final String DELIVERY_TIME = "deliveryTime";
	public static final String INIT_DATE = "initDate";
	public static final String DESTINATION = "destination";
	public static final String ESTIMATED_DATE = "estimatedDate";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String WEIGHT = "weight";
	public static final String TRANPORTER = "tranporter";
	public static final String SOURCE = "source";
	public static final String OWNER_REQUEST = "ownerRequest";
	public static final String PRODUCT_NAME = "productName";
	public static final String SHIPPING_COSTS = "shippingCosts";
	public static final String PRODUCT_VALUE = "productValue";
	public static final String DESTINATION_CONTACT = "destinationContact";
	public static final String HIGHT = "hight";
	public static final String WIDTH = "width";
	public static final String SPECIAL_CHARACTERISTICS = "specialCharacteristics";
	public static final String ID = "id";
	public static final String EXPIRATION_DATE = "expirationDate";
	public static final String STATUS = "status";

}

