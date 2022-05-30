package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
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
public class CreateBankBranchWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(CreateBankBranchWindow.class);

	private Textbox branchCodeTB;
	private Textbox branchNameTB;
	private Label bankNameLabel;
	private String bankName;

	public CreateBankBranchWindow() {
		logger.info("New Branch");

		Map params = Executions.getCurrent().getArg();
		bankName =  (String) params.get("bankName");
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		bankNameLabel.setValue(bankName);
		branchCodeTB.focus();
	}

	public void save() throws InterruptedException {
		try {
			String branchCode = branchCodeTB.getValue();
			String branchName = branchNameTB.getValue();
			
			int result = ((CreateBankWindow)this.getPreviousPage()).saveBranch(branchCode, branchName);
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
