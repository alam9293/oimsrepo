package com.cdgtaxi.ibs.acl.ui.user;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.acl.exception.InvalidPasswordException;
import com.cdgtaxi.ibs.acl.exception.PasswordUsedException;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ChangePasswordWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ChangePasswordWindow.class);
	
	public void changePassword() throws InterruptedException{
		logger.info("");
		
		String oldPassword = ((Textbox)this.getFellow("existingPassword")).getValue();
		String newPassword = ((Textbox)this.getFellow("newPassword")).getValue();
		
		if(oldPassword.equals(newPassword))
			throw new WrongValueException(this.getFellow("newPassword"), "* New password cannot be the same as existing password");
		
		try{
			this.businessHelper.getPasswordBusiness().changePassword(null, oldPassword, newPassword);
			
			//Show result
			Messagebox.show("Password has been changed", "Change Password", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		}
		catch(PasswordUsedException pue){
			Messagebox.show(pue.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			pue.printStackTrace();
		}
		catch(InvalidPasswordException ipe){
			Messagebox.show(ipe.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
			ipe.printStackTrace();
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
