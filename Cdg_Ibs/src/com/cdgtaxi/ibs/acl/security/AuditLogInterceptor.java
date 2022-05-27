package com.cdgtaxi.ibs.acl.security;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.cdgtaxi.ibs.acl.model.SatbAuditLog;

/**
 * Hibernate Interceptor for logging saves, updates and deletes to the
 * AclAuditLog Table
 */
public class AuditLogInterceptor extends EmptyInterceptor implements Interceptor {
    private static final long serialVersionUID = 1L;
    private Log log = LogFactory.getLog(AuditLogInterceptor.class);
    private SessionFactory sessionFactory;
    private static final String UPDATE = "U";
    private static final String INSERT = "I";
    private static final String DELETE = "D";

    /**
     * @param sessionFactory
     *            The sessionFactory to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Set<SatbAuditLog> inserts = Collections.synchronizedSet(new HashSet<SatbAuditLog>());
    private Set<SatbAuditLog> updates = Collections.synchronizedSet(new HashSet<SatbAuditLog>());
    private Set<SatbAuditLog> deletes = Collections.synchronizedSet(new HashSet<SatbAuditLog>());
    
    /*
     * (non-Javadoc)
     *
     * @see net.sf.hibernate.Interceptor#onFlushDirty(java.lang.Object,
     *      java.io.Serializable, java.lang.Object[], java.lang.Object[],
     *      java.lang.String[], net.sf.hibernate.type.Type[])
     */
    public boolean onFlushDirty(Object obj, Serializable id, Object[] newValues, Object[] oldValues,
            String[] properties, Type[] types) throws CallbackException {
        if (obj instanceof Auditable) {
            Session session = sessionFactory.openSession();
            Class objectClass = obj.getClass();
            String className = objectClass.getSimpleName();

            // Use the id and class to get the pre-update state from the database
            Serializable persistedObjectId = getObjectId(obj);
            Object preUpdateState = session.get(objectClass,  persistedObjectId);

            try {
                logChanges(obj, preUpdateState, null, persistedObjectId.toString(), UPDATE, className);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            session.close();
        }

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sf.hibernate.Interceptor#onSave(java.lang.Object,
     *      java.io.Serializable, java.lang.Object[], java.lang.String[],
     *      net.sf.hibernate.type.Type[])
     */
    public boolean onSave(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
    throws CallbackException {
        if (obj instanceof Auditable) {
            try {
                Class objectClass = obj.getClass();
                String className = objectClass.getSimpleName();
//                Serializable persistedObjectId = getObjectId(obj);
                String identifier = (id == null) ? null : id.toString();
                logChanges(obj, null, null, identifier, INSERT, className);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sf.hibernate.Interceptor#onDelete(java.lang.Object,
     *      java.io.Serializable, java.lang.Object[], java.lang.String[],
     *      net.sf.hibernate.type.Type[])
     */
    public void onDelete(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
    throws CallbackException {
        if (obj instanceof Auditable) {
            try {
                Class objectClass = obj.getClass();
                String className = objectClass.getSimpleName();
                logChanges(obj, null, null, id.toString(), DELETE, className);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see net.sf.hibernate.Interceptor#postFlush(java.util.Iterator)
     */
    public synchronized void postFlush(Iterator arg0) throws CallbackException {
        log.debug("In postFlush of AuditLogInterceptor..");

        Session session = sessionFactory.openSession();

        try {
        	
            synchronized (inserts) {
            	 for (SatbAuditLog logRecord : inserts) {
                     Serializable id = getObjectId(logRecord.getEntity());
                     if (id == null) logRecord.setEntityId("IDENTITY");
                     else logRecord.setEntityId(id.toString());
                     session.save(logRecord);
                 }
			}
			synchronized (updates) {
				for (SatbAuditLog logRecord : updates) session.save(logRecord);
			}
			synchronized (deletes) {
				for (SatbAuditLog logRecord : deletes) session.save(logRecord);
			}
			
        } catch (HibernateException e) {
            throw new CallbackException(e);
        } finally {
            inserts.clear();
            updates.clear();
            deletes.clear();
            session.flush();
            session.close();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.Interceptor#afterTransactionCompletion(org.hibernate.Transaction)
     */
    public void afterTransactionCompletion(Transaction arg0) {
        // clear any audit log records potentially remaining from a rolled back
        // transaction
        inserts.clear();
        updates.clear();
        deletes.clear();
    }


    /**
     * Logs changes to persistent data
     * @param newObject the object being saved, updated or deleted
     * @param existingObject the existing object in the database.  Used only for updates
     * @param parentObject the parent object. Set only if passing a Component object as the newObject
     * @param persistedObjectId the id of the persisted object.  Used only for update and delete
     * @param event the type of event being logged.  Valid values are "update", "delete", "save"
     * @param className the name of the class being logged.  Used as a reference in the AclAuditLog
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void logChanges(Object newObject, Object existingObject, Object parentObject,
            String persistedObjectId, String event, String className)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException  {

    	if(event.equals(UPDATE)) {
    		// Values haven't changed so don need to log
    		//TODO : added to check for null. To be removed.
    		if(newObject==null ? existingObject==null ? false : true : true || !newObject.toString().equals(existingObject.toString())){
    		//if(!newObject.toString().equals(existingObject.toString())){
    			
    			SatbAuditLog logRecord = new SatbAuditLog();
                logRecord.setEntity(className);
                logRecord.setAction(event);
                logRecord.setLoginId(this.getUserName());
                logRecord.setTime(new Timestamp(new Date().getTime()));
                logRecord.setEntityId(persistedObjectId);
                if(newObject!=null){
                	logRecord.setNewValue(newObject.toString());
                }
                if(existingObject!=null){
                	logRecord.setOldValue(existingObject.toString());
                }
                updates.add(logRecord);
    		}

        } else if(event.equals(DELETE)) {

        	SatbAuditLog logRecord = new SatbAuditLog();
            logRecord.setEntity(className);
            logRecord.setAction(event);
            logRecord.setLoginId(this.getUserName());
            logRecord.setTime(new Timestamp(new Date().getTime()));
            logRecord.setNewValue("");
            logRecord.setOldValue(newObject.toString());
            if (persistedObjectId != null)
                logRecord.setEntityId(persistedObjectId);

            deletes.add(logRecord);
        } else if(event.equals(INSERT)) {

        	SatbAuditLog logRecord = new SatbAuditLog();
            logRecord.setEntity(className);
            logRecord.setAction(event);
            logRecord.setLoginId(this.getUserName());
            logRecord.setTime(new Timestamp(new Date().getTime()));
            logRecord.setOldValue("");
            logRecord.setNewValue(newObject.toString());
            if (persistedObjectId != null)
                logRecord.setEntityId(persistedObjectId);

            inserts.add(logRecord);
        }
    	
    }


    /**
     * Returns an array of all fields used by this object from it's class and all superclasses.
     * @param objectClass the class
     * @param fields the current field list
     * @return an array of fields
     */
    private Field[] getAllFields(Class objectClass, Field[] fields) {

        Field[] newFields = objectClass.getDeclaredFields();

        int fieldsSize = 0;
        int newFieldsSize = 0;

        if(fields != null) {
            fieldsSize = fields.length;
        }

        if(newFields != null) {
            newFieldsSize = newFields.length;
        }

        Field[] totalFields = new Field[fieldsSize + newFieldsSize];

        if(fieldsSize > 0) {
            System.arraycopy(fields, 0, totalFields, 0, fieldsSize);
        }

        if(newFieldsSize > 0) {
            System.arraycopy(newFields, 0, totalFields, fieldsSize, newFieldsSize);
        }

        Class superClass = objectClass.getSuperclass();

        Field[] finalFieldsArray;

        if (superClass != null && ! superClass.getName().equals("java.lang.Object")) {
            finalFieldsArray = getAllFields(superClass, totalFields);
        } else {
            finalFieldsArray = totalFields;
        }

        return finalFieldsArray;

    }

    /**
     * Gets the id of the persisted object
     * @param obj the object to get the id from
     * @return object Id
     */
    private Serializable getObjectId(Object obj) {
        Serializable id = null;
        try {
            id = sessionFactory
                .getClassMetadata(obj.getClass())
                .getIdentifier(obj, EntityMode.POJO);
        } catch (Exception e) {
            log.warn("Audit Log Failed - Could not get persisted object id: " + e.getMessage());
        }
        return id;
    }

    /**
     * Gets the id property name of the persisted object
     * @param obj the object to get the id name from
     * @return String name
     */
    private String getObjectIdPropertyName(Object obj) {
        String name = null;
        try {
            name = sessionFactory
                .getClassMetadata(obj.getClass())
                .getIdentifierPropertyName();
        } catch (Exception e) {
            log.warn("Audit Log Failed - Could not get persisted object id property name: " + e.getMessage());
        }
        return name;
    }

    /**
     * Gets the current user's id from the Acegi secureContext
     *
     * @return current user's userId
     */
    private String getUserName() {
        SecurityContext secureContext = (SecurityContext) SecurityContextHolder.getContext();

        // secure context will be null when running unit tests so leave userId
        // as null
        if (secureContext != null) {
            Authentication auth = (Authentication) secureContext.getAuthentication();

            String userName = null;
            if (auth != null) {
	            if (auth.getPrincipal() instanceof UserDetails) {
	                UserDetails userDetails = (UserDetails) auth.getPrincipal();
	                userName = userDetails.getUsername();
	            } else {
	                userName = auth.getPrincipal().toString();
	            }
            }

            if(userName == null || userName.equals("")) {
                return "anonymous";
            } else {
                return userName;
            }
        } else {
            return "anonymous";
        }
    }


}

