package com.cdgtaxi.ibs.reward.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.api.Listfoot;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReq;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReqFlow;
import com.cdgtaxi.ibs.common.model.forms.SearchAdjustmentRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class SearchAdjustmentRequestWindow extends CommonWindow implements AfterCompose{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchAdjustmentRequestWindow.class);
	
	private Listbox requestLB, requestStatusLB;
	private Listfoot footer;
	private Intbox accountNoIntBox;
	private Combobox accountNameComboBox;
	private Datebox requestDateFromDB, requestDateToDB;
//	private CapsTextbox requesterTB;
	
	public void afterCompose(){
		Components.wireVariables(this, this);
		
		requestStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<Entry<String, String>> statuses = NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS.entrySet();
		for(Entry<String, String> status : statuses){
			if(status.getKey().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_NEW))
				continue;
			
			requestStatusLB.appendItem(status.getValue(), status.getKey());
		}
		if(!requestStatusLB.getChildren().isEmpty()) requestStatusLB.setSelectedIndex(0);
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			//Fix to bypass IE6 issue with double spacing
			if(accountNameComboBox.getChildren().size()==1)
				accountNameComboBox.setSelectedIndex(0);
			
			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.info("");

		Integer accountNo = accountNoIntBox.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.info(name);
		
		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameComboBox.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void search() throws InterruptedException{
		try{
			if(requestDateFromDB.getValue()!=null && requestDateToDB.getValue()==null)
				requestDateToDB.setValue(requestDateFromDB.getValue());
			else if(requestDateFromDB.getValue()==null && requestDateToDB.getValue()!=null)
				requestDateFromDB.setValue(requestDateToDB.getValue());
			
			if(requestDateFromDB.getValue()!=null && requestDateToDB.getValue()!=null)
				if(requestDateFromDB.getValue().compareTo(requestDateToDB.getValue()) == 1)
					throw new WrongValueException(requestDateFromDB, "Request Date From cannot be later than Request Date To.");
			
			SearchAdjustmentRequestForm form = new SearchAdjustmentRequestForm();
			
			if(accountNameComboBox.getSelectedItem()!=null)
				form.account = ((AmtbAccount)accountNameComboBox.getSelectedItem().getValue());
			else{
				String accountName = accountNameComboBox.getValue();
				if(accountName!=null && accountName.length()>=3)
					form.accountName = (accountNameComboBox.getValue());
			}
			if(form.account==null){
				String customerNo = accountNoIntBox.getText();
				form.customerNo = customerNo;
			}
			form.requestDateFrom = DateUtil.convertDateTo0000Hours(DateUtil.convertUtilDateToSqlDate(requestDateFromDB.getValue()));
			form.requestDateTo = DateUtil.convertDateTo2359Hours(DateUtil.convertUtilDateToSqlDate(requestDateToDB.getValue()));
//			form.requester = requesterTB.getValue();
			form.requestStatus = requestStatusLB.getSelectedItem().getValue().toString();
			
			if(form.customerNo!=null && form.customerNo.length()>0) form.isAtLeastOneCriteriaSelected = true;
			else if(form.account!=null) form.isAtLeastOneCriteriaSelected = true;
			else if(form.accountName!=null && form.accountName.length()>0) form.isAtLeastOneCriteriaSelected = true;
			else if(form.requestDateFrom!=null) form.isAtLeastOneCriteriaSelected = true;
			else if(form.requestDateTo!=null) form.isAtLeastOneCriteriaSelected = true;
			else if(form.requester!=null && form.requester.length()>0) form.isAtLeastOneCriteriaSelected = true;
			else if(form.requestStatus!=null && form.requestStatus.length()>0) form.isAtLeastOneCriteriaSelected = true;
			
			if(form.isAtLeastOneCriteriaSelected==false){
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			requestLB.getItems().clear();
			
			List<LrtbRewardAdjReqFlow> flows = this.businessHelper.getRewardBusiness().searchAdjustmentRequest(form);
			if(flows.isEmpty()){
				footer.setVisible(true);
			}else{
				footer.setVisible(false);
				
				for(LrtbRewardAdjReqFlow flow : flows){
					Listitem requestItem = new Listitem();
					
					requestItem.appendChild(newListcell(flow.getLrtbRewardAdjReq().getAdjReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
					requestItem.appendChild(newListcell(flow.getLrtbRewardAdjReq().getAmtbAccount().getCustNo()));
					requestItem.appendChild(newListcell(flow.getLrtbRewardAdjReq().getAmtbAccount().getAccountName()));
					requestItem.appendChild(newListcell(flow.getLrtbRewardAdjReq().getCreatedBy()));
					requestItem.appendChild(newListcell(flow.getLrtbRewardAdjReq().getCreatedDt()));
					if(!flow.getToStatus().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING)){
						requestItem.appendChild(newListcell(flow.getSatbUser().getLoginId()));
						requestItem.appendChild(newListcell(flow.getFlowDt()));
					}
					else{
						requestItem.appendChild(newListcell(""));
						requestItem.appendChild(newEmptyListcell(DateUtil.getSqlDateForNullComparison(), ""));
					}
					requestItem.appendChild(newListcell(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS.get(flow.getToStatus())));
					
					requestItem.setValue(flow);
					requestLB.appendChild(requestItem);
				}
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void refresh() throws InterruptedException {
		search();
	}
	
	public void reset(){
		accountNoIntBox.setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		requestDateFromDB.setValue(null);
		requestDateToDB.setValue(null);
//		requesterTB.setValue("");
		requestStatusLB.setSelectedIndex(0);
		requestLB.getItems().clear();
		footer.setVisible(true);
	}
	
	public void viewRequest() throws InterruptedException{
		Map<String, LrtbRewardAdjReqFlow> params = new HashMap<String, LrtbRewardAdjReqFlow>();
		params.put("requestFlow", (LrtbRewardAdjReqFlow)requestLB.getSelectedItem().getValue());
		this.forward(Uri.VIEW_REWARDS_ADJUSTMENT_REQUEST, params);
	}
}