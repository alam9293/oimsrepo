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

public class CreditReviewDeptWindow extends CommonCreditReviewWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreditReviewDeptWindow.class);
	private String custNo;
	private ArrayList<String> codes, parentCodes;
	private ListModelList modelList;
	@SuppressWarnings("unchecked")
	public CreditReviewDeptWindow() throws InterruptedException{
		logger.info("CreditReviewDeptWindow()");
		
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		Object cObject= map.get("code");
		Object pObject= map.get("parentCode");
		if(cObject instanceof ArrayList<?> && pObject instanceof ArrayList<?>){
			codes = (ArrayList<String>)cObject;
			parentCodes = (ArrayList<String>)pObject;
		}else {
			codes = new ArrayList<String>();
			parentCodes = new ArrayList<String>();
			codes.add((String)cObject);
			parentCodes.add((String)pObject);
		}
		
	}
	public void init() throws InterruptedException{
		logger.info("init()");
		try
		{
			//customer number
			if(custNo==null || custNo.trim().length()==0){
				throw new IBSException("No account number found!");
			}
			// account code
			if(codes==null || codes.size()==0){
				throw new IBSException("No account code found!");
			}
			// parent code
			if(parentCodes==null || parentCodes.size()==0){
				throw new IBSException("No parent code found!");
			}
			
			if(codes.size()!=parentCodes.size()){
				throw new IBSException("Code size not match with parent code size!");
			}
			
			for(String code: codes){
				if(code==null || "".equals(code.trim())){
					throw new IBSException("Selected department contain null department code!");
				}
			}
			for(String parentcode: parentCodes){
				if(parentcode==null || "".equals(parentcode.trim()))
					throw new IBSException("Selected department contain null parent (division) code!");
			}
		}
		catch(IBSException e){
			Messagebox.show(e.getMessage(), "Credit Review Department", Messagebox.OK, Messagebox.ERROR);
			this.back();
			return;
		}
		populateData(); 
	}
	public void creditReview() throws InterruptedException{
		
		validateForm();
		
		if(this.businessHelper.getAccountBusiness().hasPendingPermanentCreditReview(custNo)){
			Messagebox.show("There is existing pending request for corporate!", "Credit Review Department", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		BigDecimal deptCreditLimit = ((Decimalbox)this.getFellow("newCreditLimit")).getValue();
//		for(Object mObj: modelList){
//			CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
//			String parentCode = model.getDivCode();
//			if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, parentCode, deptCreditLimit)){
//				showErrorMsg(model.getDeptCode(), "Credit Limit is greater than Division Credit Limit!");
//				return;
//			}
//		}
		
		//check if changing in credit limit for all selected divison
		boolean changed = false;
		for(Object mObj: modelList){
			CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
			if(!deptCreditLimit.equals(model.getCreditLimit())){
				changed = true;
				break;
			}
		}
		if(!changed){
			Messagebox.show("No change in credit limit!", "Credit Review Department", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		String promptMsg;
		String reviewType = (String)((Listbox)this.getFellow("reviewTypeList")).getSelectedItem().getValue();
		if(reviewType.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
			Date toDate = ((Datebox)this.getFellow("toDate")).getValue();
			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			if(toDate==null){
				Messagebox.show("Effective Date To cannot be empty for temporary credit review!", "Credit Review Department", Messagebox.OK, Messagebox.EXCLAMATION);
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
				CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
				if(model.getCreditLimit().compareTo(deptCreditLimit)>0){
					showErrorMsg(model.getDeptCode(), "Temporary credit limit must be greater than permanent credit limit!");
					return;
				}	
			}
			
			//check if exists temporary credit limit for selected code
			ArrayList<String> tempCreditLimitDeptCodes = new ArrayList<String>();
			for(Object mObj: modelList){
				CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
				String code = model.getDeptCode();
				String parentCode = model.getDivCode();
				if(this.businessHelper.getAccountBusiness().hasTempCreditLimit(custNo, parentCode, code)){
					tempCreditLimitDeptCodes.add(code);
				}
			}
			
			//#39 check parent Credit Limit
			for(Object mObj: modelList){
				CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
				String parentCode = model.getDivCode();
				if(this.businessHelper.getAccountBusiness().checkParentCreditLimitRange(custNo, parentCode, fromDate, toDate, deptCreditLimit)){
					Messagebox.show("Temporary Credit Limit Not Within Main Account's Limit", "Credit Review Corporate", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
			
			
			if(tempCreditLimitDeptCodes.size()>0){
				promptMsg = "There is existing temporary credit limit for department " + tempCreditLimitDeptCodes.toString() +". Update?";
			} else {
				promptMsg = "Confirm update credit limit?";
			}

		}else{
			
			for(Object mObj: modelList){
				CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
				String parentCode = model.getDivCode();
				if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, parentCode, deptCreditLimit)){
					showErrorMsg(model.getDeptCode(), "Credit Limit is greater than Division Credit Limit!");
					return;
				}
			}
			
			Date fromDate = ((Datebox)this.getFellow("fromDate")).getValue();
			if(!DateUtil.isToday(fromDate) && fromDate.before(DateUtil.getCurrentDate())){
				Messagebox.show("Effective Date From must be later than today!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			for(Object mObj: modelList){
				CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
				String code = model.getDeptCode();
				String parentCode = model.getDivCode();
				if(this.businessHelper.getAccountBusiness().hasPermCreditLimit(custNo, parentCode, code, fromDate)){
					Messagebox.show("There is existing permanent credit limit on that date!", "Credit Review Division", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
			promptMsg = "Confirm update credit limit?";
		}
		if(Messagebox.show(promptMsg, "Credit Review Department", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try {
				for(Object mObj: modelList){
					CreditReviewDepartmentModel model = (CreditReviewDepartmentModel)mObj;
					String code = model.getDeptCode();
					String parentCode = model.getDivCode();
					super.creditReview(custNo, parentCode, code);
				}
				Messagebox.show("Department Credit Limit updated", "Credit Review Department", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
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
		
			int codesSize = codes.size();
			for(int i=0; i<codesSize; i++){
				
				String code = codes.get(i);
				String parentCode = parentCodes.get(i);
				
				Map<String, Object> details = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.DEPARTMENT_LEVEL, parentCode, code);
				CreditReviewDepartmentModel model = new CreditReviewDepartmentModel();
				model.setDeptCode((String)details.get("acctCode"));
				model.setDeptName((String)details.get("acctName"));
				model.setDivCode(parentCode);
				model.setDivName((String)details.get("parentName"));
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
		    	CreditReviewDepartmentModel model = (CreditReviewDepartmentModel) obj;
		    	row.appendChild(new Listcell(model.getDivName()));
		    	row.appendChild(new Listcell(model.getDeptName()));
		    	row.appendChild(new Listcell(model.getDeptCode()));
		    	row.appendChild(new Listcell(model.getParentCreditLimit()));
		    	String creditLimit = StringUtil.bigDecimalToString(model.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT);
		    	row.appendChild(new Listcell(creditLimit));
		    	row.appendChild(new Listcell(model.getCreditBalance()));
		    }
		  }
	

	 public class CreditReviewDepartmentModel{
		
		private String divCode;
		private String divName;
		private String deptCode;
		private String deptName;
		private BigDecimal creditLimit;
		private String parentCreditLimit;
		private String creditBalance;
		
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
		public String getDeptCode() {
			return deptCode;
		}
		public void setDeptCode(String deptCode) {
			this.deptCode = deptCode;
		}
		public String getDeptName() {
			return deptName;
		}
		public void setDeptName(String deptName) {
			this.deptName = deptName;
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