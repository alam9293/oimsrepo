package com.cdgtaxi.ibs.common.generator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.cdgtaxi.ibs.util.StringUtil;

public class ApplicationNoGenerator extends SequenceStyleGenerator{

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		super.configure(StandardBasicTypes.STRING, params, serviceRegistry);
		
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Serializable id = generate(session, object);
		return StringUtil.appendLeft(id.toString(), 8, "0");
	}

}