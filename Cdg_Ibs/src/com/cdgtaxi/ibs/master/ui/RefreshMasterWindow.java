package com.cdgtaxi.ibs.master.ui;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.acl.security.CustomFilterInvocationDefinitionSource;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class RefreshMasterWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	public void refreshConfigurables() throws InterruptedException{
		ConfigurableConstants constants = (ConfigurableConstants)SpringUtil.getBean("configurableConstants");
		constants.refresh();
		Messagebox.show("Configurable Constants refreshed", "Master", Messagebox.OK, Messagebox.INFORMATION);
	}
	public void refreshMasterSetup() throws InterruptedException{
		MasterSetup masterSetup = (MasterSetup)SpringUtil.getBean("masterSetup");
		for(String masterName : masterSetup.getMasterManagers().keySet()){
			masterSetup.getMasterManagers().get(masterName).refresh();
		}
		Messagebox.show("Master Setups refreshed", "Master", Messagebox.OK, Messagebox.INFORMATION);
	}
	public void refreshACLAccessRights() throws InterruptedException{
		//Reload RequestMap
		CustomFilterInvocationDefinitionSource definitionSource =
			(CustomFilterInvocationDefinitionSource) SpringUtil.getBean("objectDefinitionSource");
		definitionSource.reload();
		Messagebox.show("ACL Access Rights refreshed", "Master", Messagebox.OK, Messagebox.INFORMATION);
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}