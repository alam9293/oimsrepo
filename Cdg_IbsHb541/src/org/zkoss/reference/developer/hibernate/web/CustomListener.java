package org.zkoss.reference.developer.hibernate.web;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.RequestInterceptor;

public class CustomListener extends HibernateDaoSupport implements RequestInterceptor {
	
	CustomListener()
	{
		
	}

	@Override
	public void request(Session paramSession, Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		TransactionStatus status = this.getSessionFactory().getCurrentSession().getTransaction().getStatus(); 
		logger.info("status :"+status);
		if(status.isOneOf(TransactionStatus.NOT_ACTIVE))
		{
			this.getSessionFactory().getCurrentSession().beginTransaction();
		}
	}

}
