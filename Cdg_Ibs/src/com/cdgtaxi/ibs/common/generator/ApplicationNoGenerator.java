package com.cdgtaxi.ibs.common.generator;

import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import com.cdgtaxi.ibs.billing.ui.ApplyExcessAmountPaymentReceiptWindow;
import com.cdgtaxi.ibs.util.StringUtil;

public class ApplicationNoGenerator extends SequenceGenerator{
	
	/*private static final Logger logger = Logger.getLogger(ApplicationNoGenerator.class);
	@Override
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		logger.info("Going to configure String sequence....");
		super.configure(Hibernate.STRING, params, dialect);
	}
	
	@Override
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		logger.info("Going to generate String sequence....");
		Serializable id = super.generate(session, obj);
		return StringUtil.appendLeft(id.toString(), 8, "0");
	}*/
}
