package com.cdgtaxi.ibs.common.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface GenericDao {
	public Object get(Class arg0, Serializable arg1);
	public List getAll(Class arg0);
	public Serializable save(Object entity);
	public void saveOrUpdateAll(Collection entities);
	public void saveAll(Collection entities, String createdBy);
	public Serializable save(Object entity, String createdBy);
	public void updateAll(Collection entities, String updatedBy);
	public void update(Object entity);
	public void update(Object entity, String updatedBy);
	public void updateWithoutUpdateDt(Object entity, String updatedBy);
	public void delete(Object entity);
	public void deleteAll(Collection entities);
	public void evict(Object entity);
	public int getCount(Object entity);
	public boolean isExists(Object entity);
	public List getByExample(Object entity);
	public Object merge(Object entity);
	public Object merge(Object entity, String updatedBy);
	public BigDecimal getNextSequenceNo(String sequenceName) throws IllegalArgumentException;
	public void load(Object entity, Serializable id);
	public Object load(Class clazz, Serializable id);
}
