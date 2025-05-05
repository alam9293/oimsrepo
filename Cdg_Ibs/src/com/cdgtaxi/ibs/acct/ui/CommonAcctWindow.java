package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
/**
 * class that has all common methods in application for [?]
 * @author Tan Yiming
 *
 */
public abstract class CommonAcctWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CommonAcctWindow.class);
	private static final long serialVersionUID = 1L;
	public void checkCountry(Listitem selectedItem, Textbox city, Textbox state){
		logger.info("checkCountry(Listitem selectedItem, Textbox city, Textbox state)");
		boolean isSGSelected = false;
		if(selectedItem.getValue().equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
			isSGSelected = true;
		}
		city.setDisabled(isSGSelected);
		city.setText(null);
		state.setDisabled(isSGSelected);
		state.setText(null);
	}
	public void displayProdDiscount() throws InterruptedException{
		logger.info("displayProdDiscount()");
		this.doModal("productDiscount");
	}
	public void displayLoyalty() throws InterruptedException{
		logger.info("displayLoyalty()");
		this.doModal("rewards");
	}
	public void displaySubscription() throws InterruptedException{
		logger.info("displaySubscription()");
		this.doModal("subscribeFee");
	}
	public void displayIssuance() throws InterruptedException{
		logger.info("displayIssuance()");
		this.doModal("issuanceFee");
	}
	public void displayLateInterest() throws InterruptedException{
		logger.info("displayLateInterest()");
		this.doModal("lateInterest");
	}
	public void displayEarlyDiscount() throws InterruptedException{
		logger.info("displayEarlyDiscount()");
		this.doModal("earlyDiscount");
	}
	public void displayAdminFee() throws InterruptedException{
		logger.info("displayAdminFee()");
		this.doModal("adminFee");
	}
	public void displayVolumeDiscount() throws InterruptedException{
		logger.info("displayVolumeDiscount()");
		this.doModal("volumeDiscount");
	}
	public void displayCreditTerm() throws InterruptedException{
		logger.info("displayVolumeDiscount()");
		this.doModal("creditTerm");
	}
	public void displayPromotion() throws InterruptedException{
		logger.info("displayPromotion()");
		this.doModal("promotion");
	}
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			if(listitem.isSelected()){
				Listitem newListitem = new Listitem(listitem.getLabel(), listitem.getValue());
				newListitem.setSelected(true);
				returnList.add(newListitem);
			}else{
				returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
			}
		}
		return returnList;
	}
	private void doModal(String type) throws SuspendNotAllowedException, InterruptedException{
		logger.info("doModal(String type)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("type", type);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_INFO, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewStatusHistory(String custNo, String subAcctNo, String parentCode, String code) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory(String custNo, String subAcctNo, String parentCode, String code)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		args.put("subAcctNo", subAcctNo);
		args.put("parentCode", parentCode);
		args.put("code", code);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_STATUS_HISTORY, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewBillingCycleHistory(String custNo) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewBillingCycleHistory(String custNo)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_BILLING_CYCLE_HISTORY, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewCreditTermHistory(String custNo) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewCreditTermHistory(String custNo)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_CREDIT_TERM_HISTORY, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewEarlyPaymentHistory(String custNo) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewEarlyPaymentHistory(String custNo)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_EARLY_PAYMENT_HISTORY, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewLatePaymentHistory(String custNo) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewLatePaymentHistory(String custNo)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_LATE_PAYMENT_HISTORY, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewPromotionHistory(String custNo) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewPromotionHistory(String custNo)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_PROMOTION_HISTORY, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
	protected void viewCreditLimitHistory(String custNo, String acctNo, String parentCode, String code, String acctName) throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewCreditLimitHistory(String custNo, String acctNo, String parentCode, String code)");
		Map<String, String> args = new HashMap<String,String>();
		args.put("custNo", custNo);
		args.put("acctNo", acctNo);
		args.put("parentCode", parentCode);
		args.put("code", code);
		args.put("acctName", acctName);
		final Window win = (Window) Executions.createComponents(Uri.VIEW_CREDIT_REVIEW, null, args);
		win.setMaximizable(true);
		win.doModal();
	}
}
