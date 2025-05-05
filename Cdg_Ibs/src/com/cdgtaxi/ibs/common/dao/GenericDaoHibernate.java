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
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.Creatable;
import com.cdgtaxi.ibs.common.model.Updatable;
import com.cdgtaxi.ibs.util.DateUtil;

public class GenericDaoHibernate extends HibernateDaoSupport implements GenericDao{
	
	public Object get(Class arg0, Serializable arg1){
		return getHibernateTemplate().get(arg0, arg1);
	}
	
	public List getAll(Class arg0){
		DetachedCriteria criteria = DetachedCriteria.forClass(arg0);
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Serializable save(Object entity){
		return getHibernateTemplate().save(entity);
		
	}
	
	public void load(Object entity, Serializable id){
		getHibernateTemplate().load(entity, id);
	}
	
	public Object load(Class clazz, Serializable id){
		return getHibernateTemplate().load(clazz, id);
	}
	
	public Serializable save(Object entity, String createdBy){
		if(entity instanceof Creatable){
			((Creatable)entity).setCreatedBy(createdBy);
			((Creatable)entity).setCreatedDt(DateUtil.getCurrentTimestamp());
		}
		return getHibernateTemplate().save(entity);
	}
	public void saveOrUpdateAll(Collection entities){
		getHibernateTemplate().saveOrUpdateAll(entities);
	}
	public void saveAll(Collection entities, String createdBy){
		for(Object entity : entities){
			if(entity instanceof Creatable){
				((Creatable)entity).setCreatedBy(createdBy);
				((Creatable)entity).setCreatedDt(DateUtil.getCurrentTimestamp());
			}
		}
		getHibernateTemplate().saveOrUpdateAll(entities);
	}
	
	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}
	
	public void update(Object entity, String updatedBy) {
		if(entity instanceof Updatable){
			((Updatable)entity).setUpdatedBy(updatedBy);
			((Updatable)entity).setUpdatedDt(DateUtil.getCurrentTimestamp());
		}
		getHibernateTemplate().update(entity);
	}
	public void updateWithoutUpdateDt(Object entity, String updatedBy) {
		if(entity instanceof Updatable){
			((Updatable)entity).setUpdatedBy(updatedBy);
		}
		getHibernateTemplate().update(entity);
	}
	
	public void updateAll(Collection entities, String updatedBy){
		for(Object entity : entities){
			if(entity instanceof Updatable){
				((Updatable)entity).setUpdatedBy(updatedBy);
				((Updatable)entity).setUpdatedDt(DateUtil.getCurrentTimestamp());
			}
		}
		getHibernateTemplate().saveOrUpdateAll(entities);
	}
	
	public void delete(Object entity){
		getHibernateTemplate().delete(entity);
	}
	public void deleteAll(Collection entities){
		getHibernateTemplate().deleteAll(entities);
	}
	
	public int getCount(Object entity){
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass())
			.setProjection(Projections.rowCount())
			.add(Example.create(entity));
		
		List result = getHibernateTemplate().findByCriteria(criteria);
		if(result==null || result.size()==0) return 0;
		else return (Integer) result.get(0);
	}
	
	public boolean isExists(Object entity){
		return getCount(entity) > 0;
	}
	
	public List getByExample(Object entity){
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass()).add(Example.create(entity));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Object merge(Object entity){
        entity = getHibernateTemplate().merge(entity);
        return entity;
    }
	
	public Object merge(Object entity, String updatedBy){
		if(entity instanceof Updatable){
			((Updatable)entity).setUpdatedBy(updatedBy);
			((Updatable)entity).setUpdatedDt(DateUtil.getCurrentTimestamp());
		}
		
		entity = getHibernateTemplate().merge(entity);
        return entity;
    }
	
	protected List findAllByCriteria(DetachedCriteria criteria){
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	protected List findMaxResultByCriteria(DetachedCriteria criteria, int maxResult){
		return getHibernateTemplate().findByCriteria(criteria, 0, maxResult);
	}
	
	protected List findDefaultMaxResultByCriteria(DetachedCriteria criteria){
		//Increased one more so that we can detect that the results are actually more than the configured
		//number and do the notification.
		return getHibernateTemplate().findByCriteria(criteria, 0, ConfigurableConstants.getMaxQueryResult()+1);
	}
	
	public BigDecimal getNextSequenceNo(String sequenceName) throws IllegalArgumentException {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery("SELECT "+sequenceName+".NEXTVAL FROM DUAL");
		List result = query.list();
		if(!result.isEmpty()){
			Iterator iter = result.iterator();
			BigDecimal nextValue = (BigDecimal) iter.next();
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
		
		getHibernateTemplate().evict(entity);
	}
	
	public <T> T firstResult(DetachedCriteria dc) {
		
		List<T> list = findAllByCriteria(dc);
		
		return firstResult(list);
	}
	
	public <T> T firstResult(List<T> list) {
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}
}
