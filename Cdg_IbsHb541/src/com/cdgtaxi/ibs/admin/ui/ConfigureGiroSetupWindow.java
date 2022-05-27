package com.cdgtaxi.ibs.admin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("unchecked")
public class ConfigureGiroSetupWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfigureGiroSetupWindow.class);
	private static final String SELF = "configureGiroSetupWindow";
	private List<IttbGiroSetup> deletedSetups;
	
	public void loadSetup(){
		logger.info("");
		
		deletedSetups = new ArrayList<IttbGiroSetup>();
		
		Listbox setupListBox = (Listbox)this.getFellow("setupList");
		setupListBox.getItems().clear();
		
		List<IttbGiroSetup> setups = this.businessHelper.getAdminBusiness().getActiveGiroSetup();
		for(IttbGiroSetup setup : setups){
			
			Listitem item = new Listitem();
			item.setValue(setup);
			//empty cell for checkbox
			item.appendChild(new Listcell(""));
			//entity listbox
			Listcell cell2 = new Listcell();
			cell2.appendChild(this.createEntityListBox(setup.getFmtbEntityMaster()));
			item.appendChild(cell2);
			//cut off day listbox
			Listcell cell3 = new Listcell();
			cell3.appendChild(this.createDayListBox(setup.getCutOffDay()));
			item.appendChild(cell3);
			//request day listbox
			Listcell cell4 = new Listcell();
			cell4.appendChild(this.createDayListBox(setup.getRequestDay()));
			item.appendChild(cell4);
			//request time listbox
			Listcell cell5 = new Listcell();
			Hbox hbox = new Hbox();
			hbox.appendChild(this.createTimeListBox(new Integer(setup.getRequestTime())));
			hbox.appendChild(new Label(":00"));
			cell5.appendChild(hbox);
			item.appendChild(cell5);
			//value day listbox
			Listcell cell6 = new Listcell();
			cell6.appendChild(this.createDayListBox(setup.getValueDay()));
			item.appendChild(cell6);
			//invoice GIRO day listbox
			Listcell cell7 = new Listcell();
			cell7.appendChild(this.createDayListBox(setup.getInvoiceGiroDay()));
			item.appendChild(cell7);
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

			setupListBox.appendChild(item);
		}
	}
	
	private Component createEntityListBox(FmtbEntityMaster selectedEntity){
		Combobox entityListBox = new Combobox();
		//entityListBox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		//entityListBox.setMultiple(false);
		entityListBox.setReadonly(true);
		entityListBox.setWidth("95%");
		List<FmtbEntityMaster> entities = this.businessHelper.getAdminBusiness().getEntities();
		for(int i=0; i<entities.size(); i++){
			FmtbEntityMaster entity = entities.get(i);
			Comboitem item = new Comboitem(entity.getEntityName());
			item.setValue(entity);
			entityListBox.appendChild(item);
			if(selectedEntity!=null &&
					selectedEntity.getEntityNo().intValue()==entity.getEntityNo().intValue()) 
				entityListBox.setSelectedIndex(i);
		}
		if(entityListBox.getSelectedItem()==null) entityListBox.setSelectedIndex(0);
		return entityListBox;
	}
	
	private Component createDayListBox(Integer selectedDay){
		Listbox dayListBox = new Listbox();
		dayListBox.setMold(com.cdgtaxi.ibs.web.component.Constants.LISTBOX_MOLD_SELECT);
		dayListBox.setMultiple(false);
		for(int i=1; i<=31; i++){
			Listitem item = new Listitem(""+i);
			item.setValue(new Integer(i));
			if(selectedDay!=null &&
					i==selectedDay.intValue()) 
				item.setSelected(true);
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
			if(selectedTime!=null && 
					i==selectedTime.intValue()) 
				item.setSelected(true);
			timeListBox.appendChild(item);
		}
		if(timeListBox.getSelectedItem()==null) timeListBox.setSelectedIndex(0);
		return timeListBox;
	}
	
	public void add(){
		Listbox setupListBox = (Listbox)this.getFellow("setupList");
		Listitem item = new Listitem();
		//empty cell for checkbox
		item.appendChild(new Listcell(""));
		//entity listbox
		Listcell cell2 = new Listcell();
		cell2.appendChild(this.createEntityListBox(null));
		item.appendChild(cell2);
		//cut off day listbox
		Listcell cell3 = new Listcell();
		cell3.appendChild(this.createDayListBox(null));
		item.appendChild(cell3);
		//request day listbox
		Listcell cell4 = new Listcell();
		cell4.appendChild(this.createDayListBox(null));
		item.appendChild(cell4);
		//request time listbox
		Listcell cell5 = new Listcell();
		Hbox hbox = new Hbox();
		hbox.appendChild(this.createTimeListBox(null));
		hbox.appendChild(new Label(":00"));
		cell5.appendChild(hbox);
		item.appendChild(cell5);
		//value day listbox
		Listcell cell6 = new Listcell();
		cell6.appendChild(this.createDayListBox(null));
		item.appendChild(cell6);
		//invoice GIRO day listbox
		Listcell cell7 = new Listcell();
		cell7.appendChild(this.createDayListBox(null));
		item.appendChild(cell7);
		//Last Updated By
		item.appendChild(new Listcell(""));
		//Last Updated Dt
		item.appendChild(new Listcell(""));
		setupListBox.appendChild(item);
	}
	
	public void delete() throws InterruptedException{
		
//		if(Messagebox.show(
//				"Deleting the setup(s) will cause all existing respective requests to be marked as deleted as well AFTER SAVING. " +
//				"Do you still want to continue?", 
//				"Configure Giro Setup", 
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
//			return;
//		}
		
		Listbox setupListBox = (Listbox)this.getFellow("setupList");
		Set<Listitem> selectedItems = setupListBox.getSelectedItems();
		while(selectedItems.size() > 0){
//		for(Listitem item : selectedItems){
			Listitem item = selectedItems.iterator().next();
			if(item.getValue() != null){
				//Before allowing to delete, ensure that there are no existing requests
				if(this.businessHelper.getAdminBusiness().getExistingGiroRequest((IttbGiroSetup)item.getValue()).size()>0){
					throw new WrongValueException(item, "There are existing requests tied to previous entity, therefore changing/deleting of entity is not allowed.");
				}
				
				deletedSetups.add((IttbGiroSetup) item.getValue());
			}
			item.detach();
			selectedItems = setupListBox.getSelectedItems();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void save() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox setupListBox = (Listbox)this.getFellow("setupList");
			List<Listitem> listItems = setupListBox.getItems();
			List<IttbGiroSetup> existingSetups = new ArrayList<IttbGiroSetup>();
			List<IttbGiroSetup> newSetups = new ArrayList<IttbGiroSetup>();
			
			for(int i=0; i<listItems.size(); i++){
				
				Listitem listItem = listItems.get(i);
				
				boolean gotChanges = false;
				List childrens = listItem.getChildren();
				
				//Existing Setup begin updated
				if(listItem.getValue() !=null){
					IttbGiroSetup setup = (IttbGiroSetup)listItem.getValue();
					
					//Entity
					Listcell cell2 = (Listcell)childrens.get(1);
					FmtbEntityMaster selectedEntity = (FmtbEntityMaster) ((Combobox)cell2.getFirstChild()).getSelectedItem().getValue();
					
					//Check any clashing entity
					for(int j=0; j!=i && j<listItems.size(); j++){
						Listitem itemItem2 = listItems.get(j);
						List childrens2 = itemItem2.getChildren();
						Listcell item2cell2 = (Listcell)childrens2.get(1);
						FmtbEntityMaster comparingEntity = (FmtbEntityMaster) ((Combobox)item2cell2.getFirstChild()).getSelectedItem().getValue();
						if(selectedEntity.getEntityNo().intValue() == comparingEntity.getEntityNo().intValue()){
							throw new WrongValueException(listItem, "There is a setup with the same entity existed");
						}
					}
					
					if(selectedEntity.getEntityNo().intValue() != setup.getFmtbEntityMaster().getEntityNo().intValue()){
						//Before allowing to change, ensure that there are no existing requests
						if(this.businessHelper.getAdminBusiness().getExistingGiroRequest(setup).size()>0){
							throw new WrongValueException(listItem, "There are existing requests tied to previous entity, therefore changing/deleting of entity is not allowed.");
						}
						
						setup.setFmtbEntityMaster(selectedEntity);
						gotChanges = true;
					}
					
					//cut off day
					Listcell cell3 = (Listcell)childrens.get(2);
					Integer newCutOffDay = (Integer)((Listbox)cell3.getFirstChild()).getSelectedItem().getValue();
					if(newCutOffDay.intValue() != setup.getCutOffDay()){
						setup.setCutOffDay(newCutOffDay);
						gotChanges = true;
					}
					
					//request day
					Listcell cell4 = (Listcell)childrens.get(3);
					Integer newRequestDay = (Integer)((Listbox)cell4.getFirstChild()).getSelectedItem().getValue();
					if(newRequestDay.intValue() != setup.getRequestDay()){
						setup.setRequestDay(newRequestDay);
						gotChanges = true;
					}
					
					//request time
					Listcell cell5 = (Listcell)childrens.get(4);
					Listbox requestTimeListBox = (Listbox)cell5.getFirstChild().getFirstChild();
					int newRequestTime = Integer.parseInt((String)requestTimeListBox.getSelectedItem().getValue());
					if(newRequestTime!=setup.getRequestTime()){
						setup.setRequestTime(newRequestTime);
						gotChanges = true;
					}
					
					//value day
					Listcell cell6 = (Listcell)childrens.get(5);
					Integer newValueDay = (Integer)((Listbox)cell6.getFirstChild()).getSelectedItem().getValue(); 
					if(newValueDay.intValue() != setup.getValueDay()){
						setup.setValueDay(newValueDay);
						gotChanges = true;
					}
					
					//invoice day
					Listcell cell7 = (Listcell)childrens.get(6);
					Integer newInvoiceGiroDay = (Integer)((Listbox)cell7.getFirstChild()).getSelectedItem().getValue(); 
					if(newInvoiceGiroDay.intValue() != setup.getInvoiceGiroDay()){
						setup.setInvoiceGiroDay(newInvoiceGiroDay);
						gotChanges = true;
					}
					
					if(gotChanges) existingSetups.add(setup);
				}
				//New Setup
				else{
					IttbGiroSetup setup = new IttbGiroSetup();
					
					//Entity
					Listcell cell2 = (Listcell)childrens.get(1);
					FmtbEntityMaster selectedEntity = (FmtbEntityMaster) ((Combobox)cell2.getFirstChild()).getSelectedItem().getValue();
					
					//Check any clashing entity
					for(int j=0; j!=i && j<listItems.size(); j++){
						Listitem itemItem2 = listItems.get(j);
						List childrens2 = itemItem2.getChildren();
						Listcell item2cell2 = (Listcell)childrens2.get(1);
						FmtbEntityMaster comparingEntity = (FmtbEntityMaster) ((Combobox)item2cell2.getFirstChild()).getSelectedItem().getValue();
						if(selectedEntity.getEntityNo().intValue() == comparingEntity.getEntityNo().intValue()){
							throw new WrongValueException(listItem, "There is a setup with the same entity existed");
						}
					}
					
					setup.setFmtbEntityMaster(selectedEntity);
					
					//cut off day
					Listcell cell3 = (Listcell)childrens.get(2);
					Integer newCutOffDay = (Integer)((Listbox)cell3.getFirstChild()).getSelectedItem().getValue();
					setup.setCutOffDay(newCutOffDay);
					
					//request day
					Listcell cell4 = (Listcell)childrens.get(3);
					Integer newRequestDay = (Integer)((Listbox)cell4.getFirstChild()).getSelectedItem().getValue();
					setup.setRequestDay(newRequestDay);
					
					//request time
					Listcell cell5 = (Listcell)childrens.get(4);
					Listbox requestTimeListBox = (Listbox)cell5.getFirstChild().getFirstChild();
					int newRequestTime = Integer.parseInt((String)requestTimeListBox.getSelectedItem().getValue());
					setup.setRequestTime(newRequestTime);
					
					//value day
					Listcell cell6 = (Listcell)childrens.get(5);
					Integer newValueDay = (Integer)((Listbox)cell6.getFirstChild()).getSelectedItem().getValue(); 
					setup.setValueDay(newValueDay);
					
					//invoice day
					Listcell cell7 = (Listcell)childrens.get(6);
					Integer newInvoiceGiroDay = (Integer)((Listbox)cell7.getFirstChild()).getSelectedItem().getValue(); 
					setup.setInvoiceGiroDay(newInvoiceGiroDay);
					
					newSetups.add(setup);
				}
			}
			
			if(existingSetups.size()>0 || newSetups.size()>0 || deletedSetups.size()>0){
				this.businessHelper.getAdminBusiness().saveGiroSetupChanges(existingSetups, newSetups, deletedSetups, getUserLoginIdAndDomain());
				Messagebox.show("Changes saved successfully", 
					"Configure Giro Setup", Messagebox.OK, Messagebox.INFORMATION);
			}
			else{
				Messagebox.show("No changes made", 
						"Configure Giro Setup", Messagebox.OK, Messagebox.INFORMATION);
			}
			
			this.loadSetup();
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
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Override
	public void refresh() {
		
	}
}
