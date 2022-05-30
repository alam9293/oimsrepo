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

import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateCodeError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditBranchWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditBranchWindow.class);

	private final MstbBankMaster bank;
	private final MstbBranchMaster branch;

	private Textbox branchCodeTB;
	private Textbox branchNameTB;
	
	private Label bankNameLabel;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	@SuppressWarnings("unchecked")
	public EditBranchWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer branchNo =  (Integer) params.get("branchNo");
		logger.info("Branch No = " + branchNo);
		branch = businessHelper.getAdminBusiness().getBankBranch(branchNo);
		bank = branch.getMstbBankMaster();
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		bankNameLabel.setValue(bank.getBankName());
		branchCodeTB.setValue(branch.getBranchCode());
		branchNameTB.setValue(branch.getBranchName());

		branchCodeTB.focus();
		
		if(branch.getCreatedDt()!=null)createdByLabel.setValue(branch.getCreatedBy());
		else createdByLabel.setValue("-");
		if(branch.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(branch.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(branch.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(branch.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(branch.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(branch.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(branch.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(branch.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(branch.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(branch.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	public void update() throws InterruptedException {
		try {
			branch.setBranchCode(branchCodeTB.getValue());
			branch.setBranchName(branchNameTB.getValue());
			businessHelper.getAdminBusiness().updateBankBranch(branch, getUserLoginIdAndDomain());
			Messagebox.show(
					"Branch has been successfully saved.", "Save Branch Detail",
					Messagebox.OK, Messagebox.INFORMATION);
			
			back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateNameError dne){
			throw new WrongValueException(branchNameTB, dne.getMessage());
		}
		catch (DuplicateCodeError dce){
			throw new WrongValueException(branchCodeTB, dce.getMessage());
		}
		catch (DataValidationError ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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
