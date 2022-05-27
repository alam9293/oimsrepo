	package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductRetag;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.AccountSearchUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
	public class RetagProductWindow extends CommonWindow {
	private static final long serialVersionUID = -5566490003238378622L;
	private static Logger logger = Logger.getLogger(RetagProductWindow.class);
	//private PmtbProduct product=new PmtbProduct();
	private String accountNo="";
	private String previousAccountNo=""; //Account No before Retag
	private Set<BigDecimal> productIdSet = new HashSet<BigDecimal>();
	private AmtbAccount account= new AmtbAccount();
	@SuppressWarnings("unchecked")
	public RetagProductWindow(){
			
		HashMap<String,String> params = (HashMap)Executions.getCurrent().getArg();
	   	for(String productTypeid : params.keySet()){
			if(productTypeid.indexOf("productId")>=0)
				productIdSet.add(new BigDecimal(params.get(productTypeid)));
			//logger.info("Size of the map :"+params.size());	logger.info("Key 	:"+productTypeid);logger.info("Value 	:"+params.get(productTypeid));	
			if(productTypeid.indexOf("productAccNo")>=0)
				accountNo=params.get(productTypeid);
			previousAccountNo=accountNo;
		}

		if(accountNo!=null && !accountNo.equals(""))
			account=(AmtbAccount)this.businessHelper.getProductBusiness().getAccount(accountNo);
		//logger.info("Customer No is "+account.getCustNo());
	}

	public void populateData(){
		
		((Label)this.getFellow("accNoLabel")).setValue((String)account.getCustNo());
		((Label)this.getFellow("nameLabel")).setValue(account.getAccountName());
		int count=0;
//		int countItem=1; //To retrive the product 1000 at a time. Hibernate select something in(?) cannot provide  more than 1000
//		int maxCount=productIdSet.size();
//		int countId=1;
		Map<String, Map<String, String>> dataMap=new HashMap<String, Map<String, String>>();
//		Map<String, Map<String, String>> tempdataMap=new HashMap<String, Map<String, String>>();;
//		Set<BigDecimal> productIdSetTemp = new HashSet<BigDecimal> ();
//		
//		if(productIdSet.size()>1000){
//			Set<BigDecimal> productIdSetAll = new HashSet<BigDecimal> ();
//			Iterator<BigDecimal> it = productIdSet.iterator();
//			while (it.hasNext()) {
//				productIdSetAll.add((BigDecimal)it.next());
//			}
//			while(countItem*1000<=maxCount){
//							
//				it = productIdSetAll.iterator();
//				while (it.hasNext()) {
//			        // Get element
//					productIdSetTemp.add((BigDecimal)(it.next()));
//			    	it.remove();
//					if(countId==1000) break;
//			    	countId++;
//			    }
//				countId=1;
//				countItem++;
//				if(productIdSetTemp.size()>0){
//					tempdataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSetTemp);
//					dataMap.putAll(tempdataMap);
//				}
//				productIdSetTemp = new HashSet<BigDecimal> ();
//			}	
//		}else
			dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		
		//Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		selectedAccount();//selected Account
		resultListBox.getItems().clear();
		if(dataMap!=null){
			try{
			   //int count=0;
				for(String produtid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();
					item.setValue(produtid);
					Map<String,String> productDetails=dataMap.get(produtid);
					//item.appendChild(new Listcell(String.valueOf(count)));
					//item.appendChild(new Listcell(productDetails.get("name")));
					item.appendChild(newListcell(productDetails.get("productType")));
					item.appendChild(newListcell(productDetails.get("cardNo")));
					item.appendChild(newListcell(productDetails.get("status")));
					item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("issueDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(productDetails.get("expiryDate")));
					item.appendChild(newListcell(DateUtil.convertStrToDate(productDetails.get("suspendDate"), DateUtil.GLOBAL_DATE_FORMAT), DateUtil.GLOBAL_DATE_FORMAT));
					resultListBox.appendChild(item);
				}
				if(resultListBox.getListfoot()!=null && count>0)
						resultListBox.removeChild(resultListBox.getListfoot());	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			}catch(Exception e){e.printStackTrace();}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	public void selectedAccount(){
		
		Listbox divOrSubApplList=(Listbox) this.getFellow("divOrSubApplList");
		//divOrSubApplList.setSelectedItem(null);
	    divOrSubApplList.getItems().clear();
	    if(accountNo!=null){
		    AccountSearchUtil.populateDivisionOrSubApplicantNameRetag(divOrSubApplList, accountNo, "new");
		    if(divOrSubApplList.getItemCount()!=0){
			    Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
			   divOrSubApplLabel.setVisible(true);
		    }
			accountInfo(accountNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT));
	    }
	}
	
	public void accountInfo(String accNo,String level){
		
		String category="";
		Label divOrSubApplLabel=(Label)this.getFellow("divOrSubApplLabel");
		
		//Get the  Name on card,contact details, and card type,
		Map<String,Map<String,String>> data=null;
		if(accNo!=null){
			data=this.businessHelper.getProductBusiness().getAccountInfo(accNo);  //getCorpOrApplInfo(accNo);
		}    
		LinkedHashMap<String,String> datalist=new LinkedHashMap<String,String>();
		if(data!=null){
			for(String accountNo: data.keySet()){
				Map<String,String> list=new LinkedHashMap<String,String>();
				list=data.get(accountNo);
				category=list.get("accountCategory");
				//set the account No to the text box
				logger.info("category"+category);
			
			}
			if(level.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE+NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
				if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					category="Division ";
				}
				else{
					category="Sub Applicant ";
				}
			}
			else if(level.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION+NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)){
				if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
					category="Division ";
				}
				else{
					category="Sub Applicant ";
				}
			}
			else{
				category="Division ";
			}
			divOrSubApplLabel.setVisible(true);
			divOrSubApplLabel.setValue("*"+category);
	
		}
	}
	
	public void setVisibleFalseDepartment(){
		
		logger.info("This is setVisibleFalseDepartment()");
		Label departmentLabel=(Label)this.getFellow("departmentLabel");
		departmentLabel.setVisible(false);
		Listbox departmentList=(Listbox)this.getFellow("departmentList");
		departmentList.setVisible(false);
		logger.info("This is setVisibleFalseDepartment()");
	}
	
	public void selectedDivOrSubApplicant(){
		
		setVisibleFalseDepartment();
		//Combobox divOrSubApplCbo=(Combobox)this.getFellow("divOrSubApplCbo");
		Listbox divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
		accountNo=(String) divOrSubApplList.getSelectedItem().getValue();
			if(accountNo!=null){
			    accountInfo(accountNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION+NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT));
				//Combobox departmentCbo=(Combobox)this.getFellow("departmentCbo");
				Listbox departmentList=(Listbox)this.getFellow("departmentList");
				Label departmentLabel=(Label)this.getFellow("departmentLabel");
				departmentList.setSelectedItem(null);
				departmentList.getItems().clear();
				AccountSearchUtil.populateDepartmentName(departmentList,accountNo);//populate department combo box
				if(departmentList.getItemCount()!=0){
					departmentLabel.setVisible(true);
					departmentList.setVisible(true);
				}
				else{
					departmentLabel.setVisible(false);
					departmentList.setVisible(false);
				}
			}
			else 
				accountNo=previousAccountNo;
	}
	
	public void selectedDepartment(){
		
		
		Listbox divOrSubApplList=(Listbox)this.getFellow("departmentList");
		accountNo=(String) divOrSubApplList.getSelectedItem().getValue();
		if(accountNo!=null)
			accountInfo(accountNo,(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT));
		else{
			divOrSubApplList=(Listbox)this.getFellow("divOrSubApplList");
			accountNo=(String) divOrSubApplList.getSelectedItem().getValue();
		}
	}
	
	public void retag(){
		
		Textbox remarks=(Textbox) this.getFellow("remarks");
		String retagRemarks=(String)remarks.getValue();
		boolean isToday=false;
		boolean check=false;
		try{
			if(retagRemarks==null || retagRemarks.trim().length()<1){
				Messagebox.show("Remarks Field Cannot be blank.", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				//logger.info("Retag Account No"+accountNo);
				Datebox retagDatebox=(Datebox) this.getFellow("effectiveDate");
				Date retagDate=(Date)retagDatebox.getValue();
				Date currentDate=new Date();
				isToday=DateUtil.isToday(retagDate);
				//if(!isToday){
				if(retagDate==null){
					Messagebox.show("Effective Date cannot be blank.", "Retag Product", Messagebox.OK, Messagebox.ERROR);
				}
				else if(retagDate.before(currentDate) && isToday==false){
					Messagebox.show("Effective Date should not be earlier than Today.", "Retag Product", Messagebox.OK, Messagebox.ERROR);
				}
				else if(retagDate.after(currentDate) || isToday==true){
					//logger.info("DATE IN UI IS"+retagDate);
					java.sql.Date effectiveDate=new java.sql.Date (retagDate.getTime());
					//logger.info("Effective Date:" +effectiveDate);
					//check=this.businessHelper.getProductBusiness().saveRetag(isToday,new Timestamp(effectiveDate.getTime()),(HashSet<BigDecimal>)productIdSet,retagRemarks,accountNo,previousAccountNo);
					AmtbAccount newAccount=(AmtbAccount)businessHelper.getProductBusiness().getAccount(accountNo);
					//getAccountBusiness().getAccountById( new Integer(accountNo));
					
					TreeSet<AmtbAcctStatus> sortedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
	    				   public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
	    						return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
	    					}
	    			});
	    			sortedStatus.addAll(this.businessHelper.getAccountBusiness().getStatuses(newAccount.getAccountNo()));
	    			AmtbAcctStatus acctCurrentStatus = this.businessHelper.getAccountBusiness().getCurrentStatus(sortedStatus);
					
	    			if(acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED) ||
	    				acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED) ||
	    				acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED) || 
	    				acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
	    			
	    				Messagebox.show("Cannot be retagged due to retag to account status is terminated/suspended/parent suspended." , "Retag Product", Messagebox.OK, Messagebox.ERROR);
	    				return;
	    			}
	    			
					Iterator<BigDecimal> It = productIdSet.iterator();
					int count=0;
					boolean isAuthorizedAccount=false;
					boolean oneOrMoreAuthorizedAccount = false;
					boolean checkAccount=false;
					//List<PmtbProductRetag> retagProducts=new ArrayList<PmtbProductRetag> ();
					PmtbProductRetag retagProduct=new PmtbProductRetag(); 
					int confirmMessage=Messagebox.show("Are you sure to retag?","Create Product Retag Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
					if(confirmMessage==1){
						//Processing Indicator
						displayProcessing();
						//logger.info("newaccount");
					    try{
					       while (It.hasNext()) {
					    	   PmtbProduct product=(PmtbProduct)this.businessHelper.getProductBusiness().getProductById(It.next());
					    	   AmtbAccount oldAccount=this.businessHelper.getProductBusiness().getAccount(product.getAmtbAccount().getAccountNo().toString());
						       // logger.info("New Account No is"+ newAccount.getAccountNo().toString());
						       //logger.info("Old Account No is"+ oldAccount.getAccountNo().toString());
						       //update the future retagged.
						       PmtbProductRetag futureRetag=(PmtbProductRetag)this.businessHelper.getProductBusiness().getFutureRetagSchedule(product);
						       //
						       if(newAccount.getAccountNo().toString().equalsIgnoreCase(oldAccount.getAccountNo().toString())){
						    	   checkAccount=false;
						       }else{
						           	checkAccount=true;
						        }
						       retagProduct.setAmtbAccountByCurrentAccountNo(oldAccount);
						       retagProduct.setAmtbAccountByNewAccountNo(newAccount);
						       retagProduct.setPmtbProduct(product);
						       retagProduct.setEffectiveDt(new java.sql.Timestamp(effectiveDate.getTime()));
						       retagProduct.setRetagRemarks(retagRemarks);
						       // newAccountNo,product
						       isAuthorizedAccount=this.businessHelper.getProductBusiness().checkAuthorizedAccount(newAccount.getAccountNo(),product.getProductNo());
							      
						       if(checkAccount){
							      	//logger.info("*********HERE IS THE ADDITIONAL FUNCTION FOR AUTHORIZED ACCOUNT "+isAuthorizedAccount);
							       	if(isAuthorizedAccount){
							  	       	if(futureRetag!=null){
								        	//logger.info("There is future retag");
								        	futureRetag.setAmtbAccountByCurrentAccountNo(oldAccount);
								        	futureRetag.setAmtbAccountByNewAccountNo(newAccount);
								        	futureRetag.setPmtbProduct(product);
								        	futureRetag.setEffectiveDt(new java.sql.Timestamp(effectiveDate.getTime()));
								        	futureRetag.setRetagRemarks(retagRemarks);
								        	try{
									       		this.businessHelper.getGenericBusiness().update(futureRetag,getUserLoginIdAndDomain());
									        	//this.save(retagProduct);
									        }
								        	catch(Exception e){
									        	e.printStackTrace();
									        	Messagebox.show("This cardno : "+product.getCardNo()+" cannot be retagged, ", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
									        }
								        }else{
								        	logger.info("There is no future retag yet");
								        	try{
									       		this.businessHelper.getGenericBusiness().save(retagProduct,getUserLoginIdAndDomain());
									       	//this.save(retagProduct);
									        }catch(Exception e){
									        	e.printStackTrace();
									        	Messagebox.show("This cardno : "+product.getCardNo()+" cannot be retagged. ", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
									        }
								        }
							        }else{
							         	Messagebox.show("Cardno : "+product.getCardNo()+" cannot be retagged, ", "Unauthorized Account", Messagebox.OK, Messagebox.INFORMATION);
									}
						       }else{
						    	     Messagebox.show("Cardno : "+product.getCardNo()+" cannot be retagged. ", "Same Account", Messagebox.OK, Messagebox.INFORMATION);
						       }
						       
						       logger.info("Current Account No is"+ product.getAmtbAccount().getAccountNo().toString());
						       logger.info("Old Account No is"+ product.getAmtbAccount().getAccountNo().toString());
						       logger.info("New Account No is"+ newAccount.getAccountNo().toString());
						      	
						       //Seng Tat (new fix 08 Aug 2011): During retag need to check card status needs to be updated according to parent status.
			    			   //E.g. Originally the account is suspended therefore card becomes parent suspended.
			    			   //When a retagged to new active account, the card status needs to be updated to active.
			    			   
			    			   /*if(acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED) || 
			    					   acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
			    				   if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
			    					   Timestamp timeForNewStatus = retagProduct.getEffectiveDt();
			    					   if(isToday){
			    						   product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
			    						   timeForNewStatus = DateUtil.getCurrentTimestamp();
			    					   }
			    					   
			    					   PmtbProductStatus newStatus = new PmtbProductStatus();
			    					   newStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
			    					   newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
			    					   newStatus.setStatusDt(timeForNewStatus);
			    					   newStatus.setStatusRemarks(retagProduct.getRetagRemarks());
			    					   newStatus.setPmtbProduct(product);
			    					   newStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable("SR", "PRT"));
			    					   this.businessHelper.getGenericBusiness().save(newStatus, getUserLoginIdAndDomain());
			    				   }
			    			   }*/
			    			   if(acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
			    				   if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
			    					   Timestamp timeForNewStatus = retagProduct.getEffectiveDt();
			    					   if(isToday){
			    						   product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
			    						   timeForNewStatus = DateUtil.getCurrentTimestamp();
			    					   }
			    					   
			    					   PmtbProductStatus newStatus = new PmtbProductStatus();
			    					   newStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
			    					   newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
			    					   newStatus.setStatusDt(timeForNewStatus);
			    					   newStatus.setStatusRemarks(retagProduct.getRetagRemarks());
			    					   newStatus.setPmtbProduct(product);
			    					   newStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable("SR", "PRT"));
			    					   this.businessHelper.getGenericBusiness().save(newStatus, getUserLoginIdAndDomain());
			    				   }
			    			   }
						       
						       //If today then, update the account no immediately.Otherwise batch job will update
						       if(isToday)
						       {
						    	   try
						    	   {
						    		   if(isAuthorizedAccount){
						    			   product.setAmtbAccount(newAccount);
						    			   this.businessHelper.getGenericBusiness().update(product, getUserLoginIdAndDomain());
						    			   if(product.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE))
						    				   this.businessHelper.getProductBusiness().updateProductAPIActive(product, getUserLoginIdAndDomain());
						    			   else
						    			   {
						    				 // Need to retrieve the product current status and remarks
						    				   PmtbProductStatus latestProductStatus = this.businessHelper.getProductBusiness().getLatestProductStatus(product.getCardNo(), DateUtil.getCurrentTimestamp());
									
						    				 // inactive product, need to update AS
						    				   this.businessHelper.getProductBusiness().updateProductAPI(product, getUserLoginIdAndDomain(), latestProductStatus.getMstbMasterTable());
						    			   }
						    		   }
						    	   }catch(Exception e){
							        		e.printStackTrace();
							        		Messagebox.show("This cardno : "+product.getCardNo()+" cannot be retagged, ", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
							        }
						       }
						       count++;
						       
						       if(isAuthorizedAccount)
						    	   oneOrMoreAuthorizedAccount = isAuthorizedAccount;
					       }
					       if(oneOrMoreAuthorizedAccount)
					    	   Messagebox.show("Product(s) Retagged", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
					       this.back();
					    }catch(Exception e){	
					    	try{
					    		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					        		"Error", Messagebox.OK, Messagebox.ERROR);
					    	}catch(Exception exp){exp.printStackTrace();}
							e.printStackTrace();}
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}
		

}


