	package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.AccountSearchUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
	
public class ManageProductWindow extends CommonWindow implements AfterCompose{
	
	ProductSearchCriteria productSearchCriteria=new ProductSearchCriteria();
	private static Logger logger = Logger.getLogger(ManageProductWindow.class);
	//private PmtbProduct product=new PmtbProduct();
	private boolean checkCorpAndAppAccountNo=false;
	private boolean checkDivAndSubApplccountNo=false;
	private boolean checkDepartmentAccountNo=false;
	private Map<String,String> productIdAndStatusMap = new HashMap<String,String> ();
	private Integer selectedIndex = null;
		
	public ManageProductWindow(){}
		
	public void clickCorpAndApplAccountList(){
		checkCorpAndAppAccountNo=true;
		checkDivAndSubApplccountNo=false;
		checkDepartmentAccountNo=false;
	}
	public void clickDivAndSubApplAccountList(){
		checkCorpAndAppAccountNo=false;
		checkDivAndSubApplccountNo=true;
		checkDepartmentAccountNo=false;
	}
	public void clickDepartementAccountList(){
		checkCorpAndAppAccountNo=false;
		checkDivAndSubApplccountNo=false;
		checkDepartmentAccountNo=true;
	}
	public void search() throws InterruptedException{
			
		boolean check_flag=true;
		//Getting zul components
		ProductSearchCriteria productSearchCriteria=new ProductSearchCriteria();
		Decimalbox cardNumberStart=(Decimalbox) this.getFellow("cardNumberStart");
		Decimalbox cardNumberEnd=(Decimalbox) this.getFellow("cardNumberEnd");
		
		Listbox productType=(Listbox) this.getFellow("productTypeList");
		Listbox productStatus=(Listbox) this.getFellow("productStatusList");
		Datebox issueDateFrom=(Datebox)this.getFellow("issueDateFrom");
		Datebox issueDateTo=(Datebox)this.getFellow("issueDateTo");
		Datebox expiryDateFrom=(Datebox)this.getFellow("expiryDateFrom");
		Datebox expiryDateTo=(Datebox)this.getFellow("expiryDateTo");
		Datebox suspendDateFrom=(Datebox)this.getFellow("suspendDateFrom");
		Datebox suspendDateTo=(Datebox)this.getFellow("suspendDateTo");
		Listbox sortByList=(Listbox) this.getFellow("sortByList");
		Combobox nameCbo=(Combobox)this.getFellow("nameCbo");
		Listbox divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
		Listbox departmentList=(Listbox)this.getFellow("departmentList");
		Intbox acctNoText=(Intbox) this.getFellow("accNo");
		String custId=(String) acctNoText.getText();
		String nameOnCard = (String) ((CapsTextbox) this.getFellow("nameOnCard")).getText();
		
		String employeeId = (String) ((CapsTextbox) this.getFellow("employeeId")).getText();
		String cardHolderMobile = (String) ((CapsTextbox) this.getFellow("cardHolderMobile")).getText();
		String cardHolderEmail = (String) ((CapsTextbox) this.getFellow("cardHolderEmail")).getText();
		
		Datebox balanceExpiryDateFrom=(Datebox)this.getFellow("balanceExpiryFrom");
		Datebox balanceExpiryDateTo=(Datebox)this.getFellow("balanceExpiryTo");
		
		try{
			//Getting Search Criteria
			//Getting Account No. 	
			String accName=(String)nameCbo.getText();
			if(accName!=null && accName.trim().length()>0){
				if(accName.indexOf('(')>0){
					int indexOfRoundBracket=accName.indexOf('(');
					accName=accName.substring(0,indexOfRoundBracket-2);
					//accName=accName.substring(0,accName.length()-5);
				}
				productSearchCriteria.setAccName(accName);
			}
			boolean checkLevel=false;
			
			try{
				//logger.info("message1"+departmentList.getSelectedItem().getValue());
				if(departmentList.getSelectedItem().getValue()!=null){
					checkCorpAndAppAccountNo=false;
					checkDivAndSubApplccountNo=false;
					checkDepartmentAccountNo=true;
					checkLevel=false;
				}
			}catch(Exception ex){
				checkLevel=true;
				//logger.info("message2");
				checkCorpAndAppAccountNo=false;
				checkDivAndSubApplccountNo=false;
				checkDepartmentAccountNo=false;
			}
			
			if(checkLevel){
				try{
					logger.info("message1"+divOrSubApplList.getSelectedItem().getValue());
					if(divOrSubApplList.getSelectedItem().getValue()!=null){
						checkCorpAndAppAccountNo=false;
						checkDivAndSubApplccountNo=true;
						checkDepartmentAccountNo=false;
						checkLevel=false;
					}
				}catch(Exception ex){
					//logger.info("message1");
					checkCorpAndAppAccountNo=false;
					checkDivAndSubApplccountNo=false;
					checkDepartmentAccountNo=false;
					checkLevel=true;
				}
			}
			if(checkLevel){
				try{
					//logger.info("message1"+nameCbo.getSelectedItem().getValue());
					if(nameCbo.getSelectedItem().getValue()!=null){
						checkCorpAndAppAccountNo=true;
						checkDivAndSubApplccountNo=false;
						checkDepartmentAccountNo=false;
					}
				}catch(Exception ex){
					//logger.info("message3");
					checkCorpAndAppAccountNo=false;
					checkDivAndSubApplccountNo=false;
					checkDepartmentAccountNo=false;
				}
			}
		
			if(checkDepartmentAccountNo){
				if(departmentList.getItemCount()!=0)
					productSearchCriteria.setAccNo((String)departmentList.getSelectedItem().getValue());
				productSearchCriteria.setAccName(null);
			}
			else if(checkDivAndSubApplccountNo){
				productSearchCriteria.setAccNo((String)divOrSubApplList.getSelectedItem().getValue());
				productSearchCriteria.setAccName(null);
			}
			else if(checkCorpAndAppAccountNo){
				if(nameCbo.getSelectedItem()!=null)
					productSearchCriteria.setAccNo((String)nameCbo.getSelectedItem().getValue());
				productSearchCriteria.setAccName(null);
			}
			else{
				//for the issue log version 0.6, account search issue
				//start code
				if(custId!=null && custId.length()>0)
					productSearchCriteria.setCustNo(custId);
				//end of code
			}
			//Getting Product Details
			if(cardNumberStart.getValue()!=null)
				productSearchCriteria.setCardNoStart(cardNumberStart.getValue().toString());
			else
				productSearchCriteria.setCardNoStart(null);
			if(cardNumberEnd.getValue()!=null)
				productSearchCriteria.setCardNoEnd(cardNumberEnd.getValue().toString());
			else
				productSearchCriteria.setCardNoEnd(null);
			if(productType.getItemCount()!=0){
				productSearchCriteria.setProductType((String)productType.getSelectedItem().getValue());
			}
			if(productStatus.getItemCount()!=0){
				productSearchCriteria.setProductStatus((String)productStatus.getSelectedItem().getValue());
				if(productSearchCriteria.getProductStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ALL)){
					productSearchCriteria.setProductStatus(null);
//					logger.info("It will be null here");
				}
			}
			
			productSearchCriteria.setIssueDateFrom((Date)issueDateFrom.getValue());
			productSearchCriteria.setIssueDateTo((Date)issueDateTo.getValue());
			productSearchCriteria.setExpiryDateFrom(DateUtil.getFirstUtilDateOfMonth((Date)expiryDateFrom.getValue()));
			productSearchCriteria.setExpiryDateTo(DateUtil.getLastUtilDateOfMonth((Date)expiryDateTo.getValue()));
			productSearchCriteria.setSuspensionDateFrom((Date)suspendDateFrom.getValue());
			productSearchCriteria.setSuspensionDateTo(DateUtil.convertDateTo2359Hours(suspendDateTo.getValue()));
			
			productSearchCriteria.setBalanceExpiryDateFrom((Date)balanceExpiryDateFrom.getValue());
			productSearchCriteria.setBalanceExpiryDateTo(DateUtil.convertDateTo2359Hours(balanceExpiryDateTo.getValue()));
			
			productSearchCriteria.setSortBy((String)sortByList.getSelectedItem().getValue());
			
			productSearchCriteria.setNameOnCard(nameOnCard);
			
			productSearchCriteria.setEmployeeId(employeeId);
			productSearchCriteria.setCardHolderMobile(cardHolderMobile);
			productSearchCriteria.setCardHolderEmail(cardHolderEmail);
			
			//check_flag=true;
			if(productSearchCriteria.getIssueDateFrom()!=null && productSearchCriteria.getIssueDateTo()!=null){
				if(productSearchCriteria.getIssueDateTo().before(productSearchCriteria.getIssueDateFrom())){
					check_flag=false;
					Messagebox.show("Issue Date To shouldn't be earlier than Issue Date From .", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
				}
				
			}
			if(productSearchCriteria.getExpiryDateFrom()!=null && productSearchCriteria.getExpiryDateTo()!=null){
				if(productSearchCriteria.getExpiryDateTo().before(productSearchCriteria.getExpiryDateFrom())){
					check_flag=false;
					Messagebox.show("Expiry Date To shouldn't be earlier than Expiry Date From .", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
				}
				
			}
			if(productSearchCriteria.getSuspensionDateFrom()!=null && productSearchCriteria.getSuspensionDateTo()!=null){
				if(productSearchCriteria.getSuspensionDateTo().before(productSearchCriteria.getSuspensionDateFrom())){
					check_flag=false; 
					Messagebox.show("Suspension Date To shouldn't be earlier than Suspension Date From.", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			
			if(productSearchCriteria.getBalanceExpiryDateFrom()!=null && productSearchCriteria.getBalanceExpiryDateTo()!=null){
				if(productSearchCriteria.getBalanceExpiryDateTo().before(productSearchCriteria.getBalanceExpiryDateFrom())){
					check_flag=false;
					Messagebox.show("Balance Expiry Date To shouldn't be earlier than Balance Expiry Date From .", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
				}
				
			}
			
			//logger.info("************************************************");
			logger.info("SEARCH CRITERIA");
			//logger.info("************************************************");
			logger.info("1.Account No:"+productSearchCriteria.getAccNo());
			logger.info("2.Cust No:"+productSearchCriteria.getCustNo());
			logger.info("3.Account Name:"+productSearchCriteria.getAccName());
			logger.info("4.Card No Start:"+productSearchCriteria.getCardNoStart());
			logger.info("5.Card No End:"+productSearchCriteria.getCardNoEnd());
			logger.info("6.Product Type:"+productSearchCriteria.getProductType());
			logger.info("7.Product Status:"+productSearchCriteria.getProductStatus());
			logger.info("8.Issue Date From:"+productSearchCriteria.getIssueDateFrom());
			logger.info("9.Issue Date To:"+productSearchCriteria.getIssueDateTo());
			logger.info("10.Expiry Date From:"+productSearchCriteria.getExpiryDateFrom());
			logger.info("11.Expiry Date To:"+productSearchCriteria.getExpiryDateTo());
			logger.info("12.Suspend Date From:"+productSearchCriteria.getSuspensionDateFrom());
			logger.info("13.Suspend Date To:"+productSearchCriteria.getSuspensionDateTo());
			logger.info("14.Name On Card:"+productSearchCriteria.getNameOnCard());
			logger.info("15.Employee Id:"+productSearchCriteria.getEmployeeId());
			logger.info("16.Card Holder Mobile:"+productSearchCriteria.getCardHolderMobile());
			logger.info("17.Card Holder Email:"+productSearchCriteria.getCardHolderEmail());
			
		}catch(Exception ex){
			productSearchCriteria.setAccNo(null);
			logger.info("No Account No.");
			ex.printStackTrace();
		}
		
		//check_flag=true;
		if(check_flag){
			
			Map<String, Map<String, String>> dataMap=null;
			if((productSearchCriteria.getCustNo()==null || productSearchCriteria.getCustNo().length()<1 ) && (productSearchCriteria.getAccNo()==null || productSearchCriteria.getAccNo().trim().length()<1))
			{
				if(productSearchCriteria.getProductType()==null || productSearchCriteria.getProductType().trim().length()<1 || productSearchCriteria.getProductType().equalsIgnoreCase("-"))
				{
					if((productSearchCriteria.getCardNoStart()==null || productSearchCriteria.getCardNoStart().length()<1)
						||(productSearchCriteria.getCardNoEnd()==null || productSearchCriteria.getCardNoEnd().length()<1))	
					{
						if (productSearchCriteria.getNameOnCard()==null || "".equalsIgnoreCase(productSearchCriteria.getNameOnCard()))
						{
							if(productSearchCriteria.getEmployeeId()==null || "".equalsIgnoreCase(productSearchCriteria.getEmployeeId()))
							{
								if(productSearchCriteria.getCardHolderMobile()==null || "".equalsIgnoreCase(productSearchCriteria.getCardHolderMobile()))
								{
									if(productSearchCriteria.getCardHolderEmail()==null || "".equalsIgnoreCase(productSearchCriteria.getCardHolderEmail()))
									{
										Messagebox.show("Please enter one of mandatory filters (Account No, Card Range, Product Type, Name On Card)", "Manage Product", Messagebox.OK, Messagebox.ERROR);
										return;
									}
								}
							}
						}
					}
				}
			}
			displayProcessing();
			dataMap=this.businessHelper.getProductBusiness().getProducts(productSearchCriteria);
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultListBox.getItems().clear();
			if(dataMap.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			if(dataMap!=null){
				try{
					int count=0;
					for(String productid : dataMap.keySet()){
						count++;
						Listitem item = new Listitem();
					
						Map<String,String> productDetails=dataMap.get(productid);
						
						Listcell lastCell=new Listcell();
						lastCell.appendChild(new Checkbox());
						item.appendChild(lastCell);
						resultListBox.appendChild(item);
						item.setValue(productid);
						item.appendChild(newListcell(new Integer(productDetails.get("parentAccountNo")), StringUtil.GLOBAL_STRING_FORMAT));
						item.appendChild(newListcell(productDetails.get("parentAccountName")));
						//item.appendChild(newListcell(productDetails.get("name")));
						item.appendChild(newListcell(productDetails.get("productType")));
						item.appendChild(newListcell(productDetails.get("cardNo")));
						//item.appendChild(newListcell(productDetails.get("cardHolder")));
						item.appendChild(newListcell(productDetails.get("nameOnCard")));
						item.appendChild(newListcell(productDetails.get("status")));
						item.appendChild(newListcell(productDetails.get("divSappName")));
						item.appendChild(newListcell(productDetails.get("deptName")));
						//item.appendChild(newListcell(productDetails.get("issueDate")));
						item.appendChild(newListcell(productDetails.get("expiryDate")));
						item.appendChild(newListcell(productDetails.get("balanceExpiryDate")));
						item.appendChild(newListcell(productDetails.get("contact")));
						//logger.info("Name"+productDetails.get("*******************name"));
						productIdAndStatusMap.put(productid,productDetails.get("status"));
					}
					
					//To show the no record found message below the list
					if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
					resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
						//resultListBox.setPageSize(10);
					resultListBox.setPageSize(ConfigurableConstants.getSortPagingSize());
					
					if(dataMap.size()<1){
						if(resultListBox.getListfoot()!=null)
							resultListBox.removeChild(resultListBox.getListfoot());	
						resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
					}
						
					if(dataMap.size()>ConfigurableConstants.getMaxQueryResult())
						resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				}catch(Exception e){
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
						"Error", Messagebox.OK, Messagebox.ERROR);
						e.printStackTrace();
				}
				
				//start code //sort by suspend date
				
				//Listheader suspendHeader = (Listheader)this.getFellow("suspendlistheader");
				Listheader expiryHeader = (Listheader)this.getFellow("expirylistheader");
				//Listheader issueHeader = (Listheader)this.getFellow("issuelistheader");
			
				if(	productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_EXPIRY_DATE)){
					 expiryHeader.setSortDirection("descending");
					// issueHeader.setSortDirection("natural");
					// suspendHeader.setSortDirection("natural");
				}
				if(	productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_ISSUE_DATE)){
					// issueHeader.setSortDirection("descending");
					 expiryHeader.setSortDirection("natural");
					// suspendHeader.setSortDirection("natural");
				}
				
				// to display the total count
				if (resultListBox.getItemCount() > 0)
					resultListBox.appendChild(ComponentUtil.createTotalRecordListFoot(11, resultListBox.getItemCount()));
				
			}else{
				resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
			}
		}
	}
	
	//Product Status List
	public List<Listitem> getProductStatus(){
		logger.info("Product Status");
//		Listbox productStatusList = (Listbox)this.getFellow("productStatusList");
//		productStatusList.getItems().clear();
		ArrayList<Listitem> productStatus = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		for(String key : NonConfigurableConstants.PRODUCT_STATUS.keySet()){
			if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_NEW))
				productStatus.add(new Listitem(NonConfigurableConstants.PRODUCT_STATUS.get(key), key));
		}
		productStatus.add(new Listitem(NonConfigurableConstants.PRODUCT_STATUS_ALL,"ALL"));
		return productStatus;
	}
	
	// Salutation List
	public List<Listitem> getSalutations(){ 
		List<Listitem> salutationList = new ArrayList<Listitem>();
		salutationList.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterSalutation= ConfigurableConstants.getSalutations();
		for(String masterCode : masterSalutation.keySet()){
			salutationList.add(new Listitem(masterSalutation.get(masterCode), masterCode));
		}
		return salutationList;
	}
	
	// Sort By List
	public List<Listitem> getSortBy(){ 
		ArrayList<Listitem> sortBy = new ArrayList<Listitem>();
		sortBy.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.SORT_BY.keySet()){
			sortBy.add(new Listitem(NonConfigurableConstants.SORT_BY.get(key), key));
		}
		return sortBy;
	}
		
	public void setVisibleDivDepSubAppl(){
	
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		divOrSubApplLabel.setVisible(false);
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		departmentLabel.setVisible(false);
		Listbox divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
		divOrSubApplList.setVisible(false);
		Listbox departmentList=(Listbox)this.getFellow("departmentList");
		departmentList.setVisible(false);
	}
		
	public void setVisibleFalseDepartment(){
	
		//logger.info("This is setVisibleFalseDepartment()");
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		departmentLabel.setVisible(false);
		Listbox departmentList=(Listbox)this.getFellow("departmentList");
		departmentList.setVisible(false);
		//logger.info("This is setVisibleFalseDepartment()");
	}
	
	public void accountNoAndAccountNameSearch_Deprecated() throws InterruptedException{
		
		//hideViewtoListboxes();
		// getting the account template
		String selectedName = (String)((Combobox)this.getFellow("nameCbo")).getText();
		logger.info("This one is "+selectedName);
		String accNo = ((Textbox)this.getFellow("accNo")).getValue();
		//if(this.businessHelper.getAccountTypeBusiness().createAccountType(selectedCategory, typeName)){
		if(accNo==null) accNo="";
		if(selectedName==null) selectedName="";
		logger.info("Account No is "+accNo);
		logger.info("Account Name is "+selectedName);
		if(accNo.trim().length()>=3 || selectedName.trim().length()>=3){
			Combobox nameListCombo=(Combobox)this.getFellow("nameCbo");	
			logger.info("Account No is "+accNo);
			logger.info("Account Name is "+selectedName);
			nameListCombo.getItems().clear();
			AccountSearchUtil.populateAccountNameCbo(nameListCombo,accNo,selectedName);
			//if (nameListCombo.getItems()==null)setVisibleFields(false);
		}
//		else{ 
////			((Textbox)this.getFellow("accNo")).setFocus(true);
////			Messagebox.show("One of the search Criteria fields (Account No or Name) should be at least 3 numbers .."+accNo, "Issue Product", Messagebox.OK, Messagebox.INFORMATION);
////			((Textbox)this.getFellow("accNo")).setFocus(true);
//		}
	}
		
	public void reset(){
		
		
		Intbox accNo=(Intbox)this.getFellow("accNo");
		accNo.setValue(null);
		Listbox divOrSubApplList=(Listbox) this.getFellow("divOrSubApplList");
		divOrSubApplList.getItems().clear();
		Listbox departmentList=(Listbox) this.getFellow("departmentList");
		departmentList.getItems().clear();
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		divOrSubApplLabel.setVisible(false);
		divOrSubApplList.setVisible(false);
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		departmentLabel.setVisible(false);
		departmentList.setVisible(false);
		((Combobox)this.getFellow("nameCbo")).getItems().clear();
		((Combobox)this.getFellow("nameCbo")).setText(null);
		Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
		divAndDeptRow.setVisible(false);
		Datebox issueDateFrom=(Datebox)this.getFellow("issueDateFrom");
		issueDateFrom.setValue(null);
		Datebox issueDateTo=(Datebox)this.getFellow("issueDateTo");
		issueDateTo.setValue(null);
		Decimalbox cardNumberStart=(Decimalbox) this.getFellow("cardNumberStart");
		Decimalbox cardNumberEnd=(Decimalbox) this.getFellow("cardNumberEnd");
		cardNumberStart.setValue(null);
		cardNumberEnd.setValue(null);
		Datebox expiryDateFrom=(Datebox)this.getFellow("expiryDateFrom");
		expiryDateFrom.setValue(null);
		Datebox expiryDateTo=(Datebox)this.getFellow("expiryDateTo");
		expiryDateTo.setValue(null);
		Datebox suspendDateFrom=(Datebox)this.getFellow("suspendDateFrom");
		suspendDateFrom.setValue(null);
		Datebox suspendDateTo=(Datebox)this.getFellow("suspendDateTo");
		suspendDateTo.setValue(null);
		Listbox sortByList=(Listbox) this.getFellow("sortByList");
		sortByList.setSelectedIndex(0);
		setDefaultCardType();
		Listbox productStatusList=(Listbox) this.getFellow("productStatusList");
		productStatusList.setSelectedIndex(0);
		Listbox resultList=(Listbox) this.getFellow("resultList");
		resultList.getItems().clear();
		((CapsTextbox) this.getFellow("nameOnCard")).setValue(null);
		selectedIndex = null;
		
		ComponentUtil.reset(this.getFellow("balanceExpiryFrom"), this.getFellow("balanceExpiryTo"));
	}
	public void accountSearchByNo() throws InterruptedException{
		
		((Combobox)this.getFellow("nameCbo")).getItems().clear();
		((Combobox)this.getFellow("nameCbo")).setText(null);
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		divOrSubApplLabel.setVisible(false);
		Listbox divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
		divOrSubApplList.setVisible(false);
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		departmentLabel.setVisible(false);
		Listbox departmentList=(Listbox)this.getFellow("departmentList");
		departmentList.setVisible(false);
		String accNo = ((Intbox)this.getFellow("accNo")).getText();
		Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
		divAndDeptRow.setVisible(false);
		Combobox nameListCombo=(Combobox)this.getFellow("nameCbo");	
		nameListCombo.getItems().clear();
		divOrSubApplList.setSelectedItem(null);
		divOrSubApplList.getItems().clear();
		departmentList.setSelectedItem(null);
		departmentList.getItems().clear();
		displayProcessing();
		if (accNo==null)
			 accNo="";
		else{
			accNo=accNo.trim();
			accNo = accNo.replaceAll("[^0-9]", "");
			logger.info("+++++++++++++++++++++++"+accNo);
		}
		if(accNo.length()>0){

			try{
				//((Textbox)this.getFellow("accNo")).setValue(accNo.trim());
				//((Intbox)this.getFellow("accNo")).setValue(accNo.trim());
				AccountSearchUtil.populateAccountNameCbo(nameListCombo,accNo,"");
				
				if(nameListCombo.getItems().size()==1){
					nameListCombo.setSelectedIndex(0);
					//selectedAccount();
					selectedAccountSingleResult();
//					divAndDeptRow.setVisible(true);
//					divOrSubApplLabel.setVisible(true);
//					divOrSubApplList.setVisible(true);
				}
				
				else if(nameListCombo.getItems().size()<=0){
					//Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
					divAndDeptRow.setVisible(false);
					//Label divOrSubApplLabel=(Label) this.getFellow("divOrSubApplLabel");
					divOrSubApplLabel.setVisible(false);
					//Listbox divOrSubApplList=(Listbox) this.getFellow("divOrSubApplList");
					divOrSubApplList.setVisible(false);
					//Label departmentLabel=(Label) this.getFellow("departmentLabel");
					departmentLabel.setVisible(false);
					//Listbox departmentList=(Listbox) this.getFellow("departmentList");
					departmentList.setVisible(false);
				}
			
			}catch(Exception e){e.printStackTrace();}
		}
	}
		
	public void accountSearchByName(String name){

	 //	((Textbox)this.getFellow("accNo")).setValue(null);
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
//		divOrSubApplLabel.setVisible(false);
		Listbox divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
//		divOrSubApplList.setVisible(false);
//		
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
//		departmentLabel.setVisible(false);
		Listbox departmentList=(Listbox)this.getFellow("departmentList");
//		departmentList.setVisible(false);
		Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
		
		
		String selectedName = name;
			//(String)((Combobox)this.getFellow("nameCbo")).getText();
		//test
		divOrSubApplList.setSelectedItem(null);
		divOrSubApplList.getItems().clear();
		departmentList.setSelectedItem(null);
		departmentList.getItems().clear();
		logger.info("SELECTED NAME");
		
		if(selectedName.length()>=3){
			if(selectedName.indexOf('(')>0){
				int indexOfRoundBracket=selectedName.indexOf('(');
				selectedName=selectedName.substring(0,indexOfRoundBracket-1);
				logger.info("SELECTED NAME "+selectedName);
				//selectedName=selectedName.substring(0,selectedName.length()-5);
			}

			Intbox AccountNo=(Intbox)this.getFellow("accNo");
			String accNo="";
			if(AccountNo!=null)
				accNo=AccountNo.toString();
			
			try{
				Combobox nameListCombo=(Combobox)this.getFellow("nameCbo");
				//if(!accNo.equalsIgnoreCase("")){
					nameListCombo.getItems().clear();
					AccountSearchUtil.populateAccountNameCbo(nameListCombo,"",selectedName);
			//	}
				if(nameListCombo.getItems().size()<=0){
					divAndDeptRow.setVisible(false);
					divOrSubApplLabel.setVisible(false);
					divOrSubApplList.setVisible(false);
					departmentLabel.setVisible(false);
					departmentList.setVisible(false);
					nameListCombo.getItems().clear();
				}else if(nameListCombo.getItems().size()==1){
					divAndDeptRow.setVisible(true);
					divOrSubApplLabel.setVisible(true);
					divOrSubApplList.setVisible(true);
					nameListCombo.setSelectedIndex(0);
					selectedAccountSingleResult();
				}
			}catch(Exception e){e.printStackTrace();}
		}
		else{
			divAndDeptRow.setVisible(false);
			//((Intbox)this.getFellow("accNo")).setValue(null);
			divAndDeptRow.setVisible(false);
			divOrSubApplLabel.setVisible(false);
			divOrSubApplList.setVisible(false);
			departmentLabel.setVisible(false);
			departmentList.setVisible(false);
			Combobox nameListCombo=(Combobox)this.getFellow("nameCbo");	
			nameListCombo.getItems().clear();
		}
	}

	public void cancel() throws InterruptedException{
			this.back();
	}
		
	public void setVisibleFields(boolean flag){
			
		Combobox divOrSubApplCbo=(Combobox)this.getFellow("divOrSubApplCbo");
		Combobox departmentCbo=(Combobox)this.getFellow("departmentCbo");
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		divOrSubApplCbo.setVisible(flag);
		departmentCbo.setVisible(flag);
		divOrSubApplLabel.setVisible(flag);
		departmentLabel.setVisible(flag);
	}
		
	public void accountInfo(String accNo,String level){
		
		logger.info("Account Info");
		String category="";
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		Intbox accNoText=(Intbox)this.getFellow("accNo");
		//Get the  Name on card,contact details, and card type,
		Map<String,Map<String,String>> data=null;
		if(accNo!=null){
			//logger.info("Account No, not cust no "+accNo);
			data=this.businessHelper.getProductBusiness().getAccountInfo(accNo);  //getCorpOrApplInfo(accNo);
		}  
		//logger.info("Data"+data);
		LinkedHashMap<String,String> datalist=new LinkedHashMap<String,String>();
		if(data!=null){
			for(String accountNo: data.keySet()){
				Map<String,String> list=new LinkedHashMap<String,String>();
				list=data.get(accountNo);
				category=list.get("accountCategory");
				//set the account No to the text box
				logger.info("Customer ID"+list.get("custNo"));
				if(level.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))
					accNoText.setText( list.get("custNo"));
			}
			if(level.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
				if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					category="Division ";
				}else{
					category="Sub Applicant ";
				}
			}
			else if(level.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION+NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)){
				if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
					category="Division ";
				}else{
					category="Sub Applicant ";
				}
			}else{
				category="Division ";
			}
			divOrSubApplLabel.setVisible(true);
			divOrSubApplLabel.setValue(""+category);
		}
	}
		
	@SuppressWarnings("unchecked")
	public void getProductType(){
		
		Map<String,String> dataMap=this.businessHelper.getProductBusiness().getAllProductTypes();
		Listbox productTypeList = (Listbox)this.getFellow("productTypeList");
		//productTypeList.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		productTypeList.getItems().clear();
		ArrayList<Listitem> cardTypes = new ArrayList<Listitem>();
		if(dataMap!=null){
			productTypeList.setVisible(true);
			try{
				for(String produtTypeid : dataMap.keySet()){
					cardTypes.add(new Listitem(dataMap.get(produtTypeid), produtTypeid));
				}
				cardTypes.add(new Listitem("-","-"));
				productTypeList.getItems().addAll(cardTypes);
				setDefaultCardType();
				genenrateProductTypeEntryFields();
			}catch(Exception exp){exp.printStackTrace();}	
		}
		
	}
	
	private void setDefaultCardType(){
		
		Listbox productTypeList=(Listbox) this.getFellow("productTypeList");
		int defaultIndex = 0;
		
		List itemList = productTypeList.getItems();
		for(int i=0; i<itemList.size(); i++){
			Listitem item = (Listitem)itemList.get(i);
			if(NonConfigurableConstants.CORPORATE_CARD_ID.equals(item.getValue())){
				defaultIndex = i;
				break;
			}
		}
		productTypeList.setSelectedIndex(defaultIndex);
		
	}
	
	
	
	public void genenrateProductTypeEntryFields(){
		Listbox productTypeList = (Listbox)this.getFellow("productTypeList");
		String productTypeId=(String)productTypeList.getSelectedItem().getValue();
		PmtbProductType productType = (PmtbProductType)this.businessHelper.getGenericBusiness().get(PmtbProductType.class, productTypeId);
		if(this.checkUriAccess(Uri.RENEW_PRODUCT)) ((Button)this.getFellow("renewButton")).setDisabled(false);
	}
	
	public void hideViewtoListboxes(){
		
		Listbox divOrSubApplList=(Listbox) this.getFellow("divOrSubApplList");
		Listbox departmentList=(Listbox) this.getFellow("departmentList");
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		divOrSubApplList.setVisible(false);
		departmentList.setVisible(false);
		divOrSubApplLabel.setVisible(false);
		departmentLabel.setVisible(false);
		
	}

	public void selectedAccountSingleResult(){
		
		logger.info("selectedAccountSingleResult");
		//hideViewtoListboxes();	
		Combobox nameListCbo=(Combobox)this.getFellow("nameCbo");
		Listbox divOrSubApplList=(Listbox) this.getFellow("divOrSubApplList");
		String accNo="";
		try{
			if(nameListCbo.getSelectedItem()!= null)
				accNo=(String)nameListCbo.getSelectedItem().getValue();
			else
				accNo="";
			
			clickCorpAndApplAccountList();
			AccountSearchUtil.populateDivisionOrSubApplicantName(divOrSubApplList, accNo);
			accountInfo(accNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT));
			Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
			divAndDeptRow.setVisible(true);
			Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
			divOrSubApplLabel.setVisible(true);
			divOrSubApplList.setVisible(true);
			if(divOrSubApplList.getItemCount()<1){
				divAndDeptRow.setVisible(false);
			}
			else{
				divAndDeptRow.setVisible(true);
				divOrSubApplLabel.setVisible(true);
				divOrSubApplList.setVisible(true);
			}
			
		}catch(Exception e){
    		e.printStackTrace();
    		logger.info("Exception in selected Account()");
	    }
	}
	
	public void selectedAccount(){
		
		//hideViewtoListboxes();	
		Combobox nameListCbo=(Combobox)this.getFellow("nameCbo");
		Listbox divOrSubApplList=(Listbox) this.getFellow("divOrSubApplList");
		String accNo="";
		try{
			//Fix to bypass IE6 issue with double spacing
			if(nameListCbo.getChildren().size()==1)
				nameListCbo.setSelectedIndex(0);
			
			if(nameListCbo.getSelectedItem()!= null){
				selectedIndex = nameListCbo.getSelectedIndex();
				accNo=(String)nameListCbo.getSelectedItem().getValue();
			}
			else if(nameListCbo.getSelectedItem()==null && selectedIndex != null){
				nameListCbo.setSelectedIndex(selectedIndex);
				accNo=(String)nameListCbo.getSelectedItem().getValue();
			}
			else
				accNo="";
			
			if(nameListCbo.getItems().size()!=1){	
				logger.info("More than one Name List"+accNo);
				clickCorpAndApplAccountList();
				divOrSubApplList.setSelectedItem(null);
			    divOrSubApplList.getItems().clear();
			    if(!accNo.equalsIgnoreCase("")){
			        AccountSearchUtil.populateDivisionOrSubApplicantName(divOrSubApplList, accNo);
			    	accountInfo(accNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT));
				    if(divOrSubApplList.getItemCount()>0){
				    	logger.info("Count "+divOrSubApplList.getItemCount());
				    //	accountInfo(accNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT));
				    	Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
						divAndDeptRow.setVisible(true);
					    Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
					    divOrSubApplLabel.setVisible(true);
					    divOrSubApplList.setVisible(true);
					}else{
						Row divAndDeptRow=(Row) this.getFellow("divAndDeptRow");
						divAndDeptRow.setVisible(false);
				    	return;
					}
			    }
			}
		}catch(Exception e){
    		e.printStackTrace();
    		logger.info("Exception in selected Account()");
	    }
	}
	
	@SuppressWarnings("unchecked")
	public void selectedDepartment(){
		
		clickDepartementAccountList();
		Listbox divOrSubApplList=(Listbox)this.getFellow("departmentList");
		String accNo=(String) divOrSubApplList.getSelectedItem().getValue();
		try{
			if(accNo!=null){
				accountInfo(accNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT));
			}
			else{
				//if - then call division or sub application function to get the selected account no.
				selectedDivOrSubApplicant();
				//Messagebox.show("Please choose a valid Department.", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}
		
	public void selectedDivOrSubApplicant(){
			
		logger.info("Selected Div or Sub Applicant");
		setVisibleFalseDepartment();
		clickDivAndSubApplAccountList();
		
		//Combobox divOrSubApplCbo=(Combobox)this.getFellow("divOrSubApplCbo");
		Listbox divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
		String accNo=(String) divOrSubApplList.getSelectedItem().getValue();
		try{
			if(accNo!=null && accNo.trim().length()>0){
				accountInfo(accNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION+NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT));
				//Combobox departmentCbo=(Combobox)this.getFellow("departmentCbo");
				Listbox departmentList=(Listbox)this.getFellow("departmentList");
				Label departmentLabel=(Label)this.getFellow("departmentLabel");
				departmentList.setSelectedItem(null);
				departmentList.getItems().clear();
				
			    Label divOrSubApplicantLabel=(Label)this.getFellow("divOrSubApplLabel");
			    String divLabel=(String)divOrSubApplicantLabel.getValue();
			    //logger.info("Check Label"+(String)divOrSubApplicantLabel.getValue());
				if(divLabel.equalsIgnoreCase("Division ")){
					
				AccountSearchUtil.populateDepartmentName(departmentList,accNo);//populate department combo box
					if(departmentList.getItemCount()!=0){
						departmentLabel.setVisible(true);
						departmentList.setVisible(true);
					}
					else{
						departmentLabel.setVisible(false);
						departmentList.setVisible(false);
					}
				}
			}
			else{
				//if - then call the Account again
				selectedAccount();
				//Messagebox.show("Please choose a valid Division or Sub Applicant.", "Manage Product", Messagebox.OK, Messagebox.INFORMATION);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("Error in Selected Div or Department");
		}
	}
	
	public void checkAll(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkAll=(Checkbox)this.getFellow("checkAll");
		Checkbox checkBox=null;
			//(Checkbox) lastListItem.getFirstChild();
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		for(int i=0; i<resultListBox.getItemCount();i++){
			eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
			//lastListCell=(Listcell)eachRowListItem.getLastChild();
			lastListCell=(Listcell)eachRowListItem.getFirstChild();
			checkBox=(Checkbox)lastListCell.getFirstChild();
			if(!checkAll.isChecked())
				checkBox.setChecked(false);
			else
				checkBox.setChecked(true);
		}
	}
	
	public void getSpecifProduct(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		String productId = (String)resultListBox.getSelectedItem().getValue();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("productId", productId);
		map.put("productAccNo0", productSearchCriteria.getAccNo());
		try{
			this.forward(Uri.MANAGE_SPECIFIC_PRODUCT,map);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void retag(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		boolean retagStatusCheck=true;
		boolean terminateStatusCheck=true;
		//PmtbProduct productCheck=new PmtbProduct();
		String productStatus="";
		String productID="";
		String parentid="";
		String newparentid="";
		HashMap<String,String> map = new HashMap<String,String>();
		HashSet<BigDecimal> selectedProductIdSet = new HashSet<BigDecimal> ();
		boolean checkMultipleCorporate=false;
		try{
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				checkBox=(Checkbox)lastListCell.getFirstChild();
				if(checkBox.isChecked()){
					//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
					map.put("productId"+i,(String)eachRowListItem.getValue());
					selectedProductIdSet.add(new BigDecimal((String)eachRowListItem.getValue()));
					productID=(String)eachRowListItem.getValue();
					productStatus=productIdAndStatusMap.get(productID);
					//					productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal((String)eachRowListItem.getValue()));
					//					if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
					if(productStatus.equalsIgnoreCase("TERMINATED")){	
						terminateStatusCheck=false;
						Messagebox.show("Teminated cards cannot be retagged.", "Product Retag", Messagebox.OK, Messagebox.INFORMATION);
					}
					//logger.info("HEY TESING"+(String)eachRowListItem.getValue());
					parentid=this.businessHelper.getProductBusiness().getAccountParentIdbyProductNo((String)eachRowListItem.getValue());

					map.put("productAccNo"+i, parentid);
					
					
				}
			}
			checkMultipleCorporate=this.businessHelper.getProductBusiness().isMultipleCorporate(selectedProductIdSet);
			if(map.size()>0){
				if(terminateStatusCheck && retagStatusCheck){
					if(checkMultipleCorporate)
						this.forward(Uri.RETAG_PRODUCT,map);
					else
						Messagebox.show("Selected cards include different corporate accounts .", "Product Retag", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			else 
				Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){e.printStackTrace(); }
	}

	
	public void replace(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		HashMap<String,String> map = new HashMap<String,String>();
		boolean terminateStatusCheck=true;
		boolean checkProductType=true;
		//PmtbProduct productCheck=new PmtbProduct();
		String productStatus="";
		String productID="";
		try{
			for(int i=0; i<resultListBox.getItemCount();i++){
					eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
					//lastListCell=(Listcell)eachRowListItem.getLastChild();
					lastListCell=(Listcell)eachRowListItem.getFirstChild();
					String productId=(String)eachRowListItem.getValue();
					checkBox=(Checkbox)lastListCell.getFirstChild();
					if(checkBox.isChecked()){
						PmtbProductType productType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
						if(productType.getOneTimeUsage().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO)){
							//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
							map.put("productId"+i,productId);
							map.put("productAccNo"+i, productSearchCriteria.getAccNo());
							productID=(String)eachRowListItem.getValue();
							productStatus=productIdAndStatusMap.get(productID);
							//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal((String)eachRowListItem.getValue()));
							//if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
							if(productStatus.equalsIgnoreCase("TERMINATED")){	
								terminateStatusCheck=false;
								Messagebox.show("Teminated cards cannot be replaced.", "Card Renewal", Messagebox.OK, Messagebox.INFORMATION);
							}	
						}else{
							checkProductType=false;
							Messagebox.show("Selected Product(s) included invalid Product Type(s)", "Product Management(Replacement)", Messagebox.OK, Messagebox.INFORMATION);
							break;
							
					}
				}
			}
			if(checkProductType){
				if(map.size()>0){
					if(terminateStatusCheck){
						displayProcessing();
						this.forward(Uri.REPLACE_PRODUCT,map);
					}
				}
				else 
					Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
			}	
		}catch(Exception e){e.printStackTrace(); }
	}
		
	public void updateCreditLimit(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		HashMap<String,String> map = new HashMap<String,String>();
		boolean terminateStatusCheck=true;
		String productStatus="";
		String productID="";
		//PmtbProduct productCheck=new PmtbProduct();
		boolean checkProductType=true;
		try{
			//if(productType.getCreditLimit().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				String productId=(String)eachRowListItem.getValue();
				//logger.info("ProductID  : "+productId);
				checkBox=(Checkbox)lastListCell.getFirstChild();
				if(checkBox.isChecked()){
					PmtbProductType productType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
					if(productType.getCreditLimit().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES) && productType.getPrepaid().equals(NonConfigurableConstants.BOOLEAN_NO)){
						//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
						map.put("productId"+i,(String)eachRowListItem.getValue());
						map.put("productAccNo"+i, productSearchCriteria.getAccNo());
						productID=(String)eachRowListItem.getValue();
						productStatus=productIdAndStatusMap.get(productID);
						//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal((String)eachRowListItem.getValue()));
						//if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
						if(productStatus.equalsIgnoreCase("TERMINATED")){
							terminateStatusCheck=false;
							Messagebox.show("Teminated cards cannot Update Credit Limit.", "Card Renewal", Messagebox.OK, Messagebox.INFORMATION);
							break;
						}	
					}else {
							checkProductType=false;
							Messagebox.show("Selected Product(s) inlcuded invalid Product Type to update credit Limit.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
							break;
					}
				}
			}
			if(checkProductType){
				if(map.size()>0){
					if(terminateStatusCheck){
						displayProcessing();
						this.forward(Uri.UPDATE_CREDIT_LIMIT,map);
					}
				}
				else 
					Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
			}
		}catch(Exception e){e.printStackTrace(); }
	}
	
	
	public void renew(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		HashMap<String,String> map = new HashMap<String,String>();
		boolean terminateStatusCheck=true;
		//PmtbProduct productCheck=new PmtbProduct();
		String productStatus="";
		String productID="";
		boolean checkProductType=true;
		try{
			//if(productType.getValidityPeriod().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				checkBox=(Checkbox)lastListCell.getFirstChild();
				String productId=(String)eachRowListItem.getValue();
				if(checkBox.isChecked()){
					PmtbProductType productType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
					if(!productType.getValidityPeriod().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO) && productType.getPrepaid().equals(NonConfigurableConstants.BOOLEAN_NO)){
						map.put("validityPeriod", productType.getValidityPeriod());
						map.put("productId"+i,productId);
						map.put("productAccNo"+i, productSearchCriteria.getAccNo());
						productID=(String)eachRowListItem.getValue();
						productStatus=productIdAndStatusMap.get(productID);
						//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal((String)eachRowListItem.getValue()));
						//logger.info("productCheck "+productCheck.getCurrentStatus());
						//if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
						if(productStatus.equalsIgnoreCase("TERMINATED")){	
							terminateStatusCheck=false;
							Messagebox.show("Teminated cards cannot be renewed.", "Card Renewal", Messagebox.OK, Messagebox.INFORMATION);
						}				
					}else{ 
						checkProductType=false;
						Messagebox.show("Selected Product(s) included invalid Product Type to renew.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
						break;
					}
					
				}
			}
			if( checkProductType){
				if(map.size()>0){
					if(terminateStatusCheck){
						displayProcessing();
						this.forward(Uri.RENEW_PRODUCT,map);
					}
				}else 
					Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
			}
		}catch(Exception e){e.printStackTrace(); }
	}

	public void reactive(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		boolean suspendStatusCheck=true;
		String productStatus="";
		String productID="";
		//item.appendChild(newListcell(productDetails.get("status")));
		//PmtbProduct productCheck=new PmtbProduct();
		HashMap<String,String> map = new HashMap<String,String>();
		try{
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				checkBox=(Checkbox)lastListCell.getFirstChild();
				
				if(checkBox.isChecked()){
					//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
					map.put("productId"+i,(String)eachRowListItem.getValue());
					map.put("productAccNo"+i, productSearchCriteria.getAccNo());
					productID=(String)eachRowListItem.getValue();
					productStatus=productIdAndStatusMap.get(productID);
					//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class,new BigDecimal((String)eachRowListItem.getValue()));
					//if(!productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
					if(!productStatus.equalsIgnoreCase("SUSPENDED")){
						suspendStatusCheck=false;
						Messagebox.show("Only the suspended cards can be reactivated.", "Card Reactivation", Messagebox.OK, Messagebox.INFORMATION);
					}
				}
			}
			if(map.size()>0){
				//logger.info("Map Size"+map.size());
				
				if(suspendStatusCheck){
					displayProcessing();
					this.forward(Uri.REACTIVATE_PRODUCT,map);
				}
			}	
			else 
				Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){e.printStackTrace(); }
	
	}
		
	public void suspend(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		boolean activeStatusCheck=true;
		//PmtbProduct productCheck=new PmtbProduct();
		HashMap<String,String> map = new HashMap<String,String>();
		String productStatus="";
		String productID="";
		try{
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				checkBox=(Checkbox)lastListCell.getFirstChild();
				if(checkBox.isChecked()){
					//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
					map.put("productId"+i,(String)eachRowListItem.getValue());
					map.put("productAccNo"+i, productSearchCriteria.getAccNo());
					productID=(String)eachRowListItem.getValue();
					productStatus=productIdAndStatusMap.get(productID);
					//logger.info("CHECK THE STATUS OF PRODUCT ID : "+productID+" : "+productStatus);
					
					//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class,new BigDecimal((String)eachRowListItem.getValue()));
					if((!productStatus.equalsIgnoreCase("ACTIVE")) &&
							(!productStatus.equalsIgnoreCase("PARENT SUSPENDED"))){	
						//					if((!productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)) &&
						//						(!productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED))){
						activeStatusCheck=false;
						Messagebox.show("Only Active cards and Parent Suspend cards can be suspended.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
						break;
					}
				}
			}
			if(map.size()>0){
				if(activeStatusCheck){
					displayProcessing();
					this.forward(Uri.SUSPEND_PRODUCT,map);
					
				}
			}				
			else 
				Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){e.printStackTrace(); }
	}
		
	public void terminate(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		HashMap<String,String> map = new HashMap<String,String>();
		boolean terminateStatusCheck=true;
		//PmtbProduct productCheck=new PmtbProduct();
		String productStatus="";
		String productID="";
		try{
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				checkBox=(Checkbox)lastListCell.getFirstChild();
				if(checkBox!=null){
					try{
						if(checkBox.isChecked()){
							//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
							map.put("productId"+i,(String)eachRowListItem.getValue());
							map.put("productAccNo"+i, productSearchCriteria.getAccNo());
							productID=(String)eachRowListItem.getValue();
							productStatus=productIdAndStatusMap.get(productID);
						
//							productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class,new BigDecimal((String)eachRowListItem.getValue()));
//							if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
							if(productStatus.equalsIgnoreCase("TERMINATED")){
								terminateStatusCheck=false;
								Messagebox.show("Some Selected Cards are already Terminated.", "Card Reactivation", Messagebox.OK, Messagebox.INFORMATION);
								break;
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						logger.info("Excepition in retriving the product ID.");
					}
				}
			}
			if(map.size()>0){
				if(terminateStatusCheck){
					displayProcessing();
					this.forward(Uri.TERMINATE_PRODUCT,map);
				}
			}
			else 
				Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){e.printStackTrace(); }
	}
	
	@Override
	public void refresh() throws InterruptedException {
		search();
		Checkbox selectAll=(Checkbox)this.getFellow("checkAll");
		selectAll.setChecked(false);
	}

	public void afterCompose() {
		if(!this.checkUriAccess(Uri.RETAG_PRODUCT)) ((Button)this.getFellow("retagButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REPLACE_PRODUCT)) ((Button)this.getFellow("replaceButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.UPDATE_CREDIT_LIMIT)) ((Button)this.getFellow("updateCreditButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.RENEW_PRODUCT)) ((Button)this.getFellow("renewButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REACTIVATE_PRODUCT)) ((Button)this.getFellow("reactivateButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SUSPEND_PRODUCT)) ((Button)this.getFellow("suspendButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.TERMINATE_PRODUCT)) ((Button)this.getFellow("terminatedButton")).setDisabled(true);
	}
	
	public void assignCard(){
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell lastListCell=null;
		boolean terminateStatusCheck=true;
		//PmtbProduct productCheck=new PmtbProduct();
		String productStatus="";
		String productID="";
		String parentid="";
		HashMap<String,String> map = new HashMap<String,String>();
		HashSet<BigDecimal> selectedProductIdSet = new HashSet<BigDecimal> ();
		try{
			for(int i=0; i<resultListBox.getItemCount();i++){
				eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
				
				lastListCell=(Listcell)eachRowListItem.getFirstChild();
				checkBox=(Checkbox)lastListCell.getFirstChild();
				if(checkBox.isChecked()){
					//String productTypeId = (String)resultListBox.getSelectedItem().getValue();
					map.put("productId"+i,(String)eachRowListItem.getValue());
					selectedProductIdSet.add(new BigDecimal((String)eachRowListItem.getValue()));
					productID=(String)eachRowListItem.getValue();
					productStatus=productIdAndStatusMap.get(productID);
					//					productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal((String)eachRowListItem.getValue()));
					//					if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
					if(productStatus.equalsIgnoreCase("TERMINATED")){	
						terminateStatusCheck=false;
						Messagebox.show("Teminated cards cannot be assign.", "Product Asign Card", Messagebox.OK, Messagebox.INFORMATION);
					}
					//logger.info("HEY TESING"+(String)eachRowListItem.getValue());
					parentid=this.businessHelper.getProductBusiness().getAccountParentIdbyProductNo((String)eachRowListItem.getValue());

					map.put("productAccNo"+i, parentid);
					
					
				}
			}
			if(map.size()>0){
				if(terminateStatusCheck)
						this.forward(Uri.ASSIGN_CARD_PRODUCT,map);
			}
			else 
				Messagebox.show("No product is selected.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){e.printStackTrace(); }
	}
}


