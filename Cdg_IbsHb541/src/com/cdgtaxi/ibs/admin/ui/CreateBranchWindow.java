package com.cdgtaxi.ibs.admin.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateCodeError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;

@SuppressWarnings("serial")
public class CreateBranchWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateBranchWindow.class);

	private final MstbBankMaster bank;
	private final MstbBranchMaster branch;

	private Textbox branchCodeTB;
	private Textbox branchNameTB;

	@SuppressWarnings("unchecked")
	public CreateBranchWindow() {
		logger.info("New Branch");

		Map params = Executions.getCurrent().getArg();
		Integer bankNo =  (Integer) params.get("bankNo");
		bank = businessHelper.getAdminBusiness().getBank(bankNo);

		branch = new MstbBranchMaster();
		branch.setMstbBankMaster(bank);
	}

	public void onCreate() {
		Label bankNameLabel = (Label) getFellow("bankNameLabel");
		bankNameLabel.setValue(bank.getBankName());

		branchCodeTB = (Textbox) getFellow("branchCodeTB");
		branchNameTB = (Textbox) getFellow("branchNameTB");

		branchCodeTB.focus();
	}

	public void save() throws InterruptedException {
		try {
			branch.setBranchCode(branchCodeTB.getValue());
			branch.setBranchName(branchNameTB.getValue());
			
			businessHelper.getAdminBusiness().saveBankBranch(branch, getUserLoginIdAndDomain());
			Messagebox.show(
					"Branch has been successfully saved.", "Save Branch",
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
