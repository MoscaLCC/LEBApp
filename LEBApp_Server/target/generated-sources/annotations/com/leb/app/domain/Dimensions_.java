package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dimensions.class)
public abstract class Dimensions_ {

	public static volatile SingularAttribute<Dimensions, Request> request;
	public static volatile SingularAttribute<Dimensions, Double> depth;
	public static volatile SingularAttribute<Dimensions, Double> width;
	public static volatile SingularAttribute<Dimensions, Long> id;
	public static volatile SingularAttribute<Dimensions, Double> height;

	public static final String REQUEST = "request";
	public static final String DEPTH = "depth";
	public static final String WIDTH = "width";
	public static final String ID = "id";
	public static final String HEIGHT = "height";

}

