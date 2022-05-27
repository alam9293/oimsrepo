package com.cdgtaxi.ibs.txn.ui;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;


public class EditTxnWindow extends CommonWindow {
	
	private static Logger logger = Logger.getLogger(EditTxnWindow.class);
	private String txnID = null;
	private List<Listitem> txnStatus = new ArrayList<Listitem>();
	private TmtbAcquireTxn currentAcquireTxn = null;
	private Boolean isCardless = new Boolean (false);
	private boolean isPremierTrips = false;
	
	public EditTxnWindow(){
		// adding all txn status
		txnStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.TXN_STATUS.keySet()){
			txnStatus.add(new Listitem(NonConfigurableConstants.TXN_STATUS.get(statusCode), statusCode));
		}
		Map<String, String> map = Executions.getCurrent().getArg();
		txnID = (String) map.get("txnID");
	}

	@Override
	public void refresh() throws InterruptedException {
		
	}
	
	public List<Listitem> getProductTypes() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getAllProductTypes(), false);
		return cloneList(productTypeList);
	}
	
	public List<Listitem> getPremierProductType() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getPremierProductTypes(), false);
		return cloneList(productTypeList);
	}
	
	
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
		}
		return returnList;
	}
	
	public List<Listitem> getServiceProvider() {
		List<Listitem> serviceProviderList = ComponentUtil.convertToListitems(ConfigurableConstants.getServiceProvider(), false);
		return cloneList(serviceProviderList);
	}

	public List<Listitem> getTripType() {
		List<Listitem> tripTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleTripTypes(), false);
		return cloneList(tripTypeList);
	}
	
	public List<Listitem> getVehicleGroup() {
		List<Listitem> vehicleGroupList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleTypes(), false);
		return cloneList(vehicleGroupList);
	}
	
	public List<Listitem> getJobType() {
		List<Listitem> jobTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getJobType(), false);
		return cloneList(jobTypeList);
	}
	
	public List<Listitem> getVehicleType() {
		List<Listitem> vehicleTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleModel(), false);
		return cloneList(vehicleTypeList);
	}
	
	public void onSelectUpdateFMS() throws InterruptedException{
		logger.info("onSelectUpdateFMS");
		
		try{
			Listbox toUpdateFMSListBox = (Listbox)this.getFellow("toUpdateFMSList");
			String selectedValue = (String) toUpdateFMSListBox.getSelectedItem().getValue();
			
			if (NonConfigurableConstants.BOOLEAN_YES.equals(selectedValue))
			{
				// enable the fields
				((Row)this.getFellow("FMSRow")).setVisible(true);
				((Row)this.getFellow("FMSRow2")).setVisible(true);
				((Row)this.getFellow("FMSRow3")).setVisible(true);
//				if (NonConfigurableConstants.PREMIER_SERVICE.equals(currentAcquireTxn.getPmtbProductType().getProductTypeId())) {
				if(isCardless) {
					((Label)this.getFellow("levyLabel")).setVisible(true);
					((Decimalbox)this.getFellow("levy")).setVisible(true);
				}
				((Decimalbox)this.getFellow("FMSAmount")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroOrGreaterConstraint());
			}
			else
			{
				// disable the fields
				((Row)this.getFellow("FMSRow")).setVisible(false);
				((Row)this.getFellow("FMSRow2")).setVisible(false);
				((Row)this.getFellow("FMSRow3")).setVisible(false);
				((Label)this.getFellow("levyLabel")).setVisible(false);
				((Decimalbox)this.getFellow("levy")).setVisible(false);
				((Decimalbox)this.getFellow("FMSAmount")).setConstraint("");
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void populateDivisionDepartment(AmtbAccount amtbAccount, boolean isPremier, int divAcctNo, int deptAcctNo) throws InterruptedException{
		logger.info("");
		
		try{
			if (isPremier)
			{
				//Display division or department according to account category
				if(amtbAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = this.businessHelper.getTxnBusiness().searchPremierAccounts(amtbAccount);
					if (divisions != null && !divisions.isEmpty())
					{
						this.populateDivision(divisions, divAcctNo);
						// Populate the department list if there is
						//if (deptAcctNo != 0)
						this.populateDepartment(deptAcctNo, isPremier);
					}
				}
			}
			else{
				//Display division or department according to account category
				if(amtbAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = this.businessHelper.getTxnBusiness().searchAccounts(currentAcquireTxn.getPmtbProductType().getProductTypeId(), amtbAccount);
					if (divisions != null && !divisions.isEmpty())
					{
						this.populateDivision(divisions, divAcctNo);
						// Populate the department list if there is
						//if (deptAcctNo != 0)
						this.populateDepartment(deptAcctNo, isPremier);
					}
				}
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void populateSubMainApplicant(AmtbAccount amtbAccount, boolean isPremier, int subApplicantNo) throws InterruptedException{
		logger.info("");
		
		try{
			if (isPremier)
			{
				//Display main applicant and sub applicant
				if(amtbAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
					List<AmtbAccount> subApplicants = this.businessHelper.getTxnBusiness().searchPremierAccounts(amtbAccount);
					if (subApplicants != null && !subApplicants.isEmpty())
					{
						this.populateSubApplicant(subApplicants, subApplicantNo);
					}
				}
			}
			else{
				//Display main applicant and sub applicant
				if(amtbAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
					List<AmtbAccount> subApplicants = this.businessHelper.getTxnBusiness().searchAccounts(currentAcquireTxn.getPmtbProductType().getProductTypeId(), amtbAccount);
					if (subApplicants != null && !subApplicants.isEmpty())
					{
						this.populateSubApplicant(subApplicants, subApplicantNo);
					}
				}
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectDivision() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
			AmtbAccount selectedValue = (AmtbAccount) divisionListBox.getSelectedItem().getValue();
			
			if(selectedValue instanceof AmtbAccount){
				if (isPremierTrips)
				{
					List<AmtbAccount> departments = this.businessHelper.getTxnBusiness().searchPremierAccounts((AmtbAccount) selectedValue);
					logger.info("Account No: " + selectedValue.getAccountNo());
					if (departments != null && !departments.isEmpty())
						this.setDepartmentInputVisible(departments);
					else
						this.setDepartmentInputInvisible();
				}
				else
				{
					List<AmtbAccount> departments = this.businessHelper.getTxnBusiness().searchAccounts(currentAcquireTxn.getPmtbProductType().getProductTypeId(), (AmtbAccount) selectedValue);
					logger.info("Account No: " + selectedValue.getAccountNo());
					if (departments != null && !departments.isEmpty())
						this.setDepartmentInputVisible(departments);
					else
						this.setDepartmentInputInvisible();
				}
			}
			else{
				this.setDepartmentInputInvisible();
				// Populate the necessary information
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectActionTxn() throws InterruptedException{
		logger.info("onSelectActionTxn");
		
		try{
			Listbox actionTxnListBox = (Listbox)this.getFellow("actionTxn");
			String selectedValue = (String) actionTxnListBox.getSelectedItem().getValue();
			
			if (NonConfigurableConstants.TRANSACTION_SCREEN_VOID.equals(selectedValue))
			{
				this.setFieldsDisabled(true);
				
				boolean checkTxnBilled = true;
				if(currentAcquireTxn != null)
				{
					if(currentAcquireTxn.getTxnStatus().equals(NonConfigurableConstants.TRANSACTION_STATUS_BILLED))
						checkTxnBilled = false;
				}
				
				if (NonConfigurableConstants.BOOLEAN_YES.equals(currentAcquireTxn.getPmtbProductType().getOneTimeUsage()) && checkTxnBilled)
				{
					Listbox cancelOTU = (Listbox) this.getFellow("cancelOTU");
					cancelOTU.setVisible(true);
					Div cancelOTUdiv = (Div) this.getFellow("cancelOTUdiv");
					cancelOTUdiv.setVisible(true);
					((Label)this.getFellow("cancelOTULabel")).setVisible(true);
				}
				else
				{
					Listbox cancelOTU = (Listbox) this.getFellow("cancelOTU");
					cancelOTU.setVisible(false);
					Div cancelOTUdiv = (Div) this.getFellow("cancelOTUdiv");
					cancelOTUdiv.setVisible(false);
					((Label)this.getFellow("cancelOTULabel")).setVisible(false);
				}
			}
			else
			{
				this.setFieldsDisabled(false);
				Listbox cancelOTU = (Listbox) this.getFellow("cancelOTU");
				cancelOTU.setVisible(false);
				Div cancelOTUdiv = (Div) this.getFellow("cancelOTUdiv");
				cancelOTUdiv.setVisible(false);
				((Label)this.getFellow("cancelOTULabel")).setVisible(false);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void populateDepartment(int selectedDeptAcctNo, boolean isPremier) throws InterruptedException{
		logger.info("");
		
		try{
			Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
			AmtbAccount selectedValue = (AmtbAccount) divisionListBox.getSelectedItem().getValue();
			
			if (selectedValue != null)
			{
				List<AmtbAccount> departments = null;
				if(isPremier)
				{
					departments = this.businessHelper.getTxnBusiness().searchPremierAccounts((AmtbAccount) selectedValue);	
				}
				else
				{
					departments = this.businessHelper.getTxnBusiness().searchAccounts(currentAcquireTxn.getPmtbProductType().getProductTypeId(), (AmtbAccount) selectedValue);	
				}
				logger.info("Account No: " + selectedValue.getAccountNo());
				((Label)this.getFellow("departmentLabel")).setVisible(true);
				Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
				departmentListBox.setVisible(true);
				departmentListBox.getChildren().clear();
				
				Listitem blankItem = new Listitem("-");
				blankItem.setValue(null);
				departmentListBox.appendChild(blankItem);
				if (selectedDeptAcctNo == 0)
					departmentListBox.setSelectedIndex(0);
				if (departments != null && !departments.isEmpty())
				{

					for(AmtbAccount department : departments){
						Listitem newItem = new Listitem(department.getAccountName()+ " (" + department.getCode() + ")");
						newItem.setValue(department);
						departmentListBox.appendChild(newItem);
						if (department.getAccountNo() == selectedDeptAcctNo)
						{
							newItem.setSelected(true);
						}
					}
				}	
			}
			else{
				this.setDepartmentInputInvisible();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void populateSubApplicant(List<AmtbAccount> subApplicants, int selectedSubApplicantNo) throws InterruptedException{
		((Row)this.getFellow("subApplicantRow")).setVisible(true);
		((Label)this.getFellow("subApplicantLabel")).setVisible(true);
		Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicantListBox");
		subApplicantListBox.setVisible(true);
		subApplicantListBox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		subApplicantListBox.appendChild(blankItem);
		if (selectedSubApplicantNo == 0)
			subApplicantListBox.setSelectedIndex(0);
		for(AmtbAccount subApplicant : subApplicants){
			Listitem newItem = new Listitem(subApplicant.getAccountName());
			newItem.setValue(subApplicant);
			subApplicantListBox.appendChild(newItem);
			if (subApplicant.getAccountNo() == selectedSubApplicantNo)
			{
				newItem.setSelected(true);
			}
		}
	}
	
	private void populateDivision(List<AmtbAccount> divisions, int selectedDivAcctNo) throws InterruptedException{
		((Row)this.getFellow("divisionDepartmentRow")).setVisible(true);
		((Label)this.getFellow("divisionLabel")).setVisible(true);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.setVisible(true);
		divisionListBox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		divisionListBox.appendChild(blankItem);
		if (selectedDivAcctNo == 0)
			divisionListBox.setSelectedIndex(0);
		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+ " (" + division.getCode() + ")");
			newItem.setValue(division);
			divisionListBox.appendChild(newItem);
			if (division.getAccountNo() == selectedDivAcctNo)
			{
				newItem.setSelected(true);
			}
		}
	}
	
	private void setDepartmentInputVisible(List<AmtbAccount> departments){
		((Label)this.getFellow("departmentLabel")).setVisible(true);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.setVisible(true);
		departmentListBox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		departmentListBox.appendChild(blankItem);
		for(AmtbAccount department : departments){
			Listitem newItem = new Listitem(department.getAccountName()+ " (" + department.getCode() + ")");
			newItem.setValue(department);
			departmentListBox.appendChild(newItem);
		}
	}
	
	private void setDepartmentInputInvisible(){
		((Label)this.getFellow("departmentLabel")).setVisible(false);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.setVisible(false);
		departmentListBox.getChildren().clear();
	}
	
	private Date combineDateHrMinSec(Date startDt, int hr, int min, int sec)
	{
		Calendar calendar = Calendar.getInstance();
		if (startDt != null)
		{
			calendar.setTime(startDt);
		}
		else
		{
			return null;
		}
		
		calendar.add(Calendar.HOUR_OF_DAY, hr);
		calendar.add(Calendar.MINUTE, min);
		calendar.add(Calendar.SECOND, sec);
		return calendar.getTime();
	}
	
	public void save() throws InterruptedException{
		logger.info("save");	

		try{
			//set all to caps
			if(((CapsTextbox)this.getFellow("appRemarks")).getValue() != null)
				((CapsTextbox)this.getFellow("appRemarks")).setValue(((CapsTextbox)this.getFellow("appRemarks")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("remarks")).getValue() != null)
				((CapsTextbox)this.getFellow("remarks")).setValue(((CapsTextbox)this.getFellow("remarks")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("pickup")).getValue() != null)
				((CapsTextbox)this.getFellow("pickup")).setValue(((CapsTextbox)this.getFellow("pickup")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("destination")).getValue() != null)
				((CapsTextbox)this.getFellow("destination")).setValue(((CapsTextbox)this.getFellow("destination")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("surchargeDesc")).getValue() != null)
				((CapsTextbox)this.getFellow("surchargeDesc")).setValue(((CapsTextbox)this.getFellow("surchargeDesc")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("projCode")).getValue() != null)
				((CapsTextbox)this.getFellow("projCode")).setValue(((CapsTextbox)this.getFellow("projCode")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("projCodeReason")).getValue() != null)
				((CapsTextbox)this.getFellow("projCodeReason")).setValue(((CapsTextbox)this.getFellow("projCodeReason")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("bookedRef")).getValue() != null)
				((CapsTextbox)this.getFellow("bookedRef")).setValue(((CapsTextbox)this.getFellow("bookedRef")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("flightInfo")).getValue() != null)
				((CapsTextbox)this.getFellow("flightInfo")).setValue(((CapsTextbox)this.getFellow("flightInfo")).getValue().toUpperCase());
			if(((CapsTextbox)this.getFellow("bookedBy")).getValue() != null)
				((CapsTextbox)this.getFellow("bookedBy")).setValue(((CapsTextbox)this.getFellow("bookedBy")).getValue().toUpperCase());
			
			Listbox actionTxnListbox = (Listbox) this.getFellow("actionTxn");
			if (actionTxnListbox.getSelectedItem() != null)
			{
				if ("".equals(actionTxnListbox.getSelectedItem().getValue()))
				{
					Messagebox.show("Action to Transaction is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Action to Transaction is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
					
			Listbox toUpdateFMSListBox = (Listbox)this.getFellow("toUpdateFMSList");
			if (toUpdateFMSListBox.getSelectedItem() != null)
			{
				String selectedValue = (String) toUpdateFMSListBox.getSelectedItem().getValue();
			
				if (NonConfigurableConstants.BOOLEAN_YES.equals(selectedValue))
				{
					Listbox updateFMSListbox = (Listbox) this.getFellow("updateFMSList");
					if (updateFMSListbox.getSelectedItem() != null)
					{
						if ("".equals(updateFMSListbox.getSelectedItem().getValue()))
						{
							Messagebox.show("Collect/Refund is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
					else
					{
						Messagebox.show("Collect/Refund is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
					
					// Retrieve the actual edited date
					Datebox startDateBox = (Datebox) this.getFellow("startDate");
					Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
					Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
					Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
					Timestamp startDate = null;
					if (startDateBox != null && startDateBox.getValue() != null)
					{
						Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
								Integer.parseInt(startTimeMin.getSelectedItem().getLabel()),Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
						startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
					}
					
					// Retrieve the actual edited date
					Datebox endDateBox = (Datebox) this.getFellow("endDate");
					Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
					Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
					Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
					Timestamp endDate = null;
					if (endDateBox != null && endDateBox.getValue() != null)
					{
						Date endDateWithTime = combineDateHrMinSec(endDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()),
								Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
						endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
					}
					
					String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(currentAcquireTxn.getTaxiNo(), currentAcquireTxn.getNric(), startDate, endDate);
					if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg))
					{
						Messagebox.show("Interface Error to FMS - " + "Driver Association Not Valid", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
			else
			{
				Messagebox.show("Update FMS is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox tripType = (Listbox) this.getFellow("tripType");
			if (tripType.getSelectedItem() != null)
			{
				if ("".equals(tripType.getSelectedItem().getValue()))
				{
					Messagebox.show("Trip Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Trip Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			boolean isUpdated = false;
			// if premier, call updatePremierTxn, if non-premier call updateTxn
//			if (NonConfigurableConstants.PREMIER_SERVICE.equals(currentAcquireTxn.getPmtbProductType().getProductTypeId()))
			if(isCardless)
			{
				Listbox companyCdListbox = (Listbox) this.getFellow("companyCd");
				if (companyCdListbox.getSelectedItem() != null)
				{
					if ("".equals(companyCdListbox.getSelectedItem().getValue()))
					{
						Messagebox.show("Company Code is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Company Code is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
								
				Listbox vehicleGroup = (Listbox) this.getFellow("vehicleGroup");
				if (vehicleGroup.getSelectedItem() != null)
				{
					if ("".equals(vehicleGroup.getSelectedItem().getValue()))
					{
						Messagebox.show("Vehicle Group is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Vehicle Group is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				// Checking for non-premier service. Note that premier service and non-premier
				// services hold 2 different type of listbox
				Listbox jobType = (Listbox) this.getFellow("premJobType");
				if (jobType.getSelectedItem() != null)
				{
					if ("".equals(jobType.getSelectedItem().getValue()))
					{
						Messagebox.show("Job Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Job Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				Listbox vehicleType = (Listbox) this.getFellow("premVehicleType");
				if (vehicleType.getSelectedItem() != null)
				{
					if ("".equals(vehicleType.getSelectedItem().getValue()))
					{
						Messagebox.show("Vehicle Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Vehicle Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				Map<String, String> txnDetails = this.getPremierTxnDetails();
				
				// Validation - Closed account cannot create transaction
				if (this.businessHelper.getTxnBusiness().isAccountClosed(txnDetails.get("acctNo")))
				{
					Messagebox.show("Unable to modify trip as the account is closed", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				// Validation - Start Date cannot be later than end date
				Timestamp tripStartTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.TRIPS_DATE_FORMAT);
				Timestamp tripEndTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.TRIPS_DATE_FORMAT);
				
				if (tripStartTimeStamp.after(tripEndTimeStamp))
				{
					Messagebox.show("Trip Start Date cannot be earlier than Trip End Date", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				isUpdated = this.businessHelper.getTxnBusiness().updateTxn(txnDetails, currentAcquireTxn);

			}
			else
			{
				// Checking for non-premier service. Note that premier service and non-premier
				// services hold 2 different type of listbox
				Listbox jobType = (Listbox) this.getFellow("jobType");
				if (jobType.getSelectedItem() != null)
				{
					if ("".equals(jobType.getSelectedItem().getValue()))
					{
						Messagebox.show("Job Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Job Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
				if (vehicleType.getSelectedItem() != null)
				{
					if ("".equals(vehicleType.getSelectedItem().getValue()))
					{
						Messagebox.show("Vehicle Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Vehicle Type is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				Listbox companyCdListbox = (Listbox) this.getFellow("companyCd");
				if (companyCdListbox.getSelectedItem() != null)
				{
					if ("".equals(companyCdListbox.getSelectedItem().getValue()))
					{
						Messagebox.show("Company Code is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
				else
				{
					Messagebox.show("Company Code is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				Map<String, String> txnDetails = this.getTxnDetails();
				
				// Validation - Closed account cannot create transaction
				if (this.businessHelper.getTxnBusiness().isAccountClosed(txnDetails.get("acctNo")))
				{
					Messagebox.show("Unable to modify trip as the account is closed", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				// Validation - Start Date cannot be later than end date
				Timestamp tripStartTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.TRIPS_DATE_FORMAT);
				Timestamp tripEndTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.TRIPS_DATE_FORMAT);
				
				if (tripStartTimeStamp.after(tripEndTimeStamp))
				{
					Messagebox.show("Trip Start Date cannot be earlier than Trip End Date", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
//				if(checkProjectCode(txnDetails) && (txnDetails.get("projCodeReason") == null || txnDetails.get("projCodeReason").trim().equals("")))
//				{
//					Messagebox.show("Trip Code Reason is required", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
//					return;
//				}
				
				isUpdated = this.businessHelper.getTxnBusiness().updateTxn(txnDetails, currentAcquireTxn);
			}
			
			//before show message, disabled the edit button to avoid user create duplicate records. 
			Button submitButton = (Button)this.getFellow("submit");
			submitButton.setDisabled(true);
			if(isUpdated)
			{
				Messagebox.show("Trip is updated", "Update Trip", Messagebox.OK, Messagebox.INFORMATION);
				
			}else{
				Messagebox.show("Unable to save trip. Please try again later", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
			}
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
		catch (WrongValueException wve)
		{
			throw wve;
		}
		catch (Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
	}
	
	protected Map<String, String> getTxnDetails(){
		Map<String, String> txnDetails = new HashMap<String, String>();
		
		txnDetails.put("userId", getUserLoginIdAndDomain());
		txnDetails.put("actionTxn", (String)((Listbox)this.getFellow("actionTxn")).getSelectedItem().getValue());

		Listbox cancelOTUListBox = (Listbox)this.getFellow("cancelOTU");
		if(cancelOTUListBox.getSelectedItem()!=null)
		{
			if(cancelOTUListBox.getSelectedItem().getValue() != null)
				txnDetails.put("cancelOTU", (String) cancelOTUListBox.getSelectedItem().getValue());
		}
		// Set txn status
		txnDetails.put("txnStatus", ((Label)this.getFellow("txnStatus")).getValue());
		//selected account
		String acctNo = ((Label)this.getFellow("acctNo")).getValue();
		String name = ((Label)this.getFellow("name")).getValue();
		txnDetails.put("acctNo", acctNo);
		txnDetails.put("name", name);
		//division
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		if(divisionListBox.getSelectedItem()!=null)
		{
			if(divisionListBox.getSelectedItem().getValue() instanceof AmtbAccount && divisionListBox.getSelectedItem().getValue()!= null)
				txnDetails.put("division", ((AmtbAccount) divisionListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		//department
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		if(departmentListBox.getSelectedItem()!=null)
		{
			if(departmentListBox.getSelectedItem().getValue() instanceof AmtbAccount && departmentListBox.getSelectedItem().getValue() != null)
				txnDetails.put("department", ((AmtbAccount) departmentListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		// Sub applicant
		Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicantListBox");
		if(subApplicantListBox.getSelectedItem()!=null){
			if(subApplicantListBox.getSelectedItem().getValue() instanceof AmtbAccount && subApplicantListBox.getSelectedItem().getValue() != null)
				txnDetails.put("subApplicant", ((AmtbAccount) subApplicantListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		
		// product type
		txnDetails.put("productType", currentAcquireTxn.getPmtbProductType().getProductTypeId());
		
		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		Timestamp startDate = null;
		if (startDateBox != null && startDateBox.getValue() != null)
		{
			Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(startTimeMin.getSelectedItem().getLabel()),Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
		}
		
		txnDetails.put("startDate", DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));
		
		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		Timestamp endDate = null;
		if (endDateBox != null && endDateBox.getValue() != null)
		{
			Date endDateWithTime = combineDateHrMinSec(endDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
			endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
		}

		txnDetails.put("endDate", DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));

		// Add company Code
		Listbox companyCd = (Listbox) this.getFellow("companyCd");
		if (companyCd.getSelectedItem()!= null && companyCd.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("companyCd", (String) companyCd.getSelectedItem().getValue());
		}
		
		// Job Type
		Listbox jobType = (Listbox) this.getFellow("jobType");
		if (jobType.getSelectedItem()!= null && jobType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("jobType", (String) jobType.getSelectedItem().getValue());
		}
		
		// Vehicle Type
		Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
		if (vehicleType.getSelectedItem()!= null && vehicleType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("vehicleType", (String) vehicleType.getSelectedItem().getValue());
		}
		
		String toUpdateFMS = null;
		Listbox toUpdateFMSList = (Listbox) this.getFellow("toUpdateFMSList");
		if (toUpdateFMSList.getSelectedItem()!= null && toUpdateFMSList.getSelectedItem().getValue() !=null) {
			toUpdateFMS = (String)toUpdateFMSList.getSelectedItem().getValue();
			txnDetails.put("toUpdateFMSList", toUpdateFMS);
		}
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(toUpdateFMS)) {
			
			Listbox updateFMSList = (Listbox) this.getFellow("updateFMSList");
			if (updateFMSList.getSelectedItem()!= null && updateFMSList.getSelectedItem().getValue() !=null)
				txnDetails.put("updateFMSList", (String) updateFMSList.getSelectedItem().getValue());
			if ((((Decimalbox)this.getFellow("FMSAmount")).getValue()!= null) && !"".equals(((Decimalbox)this.getFellow("FMSAmount")).getValue().toString()))
				txnDetails.put("FMSAmount", ((Decimalbox)this.getFellow("FMSAmount")).getValue().toString());
			if ((((Decimalbox)this.getFellow("incentiveAmt")).getValue()!= null) && !"".equals(((Decimalbox)this.getFellow("incentiveAmt")).getValue().toString()))
				txnDetails.put("incentiveAmt", ((Decimalbox)this.getFellow("incentiveAmt")).getValue().toString());
			if ((((Decimalbox)this.getFellow("promoAmt")).getValue()!= null) && !"".equals(((Decimalbox)this.getFellow("promoAmt")).getValue().toString()))
				txnDetails.put("promoAmt", ((Decimalbox)this.getFellow("promoAmt")).getValue().toString());
			if ((((Decimalbox)this.getFellow("cabRewardsAmt")).getValue()!= null) && !"".equals(((Decimalbox)this.getFellow("cabRewardsAmt")).getValue().toString()))
				txnDetails.put("cabRewardsAmt", ((Decimalbox)this.getFellow("cabRewardsAmt")).getValue().toString());
		}
		
		if ((((Decimalbox)this.getFellow("fareAmt")).getValue() != null) && !"".equals(((Decimalbox)this.getFellow("fareAmt")).getValue().toString()))
			txnDetails.put("fareAmt", ((Decimalbox)this.getFellow("fareAmt")).getValue().toString());
		
		
		Row prepaidAdminRow = (Row) this.getFellow("prepaidAdminRow");
		if(prepaidAdminRow.isVisible()){
			
			BigDecimal prepaidAdminFee = ((Decimalbox) this.getFellow("prepaidAdminFee")).getValue();
			BigDecimal prepaidGst = ((Decimalbox) this.getFellow("prepaidGst")).getValue();
			
			if(prepaidAdminFee!=null){
				txnDetails.put("prepaidAdminFee",prepaidAdminFee.toString());
			}
			
			if(prepaidGst!=null){
				txnDetails.put("prepaidGst",prepaidGst.toString());
			}
		}
		
		if ((((CapsTextbox)this.getFellow("pickup")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("pickup")).getValue()))
			txnDetails.put("pickup", ((CapsTextbox)this.getFellow("pickup")).getValue());
		if ((((CapsTextbox)this.getFellow("destination")).getValue() != null) && !"".equals(((CapsTextbox)this.getFellow("destination")).getValue()))
			txnDetails.put("destination", ((CapsTextbox)this.getFellow("destination")).getValue());
		if ((((CapsTextbox)this.getFellow("remarks")).getValue() != null) && !"".equals(((CapsTextbox)this.getFellow("remarks")).getValue()))
			txnDetails.put("remarks", ((CapsTextbox)this.getFellow("remarks")).getValue());
		if ((((CapsTextbox)this.getFellow("appRemarks")).getValue() != null) && !"".equals(((CapsTextbox)this.getFellow("appRemarks")).getValue()))
			txnDetails.put("appRemarks", ((CapsTextbox)this.getFellow("appRemarks")).getValue());
		if ((((CapsTextbox)this.getFellow("salesDraft")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("salesDraft")).getValue()))
			txnDetails.put("salesDraft", ((CapsTextbox)this.getFellow("salesDraft")).getValue());
		if ((((CapsTextbox)this.getFellow("projCode")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("projCode")).getValue()))
			txnDetails.put("projCode", ((CapsTextbox)this.getFellow("projCode")).getValue());
		if ((((CapsTextbox)this.getFellow("projCodeReason")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("projCodeReason")).getValue()))
			txnDetails.put("projCodeReason", ((CapsTextbox)this.getFellow("projCodeReason")).getValue());
		if ((((CapsTextbox)this.getFellow("surchargeDesc")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("surchargeDesc")).getValue()))
			txnDetails.put("surchargeDesc", ((CapsTextbox)this.getFellow("surchargeDesc")).getValue());

		// checkbox for complimentary
		Checkbox checkbox = (Checkbox)this.getFellow("complimentary");
		if (checkbox.isChecked())
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);

		// fields not updated will be picked up from the currentAcquireTxn instance
		//txnDetails.put("taxiNo", currentAcquireTxn.getTaxiNo());
		//txnDetails.put("nric", currentAcquireTxn.getNric());
		//txnDetails.put("jobNo", currentAcquireTxn.getJobNo());
		
		// Trip Type
		Listbox tripType = (Listbox) this.getFellow("tripType");
		if (tripType.getSelectedItem()!= null && tripType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("tripType", (String) tripType.getSelectedItem().getValue());
		}
		
		return txnDetails;
	}
	
	protected Map<String, String> getPremierTxnDetails(){
		Map<String, String> txnDetails = new HashMap<String, String>();
		
		//contactDetails.put("userId", this.getUserLoginId());
		txnDetails.put("userId", getUserLoginIdAndDomain());
		txnDetails.put("actionTxn", (String)((Listbox)this.getFellow("actionTxn")).getSelectedItem().getValue());
		
		// Set txn status
		txnDetails.put("txnStatus", ((Label)this.getFellow("txnStatus")).getValue());
				
		//selected account
		String acctNo = ((Label)this.getFellow("acctNo")).getValue();
		String name = ((Label)this.getFellow("name")).getValue();
		txnDetails.put("acctNo", acctNo);
		txnDetails.put("name", name);
		//division
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		if(divisionListBox.getSelectedItem()!=null){
			if(divisionListBox.getSelectedItem().getValue() instanceof AmtbAccount && divisionListBox.getSelectedItem().getValue()!= null)
				txnDetails.put("division", ((AmtbAccount) divisionListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		//department
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		if(departmentListBox.getSelectedItem()!=null){
			if(departmentListBox.getSelectedItem().getValue() instanceof AmtbAccount && departmentListBox.getSelectedItem().getValue() != null)
				txnDetails.put("department", ((AmtbAccount) departmentListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		
		Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicantListBox");
		if(subApplicantListBox.getSelectedItem()!=null){
			if(subApplicantListBox.getSelectedItem().getValue() instanceof AmtbAccount && subApplicantListBox.getSelectedItem().getValue() != null)
				txnDetails.put("subApplicant", ((AmtbAccount) subApplicantListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		
		// product type
		txnDetails.put("productType", currentAcquireTxn.getPmtbProductType().getProductTypeId());
		
		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		Timestamp startDate = null;
		if (startDateBox != null && startDateBox.getValue() != null)
		{
			Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(startTimeMin.getSelectedItem().getLabel()),Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
		}
		
		txnDetails.put("startDate", DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));
		
		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		Timestamp endDate = null;
		if (endDateBox != null && endDateBox.getValue() != null)
		{
			Date endDateWithTime = combineDateHrMinSec(endDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
			endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
		}

		txnDetails.put("endDate", DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));

		// Add company Code
		Listbox companyCd = (Listbox) this.getFellow("companyCd");
		if (companyCd.getSelectedItem()!= null && companyCd.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("companyCd", (String) companyCd.getSelectedItem().getValue());
		}
		//txnDetails.put("companyCd", ((CapsTextbox)this.getFellow("cardNo")).getValue());
		txnDetails.put("fareAmt", ((Decimalbox)this.getFellow("fareAmt")).getValue().toString());
		txnDetails.put("pickup", ((CapsTextbox)this.getFellow("pickup")).getValue());
		txnDetails.put("destination", ((CapsTextbox)this.getFellow("destination")).getValue());
		txnDetails.put("remarks", ((CapsTextbox)this.getFellow("remarks")).getValue());
		if (((CapsTextbox)this.getFellow("appRemarks")).getValue() != null && !"".equals(((CapsTextbox)this.getFellow("appRemarks")).getValue()))
			txnDetails.put("appRemarks", ((CapsTextbox)this.getFellow("appRemarks")).getValue());
		
		// checkbox for complimentary
		Checkbox checkbox = (Checkbox)this.getFellow("complimentary");
		if (checkbox.isChecked())
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);

		
		// Premier txn additional fields
		txnDetails.put("surchargeDesc", ((CapsTextbox)this.getFellow("surchargeDesc")).getValue());
		txnDetails.put("bookedBy", ((CapsTextbox)this.getFellow("bookedBy")).getValue());
		txnDetails.put("bookedRef", ((CapsTextbox)this.getFellow("bookedRef")).getValue());
		txnDetails.put("flightInfo", ((CapsTextbox)this.getFellow("flightInfo")).getValue());
		
		Datebox bookedDateBox = (Datebox) this.getFellow("bookedDate");
		if (bookedDateBox.getValue() != null && !"".equals(bookedDateBox.getValue()))
			txnDetails.put("bookedDate", DateUtil.convertDateToStr(bookedDateBox.getValue(), DateUtil.TRIPS_DATE_FORMAT));
		
		// Trip Type
		Listbox tripType = (Listbox) this.getFellow("tripType");
		if (tripType.getSelectedItem()!= null && tripType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("tripType", (String) tripType.getSelectedItem().getValue());
		}
		
		// Vehicle Group
		Listbox vehicleGroup = (Listbox) this.getFellow("vehicleGroup");
		if (vehicleGroup.getSelectedItem()!= null && vehicleGroup.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("vehicleGroup", (String) vehicleGroup.getSelectedItem().getValue());
		}
		
		// Job Type
		Listbox jobType = (Listbox) this.getFellow("premJobType");
		if (jobType.getSelectedItem()!= null && jobType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("jobType", (String) jobType.getSelectedItem().getValue());
		}
		
		// Vehicle Type
		Listbox vehicleType = (Listbox) this.getFellow("premVehicleType");
		if (vehicleType.getSelectedItem()!= null && vehicleType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("vehicleType", (String) vehicleType.getSelectedItem().getValue());
		}
		
		txnDetails.put("paxName", ((CapsTextbox)this.getFellow("paxName")).getValue());
		
		String toUpdateFMS = null;
		Listbox toUpdateFMSList = (Listbox) this.getFellow("toUpdateFMSList");
		if (toUpdateFMSList.getSelectedItem()!= null && toUpdateFMSList.getSelectedItem().getValue() !=null) {
			toUpdateFMS = (String) toUpdateFMSList.getSelectedItem().getValue();
			txnDetails.put("toUpdateFMSList", toUpdateFMS);
		}
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(toUpdateFMS)) {

			Listbox updateFMSList = (Listbox) this.getFellow("updateFMSList");
			if (updateFMSList.getSelectedItem()!= null && updateFMSList.getSelectedItem().getValue() !=null)
				txnDetails.put("updateFMSList", (String) updateFMSList.getSelectedItem().getValue());
			
			if (((Decimalbox)this.getFellow("FMSAmount")).getValue() != null && !"".equals(((Decimalbox)this.getFellow("FMSAmount")).getValue().toString()))
				txnDetails.put("FMSAmount", ((Decimalbox)this.getFellow("FMSAmount")).getValue().toString());
			if (((Decimalbox)this.getFellow("incentiveAmt")).getValue() != null && !"".equals(((Decimalbox)this.getFellow("incentiveAmt")).getValue().toString()))
				txnDetails.put("incentiveAmt", ((Decimalbox)this.getFellow("incentiveAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("promoAmt")).getValue() != null && !"".equals(((Decimalbox)this.getFellow("promoAmt")).getValue().toString()))
				txnDetails.put("promoAmt", ((Decimalbox)this.getFellow("promoAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("cabRewardsAmt")).getValue() != null && !"".equals(((Decimalbox)this.getFellow("cabRewardsAmt")).getValue().toString()))
				txnDetails.put("cabRewardsAmt", ((Decimalbox)this.getFellow("cabRewardsAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("levy")).getValue() != null && !"".equals(((Decimalbox)this.getFellow("levy")).getValue().toString()))
				txnDetails.put("levy", ((Decimalbox)this.getFellow("levy")).getValue().setScale(2).toString());
		}
		
		if ((((CapsTextbox)this.getFellow("projCode")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("projCode")).getValue()))
			txnDetails.put("projCode", ((CapsTextbox)this.getFellow("projCode")).getValue());
		if ((((CapsTextbox)this.getFellow("projCodeReason")).getValue()!= null) && !"".equals(((CapsTextbox)this.getFellow("projCodeReason")).getValue()))
			txnDetails.put("projCodeReason", ((CapsTextbox)this.getFellow("projCodeReason")).getValue());

		return txnDetails;
	}
	
	public void init() throws InterruptedException
	{
		try
		{
			TmtbAcquireTxn tmtbAcquireTxn = this.businessHelper.getTxnBusiness().searchTxn(txnID);
			// Assign to current session
			currentAcquireTxn = tmtbAcquireTxn;
			if (tmtbAcquireTxn != null)
			{
				List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
				for(PmtbProductType cardlessProduct : cardlessProducts)
				{
					if(cardlessProduct.getProductTypeId().equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
					{
						isCardless = true;
						break;
					}
				}
				
				// Populate account
				if (tmtbAcquireTxn.getAmtbAccount()!= null)
				{
					AmtbAccount amtbAccount = tmtbAcquireTxn.getAmtbAccount();
//					if (NonConfigurableConstants.PREMIER_SERVICE.equalsIgnoreCase(currentAcquireTxn.getPmtbProductType().getProductTypeId()))
					if(isCardless)
					{
						isPremierTrips = true;
					}
					// TODO: Check for premier here
					if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(amtbAccount.getAccountCategory()))
					{
						// It is a department
						populateDivisionDepartment(amtbAccount.getAmtbAccount().getAmtbAccount(), isPremierTrips, amtbAccount.getAmtbAccount().getAccountNo(), amtbAccount.getAccountNo());
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAmtbAccount().getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(amtbAccount.getAccountCategory()))
					{
						// It is a division
						populateDivisionDepartment(amtbAccount.getAmtbAccount(), isPremierTrips, amtbAccount.getAccountNo(), 0);
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(amtbAccount.getAccountCategory()))
					{
						// It is a corp
						populateDivisionDepartment(amtbAccount, isPremierTrips, 0, 0);
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(amtbAccount.getAccountCategory()))
					{
						populateSubMainApplicant(amtbAccount, isPremierTrips, 0);
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT);
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(amtbAccount.getAccountCategory()))
					{
						populateSubMainApplicant(amtbAccount.getAmtbAccount(), isPremierTrips, amtbAccount.getAccountNo());
						((Label)this.getFellow("acctNo")).setValue(amtbAccount.getAmtbAccount().getCustNo());
						((Label)this.getFellow("name")).setValue(amtbAccount.getAmtbAccount().getAccountName());
						setContact(amtbAccount, NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT);
					}
				}
				else
				{
					throw new Exception("Account is not created properly");
				}
				if (tmtbAcquireTxn.getPmtbProduct() != null)
				{
					PmtbProduct pmtbProduct = tmtbAcquireTxn.getPmtbProduct();
					if (pmtbProduct.getCardNo() != null && !"".equals(pmtbProduct.getCardNo()))
					{
						((Row)this.getFellow("cardNoRow")).setVisible(true);
						((Label)this.getFellow("cardNo")).setValue(pmtbProduct.getCardNo());
						((Label)this.getFellow("nameOnCard")).setValue(pmtbProduct.getNameOnProduct());
					}
				}
				else
				{
					// To display external card
					if (tmtbAcquireTxn.getExternalCardNo() != null && !"".equals(tmtbAcquireTxn.getExternalCardNo()))
					{
						((Row)this.getFellow("cardNoRow")).setVisible(true);
						((Label)this.getFellow("cardNo")).setValue(tmtbAcquireTxn.getExternalCardNo());
						((Label)this.getFellow("nameOnCard")).setValue("-");
					}
				}
				if (tmtbAcquireTxn.getPmtbProductType() != null)
				{
//					if (!NonConfigurableConstants.PREMIER_SERVICE.equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
					if(!isCardless)
					{
						((Row)this.getFellow("salesDraftRow")).setVisible(true);
						((Label)this.getFellow("productType")).setValue(tmtbAcquireTxn.getPmtbProductType().getName());
					}
				}
				if (tmtbAcquireTxn.getTxnStatus() != null && !"".equals(tmtbAcquireTxn.getTxnStatus()))
				{
					((Label)this.getFellow("txnStatus")).setValue(NonConfigurableConstants.TXN_STATUS.get(tmtbAcquireTxn.getTxnStatus()));
				}
				if (tmtbAcquireTxn.getTaxiNo() != null && !"".equals(tmtbAcquireTxn.getTaxiNo()))
				{
					((Label)this.getFellow("taxiNo")).setValue(tmtbAcquireTxn.getTaxiNo());
				}
				if (tmtbAcquireTxn.getNric() != null && !"".equals(tmtbAcquireTxn.getNric()))
				{
					((Label)this.getFellow("nric")).setValue(StringUtil.maskNric(tmtbAcquireTxn.getNric()));
				}
				if (tmtbAcquireTxn.getTripStartDt() != null)
				{
					((Datebox)this.getFellow("startDate")).setValue(DateUtil.convertStrToDate((DateUtil.convertTimestampToStr(tmtbAcquireTxn.getTripStartDt(), DateUtil.GLOBAL_DATE_FORMAT)),DateUtil.GLOBAL_DATE_FORMAT ));
					Listbox startTimeHrDDL = ((Listbox)this.getFellow("startTimeHrDDL"));
					Listbox startTimeMinDDL = ((Listbox)this.getFellow("startTimeMinDDL"));
					Listbox startTimeSecDDL = ((Listbox)this.getFellow("startTimeSecDDL"));
					List<Listitem> listitems = startTimeHrDDL.getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getLabel();
						if (tempValue.equals(DateUtil.convertDateToStr(tmtbAcquireTxn.getTripStartDt(), "HH")))
						{
							listitem.setSelected(true);
						}
					}
					listitems = startTimeMinDDL.getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getLabel();
						if (tempValue.equals(DateUtil.convertDateToStr(tmtbAcquireTxn.getTripStartDt(), "mm")))
						{
							listitem.setSelected(true);
						}
					}
					listitems = startTimeSecDDL.getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getLabel();
						if (tempValue.equals(DateUtil.convertDateToStr(tmtbAcquireTxn.getTripStartDt(), "ss")))
						{
							listitem.setSelected(true);
						}
					}
				}
				if (tmtbAcquireTxn.getTripEndDt() != null)
				{
					((Datebox)this.getFellow("endDate")).setValue(DateUtil.convertStrToDate((DateUtil.convertTimestampToStr(tmtbAcquireTxn.getTripEndDt(), DateUtil.GLOBAL_DATE_FORMAT)),DateUtil.GLOBAL_DATE_FORMAT ));
					//((Datebox)this.getFellow("endDate")).setValue(tmtbAcquireTxn.getTripEndDt());					
					Listbox endTimeHrDDL = ((Listbox)this.getFellow("endTimeHrDDL"));
					Listbox endTimeMinDDL = ((Listbox)this.getFellow("endTimeMinDDL"));
					Listbox endTimeSecDDL = ((Listbox)this.getFellow("endTimeSecDDL"));
					List<Listitem> listitems = endTimeHrDDL.getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getLabel();
						if (tempValue.equals(DateUtil.convertDateToStr(tmtbAcquireTxn.getTripEndDt(), "HH")))
						{
							listitem.setSelected(true);
						}
					}
					listitems = endTimeMinDDL.getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getLabel();
						if (tempValue.equals(DateUtil.convertDateToStr(tmtbAcquireTxn.getTripEndDt(), "mm")))
						{
							listitem.setSelected(true);
						}
					}
					listitems = endTimeSecDDL.getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getLabel();
						if (tempValue.equals(DateUtil.convertDateToStr(tmtbAcquireTxn.getTripEndDt(), "ss")))
						{
							listitem.setSelected(true);
						}
					}
				}
				
				if (tmtbAcquireTxn.getMstbMasterTableByServiceProvider() != null)
				{
					MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByServiceProvider();
					((Listbox)this.getFellow("companyCd")).setVisible(true);
					List<Listitem> listitems = (List<Listitem>) this.getFellow("companyCd").getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getValue();
						if (tempValue.equals(mstbMasterTable.getMasterCode()))
						{
							listitem.setSelected(true);
						}
					}
				}

				if (tmtbAcquireTxn.getJobNo() != null && !"".equals(tmtbAcquireTxn.getJobNo()))
				{
					((Label)this.getFellow("jobNo")).setValue(tmtbAcquireTxn.getJobNo());
				}
				
				if (tmtbAcquireTxn.getProjectDesc() != null && !"".equals(tmtbAcquireTxn.getProjectDesc()))
				{
					((Row)this.getFellow("projCodeRow")).setVisible(true);
					((CapsTextbox)this.getFellow("projCode")).setValue(tmtbAcquireTxn.getProjectDesc());
				}
				if (tmtbAcquireTxn.getTripCodeReason() != null && !"".equals(tmtbAcquireTxn.getTripCodeReason()))
				{
					((Row)this.getFellow("projCodeReasonRow")).setVisible(true);
					((CapsTextbox)this.getFellow("projCodeReason")).setValue(tmtbAcquireTxn.getTripCodeReason());
				}
				if (tmtbAcquireTxn.getFareAmt() != null)
				{
					// Set the previous fare amount for comparison if there is a change
					((Decimalbox)this.getFellow("fareAmt")).setValue(tmtbAcquireTxn.getFareAmt().setScale(2));
				}
				if (tmtbAcquireTxn.getAdminFeeValue()!= null)
				{
					((Decimalbox)this.getFellow("prepaidAdminFee")).setValue(tmtbAcquireTxn.getAdminFeeValue().setScale(2));
				}
				
				if (tmtbAcquireTxn.getGstValue()!= null)
				{
					((Decimalbox)this.getFellow("prepaidGst")).setValue(tmtbAcquireTxn.getGstValue().setScale(2));
				}
				
				if (tmtbAcquireTxn.getFareAmt() != null)
				{
					// Set the previous fare amount for comparison if there is a change
					((Decimalbox)this.getFellow("fareAmt")).setValue(tmtbAcquireTxn.getFareAmt().setScale(2));
				}
				
				if (tmtbAcquireTxn.getPickupAddress() != null  && !"".equals(tmtbAcquireTxn.getPickupAddress()))
				{
					((CapsTextbox)this.getFellow("pickup")).setValue(tmtbAcquireTxn.getPickupAddress());
				}
				if (tmtbAcquireTxn.getDestination() != null && !"".equals(tmtbAcquireTxn.getDestination()))
				{
					((CapsTextbox)this.getFellow("destination")).setValue(tmtbAcquireTxn.getDestination());
				}
				
				if (tmtbAcquireTxn.getRemarks() != null && !"".equals(tmtbAcquireTxn.getRemarks()))
				{
					((CapsTextbox)this.getFellow("remarks")).setValue(tmtbAcquireTxn.getRemarks());
				}
				
				if (tmtbAcquireTxn.getComplimentary() != null && !"".equals(tmtbAcquireTxn.getComplimentary()))
				{
					if (NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(tmtbAcquireTxn.getComplimentary()))
						((Checkbox)this.getFellow("complimentary")).setChecked(true);
					else
						((Checkbox)this.getFellow("complimentary")).setChecked(false);
				}

				if (tmtbAcquireTxn.getFmsAmt() != null && !"".equals(tmtbAcquireTxn.getFmsAmt().toString()))
				{
					((Row)this.getFellow("prevFMSRow")).setVisible(true);
					((Label)this.getFellow("prevFMSAmount")).setVisible(true);
					((Label)this.getFellow("prevFMSAmount")).setValue(tmtbAcquireTxn.getFmsAmt().setScale(2).toString());
				}
				
				if (tmtbAcquireTxn.getIncentiveAmt() != null && !"".equals(tmtbAcquireTxn.getIncentiveAmt().toString()))
				{
					((Row)this.getFellow("prevFMSRow2")).setVisible(true);
					((Label)this.getFellow("prevIncentiveAmt")).setVisible(true);
					((Label)this.getFellow("prevIncentiveAmt")).setValue(tmtbAcquireTxn.getIncentiveAmt().setScale(2).toString());
				}
				
				if (tmtbAcquireTxn.getPromoAmt() != null && !"".equals(tmtbAcquireTxn.getPromoAmt().toString()))
				{
					((Row)this.getFellow("prevFMSRow2")).setVisible(true);
					((Label)this.getFellow("prevPromoAmt")).setVisible(true);
					((Label)this.getFellow("prevPromoAmt")).setValue(tmtbAcquireTxn.getPromoAmt().setScale(2).toString());
				}
				if (tmtbAcquireTxn.getCabRewardsAmt() != null && !"".equals(tmtbAcquireTxn.getCabRewardsAmt().toString()))
				{
					((Row)this.getFellow("prevFMSRow3")).setVisible(true);
					((Label)this.getFellow("prevCabRewardsAmt")).setVisible(true);
					((Label)this.getFellow("prevCabRewardsAmt")).setValue(tmtbAcquireTxn.getCabRewardsAmt().setScale(2).toString());
				}
				
				if (tmtbAcquireTxn.getFmsFlag() != null && !"".equals(tmtbAcquireTxn.getFmsFlag()))
				{
					((Row)this.getFellow("prevFMSRow")).setVisible(true);
					((Label)this.getFellow("prevToUpdateFMSList")).setVisible(true);
					((Label)this.getFellow("prevToUpdateFMSList")).setValue(tmtbAcquireTxn.getFmsFlag());
				}
				
				if (tmtbAcquireTxn.getUpdateFms() != null && !"".equals(tmtbAcquireTxn.getUpdateFms()))
				{
					((Row)this.getFellow("prevFMSRow")).setVisible(true);
					((Row)this.getFellow("prevFMSRow2")).setVisible(true);
					((Row)this.getFellow("prevFMSRow3")).setVisible(true);
					((Label)this.getFellow("prevUpdateFMSList")).setVisible(true);
					((Label)this.getFellow("prevUpdateFMSList")).setValue(tmtbAcquireTxn.getUpdateFms());
				}
				
				Row row = (Row) this.getFellow("prepaidAdminRow");
				boolean isPrepaid = NonConfigurableConstants.getBoolean(tmtbAcquireTxn.getPmtbProductType().getPrepaid());
				row.setVisible(isPrepaid);
				
				((Row)this.getFellow("surchargeRow")).setVisible(true);
				((CapsTextbox)this.getFellow("surchargeDesc")).setVisible(true);
				if (tmtbAcquireTxn.getSurcharge() != null  && !"".equals(tmtbAcquireTxn.getSurcharge()))
				{
					((CapsTextbox)this.getFellow("surchargeDesc")).setValue(tmtbAcquireTxn.getSurcharge());
				}
				
				if (tmtbAcquireTxn.getMstbMasterTableByTripType() != null)
				{
					MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByTripType();
					((Listbox)this.getFellow("tripType")).setVisible(true);
					List<Listitem> listitems = (List<Listitem>) this.getFellow("tripType").getChildren();
					for(Listitem listitem : listitems){
						String tempValue = (String) listitem.getValue();
						if (tempValue.equals(mstbMasterTable.getMasterCode()))
						{
							listitem.setSelected(true);
						}
					}
				}
				
//				if (NonConfigurableConstants.PREMIER_SERVICE.equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
				if(isCardless)
				{
					if (tmtbAcquireTxn.getPmtbProductType() != null)
					{
						((Row)this.getFellow("productTypeRow")).setVisible(true);
						((Label)this.getFellow("productType_p")).setValue(tmtbAcquireTxn.getPmtbProductType().getName());
					}
					
					// Premier fields
					
					if (tmtbAcquireTxn.getBookedBy() != null && !"".equals(tmtbAcquireTxn.getBookedBy()))
					{
						((CapsTextbox)this.getFellow("bookedBy")).setVisible(true);
						((CapsTextbox)this.getFellow("bookedBy")).setValue(tmtbAcquireTxn.getBookedBy());
					}

					((Row)this.getFellow("flightInfoRow")).setVisible(true);
					if (tmtbAcquireTxn.getFlightInfo() != null && !"".equals(tmtbAcquireTxn.getFlightInfo()))
					{
						((CapsTextbox)this.getFellow("flightInfo")).setVisible(true);
						((CapsTextbox)this.getFellow("flightInfo")).setValue(tmtbAcquireTxn.getFlightInfo());
					}
					if (tmtbAcquireTxn.getBookingRef() != null && !"".equals(tmtbAcquireTxn.getBookingRef()))
					{
						((CapsTextbox)this.getFellow("bookedRef")).setVisible(true);
						((CapsTextbox)this.getFellow("bookedRef")).setValue(tmtbAcquireTxn.getBookingRef());
					}
					
					((Row)this.getFellow("bookedByBookedDateRow")).setVisible(true);
					if (tmtbAcquireTxn.getBookDateTime() != null)
					{
						((Datebox)this.getFellow("bookedDate")).setVisible(true);
						((Datebox)this.getFellow("bookedDate")).setValue(tmtbAcquireTxn.getBookDateTime());
					}
					// TRIP TYPE and vehicle group
					((Row)this.getFellow("tripTypeJobTypeRow")).setVisible(true);
					((Label)this.getFellow("premJobTypeLabel")).setVisible(true);
					((Listbox)this.getFellow("premJobType")).setVisible(true);
					((Row)this.getFellow("vehicleGroupVehicleTypeRow")).setVisible(true);
					
					if (tmtbAcquireTxn.getMstbMasterTableByVehicleType() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByVehicleType();
						((Listbox)this.getFellow("vehicleGroup")).setVisible(true);
						List<Listitem> listitems = (List<Listitem>) this.getFellow("vehicleGroup").getChildren();
						for(Listitem listitem : listitems){
							String tempValue = (String) listitem.getValue();
							if (tempValue.equals(mstbMasterTable.getMasterCode()))
							{
								listitem.setSelected(true);
							}
						}
					}
					if (tmtbAcquireTxn.getMstbMasterTableByJobType() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByJobType();
						((Listbox)this.getFellow("premJobType")).setVisible(true);
						List<Listitem> listitems = (List<Listitem>) this.getFellow("premJobType").getChildren();
						for(Listitem listitem : listitems){
							String tempValue = (String) listitem.getValue();
							if (tempValue.equals(mstbMasterTable.getMasterCode()))
							{
								listitem.setSelected(true);
							}
						}
					}
					if (tmtbAcquireTxn.getMstbMasterTableByVehicleModel() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByVehicleModel();
						((Listbox)this.getFellow("premVehicleType")).setVisible(true);
						List<Listitem> listitems = (List<Listitem>) this.getFellow("premVehicleType").getChildren();
						for(Listitem listitem : listitems){
							String tempValue = (String) listitem.getValue();
							if (tempValue.equals(mstbMasterTable.getMasterCode()))
							{
								listitem.setSelected(true);
							}
						}
					}
					((Row)this.getFellow("paxNameLevyRow")).setVisible(true);
					if (tmtbAcquireTxn.getPassengerName() != null && !"".equals(tmtbAcquireTxn.getPassengerName()))
					{
						((CapsTextbox)this.getFellow("paxName")).setVisible(true);
						((CapsTextbox)this.getFellow("paxName")).setValue(tmtbAcquireTxn.getPassengerName());
					}

					((Row)this.getFellow("prevFMSRow3")).setVisible(true);
					if (tmtbAcquireTxn.getLevy() != null && !"".equals(tmtbAcquireTxn.getLevy()))
					{
						((Label)this.getFellow("prevLevyLabel")).setVisible(true);
						((Label)this.getFellow("prevLevy")).setVisible(true);
						((Label)this.getFellow("prevLevy")).setValue(tmtbAcquireTxn.getLevy().toString());
					}
				}
				else
				{
					if (tmtbAcquireTxn.getSalesDraftNo() != null && !"".equals(tmtbAcquireTxn.getSalesDraftNo()))
					{
						((Label)this.getFellow("salesDraftLabel")).setVisible(true);
						((CapsTextbox)this.getFellow("salesDraft")).setVisible(true);
						((CapsTextbox)this.getFellow("salesDraft")).setValue(tmtbAcquireTxn.getSalesDraftNo());
					}
					((Row)this.getFellow("jobTypeVehicleTypeRow")).setVisible(true);
					if (tmtbAcquireTxn.getMstbMasterTableByJobType() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByJobType();
						((Listbox)this.getFellow("jobType")).setVisible(true);
						List<Listitem> listitems = (List<Listitem>) this.getFellow("jobType").getChildren();
						for(Listitem listitem : listitems){
							String tempValue = (String) listitem.getValue();
							if (tempValue.equals(mstbMasterTable.getMasterCode()))
							{
								listitem.setSelected(true);
							}
						}
					}
					if (tmtbAcquireTxn.getMstbMasterTableByVehicleModel() != null)
					{
						MstbMasterTable mstbMasterTable = tmtbAcquireTxn.getMstbMasterTableByVehicleModel();
						((Listbox)this.getFellow("vehicleType")).setVisible(true);
						List<Listitem> listitems = (List<Listitem>) this.getFellow("vehicleType").getChildren();
						for(Listitem listitem : listitems){
							String tempValue = (String) listitem.getValue();
							if (tempValue.equals(mstbMasterTable.getMasterCode()))
							{
								listitem.setSelected(true);
							}
						}
					}
				}
				
			}

		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void setContact(AmtbAccount amtbAccount, String acctCat)
	{
		// Get the first main contact since there should only be 1.
		Iterator<AmtbAcctMainContact> mainContacts = null;
		/*if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAccount().getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAcctMainContacts().iterator();			
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(acctCat))
		{
			mainContacts = amtbAccount.getAmtbAccount().getAmtbAcctMainContacts().iterator();
		}*/
		mainContacts = amtbAccount.getAmtbAcctMainContacts().iterator();
		// Set main contact information
		AmtbAcctMainContact amtbAccountMainContact = mainContacts.next();
		String mainContactName = amtbAccountMainContact.getAmtbContactPerson().getMainContactName();
		String secContactName = amtbAccountMainContact.getAmtbContactPerson().getSubContactName();
		Label billContact = (Label) this.getFellow("billContact");
		if (mainContactName != null && !"".equals(mainContactName))
		{
			if (secContactName != null && !"".equals(secContactName))
			{
				billContact.setValue(mainContactName + "/" + secContactName);
			}
			else
			{
				billContact.setValue(mainContactName);							
			}
		}
	}
	
	private void setFieldsDisabled(boolean disabled)
	{
		((Listbox)this.getFellow("divisionListBox")).setDisabled(disabled);
		((Listbox)this.getFellow("departmentListBox")).setDisabled(disabled);
		((Listbox)this.getFellow("subApplicantListBox")).setDisabled(disabled);
		((Datebox)this.getFellow("startDate")).setDisabled(disabled);
		Listbox startTimeHrDDL = ((Listbox)this.getFellow("startTimeHrDDL"));
		Listbox startTimeMinDDL = ((Listbox)this.getFellow("startTimeMinDDL"));
		Listbox startTimeSecDDL = ((Listbox)this.getFellow("startTimeSecDDL"));
		startTimeHrDDL.setDisabled(disabled);
		startTimeMinDDL.setDisabled(disabled);
		startTimeSecDDL.setDisabled(disabled);
	
		((Datebox)this.getFellow("endDate")).setDisabled(disabled);
		Listbox endTimeHrDDL = ((Listbox)this.getFellow("endTimeHrDDL"));
		Listbox endTimeMinDDL = ((Listbox)this.getFellow("endTimeMinDDL"));
		Listbox endTimeSecDDL = ((Listbox)this.getFellow("endTimeSecDDL"));
		endTimeHrDDL.setDisabled(disabled);
		endTimeMinDDL.setDisabled(disabled);
		endTimeSecDDL.setDisabled(disabled);
		Listbox companyCd = (Listbox) this.getFellow("companyCd");
		companyCd.setDisabled(disabled);
		((Decimalbox)this.getFellow("fareAmt")).setDisabled(disabled);
		((Decimalbox)this.getFellow("prepaidAdminFee")).setDisabled(disabled);
		((Decimalbox)this.getFellow("prepaidGst")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("pickup")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("destination")).setDisabled(disabled);
		((Listbox)this.getFellow("jobType")).setDisabled(disabled);
		((Listbox)this.getFellow("vehicleType")).setDisabled(disabled);

		// Premier fields
		((CapsTextbox)this.getFellow("surchargeDesc")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("bookedBy")).setDisabled(disabled);
		((Datebox)this.getFellow("bookedDate")).setDisabled(disabled);
		((Listbox)this.getFellow("tripType")).setDisabled(disabled);
		((Listbox)this.getFellow("vehicleGroup")).setDisabled(disabled);
		((Listbox)this.getFellow("premVehicleType")).setDisabled(disabled);
		((Listbox)this.getFellow("premJobType")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("paxName")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("remarks")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("salesDraft")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("projCode")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("projCodeReason")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("flightInfo")).setDisabled(disabled);
		((CapsTextbox)this.getFellow("bookedRef")).setDisabled(disabled);
	}	
	
	public void exportResult() throws InterruptedException, IOException {
		logger.info("exportResult");
		
		if(((CapsTextbox)this.getFellow("appRemarks")).getValue() != null)
			((CapsTextbox)this.getFellow("appRemarks")).setValue(((CapsTextbox)this.getFellow("appRemarks")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("remarks")).getValue() != null)
			((CapsTextbox)this.getFellow("remarks")).setValue(((CapsTextbox)this.getFellow("remarks")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("pickup")).getValue() != null)
			((CapsTextbox)this.getFellow("pickup")).setValue(((CapsTextbox)this.getFellow("pickup")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("destination")).getValue() != null)
			((CapsTextbox)this.getFellow("destination")).setValue(((CapsTextbox)this.getFellow("destination")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("surchargeDesc")).getValue() != null)
			((CapsTextbox)this.getFellow("surchargeDesc")).setValue(((CapsTextbox)this.getFellow("surchargeDesc")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("projCode")).getValue() != null)
			((CapsTextbox)this.getFellow("projCode")).setValue(((CapsTextbox)this.getFellow("projCode")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("projCodeReason")).getValue() != null)
			((CapsTextbox)this.getFellow("projCodeReason")).setValue(((CapsTextbox)this.getFellow("projCodeReason")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("bookedRef")).getValue() != null)
			((CapsTextbox)this.getFellow("bookedRef")).setValue(((CapsTextbox)this.getFellow("bookedRef")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("flightInfo")).getValue() != null)
			((CapsTextbox)this.getFellow("flightInfo")).setValue(((CapsTextbox)this.getFellow("flightInfo")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("bookedBy")).getValue() != null)
			((CapsTextbox)this.getFellow("bookedBy")).setValue(((CapsTextbox)this.getFellow("bookedBy")).getValue().toUpperCase());
		
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("issueToAcctDetails"), "Account Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("txnDetailsGrid"), "Transaction Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("updateFMSGrid"), "Update FMS", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter();
		try{
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("Edit_Trip_Txn.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
	public void checkAdminGst(Listitem selectedItem){
		logger.info("checkAdminGst(Listitem selectedItem)");
		boolean toPt = false;

		if(selectedItem != null)
		{
			if(selectedItem.getValue() != null && !selectedItem.getValue().toString().trim().equals(""))
			{
				MstbMasterTable tripTypeMaster = ConfigurableConstants.getMasterTable(ConfigurableConstants.VEHICLE_TRIP_TYPE, selectedItem.getValue().toString());
				
				if(tripTypeMaster.getInterfaceMappingValue().length() >= 4)
				{
					if(tripTypeMaster.getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT")
							&& tripTypeMaster.getMasterStatus().trim().equalsIgnoreCase("A"))
						toPt = true;
				}
			}
		}
		((Decimalbox)this.getFellow("prepaidAdminFee")).setDisabled(toPt);
		((Decimalbox)this.getFellow("prepaidGst")).setDisabled(toPt);
	}
//	private Boolean checkProjectCode(Map<String, String> txnDetails) {
//		String productNoTextBox = ((Label)this.getFellow("cardNo")).getValue();
//
//		Datebox startDateBox = (Datebox) this.getFellow("startDate");
//		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
//		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
//		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
//		Timestamp startDate = null;
//		if (startDateBox != null && startDateBox.getValue() != null)
//		{
//			Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
//					Integer.parseInt(startTimeMin.getSelectedItem().getLabel()),Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
//			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
//		}
//		
//		AmtbAccount acct = this.businessHelper.getTxnBusiness().getAccount(productNoTextBox, startDate);
//		
//		if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE) 
//				|| acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
//				|| acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
//
//			AmtbAccount corpAcct = acct;
//			
//			if(acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) ) {
//				corpAcct = acct.getAmtbAccount();
//			} 
//			else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
//				corpAcct = acct.getAmtbAccount().getAmtbAccount();
//			}
//			if (corpAcct.getAmtbCorporateDetails() != null) {
//				if (corpAcct.getAmtbCorporateDetails().iterator().hasNext()) {
//					AmtbCorporateDetail amtbCorporateDetail = corpAcct.getAmtbCorporateDetails().iterator().next();
//					if (NonConfigurableConstants.BOOLEAN_YES.equals(amtbCorporateDetail.getProjectCode())) {
//						return true;
//					} 
//				}
//			}
//		} 
//		
//		return false;
//	}
}
