package com.cdgtaxi.ibs.inventory.ui;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.InsufficientInventoryStockException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoReservedException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoUnavailableException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class RequestIssuanceWindow extends CommonWindow implements AfterCompose {
	private static final Logger logger = Logger.getLogger(RequestIssuanceWindow.class);

	private Intbox accountNoIB;
	private Combobox accountNameCB;
	private Listbox divisionLB, departmentLB, itemTypeLB;
	private Row divisionDepartmentRow;
	private Label divisionLbl, departmentLbl;
	private Integer selectedIndex = null;
	private Datebox expiryDateDB;
	private Decimalbox handlingFeeDMB, discountDMB, deliveryChargesDMB, serialNoStartDMB, serialNoEndDMB;

	private Label contactPerson1Lbl, jobTitleLbl, officeContactNoLbl, mobileContactNoLbl,
		faxLbl, emailLbl, contactPerson2Lbl, officeContactNo2Lbl, mobileContactNo2Lbl, 
		email2Lbl, quantityLbl;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		//Populate Item Type
		List<ImtbItemType> itemTypes = this.businessHelper.getInventoryBusiness().getItemTypes();
		for(ImtbItemType itemType : itemTypes){
			itemTypeLB.appendChild(new Listitem(itemType.getTypeName(), itemType));
		}
		if(itemTypeLB.getChildren().size()>0) itemTypeLB.setSelectedIndex(0);
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");
		
		try{
			//Fix to bypass IE6 issue with double spacing
			if(accountNameCB.getChildren().size()==1)
				accountNameCB.setSelectedIndex(0);
			
			if(accountNameCB.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
				selectedIndex = accountNameCB.getSelectedIndex();
				accountNoIB.setText(selectedAccount.getCustNo());
				
				//Update Billing Contact Information
				this.updateBillingContactInformation(selectedAccount);
				
				//Display division or department according to account category
				if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = this.businessHelper.getPaymentBusiness().searchBillableAccountByParentAccount(selectedAccount);
					List<AmtbAccount> departments = this.businessHelper.getPaymentBusiness().searchBillableAccountByGrandParentAccount(selectedAccount);
					this.setDivisionInputVisible(divisions);
					this.setDepartmentInputVisible(departments);
				}
				else{
					this.setDivisionInputInvisible();
					this.setDepartmentInputInvisible();
				}
			}
			else{
				this.setDivisionInputInvisible();
				this.setDepartmentInputInvisible();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectDivision(Listbox divisionListBox) throws InterruptedException{
		logger.info("");
		
		try{
			Object selectedValue = divisionListBox.getSelectedItem().getValue();
			if((selectedValue instanceof String) == false){
				departmentLB.setSelectedIndex(0);
				this.updateBillingContactInformation((AmtbAccount)selectedValue);
			}
			else
				this.updateBillingContactInformation((AmtbAccount)accountNameCB.getSelectedItem().getValue());
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectDepartment(Listbox departmentListBox) throws InterruptedException{
		logger.info("");
		
		try{
			Object selectedValue = departmentListBox.getSelectedItem().getValue();
			if((selectedValue instanceof String) == false){
				divisionLB.setSelectedIndex(0);
				this.updateBillingContactInformation((AmtbAccount)selectedValue);
			}
			else
				this.updateBillingContactInformation((AmtbAccount)accountNameCB.getSelectedItem().getValue());
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.info("");
		
		Integer accountNo = accountNoIB.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameCB.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) return;
		}
		
		//Clear combobox for a new search
		accountNameCB.setText("");
		//Clear list for every new search
		accountNameCB.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		//clear selectedIndex
		selectedIndex = null;
		this.clearBillingContactInformation();
		
		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.info("");
		//only begin new search if input is greater than 2
		if(name.length()<3) return;
		
		//accountName still the same as selected one, skip
		if(accountNameCB.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")"))
				return;
		}
		
		//clear textbox for a new search
		accountNoIB.setText("");
		//Clear list for every new search
		accountNameCB.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		//clear selectedIndex
		selectedIndex = null;
		this.clearBillingContactInformation();
		
		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameCB.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void setDivisionInputVisible(List<AmtbAccount> divisions){
		divisionDepartmentRow.setVisible(true);
		divisionLbl.setVisible(true);
		divisionLB.setVisible(true);
		divisionLB.getChildren().clear();
		divisionLB.appendChild(ComponentUtil.createNotRequiredListItem());
		
		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+" ("+division.getCode()+")");
			newItem.setValue(division);
			divisionLB.appendChild(newItem);
		}
		// Focus on division box so that the onchanging event for search account name will not be triggered.
		if (divisionLB.getItemCount()> 0)
			divisionLB.focus();
	}
	
	private void setDepartmentInputVisible(List<AmtbAccount> departments){
		departmentLbl.setVisible(true);
		departmentLB.setVisible(true);
		departmentLB.getChildren().clear();
		departmentLB.appendChild(ComponentUtil.createNotRequiredListItem());
		
		for(AmtbAccount department : departments){
			Listitem newItem = new Listitem(department.getAccountName()+" ("+department.getCode()+")");
			newItem.setValue(department);
			departmentLB.appendChild(newItem);
		}
	}
	
	private void setDivisionInputInvisible(){
		divisionDepartmentRow.setVisible(false);
		divisionLbl.setVisible(false);
		divisionLB.setVisible(false);
		divisionLB.getChildren().clear();
	}
	
	private void setDepartmentInputInvisible(){
		departmentLbl.setVisible(false);
		departmentLB.setVisible(false);
		departmentLB.getChildren().clear();
	}

	public void requestIssuance() throws InterruptedException {
		try {
			//Fix to bypass IE6 issue with double spacing
			if(accountNameCB.getChildren().size()==1)
				accountNameCB.setSelectedIndex(0);
			else if(selectedIndex != null)
				accountNameCB.setSelectedIndex(selectedIndex);
			
			AmtbAccount account;
			if (departmentLB.getSelectedIndex()>0) {
				account = (AmtbAccount) departmentLB.getSelectedItem().getValue();
			} else if (divisionLB.getSelectedIndex()>0) {
				account = (AmtbAccount) divisionLB.getSelectedItem().getValue();
			} else {
				if(accountNameCB.getSelectedItem()==null)
					throw new WrongValueException(accountNameCB, "* Mandatory field");
				
				account = (AmtbAccount) accountNameCB.getSelectedItem().getValue();
			}

			ImtbItemType itemType = (ImtbItemType) itemTypeLB.getSelectedItem().getValue();
			AmtbContactPerson contactPerson = this.updateBillingContactInformation(account);
			Long quantity = this.updateQuantity();
			String requestoRemarks = ((CapsTextbox) this.getFellow("requestorRemarks")).getValue();
			
			ImtbIssueReq request = new ImtbIssueReq();
			request.setImtbItemType(itemType);
			request.setQuantity(quantity);
			request.setAmtbAccount(account);
			request.setCreateBy(getUserLoginIdAndDomain());
			request.setAmtbContactPerson(contactPerson);
			request.setDeliveryCharges(deliveryChargesDMB.getValue());
			request.setDiscount(discountDMB.getValue());
			request.setExpiryDate(DateUtil.convertUtilDateToSqlDate(expiryDateDB.getValue()));
			request.setHandlingFee(handlingFeeDMB.getValue());
			request.setSerialNoStart(serialNoStartDMB.getValue().setScale(0, BigDecimal.ROUND_HALF_UP));
			request.setSerialNoEnd(serialNoEndDMB.getValue().setScale(0, BigDecimal.ROUND_HALF_UP));
			request.setRequestorRemarks(requestoRemarks);
			displayProcessing();
			businessHelper.getInventoryBusiness().requestIssuance(request);
			Messagebox.show("Request has been successfully submitted", "Request Issuance",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} 
		catch (WrongValueException wve) {
			throw wve;
		}
		catch (InsufficientInventoryStockException e) {
			Messagebox.show(e.getMessage(), "Insufficient Stock", Messagebox.OK, Messagebox.ERROR);
		}
		catch (InventorySerialNoReservedException e) {
			Messagebox.show(e.getMessage(), "Serial No Reserved", Messagebox.OK, Messagebox.ERROR);
		}
		catch (InventorySerialNoUnavailableException e) {
			Messagebox.show(e.getMessage(), "Serial No Unavailable", Messagebox.OK, Messagebox.ERROR);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Override
	public CommonWindow back() throws InterruptedException{
		logger.info("");
		CommonWindow previousPage = this.getPreviousPage();
		previousPage.setVisible(true);
		this.detach();
		return previousPage;
	}

	@Override
	public void refresh() throws InterruptedException {
		
	}
	
	private AmtbContactPerson updateBillingContactInformation(AmtbAccount account){
		
		this.clearBillingContactInformation();
		AmtbContactPerson contactPerson = this.businessHelper.getAccountBusiness().getMainContactByType(account.getAccountNo(), NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);
		
		contactPerson1Lbl.setValue(contactPerson.getMainContactName());
		if(contactPerson.getMainContactTitle()!=null && contactPerson.getMainContactTitle().length()>0)
			jobTitleLbl.setValue(contactPerson.getMainContactTitle());
		if(contactPerson.getMainContactTel()!=null && contactPerson.getMainContactTel().length()>0)
			officeContactNoLbl.setValue(contactPerson.getMainContactTel());
		if(contactPerson.getMainContactMobile()!=null && contactPerson.getMainContactMobile().length()>0)
			mobileContactNoLbl.setValue(contactPerson.getMainContactMobile());
		if(contactPerson.getMainContactFax()!=null && contactPerson.getMainContactFax().length()>0)
			faxLbl.setValue(contactPerson.getMainContactFax());
		if(contactPerson.getMainContactEmail()!=null && contactPerson.getMainContactEmail().length()>0)
			emailLbl.setValue(contactPerson.getMainContactEmail());
		if(contactPerson.getSubContactName()!=null && contactPerson.getSubContactName().length()>0)
			contactPerson2Lbl.setValue(contactPerson.getSubContactName());
		if(contactPerson.getSubContactTel()!=null && contactPerson.getSubContactTel().length()>0)
			officeContactNo2Lbl.setValue(contactPerson.getSubContactTel());
		if(contactPerson.getSubContactMobile()!=null && contactPerson.getSubContactMobile().length()>0)
			mobileContactNo2Lbl.setValue(contactPerson.getSubContactMobile());
		if(contactPerson.getSubContactEmail()!=null && contactPerson.getSubContactEmail().length()>0)
			email2Lbl.setValue(contactPerson.getSubContactEmail());
		
		return contactPerson;
	}
	
	private void clearBillingContactInformation(){
		contactPerson1Lbl.setValue("");
		jobTitleLbl.setValue("");
		officeContactNoLbl.setValue("");
		mobileContactNoLbl.setValue("");
		faxLbl.setValue("");
		emailLbl.setValue("");
		contactPerson2Lbl.setValue("");
		officeContactNo2Lbl.setValue("");
		mobileContactNo2Lbl.setValue("");
		email2Lbl.setValue("");
	}
	
	public Long updateQuantity(){
		if(serialNoStartDMB.getValue()!=null && serialNoEndDMB.getValue()!=null){
			BigDecimal serialNoStart = serialNoStartDMB.getValue().setScale(0, BigDecimal.ROUND_HALF_UP);
			BigDecimal serialNoEnd = serialNoEndDMB.getValue().setScale(0, BigDecimal.ROUND_HALF_UP);
			if(serialNoStart.compareTo(serialNoEnd) >0)
				throw new WrongValueException(serialNoStartDMB, "Serial No Start cannot be greater than Serial No End");
			
			BigDecimal difference = serialNoEnd.subtract(serialNoStart).add(BigDecimal.ONE);
			quantityLbl.setValue(StringUtil.bigDecimalToString(difference, StringUtil.DECIMAL_IN_INTEGER_FORMAT));
			return difference.longValue();
		}
		else return null;
	}
}
