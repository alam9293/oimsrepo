package com.cdgtaxi.ibs.billgen.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class ConfigureBillGenSetupWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfigureBillGenSetupWindow.class);
	private static final String SELF = "configureBillGenSetupWindow";
	
	public void loadSetup(){
		logger.info("");
		
		Listbox setupListBox = (Listbox)this.getFellow("setupList");
		setupListBox.getItems().clear();
		
		List<BmtbBillGenSetup> setups = this.businessHelper.getGenericBusiness().getAll(BmtbBillGenSetup.class);
		for(BmtbBillGenSetup setup : setups){
			if(setup.getSetupNo().intValue() == NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC.intValue() ||
					setup.getSetupNo().intValue() == NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue()){
				Listitem item = new Listitem();
				item.setValue(setup);
				//setup no label
				item.appendChild(new Listcell(setup.getSetupNo().toString()));
				//setup name text box
				CapsTextbox setupNameTextBox = new CapsTextbox();
				setupNameTextBox.setValue(setup.getName());
				setupNameTextBox.setMaxlength(80);
				Listcell cell2 = new Listcell();
				cell2.appendChild(setupNameTextBox);
				item.appendChild(cell2);
				//cut off day
				item.appendChild(new Listcell("-"));
				//bill gen day
				item.appendChild(new Listcell("-"));
				//bill gen time
				item.appendChild(new Listcell("-"));
				//invoice day
				item.appendChild(new Listcell("-"));
				//Last Updated By
				String lastUpdatedBy = "";
				if(setup.getUpdatedDt()==null) lastUpdatedBy = setup.getCreatedBy();
				else lastUpdatedBy = setup.getUpdatedBy();
				item.appendChild(new Listcell(lastUpdatedBy));
				//Last Updated Dt
				String lastUpdatedDt = "";
				if(setup.getUpdatedDt()==null) lastUpdatedDt = DateUtil.convertTimestampToStr(setup.getCreatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT);
				else lastUpdatedDt = DateUtil.convertTimestampToStr(setup.getUpdatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT);
				item.appendChild(new Listcell(lastUpdatedDt));
				//Govt EInv
				Listcell cell9 = new Listcell();
				Listbox govtEInvLB = (Listbox) this.createGovtEInvListBox(setup.getGovtEInvoiceFlag());
				if(setup.getSetupNo().intValue() == NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue())
					govtEInvLB.setDisabled(true);
				cell9.appendChild(govtEInvLB);
				item.appendChild(cell9);
				
				//pubbs
				Listcell cell10 = new Listcell();
				Listbox pubbsLB = (Listbox) this.createPubbsListBox(setup.getPubbsFlag());
				if(setup.getSetupNo().intValue() == NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue())
					pubbsLB.setDisabled(true);
				cell10.appendChild(pubbsLB);
				item.appendChild(cell10);
				
				setupListBox.appendChild(item);
			}
			else{
				Listitem item = new Listitem();
				item.setValue(setup);
				//setup no label
				item.appendChild(new Listcell(setup.getSetupNo().toString()));
				//setup name text box
				CapsTextbox setupNameTextBox = new CapsTextbox();
				setupNameTextBox.setValue(setup.getName());
				setupNameTextBox.setMaxlength(80);
				Listcell cell2 = new Listcell();
				cell2.appendChild(setupNameTextBox);
				item.appendChild(cell2);
				//cut off day listbox
				Listcell cell3 = new Listcell();
				cell3.appendChild(this.createDayListBox(setup.getCutOffDay()));
				item.appendChild(cell3);
				//bill gen day listbox
				Listcell cell4 = new Listcell();
				cell4.appendChild(this.createDayListBox(setup.getBillGenDay()));
				item.appendChild(cell4);
				//bill gen time listbox
				Listcell cell5 = new Listcell();
				Hbox hbox = new Hbox();
				hbox.appendChild(this.createTimeListBox(new Integer(setup.getBillGenTime().substring(0, 2))));
				hbox.appendChild(new Label(":00"));
				cell5.appendChild(hbox);
				item.appendChild(cell5);
				//invoice day listbox
				Listcell cell6 = new Listcell();
				cell6.appendChild(this.createDayListBox(setup.getInvoiceDay()));
				item.appendChild(cell6);
				//Last Updated By
				String lastUpdatedBy = "";
				if(setup.getUpdatedDt()==null) lastUpdatedBy = setup.getCreatedBy();
				else lastUpdatedBy = setup.getUpdatedBy();
				item.appendChild(new Listcell(lastUpdatedBy));
				//Last Updated Dt
				String lastUpdatedDt = "";
				if(setup.getUpdatedDt()==null) lastUpdatedDt = DateUtil.convertTimestampToStr(setup.getCreatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT);
				else lastUpdatedDt = DateUtil.convertTimestampToStr(setup.getUpdatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT);
				item.appendChild(new Listcell(lastUpdatedDt));
				//Govt EInv
				Listcell cell9 = new Listcell();
				cell9.appendChild(this.createGovtEInvListBox(setup.getGovtEInvoiceFlag()));
				item.appendChild(cell9);
				
				//Pubbs
				Listcell cell10 = new Listcell();
				cell10.appendChild(this.createPubbsListBox(setup.getPubbsFlag()));
				item.appendChild(cell10);
				
				setupListBox.appendChild(item);
			}
		}
	}
	
	private Component createDayListBox(Integer selectedDay){
		Listbox dayListBox = new Listbox();
		dayListBox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		dayListBox.setMultiple(false);
		for(int i=1; i<=31; i++){
			Listitem item = new Listitem(""+i);
			item.setValue(new Integer(i));
			if(i==selectedDay.intValue()) item.setSelected(true);
			dayListBox.appendChild(item);
		}
		if(dayListBox.getSelectedItem()==null) dayListBox.setSelectedIndex(0);
		return dayListBox;
	}
	
	private Component createTimeListBox(Integer selectedTime){
		Listbox timeListBox = new Listbox();
		timeListBox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		timeListBox.setMultiple(false);
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			if(i==selectedTime.intValue()) item.setSelected(true);
			timeListBox.appendChild(item);
		}
		if(timeListBox.getSelectedItem()==null) timeListBox.setSelectedIndex(0);
		return timeListBox;
	}
	
	private Component createGovtEInvListBox(String selectedFlag){
		Listbox govtEInvListBox = new Listbox();
		govtEInvListBox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		govtEInvListBox.setMultiple(false);
		
		Listitem offItem = new Listitem("OFF");
		offItem.setValue(NonConfigurableConstants.BILL_GEN_SETUP_GOVT_EINV_OFF);
		if(selectedFlag!=null && selectedFlag.equals(NonConfigurableConstants.BILL_GEN_SETUP_GOVT_EINV_OFF))
			offItem.setSelected(true);
		govtEInvListBox.appendChild(offItem);
		
		Listitem onItem = new Listitem("ON");
		onItem.setValue(NonConfigurableConstants.BILL_GEN_SETUP_GOVT_EINV_ON);
		if(selectedFlag!=null && selectedFlag.equals(NonConfigurableConstants.BILL_GEN_SETUP_GOVT_EINV_ON))
			onItem.setSelected(true);
		govtEInvListBox.appendChild(onItem);
		
		if(govtEInvListBox.getSelectedItem()==null) govtEInvListBox.setSelectedIndex(0);
		return govtEInvListBox;
	}
	
	private Component createPubbsListBox(String selectedFlag){
		Listbox pubbsListBox = new Listbox();
		pubbsListBox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		pubbsListBox.setMultiple(false);
		
		Listitem offItem = new Listitem("OFF");
		offItem.setValue(NonConfigurableConstants.BILL_GEN_SETUP_PUBBS_OFF);
		if(selectedFlag!=null && selectedFlag.equals(NonConfigurableConstants.BILL_GEN_SETUP_PUBBS_OFF))
			offItem.setSelected(true);
		pubbsListBox.appendChild(offItem);
		
		Listitem onItem = new Listitem("ON");
		onItem.setValue(NonConfigurableConstants.BILL_GEN_SETUP_PUBBS_ON);
		if(selectedFlag!=null && selectedFlag.equals(NonConfigurableConstants.BILL_GEN_SETUP_PUBBS_ON))
			onItem.setSelected(true);
		pubbsListBox.appendChild(onItem);
		
		if(pubbsListBox.getSelectedItem()==null) pubbsListBox.setSelectedIndex(0);
		return pubbsListBox;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void save() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox setupListBox = (Listbox)this.getFellow("setupList");
			List<Listitem> listItems = setupListBox.getItems();
			List<BmtbBillGenSetup> setups = new ArrayList<BmtbBillGenSetup>();
			
			for(Listitem listItem : listItems){
				
				boolean gotChanges = false;
				List childrens = listItem.getChildren();
				
				BmtbBillGenSetup setup = (BmtbBillGenSetup)listItem.getValue();
				if(setup.getSetupNo().intValue() == NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC.intValue() ||
						setup.getSetupNo().intValue() == NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue()){
					Listcell cell2 = (Listcell)childrens.get(1);
					String newName = ((CapsTextbox)cell2.getFirstChild()).getValue();
					if(!newName.equals(setup.getName())){
						setup.setName(newName);
						gotChanges = true;
					}
					//Govt eInv
					Listcell cell9 = (Listcell)childrens.get(8);
					String govtEInvFlag = (String)((Listbox)cell9.getFirstChild()).getSelectedItem().getValue();
					if(!govtEInvFlag.equals(setup.getGovtEInvoiceFlag())){
						setup.setGovtEInvoiceFlag(govtEInvFlag);
						gotChanges = true;
					}
					//Pubbs
					Listcell cell10 = (Listcell)childrens.get(9);
					String pubbsFlag = (String)((Listbox)cell10.getFirstChild()).getSelectedItem().getValue();
					if(!pubbsFlag.equals(setup.getPubbsFlag())){
						setup.setPubbsFlag(pubbsFlag);
						gotChanges = true;
					}
				}
				else{
					
					
					//name
					Listcell cell2 = (Listcell)childrens.get(1);
					String newName = ((CapsTextbox)cell2.getFirstChild()).getValue();
					if(!newName.equals(setup.getName())){
						setup.setName(newName);
						gotChanges = true;
					}
					//cut off day
					Listcell cell3 = (Listcell)childrens.get(2);
					Integer newCutOffDay = (Integer)((Listbox)cell3.getFirstChild()).getSelectedItem().getValue();
					if(newCutOffDay.intValue() != setup.getCutOffDay().intValue()){
						setup.setCutOffDay(newCutOffDay);
						gotChanges = true;
					}
					//bill gen day
					Listcell cell4 = (Listcell)childrens.get(3);
					Integer newBillGenDay = (Integer)((Listbox)cell4.getFirstChild()).getSelectedItem().getValue();
					if(newBillGenDay.intValue() != setup.getBillGenDay().intValue()){
						setup.setBillGenDay(newBillGenDay);
						gotChanges = true;
					}
					//bill gen time
					Listcell cell5 = (Listcell)childrens.get(4);
					Listbox billGenTimeListBox = (Listbox)cell5.getFirstChild().getFirstChild();
					String newBillGenTime = ((String)billGenTimeListBox.getSelectedItem().getValue()) + ":00";
					if(!newBillGenTime.equals(setup.getBillGenTime())){
						setup.setBillGenTime(newBillGenTime);
						gotChanges = true;
					}
					//invoice day
					Listcell cell6 = (Listcell)childrens.get(5);
					Integer newInvoiceDay = (Integer)((Listbox)cell6.getFirstChild()).getSelectedItem().getValue(); 
					if(newInvoiceDay.intValue() != setup.getInvoiceDay().intValue()){
						setup.setInvoiceDay(newInvoiceDay);
						gotChanges = true;
					}
					//Govt eInv
					Listcell cell9 = (Listcell)childrens.get(8);
					String govtEInvFlag = (String)((Listbox)cell9.getFirstChild()).getSelectedItem().getValue();
					if(!govtEInvFlag.equals(setup.getGovtEInvoiceFlag())){
						setup.setGovtEInvoiceFlag(govtEInvFlag);
						gotChanges = true;
					}
					
					//Pubbs 
					Listcell cell10 = (Listcell)childrens.get(9);
					String pubbsFlag = (String)((Listbox)cell10.getFirstChild()).getSelectedItem().getValue();
					if(!pubbsFlag.equals(setup.getPubbsFlag())){
						setup.setPubbsFlag(pubbsFlag);
						gotChanges = true;
					}
				}
				
				if(gotChanges) setups.add(setup);
			}
			
			if(setups.size()>0){
				this.businessHelper.getBillGenBusiness().saveSetupsChanges(setups, getUserLoginIdAndDomain());
				Messagebox.show("Changes saved successfully", 
					"Configure Bill Gen Setup", Messagebox.OK, Messagebox.INFORMATION);
			}
			else{
				Messagebox.show("No changes made", 
						"Configure Bill Gen Setup", Messagebox.OK, Messagebox.INFORMATION);
			}
			
			this.loadSetup();
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
	
	@Override
	public void refresh() {
		
	}
}
