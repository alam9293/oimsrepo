package com.cdgtaxi.ibs.acl.business;

import java.util.List;

import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.exception.InvalidPasswordException;
import com.cdgtaxi.ibs.acl.exception.PasswordUsedException;
import com.cdgtaxi.ibs.acl.model.SatbPassword;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class PasswordBusinessImpl extends GenericBusinessImpl implements PasswordBusiness {

	public void changePassword(String loginId, String oldPassword, String newPassword) throws PasswordUsedException, InvalidPasswordException {
		//Encode password first before proceeding to do anything
		ShaPasswordEncoder passwordEncoder = (ShaPasswordEncoder)SpringUtil.getBean("passwordEncoder");
		oldPassword = passwordEncoder.encodePassword(oldPassword, null);
		newPassword = passwordEncoder.encodePassword(newPassword, null);
		
		SatbUser user = (SatbUser) this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		
		//Check whether entered old password same as current user's password
		if(!user.getPassword().equals(oldPassword))
			throw new InvalidPasswordException("Invalid existing password entered");
		
		//Check whether previous used passwords are the same as the new password
		this.checkPasswordUsedBefore(user, newPassword);
		
		//All checks passed, change user's password
		this.createPasswordHistory(user, oldPassword);
		user.setPassword(newPassword);
		this.daoHelper.getGenericDao().update(user);
	}
	
	private void checkPasswordUsedBefore(SatbUser user, String newPassword) throws PasswordUsedException{
		List<SatbPassword> usedPasswords = this.daoHelper.getPasswordDao().getPreviousUsedPasswords(user);
		for(SatbPassword password : usedPasswords){
			if(password.getPassword().equals(newPassword))
				throw new PasswordUsedException("Password has been used in the last "+Constants.PASSWORD_HISTORY+" password change histroy");
		}
	}
	
	private void createPasswordHistory(SatbUser user, String oldPassword){
		SatbPassword password = new SatbPassword();
		password.setSatbUser(user);
		password.setPassword(oldPassword);
		password.setTime(DateUtil.getCurrentTimestamp());
		this.daoHelper.getGenericDao().save(password);
	}
}
