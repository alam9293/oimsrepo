package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.dao.GenericDaoHibernate;

public class RoleDaoHibernate extends GenericDaoHibernate implements RoleDao{
	
	/**
	 * To retrieve all roles sorted asc by name
	 */
	public List<SatbRole> getRoles(){
		DetachedCriteria roleCriteria = DetachedCriteria.forClass(SatbRole.class);
		roleCriteria.addOrder(Order.asc("name"));
		
		return this.findAllByCriteria(roleCriteria);
	}
	
	/**
	 * To retrieve all active roles sorted asc by name
	 */
	public List<SatbRole> getActiveRoles(){
		DetachedCriteria roleCriteria = DetachedCriteria.forClass(SatbRole.class);
		roleCriteria.add(Restrictions.eq("status", Constants.STATUS_ACTIVE));
		roleCriteria.addOrder(Order.asc("name"));
		
		return this.findAllByCriteria(roleCriteria);
	}
	
	/**
	 * To find out whether the role name is used.
	 * @param name
	 * @return True if name is used else otherwise
	 */
	public boolean isNameUsed(String name){
		DetachedCriteria roleCriteria = DetachedCriteria.forClass(SatbRole.class);
		roleCriteria.add(Restrictions.ilike("name", name, MatchMode.EXACT));
		List roles = this.findAllByCriteria(roleCriteria);
		
		if(roles.isEmpty()) return false;
		else return true;
	}
	
	public List<SatbRole> searchRole(String name, String status){
		DetachedCriteria roleCriteria = DetachedCriteria.forClass(SatbRole.class);
		
		if(name !=null && !name.equals(""))
			roleCriteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		if(status !=null && !status.equals(""))
			roleCriteria.add(Restrictions.eq("status", status));
		
		return findDefaultMaxResultByCriteria(roleCriteria);
	}
	
	public SatbRole get(Long roleId){
		DetachedCriteria roleCriteria = DetachedCriteria.forClass(SatbRole.class);
		DetachedCriteria resourceCriteria = roleCriteria.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		
		roleCriteria.add(Restrictions.eq("roleId", roleId));
		
		List results = this.findAllByCriteria(roleCriteria);
		if(results.isEmpty())
			return null;
		else
			return (SatbRole)results.get(0);
	}
	
	public SatbRole getActiveRole(String name){
		DetachedCriteria roleCriteria = DetachedCriteria.forClass(SatbRole.class);
		roleCriteria.add(Restrictions.eq("status", Constants.STATUS_ACTIVE));
		roleCriteria.add(Restrictions.eq("name", name));
		roleCriteria.addOrder(Order.asc("name"));
		
		List results =  this.findAllByCriteria(roleCriteria);
		if(results.isEmpty())
			return null;
		else
			return (SatbRole)results.get(0);
	}
}
