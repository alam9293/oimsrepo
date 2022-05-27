package org.zkoss.reference.developer.hibernate.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Filter;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;
import org.zkoss.reference.developer.hibernate.dao.HibernateUtil;

public class OpenSessionInViewListener implements ExecutionInit, ExecutionCleanup, HibernateOperations {
	private static final Logger log = Logger.getLogger(OpenSessionInViewListener.class);

	public void init(Execution exec, Execution parent) {
		if (parent == null) { //the root execution of a servlet request
			log.info("Starting a database transaction: "+exec);
			if(!HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().isActive())
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		}
	}

	public void cleanup(Execution exec, Execution parent, List errs) {
		if (parent == null) { //the root execution of a servlet request
			if (errs == null || errs.isEmpty()) {
				log.info("Committing the database transaction: "+exec);
				//HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			} else {
				final Throwable ex = (Throwable) errs.get(0);
				rollback(exec, ex);
			}
		}
	}

	private void rollback(Execution exec, Throwable ex) {
		try {
			if (HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().isActive()) {
				log.info("Trying to rollback database transaction after exception:"+ex);
				HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			}
		} catch (Throwable rbEx) {
			log.info("Could not rollback transaction after exception! Original Exception:\n"+ex, rbEx);
		}
	}

	@Override
	public <T> T execute(HibernateCallback<T> action) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String entityName, Serializable id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T load(Class<T> entityClass, Serializable id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T load(Class<T> entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object load(String entityName, Serializable id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object load(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> loadAll(Class<T> entityClass) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load(Object entity, Serializable id) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void evict(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(Object proxy) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Filter enableFilter(String filterName) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lock(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable save(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable save(String entityName, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String entityName, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(String entityName, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replicate(Object entity, ReplicationMode replicationMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replicate(String entityName, Object entity, ReplicationMode replicationMode)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(String entityName, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T merge(T entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T merge(String entityName, T entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String entityName, Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Collection<?> entities) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> findByCriteria(DetachedCriteria criteria) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByExample(T exampleEntity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByExample(String entityName, T exampleEntity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByExample(T exampleEntity, int firstResult, int maxResults) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByExample(String entityName, T exampleEntity, int firstResult, int maxResults)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> find(String queryString, Object... values) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByNamedParam(String queryString, String paramName, Object value) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByNamedParam(String queryString, String[] paramNames, Object[] values)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByValueBean(String queryString, Object valueBean) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByNamedQuery(String queryName, Object... values) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> findByNamedQueryAndValueBean(String queryName, Object valueBean) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<?> iterate(String queryString, Object... values) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeIterator(Iterator<?> it) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int bulkUpdate(String queryString, Object... values) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}
}