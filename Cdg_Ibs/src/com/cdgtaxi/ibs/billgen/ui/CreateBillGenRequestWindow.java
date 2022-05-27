package com.cdgtaxi.ibs.billgen.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.BillGenErrorFoundException;
import com.cdgtaxi.ibs.common.exception.BillGenRequestCreationException;
import com.cdgtaxi.ibs.common.exception.BillGenRequestExistanceException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class CreateBillGenRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateBillGenRequestWindow.class);
	private static final String SELF = "createBillGenRequestWindow";
	
	private Integer currentSelectedBillGenSetup = null;
	private Map<Integer, ArrayList<Component>> billGenSetupDetailMap = new HashMap<Integer, ArrayList<Component>>();

	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		//register normal detail which is just not for ad hoc
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow1"), NonConfigurableConstants.BILL_GEN_SETUP_MONTHLY);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow2"), NonConfigurableConstants.BILL_GEN_SETUP_MONTHLY);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow3"), NonConfigurableConstants.BILL_GEN_SETUP_MONTHLY);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow1"), NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_1);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow2"), NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_1);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow3"), NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_1);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow1"), NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_2);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow2"), NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_2);
		this.registerBillGenSetupDetail(this.getFellow("normalDetailRow3"), NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_2);
		
		//register ad hoc detail
		this.registerBillGenSetupDetail(this.getFellow("adHocDetailRow"), NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC);
		this.registerBillGenSetupDetail(this.getFellow("adHocDetailRow"), NonConfigurableConstants.BILL_GEN_SETUP_DRAFT);
		
		//populating month list boxes
		Integer currentMonth = new Integer(DateUtil.getCurrentMonth());
		Integer oneMonthBefore = currentMonth - 1;
		if(oneMonthBefore.intValue()==0) oneMonthBefore = 12;
		Integer oneMonthAfter = currentMonth + 1;
		if(oneMonthAfter.intValue()==13) oneMonthAfter = 1;
		
		//populate bill gen month +=
		Listbox monthOfBillGenListBox = (Listbox)this.getFellow("monthOfBillGenListBox");
		monthOfBillGenListBox.appendChild(new Listitem(NonConfigurableConstants.CALENDAR_MONTH.get(currentMonth), currentMonth));
		monthOfBillGenListBox.appendChild(new Listitem(NonConfigurableConstants.CALENDAR_MONTH.get(oneMonthAfter), oneMonthAfter));
		((Listitem)monthOfBillGenListBox.getFirstChild()).setSelected(true);
		
		//populate bill gen setup listbox
		Listbox billGenSetupListBox = (Listbox)this.getFellow("billGenSetupListBox");
		for(Integer key : NonConfigurableConstants.BILL_GEN_SETUP.keySet()){
			billGenSetupListBox.appendChild(new Listitem(NonConfigurableConstants.BILL_GEN_SETUP.get(key), key));
		}
		((Listitem)billGenSetupListBox.getFirstChild()).setSelected(true);
		this.onSelectBillGenSetup(billGenSetupListBox);
		
		//populate entity list
		Listbox entityListBox = (Listbox)this.getFellow("entityListBox");
		List<FmtbEntityMaster> entities = this.businessHelper.getAdminBusiness().getActiveEntities();
		Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
		for(FmtbEntityMaster entity : entities){
			entityListBox.appendChild(new Listitem(entity.getEntityName(), entity.getEntityNo()));
		}
		if(entityListBox.getChildren().size()>0) entityListBox.setSelectedIndex(0);
	}
	
	
	public void onSelectBillGenSetup(Listbox listbox){
		//set previous selected bill gen setup detail invisible
		if(currentSelectedBillGenSetup!=null){
			ArrayList<Component> billGenSetupDetailList = this.billGenSetupDetailMap.get(currentSelectedBillGenSetup);
			for(Component component : billGenSetupDetailList){
				component.setVisible(false);
			}
		}
		
		//change current selected value from the listbox
		currentSelectedBillGenSetup = (Integer)listbox.getSelectedItem().getValue();
			
		//set current selected bill gen setup detail visible
		ArrayList<Component> billGenSetupDetailList = this.billGenSetupDetailMap.get(currentSelectedBillGenSetup);
		for(Component component : billGenSetupDetailList){
			component.setVisible(true);
		}
		
		//populate default values depending on selected bill gen set up
		if(currentSelectedBillGenSetup.intValue() != NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC.intValue() &&
				currentSelectedBillGenSetup.intValue() != NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue()){
			//retrieve setup and put up invoice day
			BmtbBillGenSetup billGenSetup = (BmtbBillGenSetup)this.businessHelper.getGenericBusiness().get(BmtbBillGenSetup.class, currentSelectedBillGenSetup);
			Label invoiceDayValueLabel = (Label)this.getFellow("invoiceDayValueLabel");
			invoiceDayValueLabel.setValue(billGenSetup.getBillGenDay().toString());
			Label billGenTimeLabel = (Label)this.getFellow("billGenTimeLabel");
			billGenTimeLabel.setValue(billGenSetup.getBillGenTime().toString());
			Label invoiceDayLabel = (Label)this.getFellow("invoiceDayLabel");
			invoiceDayLabel.setValue(billGenSetup.getInvoiceDay().toString());
			Label cutOffDayLabel = (Label)this.getFellow("cutOffDayLabel");
			cutOffDayLabel.setValue(billGenSetup.getCutOffDay().toString());
		}
	}
	
	public void registerBillGenSetupDetail(Component component, Integer key){
		ArrayList<Component> billGenSetupDetailList = this.billGenSetupDetailMap.get(key);
		if(billGenSetupDetailList==null) billGenSetupDetailList = new ArrayList<Component>();
		billGenSetupDetailList.add(component);
		this.billGenSetupDetailMap.put(key, billGenSetupDetailList);
	}
	
	public void createRequest() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox billGenSetupListBox = (Listbox)this.getFellow("billGenSetupListBox");
			Integer billGenSetupNo = (Integer)billGenSetupListBox.getSelectedItem().getValue();
			Integer requestNo = null;
			
			//this shows it is a normal bill gen request to be created
			if(billGenSetupNo.intValue()!=NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC.intValue() &&
					billGenSetupNo.intValue()!=NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue()){
				Listbox monthOfBillGenListBox = (Listbox)this.getFellow("monthOfBillGenListBox");
				Integer monthOfBillGen = (Integer)monthOfBillGenListBox.getSelectedItem().getValue();
				Integer entityNo = (Integer)((Listbox)this.getFellow("entityListBox")).getSelectedItem().getValue();
				requestNo = this.businessHelper.getBillGenBusiness().createNormalRequest(billGenSetupNo, monthOfBillGen, entityNo, getUserLoginIdAndDomain());
			}
			else{
				Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
				if(accountNameComboBox.getSelectedItem()==null)
					throw new WrongValueException(accountNameComboBox, "* Mandatory field");
				else{	
					AmtbAccount topLevelAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
					if(topLevelAccount==null)
						throw new WrongValueException(accountNameComboBox, "* Mandatory field");
					else{
						if(billGenSetupNo.intValue()==NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC.intValue()){
							if(Messagebox.show("You are about to create an ad hoc request for "+topLevelAccount.getAccountName()+"("+topLevelAccount.getCustNo()+"). Are you sure?", 
									"Create Ad Hoc Request", 
									Messagebox.OK | Messagebox.CANCEL, 
									Messagebox.QUESTION)==Messagebox.OK){
								requestNo = this.businessHelper.getBillGenBusiness().createAdHocRequest(topLevelAccount, getUserLoginIdAndDomain());
							}
							else return;
						}
						else if(billGenSetupNo.intValue()==NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue()){
							if(Messagebox.show("You are about to create an draft request for "+topLevelAccount.getAccountName()+"("+topLevelAccount.getCustNo()+"). Are you sure?", 
									"Create Draft Request", 
									Messagebox.OK | Messagebox.CANCEL, 
									Messagebox.QUESTION)==Messagebox.OK){
								requestNo = this.businessHelper.getBillGenBusiness().createDraftRequest(topLevelAccount, getUserLoginIdAndDomain());
							}
							else return;
						}
					}
				}
			}
			
			Messagebox.show("New request("+requestNo+") successfully created", "Create Bill Gen Request", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(BillGenRequestExistanceException bree){
			Messagebox.show(bree.getMessage(), 
					"Bill Gen Request Existance Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(BillGenErrorFoundException bgefe){
			Messagebox.show(bgefe.getMessage(), 
					"Bill Gen Error Found", Messagebox.OK, Messagebox.ERROR);
		}
		catch(BillGenRequestCreationException bgrce){
			Messagebox.show(bgrce.getMessage(), 
					"Bill Gen Request Creation Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.info("");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
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
			List<AmtbAccount> accounts = this.businessHelper.getBillGenBusiness().getBilliableAccountOnlyTopLevelWithEffectiveEntity(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameComboBox.setSelectedIndex(0);
			}
			else accountNameComboBox.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.info("");

		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
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
			List<AmtbAccount> accounts = this.businessHelper.getBillGenBusiness().getBilliableAccountOnlyTopLevelWithEffectiveEntity(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");

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
	
	@Override
	public void refresh() {
		
	}
}
