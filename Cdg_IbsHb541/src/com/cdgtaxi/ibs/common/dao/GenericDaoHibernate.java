package com.cdgtaxi.ibs.common.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.Creatable;
import com.cdgtaxi.ibs.common.model.Updatable;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("deprecation")
public class GenericDaoHibernate extends HibernateDaoSupport implements GenericDao{
	
    public void init(){
		getActiveDBTransaction();
	}
	public Object get(Class arg0, Serializable arg1){
		getActiveDBTransaction();
		Object rtobj = getHibernateTemplate().get(arg0, arg1);
		this.currentSession().close();
		return rtobj;
	}
	
	public List getAll(Class arg0){
		getActiveDBTransaction();
		DetachedCriteria criteria = DetachedCriteria.forClass(arg0);
		List rtlist= getHibernateTemplate().findByCriteria(criteria);
		this.currentSession().close();
		return rtlist;
	}
	
	public Serializable save(Object entity){
		getActiveDBTransaction();
		Serializable sbl= getHibernateTemplate().save(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
		return sbl;
	}
	
	public void load(Object entity, Serializable id){
		getActiveDBTransaction();
		getHibernateTemplate().load(entity, id);
		this.currentSession().close();
	}
	
	public Object load(Class clazz, Serializable id){
		getActiveDBTransaction();
		Object rtobj =getHibernateTemplate().load(clazz, id);
		this.currentSession().close();
		return rtobj;
	}
	
	public Serializable save(Object entity, String createdBy){
		getActiveDBTransaction();
		if(entity instanceof Creatable){
			((Creatable)entity).setCreatedBy(createdBy);
			((Creatable)entity).setCreatedDt(DateUtil.getCurrentTimestamp());
		}
		Serializable sbl= getHibernateTemplate().save(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
		return sbl;
	}
	public void saveOrUpdateAll(List entities){
		getActiveDBTransaction();
		for(Object entity : entities){
			getHibernateTemplate().saveOrUpdate(entity);
		}
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	public void saveAll(Collection entities, String createdBy){
		getActiveDBTransaction();
		for(Object entity : entities){
			if(entity instanceof Creatable){
				((Creatable)entity).setCreatedBy(createdBy);
				((Creatable)entity).setCreatedDt(DateUtil.getCurrentTimestamp());
			}
			getHibernateTemplate().saveOrUpdate(entity);
		}
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	
	public void update(Object entity) {

		getActiveDBTransaction();
		getHibernateTemplate().update(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	
	public void update(Object entity, String updatedBy) {
		getActiveDBTransaction();
		if(entity instanceof Updatable){
			((Updatable)entity).setUpdatedBy(updatedBy);
			((Updatable)entity).setUpdatedDt(DateUtil.getCurrentTimestamp());
		}
		getHibernateTemplate().update(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	public void updateWithoutUpdateDt(Object entity, String updatedBy) {
		getActiveDBTransaction();
		if(entity instanceof Updatable){
			((Updatable)entity).setUpdatedBy(updatedBy);
		}
		getHibernateTemplate().update(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	
	public void updateAll(Collection entities, String updatedBy){
		getActiveDBTransaction();
		for(Object entity : entities){
			if(entity instanceof Updatable){
				((Updatable)entity).setUpdatedBy(updatedBy);
				((Updatable)entity).setUpdatedDt(DateUtil.getCurrentTimestamp());
			}
		}
		getHibernateTemplate().saveOrUpdate(entities);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	
	public void delete(Object entity){
		getActiveDBTransaction();
		getHibernateTemplate().delete(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	public void deleteAll(Collection entities){
		getActiveDBTransaction();
		getHibernateTemplate().deleteAll(entities);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
	}
	
	public int getCount(Object entity){
		getActiveDBTransaction();
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass())
			.setProjection(Projections.rowCount())
			.add(Example.create(entity));
		
		List result = getHibernateTemplate().findByCriteria(criteria);
		this.currentSession().close();
		if(result==null || result.size()==0) return 0;
		else return (Integer) result.get(0);
	}
	
	public boolean isExists(Object entity){
		return getCount(entity) > 0;
	}
	
	public List getByExample(Object entity){
		getActiveDBTransaction();
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass()).add(Example.create(entity));
		List rtlist = getHibernateTemplate().findByCriteria(criteria);
		this.currentSession().close();
		return rtlist;
	}
	
	public Object merge(Object entity){
		getActiveDBTransaction();
        entity = getHibernateTemplate().merge(entity);
        this.currentSession().getTransaction().commit();
        this.currentSession().close();
        return entity;
    }
	
	public Object merge(Object entity, String updatedBy){
		getActiveDBTransaction();
		if(entity instanceof Updatable){
			((Updatable)entity).setUpdatedBy(updatedBy);
			((Updatable)entity).setUpdatedDt(DateUtil.getCurrentTimestamp());
		}
		
		entity = getHibernateTemplate().merge(entity);
		this.currentSession().getTransaction().commit();
		this.currentSession().close();
        return entity;
    }
	
	protected List findAllByCriteria(DetachedCriteria criteria){
		getActiveDBTransaction();
		List l = getHibernateTemplate().findByCriteria(criteria);
		this.currentSession().close();
		return l;
	}
	
	protected List findMaxResultByCriteria(DetachedCriteria criteria, int maxResult){
		getActiveDBTransaction();
		List rtlist = getHibernateTemplate().findByCriteria(criteria, 0, maxResult);
		return rtlist;
	}
	
	protected List findDefaultMaxResultByCriteria(DetachedCriteria criteria){
		//Increased one more so that we can detect that the results are actually more than the configured
		//number and do the notification.
		getActiveDBTransaction();
		List rtlist = getHibernateTemplate().findByCriteria(criteria, 0, ConfigurableConstants.getMaxQueryResult()+1);
		return rtlist;
	}
	
	public BigDecimal getNextSequenceNo(String sequenceName) throws IllegalArgumentException {
		Session session = null;
		BigDecimal nextValue =null;
		try
		{
			getActiveDBTransaction();
			session = this.currentSession();
			SQLQuery query = session.createSQLQuery("SELECT "+sequenceName+".NEXTVAL FROM DUAL");
			List result = query.list();
			if(!result.isEmpty()){
				Iterator iter = result.iterator();
				nextValue = (BigDecimal) iter.next();
				if(session.isConnected()){
					session.close();
				}
				return nextValue;
			}else{
				if(session.isConnected()){
					session.close();
				}
				throw new IllegalArgumentException("Sequence Name ["+sequenceName+"] Not found!");
			}
		}
		catch(Exception e)
	    {
			logger.info(e);
	    }
		finally{if(session.isConnected()){session.close();}}
		return nextValue;
	}
	
	public DetachedCriteria clone(DetachedCriteria detachedCriteria){
		DetachedCriteria idCriteria;
		try {
			ByteArrayOutputStream baostream = new ByteArrayOutputStream();
			ObjectOutputStream oostream = new ObjectOutputStream(baostream);
			oostream.writeObject(detachedCriteria);
			oostream.flush();
			oostream.close();
			ByteArrayInputStream baistream = new ByteArrayInputStream(baostream.toByteArray());
			ObjectInputStream oistream = new ObjectInputStream(baistream);
			idCriteria = (DetachedCriteria)oistream.readObject();
			oistream.close();
		} 
		catch(Throwable t) {
			throw new HibernateException(t);
		}
		return idCriteria;
	}
	
	/**
	 * Due to left join, the result returned more than max result.
	 * End search result may only retrieve total entities less than max result.
	 * @param originalCriteria
	 * @param primaryKeyPropertyName This must be the database column name not model property name
	 * @return
	 */
	public DetachedCriteria createMaxResultSubquery(DetachedCriteria originalCriteria,
			String primaryKeyPropertyName, int limit){
		String alias = originalCriteria.getAlias()+"_";
		Integer maxResult = limit+1;
		
		DetachedCriteria criteria = this.clone(originalCriteria);
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.sqlGroupProjection("distinct case when ROW_NUMBER() OVER (order by "+alias+"."+primaryKeyPropertyName+") <= "+maxResult+" then "+alias+"."+primaryKeyPropertyName+" else -999 end", 
				alias+"."+primaryKeyPropertyName, new String[]{}, new Type[]{}));
		criteria.setProjection(projection);
		
		return criteria;
	}
	
	public DetachedCriteria createMaxResultSubquery(DetachedCriteria originalCriteria,
			String primaryKeyPropertyName){
		return createMaxResultSubquery(originalCriteria, primaryKeyPropertyName, ConfigurableConstants.getMaxQueryResult());
	}
	
	
	/**
	 * Overloaded method. Above method only cater for numbers. Below method caters for both
	 * Due to left join, the result returned more than max result.
	 * End search result may only retrieve total entities less than max result.
	 * @param originalCriteria
	 * @param primaryKeyPropertyName This must be the database column name not model property name
	 * @param clazz the datatype of the column. Currently only String and others
	 * @return
	 */
	public DetachedCriteria createMaxResultSubquery(DetachedCriteria originalCriteria,
			String primaryKeyPropertyName, Class clazz){
		String alias = originalCriteria.getAlias()+"_";
		Integer maxResult = ConfigurableConstants.getMaxQueryResult()+1;
		if(clazz.equals(String.class)){
			alias = "to_number(" + alias;
			primaryKeyPropertyName = primaryKeyPropertyName + ")";
		}
		DetachedCriteria criteria = this.clone(originalCriteria);
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.sqlGroupProjection("distinct case when ROW_NUMBER() OVER (order by "+alias+"."+primaryKeyPropertyName+") <= "+maxResult+" then "+alias+"."+primaryKeyPropertyName+" else -999 end", 
				alias+"."+primaryKeyPropertyName, new String[]{}, new Type[]{}));
		criteria.setProjection(projection);
		
		return criteria;
	}

	public void evict(Object entity) {
		this.getActiveDBTransaction();
		getHibernateTemplate().evict(entity);
		this.currentSession().close();
	}
	
	public <T> T firstResult(DetachedCriteria dc) {
		
		getActiveDBTransaction();
		List<T> list = findAllByCriteria(dc);
		this.currentSession().close();
		return firstResult(list);
	}
	
	public <T> T firstResult(List<T> list) {
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}
	
    public void getActiveDBTransaction(){
    	
		TransactionStatus status = this.currentSession().getTransaction().getStatus(); 
		logger.info("status :"+status);
		if(!status.name().equalsIgnoreCase("ACTIVE"))
		{
			this.currentSession().beginTransaction();
		}
	}
}
