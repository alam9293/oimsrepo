package com.cdgtaxi.ibs.acl.ui.role;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;

import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class CreateRoleWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateRoleWindow.class);
	
	public void create() throws InterruptedException{
		logger.info("");
		String name = ((CapsTextbox)this.getFellow("name")).getValue();
		
		if(this.businessHelper.getRoleBusiness().isNameUsed(name))
			throw new WrongValueException(this.getFellow("name"), "Name has been used");
		
		try{
			SatbRole newlyCreatedRole = this.businessHelper.getRoleBusiness().createNewRole(name, getUserLoginIdAndDomain());
			
			//Show result
			Messagebox.show("New role "+name+" created", "Create role", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
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
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
