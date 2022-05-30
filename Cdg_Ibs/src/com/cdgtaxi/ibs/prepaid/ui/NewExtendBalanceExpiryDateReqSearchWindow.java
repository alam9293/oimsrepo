package com.cdgtaxi.ibs.prepaid.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidProductForm;
import com.cdgtaxi.ibs.common.ui.CommonSearchByAccountWindow;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.ProductUtil;
import com.cdgtaxi.ibs.web.component.Datebox;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class NewExtendBalanceExpiryDateReqSearchWindow extends CommonSearchByAccountWindow implements AfterCompose {

	private Textbox cardNumberStartField, cardNumberEndField, nameOnCardField;
	private Datebox cardExpiryFromField, cardExpiryToField;
	private Datebox balanceExpiryFromField, balanceExpiryToField;
	private Listbox resultList, cardStatusField;
	private static final Logger logger = Logger.getLogger(NewExtendBalanceExpiryDateReqSearchWindow.class);


	public void afterCompose() {
		Components.wireVariables(this, this);
		ComponentUtil.buildListbox(cardStatusField, ProductUtil.getProductStatusForPrepaidRequest(), true);

	}
	
	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String name) {
		return this.businessHelper.getProductBusiness().getActiveAccountList(custNo, name);

	}


	public void onSelectResultList() throws InterruptedException{
		
		PmtbProduct product = ComponentUtil.getSelectedItem(resultList);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("productNo", product.getProductNo());
		forward(Uri.NEW_EXTEND_BALANCE_EXPIRY_DATE_REQ ,map);
	}
	
	
	public void searchPrepaidProducts() throws InterruptedException{
		
		resultList.getItems().clear();
		SearchPrepaidProductForm form = new SearchPrepaidProductForm();
		
		if(selectedAccount!=null){
			form.setAccountNo(selectedAccount.getAccountNo());
		}

		form.setCardNoStart(cardNumberStartField.getValue());
		form.setCardNoEnd(cardNumberEndField.getValue());
		form.setNameOnCard(nameOnCardField.getValue());
		form.setStatus((String) cardStatusField.getSelectedItem().getValue());
		form.setCardExpiryFrom(cardExpiryFromField.getValue());
		form.setCardExpiryTo(cardExpiryToField.getValue());
		form.setBalanceExpiryFrom(balanceExpiryFromField.getValue());
		form.setBalanceExpiryTo(balanceExpiryToField.getValue());
		
		this.displayProcessing();
		List<PmtbProduct> productList = businessHelper.getPrepaidBusiness().searchPrepaidProducts(form);
		
		if(productList.size()>0){
			
			if(productList.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for(PmtbProduct product : productList){
				AmtbAccount acct = product.getAmtbAccount();
				
				AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
				Listitem item = new Listitem();
				item.setValue(product);
				item.appendChild(newListcell(topAcct.getCustNo()));
				item.appendChild(newListcell(topAcct.getAccountName()));
				
				String acctCategory = acct.getAccountCategory();
				if(acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)){
					item.appendChild(newListcell(acct.getAccountName()));
				} else {
					item.appendChild(newListcell("-"));
				}
				
				if(acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
					item.appendChild(newListcell(acct.getAccountName()));
				} else {
					item.appendChild(newListcell("-"));
				}

				item.appendChild(newListcell(product.getPmtbProductType().getName()));
				item.appendChild(newListcell(product.getCardNo()));
				item.appendChild(newListcell(product.getNameOnProduct()));
				item.appendChild(newListcell(NonConfigurableConstants.PRODUCT_STATUS.get(product.getCurrentStatus())));
				item.appendChild(newListcell(product.getBalanceExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(product.getExpiryDate(), DateUtil.GLOBAL_DATE_FORMAT));
				
				resultList.appendChild(item);
			}
			
			if(productList.size()>ConfigurableConstants.getMaxQueryResult())
				resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultList.getListfoot()!=null)
				resultList.removeChild(resultList.getListfoot());
		}
		else{
			if(resultList.getListfoot()==null){
				resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
			}
		}

	}
	
	

	@Override
	public void reset() throws InterruptedException {

		super.reset();
		ComponentUtil.reset(cardNumberStartField, cardNumberEndField, nameOnCardField,
				cardExpiryFromField, cardExpiryToField, balanceExpiryFromField, balanceExpiryToField,
				resultList, cardStatusField);
		
	}
	
	
	@Override
	public void refresh() throws InterruptedException {
		super.refresh();
		resultList.clearSelection();
	}
	
}
