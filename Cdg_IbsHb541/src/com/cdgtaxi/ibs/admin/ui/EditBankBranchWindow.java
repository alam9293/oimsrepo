package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings({"serial", "unchecked"})
public class EditBankBranchWindow extends CommonWindow implements AfterCompose{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(EditBankBranchWindow.class);

	private Label bankNameLabel;
	private Textbox branchCodeTB;
	private Textbox branchNameTB;
	
	private String bankName;
	private String previousBranchCode;
	private String previousBranchName;

	public EditBankBranchWindow() {
		Map params = Executions.getCurrent().getArg();
		bankName = (String) params.get("bankName");
		previousBranchCode = (String) params.get("branchCode");
		previousBranchName = (String) params.get("branchName");
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		bankNameLabel.setValue(bankName);
		branchCodeTB.setValue(previousBranchCode);
		branchNameTB.setValue(previousBranchName);

		branchCodeTB.focus();
	}

	public void update() throws InterruptedException {
		try {
			
			int result = ((CreateBankWindow)this.getPreviousPage()).updateBranch(previousBranchCode,
					branchCodeTB.getValue(), branchNameTB.getValue());
			MasterSetup.getBankManager().refresh();
			if(result == 1)
				throw new WrongValueException(branchCodeTB, "Branch Code has been used");
			else if(result == 2)
				throw new WrongValueException(branchNameTB, "Branch Name has been used");
			
			back();
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

	@Override
	public void refresh() throws InterruptedException {
	}
}
