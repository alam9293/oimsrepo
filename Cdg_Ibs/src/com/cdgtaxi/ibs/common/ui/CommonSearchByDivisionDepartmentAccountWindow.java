package com.cdgtaxi.ibs.common.ui;

import java.util.List;

import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.google.common.collect.Lists;


@SuppressWarnings("serial")
public abstract class CommonSearchByDivisionDepartmentAccountWindow extends CommonSearchByAccountWindow {

	
	public static final String TYPE_CENTRAL = "C";
	public static final String TYPE_DEPEND = "D";
	
	protected static final String DEPT_ID = "departmentComboBox";
	protected static final String DIV_ID = "divisionComboBox";
	protected static final String DIV_DEPT_ROW_ID = "divisionDepartmentRow";
	
	protected static final String DIV_LABEL_ID = "divisionLabel";
	protected static final String DEPT_LABEL_ID = "departmentLabel";
	
	
	protected Combobox divisionComboBox;
	protected Combobox departmentComboBox;
	protected Row divDeptRow;
	protected Label divisionLabel;
	protected Label departmentLabel;
	
	//default type is central
	protected String divDeptWindowType = TYPE_CENTRAL;

	@Override
	public void onCreate(CreateEvent ce) throws Exception {
		
		super.onCreate(ce);
		divisionComboBox = (Combobox)getFellow(DIV_ID);
		departmentComboBox = (Combobox)getFellow(DEPT_ID);
		divDeptRow = (Row) getFellow(DIV_DEPT_ROW_ID);
		divDeptRow.setVisible(false);
		
		divisionComboBox.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onSelectDivision();
			}
		});
		
		departmentComboBox.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onSelectDepartment();
			}
		});
		
		
		divisionLabel = (Label)getFellow(DIV_LABEL_ID);
		departmentLabel= (Label)getFellow(DEPT_LABEL_ID);
	}
	
	
	@Override
	public void onChangeCustNo() throws InterruptedException {
		super.onChangeCustNo();
	}
	
	
	@Override
	public void onSelectAccountName() throws InterruptedException{
		
		logger.debug("Account name selected");
		super.onSelectAccountName();

		if(selectedAccount!=null){
			String accountCategory = selectedAccount.getAccountCategory();
			boolean showDepartment = false;
			
			if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
				
				if(divDeptWindowType.equals(TYPE_CENTRAL)){
				
					List<AmtbAccount> divisions = populateDivisionAccounts(selectedAccount);
					List<AmtbAccount> departments = populateDepartmentAccounts(selectedAccount);
					buildChildAccountListbox(divisionComboBox, divisions);
					buildChildAccountListbox(departmentComboBox, departments);
					showDepartment = true;
					
				} else if(divDeptWindowType.equals(TYPE_DEPEND)){
					
					List<AmtbAccount> divisions = populateDivisionAccounts(selectedAccount);
					buildChildAccountListbox(divisionComboBox, divisions);
					List<AmtbAccount> emptyDepartmentList = Lists.newArrayList();
					buildChildAccountListbox(departmentComboBox, emptyDepartmentList);
					showDepartment = false;
				}
				
				divisionLabel.setValue("Division");
			}
			
			else if(accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
				List<AmtbAccount> subApplicants = populateDivisionAccounts(selectedAccount);
				buildChildAccountListbox(divisionComboBox, subApplicants);
				showDepartment = false;
				
				divisionLabel.setValue("Sub Applicant");
			}
			
			departmentComboBox.setVisible(showDepartment);
			departmentLabel.setVisible(showDepartment);
			divDeptRow.setVisible(true);
			
			
		} else {
			divDeptRow.setVisible(false);
		}
		
	}
	
	public abstract List<AmtbAccount> populateDivisionAccounts(AmtbAccount acct);
	
	public abstract List<AmtbAccount> populateDepartmentAccounts(AmtbAccount acct);
	

	protected void buildChildAccountListbox(Combobox combobox, List<AmtbAccount> childAccts){
		
		combobox.getChildren().clear();
		combobox.appendChild(ComponentUtil.createNotRequiredComboitem());

		if(childAccts!=null){
			for(AmtbAccount acct : childAccts){
				Comboitem newItem = new Comboitem(acct.getAccountName()+" ("+acct.getCode()+")");
				newItem.setValue(acct);
				combobox.appendChild(newItem);
			}
		}
		
		combobox.setSelectedIndex(0);
	
	}
	
	
	public void onSelectDivision() throws InterruptedException{
		logger.debug("Division selected");

		selectedAccount = ComponentUtil.getSelectedItem(divisionComboBox);

		if(selectedAccount!=null){
			if(divDeptWindowType.equals(TYPE_DEPEND)){
				List<AmtbAccount> departments = populateDepartmentAccounts(selectedAccount);
				buildChildAccountListbox(departmentComboBox, departments);
				
				boolean showDepartment = departments.isEmpty() ? false : true;
	
				departmentComboBox.setVisible(showDepartment);
				departmentLabel.setVisible(showDepartment);
			}
		}
		
		if(departmentComboBox.getChildren().size()>0){
			departmentComboBox.setSelectedIndex(0);
		}
		
		if(selectedAccount==null){
			if(departmentComboBox!=null){
				selectedAccount = ComponentUtil.getSelectedItem(departmentComboBox);
			}
			if(selectedAccount==null){
				selectedAccount = ComponentUtil.getSelectedItem(accountNameComboBox);
			}
		}
		
		logger.info("Selected Account: " + (selectedAccount)!=null ? selectedAccount.getAccountNo() : null);
		
		onSelectDivisionOrDepartment();
	}

	public void onSelectDepartment() throws InterruptedException{
		logger.info("Department selected");
		
		selectedAccount = ComponentUtil.getSelectedItem(departmentComboBox);
		
		if(divDeptWindowType.equals(TYPE_CENTRAL)){
			if(divisionComboBox.getChildren().size()>0){
				divisionComboBox.setSelectedIndex(0);
			}
		}

		if(selectedAccount==null){
			selectedAccount = ComponentUtil.getSelectedItem(divisionComboBox);
			if(selectedAccount==null){
				selectedAccount = ComponentUtil.getSelectedItem(accountNameComboBox);
			}
		}
		
		logger.info("Selected Account: " + (selectedAccount)!=null ? selectedAccount.getAccountNo() : null);
		
		onSelectDivisionOrDepartment();
	}
	
	
	
	public void onSelectDivisionOrDepartment() throws InterruptedException{
		
	}
	
	@Override
	public void reset() throws InterruptedException {
		
		super.reset();
		ComponentUtil.reset(divisionComboBox, departmentComboBox);
		divDeptRow.setVisible(false);
	}
	
	public String getDivDeptWindowType() {
		return divDeptWindowType;
	}

	public void setDivDeptWindowType(String windowType) {
		this.divDeptWindowType = windowType;
	}

	
}
