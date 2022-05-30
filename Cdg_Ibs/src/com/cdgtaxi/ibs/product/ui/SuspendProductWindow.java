package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class SuspendProductWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SuspendProductWindow.class);
	private HashSet<BigDecimal> productIdSet = new HashSet<BigDecimal>();

	@SuppressWarnings("unchecked")
	public SuspendProductWindow() {

		HashMap<String, String> params = (HashMap<String, String>) Executions.getCurrent().getArg();
		for (String productId : params.keySet()) {
			if (productId.indexOf("productId") >= 0)
				productIdSet.add(new BigDecimal(params.get(productId)));
		}
	}

	public List<Listitem> getSuspendReasons() {
		List<Listitem> reasonList = new ArrayList<Listitem>();
		Map<String, String> masterSuspendResasons = ConfigurableConstants.getProductSuspendReasons();
		for (String masterCode : masterSuspendResasons.keySet()) {
			reasonList.add(new Listitem(masterSuspendResasons.get(masterCode), masterCode));
		}
		return reasonList;
	}

	public void populateData() {
		int count = 0;
		Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String, String>>();
		dataMap = this.businessHelper.getProductBusiness().getProductsbyIdSet(productIdSet);
		logger.info("Map Size = " + dataMap.size());
		Listbox resultListBox = (Listbox) this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		try {
			for (String productid : dataMap.keySet()) {
				count++;
				Listitem item = new Listitem();
				item.setValue(productid);
				Map<String, String> productDetails = dataMap.get(productid);
				item.appendChild(newListcell(productDetails.get("parentAccountNo")));
				item.appendChild(newListcell(productDetails.get("accountName")));
				item.appendChild(newListcell(productDetails.get("productType")));
				item.appendChild(newListcell(productDetails.get("cardNo")));
				item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("issueDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(productDetails.get("expiryDate")));
				item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("suspendDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
				resultListBox.appendChild(item);
			}
			// To show the no record found message below the list
			if (resultListBox.getListfoot() != null && count > 0) {
				resultListBox.removeChild(resultListBox.getListfoot());
			}
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(10);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}

	public void getCurrentDate() {
		Datebox suspendDate = (Datebox) this.getFellow("suspendDate");
		Date today = new Date();
		suspendDate.setValue(today);
	}

	public void suspend() throws InterruptedException {
		logger.info("suspend()");
		displayProcessing();
		String suspendReason = (String)((Listbox)this.getFellow("suspendReasonList")).getSelectedItem().getValue();
		Date suspendDate = ((Datebox)this.getFellow("suspendDate")).getValue();
		String suspendHr = ((Listbox)this.getFellow("startTimeHrDDL")).getSelectedItem().getLabel();
		String suspendMin = ((Listbox)this.getFellow("startTimeMinDDL")).getSelectedItem().getLabel();
		Date reactivateDate = ((Datebox)this.getFellow("reactiveDate")).getValue();
		String reactivateHr = ((Listbox)this.getFellow("endTimeHrDDL")).getSelectedItem().getLabel();
		String reactivateMin = ((Listbox)this.getFellow("endTimeMinDDL")).getSelectedItem().getLabel();
		String suspensionRemarks = ((Textbox)this.getFellow("remarks")).getValue();
		Calendar suspendCalendar = Calendar.getInstance();
		suspendCalendar.setTime(suspendDate);
		suspendCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(suspendHr));
		suspendCalendar.set(Calendar.MINUTE, Integer.parseInt(suspendMin));
		// checking for current date time
		if(suspendCalendar.before(Calendar.getInstance())) {
			// comment out to follow the standard way of doing. If use this method, users might ask to change for other screens e.g. create trip screen
//			if(Messagebox.show("The selected suspension date is before current time. Use current time instead?", "Product Suspension", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.OK){
//				suspendCalendar = Calendar.getInstance();
//			} else {
//				return;
//			}
			Messagebox.show("The selected suspension date is before current time!", "Product Suspension", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		// checking for reactivate time
		Calendar reactivateCalendar = null;
		if(reactivateDate!=null) {
			reactivateCalendar = Calendar.getInstance();
			reactivateCalendar.setTime(reactivateDate);
			reactivateCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(reactivateHr));
			reactivateCalendar.set(Calendar.MINUTE, Integer.parseInt(reactivateMin));
			if(reactivateCalendar.before(suspendCalendar)) {
				Messagebox.show("Suspension date is after reactivation!", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		// now checking for termination
		List<BigDecimal> productIds = new ArrayList<BigDecimal>(productIdSet);
		if(this.businessHelper.getProductBusiness().isFutureTerminationByRange(productIds, DateUtil.getCurrentUtilDate(), suspendCalendar.getTime())){
			Messagebox.show("One/more products are terminated before the start of suspension!", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(reactivateDate!=null){
			if(this.businessHelper.getProductBusiness().isFutureTerminationByRange(productIds, suspendCalendar.getTime(), reactivateCalendar.getTime())){
				Messagebox.show("One/more products are terminated before the start of reactivation!", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		if(this.businessHelper.getProductBusiness().hasStatus(productIds, suspendCalendar.getTime())){
			Messagebox.show("One/more products has status on suspension date!", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(reactivateDate!=null){
			if(this.businessHelper.getProductBusiness().hasStatus(productIds, reactivateCalendar.getTime())){
				Messagebox.show("One/more products has status on reactivation date!", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		if(Messagebox.show("Suspend Products?", "Product Suspension", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			try {
				this.businessHelper.getProductBusiness().suspendProduct(
						productIds,
						new Timestamp(suspendCalendar.getTimeInMillis()),
						reactivateDate!=null ? new Timestamp(reactivateCalendar.getTimeInMillis()) : null,
						ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_SUSPEND_REASON, suspendReason),
						suspensionRemarks,
						getUserLoginIdAndDomain());
				if(reactivateDate!=null){
					this.businessHelper.getProductBusiness().reactivateProduct(
							productIds,
							new Timestamp(reactivateCalendar.getTimeInMillis()),
							ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_SUSPEND_REASON, suspendReason),
							// change to "END OF SUSPENSION" as requested by geok hua
							"END OF SUSPENSION",
							getUserLoginIdAndDomain());
				}
			} catch (Exception e) {
				Messagebox.show("Unable to suspend product(s)! Please try again later", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
				logger.error("Error", e);
				e.printStackTrace();
				Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
			}
			Messagebox.show("Product(s) Suspended.", "Product Suspension", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}

		Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH
		// changed to above for multiple suspension for evouchers
//		PmtbProductStatus productStatus = new PmtbProductStatus();
//		Textbox remarks = (Textbox) this.getFellow("remarks");
//		String replacemnetRemarks = (String) remarks.getValue();
//		Date currentDate = new Date();
//		Datebox suspendDate = (Datebox) this.getFellow("suspendDate");
//		Datebox reactivateDate = (Datebox) this.getFellow("reactiveDate");
//		Date suspensionDate = (Date) suspendDate.getValue();
//		Date reactivationDate = (Date) reactivateDate.getValue();
//		Listbox suspendReasonList = (Listbox) this.getFellow("suspendReasonList");
//		String suspendReason = (String) suspendReasonList.getSelectedItem().getValue();
//		boolean datecheck = true;
//		boolean todayCheck = false;
//		boolean isFutureTermination = false;
//		try {
//			if (suspensionDate != null) {
//				todayCheck = DateUtil.isToday(suspensionDate);
//				if (todayCheck) {
//					suspensionDate = DateUtil.getCurrentDate();
//				} else {
//					suspensionDate = DateUtil.convertDateTo0000Hours(suspensionDate);
//				}
//				if (replacemnetRemarks == null || replacemnetRemarks.trim().length() < 1) {
//					Messagebox.show("Remarks field should not be blank", "Error", Messagebox.OK, Messagebox.ERROR);
//				} else {
//					Iterator<BigDecimal> It = productIdSet.iterator();
//					PmtbProduct product = new PmtbProduct();
//					productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_SUSPEND_REASON, suspendReason));
//					productStatus.setStatusRemarks(replacemnetRemarks);
//					if (suspensionDate.compareTo(currentDate) < 0 && todayCheck == false) {
//						Messagebox.show("Suspension Date should not be earlier than today.", "Error", Messagebox.OK, Messagebox.ERROR);
//						datecheck = false;
//					}
//					if (reactivationDate != null && datecheck == true) {
//						if (suspensionDate.after(reactivationDate) && !(DateUtil.isToday(suspensionDate) && DateUtil.isToday(reactivationDate))) {
//							Messagebox.show("Reactivation Date should not be earlier than Suspension Date.", "Error", Messagebox.OK, Messagebox.ERROR);
//							datecheck = false;
//						}
//					}
//					if (datecheck == true) {
//						int confirmMessage = Messagebox.show("Are you sure to suspend ?", "Suspend Product Confirmation ", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
//						if (confirmMessage == Messagebox.OK) {
//							displayProcessing();
//							while (It.hasNext()) {
//								productStatus.setStatusDt(new Timestamp(suspensionDate.getTime()));
//								// All the active cards will be selected so
//								// status from should be ACTIVE to SUSPEND
//								productStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//								productStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//								product = (PmtbProduct) this.businessHelper.getProductBusiness().getProductById(It.next());
//								productStatus.setPmtbProduct(product);
//
//								if (reactivationDate != null) {
//									isFutureTermination = this.businessHelper.getProductBusiness().isFutureTerminationByRange(product, currentDate, suspensionDate)
//										|| this.businessHelper.getProductBusiness().isFutureTerminationByRange(product, currentDate, suspensionDate);
//								} else {
//									isFutureTermination = this.businessHelper.getProductBusiness().isFutureTerminationByRange(product, currentDate, suspensionDate);
//								}
//								if (isFutureTermination) {
//									Messagebox.show("Unable to proceed. There is a future termination schedule for the Product(s).", "Error", Messagebox.OK, Messagebox.ERROR);
//								} else {
//									PmtbProductStatus futureSuspend = (PmtbProductStatus) this.businessHelper.getProductBusiness().getFutureSuspendSchedule(product);
//									PmtbProductStatus futureReactivate = (PmtbProductStatus) this.businessHelper.getProductBusiness().getFutureReactivateSchedule(product);
//									if (todayCheck || futureSuspend == null) {
//										boolean checkSavingProductStatus = this.businessHelper.getGenericBusiness().save(productStatus, this.getUserLoginId()) != null ? true : false;
//										if (!checkSavingProductStatus) {
//											Messagebox.show("Product(s)cannot be suspended.", "Product Suspension", Messagebox.OK, Messagebox.ERROR);
//										}
//									}
//									if (todayCheck) {
//										product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//										try {
//											this.businessHelper.getGenericBusiness().update(product, this.getUserLoginId());
//											// interface to AS that product had been suspended.
//											this.businessHelper.getProductBusiness().updateProductAPI(product, this.getUserLoginId(), productStatus.getMstbMasterTable());
//										} catch (Exception exp) {
//											exp.printStackTrace();
//											logger.error("New Status cannot updated into product table.");
//											logger.error(exp);
//										}
//									}
//									if (todayCheck == false && futureSuspend != null) {
//										logger.info("Updating Future suspension");
//										// All the active cards will be selected so status from should be ACTIVE to SUSPEND
//										futureSuspend.setStatusRemarks(replacemnetRemarks);
//										futureSuspend.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_SUSPEND_REASON, suspendReason));
//										futureSuspend.setStatusDt(new Timestamp(suspensionDate.getTime()));
//										futureSuspend.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//										futureSuspend.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//										futureSuspend.setPmtbProduct(product);
//										try {
//											this.businessHelper.getGenericBusiness().update(futureSuspend, this.getUserLoginId());
//										} catch (Exception exp) {
//											exp.printStackTrace();
//											logger.error(exp);
//											logger.error("Future Suspend cannot be updated");
//										}
//									}
//									if (reactivationDate != null) {
//										reactivationDate = DateUtil.convertDateTo2359Hours(reactivationDate);
//										productStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//										productStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
//										productStatus.setStatusDt(new Timestamp(reactivationDate.getTime()));
//										if (futureReactivate == null) {
//											try {
//												this.businessHelper.getGenericBusiness().save(productStatus, this.getUserLoginId());
//											} catch (Exception exp) {
//												exp.printStackTrace();
//												logger.error(exp);
//												logger.error("Future Reactivate cannot be created");
//											}
//										} else {
//											try {
//												logger.info("Future Reactivate updating ..");
//												futureReactivate.setStatusRemarks(replacemnetRemarks);
//												futureReactivate.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_SUSPEND_REASON, suspendReason));
//												futureReactivate.setStatusDt(new Timestamp(suspensionDate.getTime()));
//												futureReactivate.setPmtbProduct(product);
//												futureReactivate.setStatusDt(new Timestamp(reactivationDate.getTime()));
//												this.businessHelper.getGenericBusiness().update(futureReactivate, this.getUserLoginId());
//											} catch (Exception exp) {
//												exp.printStackTrace();
//												logger.error(exp);
//												logger.error("Future Reactivate cannot be updated");
//											}
//										}
//									}
//								}
//							}
//							Messagebox.show("Product(s) Suspended.", "Product Suspension", Messagebox.OK, Messagebox.INFORMATION);
//							this.back();
//						}
//					}
//				}
//			} else {
//				Messagebox.show("Supspension date is a mandatory field", "Product Suspension", Messagebox.OK, Messagebox.INFORMATION);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void cancel() throws InterruptedException {
		this.back();
	}
}