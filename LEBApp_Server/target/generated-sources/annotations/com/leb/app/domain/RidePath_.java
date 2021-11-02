package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RidePath.class)
public abstract class RidePath_ {

	public static volatile SingularAttribute<RidePath, String> estimatedTime;
	public static volatile SingularAttribute<RidePath, String> distance;
	public static volatile SingularAttribute<RidePath, String> destination;
	public static volatile SingularAttribute<RidePath, Long> id;
	public static volatile SingularAttribute<RidePath, String> source;
	public static volatile SingularAttribute<RidePath, Double> radius;

	public static final String ESTIMATED_TIME = "estimatedTime";
	public static final String DISTANCE = "distance";
	public static final String DESTINATION = "destination";
	public static final String ID = "id";
	public static final String SOURCE = "source";
	public static final String RADIUS = "radius";

}

