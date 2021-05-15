package com.leb.app.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, LocalDate> birthday;
	public static volatile SingularAttribute<UserInfo, String> phoneNumber;
	public static volatile SingularAttribute<UserInfo, String> nib;
	public static volatile SingularAttribute<UserInfo, Integer> nif;
	public static volatile SingularAttribute<UserInfo, String> adress;
	public static volatile SingularAttribute<UserInfo, Long> id;
	public static volatile SingularAttribute<UserInfo, User> user;

	public static final String BIRTHDAY = "birthday";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String NIB = "nib";
	public static final String NIF = "nif";
	public static final String ADRESS = "adress";
	public static final String ID = "id";
	public static final String USER = "user";

}

