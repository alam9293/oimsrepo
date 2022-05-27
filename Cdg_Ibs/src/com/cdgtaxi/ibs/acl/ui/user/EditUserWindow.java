package com.cdgtaxi.ibs.acl.ui.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ad.ADServiceException;
import com.cdgtaxi.ad.ComfortDelgroADService;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;

public class EditUserWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditUserWindow.class);
	private Combobox domainCB;
	
	//entity for ui to display the values
	private SatbUser user;
	
	@SuppressWarnings("rawtypes")
	public void afterCompose() {
		Components.wireVariables(this, this);

		Map adProperties = (Map) SpringUtil.getBean("adProperties");
		String disableAD = (String) adProperties.get("ad.disable");

		if (disableAD == null || !disableAD.equals("true")) {
			String[] domains = new String[]{};
			try {
				domains = ComfortDelgroADService.getAllSupportedDomains();
			} catch (ADServiceException e) {
				e.printStackTrace();
			}
			boolean found = false;
			for (String domain : domains) {
				Comboitem item = new Comboitem();
				item.setLabel(domain);
				item.setValue(domain);
				domainCB.appendChild(item);
				if(domain.equals(user.getDomain())){
					domainCB.setSelectedItem(item);
					found = true;
				}
			}
			
			if(!found){
				Comboitem item = new Comboitem();
				item.setLabel(user.getDomain());
				item.setValue(user.getDomain());
				domainCB.appendChild(item);
				domainCB.setSelectedItem(item);
			}
		} else {
			Comboitem item = new Comboitem();
			item.setLabel("TestDomain");
			item.setValue("TestDomain");
			domainCB.appendChild(item);
		}

		if (domainCB.getChildren().size() == 1)
			domainCB.setSelectedIndex(0);
	}
	
	public EditUserWindow(){
		//retreive parameters from request
		Map params = Executions.getCurrent().getArg();
		Long userId = (Long)params.get("userId");
		user = (SatbUser)this.businessHelper.getUserBusiness().getUserWithRoles(userId);
		if(user==null)
			throw new NullPointerException("UserId["+userId+"] not found!"); //This should not happen at all
	}
	
	public void save() throws InterruptedException{
		logger.info("");
		String name = ((CapsTextbox)this.getFellow("name")).getValue();
		String email = ((Textbox)this.getFellow("email")).getValue();
		Listbox roleListBox = (Listbox)this.getFellow("role");
		RequiredConstraint.validate(domainCB);
		String domain = (String) domainCB.getSelectedItem().getValue();
		
		//Validations on the inputs before saving
		if(!user.getDomain().equals(domain))
			if(this.businessHelper.getUserBusiness().isLoginIdAndDomainUsed(user.getLoginId(), domain))
				throw new WrongValueException(this.getFellow("loginId"), "LoginId already existed in the specified domain");
		if(roleListBox.getSelectedCount()==0)
			throw new WrongValueException(roleListBox, "Mandatory field");
		
		try{
			Set<Listitem> selectedItems = roleListBox.getSelectedItems();
			Set<SatbRole> selectedRoles = new HashSet<SatbRole>();
			
			for(Listitem item : selectedItems){
				selectedRoles.add((SatbRole)item.getValue());
			}
			
			user.setSatbRoles(selectedRoles);
			user.setName(name);
			user.setEmail(email);
			user.setDomain(domain);
			
			this.businessHelper.getGenericBusiness().update(user, getUserLoginIdAndDomain());
			Messagebox.show("Changes saved", "Edit User", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void unlockAccount() throws InterruptedException{
		logger.info("");
		
		try{
			int response = Messagebox.show("Are you sure you want to unlock the user?", "Unlock User", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
			if(response == Messagebox.YES) this.businessHelper.getUserBusiness().unlockAccount(user);
			Messagebox.show("User has been unlocked", "Unlock User", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void activateAccount() throws InterruptedException{
		logger.info("");
		
		try{
			int response = Messagebox.show("Are you sure you want to activate \nthe user?", "Activate User", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
			if(response == Messagebox.YES) 
			{
				this.businessHelper.getUserBusiness().activateAccount(user);
				Messagebox.show("User has been activated", "Activate User", Messagebox.OK, Messagebox.INFORMATION);
			}
			this.back();
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void deactivateAccount() throws InterruptedException{
		logger.info("");
		
		try{
			int response = Messagebox.show("Are you sure you want to deactivate \nthe user?", "Deactivate User", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
			if(response == Messagebox.YES) 
			{
				this.businessHelper.getUserBusiness().deactivateAccount(user);
				Messagebox.show("User has been deactivated", "Deactivate User", Messagebox.OK, Messagebox.INFORMATION);
			}
			this.back();
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void resetPassword() throws InterruptedException{
		logger.info("");
		
		try{
			int response = Messagebox.show("Are you sure you want to reset the user's password?", "Reset Password", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
			if(response == Messagebox.YES) this.businessHelper.getUserBusiness().resetPassword(null,user);
			Messagebox.show("User's password has been resetted. An email has sent to user.", 
					"Reset Password", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void populateRole(Listbox listBox){
		List<SatbRole> roles = (List<SatbRole>)this.businessHelper.getRoleBusiness().getRoles();
		Set<SatbRole> userRoles = user.getSatbRoles();
		
		listBox.setRows(roles.size());
		for(SatbRole role : roles){
			Listitem item = new Listitem(role.getName());
			item.setValue(role);
			if(role.getStatus().equals(Constants.ROLE_STATUS_INACTIVE)) item.setDisabled(true);
			if(userRoles.contains(role)) item.setSelected(true);
			listBox.appendChild(item);
		}
	}
	
	public SatbUser getUser(){
		return user;
	}

	@Override
	public void refresh() {
		
	}
}
