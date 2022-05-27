package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.BankMasterManager;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.PromotionMasterManager;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.constraint.EqualOrLaterThanNowConstraint;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;

public class EditBillingWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditBillingWindow.class);
	private String custNo, acctStatus;
	List<Listitem> billingCycles = ComponentUtil.convertToListitems(NonConfigurableConstants.BILLING_CYCLES, true);
	List<Listitem> pymtMode, creditTerms, earlyPayments, latePayments, banks, volumeDiscounts, adminFees, promotions;
	String /*volumeDiscountName = null,*/ adminFeeName = null;
	@SuppressWarnings("unchecked")
	public EditBillingWindow() throws InterruptedException{
		logger.info("EditBillingWindow()");
		Map<Integer, String> promotionMasters = MasterSetup.getPromotionManager().getAllEffectiveAccountPromotions();
		promotions = new ArrayList<Listitem>();
		for(Integer promotionNo : promotionMasters.keySet()){
			promotions.add(new Listitem(promotionMasters.get(promotionNo), promotionNo));
		}
		Map<Integer, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMasters();
		creditTerms = new ArrayList<Listitem>();
		for(Integer creditTermNo : creditTermMasters.keySet()){
			creditTerms.add(new Listitem(creditTermMasters.get(creditTermNo), creditTermNo));
		}
		pymtMode = new LinkedList<Listitem>();
		for(String paymentCode : ConfigurableConstants.getPaymentModes().keySet()){
			if(paymentCode.equals("CT") || paymentCode.equals("MEMO")){
				continue;
			}
			pymtMode.add(new Listitem(ConfigurableConstants.getPaymentModes().get(paymentCode), paymentCode));
		}
		Map<Integer, String> earlyPymtMasters = MasterSetup.getEarlyPaymentManager().getAllMasters();
		earlyPayments = new ArrayList<Listitem>();
		Listitem notRequiredItem = (Listitem)ComponentUtil.createNotRequiredListItem();
		notRequiredItem.setValue(null);
		earlyPayments.add(notRequiredItem);
		for(Integer earlyPymtNo : earlyPymtMasters.keySet()){
			earlyPayments.add(new Listitem(earlyPymtMasters.get(earlyPymtNo), earlyPymtNo));
		}
		Map<Integer, String> latePymtMasters = MasterSetup.getLatePaymentManager().getAllMasters();
		latePayments = new ArrayList<Listitem>();
		for(Integer latePymtNo : latePymtMasters.keySet()){
			latePayments.add(new Listitem(latePymtMasters.get(latePymtNo), latePymtNo));
		}
		Map<Integer, String> volumeDiscountMasters = MasterSetup.getVolumeDiscountManager().getAllMasters();
		volumeDiscounts = new ArrayList<Listitem>();
		notRequiredItem = (Listitem)ComponentUtil.createNotRequiredListItem();
		notRequiredItem.setValue(null);
		volumeDiscounts.add(notRequiredItem);
		for(Integer volumeDiscountNo : volumeDiscountMasters.keySet()){
			volumeDiscounts.add(new Listitem(volumeDiscountMasters.get(volumeDiscountNo), volumeDiscountNo));
		}
		Map<Integer, String> bankMasters = MasterSetup.getBankManager().getAllMasters();
		banks = new ArrayList<Listitem>();
		banks.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Integer bankNo : bankMasters.keySet()){
			banks.add(new Listitem(bankMasters.get(bankNo), bankNo));
		}
		Collections.sort(banks, new Comparator<Listitem>() {
			public int compare(Listitem o1, Listitem o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		Map<Integer, String> adminFeeMasters = MasterSetup.getAdminFeeManager().getAllMasters();
		adminFees = new ArrayList<Listitem>();
		for(Integer adminFeeNo : adminFeeMasters.keySet()){
			adminFees.add(new Listitem(adminFeeMasters.get(adminFeeNo), adminFeeNo));
		}
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
			return;
		}
		Map<String, List<Map<String, Object>>> billingDetails = this.businessHelper.getAccountBusiness().getTopRowOfBillingDetails(custNo);
//		Map<String, List<Map<String, Object>>> billingDetails = this.businessHelper.getAccountBusiness().getBillingDetails(custNo);
		
		// billing
		List<Map<String, Object>> billing = billingDetails.get("billing");
		Rows billingRows = (Rows)this.getFellow("billing");
		while(billingRows.getChildren().size()!=1){
			billingRows.removeChild(billingRows.getFirstChild());
		}
		for(Map<String, Object> bill : billing){
			boolean disabled = true;
			if(DateUtil.isToday((Date)bill.get("effectiveDate")) || DateUtil.getCurrentDate().before((Date)bill.get("effectiveDate"))){
				disabled = false;
			}
			Row billingRow = new Row();
			billingRow.appendChild(new Label(""+billingRows.getChildren().size()));
			Listbox billingCycle = new Listbox();
			billingCycle.setWidth("100%"); billingCycle.setRows(1); billingCycle.setMold("select");billingCycle.setDisabled(disabled);
			billingCycle.getItems().addAll(cloneList(billingCycles));
			if(bill.get("billingCycle")!=null){
				for(Object billCycle : billingCycle.getItems()){
					if(bill.get("billingCycle").equals(((Listitem)billCycle).getValue())){
						((Listitem)billCycle).setSelected(true);
						break;
					}
				}
			}else{
				billingCycle.setSelectedIndex(0);
			}
			billingRow.appendChild(billingCycle);
			Listbox volumeDiscount = new Listbox();
			volumeDiscount.setWidth("100%"); volumeDiscount.setRows(1); volumeDiscount.setMold("select");volumeDiscount.setDisabled(disabled);
			volumeDiscount.getItems().addAll(cloneList(this.volumeDiscounts));
			if(bill.get("volumeDiscount")!=null){
				for(Object volumeDiscountItem : volumeDiscount.getItems()){
					if(bill.get("volumeDiscount").equals(((Listitem)volumeDiscountItem).getValue())){
						((Listitem)volumeDiscountItem).setSelected(true);
						break;
					}
				}
			}else{
				volumeDiscount.setSelectedIndex(0);
			}
			billingRow.appendChild(volumeDiscount);
			// adding [?] to row
			Image volDiscImage = new Image("/images/question.png");
			volDiscImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editBillingWindow.displayVolumeDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			volDiscImage.addEventHandler("onClick", pdEvent);
			billingRow.appendChild(volDiscImage);
			Listbox adminFee = new Listbox();
			adminFee.setWidth("100%"); adminFee.setRows(1); adminFee.setMold("select"); adminFee.setDisabled(disabled);
			adminFee.getItems().addAll(cloneList(this.adminFees));
			if(bill.get("adminFee")!=null){
				for(Object adminFeeItem : adminFee.getItems()){
					if(bill.get("adminFee").equals(((Listitem)adminFeeItem).getValue())){
						((Listitem)adminFeeItem).setSelected(true);
						break;
					}
				}
			}else{
				adminFee.setSelectedIndex(0);
			}
			billingRow.appendChild(adminFee);
			// adding [?] to row
			Image adminFeeImage = new Image("/images/question.png");
			adminFeeImage.setStyle("cursor: help");
			showInfo = ZScript.parseContent("editBillingWindow.displayAdminFee()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			adminFeeImage.addEventHandler("onClick", pdEvent);
			billingRow.appendChild(adminFeeImage);
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("90%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);effectiveDate.setDisabled(disabled);
			if(!disabled){
				effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			}
			effectiveDate.setValue((Date)bill.get("effectiveDate"));
			billingRow.appendChild(effectiveDate);
			billingRow.appendChild(new Label());
			billingRows.insertBefore(billingRow, billingRows.getLastChild());
		}
		// credit term
		Rows creditTermRows = (Rows)this.getFellow("creditTerm");
		while(creditTermRows.getChildren().size()!=1){
			creditTermRows.removeChild(creditTermRows.getFirstChild());
		}
		List<Map<String, Object>> creditTerm = billingDetails.get("creditTerm");
		for(Map<String, Object> credit : creditTerm){
			boolean disabled = true;
			if(DateUtil.isToday((Date)credit.get("effectiveDate")) || DateUtil.getCurrentDate().before((Date)credit.get("effectiveDate"))){
				disabled = false;
			}
			Row creditTermRow = new Row();
			creditTermRow.appendChild(new Label(""+creditTermRows.getChildren().size()));
			Listbox creditTermBox = new Listbox();
			creditTermBox.setWidth("100%"); creditTermBox.setRows(1); creditTermBox.setMold("select");creditTermBox.setDisabled(disabled);
			creditTermBox.getItems().addAll(cloneList(creditTerms));
			if(credit.get("creditTerm")!=null){
				for(Object creditTermItem : creditTermBox.getItems()){
					if(credit.get("creditTerm").equals(((Listitem)creditTermItem).getValue())){
						((Listitem)creditTermItem).setSelected(true);
						break;
					}
				}
			}
			creditTermRow.appendChild(creditTermBox);
			// adding [?] to row
			Image creditTermImage = new Image("/images/question.png");
			creditTermImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editBillingWindow.displayCreditTerm()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			creditTermImage.addEventHandler("onClick", pdEvent);
			creditTermRow.appendChild(creditTermImage);
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("91.5%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			if(!disabled){
				effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			}
			effectiveDate.setValue((Date)credit.get("effectiveDate"));effectiveDate.setDisabled(disabled);
			creditTermRow.appendChild(effectiveDate);
			creditTermRow.appendChild(new Label());
			creditTermRows.insertBefore(creditTermRow, creditTermRows.getLastChild());
		}
		// early payment
		Rows earlyPymtRows = (Rows)this.getFellow("earlyPayment");
		while(earlyPymtRows.getChildren().size()!=1){
			earlyPymtRows.removeChild(earlyPymtRows.getFirstChild());
		}
		List<Map<String, Object>> earlyPymt = billingDetails.get("earlyPymt");
		for(Map<String, Object> early : earlyPymt){
			boolean disabled = true;
			if(DateUtil.isToday((Date)early.get("effectiveDate")) || DateUtil.getCurrentDate().before((Date)early.get("effectiveDate"))){
				disabled = false;
			}
			Row earlyPymtRow = new Row();
			earlyPymtRow.appendChild(new Label(""+earlyPymtRows.getChildren().size()));
			Listbox earlyPymtBox = new Listbox();
			earlyPymtBox.setWidth("100%"); earlyPymtBox.setRows(1); earlyPymtBox.setMold("select");earlyPymtBox.setDisabled(disabled);
			earlyPymtBox.getItems().addAll(cloneList(this.earlyPayments));
			if(early.get("earlyPymt")!=null){
				for(Object earlyPymtItem : earlyPymtBox.getItems()){
					if(early.get("earlyPymt").equals(((Listitem)earlyPymtItem).getValue())){
						((Listitem)earlyPymtItem).setSelected(true);
						break;
					}
				}
			}else{
				earlyPymtBox.setSelectedIndex(0);
			}
			earlyPymtRow.appendChild(earlyPymtBox);
			// adding [?] to row
			Image earlyPymtImage = new Image("/images/question.png");
			earlyPymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editBillingWindow.displayEarlyDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			earlyPymtImage.addEventHandler("onClick", pdEvent);
			earlyPymtRow.appendChild(earlyPymtImage);
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("91.5%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			if(!disabled){
				effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			}
			effectiveDate.setValue((Date)early.get("effectiveDate"));effectiveDate.setDisabled(disabled);
			earlyPymtRow.appendChild(effectiveDate);
			earlyPymtRow.appendChild(new Label());
			earlyPymtRows.insertBefore(earlyPymtRow, earlyPymtRows.getLastChild());
		}
		// late payment
		Rows latePymtRows = (Rows)this.getFellow("latePayment");
		while(latePymtRows.getChildren().size()!=1){
			latePymtRows.removeChild(latePymtRows.getFirstChild());
		}
		List<Map<String, Object>> latePymt = billingDetails.get("latePymt");
		for(Map<String, Object> late : latePymt){
			boolean disabled = true;
			if(DateUtil.isToday((Date)late.get("effectiveDate")) || DateUtil.getCurrentDate().before((Date)late.get("effectiveDate"))){
				disabled = false;
			}
			Row latePymtRow = new Row();
			latePymtRow.appendChild(new Label(""+latePymtRows.getChildren().size()));
			Listbox latePymtBox = new Listbox();
			latePymtBox.setWidth("100%"); latePymtBox.setRows(1); latePymtBox.setMold("select");latePymtBox.setDisabled(disabled);
			latePymtBox.getItems().addAll(cloneList(this.latePayments));
			if(late.get("latePymt")!=null){
				for(Object latePymtItem : latePymtBox.getItems()){
					if(late.get("latePymt").equals(((Listitem)latePymtItem).getValue())){
						((Listitem)latePymtItem).setSelected(true);
						break;
					}
				}
			}
			latePymtRow.appendChild(latePymtBox);
			// adding [?] to row
			Image latePymtImage = new Image("/images/question.png");
			latePymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editBillingWindow.displayLateInterest()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			latePymtImage.addEventHandler("onClick", pdEvent);
			latePymtRow.appendChild(latePymtImage);
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("91.5%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			if(!disabled){
				effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			}
			effectiveDate.setValue((Date)late.get("effectiveDate"));effectiveDate.setDisabled(disabled);
			latePymtRow.appendChild(effectiveDate);
			latePymtRow.appendChild(new Label());
			latePymtRows.insertBefore(latePymtRow, latePymtRows.getLastChild());
		}
		// promotion
		Rows promotionRows = (Rows)this.getFellow("promotion");
		while(promotionRows.getChildren().size()!=1){
			promotionRows.removeChild(promotionRows.getFirstChild());
		}
		List<Map<String, Object>> promotion = billingDetails.get("promotion");
		for(Map<String, Object> promo : promotion){
			boolean disabledFrom = true, disabledTo = true, promotionItemFound = false;
			if(DateUtil.isToday((Date)promo.get("effectiveDateFrom")) || DateUtil.getCurrentDate().before((Date)promo.get("effectiveDateFrom"))){
				disabledFrom = false;
			}
			if(promo.get("effectiveDateTo")==null || DateUtil.isToday((Date)promo.get("effectiveDateTo")) || DateUtil.getCurrentDate().before((Date)promo.get("effectiveDateTo"))){
				disabledTo = false;
			}
			Row promotionRow = new Row();
			promotionRow.setValue(promo.get("acctPromotionNo"));
			promotionRow.appendChild(new Label(""+promotionRows.getChildren().size()));
			Listbox promotionBox = new Listbox();
			promotionBox.setWidth("100%"); promotionBox.setRows(1); promotionBox.setMold("select");promotionBox.setDisabled(disabledFrom);
			promotionBox.getItems().addAll(cloneList(promotions));
			if(promo.get("promotion")!=null){
				for(Object promotionItem : promotionBox.getItems()){
					if(promo.get("promotion").equals(((Listitem)promotionItem).getValue())){
						((Listitem)promotionItem).setSelected(true);
						promotionItemFound = true;
						break;
					}
				}
			}
			if(!promotionItemFound){
				Map promotionMap = MasterSetup.getPromotionManager().getAllAccountPromotions().get(promo.get("promotion"));
				Listitem item = new Listitem(promotionMap.get(PromotionMasterManager.MASTER_NAME).toString(), promo.get("promotion"));
				item.setSelected(true);
				promotionBox.appendChild(item);
			}
			promotionRow.appendChild(promotionBox);
			// adding [?] to row
			Image promotionImage = new Image("/images/question.png");
			promotionImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editBillingWindow.displayPromotion()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			promotionImage.addEventHandler("onClick", pdEvent);
			promotionRow.appendChild(promotionImage);
			Datebox effectiveDateFrom = new Datebox();
			effectiveDateFrom.setWidth("91.5%");
			effectiveDateFrom.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			if(!disabledFrom){
				effectiveDateFrom.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			}
			effectiveDateFrom.setValue((Date)promo.get("effectiveDateFrom"));effectiveDateFrom.setDisabled(disabledFrom);
			promotionRow.appendChild(effectiveDateFrom);
			Datebox effectiveDateTo = new Datebox();
			effectiveDateTo.setWidth("91.5%");
			effectiveDateTo.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			if(!disabledTo){
				effectiveDateTo.setConstraint(new EqualOrLaterThanNowConstraint());
			}
			effectiveDateTo.setValue((Date)promo.get("effectiveDateTo"));effectiveDateTo.setDisabled(disabledTo);
			promotionRow.appendChild(effectiveDateTo);
			promotionRow.appendChild(new Label());
			promotionRows.insertBefore(promotionRow, promotionRows.getLastChild());
		}
		// bank info
		List<Map<String, Object>> bankInfo = billingDetails.get("bankInfo");
		if(!bankInfo.isEmpty()){
			if(bankInfo.get(0).get("bankMaster")!=null){
				Listbox bankList = (Listbox)this.getFellow("bankList");
				for(Object bankItem : bankList.getItems()){
					if(((Listitem)bankItem).getValue().equals(bankInfo.get(0).get("bankMaster"))){
						((Listitem)bankItem).setSelected(true);
						updateBranchs((Listitem)bankItem);
						break;
					}
				}
			}else{
				((Listbox)this.getFellow("bankList")).setSelectedIndex(0);
				updateBranchs((Listitem)this.getFellow("bankList").getFirstChild());
			}
			if(bankInfo.get(0).get("branchMaster")!=null){
				Listbox branchList = (Listbox)this.getFellow("branchList");
				for(Object branchItem : branchList.getItems()){
					if(((Listitem)branchItem).getValue().equals(bankInfo.get(0).get("branchMaster"))){
						((Listitem)branchItem).setSelected(true);
						break;
					}
				}
			}else{
				((Listbox)this.getFellow("branchList")).appendChild((Listitem)ComponentUtil.createNotRequiredListItem());
				((Listbox)this.getFellow("branchList")).setSelectedIndex(0);
			}
			if(bankInfo.get(0).get("bankAcctNo")!=null){
				((Textbox)this.getFellow("bankAcctNo")).setValue((String)bankInfo.get(0).get("bankAcctNo"));
			}else{
				((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			}
			if(bankInfo.get(0).get("defaultPaymentMode")!=null){
				Listbox pymtModesList = (Listbox)this.getFellow("paymentModeList");
				for(Object pymtModeItem : pymtModesList.getItems()){
					if(((Listitem)pymtModeItem).getValue().equals(bankInfo.get(0).get("defaultPaymentMode"))){
						((Listitem)pymtModeItem).setSelected(true);
						break;
					}
				}
			}else{
				((Listbox)this.getFellow("paymentModeList")).setSelectedIndex(0);
			}
		}else{
			((Listbox)this.getFellow("bankList")).setSelectedIndex(0);
			updateBranchs((Listitem)this.getFellow("bankList").getFirstChild());
			((Listbox)this.getFellow("branchList")).setSelectedIndex(0);
			((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			((Listbox)this.getFellow("paymentModeList")).setSelectedIndex(0);
		}
		
		//If payment mode selected is Interbank Giro, bank account is mandatory and enabled otherwise disabled.
		if(((Listbox)this.getFellow("paymentModeList")).getSelectedItem().getValue().equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(false);
		}
		else{
			((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(true);
		}
	}
	public void updateBranchs(Listitem selectedBank){
		logger.info("updateBranchs()");
		if(selectedBank.getValue()!=null && !selectedBank.getValue().equals("")){
			Listbox branchList = (Listbox)this.getFellow("branchList");
			if(branchList.getFirstChild()==null){
				branchList.appendChild(ComponentUtil.createNotRequiredListItem());
			}
			Listitem firstItem = branchList.getItemAtIndex(0);
			branchList.getItems().clear();
			branchList.appendChild(firstItem);
			Map<Integer, Map<String, String>> branchs = MasterSetup.getBankManager().getAllDetails((Integer)selectedBank.getValue());
			for(Integer branchNo : branchs.keySet()){
				branchList.appendChild(new Listitem(
						branchs.get(branchNo).get(BankMasterManager.DETAIL_BRANCH_CODE)
						+ " - " +
						branchs.get(branchNo).get(BankMasterManager.DETAIL_BRANCH_NAME)
						, branchNo));
			}
		}
	}
	public void deleteRow(Row row){
		Rows rows = (Rows)row.getParent();
		rows.removeChild(row);
		// now renumber the rows
		for(int i=1;i<rows.getChildren().size();i++){
			Row tempRow = (Row)rows.getChildren().get(i-1);
			((Label)tempRow.getFirstChild()).setValue(i + "");
		}
	}
	@SuppressWarnings("unchecked")
	public void addBilling(){
		logger.info("addBilling()");
		Rows billingRows = (Rows)this.getFellow("billing");
		Row billingRow = new Row();
		billingRow.appendChild(new Label(""+billingRows.getChildren().size()));
		Listbox billingCycle = new Listbox();
		billingCycle.setWidth("100%"); billingCycle.setRows(1); billingCycle.setMold("select");
		billingCycle.getItems().addAll(cloneList(billingCycles));
		billingCycle.setSelectedIndex(0);
		billingRow.appendChild(billingCycle);
		Listbox volumeDiscount = new Listbox();
		volumeDiscount.setWidth("100%"); volumeDiscount.setRows(1); volumeDiscount.setMold("select");
		volumeDiscount.getItems().addAll(cloneList(this.volumeDiscounts));
		volumeDiscount.setSelectedIndex(0);
		billingRow.appendChild(volumeDiscount);
		// adding [?] to row
		Image volDiscImage = new Image("/images/question.png");
		volDiscImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editBillingWindow.displayVolumeDiscount()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		volDiscImage.addEventHandler("onClick", pdEvent);
		billingRow.appendChild(volDiscImage);
		Listbox adminFee = new Listbox();
		adminFee.setWidth("100%"); adminFee.setRows(1); adminFee.setMold("select");
		adminFee.getItems().addAll(cloneList(this.adminFees));
		adminFee.setSelectedIndex(0);
		billingRow.appendChild(adminFee);
		// adding [?] to row
		Image adminFeeImage = new Image("/images/question.png");
		adminFeeImage.setStyle("cursor: help");
		showInfo = ZScript.parseContent("editBillingWindow.displayAdminFee()");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		adminFeeImage.addEventHandler("onClick", pdEvent);
		billingRow.appendChild(adminFeeImage);
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("90%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		billingRow.appendChild(effectiveDate);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editBillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		billingRow.appendChild(deleteImage);
		billingRows.insertBefore(billingRow, billingRows.getLastChild());
	}
	@SuppressWarnings("unchecked")
	public void addCreditTerm(){
		logger.info("addCreditTerm()");
		Rows creditTermRows = (Rows)this.getFellow("creditTerm");
		Row creditTermRow = new Row();
		creditTermRow.appendChild(new Label(""+creditTermRows.getChildren().size()));
		Listbox creditTermBox = new Listbox();
		creditTermBox.setWidth("100%"); creditTermBox.setRows(1); creditTermBox.setMold("select");
		creditTermBox.getItems().addAll(cloneList(creditTerms));
		creditTermBox.setSelectedIndex(0);
		creditTermRow.appendChild(creditTermBox);
		// adding [?] to row
		Image creditTermImage = new Image("/images/question.png");
		creditTermImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editBillingWindow.displayCreditTerm()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		creditTermImage.addEventHandler("onClick", pdEvent);
		creditTermRow.appendChild(creditTermImage);
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("91.5%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		creditTermRow.appendChild(effectiveDate);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editBillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		creditTermRow.appendChild(deleteImage);
		creditTermRows.insertBefore(creditTermRow, creditTermRows.getLastChild());
	}
	@SuppressWarnings("unchecked")
	public void addEarlyPymt(){
		logger.info("addEarlyPymt()");
		Rows earlyPymtRows = (Rows)this.getFellow("earlyPayment");
		Row earlyPymtRow = new Row();
		earlyPymtRow.appendChild(new Label(""+earlyPymtRows.getChildren().size()));
		Listbox earlyPymtBox = new Listbox();
		earlyPymtBox.setWidth("100%"); earlyPymtBox.setRows(1); earlyPymtBox.setMold("select");
		earlyPymtBox.getItems().addAll(cloneList(earlyPayments));
		earlyPymtBox.setSelectedIndex(0);
		earlyPymtRow.appendChild(earlyPymtBox);
		// adding [?] to row
		Image earlyPymtImage = new Image("/images/question.png");
		earlyPymtImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editBillingWindow.displayEarlyDiscount()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		earlyPymtImage.addEventHandler("onClick", pdEvent);
		earlyPymtRow.appendChild(earlyPymtImage);
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("91.5%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		earlyPymtRow.appendChild(effectiveDate);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editBillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		earlyPymtRow.appendChild(deleteImage);
		earlyPymtRows.insertBefore(earlyPymtRow, earlyPymtRows.getLastChild());
	}
	@SuppressWarnings("unchecked")
	public void addLatePymt(){
		logger.info("addLatePymt()");
		Rows latePymtRows = (Rows)this.getFellow("latePayment");
		Row latePymtRow = new Row();
		latePymtRow.appendChild(new Label(""+latePymtRows.getChildren().size()));
		Listbox latePymtBox = new Listbox();
		latePymtBox.setWidth("100%"); latePymtBox.setRows(1); latePymtBox.setMold("select");
		latePymtBox.getItems().addAll(cloneList(latePayments));
		latePymtBox.setSelectedIndex(0);
		latePymtRow.appendChild(latePymtBox);
		// adding [?] to row
		Image latePymtImage = new Image("/images/question.png");
		latePymtImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editBillingWindow.displayEarlyDiscount()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		latePymtImage.addEventHandler("onClick", pdEvent);
		latePymtRow.appendChild(latePymtImage);
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("91.5%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		latePymtRow.appendChild(effectiveDate);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editBillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		latePymtRow.appendChild(deleteImage);
		latePymtRows.insertBefore(latePymtRow, latePymtRows.getLastChild());
	}
	@SuppressWarnings("unchecked")
	public void addPromotion() throws InterruptedException{
		if(promotions.size()==0){
			Messagebox.show("No Promotion available!", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		logger.info("addPromotion()");
		Rows promotionRows = (Rows)this.getFellow("promotion");
		Row promotionRow = new Row();
		promotionRow.appendChild(new Label(""+promotionRows.getChildren().size()));
		Listbox promotionBox = new Listbox();
		promotionBox.setWidth("100%"); promotionBox.setRows(1); promotionBox.setMold("select");
		promotionBox.getItems().addAll(cloneList(promotions));
		promotionBox.setSelectedIndex(0);
		promotionRow.appendChild(promotionBox);
		// adding [?] to row
		Image promotionImage = new Image("/images/question.png");
		promotionImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editBillingWindow.displayPromotion()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		promotionImage.addEventHandler("onClick", pdEvent);
		promotionRow.appendChild(promotionImage);
		Datebox effectiveDateFrom = new Datebox();
		effectiveDateFrom.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		effectiveDateFrom.setWidth("91.5%");
		effectiveDateFrom.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDateFrom.setValue(convertToSQLDate(Calendar.getInstance()));
		promotionRow.appendChild(effectiveDateFrom);
		Datebox effectiveDateTo = new Datebox();
		effectiveDateTo.setWidth("91.5%");
		effectiveDateTo.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		promotionRow.appendChild(effectiveDateTo);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editBillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		promotionRow.appendChild(deleteImage);
		promotionRows.insertBefore(promotionRow, promotionRows.getLastChild());
	}
	public void save() throws InterruptedException{
		logger.info("save()");
		displayProcessing();
		// checking whether there is pending request
		if(this.businessHelper.getAccountBusiness().hasPendingBillingChangeRequest(custNo)){
			Messagebox.show("There is a pending request already!", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		/* For History*/
		
		Map<String, List<Map<String, Object>>> billingCycleDetailsHistory = this.businessHelper.getAccountBusiness().getBillingCycleHistoricalDetails(custNo);
		List<Map<String, Object>> billingCycleHistory = billingCycleDetailsHistory.get("billing");
		
		Map<String, List<Map<String, Object>>> creditTermDetailsHistory = this.businessHelper.getAccountBusiness().getCreditTermHistoricalDetails(custNo);
		// credit term
		List<Map<String, Object>> creditTermsHistory = creditTermDetailsHistory.get("creditTerm");
		
		Map<String, List<Map<String, Object>>> earlyPaymentMapHistory = this.businessHelper.getAccountBusiness().getEarlyPaymentHistoricalDetails(custNo);
		// early payment
		List<Map<String, Object>> earlyPaymentListHistory = earlyPaymentMapHistory.get("earlyPymt");
		
		Map<String, List<Map<String, Object>>> latePaymentMapHistory = this.businessHelper.getAccountBusiness().getLatePaymentHistoricalDetails(custNo);
		// billing
		List<Map<String, Object>> latePaymentListHistory = latePaymentMapHistory.get("latePymt");
		
		Map<String, List<Map<String, Object>>> promotionDetailsHistory = this.businessHelper.getAccountBusiness().getPromotionHistoricalDetails(custNo);
		// promotion
		List<Map<String, Object>> promotionListHistory = promotionDetailsHistory.get("promotion");
		/* For History*/
		
		// Extracting inputs
		// billing inputs
		Rows billingRows = (Rows)this.getFellow("billing");
		Map<Date, Map<String, Object>> billingMap = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object row : billingRows.getChildren()){
			if(!row.equals(billingRows.getLastChild())){
				Row billingRow = (Row)row;
				Datebox effectiveDate = (Datebox)billingRow.getChildren().get(6);
				if(effectiveDate.getValue()==null){
					Messagebox.show("Please fill in all effective dates for Billing Details", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Listbox billingCycle = (Listbox)billingRow.getChildren().get(1);
				Listitem billingCycleItem = billingCycle.getSelectedItem();
				Map<String, Object> billingDetail = new HashMap<String, Object>();
				billingDetail.put("billingCycle", billingCycleItem.getValue());
				Listbox volumeDiscountBox = (Listbox)billingRow.getChildren().get(2);
				Listitem volumeDiscountItem = volumeDiscountBox.getSelectedItem();
				billingDetail.put("volumeDiscount", volumeDiscountItem.getValue());
				Listbox adminFeeBox = (Listbox)billingRow.getChildren().get(4);
				Listitem adminFeeItem = adminFeeBox.getSelectedItem();
				billingDetail.put("adminFee", adminFeeItem.getValue());
				if(billingMap.containsKey(effectiveDate.getValue())){
					Messagebox.show("Duplicate effective dates in Billing Details", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				billingMap.put(effectiveDate.getValue(), billingDetail);
			}
		}
		
		//get history value
		if (billingCycleHistory != null) {
			if(billingCycleHistory.size() > 0)
			{
				for(Map<String, Object> bill : billingCycleHistory) {
					
					Map<String, Object> billingDetail = new HashMap<String, Object>();
					if(bill.get("billingCycle")!=null)
						billingDetail.put("billingCycle", bill.get("billingCycle"));
					else
						billingDetail.put("billingCycle", null);
					
					if(bill.get("volumeDiscount")!=null)
						billingDetail.put("volumeDiscount", bill.get("volumeDiscount"));
					else
						billingDetail.put("volumeDiscount", null);
					
					if(bill.get("adminFee")!=null)
						billingDetail.put("adminFee", bill.get("adminFee"));
					
					billingMap.put((Date)bill.get("effectiveDate"), billingDetail);
				}
			}
		}
		// now checking for no changes
//		String prevCycle = null;
//		Integer prevVolume = null;
//		int counter = 0;
//		for(Date effectiveDate : billingMap.keySet()){
//			// skip first row checking
//			if(counter++ != 0){
//				if(billingMap.get(effectiveDate).get("billingCycle").equals(prevCycle)){
//					// checking for both null
//					if(billingMap.get(effectiveDate).get("volumeDiscount")==prevVolume){
//						Messagebox.show("No changes detected for Billing Details. Please change billing details.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
//						return;
//					}else if(billingMap.get(effectiveDate).get("volumeDiscount")!=null && prevVolume!=null){// now checking for both value
//						if(billingMap.get(effectiveDate).get("volumeDiscount").equals(prevVolume)){
//							Messagebox.show("No changes detected for Billing Details. Please change billing details.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
//							return;
//						}
//					}
//				}
//			}
//			prevCycle = (String)billingMap.get(effectiveDate).get("billingCycle");
//			prevVolume = (Integer)billingMap.get(effectiveDate).get("volumeDiscount");
//		}
		// credit term inputs
		Rows creditTermRows = (Rows)this.getFellow("creditTerm");
		Map<Date, Integer> creditTermMap = new TreeMap<Date, Integer>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object row : creditTermRows.getChildren()){
			if(!row.equals(creditTermRows.getLastChild())){
				Row creditTermRow = (Row)row;
				Datebox effectiveDate = (Datebox)creditTermRow.getChildren().get(3);
				if(effectiveDate.getValue()==null){
					Messagebox.show("Please fill in all effective dates for Credit Term", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Listbox creditTerm = (Listbox)creditTermRow.getChildren().get(1);
				Listitem creditTermItem = creditTerm.getSelectedItem();
				if(creditTermMap.put(effectiveDate.getValue(), (Integer)creditTermItem.getValue())!=null){
					Messagebox.show("Duplicate effective dates in Credit Term", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		
		//get history value
		if (creditTermsHistory != null) {
			if(creditTermsHistory.size() > 0)
			{
				for(Map<String, Object> creditTermHist : creditTermsHistory) {
					// getting the details
					if(creditTermHist.get("creditTerm")!=null)
						creditTermMap.put((Date)creditTermHist.get("effectiveDate"), Integer.parseInt(creditTermHist.get("creditTerm").toString()));
					else
						creditTermMap.put((Date)creditTermHist.get("effectiveDate"), null);
				}
			}
		}
		// now checking for no changes
//		Integer prevTerm = null;
//		for(Date effectiveDate : creditTermMap.keySet()){
//			if(creditTermMap.get(effectiveDate).equals(prevTerm)){
//				Messagebox.show("No changes detected for Credit Term. Please change credit terms.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
//				return;
//			}else{
//				prevTerm = creditTermMap.get(effectiveDate);
//			}
//		}
		// early payment input
		Rows earlyPymtRows = (Rows)this.getFellow("earlyPayment");
		Map<Date, Integer> earlyPymtMap = new TreeMap<Date, Integer>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object row : earlyPymtRows.getChildren()){
			if(!row.equals(earlyPymtRows.getLastChild())){
				Row earlyPymtRow = (Row)row;
				Datebox effectiveDate = (Datebox)earlyPymtRow.getChildren().get(3);
				if(effectiveDate.getValue()==null){
					Messagebox.show("Please fill in all effective dates for Early Payment", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Listbox earlyPymt = (Listbox)earlyPymtRow.getChildren().get(1);
				Listitem earlyPymtItem = earlyPymt.getSelectedItem();
				if(earlyPymtMap.containsKey(effectiveDate.getValue())){
					Messagebox.show("Duplicate effective dates in Early Payment", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				if(earlyPymtItem.getValue()!=null){
					earlyPymtMap.put(effectiveDate.getValue(), (Integer)earlyPymtItem.getValue());
				}else{
					earlyPymtMap.put(effectiveDate.getValue(), null);
				}
			}
		}
		//get history value
		if (earlyPaymentListHistory != null) {
			if(earlyPaymentListHistory.size() > 0)
			{
				for(Map<String, Object> earlyPaymentHist : earlyPaymentListHistory) {
					// getting the details
					if(earlyPaymentHist.get("earlyPymt")!=null)
						earlyPymtMap.put((Date)earlyPaymentHist.get("effectiveDate"), Integer.parseInt(earlyPaymentHist.get("earlyPymt").toString()));
					else
						earlyPymtMap.put((Date)earlyPaymentHist.get("effectiveDate"), null);
				}
			}
		}
		
		// now checking for no changes
//		Integer prevEarly = null;
//		counter = 0;
//		for(Date effectiveDate : earlyPymtMap.keySet()){
//			if(counter++ !=0){
//				if(earlyPymtMap.get(effectiveDate) == prevEarly){
//					Messagebox.show("No changes detected for Early Payment. Please change early payment.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
//					return;
//				}else if(earlyPymtMap.get(effectiveDate)!=null && prevEarly!=null){
//					if(earlyPymtMap.get(effectiveDate).equals(prevEarly)){
//						Messagebox.show("No changes detected for Early Payment. Please change early payment.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
//						return;
//					}
//				}
//			}
//			prevEarly = earlyPymtMap.get(effectiveDate);
//		}
		// late payment input
		Rows latePymtRows = (Rows)this.getFellow("latePayment");
		Map<Date, Integer> latePymtMap = new TreeMap<Date, Integer>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object row : latePymtRows.getChildren()){
			if(!row.equals(latePymtRows.getLastChild())){
				Row latePymtRow = (Row)row;
				Datebox effectiveDate = (Datebox)latePymtRow.getChildren().get(3);
				if(effectiveDate.getValue()==null){
					Messagebox.show("Please fill in all effective dates for Credit Term", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Listbox latePymt = (Listbox)latePymtRow.getChildren().get(1);
				Listitem latePymtItem = latePymt.getSelectedItem();
				if(latePymtMap.put(effectiveDate.getValue(), (Integer)latePymtItem.getValue())!=null){
					Messagebox.show("Duplicate effective dates in late Payment", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		//get history value
		if (latePaymentListHistory != null) {
			if(latePaymentListHistory.size() > 0)
			{
				for(Map<String, Object> latePaymentHist : latePaymentListHistory) {
					// getting the details
					latePymtMap.put((Date)latePaymentHist.get("effectiveDate"), Integer.parseInt(latePaymentHist.get("latePymt").toString()));
				}
			}
		}
		
		// now checking for no changes
//		Integer prevLate = null;
//		for(Date effectiveDate : latePymtMap.keySet()){
//			if(latePymtMap.get(effectiveDate).equals(prevLate)){
//				Messagebox.show("No changes detected for Late Payment. Please change late payment.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
//				return;
//			}else{
//				prevLate = latePymtMap.get(effectiveDate);
//			}
//		}
		// promotion inputs
		Rows promotionRows = (Rows)this.getFellow("promotion");
		Set<Map<String, Object>> promotionSet = new TreeSet<Map<String, Object>>(new Comparator<Map<String, Object>>(){
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("effectiveDateFrom").equals(o2.get("effectiveDateFrom")) ? o1.toString().compareTo(o2.toString()) : ((Date)o1.get("effectiveDateFrom")).compareTo((Date)o2.get("effectiveDateFrom"));
			}
		});
		for(int i=0; i<promotionRows.getChildren().size()-1; i++){
			Object row = promotionRows.getChildren().get(i);
			Row promotionRow = (Row)row;
			Datebox effectiveDateFrom = (Datebox)promotionRow.getChildren().get(3);
			if(effectiveDateFrom.getValue()==null){
				Messagebox.show("Please fill in all effective dates from for Promotion", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			Datebox effectiveDateTo = (Datebox)promotionRow.getChildren().get(4);
			Listbox promotion = (Listbox)promotionRow.getChildren().get(1);
			Listitem promotionItem = promotion.getSelectedItem();
			
//			//result is 1(true) means YES there are overlapping
//			if(this.businessHelper.getAccountBusiness().checkPromotionOverlapping(custNo, (Integer)promotionItem.getValue(), effectiveDateFrom.getValue(), effectiveDateTo.getValue())){
//				Messagebox.show("There is overlapping with existing Promotion(s)", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
//				return;
//			}
			//Above codes are not required and not tested because below codes already fulfill above requirement.
			for(int j=0; j<promotionRows.getChildren().size()-1; j++){
				if(j==i) continue;
				
				Object comparingRow = promotionRows.getChildren().get(j);
				Row comparingPromotionRow = (Row)comparingRow;
				Datebox comparingEffectiveDateFrom = (Datebox)comparingPromotionRow.getChildren().get(3);
				if(comparingEffectiveDateFrom.getValue()==null){
					Messagebox.show("Please fill in all effective dates from for Promotion", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Datebox comparingEffectiveDateTo = (Datebox)comparingPromotionRow.getChildren().get(4);
				Listbox comparingPromotion = (Listbox)comparingPromotionRow.getChildren().get(1);
				Listitem comparingPromotionItem = comparingPromotion.getSelectedItem();
				
				logger.info("effectiveDateFrom:"+effectiveDateFrom.getValue());
				logger.info("effectiveDateTo:"+effectiveDateTo.getValue());
				logger.info("comparingEffectiveDateFrom:"+comparingEffectiveDateFrom.getValue());
				logger.info("comparingEffectiveDateTo:"+comparingEffectiveDateTo.getValue());
				if(((Integer)comparingPromotionItem.getValue()).intValue() == ((Integer)promotionItem.getValue()).intValue()){
					//from date b4 comparing from date
					if(effectiveDateFrom.getValue().compareTo(comparingEffectiveDateFrom.getValue()) < 0){
						if(effectiveDateTo.getValue()==null){
							logger.info("check 1 failed: effectiveDateFrom before comparingFromDate, effectiveDateTo endless");
							Messagebox.show("There is overlapping Promotion(s)", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}
						//from date b4 comparing from date && to date after comparing from date
						else if(effectiveDateFrom.getValue()!=null &&
								effectiveDateTo.getValue().compareTo(comparingEffectiveDateFrom.getValue()) >= 0){
							logger.info("check 2 failed: effectiveDateFrom before comparingFromDate, effectiveDateTo after comparingFromDate");
							Messagebox.show("There is overlapping Promotion(s)", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}
					}
					else{
						if(comparingEffectiveDateTo.getValue()==null){
							logger.info("check 3 failed: effectiveDateFrom after comparingFromDate, comparingToDate endless");
							Messagebox.show("There is overlapping Promotion(s)", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}
						else if(comparingEffectiveDateTo.getValue()!=null && 
								comparingEffectiveDateTo.getValue().compareTo(effectiveDateFrom.getValue()) >= 0){
							logger.info("check 4 failed: effectiveDateFrom after comparingFromDate, comparingToDate after effectiveDateFrom");
							Messagebox.show("There is overlapping Promotion(s)", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}
					}
				}
				
				logger.info("******");
			}
		}
		
		for(Object row : promotionRows.getChildren()){
			if(!row.equals(promotionRows.getLastChild())){
				Row promotionRow = (Row)row;
				Datebox effectiveDateFrom = (Datebox)promotionRow.getChildren().get(3);
				if(effectiveDateFrom.getValue()==null){
					Messagebox.show("Please fill in all effective dates from for Promotion", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Datebox effectiveDateTo = (Datebox)promotionRow.getChildren().get(4);
				Listbox promotion = (Listbox)promotionRow.getChildren().get(1);
				Listitem promotionItem = promotion.getSelectedItem();
				Map<String, Object> promotionMap = new HashMap<String, Object>();
				promotionMap.put("effectiveDateFrom", effectiveDateFrom.getValue());
				promotionMap.put("effectiveDateTo", effectiveDateTo.getValue());
				promotionMap.put("promotion", (Integer)promotionItem.getValue());
				promotionMap.put("acctPromotionNo", promotionRow.getValue());
				promotionSet.add(promotionMap);
			}
		}
		//get history value
		if (promotionListHistory != null) {
			if(promotionListHistory.size() > 0)
			{
				for(Map<String, Object> promotionHist : promotionListHistory) {
					// getting the details
					Map<String, Object> promotionMap = new HashMap<String, Object>();
					promotionMap.put("effectiveDateFrom", (Date)promotionHist.get("effectiveDateFrom"));
					promotionMap.put("effectiveDateTo", (Date)promotionHist.get("effectiveDateTo"));
					promotionMap.put("promotion", (Integer)promotionHist.get("promotion"));
					promotionMap.put("acctPromotionNo", (Integer)promotionHist.get("acctPromotionNo"));
					promotionSet.add(promotionMap);
				}
			}
		}
		
		// bank info inputs
		Map<String, Object> bankMap = new HashMap<String, Object>();
		Listbox bankList = (Listbox)this.getFellow("bankList");
		if(bankList.getSelectedItem()!=null){
			if(bankList.getSelectedItem().getValue()!=null && !bankList.getSelectedItem().getValue().equals("")){
				bankMap.put("bankNo", (Integer)bankList.getSelectedItem().getValue());
			}
		}
		Listbox branchList = (Listbox)this.getFellow("branchList");
		if(branchList.getSelectedItem()!=null){
			if(branchList.getSelectedItem().getValue()!=null && !branchList.getSelectedItem().getValue().equals("")){
				bankMap.put("branchNo", (Integer)branchList.getSelectedItem().getValue());
			}
		}
		bankMap.put("bankAcctNo", ((Textbox)this.getFellow("bankAcctNo")).getValue());
		Listbox paymentModeList = (Listbox)this.getFellow("paymentModeList");
		if(paymentModeList.getSelectedItem()!=null){
			if(paymentModeList.getSelectedItem().getValue()!=null && !paymentModeList.getSelectedItem().getValue().equals("")){
				bankMap.put("paymentModeCode", (String)paymentModeList.getSelectedItem().getValue());
				
				//Validation Check
				if(((String)paymentModeList.getSelectedItem().getValue()).equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
					if(bankMap.get("bankNo") == null || 
							bankMap.get("branchNo") == null ||
							bankMap.get("bankAcctNo") == null ||
							bankMap.get("bankAcctNo").toString().length()==0){
						Messagebox.show("Customer Bank Information must be all filled up when default payment mode is INTERBANK GIRO!",
								"Edit Billing", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
		}
		// now checking that billing, credit term and late payment must have one effective
		boolean found = false;
		for(Date effectiveDate : billingMap.keySet()){
			if(effectiveDate.before(DateUtil.getCurrentDate())){
				found = true;
				break;
			}
		}
		if(!found){
			Messagebox.show("There is no Billing Details that is in effect!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		found = false;
		for(Date effectiveDate : creditTermMap.keySet()){
			if(effectiveDate.before(DateUtil.getCurrentDate())){
				found = true;
				break;
			}
		}
		if(!found){
			Messagebox.show("There is no Credit Term that is in effect!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		found = false;
		for(Date effectiveDate : latePymtMap.keySet()){
			if(effectiveDate.before(DateUtil.getCurrentDate())){
				found = true;
				break;
			}
		}
		if(!found){
			Messagebox.show("There is no Late Payment that is in effect!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String userId = this.getUserLoginIdAndDomain();
		if(this.businessHelper.getAccountBusiness().createBillingRequest(custNo, billingMap, creditTermMap, earlyPymtMap, latePymtMap, promotionSet, bankMap, userId)){
			Messagebox.show("Billing information saved", "Edit Billing", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else{
			Messagebox.show("Unable to save. Please try again later", "Edit Billing", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public void viewDeposit() throws InterruptedException{
		logger.info("viewDeposit()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(Uri.VIEW_DEPOSIT, params, this.getParent());
	}
	@Override
	public void refresh() throws InterruptedException {
		this.init();
	}
	public List<Listitem> getPaymentModes(){
		return this.pymtMode;
	}
	public List<Listitem> getBanks(){
		return this.banks;
	}
	private java.sql.Date convertToSQLDate(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(calendar.getTimeInMillis());
	}
	
	public void onSelectPaymentMode(Listitem item){
		//If payment mode selected is Interbank Giro, bank account is mandatory and enabled otherwise disabled.
		if(item.getValue().equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(false);
		}
		else{
			((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(true);
		}
	}
	public void viewBillingCycleHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewBillingCycleHistory()");
		super.viewBillingCycleHistory(custNo);
	}
	public void viewCreditTermHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewCreditTermHistory()");
		super.viewCreditTermHistory(custNo);
	}
	public void viewEarlyPaymentHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewEarlyPaymentHistory()");
		super.viewEarlyPaymentHistory(custNo);
	}
	public void viewLatePaymentHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewLatePaymentHistory()");
		super.viewLatePaymentHistory(custNo);
	}
	public void viewPromotionHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewPromotionHistory()");
		super.viewPromotionHistory(custNo);
	}
}