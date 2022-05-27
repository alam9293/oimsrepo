package com.cdgtaxi.ibs.acl.business;

import java.util.List;
import java.util.Set;

import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.acl.security.PassPhrase;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.EmailUtil;

public class UserBusinessImpl extends GenericBusinessImpl implements UserBusiness {

	public UserDetails loadUserByUsernameAndDomain(String loginId, String domain){
		return this.daoHelper.getUserDao().loadUserByUsernameAndDomain(loginId, domain);
	}
	
	/**
	 * To get user together with role details pulled out.
	 * @param userId Primary key
	 * @return AclUser Entity with AclRole loaded
	 */
	public SatbUser getUserWithRoles(Long userId){
		return this.daoHelper.getUserDao().getUserWithRoles(userId);
	}
	
	public List searchUser(String loginId, String domain, String name, String email, String status, String locked){
		return this.daoHelper.getUserDao().searchUser(loginId, domain, name, email, status, locked);
	}
	
	public void unlockAccount(SatbUser user){
		user.setLocked(Constants.BOOLEAN_NO);
		this.daoHelper.getGenericDao().update(user);
	}
	
	public void activateAccount(SatbUser user){
		user.setStatus(Constants.STATUS_ACTIVE);
		this.daoHelper.getGenericDao().update(user);
	}
	
	public void deactivateAccount(SatbUser user){
		user.setStatus(Constants.STATUS_INACTIVE);
		this.daoHelper.getGenericDao().update(user);
	}
	
	public boolean isLoginIdAndDomainUsed(String loginId, String domain){
		return this.daoHelper.getUserDao().isLoginIdAndDomainUsed(loginId, domain);
	}
	
	public SatbUser createNewUser(String loginId, String domain, Set<Component> selectedRoles, String name, String email, String createdBy){
		
		SatbUser newUser = new SatbUser();
		newUser.setLoginId(loginId);
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setDomain(domain);
		
		//Retrieve the selected roles and tie to user
		for(Component component : selectedRoles){
			SatbRole role = (SatbRole)this.daoHelper.getGenericDao().get(SatbRole.class, ((SatbRole)component.getAttribute("role")).getRoleId());
			newUser.getSatbRoles().add(role);
		}
		
		//default user status
		//newUser.setStatus(Constants.STATUS_NEW);
		newUser.setStatus(Constants.STATUS_ACTIVE);
		newUser.setLocked(Constants.BOOLEAN_NO);
		
		//Encode password
//		String generatedPassword = PassPhrase.getNext();
//		ShaPasswordEncoder passwordEncoder = (ShaPasswordEncoder)SpringUtil.getBean("passwordEncoder");
//		newUser.setPassword(passwordEncoder.encodePassword(generatedPassword, null));
		
		//Save the newUser
		this.daoHelper.getGenericDao().save(newUser, createdBy);
		
		//Fire out email to notify user
//		String subject = "Account created";
//		String content = "Your account has been created.\n"+
//							"You are required to change your password upon your next login.\n\n"+
//							"\tLogin ID: "+loginId+"\n"+
//							"\tPassword: "+generatedPassword+"\n\n"+
//							"Regards,\n"+
//							"System";
//		String to = newUser.getEmail();
//		EmailUtil.sendEmail(to, subject, content);
		
		return newUser;
	}
	
	public void resetPassword(String loginId, SatbUser user){
		//Retrieve information of the admin that reset the selected user's password
		SatbUser admin = (SatbUser)this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		
		//Generate random password
		String newPassword = PassPhrase.getNext();
		
		//Hash and override existing password
		ShaPasswordEncoder passwordEncoder = (ShaPasswordEncoder)SpringUtil.getBean("passwordEncoder");
		user.setPassword(passwordEncoder.encodePassword(newPassword, null));
//		user.setStatus(Constants.STATUS_NEW);
		this.daoHelper.getGenericDao().update(user);
		
		//Fire out email to notify user
		String subject = "Password Reset";
		String content = "Your password has been resetted.\n"+
							"You are required to change your password upon your next login.\n\n"+
							"\tNew password: "+newPassword+"\n\n"+
							"Regards,\n"+
							admin.getName();
		String from = admin.getEmail();
		String to = user.getEmail();
		EmailUtil.sendEmail(from, to, subject, content);
	}
	
//	public SatbUser getUser(String loginId){
//		return this.daoHelper.getUserDao().getUser(loginId);
//	}
	
	public List<String> getDomains(){
		return this.daoHelper.getUserDao().getDomains();
	}
}
