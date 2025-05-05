package com.cdgtaxi.ibs.admin.ui;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class CreateAcquirerWindow extends CommonWindow implements AfterCompose {
	
	private CapsTextbox nameTextBox;
	
	public void createAcquirer() throws InterruptedException{
		try{
			this.displayProcessing();
			
			String name = nameTextBox.getValue();

			MstbAcquirer newAcquirer = new MstbAcquirer();
			newAcquirer.setName(name);
			
			if(this.businessHelper.getGenericBusiness().isExists(newAcquirer))
				throw new WrongValueException(nameTextBox, "Name has been used.");
			
			this.businessHelper.getGenericBusiness().save(newAcquirer, getUserLoginIdAndDomain());
			Messagebox.show("New acquirer created.", "Create Acquirer", Messagebox.OK, Messagebox.INFORMATION);

			//Executions.getCurrent().sendRedirect("");
			this.back();
		}
		catch(WrongValueException wve){
			throw wve;
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
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
	}
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
