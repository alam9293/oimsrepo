package com.cdgtaxi.ibs.master.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.dao.GenericDaoHibernate;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public class ConstantDaoHibernate extends GenericDaoHibernate implements ConstantDao{
	@SuppressWarnings("unchecked")
	public List<MstbMasterTable> getAllMasterTable() {
		DetachedCriteria masterCriteria = DetachedCriteria.forClass(MstbMasterTable.class);
		masterCriteria.addOrder(Order.asc("masterValue"));
		//return (List<MstbMasterTable>) this.getHibernateTemplate().findByCriteria(masterCriteria);
		return (List<MstbMasterTable>) this.findAllByCriteria(masterCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbMasterTable> getAllActiveMasterTable(){
		DetachedCriteria masterCriteria = DetachedCriteria.forClass(MstbMasterTable.class);
		masterCriteria.add(Restrictions.eq("masterStatus", "A"));
		masterCriteria.addOrder(Order.asc("masterValue"));
		return (List<MstbMasterTable>) this.findAllByCriteria(masterCriteria);
	}
}