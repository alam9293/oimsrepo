package com.cdgtaxi.ibs.common.generator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

public class BigDecimalSequenceGenerator extends SequenceGenerator{
	
	/*private static final Logger logger = Logger.getLogger(BigDecimalSequenceGenerator.class);
	@Override
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		logger.info("Going to configure BigDecimal sequence....");
		super.configure(Hibernate.STRING, params, dialect);
	}
	
	@Override
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		logger.info("Going to generate sequence....");
		return new BigDecimal((String)super.generate(session, obj));
	}*/
}
