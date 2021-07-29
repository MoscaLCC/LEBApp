package com.leb.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Zone.class)
public abstract class Zone_ {

	public static volatile SetAttribute<Zone, Transporter> transporters;
	public static volatile SingularAttribute<Zone, String> name;
	public static volatile SingularAttribute<Zone, Long> id;
	public static volatile SetAttribute<Zone, Point> points;

	public static final String TRANSPORTERS = "transporters";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String POINTS = "points";

}

