package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class IssueProductWindow extends CommonIssueProductWindow implements AfterCompose {

	
	private static Logger logger = Logger.getLogger(IssueProductWindow.class);
	
	private Grid contactDetailsGridTitle, contactDetailsGrid;
	private Label contactNameLabel, contactNumberLabel, contactAddressLabel;
	private Button viewIssuanceHistBtn, viewAcctAgingBtn;
	private Grid issueButtonGrid;
	
	@Override
	public void onCreate(CreateEvent ce) throws Exception {
		super.onCreate(ce);
		setDivDeptWindowType(TYPE_DEPEND);
		
		if (!this.checkUriAccess(Uri.PRODUCT_ISSUE_HISTORY))
			viewIssuanceHistBtn.setDisabled(true);
		if (!this.checkUriAccess(Uri.VIEW_ACCOUNT_AGING))
			viewAcctAgingBtn.setDisabled(true);
		
		populateDetails();
	}

	public void ViewIssuanceHistory() throws SuspendNotAllowedException, InterruptedException {

		Integer accountNo = selectedAccount.getAccountNo();
		PmtbProductType productType = ComponentUtil.getSelectedItem(productTypeListBox);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("accountId", String.valueOf(accountNo));
		map.put("productType", productType.getProductTypeId());

		final Window win = (Window) Executions.createComponents(Uri.PRODUCT_ISSUE_HISTORY, null, map);
		win.setMaximizable(true);
		win.setClosable(true);
		win.doModal();

	}

	public void viewAccountAging() throws InterruptedException {

		Integer accountNo = selectedAccount.getAccountNo();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("accountNo", String.valueOf(accountNo));
		final Window win = (Window) Executions.createComponents(Uri.VIEW_ACCOUNT_AGING, null, map);
		win.setClosable(true);
		win.doModal();

	}

	
	@Override
	public void reset() throws InterruptedException {
		super.reset();
		resetProductDetails();
	}

	public void populateDetails() throws SuspendNotAllowedException, InterruptedException {

		super.populateDetails();
		populateShippingContactDetails();
		
	}
	

	private void populateShippingContactDetails() {

		boolean showContactDetails = false;
		if (selectedAccount != null) {

			showContactDetails = true;
			AmtbContactPerson contactPerson = this.businessHelper.getAccountBusiness().getMainContactByType(selectedAccount.getAccountNo(), NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING);
			String contactName = "-";
			String contactNo = "-";
			String address = "";

			if (contactPerson != null) {

				contactName = contactPerson.getMainContactName();

				String contactMobile = contactPerson.getMainContactMobile();
				String contactTel = contactPerson.getMainContactTel();

				List<String> contactList = Lists.newArrayList();
				if (!Strings.isNullOrEmpty(contactMobile)) {
					contactList.add(contactMobile);
				}
				if (!Strings.isNullOrEmpty(contactTel)) {
					contactList.add(contactTel);
				}

				if (!contactList.isEmpty()) {
					contactNo = Joiner.on(", ").join(contactList);
				}

				// [AREA][BLOCK] [STREET] [UNIT][BUILDING NAME][COUNTRY][STATE, CITY (hide if SINGAPORE)] [POSTAL CODE]
				String block = contactPerson.getAddressBlock();
				String building = contactPerson.getAddressBuilding();
				String area = contactPerson.getAddressArea();
				String city = contactPerson.getAddressCity();
				String state = contactPerson.getAddressCity();
				String country = contactPerson.getMstbMasterTableByAddressCountry().getMasterValue();
				String postal = contactPerson.getAddressPostal();
				String street = contactPerson.getAddressStreet();
				String unit = contactPerson.getAddressUnit();

				if (area != null)
					address = area + ", ";
				if (block != null)
					address = block + ", ";
				if (street != null)
					address = address + street + ", ";
				if (unit != null)
					address = address + unit + ", ";
				if (building != null)
					address = address + building + ", ";
				if (country != null)
					address = address + country + " ";
				if (state != null)
					address = address + state + ", ";
				if (city != null)
					address = address + city + ", ";
				if (postal != null)
					address = address + postal;

			}

			contactNameLabel.setValue(contactName);
			contactNumberLabel.setValue(contactNo);
			contactAddressLabel.setValue(address);
		}

		contactDetailsGridTitle.setVisible(showContactDetails);
		contactDetailsGrid.setVisible(showContactDetails);

	}

	

	@Override
	public void refresh() throws InterruptedException {

	}

	
	public void save() throws InterruptedException {

		
		try {

			checkAccountNotNull();

			// Do not do anything if accNo is empty
			if (selectedAccount != null) {
				
				Boolean checkEmployeeId = false;
				
				if (!ComponentUtil.confirmBox("Issue Card(s)?", "Create Product Confirmation ")) {
					return;
				}
				
				if(coporateCheckBox.isChecked())
				{
					//check if email & employeeId is empty
					Boolean checkIndustry = this.businessHelper.getProductBusiness().getCheckProductIndustry(null, selectedAccount);

					if(checkIndustry)
					{
						if((employeeIdField.getValue() != null && employeeIdField.getValue().trim().equals("")) ||
								(emailField.getValue() != null && emailField.getValue().trim().equals("")) )
						{
							if (!ComponentUtil.confirmBox("Continue without <employee id>/<email address> ?", "Edit Product")) {
								return;
							}
						}
					}
				}
				displayProcessing();
				
				IssueProductForm issueProductForm = getIssueProductForm();
				
				List<PmtbProduct> issuedProducts = this.businessHelper.getProductBusiness().issueProduct(issueProductForm);

				// if save successfully, re-populate the product details but keep card type and card limit
				
				//creditLimitField
				
				PmtbProductType keepProductType = ComponentUtil.getSelectedItem(productTypeListBox);
				BigDecimal keepCreditLimit = creditLimitField.getValue();
				
				resetProductDetails();
				populateProductDetails();
				
				ComponentUtil.setSelectedItem(productTypeListBox, keepProductType);
				creditLimitField.setValue(keepCreditLimit);
				
				String accountName = "-";
				String division = "-";
				String subApplicant = "-";
				String corpOrPersonal = "";
				String department = "-";
				
				
				AmtbAccount runningAcct = selectedAccount;
				do {
					
					String accountCategory = runningAcct.getAccountCategory();
					if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
						accountName = runningAcct.getAccountName();
						corpOrPersonal = "corp";
					} else if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
						department = runningAcct.getAccountName();
					} else if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
						division = runningAcct.getAccountName();
					} else if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
						accountName = runningAcct.getAccountName();
						corpOrPersonal = "personal";
					} else if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)){
						subApplicant = runningAcct.getAccountName();
					}
				
					runningAcct = runningAcct.getAmtbAccount();
				}
				while(runningAcct!=null);
				
				Map<String, Map<String, String>> cardInfoMap = new LinkedHashMap<String, Map<String, String>>();
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				for (PmtbProduct issuedProduct : issuedProducts) {
					// Parse the data into Map for View Issued Card Info
					dataMap.put(issuedProduct.getCardNo(), issuedProduct.getCardNo());
					if (issuedProduct.getExpiryDate() != null)
						dataMap.put("expiry" + issuedProduct.getCardNo(), DateUtil.convertDateToStr(issuedProduct.getExpiryDate(), DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
					dataMap.put("issue" + issuedProduct.getCardNo(), DateUtil.convertDateToStr(issuedProduct.getIssueDate(), DateUtil.GLOBAL_DATE_FORMAT));
					dataMap.put("accountName", accountName);
					dataMap.put("subApplicant", subApplicant);
					dataMap.put("corpOrPersonal", corpOrPersonal);
					dataMap.put("division", division);
					dataMap.put("department", department);
					dataMap.put("nameOnCard", issuedProduct.getNameOnProduct());
					dataMap.put("contatct", contactNameLabel.getValue());
					cardInfoMap.put(issuedProduct.getCardNo(), dataMap);
				}

				if (!cardInfoMap.isEmpty()) {
					this.forward(Uri.VIEW_ISSUED_PRODUCTS, cardInfoMap);
				}

			}
		}
		catch(HibernateOptimisticLockingFailureException e){
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("There are other users issue the same product type and causing concurrency exception, please retry to issue the product.");
		}
		catch (Exception e) {

			String msg = "Failed to issue product. Please contact system administrator.";
			if (e instanceof WrongValueException) {
				msg = e.getMessage();
				logger.debug("error message: " + msg);
				//LoggerUtil.printStackTrace(logger, e);
			} else {
				LoggerUtil.printStackTrace(logger, e);
			}

			Messagebox.show(msg, "Issue Product", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Override
	public List<PmtbProductType> getIssuableProductTypes(AmtbAccount account) {
		return this.businessHelper.getProductBusiness().getIssuableProductTypes(String.valueOf(account.getAccountNo()), false);
	}
	
	
	public void afterPopulatedProductDetails(){
		super.afterPopulatedProductDetails();
		
		//if product details shown, then show button
		boolean isProductDetailsShown = commonProductDetailsGrid.isVisible();
		issueButtonGrid.setVisible(isProductDetailsShown);
		
	}

	@Override
	public List<AmtbAccount> populateDivisionAccounts(AmtbAccount topAcct) {

		return this.businessHelper.getProductBusiness().getActiveDivOrSubApplList(String.valueOf(topAcct.getAccountNo()));
	}

	@Override
	public List<AmtbAccount> populateDepartmentAccounts(AmtbAccount divAccount) {
		return this.businessHelper.getProductBusiness().getActiveDepartmentByDivisionList(String.valueOf(divAccount.getAccountNo()));
	}
	
	public void onSelectProductType2() {
		onSelectProductType();
		
		PmtbProductType selectedProductType = ComponentUtil.getSelectedItem(productTypeListBox);
		boolean isVirtual = NonConfigurableConstants.getBoolean(selectedProductType.getVirtualProduct());

		if(isVirtual)
		{
			coporateCheckBox.setChecked(true);
			coporateCheckBox.setDisabled(true);
			((Label) this.getFellow("mobileLabel")).setSclass("fieldLabel required");
		}
		else
		{
			coporateCheckBox.setDisabled(false);
			coporateCheckBox.setChecked(false);
			((Label) this.getFellow("mobileLabel")).setSclass("fieldLabel");
			((CapsTextbox) this.getFellow("mobileField")).setValue("");
		}
		onCheckCorporate();
	}
	
	public void populateProductDetails() {
		logger.debug("populateProductDetails");
		boolean showProductDetails = false;
		try {
			
			if (selectedAccount != null) 
			{
				logger.debug("populateProductDetails - selected account is not null");
			
				// get issue able product type
				List<PmtbProductType> issuableProductTypes = getIssuableProductTypes(selectedAccount);
				if (issuableProductTypes.isEmpty()) {
					throw new WrongValueException(emptyIssuableProductTypeErrorMsg());
				}

				showProductDetails = true;
				// build product type list box
				Map<PmtbProductType, String> productTypeMap = Maps.newHashMap();
				for (PmtbProductType type : issuableProductTypes) {
					productTypeMap.put(type, type.getName());
				}
				productTypeListBox.getItems().clear();
				ComponentUtil.buildListbox(productTypeListBox, productTypeMap, false);
				// set corporate card as default if exists
				@SuppressWarnings("rawtypes")
				List productTypeList = productTypeListBox.getItems();
				int productTypeListSize = productTypeList.size();
				for (int i = 0; i < productTypeListSize; i++) {
					Listitem item = (Listitem) productTypeList.get(i);
					PmtbProductType type = (PmtbProductType) item.getValue();
					if (NonConfigurableConstants.CORPORATE_CARD_ID.equals(type.getProductTypeId())) {
						productTypeListBox.setSelectedIndex(i);
						break;
					}
				}
		
				coporateCheckBox.setChecked(false);
				waiveSubscFeeChkBox.setChecked(true);
			
				onCheckCorporate();
				onSelectProductType2();
		
			}
		} catch (WrongValueException e) {
			throw e;
		} finally {
			commonProductDetailsGridTitle.setVisible(showProductDetails);
			commonProductDetailsGrid.setVisible(showProductDetails);
			afterPopulatedProductDetails();
		}

	}

}
