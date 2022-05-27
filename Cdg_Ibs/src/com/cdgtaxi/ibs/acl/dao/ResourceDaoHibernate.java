package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.dao.GenericDaoHibernate;

public class ResourceDaoHibernate extends GenericDaoHibernate implements ResourceDao{
	public List retrieveMenu(Long parentRsrcId, List<String> grantedAuthorities){

		DetachedCriteria resourceCriteria = DetachedCriteria.forClass(SatbResource.class);
		DetachedCriteria roleCriteria = resourceCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		//		DetachedCriteria parentResourceCriteria = resourceCriteria.createCriteria("satbResource", DetachedCriteria.LEFT_JOIN);

		roleCriteria.add(Restrictions.in("name", grantedAuthorities));
		resourceCriteria.add(Restrictions.eq("display", Constants.BOOLEAN_YES));
		//		parentResourceCriteria.add(Restrictions.eq("rsrcId", parentRsrcId));

		//Do a Distinct
		resourceCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		//Ordering
		resourceCriteria.addOrder(Order.asc("sequence"));

		return this.findAllByCriteria(resourceCriteria);
	}

	public List<SatbResource> getAll(){
		DetachedCriteria resourceCriteria = DetachedCriteria.forClass(SatbResource.class);
		DetachedCriteria roleCriteria = resourceCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria parentResourceCriteria = resourceCriteria.createCriteria("satbResource", DetachedCriteria.LEFT_JOIN);
		return this.findAllByCriteria(resourceCriteria);
	}


	/**
	 * To get all resources with given parent resource id.
	 * This method will lazy init the children resources up to 5 tier.
	 * @param parentRsrcId Parent Resource Id
	 * @return List of Resources
	 */
	public List<SatbResource> get(Long parentRsrcId){
		DetachedCriteria resourceCriteria = DetachedCriteria.forClass(SatbResource.class);
		DetachedCriteria parentResourceCriteria = resourceCriteria.createCriteria("satbResource", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria1 = resourceCriteria.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria2 = childrenResourceCriteria1.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria3 = childrenResourceCriteria2.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria4 = childrenResourceCriteria3.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria5 = childrenResourceCriteria4.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);

		parentResourceCriteria.add(Restrictions.eq("rsrcId", parentRsrcId));

		//Do a Distinct
		resourceCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		//Ordering
		resourceCriteria.addOrder(Order.asc("sequence"));
		childrenResourceCriteria1.addOrder(Order.asc("sequence"));
		childrenResourceCriteria2.addOrder(Order.asc("sequence"));
		childrenResourceCriteria3.addOrder(Order.asc("sequence"));
		childrenResourceCriteria4.addOrder(Order.asc("sequence"));
		childrenResourceCriteria5.addOrder(Order.asc("sequence"));

		return this.findAllByCriteria(resourceCriteria);
	}

	/**
	 * To get a particular resource with given resource name.
	 * This method will lazy init the children resources
	 * @param rsrcName resourceCode
	 * @return SatbResource
	 */
	public SatbResource get(String rsrcName){
		DetachedCriteria resourceCriteria = DetachedCriteria.forClass(SatbResource.class);
		DetachedCriteria parentResourceCriteria = resourceCriteria.createCriteria("satbResource", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria = resourceCriteria.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);

		resourceCriteria.add(Restrictions.eq("rsrcName", rsrcName));

		List results = this.findAllByCriteria(resourceCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (SatbResource)results.get(0);
		}
	}
	
	public List<SatbResource> getReportCategories(SatbRole role){
		DetachedCriteria resourceCriteria = DetachedCriteria.forClass(SatbResource.class);
		DetachedCriteria parentResourceCriteria = resourceCriteria.createCriteria("satbResource", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria childrenResourceCriteria = resourceCriteria.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria roleCriteria = childrenResourceCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		
		parentResourceCriteria.add(Restrictions.eq("uri", Uri.REPORT));
		resourceCriteria.add(Restrictions.ne("uri", Uri.REPORT_SELECTION));
		roleCriteria.add(Restrictions.idEq(role.getRoleId()));
		resourceCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		resourceCriteria.addOrder(Order.asc("displayName"));
		
		return this.findAllByCriteria(resourceCriteria);
	}
	
	public List<SatbResource> getReports(SatbResource reportCategory, SatbRole role){
		DetachedCriteria resourceCriteria = DetachedCriteria.forClass(SatbResource.class);
		DetachedCriteria parentResourceCriteria = resourceCriteria.createCriteria("satbResource", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria roleCriteria = resourceCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		
		resourceCriteria.add(Restrictions.eq("satbResource", reportCategory));
		roleCriteria.add(Restrictions.idEq(role.getRoleId()));
		resourceCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		resourceCriteria.addOrder(Order.asc("displayName"));
		
		return this.findAllByCriteria(resourceCriteria);
	}
}
