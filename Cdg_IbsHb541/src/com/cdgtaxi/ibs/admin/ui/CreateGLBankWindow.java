package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class CreateGLBankWindow extends CommonWindow implements AfterCompose{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateGLBankWindow.class);

	private Combobox entityCB, taxTypeCB;
	private CapsTextbox bankCodeTB, bankNameTB, branchCodeTB, branchNameTB, costCentreTB, bankAcctNoTB, bankAcctNameTB;
	private Intbox glCodeIB;
	private Checkbox isDefaultCB, isDefaultGB;
	private Datebox effectiveDateDB;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		// populate Tax Type list
		Set<Entry<String, String>> entries =
			ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE).entrySet();
		for(Entry<String, String> entry : entries){
			taxTypeCB.appendChild(newComboitem(entry.getKey(), entry.getValue()));
		}
		
		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity, entity.getEntityName()));
		}

		entityCB.focus();
	}

	public void create() throws InterruptedException {
		try {
			entityCB.getValue();

			FmtbBankCode bankCode = new FmtbBankCode();
			bankCode.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());
			bankCode.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.TAX_TYPE, (String)taxTypeCB.getSelectedItem().getValue()));
			bankCode.setBankCode(bankCodeTB.getValue());
			bankCode.setBankName(bankNameTB.getValue());
			bankCode.setBranchCode(branchCodeTB.getValue());
			bankCode.setBranchName(branchNameTB.getValue());
			bankCode.setGlCode(glCodeIB.getValue().toString());
			bankCode.setCostCentre(costCentreTB.getValue());
			bankCode.setIsDefault(isDefaultCB.isChecked() ? NonConfigurableConstants.BOOLEAN_YN_YES : NonConfigurableConstants.BOOLEAN_YN_NO);
			bankCode.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDateDB.getValue()));
			bankCode.setBankAcctNo(bankAcctNoTB.getValue());
			bankCode.setIsDefaultGiroBank(isDefaultGB.isChecked() ? NonConfigurableConstants.BOOLEAN_YN_YES : NonConfigurableConstants.BOOLEAN_YN_NO);
			bankCode.setBankAcctName(bankAcctNameTB.getValue());
			
			FmtbBankCode defaultGiroBank = null;
			if(bankCode.getIsDefaultGiroBank().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				defaultGiroBank = businessHelper.getAdminBusiness().checkDefaultGiroBankExist(bankCode.getEffectiveDate(), null);
				if(defaultGiroBank!=null){
					if(Messagebox.show(
							"Another bank has already been marked as default giro bank with the same effective date. " +
								"Continue to create and override the default giro bank?", 
							"Create GL Bank", 
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
						return;
					}
				}
			}
			
			if(bankCode.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				FmtbBankCode tempBank = businessHelper.getAdminBusiness().checkDefaultCollectionBankExist(
						bankCode.getFmtbEntityMaster().getEntityNo(), 
						bankCode.getEffectiveDate(), 
						null);
				if(tempBank!=null){
					if(Messagebox.show(
							"Another bank has already been marked as default collection bank with the same effective date. " +
								"Continue to create and override the default collection bank?", 
							"Create GL Bank", 
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
						return;
					}
				}
			}
			
			businessHelper.getAdminBusiness().createGLBank(bankCode, defaultGiroBank, getUserLoginIdAndDomain());
			MasterSetup.getBankManager().refresh();
			Messagebox.show("GL Bank Code has been successfully created", "Create GL Bank Code",
					Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
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
