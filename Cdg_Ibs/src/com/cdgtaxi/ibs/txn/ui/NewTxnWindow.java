package com.cdgtaxi.ibs.txn.ui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
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
import com.cdgtaxi.ibs.common.model.PmtbProductRetag;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.IncentiveAmountConstraint;

public class NewTxnWindow extends CommonWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8606615269170638410L;
	private static Logger logger = Logger.getLogger(NewTxnWindow.class);

	public NewTxnWindow() {

	}

	public void onCreate() {
		CapsTextbox productNoTextBox = (CapsTextbox) this.getFellow("cardNo");
		productNoTextBox.focus();
	}

	@Override
	public void refresh() throws InterruptedException {

	}

	public List<Listitem> getProductTypes() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getAllProductTypes(), false);
		return cloneList(productTypeList);
	}

	public List<Listitem> getServiceProvider() {
		List<Listitem> serviceProviderList = ComponentUtil.convertToListitems(ConfigurableConstants.getServiceProvider(), false);
		return cloneList(serviceProviderList);
	}

	protected List<Listitem> cloneList(List<Listitem> listitems) {
		List<Listitem> returnList = new ArrayList<Listitem>();
		for (Listitem listitem : listitems) {
			returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
		}
		return returnList;
	}

	public List<Listitem> getJobType() {
		List<Listitem> jobTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getJobType(), false);
		return cloneList(jobTypeList);
	}

	public List<Listitem> getVehicleType() {
		List<Listitem> vehicleTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleModel(), false);
		return cloneList(vehicleTypeList);
	}

	public void onSelectProductNoOnly() throws InterruptedException {
		logger.info("OnSelectProductNoOnly");

		try {
			CapsTextbox productNoTextBox = (CapsTextbox) this.getFellow("cardNo");

			if (productNoTextBox != null && !"".equals(productNoTextBox)) {
				// Retrieve product information and account information
				if (productNoTextBox.getText() != null && !"".equals(productNoTextBox.getText())) {
					Timestamp startDate = null;
					startDate = DateUtil.getCurrentTimestamp();

					AmtbAccount acct = this.businessHelper.getTxnBusiness().getAccount(productNoTextBox.getText(), startDate);

					// Set the acct info
					if (acct != null) {

						// Set main contact information
						Iterator<PmtbProduct> productIterator = acct.getPmtbProducts().iterator();
						// Get the first product since there should only be 1
						PmtbProduct pmtbProduct = productIterator.next();

						// check if the product has been re-tagged before using
						// internal product_no. If it is being re-tagged, used
						// the correct re-tagged account
						// based on trip-date
						PmtbProductRetag pmtbProductRetag = this.businessHelper.getTxnBusiness().getRetagProductsByDate(pmtbProduct.getProductNo(), startDate);
						if (pmtbProductRetag != null) {
							// There is a retagged. set account to this retagged
							// account
							acct = pmtbProductRetag.getAmtbAccountByCurrentAccountNo();
						}

						Label nameOnCard = (Label) this.getFellow("nameOnCard");
						nameOnCard.setValue(pmtbProduct.getNameOnProduct());

						if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)) {

							// Set the necessary info for Corporate
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(acct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(acct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division, department, applicant and
							// subapplicant invisible
							setDivisionInvisible();
							setDepartmentInvisible();
							setSubApplicantInvisible();
//							checkProjectCode(acct);

						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)) {
							// Set the necessary info for Corporate
							AmtbAccount corpAcct = acct.getAmtbAccount();
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(corpAcct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(corpAcct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(corpAcct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division visible
							setDivisionVisible(acct.getAccountName(), acct.getCode());

							// Set department, applicant and subapplicant
							// invisible
							setDepartmentInvisible();
							setSubApplicantInvisible();
//							checkProjectCode(corpAcct);
						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
							AmtbAccount divAcct = acct.getAmtbAccount();
							AmtbAccount corpAcct = divAcct.getAmtbAccount();
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(corpAcct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(corpAcct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(corpAcct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division visible
							setDivisionVisible(divAcct.getAccountName(), divAcct.getCode());
							setDepartmentVisible(acct.getAccountName(), acct.getCode());
							setSubApplicantInvisible();
//							checkProjectCode(corpAcct);

						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
							// Set the necessary info for Corporate
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(acct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(acct.getAccountName());
							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division, department, applicant and
							// subapplicant invisible
							setDivisionInvisible();
							setDepartmentInvisible();
							setSubApplicantInvisible();
//							checkProjectCode(acct);

						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
							// Set the necessary info for Personal
							AmtbAccount applicantAcct = acct.getAmtbAccount();
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(applicantAcct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(applicantAcct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(applicantAcct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set sub applicant visible
							setSubApplicantVisible(acct.getAccountName());

							// Set department, applicant and subapplicant
							// invisible
							setDepartmentInvisible();
							setDivisionInvisible();
							// Depend on account proj description flag, open up
							// the
							// project description field
//							checkProjectCode(applicantAcct);
						}
						// Get contact
						AmtbAcctMainContact amtbAccountMainContact = this.businessHelper.getTxnBusiness().getMainBillingContact(acct);

						String mainContactName = amtbAccountMainContact.getAmtbContactPerson().getMainContactName();
						String secContactName = amtbAccountMainContact.getAmtbContactPerson().getSubContactName();
						Label billContact = (Label) this.getFellow("billContact");
						if (mainContactName != null && !"".equals(mainContactName)) {
							if (secContactName != null && !"".equals(secContactName)) {
								billContact.setValue(mainContactName + "/" + secContactName);
							} else {
								billContact.setValue(mainContactName);
							}
						}

						// Default product type
						PmtbProductType productType = this.businessHelper.getTxnBusiness().getProductType(productNoTextBox.getText(), startDate);
						String cardProductType = productType.getProductTypeId();
						Listbox productTypeListbox = (Listbox) this.getFellow("productType");
						List<Listitem> listItems = productTypeListbox.getItems();
						for (Listitem listItem : listItems) {
							if (((String) listItem.getValue()).equals(cardProductType)) {
								listItem.setSelected(true);
								((Listbox) this.getFellow("productType")).setDisabled(true);
								break;
							}
						}
						
						Row row = (Row) this.getFellow("prepaidAdminRow");
						boolean isPrepaid = NonConfigurableConstants.getBoolean(productType.getPrepaid());
						row.setVisible(isPrepaid);

					} else {
						// Clear
						this.clearAcct();
						Messagebox.show("There is no such card in the system", "Information", Messagebox.OK, Messagebox.INFORMATION);
						((CapsTextbox) this.getFellow("cardNo")).focus();
					}
				}
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}

	public void onSelectProductNo() throws InterruptedException {
		logger.info("OnSelectProductNo");

		try {
			CapsTextbox productNoTextBox = (CapsTextbox) this.getFellow("cardNo");

			if (productNoTextBox != null && !"".equals(productNoTextBox)) {
				// Retrieve product information and account information
				if (productNoTextBox.getText() != null && !"".equals(productNoTextBox.getText())) {
					// Check for start date/time
					// If the user did not enter the start date, get current
					// start date
					Datebox startDateBox = (Datebox) this.getFellow("startDate");
					Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
					Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
					Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
					Timestamp startDate = null;
					if (startDateBox != null) {
						if (startDateBox.getValue() != null) {
							Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
									Integer.parseInt(startTimeMin.getSelectedItem().getLabel()), Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
							startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
						} else {
							// Ignore the Time fields
							startDate = DateUtil.convertDateToTimestamp(new Date());
						}
					} else {
						throw new Exception("Date Box should not be null");
					}

					AmtbAccount acct = this.businessHelper.getTxnBusiness().getAccount(productNoTextBox.getText(), startDate);

					// Set the acct info
					if (acct != null) {
						// Set main contact information
						Iterator<PmtbProduct> productIterator = acct.getPmtbProducts().iterator();
						// Get the first product since there should only be 1
						PmtbProduct pmtbProduct = productIterator.next();

						// check if the product has been re-tagged before using
						// internal product_no. If it is being re-tagged, used
						// the correct re-tagged account
						// based on trip-date
						PmtbProductRetag pmtbProductRetag = this.businessHelper.getTxnBusiness().getRetagProductsByDate(pmtbProduct.getProductNo(), startDate);
						if (pmtbProductRetag != null) {
							// There is a retagged. set account to this retagged
							// account
							acct = pmtbProductRetag.getAmtbAccountByCurrentAccountNo();
						}

						if (NonConfigurableConstants.PRODUCT_STATUS_RECYCLED.equalsIgnoreCase(pmtbProduct.getCurrentStatus())) {
							Messagebox.show("The card has been recycled. Please verify that the Trip Start Date and Time.", "Information", Messagebox.OK, Messagebox.INFORMATION);
						}

						// Iterator<PmtbProductStatus> statusIterator =
						// pmtbProduct.getPmtbProductStatuses().iterator();
						// String status = statusIterator.next().getStatusTo();
						// logger.info("Status :" + status);
						// DO not need to check the status - might change mind
						// anytime so block off currently
						// if
						// (NonConfigurableConstants.PRODUCT_STATUS_ACTIVE.equals(status))
						// {
						// Get contact
						AmtbAcctMainContact amtbAccountMainContact = this.businessHelper.getTxnBusiness().getMainBillingContact(acct);
						String mainContactName = amtbAccountMainContact.getAmtbContactPerson().getMainContactName();
						String secContactName = amtbAccountMainContact.getAmtbContactPerson().getSubContactName();
						Label billContact = (Label) this.getFellow("billContact");
						if (mainContactName != null && !"".equals(mainContactName)) {
							if (secContactName != null && !"".equals(secContactName)) {
								billContact.setValue(mainContactName + "/" + secContactName);
							} else {
								billContact.setValue(mainContactName);
							}
						}

						Label nameOnCard = (Label) this.getFellow("nameOnCard");
						nameOnCard.setValue(pmtbProduct.getNameOnProduct());

						if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)) {
							// Set the necessary info for Corporate
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(acct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(acct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division, department, applicant and
							// subapplicant invisible
							setDivisionInvisible();
							setDepartmentInvisible();
							setSubApplicantInvisible();
//							checkProjectCode(acct);

						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)) {
							// Set the necessary info for Corporate
							AmtbAccount corpAcct = acct.getAmtbAccount();
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(corpAcct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(corpAcct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(corpAcct.getCustNo().toString())) {
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division visible
							setDivisionVisible(acct.getAccountName(), acct.getCode());

							// Set department, applicant and subapplicant
							// invisible
							setDepartmentInvisible();
							setSubApplicantInvisible();
//							checkProjectCode(corpAcct);
						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
							AmtbAccount divAcct = acct.getAmtbAccount();
							AmtbAccount corpAcct = divAcct.getAmtbAccount();
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(corpAcct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(corpAcct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(corpAcct.getCustNo().toString())) {

								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division visible
							setDivisionVisible(divAcct.getAccountName(), divAcct.getCode());
							setDepartmentVisible(acct.getAccountName(), acct.getCode());
							setSubApplicantInvisible();
//							checkProjectCode(corpAcct);
						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
							// Set the necessary info for Corporate
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(acct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(acct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString())) {

								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								// Clear
								this.clearAcct();
								return;
							}

							// Set division, department, applicant and
							// subapplicant invisible
							setDivisionInvisible();
							setDepartmentInvisible();
							setSubApplicantInvisible();
//							checkProjectCode(acct);
						} else if (acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
							// Set the necessary info for Personal
							AmtbAccount applicantAcct = acct.getAmtbAccount();
							Label acctNo = (Label) this.getFellow("acctNo");
							acctNo.setValue(applicantAcct.getCustNo());
							Label name = (Label) this.getFellow("name");
							name.setValue(applicantAcct.getAccountName());

							// Validation - Closed account cannot create
							// transaction
							// Only need to check corporate or applicant. Not
							// required for division as it will be terminated
							// instead of closed.
							if (this.businessHelper.getTxnBusiness().isAccountClosed(applicantAcct.getCustNo().toString())) {
								// Clear
								Messagebox.show("The corporate/applicant account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
								this.clearAcct();
								return;
							}
							// Set sub applicant visible
							setSubApplicantVisible(acct.getAccountName());

							// Set department, applicant and subapplicant
							// invisible
							setDepartmentInvisible();
							setDivisionInvisible();
//							checkProjectCode(applicantAcct);
						}

						// Default product type
						PmtbProductType productType = this.businessHelper.getTxnBusiness().getProductType(productNoTextBox.getText(), startDate);
						String cardProductType = productType.getProductTypeId();
								
						Listbox productTypeListbox = (Listbox) this.getFellow("productType");
						List<Listitem> listItems = productTypeListbox.getItems();
						for (Listitem listItem : listItems) {
							if (((String) listItem.getValue()).equals(cardProductType)) {
								listItem.setSelected(true);
								((Listbox) this.getFellow("productType")).setDisabled(true);
								break;
							}
						}

						Row row = (Row) this.getFellow("prepaidAdminRow");
						boolean isPrepaid = NonConfigurableConstants.getBoolean(productType.getPrepaid());
						row.setVisible(isPrepaid);
						
						
					} else {
						// retrieve the last status of the product
						PmtbProductStatus pmtbProductStatus = this.businessHelper.getTxnBusiness().getEarliestProductIssuedStatus(productNoTextBox.getText());
						if (pmtbProductStatus != null) {
							// this.clearAcct();
							Messagebox.show("The card is issued on " + DateUtil.convertTimestampToStr(pmtbProductStatus.getStatusDt(), DateUtil.TRIPS_DATE_FORMAT)
									+ ". Please change the Trip Start Date and Time to be after the issue date.", "Information", Messagebox.OK, Messagebox.INFORMATION);
						} else {
							// Clear
							this.clearAcct();
							Messagebox.show("There is no such card in the system", "Information", Messagebox.OK, Messagebox.INFORMATION);
						}
					}
				}
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}

	public void onSelectUpdateFMS() throws InterruptedException {
		logger.info("onSelectUpdateFMS");

		try {
			Listbox toUpdateFMSListBox = (Listbox) this.getFellow("toUpdateFMSList");
			String selectedValue = (String) toUpdateFMSListBox.getSelectedItem().getValue();

			if (NonConfigurableConstants.BOOLEAN_YES.equals(selectedValue)) {
				// enable the fields
				((Row) this.getFellow("FMSRow")).setVisible(true);
				((Row) this.getFellow("FMSRow2")).setVisible(true);
				((Row) this.getFellow("FMSRow3")).setVisible(true);
				((Decimalbox) this.getFellow("FMSAmount")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroOrGreaterConstraint());
				((Decimalbox) this.getFellow("incentiveAmt")).setConstraint(new com.cdgtaxi.ibs.web.constraint.IncentiveAmountConstraint());
			} else {
				// disable the fields
				((Row) this.getFellow("FMSRow")).setVisible(false);
				((Row) this.getFellow("FMSRow2")).setVisible(false);
				((Row) this.getFellow("FMSRow3")).setVisible(false);
				((Decimalbox) this.getFellow("FMSAmount")).setConstraint("");
				((Decimalbox) this.getFellow("incentiveAmt")).setConstraint("");
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}

	private void clearAcct() {
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
		this.setDivisionInvisible();
		this.setDepartmentInvisible();
		this.setSubApplicantInvisible();
//		this.setProjectCodeInvisible();

		// Note : must remove the required constraint first before setting to
		// blank
		// If not, the required constraint will be triggered and the other
		// actions will be ignored
		((CapsTextbox) this.getFellow("cardNo")).setConstraint("");
		((CapsTextbox) this.getFellow("cardNo")).setText(null);
		((CapsTextbox) this.getFellow("cardNo")).focus();
	}

	private void setDivisionVisible(String divName, String divCode) {
		((Row) this.getFellow("divisionDepartmentRow")).setVisible(true);
		((Label) this.getFellow("divisionLabel")).setVisible(true);
		Label division = (Label) this.getFellow("division");
		division.setVisible(true);
		division.setValue(divName + " (" + divCode + ")");
	}

	private void setDepartmentVisible(String deptName, String deptCode) {
		((Label) this.getFellow("departmentLabel")).setVisible(true);
		Label department = (Label) this.getFellow("department");
		department.setVisible(true);
		department.setValue(deptName + " (" + deptCode + ")");
	}

	private void setSubApplicantVisible(String subApplicantName) {
		((Row) this.getFellow("applicantSubApplicantRow")).setVisible(true);
		((Label) this.getFellow("subApplicantLabel")).setVisible(true);
		Label subApplicant = (Label) this.getFellow("subApplicant");
		subApplicant.setVisible(true);
		subApplicant.setValue(subApplicantName);
	}

	private void setDivisionInvisible() {
		((Row) this.getFellow("divisionDepartmentRow")).setVisible(false);
		((Label) this.getFellow("divisionLabel")).setVisible(false);
		Label division = (Label) this.getFellow("division");
		division.setVisible(false);
		division.setValue(null);
	}

	private void setDepartmentInvisible() {
		((Label) this.getFellow("departmentLabel")).setVisible(false);
		Label department = (Label) this.getFellow("department");
		department.setVisible(false);
		department.setValue(null);
	}

	private void setSubApplicantInvisible() {
		((Row) this.getFellow("applicantSubApplicantRow")).setVisible(false);
		((Label) this.getFellow("subApplicantLabel")).setVisible(false);
		Label subApplicant = (Label) this.getFellow("subApplicant");
		subApplicant.setVisible(false);
		subApplicant.setValue(null);
	}

//	private void setProjectCodeVisible() {
//		((Row) this.getFellow("projCodeRow")).setVisible(true);
//		CapsTextbox projCode = (CapsTextbox) this.getFellow("projCode");
//		
//		((Row) this.getFellow("projCodeReasonRow")).setVisible(true);
//		CapsTextbox projCodeReason = (CapsTextbox) this.getFellow("projCodeReason");
//	}
//
//	private void setProjectCodeInvisible() {
//		((Row) this.getFellow("projCodeRow")).setVisible(false);
//		CapsTextbox projCode = (CapsTextbox) this.getFellow("projCode");
//		projCode.setValue(null);
//		
//		((Row) this.getFellow("projCodeReasonRow")).setVisible(false);
//		CapsTextbox projCodeReason = (CapsTextbox) this.getFellow("projCodeReason");
//		projCodeReason.setValue(null);
//	}
//
//	private void checkProjectCode(AmtbAccount acct) {
//		if (acct.getAmtbCorporateDetails() != null) {
//			if (acct.getAmtbCorporateDetails().iterator().hasNext()) {
//				AmtbCorporateDetail amtbCorporateDetail = acct.getAmtbCorporateDetails().iterator().next();
//				if (NonConfigurableConstants.BOOLEAN_YES.equals(amtbCorporateDetail.getProjectCode())) {
//					setProjectCodeVisible();
//				} else
//					setProjectCodeInvisible();
//			} else
//				setProjectCodeInvisible();
//		} else
//			setProjectCodeInvisible();
//	}
//	private Boolean checkProjectCode() {
//		CapsTextbox productNoTextBox = (CapsTextbox) this.getFellow("cardNo");
//		Timestamp startDate = DateUtil.getCurrentTimestamp();
//
//		AmtbAccount acct = this.businessHelper.getTxnBusiness().getAccount(productNoTextBox.getText(), startDate);
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

	private Date combineDateHrMinSec(Date startDt, int hr, int min, int sec) {
		Calendar calendar = Calendar.getInstance();
		if (startDt != null) {
			calendar.setTime(startDt);
		} else {
			return null;
		}

		calendar.add(Calendar.HOUR_OF_DAY, hr);
		calendar.add(Calendar.MINUTE, min);
		calendar.add(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	public void save() throws InterruptedException {
		logger.info("save");
		displayProcessing();
		// Generated from sequence number
		try {
			// get all the listbox for mandatory field checking
			// do a onSelectProductNo to retrieve the acct information
			onSelectProductNo();
			Map<String, String> newTxnDetails = getTxnDetails();
			Listbox updateFMSListbox = (Listbox) this.getFellow("updateFMSList");
			Listbox toUpdateFMSListBox = (Listbox) this.getFellow("toUpdateFMSList");

			if (toUpdateFMSListBox.getSelectedItem() != null) {
				String toUpdateFMS = (String) toUpdateFMSListBox.getSelectedItem().getValue();

				if (NonConfigurableConstants.BOOLEAN_YES.equals(toUpdateFMS)) {
					if (updateFMSListbox.getSelectedItem() != null) {
						if ("".equals(updateFMSListbox.getSelectedItem().getValue())) {
							Messagebox.show("Collect/Refund is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					} else {
						Messagebox.show("Collect/Refund is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
					String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(newTxnDetails.get("taxiNo"), newTxnDetails.get("nric"),
							DateUtil.convertStrToTimestamp(newTxnDetails.get("startDate"), DateUtil.FMS_TRIPS_DATE_FORMAT),
							DateUtil.convertStrToTimestamp(newTxnDetails.get("endDate"), DateUtil.FMS_TRIPS_DATE_FORMAT));
					if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg)) {
						// Note: error message will be changed again
						Messagebox.show("Interface Error to FMS - " + "Driver Association Not Valid", "Create Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					} else
						logger.info("FMS Interface is disabled");
				}
			} else {
				Messagebox.show("Update FMS is a mandatory field", "Edit Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			Listbox productTypeListbox = (Listbox) this.getFellow("productType");
			if (productTypeListbox.getSelectedItem() != null) {
				if ("".equals(productTypeListbox.getSelectedItem().getValue())) {
					Messagebox.show("Product Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("Product Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			Listbox companyCdListbox = (Listbox) this.getFellow("companyCd");
			if (companyCdListbox.getSelectedItem() != null) {
				if ("".equals(companyCdListbox.getSelectedItem().getValue())) {
					Messagebox.show("Company Code is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("Company Code is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			Listbox jobType = (Listbox) this.getFellow("jobType");
			if (jobType.getSelectedItem() != null) {
				if ("".equals(jobType.getSelectedItem().getValue())) {
					Messagebox.show("Job Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("Job Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
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
			
			Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
			if (vehicleType.getSelectedItem() != null) {
				if ("".equals(vehicleType.getSelectedItem().getValue())) {
					Messagebox.show("Vehicle Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("Vehicle Type is a mandatory field", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			// Validation - Closed account cannot create transaction
			if (this.businessHelper.getTxnBusiness().isAccountClosed(newTxnDetails.get("acctNo"))) {
				Messagebox.show("Unable to create new Trips as the account is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			// Validation - Start Date cannot be later than end date
			Timestamp tripStartTimeStamp = DateUtil.convertStrToTimestamp(newTxnDetails.get("startDate"), DateUtil.TRIPS_DATE_FORMAT);
			Timestamp tripEndTimeStamp = DateUtil.convertStrToTimestamp(newTxnDetails.get("endDate"), DateUtil.TRIPS_DATE_FORMAT);

			if (tripStartTimeStamp.after(tripEndTimeStamp)) {
				Messagebox.show("Trip Start Date cannot be earlier than Trip End Date", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			PmtbProduct product = this.businessHelper.getTxnBusiness().getProduct(newTxnDetails.get("cardNo"));
			if (product != null) {
				if (NonConfigurableConstants.PRODUCT_STATUS_USED.equals(product.getCurrentStatus())) {
					Messagebox.show("Unable to create new trips as the card has been used already", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}

			if (this.businessHelper.getTxnBusiness().hasDuplicateDraftNo(newTxnDetails.get("salesDraftNo"))) {
				Messagebox.show("Unable to create new trips due to duplicate sales draft no", "Create Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
//			if(checkProjectCode() && newTxnDetails.get("projCodeReason").trim().equals(""))
//			{
//				Messagebox.show("Trip Code Reason is required", "Create Trip", Messagebox.OK, Messagebox.ERROR);
//				return;
//			}
			
			String jobNo = this.businessHelper.getTxnBusiness().createTxn(newTxnDetails, getUserLoginIdAndDomain());
			if (jobNo != null) {
				Messagebox.show("New trip created with Job No: " + jobNo, "Create Trip", Messagebox.OK, Messagebox.INFORMATION);

			} else {
				Messagebox.show("Unable to save trip. Please try again later", "Create Trip", Messagebox.OK, Messagebox.ERROR);
			}
			logger.info("saved successfully");
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			logger.info("exception in saving");
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		}
	}

	protected Map<String, String> getTxnDetails() {
		Map<String, String> txnDetails = new HashMap<String, String>();

		// contactDetails.put("userId", this.getUserLoginId());
		txnDetails.put("acctNo", ((Label) this.getFellow("acctNo")).getValue());
		txnDetails.put("name", ((Label) this.getFellow("name")).getValue());
		Label division = (Label) this.getFellow("division");
		if (division.getValue() != null && !"".equals(division.getValue())) {
			txnDetails.put("division", division.getValue());
		}
		Label department = (Label) this.getFellow("department");
		if (department.getValue() != null && !"".equals(department.getValue())) {
			txnDetails.put("department", department.getValue());
		}
		Label subApplicant = (Label) this.getFellow("subApplicant");
		if (subApplicant.getValue() != null && !"".equals(subApplicant.getValue())) {
			txnDetails.put("subApplicant", subApplicant.getValue());
		}

		Listbox productType = (Listbox) this.getFellow("productType");
		if (productType.getSelectedItem() != null && productType.getSelectedItem().getValue() != null) {
			txnDetails.put("productType", (String) productType.getSelectedItem().getValue());
		}
		txnDetails.put("cardNo", ((CapsTextbox) this.getFellow("cardNo")).getValue());
		txnDetails.put("taxiNo", ((CapsTextbox) this.getFellow("taxiNo")).getValue());
		txnDetails.put("nric", ((CapsTextbox) this.getFellow("nric")).getValue());

		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		Timestamp startDate = null;
		if (startDateBox != null && startDateBox.getValue() != null) {
			Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(startTimeMin.getSelectedItem().getLabel()), Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
		}

		txnDetails.put("startDate", DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));

		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		Timestamp endDate = null;
		if (endDateBox != null && endDateBox.getValue() != null) {
			Date endDateWithTime = combineDateHrMinSec(endDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()), Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),
					Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
			endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
		}

		txnDetails.put("endDate", DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));

		// Add company Code
		Listbox companyCd = (Listbox) this.getFellow("companyCd");
		if (companyCd.getSelectedItem() != null && companyCd.getSelectedItem().getValue() != null) {
			txnDetails.put("companyCd", (String) companyCd.getSelectedItem().getValue());
		}

		Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
		if (vehicleType.getSelectedItem() != null && vehicleType.getSelectedItem().getValue() != null) {
			txnDetails.put("vehicleType", (String) vehicleType.getSelectedItem().getValue());
		}

		Listbox jobType = (Listbox) this.getFellow("jobType");
		if (jobType.getSelectedItem() != null && jobType.getSelectedItem().getValue() != null) {
			txnDetails.put("jobType", (String) jobType.getSelectedItem().getValue());
		}

		txnDetails.put("salesDraft", ((CapsTextbox) this.getFellow("salesDraft")).getValue());
		txnDetails.put("projCode", ((CapsTextbox) this.getFellow("projCode")).getValue());
		txnDetails.put("fareAmt", ((Decimalbox) this.getFellow("fareAmt")).getValue().toString());
		txnDetails.put("pickup", ((CapsTextbox) this.getFellow("pickup")).getValue());
		txnDetails.put("destination", ((CapsTextbox) this.getFellow("destination")).getValue());
		txnDetails.put("remarks", ((CapsTextbox) this.getFellow("remarks")).getValue());

		txnDetails.put("projCodeReason", ((CapsTextbox) this.getFellow("projCodeReason")).getValue());
		
		// checkbox for complimentary
		Checkbox checkbox = (Checkbox) this.getFellow("complimentary");
		if (checkbox.isChecked())
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);

		txnDetails.put("surchargeDesc", ((CapsTextbox)this.getFellow("surchargeDesc")).getValue());
		
		Listitem toUpdateFMSListitem = ((Listbox) this.getFellow("toUpdateFMSList")).getSelectedItem();
		String toUpdateFMS = null;
		if (toUpdateFMSListitem != null) {
			toUpdateFMS = (String) toUpdateFMSListitem.getValue();
			txnDetails.put("toUpdateFMSList", toUpdateFMS);
		}
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(toUpdateFMS)) {

			Listitem updateFMSListitem = ((Listbox) this.getFellow("updateFMSList")).getSelectedItem();
			if (updateFMSListitem != null)
				txnDetails.put("updateFMSList", (String) (updateFMSListitem.getValue()));
			if (((Decimalbox) this.getFellow("FMSAmount")).getValue() != null)
				txnDetails.put("FMSAmount", ((Decimalbox) this.getFellow("FMSAmount")).getValue().toString());
			if (((Decimalbox) this.getFellow("incentiveAmt")).getValue() != null)
				txnDetails.put("incentiveAmt", ((Decimalbox) this.getFellow("incentiveAmt")).getValue().toString());
			if (((Decimalbox) this.getFellow("promoAmt")).getValue() != null)
				txnDetails.put("promoAmt", ((Decimalbox) this.getFellow("promoAmt")).getValue().toString());
			if (((Decimalbox) this.getFellow("cabRewardsAmt")).getValue() != null)
				txnDetails.put("cabRewardsAmt", ((Decimalbox) this.getFellow("cabRewardsAmt")).getValue().toString());
		}
		
		Row prepaidAdminRow = (Row) this.getFellow("prepaidAdminRow");
		if(prepaidAdminRow.isVisible()){
			txnDetails.put("prepaidAdminFee", ((Decimalbox) this.getFellow("prepaidAdminFee")).getValue().toString());
			txnDetails.put("prepaidGst", ((Decimalbox) this.getFellow("prepaidGst")).getValue().toString());
			
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

	public void reset() throws InterruptedException {
		logger.info("reset");
		this.removeConstraints();
		this.clearAcct();
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
		((Checkbox) this.getFellow("complimentary")).setChecked(false);
		((CapsTextbox) this.getFellow("remarks")).setValue(null);
		((CapsTextbox) this.getFellow("pickup")).setValue(null);
		((CapsTextbox) this.getFellow("destination")).setValue(null);
		((CapsTextbox)this.getFellow("surchargeDesc")).setValue(null);
		((Decimalbox) this.getFellow("FMSAmount")).setValue(null);
		((Decimalbox) this.getFellow("incentiveAmt")).setValue(null);
		((Decimalbox) this.getFellow("promoAmt")).setValue(null);
		((Decimalbox) this.getFellow("cabRewardsAmt")).setValue(null);
		((Decimalbox) this.getFellow("fareAmt")).setValue(null);
		((Listbox) this.getFellow("companyCd")).setSelectedIndex(0);
		((Listbox) this.getFellow("vehicleType")).setSelectedIndex(0);
		((Listbox) this.getFellow("jobType")).setSelectedIndex(0);
		((Listbox) this.getFellow("updateFMSList")).setSelectedIndex(0);

		((CapsTextbox) this.getFellow("taxiNo")).setText(null);
		((CapsTextbox) this.getFellow("taxiNo")).setValue(null);
		((CapsTextbox) this.getFellow("nric")).setText(null);
		((CapsTextbox) this.getFellow("nric")).setValue(null);
		((Listbox) this.getFellow("toUpdateFMSList")).setSelectedIndex(0);
		((CapsTextbox) this.getFellow("salesDraft")).setText(null);
		((CapsTextbox) this.getFellow("salesDraft")).setValue(null);
		// disable the fields
		((Row) this.getFellow("FMSRow")).setVisible(false);
		((Row) this.getFellow("FMSRow2")).setVisible(false);
		((Row) this.getFellow("FMSRow3")).setVisible(false);
		((CapsTextbox) this.getFellow("projCode")).setValue(null);
		((CapsTextbox) this.getFellow("projCodeReason")).setValue(null);
		((CapsTextbox) this.getFellow("cardNo")).setValue(null);

		((Checkbox) this.getFellow("complimentary")).setChecked(false);

		this.addConstraints();
	}

	private void removeConstraints() throws InterruptedException {
		((Datebox) this.getFellow("endDate")).setConstraint("");
		((Datebox) this.getFellow("startDate")).setConstraint("");
		((CapsTextbox) this.getFellow("remarks")).setConstraint("");
		((CapsTextbox) this.getFellow("pickup")).setConstraint("");
		((Decimalbox) this.getFellow("FMSAmount")).setConstraint("");
		((Decimalbox) this.getFellow("incentiveAmt")).setConstraint("");
		((Decimalbox) this.getFellow("fareAmt")).setConstraint("");
		((CapsTextbox) this.getFellow("cardNo")).setConstraint("");
		((CapsTextbox) this.getFellow("taxiNo")).setConstraint("");
		((CapsTextbox) this.getFellow("nric")).setConstraint("");
	}

	private void addConstraints() throws InterruptedException {
		((Datebox) this.getFellow("endDate")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredBeforeOrEqualsCurrentDateConstraint());
		((Datebox) this.getFellow("startDate")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredBeforeOrEqualsCurrentDateConstraint());
		((CapsTextbox) this.getFellow("pickup")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((Decimalbox) this.getFellow("fareAmt")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroAmountForFareConstraint());
		((CapsTextbox) this.getFellow("cardNo")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredNumericConstraint());
		((CapsTextbox) this.getFellow("taxiNo")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox) this.getFellow("nric")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((Decimalbox) this.getFellow("incentiveAmt")).setConstraint(new IncentiveAmountConstraint());
	}

	public List<Listitem> getTripType() {
		List<Listitem> tripTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleTripTypes(), false);
		return cloneList(tripTypeList);
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
}
