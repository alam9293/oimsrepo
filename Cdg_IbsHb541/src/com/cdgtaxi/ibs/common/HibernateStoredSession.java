package com.cdgtaxi.ibs.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateStoredSession {

	public static final List<Session>SessionList;
	public static final List<Session>BackupSessionList;
	public static final Log logger = LogFactory.getLog(HibernateStoredSession.class);
	static
	{
		logger.info("SessionList intialize :");
		SessionList = new ArrayList<Session>();
		BackupSessionList = new ArrayList<Session>();
	}
	
	public static void addSessionintoList(Session session) {
		
		SessionList.add(session);
		logger.info("addSessionintoList SessionList.size() :"+SessionList.size());
	}
	
	public static void closeinActiveSession() {
		try
		{
			logger.info("closeinActiveSession SessionList.size() :"+SessionList.size());
			logger.info("closeinActiveSession SessionList.size() :"+BackupSessionList.size());
			for(Session session : SessionList)
			{
				logger.info("Session Status:"+session.getTransaction().getStatus().name());
				
				if(!session.getTransaction().getStatus().name().equalsIgnoreCase("ACTIVE"))
				{
					
					//logger.info("Going to close Session :"+((SessionHolder)entry.getValue()).getSession().getTransaction().getStatus().name());
					//session.beginTransaction().commit();
				}
				else
				{
					if(SessionList.size()!=1)
					{	
						logger.info("session.isConnected():"+session.isConnected());
						logger.info("session.isJoinedToTransaction():"+session.isJoinedToTransaction());
						logger.info("session.isOpen():"+session.isOpen());
						logger.info("session.isDefaultReadOnly():"+session.isDefaultReadOnly());
						logger.info("session.getTransaction().isActive():"+session.getTransaction().isActive());
						session.getTransaction().commit();
					}
					BackupSessionList.add(session);
				}
				
			}
			SessionList.clear();
			SessionList.addAll(BackupSessionList);
		}
		catch(Exception e)
		{
			logger.info(e);
		}
		
	}

	
	
}
