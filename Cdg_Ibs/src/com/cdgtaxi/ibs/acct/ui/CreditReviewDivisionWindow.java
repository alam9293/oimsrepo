package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.IBSException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class CreditReviewDivisionWindow extends CommonCreditReviewWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreditReviewDivisionWindow.class);
	private String custNo; 
	private ArrayList<String> codes;
	private ListModelList modelList;
	
	@SuppressWarnings("unchecked")
	public CreditReviewDivisionWindow() throws InterruptedException{
		logger.info("CreditReviewDivisionWindow()");
		//customer number
		Map<String, Object> map = Executions.getCurrent().getArg();
		custNo = (String)map.get("custNo");
		Object cObject= map.get("code");
		if(cObject instanceof ArrayList<?>){
			codes = (ArrayList<String>)cObject;
		}else {
			codes = new ArrayList<String>();
			codes.add((String)cObject);
		}
	
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		try
		{
			if(custNo==null || custNo.trim().length()==0){
				throw new IBSException("No account number found!");
			}
			// account code
			if(codes==null || codes.size()==0){
				throw new IBSException("No account code found!");
			}
			
			for(String code: codes){
				if(code==null || "".equals(code.trim()))
					throw new IBSException("Selected division contain null division code!");
			}
		}
		catch(IBSException e){
			Messagebox.show(e.getMessage(), "Credit Review Division", Messagebox.OK, Messagebox.ERROR);
			this.back();
			return;
		}
		populateData(); 
	}
	public void creditReview() throws InterruptedException{
	
		validateForm();
		
		if(this.businessHelper.getAccountBusiness().hasPendingPermanentCreditReview(custNo)){
			Messagebox.show("There is existing pending request for corporate!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		BigDecimal divCreditLimit = ((Decimalbox)this.getFellow("newCreditLimit")).getValue();
//		if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, null, divCreditLimit)){
//			Messagebox.show("Credit Limit is greater than Corporate!", "Credit Review Division", Messagebox.OK, Messagebox.ERROR);
//			return;
//		}
		
		//check if changing in credit limit for all selected divison
		boolean changed = false;
		for(Object mObj: modelList){
			CreditReviewDivisionModel model = (CreditReviewDivisionModel)mObj;
			if(!divCreditLimit.equals(model.getCreditLimit())){
				changed = true;
				break;
			}
		}
		if(!changed){
			Messagebox.show("No change in credit limit!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		String promptMsg;
		String reviewType = (String)((Listbox)this.getFellow("reviewTypeList")).getSelectedItem().getValue();
		ArrayList<String> tempCreditLimitDivCodes = new ArrayList<String>();
		ArrayList<String> tempCreditLimitDeptCodes = new ArrayList<String>();
		
		if(reviewType.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			Date toDate = ((Datebox)this.getFellow("toDate")).getValue();
			if(toDate==null){
				Messagebox.show("Effective Date To cannot be empty for temporary credit review!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}else{
				if(fromDate.after(toDate)){
					Messagebox.show("Effective Date To must be equal to or later than Effective Date From!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){
					Messagebox.show("Effective Date From must be equal to or later than today!", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
			
			//check if selected code temporary credit limit greater than permanent credit limit
			for(Object mObj: modelList){
				CreditReviewDivisionModel model = (CreditReviewDivisionModel)mObj;
				if(model.getCreditLimit().compareTo(divCreditLimit)>0){
					showErrorMsg(model.getDivCode(), "Temporary credit limit must be greater than permanent credit limit!");
					return;
				}	
			}
			
			//check if exists temporary credit limit for selected code
			for(String code: codes){
				if(this.businessHelper.getAccountBusiness().hasTempCreditLimit(custNo, null, code)){
					tempCreditLimitDivCodes.add(code);
				}
				if(this.businessHelper.getAccountBusiness().hasTempChildFutureCreditLimit(custNo, null, code)){
					tempCreditLimitDeptCodes.add(code);
				}
			}
			
//			#39 2016 check parent Credit Limit
			if(this.businessHelper.getAccountBusiness().checkParentCreditLimitRange(custNo, null, fromDate, toDate, divCreditLimit)){
				Messagebox.show("Temporary Credit Limit Not Within Main Account's Limit", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			if(tempCreditLimitDivCodes.size()>0){
				promptMsg = "There is existing temporary credit limit for division " + tempCreditLimitDivCodes.toString() +". Update?";
			} else {
				promptMsg = "Confirm update credit limit?";
			}
			
		}else{
			
			if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, null, divCreditLimit)){
				Messagebox.show("Credit Limit is greater than Corporate!", "Credit Review Division", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){
				Messagebox.show("Effective Date From must be later than today!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			for(String code: codes){
				if(this.businessHelper.getAccountBusiness().checkChildrenCreditLimit(custNo, code, divCreditLimit)){
					showErrorMsg(code, "Credit limit must be greater than children's credit limit!");
					return;
				}
				if(this.businessHelper.getAccountBusiness().hasPermCreditLimit(custNo, null, code, fromDate)){
					showErrorMsg(code, "There is existing permanent credit limit on that date!");
					return;
				}
			}
			promptMsg = "Confirm update credit limit?";
		}
		
		if(Messagebox.show(promptMsg, "Credit Review Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				logger.debug("Credit Review for CustNo: " + custNo);
				
				//if got temp future. child limit.
				if(tempCreditLimitDivCodes.size()>0 && tempCreditLimitDeptCodes.size() > 0)
				{
					String promptMsg2 = "Warning: There are future Temporary Credit limits in Dept. Do you want to clear Dept's future Temporary Limits?";
					String userId = this.getUserLoginIdAndDomain();
					
					if(Messagebox.show(promptMsg2, "Credit Review Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
						
						//do clear dept limit
						for(String code: codes){
							this.businessHelper.getAccountBusiness().clearFutureChildCreditLimit(custNo, null, code, userId);
						}
						
						//do back original code
						for(String code: codes){
							logger.debug("-Credit Review for Code: " + code);
							super.creditReview(custNo, null, code);
						}
						Messagebox.show("Division Credit Limit updated", "Credit Review Division", Messagebox.OK, Messagebox.INFORMATION);
						this.back();
					}
				}
				else
				{
					for(String code: codes){
						logger.debug("-Credit Review for Code: " + code);
						super.creditReview(custNo, null, code);
					}
					Messagebox.show("Division Credit Limit updated", "Credit Review Division", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}
			}catch (WrongValueException wve){
				throw wve;
			}catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to raise request! Please try again later", "Credit Review Corporate", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	
	
	public void populateData(){
		
		try{
			((Datebox)this.getFellow("fromDate")).setValue(new Date());
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.setItemRenderer(new ResultRenderer());
			modelList = new ListModelList();
			
			for(String code: codes){
				Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DIVISION_LEVEL, null, code);
				CreditReviewDivisionModel model = new CreditReviewDivisionModel();
				model.setDivCode((String)details.get("acctCode"));
				model.setDivName((String)details.get("acctName"));
				
				if(details.get("parentTempCreditLimit") != null)
					model.setParentCreditLimit((String)details.get("parentCreditLimit") + " (Temp "+details.get("parentTempCreditLimit")+")");
				else
					model.setParentCreditLimit((String)details.get("parentCreditLimit"));
				model.setCreditLimit((BigDecimal)details.get("creditLimit"));
				model.setCreditBalance((String)details.get("creditBalance"));
				modelList.add(model);
			}
			resultListBox.setModel(modelList);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void showErrorMsg(String divCode, String msg) throws InterruptedException{
		Messagebox.show(msg + " [" + divCode + "]", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
	}
	
	 public class ResultRenderer implements ListitemRenderer {
		    public void render(Listitem row, Object obj) {
		    	
		    	CreditReviewDivisionModel model = (CreditReviewDivisionModel) obj;
		    	row.appendChild(new Listcell(model.getDivName()));
		    	row.appendChild(new Listcell(model.getDivCode()));
		    	row.appendChild(new Listcell(model.getParentCreditLimit()));
		    	String creditLimit = StringUtil.bigDecimalToString(model.getCreditLimit(),  StringUtil.GLOBAL_DECIMAL_FORMAT);
		    	row.appendChild(new Listcell(creditLimit));
		    	row.appendChild(new Listcell(model.getCreditBalance()));
		    }
		  }
	

	 public class CreditReviewDivisionModel{
		
		private String divCode;
		private String divName;
		private BigDecimal creditLimit;
		private String creditBalance;
		private String parentCreditLimit;
		
		public String getDivCode() {
			return divCode;
		}
		public void setDivCode(String divCode) {
			this.divCode = divCode;
		}
		public String getDivName() {
			return divName;
		}
		public void setDivName(String divName) {
			this.divName = divName;
		}
		public BigDecimal getCreditLimit() {
			return creditLimit;
		}
		public void setCreditLimit(BigDecimal creditLimit) {
			this.creditLimit = creditLimit;
		}
		public String getParentCreditLimit() {
			return parentCreditLimit;
		}
		public void setParentCreditLimit(String parentCreditLimit) {
			this.parentCreditLimit = parentCreditLimit;
		}
		public String getCreditBalance() {
			return creditBalance;
		}
		public void setCreditBalance(String creditBalance) {
			this.creditBalance = creditBalance;
		}
	}
	
}