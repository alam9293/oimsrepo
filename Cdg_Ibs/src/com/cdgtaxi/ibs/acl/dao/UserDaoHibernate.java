package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.dao.GenericDaoHibernate;

public class UserDaoHibernate extends GenericDaoHibernate implements UserDao, UserDetailsService{

	/**
	 * Becomes a dummy method
	 */
	@SuppressWarnings("serial")
	@Deprecated
	public UserDetails loadUserByUsername(String loginId)
			throws UsernameNotFoundException, DataAccessException {
        return new UserDetails() {
			public boolean isEnabled() {
				return true;
			}
			public boolean isCredentialsNonExpired() {
				return true;
			}
			public boolean isAccountNonLocked() {
				return true;
			}
			public boolean isAccountNonExpired() {
				return true;
			}
			public String getUsername() {
				return null;
			}
			public String getPassword() {
				return null;
			}
			public GrantedAuthority[] getAuthorities() {
				return null;
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	public UserDetails loadUserByUsernameAndDomain(String loginId, String domain)
		throws UsernameNotFoundException, DataAccessException {
		DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
		userCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		userCriteria.add(Restrictions.sqlRestriction("lower(login_Id) = lower(?)", loginId, Hibernate.STRING));
		userCriteria.add(Restrictions.sqlRestriction("lower(domain) = lower(?)", domain, Hibernate.STRING));
		
		List users = this.findAllByCriteria(userCriteria);
		if (users == null || users.isEmpty()){
			logger.warn("user ['" + loginId + "', '" + domain + "'] not found...");
		    return null;
		}
		else {
		    return (SatbUser) users.get(0);
		}
	}
	
	/**
	 * To get user together with role details pulled out.
	 * @param userId Primary key
	 * @return AclUser Entity with AclRole loaded
	 */
	@SuppressWarnings("rawtypes")
	public SatbUser getUserWithRoles(Long userId){
		DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
		userCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		userCriteria.add(Restrictions.eq("userId", userId));
		
		List result = this.findAllByCriteria(userCriteria);
		if(!result.isEmpty())
			return (SatbUser)result.get(0);
		else
			return null;
	}

	@SuppressWarnings("rawtypes")
	public List searchUser(String loginId, String domain, String name, String email, String status, String locked){
		DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
		
		if(loginId !=null && !loginId.equals(""))
			userCriteria.add(Restrictions.ilike("loginId", loginId, MatchMode.ANYWHERE));
		if(name !=null && !name.equals(""))
			userCriteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		if(email !=null && !email.equals(""))
			userCriteria.add(Restrictions.ilike("email", email, MatchMode.ANYWHERE));
		if(status !=null && !status.equals(""))
			userCriteria.add(Restrictions.eq("status", status));
		if(locked !=null && !locked.equals(""))
			if(locked.equals(Constants.BOOLEAN_YES)) userCriteria.add(Restrictions.eq("locked", locked));
			else userCriteria.add(Restrictions.ne("locked", Constants.BOOLEAN_YES));
		if(domain !=null && !domain.equals(""))
			userCriteria.add(Restrictions.eq("domain", domain));
		
		return this.findDefaultMaxResultByCriteria(userCriteria);
	}
	
	/**
	 * To check loginId is used or not
	 * @param loginId
	 * @return false is loginId is not used, otherwise true
	 */
	public boolean isLoginIdAndDomainUsed(String loginId, String domain){
		return this.loadUserByUsernameAndDomain(loginId, domain) == null ? false : true;
	}
	
	@SuppressWarnings("unchecked")
	public List<SatbUser> searchUser(String uri){
		DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
		userCriteria = userCriteria.createCriteria("satbRoles", DetachedCriteria.LEFT_JOIN);
		userCriteria = userCriteria.createCriteria("satbResources", DetachedCriteria.LEFT_JOIN);
		userCriteria.add(Restrictions.eq("uri", uri));
		userCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(userCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public SatbUser getUser(String loginId, String domain){
		DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
		userCriteria.add(Restrictions.sqlRestriction("lower(login_Id) = lower(?)", loginId, Hibernate.STRING));
		userCriteria.add(Restrictions.sqlRestriction("lower(domain) = lower(?)", domain, Hibernate.STRING));
		List<SatbUser> users = this.findAllByCriteria(userCriteria);
		if(!users.isEmpty()){
			return users.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDomains(){
		DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
		userCriteria.setProjection(Projections.distinct(Projections.property("domain")));
		userCriteria.addOrder(Order.asc("domain"));
		List<String> domains = this.findAllByCriteria(userCriteria);
		return domains;
	}
}
