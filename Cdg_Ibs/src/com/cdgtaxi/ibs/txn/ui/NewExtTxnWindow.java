package com.cdgtaxi.ibs.txn.ui;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;


public class NewExtTxnWindow extends CommonWindow implements AfterCompose{
	
	private static Logger logger = Logger.getLogger(NewExtTxnWindow.class);
	private Textbox cardNo;
	
	public NewExtTxnWindow(){
		
	}
	
	public void onCreate()
	{
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		cardNo.focus();
	}

	@Override
	public void refresh() throws InterruptedException {
			
	}
	
	public List<Listitem> getProductTypes() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getAllProductTypes(), false);
		return cloneList(productTypeList);
	}
	
	public List<Listitem> getExternalProductType() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getExternalProductTypes(), false);
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
				((Decimalbox)this.getFellow("FMSAmount")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroOrGreaterConstraint());
			}
			else
			{
				// disable the fields
				((Row)this.getFellow("FMSRow")).setVisible(false);
				((Row)this.getFellow("FMSRow2")).setVisible(false);
				((Decimalbox)this.getFellow("FMSAmount")).setConstraint("");
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
//	private void setProjectCodeVisible(){
//		((Row)this.getFellow("projCodeRow")).setVisible(true);
//		CapsTextbox projCode = (CapsTextbox) this.getFellow("projCode");
//		projCode.setValue(null);
//		
//		((Row)this.getFellow("projCodeReasonRow")).setVisible(true);
//		CapsTextbox projCodeReason = (CapsTextbox) this.getFellow("projCodeReason");
//		projCodeReason.setValue(null);
//	}
//	
//	private void setProjectCodeInvisible(){
//		((Row)this.getFellow("projCodeRow")).setVisible(false);
//		CapsTextbox projCode = (CapsTextbox) this.getFellow("projCode");
//		projCode.setValue(null);
//		
//		((Row)this.getFellow("projCodeReasonRow")).setVisible(false);
//		CapsTextbox projCodeReason = (CapsTextbox) this.getFellow("projCodeReason");
//		projCodeReason.setValue(null);
//	}
//	
//	private void checkProjectCode(AmtbAccount acct)
//	{
//		if (acct.getAmtbCorporateDetails() != null)
//		{
//			if (acct.getAmtbCorporateDetails().iterator().hasNext())
//			{
//				AmtbCorporateDetail amtbCorporateDetail = acct.getAmtbCorporateDetails().iterator().next();
//				if (NonConfigurableConstants.BOOLEAN_YES.equals(amtbCorporateDetail.getProjectCode()))
//				{
//					setProjectCodeVisible();
//				}
//				else
//					setProjectCodeInvisible();
//			}
//			else
//				setProjectCodeInvisible();
//		}
//		else
//			setProjectCodeInvisible();
//	}
	
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
		// getting the account template
		// Generated from sequence number
		try{
			Map<String, String> txnDetails = getTxnDetails();
			// Validation rules
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
							Messagebox.show("Collect/Refund is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
					else
					{
						Messagebox.show("Collect/Refund is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
					String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(txnDetails.get("taxiNo"), txnDetails.get("nric"), DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.FMS_TRIPS_DATE_FORMAT), DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.FMS_TRIPS_DATE_FORMAT));
					if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg))
					{
						Messagebox.show("Interface Error to FMS - " + msg, "Create External Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
			else
			{
				Messagebox.show("Update FMS is a mandatory field", "Edit External Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox productTypeListbox = (Listbox) this.getFellow("productType");
			if (productTypeListbox.getSelectedItem() != null)
			{
				if ("".equals(productTypeListbox.getSelectedItem().getValue()))
				{
					Messagebox.show("Product Type is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Product Type is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox companyCdListbox = (Listbox) this.getFellow("companyCd");
			if (companyCdListbox.getSelectedItem() != null)
			{
				if ("".equals(companyCdListbox.getSelectedItem().getValue()))
				{
					Messagebox.show("Company Code is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Company Code is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox jobType = (Listbox) this.getFellow("jobType");
			if (jobType.getSelectedItem() != null)
			{
				if ("".equals(jobType.getSelectedItem().getValue()))
				{
					Messagebox.show("Job Type is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Job Type is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
			if (vehicleType.getSelectedItem() != null)
			{
				if ("".equals(vehicleType.getSelectedItem().getValue()))
				{
					Messagebox.show("Vehicle Type is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Vehicle Type is a mandatory field", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			// Validation - Closed account cannot create transaction
			if (this.businessHelper.getTxnBusiness().isAccountClosed(txnDetails.get("acctNo")))
			{
				Messagebox.show("Unable to create new Trips as the account is closed", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			// Validation - Start Date cannot be later than end date
			Timestamp tripStartTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.TRIPS_DATE_FORMAT);
			Timestamp tripEndTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.TRIPS_DATE_FORMAT);
			
			if (tripStartTimeStamp.after(tripEndTimeStamp))
			{
				Messagebox.show("Trip Start Date cannot be earlier than Trip End Date", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox tripType = (Listbox) this.getFellow("tripType");
			if (tripType.getSelectedItem() != null)
			{
				if ("".equals(tripType.getSelectedItem().getValue()))
				{
					Messagebox.show("Trip Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Trip Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			String jobNo = this.businessHelper.getTxnBusiness().createExternalTxn(txnDetails, getUserLoginIdAndDomain());
			if(jobNo != null)
			{
				Messagebox.show("New External Service trip created with \nJob No: " + jobNo , "Create External Trip",  Messagebox.OK, Messagebox.INFORMATION);
				
			}else{
				Messagebox.show("Unable to save trip. Please try again later", "Create External Trip", Messagebox.OK, Messagebox.ERROR);
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
		
		//contactDetails.put("userId", this.getUserLoginId());
		//txnDetails.put("acctNo", ((AmtbAccount) accountNameComboBox.getSelectedItem().getValue()).getAccountNo().toString());
		txnDetails.put("acctNo", ((Label)this.getFellow("acctNo")).getValue());
		txnDetails.put("name", ((Label)this.getFellow("name")).getValue());
		txnDetails.put("cardNo", ((CapsTextbox)this.getFellow("cardNo")).getValue());

		Listbox productType = (Listbox)this.getFellow("productType");
		if (productType.getSelectedItem()!= null && productType.getSelectedItem().getValue()!=null)
		{
			txnDetails.put("productType", (String) productType.getSelectedItem().getValue());
		}
		Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
		if (vehicleType.getSelectedItem()!= null && vehicleType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("vehicleType", (String) vehicleType.getSelectedItem().getValue());
		}
		
		Listbox jobType = (Listbox) this.getFellow("jobType");
		if (jobType.getSelectedItem()!= null && jobType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("jobType", (String) jobType.getSelectedItem().getValue());
		}
		
		
		txnDetails.put("taxiNo", ((CapsTextbox)this.getFellow("taxiNo")).getValue());
		txnDetails.put("nric", ((CapsTextbox)this.getFellow("nric")).getValue());
		
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
		txnDetails.put("projCode", ((CapsTextbox)this.getFellow("projCode")).getValue());
		txnDetails.put("projCodeReason", ((CapsTextbox)this.getFellow("projCodeReason")).getValue());
		txnDetails.put("salesDraft", ((CapsTextbox)this.getFellow("salesDraft")).getValue());

		// checkbox for complimentary
		Checkbox checkbox = (Checkbox)this.getFellow("complimentary");
		if (checkbox.isChecked())
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);
		
		txnDetails.put("surchargeDesc", ((CapsTextbox)this.getFellow("surchargeDesc")).getValue());
		
		Listitem toUpdateFMSListitem = ((Listbox)this.getFellow("toUpdateFMSList")).getSelectedItem();
		String toUpdateFMS = null;
		if (toUpdateFMSListitem != null) {
			toUpdateFMS = (String) toUpdateFMSListitem.getValue();
			txnDetails.put("toUpdateFMSList", toUpdateFMS);
		}
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(toUpdateFMS)) {
			Listitem updateFMSListitem = ((Listbox)this.getFellow("updateFMSList")).getSelectedItem();
			if (updateFMSListitem != null)
				txnDetails.put("updateFMSList", (String)(updateFMSListitem.getValue()));
			if (((Decimalbox)this.getFellow("FMSAmount")).getValue() != null)
				txnDetails.put("FMSAmount", ((Decimalbox)this.getFellow("FMSAmount")).getValue().toString());
			if (((Decimalbox)this.getFellow("promoAmt")).getValue() != null)
				txnDetails.put("promoAmt", ((Decimalbox)this.getFellow("promoAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("cabRewardsAmt")).getValue() != null)
				txnDetails.put("cabRewardsAmt", ((Decimalbox)this.getFellow("cabRewardsAmt")).getValue().toString());
		}
		
		// Trip Type
		Listbox tripType = (Listbox) this.getFellow("tripType");
		if (tripType.getSelectedItem()!= null && tripType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("tripType", (String) tripType.getSelectedItem().getValue());
		}
		
		txnDetails.put("user", getUserLoginIdAndDomain());
		return txnDetails;
	}
	
	public void onSelectProductNoOnly() throws InterruptedException{
		logger.info("OnSelectProductNoOnly");
		
		try{
			CapsTextbox productNoTextBox = (CapsTextbox)this.getFellow("cardNo");
			
			if (productNoTextBox != null && !"".equals(productNoTextBox))
			{
				// Retrieve product information and account information
				if (productNoTextBox.getText() != null && !"".equals(productNoTextBox.getText()))
				{
					Timestamp startDate = null;
					startDate = DateUtil.getCurrentTimestamp();
					
					// To retrieve product type based on BIN range
					if (productNoTextBox.getText().length() > 10)
					{
						String binRange = productNoTextBox.getText().substring(0,6);
						String subBinRange = productNoTextBox.getText().substring(6,10);
						PmtbProductType pmtbProductType = this.businessHelper.getProductTypeBusiness().getExternalProductType(binRange, subBinRange);
						
						// Based on product type get the account
						
						if (pmtbProductType != null)
						{
						
							AmtbAccount acct = this.businessHelper.getAccountBusiness().getAccountSubscribedToExternalCard(pmtbProductType.getProductTypeId());
		
							// Set the acct info
							if (acct != null)
							{
								// Assumed that the account is always a corporate or personal
								if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE))
								{
								
									// Set the necessary info for Corporate
									Label acctNo = (Label) this.getFellow("acctNo");
									acctNo.setValue(acct.getCustNo());
									Label name = (Label) this.getFellow("name");
									name.setValue(acct.getAccountName());
									
									// Validation - Closed account cannot create transaction
									// Only need to check corporate or applicant. Not required for division as it will be terminated instead of closed.
									if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString()))
									{
										Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
										// Clear
										this.clearAcct();
										return;
									}
									
//									checkProjectCode(acct);
									
								}
								else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))
								{
									// Set the necessary info for Corporate
									Label acctNo = (Label) this.getFellow("acctNo");
									acctNo.setValue(acct.getCustNo());
									Label name = (Label) this.getFellow("name");
									name.setValue(acct.getAccountName());
									// Validation - Closed account cannot create transaction
									// Only need to check corporate or applicant. Not required for division as it will be terminated instead of closed.
									if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString()))
									{
										Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
										// Clear
										this.clearAcct();
										return;
									}
//									checkProjectCode(acct);
									
								}
		
								// Get contact
								AmtbAcctMainContact amtbAccountMainContact = this.businessHelper.getTxnBusiness().getMainBillingContact(acct);							
								
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
								
								// Default product type
								Listbox productType = (Listbox) this.getFellow("productType");
								List<Listitem> listItems = productType.getItems();
								for(Listitem listItem : listItems){
									if(((String)listItem.getValue()).equals(pmtbProductType.getProductTypeId())){
										listItem.setSelected(true);
										((Listbox) this.getFellow("productType")).setDisabled(true);
										break;
									}
								}
							}
							else
							{
								// Clear
								this.clearAcct();
								Messagebox.show("There is no such card in the system", 
										"Information", Messagebox.OK, Messagebox.INFORMATION);
								((CapsTextbox) this.getFellow("cardNo")).focus();
							}
						}
						else
						{
							// Clear
							this.clearAcct();
							Messagebox.show("There is no such BIN Range/Sub BIN Range in the system", 
									"Information", Messagebox.OK, Messagebox.INFORMATION);
							((CapsTextbox) this.getFellow("cardNo")).focus();
						}
					}
					else
					{
						// Clear
						this.clearAcct();
						Messagebox.show("Please enter more than 10 digits for the card no", 
								"Information", Messagebox.OK, Messagebox.INFORMATION);
						((CapsTextbox) this.getFellow("cardNo")).focus();
					}
				}
			}
		}
		catch(WrongValueException wve)
		{
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void reset() throws InterruptedException{
		logger.info("reset");
		this.removeConstraints();
		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		endDateBox.setValue(null);
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		endTimeHr.setSelectedIndex(0);
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		endTimeMin.setSelectedIndex(0);

		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		endTimeSec.setSelectedIndex(0);
		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		startDateBox.setValue(null);
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		startTimeHr.setSelectedIndex(0);
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		startTimeMin.setSelectedIndex(0);
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		startTimeSec.setSelectedIndex(0);
		
		Listbox productTypeListbox = (Listbox) this.getFellow("productType");
		productTypeListbox.setSelectedIndex(0);
		((CapsTextbox)this.getFellow("remarks")).setValue(null);
		((CapsTextbox)this.getFellow("pickup")).setValue(null);
		((CapsTextbox)this.getFellow("destination")).setValue(null);
		((CapsTextbox)this.getFellow("surchargeDesc")).setValue(null);
		((Checkbox)this.getFellow("complimentary")).setChecked(false);

		((Decimalbox)this.getFellow("FMSAmount")).setValue(null);
		((Decimalbox)this.getFellow("fareAmt")).setValue(null);
		((Listbox)this.getFellow("companyCd")).setSelectedIndex(0);
		((Listbox)this.getFellow("vehicleType")).setSelectedIndex(0);
		((Listbox)this.getFellow("jobType")).setSelectedIndex(0);
		((Listbox)this.getFellow("updateFMSList")).setSelectedIndex(0);

		((CapsTextbox)this.getFellow("taxiNo")).setValue(null);
		((CapsTextbox)this.getFellow("nric")).setValue(null);
		((Listbox)this.getFellow("toUpdateFMSList")).setSelectedIndex(0);
		((CapsTextbox)this.getFellow("salesDraft")).setText(null);
		((CapsTextbox)this.getFellow("salesDraft")).setValue(null);
		((Decimalbox)this.getFellow("promoAmt")).setValue(null);
		((Decimalbox)this.getFellow("cabRewardsAmt")).setValue(null);

		// disable the fields
		((Row)this.getFellow("FMSRow")).setVisible(false);
		((Row)this.getFellow("FMSRow2")).setVisible(false);
		((Row)this.getFellow("projCodeRow")).setVisible(false);
		((CapsTextbox)this.getFellow("projCode")).setValue(null);
		((Row)this.getFellow("projCodeReasonRow")).setVisible(false);
		((CapsTextbox)this.getFellow("projCodeReason")).setValue(null);
		this.addConstraints();
		clearAcct();
		
	}
	
	private void removeConstraints() throws InterruptedException{
		((Datebox) this.getFellow("endDate")).setConstraint("");
		((Datebox) this.getFellow("startDate")).setConstraint("");
		((CapsTextbox)this.getFellow("remarks")).setConstraint("");
		((CapsTextbox)this.getFellow("pickup")).setConstraint("");
		((CapsTextbox)this.getFellow("destination")).setConstraint("");
		((Decimalbox)this.getFellow("FMSAmount")).setConstraint("");
		((Decimalbox)this.getFellow("fareAmt")).setConstraint("");
		((CapsTextbox)this.getFellow("taxiNo")).setConstraint("");
		((CapsTextbox)this.getFellow("nric")).setConstraint("");
	}
	
	private void addConstraints() throws InterruptedException{
		((Datebox) this.getFellow("endDate")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredBeforeOrEqualsCurrentDateConstraint());
		((Datebox) this.getFellow("startDate")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredBeforeOrEqualsCurrentDateConstraint());
//		((CapsTextbox)this.getFellow("remarks")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox)this.getFellow("pickup")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox)this.getFellow("destination")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((Decimalbox)this.getFellow("fareAmt")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroAmountForFareConstraint());
		((CapsTextbox)this.getFellow("taxiNo")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox)this.getFellow("nric")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
	}
	
	private void clearAcct()
	{
		Label acctNo = (Label) this.getFellow("acctNo");
		acctNo.setValue(null);
		Label name = (Label) this.getFellow("name");
		name.setValue(null);
		Label nameOnCard = (Label) this.getFellow("nameOnCard");
		nameOnCard.setValue(null);
		Label billContact = (Label) this.getFellow("billContact");
		billContact.setValue(null);
		
		Listbox productType = (Listbox) this.getFellow("productType");
		productType.setDisabled(true);
		productType.setSelectedIndex(0);

//		this.setProjectCodeInvisible();
		
		// Note : must remove the required constraint first before setting to blank
		// If not, the required constraint will be triggered and the other actions will be ignored
		((CapsTextbox)this.getFellow("cardNo")).setConstraint("");
		((CapsTextbox) this.getFellow("cardNo")).setText(null);
		((CapsTextbox) this.getFellow("cardNo")).focus();
	}
	
}
