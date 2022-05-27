package com.cdgtaxi.ibs.common.generator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

public class BigDecimalSequenceGenerator extends SequenceStyleGenerator{
	
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		super.configure(StandardBasicTypes.STRING, params, serviceRegistry);
	}
	
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		return new BigDecimal((String)super.generate(session, obj));
	}
}