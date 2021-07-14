package com.leb.app.domain;

import com.leb.app.domain.enumeration.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Request.class)
public abstract class Request_ {

	public static volatile SingularAttribute<Request, Point> destinationPoint;
	public static volatile SingularAttribute<Request, String> deliveryTime;
	public static volatile SingularAttribute<Request, Point> originalPoint;
	public static volatile SingularAttribute<Request, String> initDate;
	public static volatile SingularAttribute<Request, String> destination;
	public static volatile SingularAttribute<Request, String> estimatedDate;
	public static volatile SingularAttribute<Request, Double> rating;
	public static volatile SingularAttribute<Request, String> description;
	public static volatile SingularAttribute<Request, String> source;
	public static volatile SingularAttribute<Request, Double> productWeight;
	public static volatile SingularAttribute<Request, String> productName;
	public static volatile SingularAttribute<Request, Double> shippingCosts;
	public static volatile SingularAttribute<Request, DeliveryMan> collector;
	public static volatile SingularAttribute<Request, DeliveryMan> destributor;
	public static volatile SingularAttribute<Request, Double> productValue;
	public static volatile SingularAttribute<Request, RidePath> ridePath;
	public static volatile SingularAttribute<Request, String> destinationContact;
	public static volatile SingularAttribute<Request, Transporter> transporter;
	public static volatile SingularAttribute<Request, Producer> producer;
	public static volatile SingularAttribute<Request, String> specialCharacteristics;
	public static volatile SingularAttribute<Request, Long> id;
	public static volatile SingularAttribute<Request, String> expirationDate;
	public static volatile SingularAttribute<Request, Status> status;
	public static volatile SingularAttribute<Request, Dimensions> dimensions;

	public static final String DESTINATION_POINT = "destinationPoint";
	public static final String DELIVERY_TIME = "deliveryTime";
	public static final String ORIGINAL_POINT = "originalPoint";
	public static final String INIT_DATE = "initDate";
	public static final String DESTINATION = "destination";
	public static final String ESTIMATED_DATE = "estimatedDate";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String SOURCE = "source";
	public static final String PRODUCT_WEIGHT = "productWeight";
	public static final String PRODUCT_NAME = "productName";
	public static final String SHIPPING_COSTS = "shippingCosts";
	public static final String COLLECTOR = "collector";
	public static final String DESTRIBUTOR = "destributor";
	public static final String PRODUCT_VALUE = "productValue";
	public static final String RIDE_PATH = "ridePath";
	public static final String DESTINATION_CONTACT = "destinationContact";
	public static final String TRANSPORTER = "transporter";
	public static final String PRODUCER = "producer";
	public static final String SPECIAL_CHARACTERISTICS = "specialCharacteristics";
	public static final String ID = "id";
	public static final String EXPIRATION_DATE = "expirationDate";
	public static final String STATUS = "status";
	public static final String DIMENSIONS = "dimensions";

}

