	package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
	
	@SuppressWarnings("unchecked")
	public class ManageSpecificProductWindow extends CommonWindow implements AfterCompose {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RenewProductWindow.class);
	//private PmtbProduct product=new PmtbProduct();
	private String accountNo="";
	private Set<BigDecimal> productIdSet = new HashSet ();
	@SuppressWarnings("unused")
	private AmtbAccount account= new AmtbAccount();
	String productId="";
	
	public ManageSpecificProductWindow(){
		
		HashMap<String,String> params = (HashMap)Executions.getCurrent().getArg();
		for(String productNo : params.keySet()){
	
			if(productNo.indexOf("productId")>=0){
				productIdSet.add(new BigDecimal(params.get(productNo)));
				productId=params.get(productNo);
				logger.info("This is product ID ***************************"+productId);
			}
//			if(productNo.indexOf("productAccNo")>=0)
//				accountNo=params.get(productNo);
		}
		//account=(AmtbAccount)this.businessHelper.getProductBusiness().getAccount(accountNo);
	}
		
	
	public void populateData(){
		
		//PmtbProductType selectedProductType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
		Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		Label accountNo=(Label) this.getFellow("accountNo");
		Label name=(Label) this.getFellow("name");
		Label status=(Label) this.getFellow("status");
		Label productType=(Label) this.getFellow("productType");
		Label cardStatus=(Label) this.getFellow("cardStatus");
		Label cardNo=(Label) this.getFellow("cardNo");
		Label issueDate=(Label) this.getFellow("issueDate");
		Label expiryDateLabel=(Label) this.getFellow("expiryDateLabel");
		Label expiryDateValueLabel=(Label) this.getFellow("expiryDateValueLabel");
		Label nameOnCard=(Label) this.getFellow("nameOnCard");
		Label creditBalance=(Label) this.getFellow("creditBalance");
		Label tempCreditLimit=(Label) this.getFellow("tempCreditLimit");
		Label creditLimt=(Label) this.getFellow("creditLimt");
		Label suspendDate=(Label) this.getFellow("suspendDate");
		Label balanceExpiryDate=(Label) this.getFellow("balanceExpiryDate");
		Label lastUpdatedBy=(Label) this.getFellow("lastUpdatedBy");
		Label lastUpdatedDate=(Label) this.getFellow("lastUpdatedDate");
		Label lastUpdatedTime=(Label) this.getFellow("lastUpdatedTime");
		Label subscriptionFees=(Label) this.getFellow("subscriptionFees");
		Label smsExpiryFlag=(Label) this.getFellow("smsExpiryFlag");
		Label smsTopUpFlag=(Label) this.getFellow("smsTopUpFlag");
		
		Label divOrSubAppId=(Label) this.getFellow("divOrSubAppId");
		Label divOrSubAppName=(Label) this.getFellow("divOrSubAppName");
		Label departmentId=(Label) this.getFellow("departmentId");
		Label departmentName=(Label) this.getFellow("departmentName");
		Row DivSubAppDeptId=(Row)this.getFellow("DivSubAppDeptId");
		
		Label cardHolderName=(Label) this.getFellow("cardHolderName");
		Label position=(Label) this.getFellow("position");
		Label telephone=(Label) this.getFellow("telephone");
		Label email=(Label) this.getFellow("email");
		Label fixedValue=(Label) this.getFellow("fixedValue");
		Row corpFirstRow=(Row)this.getFellow("corpFirstRow");
		Row corpSecondRow=(Row)this.getFellow("corpSecondRow");
		Row corpThirdRow=(Row)this.getFellow("corpThirdRow");
		Label employeeId=(Label) this.getFellow("employeeId");
		Row fixedValueRow=(Row)this.getFellow("fixedValueRow");
		Button suspend=(Button) this.getFellow("Suspend");
		Button reactivate=(Button) this.getFellow("Reactivate");
		Button terminate=(Button) this.getFellow("Terminate");
		
		Row offlineCountRow = (Row)this.getFellow("offlineCountRow");
		Row offlineAmountAccumulativeRow = (Row)this.getFellow("offlineAmountAccumulativeRow");
		Row offlineAmountPerTxnRow = (Row)this.getFellow("offlineAmountPerTxnRow");
		Row embossNameOnCardRow = (Row)this.getFellow("embossNameOnCardRow");
		Label offlineCount = (Label)this.getFellow("offlineCount");
		Label offlineAmountAccumulative = (Label)this.getFellow("offlineAmountAccumulative");
		Label offlineAmountPerTxn = (Label)this.getFellow("offlineAmountPerTxn");
		Label embossNameOnCard = (Label)this.getFellow("embossNameOnCard");
		
		Row tempCreditLimitRow = (Row)this.getFellow("tempCreditLimitRow");
		
		if(dataMap!=null){
			try{
				int count=0;
				for(String productid : dataMap.keySet()){
					count++;
					Listitem item = new Listitem();
					item.setValue(productid);
					Map<String,String> productDetails=dataMap.get(productid);
					accountNo.setValue(productDetails.get("parentAccountNo"));
					name.setValue(productDetails.get("accountName"));
					status.setValue(productDetails.get("accountStatus"));
					productType.setValue(productDetails.get("productType"));
					cardStatus.setValue(productDetails.get("status"));
//					if(cardStatus.getValue().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS.get(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED))){
//						suspend.setDisabled(true);
//					}else{
//						reactivate.setDisabled(true);
//					}
					if(cardStatus.getValue().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS.get(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED))){
						logger.info("CARD HAD BEEN TERMINATE");
						suspend.setDisabled(true);
						reactivate.setDisabled(true);
						terminate.setDisabled(true);
					}
					cardNo.setValue(productDetails.get("cardNo"));
					issueDate.setValue(productDetails.get("issueDate"));
					expiryDateValueLabel.setValue(productDetails.get("expiryDate"));
					nameOnCard.setValue(productDetails.get("name"));
					creditBalance.setValue(productDetails.get("creditBalance"));
					tempCreditLimit.setValue(productDetails.get("tempCreditLimit"));
					creditLimt.setValue(productDetails.get("creditLimit"));
					suspendDate.setValue(productDetails.get("suspendDate"));
					balanceExpiryDate.setValue(productDetails.get("balanceExpiryDate"));
					if(productDetails.get("lastUpdatedBy")!=null && productDetails.get("lastUpdatedBy").length()!=0){
						lastUpdatedBy.setValue(productDetails.get("lastUpdatedBy"));
					}
					if(productDetails.get("lastUpdatedDate")!=null && productDetails.get("lastUpdatedDate").length()!=0){
						lastUpdatedDate.setValue(productDetails.get("lastUpdatedDate"));
					}
					if(productDetails.get("lastUpdatedTime")!=null && productDetails.get("lastUpdatedTime").length()!=0){
						lastUpdatedTime.setValue(productDetails.get("lastUpdatedTime"));
					}
					if(productDetails.get("subscriptionFees")!=null){
						if(productDetails.get("subscriptionFees").equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES))
							subscriptionFees.setValue("YES");
						else
							subscriptionFees.setValue("NO");
					}else{
						subscriptionFees.setValue("-");
					}
					if(productDetails.get("smsExpiryFlag")!=null){
						
						smsExpiryFlag.setValue(NonConfigurableConstants.SMS_FLAG.get(productDetails.get("smsExpiryFlag")));
					}else{
						smsExpiryFlag.setValue("-");
					}
					if(productDetails.get("smsTopUpFlag")!=null){
						smsTopUpFlag.setValue(NonConfigurableConstants.SMS_FLAG.get(productDetails.get("smsTopUpFlag")));
					}else{
						smsTopUpFlag.setValue("-");
					}
					String isIndividualCard=productDetails.get("isIndividualCard");
					if(isIndividualCard.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
						corpFirstRow.setVisible(true);
						corpSecondRow.setVisible(true);
						cardHolderName.setValue(productDetails.get("cardHolder"));
						position.setValue(productDetails.get("position"));
						telephone.setValue(productDetails.get("telephone"));
						email.setValue(productDetails.get("email"));
						
						corpThirdRow.setVisible(true);
						employeeId.setValue(productDetails.get("employeeId"));
					}
					//Added to view fixed value
					if(productDetails.get("fixedValue")!=null){
						logger.info("Fixed Value"+productDetails.get("fixedValue"));
						fixedValueRow.setVisible(true);
						fixedValue.setValue(productDetails.get("fixedValue"));
					}
					
					
					String category=productDetails.get("accountCategory");
					if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
						logger.info("category "+category);
						DivSubAppDeptId.setVisible(true);
						divOrSubAppId.setVisible(true);
						divOrSubAppId.setValue("Division Name");
						divOrSubAppName.setVisible(true);
						divOrSubAppName.setValue(productDetails.get("divSappName"));
						logger.info("Division"+productDetails.get("divSappName"));
						departmentId.setVisible(false);
						departmentName.setVisible(false);
					}
					else if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)){
						logger.info("category "+category);
						DivSubAppDeptId.setVisible(true);
						divOrSubAppId.setVisible(true);
						divOrSubAppId.setValue("Sub Applicant Name");
						divOrSubAppName.setVisible(true);
						divOrSubAppName.setValue(productDetails.get("divSappName"));
						logger.info("Sub Applicant"+productDetails.get("divSappName"));
						departmentId.setVisible(false);
						departmentName.setVisible(false);
						
					}
					else if (category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
						logger.info("category "+category);
						DivSubAppDeptId.setVisible(true);
						divOrSubAppId.setVisible(true);
						divOrSubAppId.setValue("Division Name");
						divOrSubAppName.setVisible(true);
						divOrSubAppName.setValue(productDetails.get("divSappName"));
						logger.info("divSappName"+productDetails.get("divSappName"));
						logger.info("Department"+productDetails.get("deptName"));
						departmentId.setVisible(true);
						departmentName.setVisible(true);
						departmentName.setValue(productDetails.get("deptName"));
						
					}
					
					if(productDetails.get("validityPeriod").equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_MMYY)){
						expiryDateLabel.setVisible(true);
						expiryDateValueLabel.setVisible(true);
					}
					else if(productDetails.get("validityPeriod").equals(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_BY_DDMMYYYY_HHMM)){
						((Row)this.getFellow("expiryDateTimeRow")).setVisible(true);
						((Label)this.getFellow("expiryDateUpToDayLabel")).setValue(productDetails.get("expiryDateUpToDay"));
						((Label)this.getFellow("expiryTimeLabel")).setValue(productDetails.get("expiryTime"));
					}
					
					
					if(productDetails.get("prepaid").equals(NonConfigurableConstants.BOOLEAN_YES)){
						((Row)this.getFellow("creditLimitRow")).setVisible(false);
						tempCreditLimitRow.setVisible(false);
					}
					
					if(productDetails.get("prepaid").equals(NonConfigurableConstants.BOOLEAN_YES)){
						((Row)this.getFellow("prepaidRow")).setVisible(true);
						((Label)this.getFellow("cardValueLabel")).setValue(productDetails.get("cardValue"));
						((Label)this.getFellow("cashPlusLabel")).setValue(productDetails.get("cashPlus"));
					}
					
					if(productDetails.get("contactlessFlag").equals(NonConfigurableConstants.BOOLEAN_YES)){
						offlineCountRow.setVisible(true);
						offlineAmountAccumulativeRow.setVisible(true);
						offlineAmountPerTxnRow.setVisible(true);
						embossNameOnCardRow.setVisible(true);
						offlineCount.setValue(productDetails.get("offlineCount"));
						offlineAmountAccumulative.setValue(productDetails.get("offlineAmount"));
						offlineAmountPerTxn.setValue(productDetails.get("offlineTxnAmount"));
						if(productDetails.get("embossNameOnCardFlag").equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES))
							embossNameOnCard.setValue("YES");
						else
							embossNameOnCard.setValue("NO");
					}
					else {
						offlineCountRow.setVisible(false);
						offlineAmountAccumulativeRow.setVisible(false);
						offlineAmountPerTxnRow.setVisible(false);
						embossNameOnCardRow.setVisible(false);
					}
				
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void ViewHistory(){
		
		//PmtbProduct productCheck=new PmtbProduct();
		//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		try{
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("productId", productId);
			this.forward(Uri.PRODUCT_HISTORY,map);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void ViewCreditHistory(){
		
		//PmtbProduct productCheck=new PmtbProduct();
		//productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		try{
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("productId", productId);
			this.forward(Uri.PRODUCT_HISTORY,map);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void StatusHistory(){
		
		try{
			logger.info("*****THIS IS THE PLACE TO CHECK PRODUCT ID"+productId);
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("productId", productId);
			final Window win = (Window) Executions.createComponents(Uri.PRODUCT_STATUS_HISTORY, null, map);
			win.setMaximizable(true);
			win.setClosable(true);
			win.doModal();
			//this.forward(Uri.PRODUCT_HISTORY,map);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void Retag(){
		
		boolean terminateStatusCheck=true;
		PmtbProduct productCheck=new PmtbProduct();
		productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		try{
			if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
				terminateStatusCheck=false;
					Messagebox.show("Teminated card cannot be retagged", "Card Retag", Messagebox.OK, Messagebox.INFORMATION);
			}
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("productId", productId);
			//map.put("productAccNo0", accountNo);
			logger.info("productId"+productId);
			logger.info("productAccNo"+accountNo);
			//
			String parentid=this.businessHelper.getProductBusiness().getAccountParentIdbyProductNo(productId);
			map.put("productAccNo0", parentid);
			logger.info("account No"+parentid);
			//
			if(terminateStatusCheck)
				this.forward(Uri.RETAG_PRODUCT,map);
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void AssignCard(){
		boolean terminateStatusCheck=true;
		PmtbProduct productCheck=new PmtbProduct();
		productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		try{
			if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
				terminateStatusCheck=false;
					Messagebox.show("Teminated card cannot be assigned card", "Card Assign", Messagebox.OK, Messagebox.INFORMATION);
			}
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("productId", productId);
			//map.put("productAccNo0", accountNo);
			logger.info("productId"+productId);
			logger.info("productAccNo"+accountNo);
			//
			String parentid=this.businessHelper.getProductBusiness().getAccountParentIdbyProductNo(productId);
			map.put("productAccNo0", parentid);
			logger.info("account No"+parentid);
			//
			if(terminateStatusCheck)
				this.forward(Uri.ASSIGN_CARD_PRODUCT,map);
		}
		catch(Exception e){e.printStackTrace();}
	}
		
	public void Replace(){
		
		boolean terminateStatusCheck=true;
		PmtbProduct productCheck=new PmtbProduct();
		productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		PmtbProductType productType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
		try{
			if(productType.getOneTimeUsage().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO)){
			
				if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
					terminateStatusCheck=false;
						Messagebox.show("Teminated card cannot be replaced.", "Card Replacement", Messagebox.OK, Messagebox.INFORMATION);
				}
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("productId0", productId);
				map.put("productAccNo0", accountNo);
				if(terminateStatusCheck)
						this.forward(Uri.REPLACE_PRODUCT,map);
			}else{
				Messagebox.show("This Product Type cannot be replaced", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
		
	public void UpdateCreditLimit(){
		
		boolean terminateStatusCheck=true;
		PmtbProduct productCheck=new PmtbProduct();
		productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		PmtbProductType productType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
		try{
			if(productType.getCreditLimit().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES) && productType.getPrepaid().equals(NonConfigurableConstants.BOOLEAN_NO)){
				if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
					terminateStatusCheck=false;
					Messagebox.show("Teminated cards cannot Update Credit Limit.", "Update Credit Limit", Messagebox.OK, Messagebox.INFORMATION);
				}	
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("productId", productId);
				map.put("productAccNo0", accountNo);
				if(terminateStatusCheck)
					this.forward(Uri.UPDATE_CREDIT_LIMIT,map);
			}else 
				Messagebox.show("This product Type cannot update credit Limit.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);	
		}
		catch(Exception e){e.printStackTrace();}	
	}
	
	public void Renew(){
	
		boolean terminateStatusCheck=true;
		PmtbProduct productCheck=new PmtbProduct();
		productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		PmtbProductType productType=(PmtbProductType)this.businessHelper.getProductBusiness().getProductTypebyProductId(new BigDecimal(productId));
		try{
			if(!productType.getValidityPeriod().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO) && productType.getPrepaid().equals(NonConfigurableConstants.BOOLEAN_NO)){
				if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
					terminateStatusCheck=false;
					Messagebox.show("Teminated cards cannot be renewed.", "Card Management", Messagebox.OK, Messagebox.INFORMATION);
				}	
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("validityPeriod", productType.getValidityPeriod());
				map.put("productId", productId);
				map.put("productAccNo0", accountNo);
				if(terminateStatusCheck)
					this.forward(Uri.RENEW_PRODUCT,map);
			}else 
				Messagebox.show("This Product Type Cannot be renewed.", "Product Management", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){e.printStackTrace();}
	}

	public void Reactive() throws InterruptedException{
		
//		boolean suspendStatusCheck=true;
//		PmtbProduct productCheck = (PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
//		try{
//			if(!productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
//				suspendStatusCheck=false;
//				Messagebox.show("Only the suspended card can be reactivated.", "Card Reactivation", Messagebox.OK, Messagebox.INFORMATION);
//			}
//			HashMap<String,String> map = new HashMap<String,String>();
//			map.put("productId", productId);
//			map.put("productAccNo0",accountNo);
//			if(suspendStatusCheck)
//			this.forward(Uri.REACTIVATE_PRODUCT,map);
//		}
//		catch(Exception e){e.printStackTrace();}
		// disabling the check
		PmtbProduct product = (PmtbProduct)this.businessHelper.getGenericBusiness().get(PmtbProduct.class, new BigDecimal(productId));
		if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_USED)){
			Messagebox.show("Unable to reactivate used cards!", "Card Reactivation", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}else if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
			Messagebox.show("Unable to reactivate terminated cards!", "Card Reactivation", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}else if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED)){
			Messagebox.show("Unable to reactivate recycled cards!", "Card Reactivation", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("productId", productId);
		map.put("productAccNo0", accountNo);
		this.forward(Uri.REACTIVATE_PRODUCT, map);
	}
		
	public void Suspend() throws InterruptedException{
//		boolean activeStatusCheck=true;
//		PmtbProduct productCheck=new PmtbProduct();
//		productCheck=(PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
//		try{
//			if(!productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
//				activeStatusCheck=false;
//				Messagebox.show("Only the active card can be suspended.", "Card Reactivation", Messagebox.OK, Messagebox.INFORMATION);
//			}
//			HashMap<String,String> map = new HashMap<String,String>();
//			map.put("productId", productId);
//			map.put("productAccNo0", accountNo);
//			if(activeStatusCheck)
//					this.forward(Uri.SUSPEND_PRODUCT,map);
//		}
//			catch(Exception e){e.printStackTrace();}
		PmtbProduct product = (PmtbProduct)this.businessHelper.getGenericBusiness().get(PmtbProduct.class, new BigDecimal(productId));
		if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_USED)){
			Messagebox.show("Unable to suspend used cards!", "Card Suspension", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}else if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
			Messagebox.show("Unable to suspend terminated cards!", "Card Suspension", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}else if(product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED)){
			Messagebox.show("Unable to suspend recycled cards!", "Card Suspension", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("productId", productId);
		map.put("productAccNo0", accountNo);
		this.forward(Uri.SUSPEND_PRODUCT, map);
	}
		
	public void Terminate() throws InterruptedException{
		PmtbProduct productCheck = (PmtbProduct)this.businessHelper.getGenericBusiness(). get(PmtbProduct.class, new BigDecimal(productId));
		if(productCheck.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
			Messagebox.show("This product is already Terminated.", "Card Termination", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("productId", productId);
		map.put("productAccNo0",accountNo);
		this.forward(Uri.TERMINATE_PRODUCT, map);
	}
	
	public void edit() throws InterruptedException{

		HashMap<String,String> map = new HashMap<String, String>();
		map.put("productId", productId);
		final Window win = (Window) Executions.createComponents(Uri.EDIT_PRODUCT, null, map);
		win.setMaximizable(false);
		win.doModal();

		this.refresh();
		//this.forward(Uri.EDIT_PRODUCT, map);
	}
		
	public void cancel() throws InterruptedException {
		this.back();
	}
	
	public void Status(){

		HashMap<String,String> map = new HashMap<String,String>();
		map.put("productId", productId);
		try{
			final Window win = (Window) Executions.createComponents(Uri.PRODUCT_STATUS_HISTORY, null, map);
			win.setMaximizable(true);
			win.setClosable(true);
			win.doModal();

		}catch(Exception e){e.printStackTrace();}
	}
		
	@Override
	public void refresh() throws InterruptedException {
		populateData();
		//((Textbox)this.getFellow("typeName")).setValue("");
	}


	public void afterCompose() {
		if(!this.checkUriAccess(Uri.RETAG_PRODUCT)) ((Button)this.getFellow("retagButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REPLACE_PRODUCT)) ((Button)this.getFellow("replaceButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.UPDATE_CREDIT_LIMIT)) ((Button)this.getFellow("updateCreditButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.RENEW_PRODUCT)) ((Button)this.getFellow("renewButton")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REACTIVATE_PRODUCT)) ((Button)this.getFellow("Reactivate")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SUSPEND_PRODUCT)) ((Button)this.getFellow("Suspend")).setDisabled(true);
		if(!this.checkUriAccess(Uri.TERMINATE_PRODUCT)) ((Button)this.getFellow("Terminate")).setDisabled(true);
	}
	
	public void checkForEditProductAccess(Button button){
		if(this.checkUriAccess(Uri.EDIT_PRODUCT)) button.setVisible(true);
		else button.setVisible(false);
	}
}


