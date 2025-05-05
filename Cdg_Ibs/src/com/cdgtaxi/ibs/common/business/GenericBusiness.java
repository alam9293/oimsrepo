package com.cdgtaxi.ibs.common.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.cdgtaxi.ibs.common.dao.DaoHelper;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;

public interface GenericBusiness {
	public void setDaoHelper(DaoHelper daoHelper);
	public Object get(Class arg0, Serializable arg1);
	public List getAll(Class arg0);
	public Serializable save(Object entity);
	public Serializable save(Object entity, String createdBy);
	public void update(Object entity);
	public void update(Object entity, String updatedBy);
	public void delete(Object entity);
	public void evict(Object entity);
	public int getCount(Object entity);
	public boolean isExists(Object entity);
	public List getByExample(Object entity);
	public Object merge(Object entity);
	public Long generateMiscInvoice(BmtbInvoiceHeader header);
	public BigDecimal getNextSequenceNo(String sequenceName) throws IllegalArgumentException;
}