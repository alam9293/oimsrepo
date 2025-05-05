package com.cdgtaxi.ibs.acl.ui.user;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings("unchecked")
public class ViewUserWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewUserWindow.class);
	
	private Label loginLbl, nameLbl, emailLbl, statusLbl, lockedLbl, domainLbl;
	
	//entity for ui to display the values
	private SatbUser user;
	
	public ViewUserWindow(){
		//retreive parameters from request
		Map params = Executions.getCurrent().getArg();
		Long userId = (Long)params.get("userId");
		user = (SatbUser)this.businessHelper.getUserBusiness().getUserWithRoles(userId);
		if(user==null)
			throw new NullPointerException("UserId["+userId+"] not found!"); //This should not happen at all
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		loginLbl.setValue(user.getLoginId());
		nameLbl.setValue(user.getName());
		emailLbl.setValue(user.getEmail());
		statusLbl.setValue(Constants.STATUS_MAP.get(user.getStatus()));
		lockedLbl.setValue(NonConfigurableConstants.BOOLEAN_YN.get(user.getLocked()));
		domainLbl.setValue(user.getDomain());
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
