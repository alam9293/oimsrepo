package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
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
public class EditGLBankWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditGLBankWindow.class);

	private FmtbBankCode bank;
	private Combobox entityCB, taxTypeCB;
	private CapsTextbox bankCodeTB, bankNameTB, branchCodeTB, branchNameTB, costCentreTB, bankAcctNoTB, bankAcctNameTB;
	private Intbox glCodeIB;
	private Checkbox isDefaultCB, isDefaultGB;
	private Datebox effectiveDateDB;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	  			  lastUpdatedByLabel, lastUpdatedDateLabel, 
	              lastUpdatedTimeLabel;

	@SuppressWarnings("unchecked")
	public EditGLBankWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer bankCodeNo =  (Integer) params.get("bankCodeNo");
		logger.info("Bank Code No = " + bankCodeNo);
		bank = businessHelper.getAdminBusiness().getGLBank(bankCodeNo);
	}
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		// populate Tax Type list
		Set<Entry<String, String>> entries =
			ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE).entrySet();
		for(Entry<String, String> entry : entries){
			taxTypeCB.appendChild(newComboitem(entry.getKey(), entry.getValue()));
			if(bank.getMstbMasterTable().getMasterCode().equals(entry.getKey()))
				taxTypeCB.setSelectedItem((Comboitem) taxTypeCB.getLastChild());
		}
		
		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Comboitem item = newComboitem(entity, entity.getEntityName());
			entityCB.appendChild(item);
			if (bank.getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo())) {
				entityCB.setSelectedItem(item);
			}
		}
		
		bankCodeTB.setValue(bank.getBankCode());
		bankNameTB.setValue(bank.getBankName());
		branchCodeTB.setValue(bank.getBranchCode());
		branchNameTB.setValue(bank.getBranchName());
		glCodeIB.setValue(new Integer(bank.getGlCode()));
		costCentreTB.setValue(bank.getCostCentre());
		isDefaultCB.setChecked(bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES));
		effectiveDateDB.setValue(bank.getEffectiveDate());
		bankAcctNoTB.setValue(bank.getBankAcctNo());
		bankAcctNameTB.setValue(bank.getBankAcctName());
		isDefaultGB.setChecked(bank.getIsDefaultGiroBank().equals(NonConfigurableConstants.BOOLEAN_YN_YES));
		
		if(bank.getCreatedDt()!=null)createdByLabel.setValue(bank.getCreatedBy());
		else createdByLabel.setValue("-");
		if(bank.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(bank.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(bank.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(bank.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(bank.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(bank.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(bank.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(bank.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(bank.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(bank.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	
	@Override
	public void refresh() throws InterruptedException {
	}
	
	public void update() throws InterruptedException {
		try {
			entityCB.getValue();

			bank.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());
			bank.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.TAX_TYPE, (String)taxTypeCB.getSelectedItem().getValue()));
			bank.setBankCode(bankCodeTB.getValue());
			bank.setBankName(bankNameTB.getValue());
			bank.setBranchCode(branchCodeTB.getValue());
			bank.setBranchName(branchNameTB.getValue());
			bank.setGlCode(glCodeIB.getValue().toString());
			bank.setCostCentre(costCentreTB.getValue());
			bank.setIsDefault(isDefaultCB.isChecked() ? NonConfigurableConstants.BOOLEAN_YN_YES : NonConfigurableConstants.BOOLEAN_YN_NO);
			bank.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDateDB.getValue()));
			bank.setBankAcctNo(bankAcctNoTB.getValue());
			bank.setIsDefaultGiroBank(isDefaultGB.isChecked() ? NonConfigurableConstants.BOOLEAN_YN_YES : NonConfigurableConstants.BOOLEAN_YN_NO);
			bank.setBankAcctName(bankAcctNameTB.getValue());
			
			FmtbBankCode defaultGiroBank = null;
			if(bank.getIsDefaultGiroBank().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				defaultGiroBank = businessHelper.getAdminBusiness().checkDefaultGiroBankExist(bank.getEffectiveDate(), bank.getBankCodeNo());
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
			
			if(bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				FmtbBankCode tempBank = businessHelper.getAdminBusiness().checkDefaultCollectionBankExist(
						bank.getFmtbEntityMaster().getEntityNo(), 
						bank.getEffectiveDate(), 
						bank.getBankCodeNo());
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
			
			businessHelper.getAdminBusiness().updateGLBank(bank, defaultGiroBank, getUserLoginIdAndDomain());
			MasterSetup.getBankManager().refresh();
			Messagebox.show(
					"Bank has been successfully saved.", "Save Bank",
					Messagebox.OK, Messagebox.INFORMATION);
			
			back();
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
}
