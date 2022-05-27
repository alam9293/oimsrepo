package com.cdgtaxi.ibs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Intbox;
import org.zkoss.zul.api.Listhead;

import com.cdgtaxi.ibs.web.component.Datebox;
import com.cdgtaxi.ibs.web.component.Listbox;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;


public class ComponentUtil {
	/**
	 * @param span the number of cols span for listfooter
	 * @return
	 */
	
	private static final Logger logger = Logger.getLogger(ComponentUtil.class);
	
	public static Component createNoRecordsFoundListFoot(int span){
		Listfoot listFoot = new Listfoot();
		Listfooter listFooter = new Listfooter();
		listFooter.appendChild(new Label("No records found"));
		listFooter.setSpan(span);
		listFoot.appendChild(listFooter);
		return listFoot;
	}
	
	public static Component createTotalRecordListFoot(int span, int count){
		Listfoot listFoot = new Listfoot();
		Listfooter listFooter = new Listfooter();
		listFooter.appendChild(new Label("Total Count: " + count));
		listFooter.setSpan(span);
		listFoot.appendChild(listFooter);
		return listFoot;
	}
	/**
	 * method to create a listitem of label "-" and value ""
	 * @return listitem
	 */
	
	public static Component createNullValueListItem(){
		Listitem listItem =  new Listitem("-", null);
		listItem.setSelected(true);
		return listItem;
	}
	
	public static Component createNotRequiredListItem(){
		Listitem listItem =  new Listitem("-", "");
		listItem.setSelected(true);
		return listItem;
	}
	public static Component createNotRequiredListItem(Object value){
		Listitem listItem =  new Listitem("-", value);
		listItem.setSelected(true);
		return listItem;
	}
	/**
	 * method to create a listitem of label "All" and value "[null]"
	 * @return listitem
	 */
	public static Component createNotRequiredAllListItem(){
		Listitem listItem =  new Listitem("All", null);
		listItem.setSelected(true);
		return listItem;
	}
	public static Listitem createRequiredDefaultListitem(){
		Listitem listitem = new Listitem("-- PLEASE SELECT ONE --", null);
		listitem.setSelected(true);
		return listitem;
	}
	/**
	 * method to convert non configurable constants to a list of listitem
	 * @param nonConfigurableConstants - the map in the class NonConfigurableConstants
	 * @param isRequired - if false, will add a "-" list item first.
	 * @return
	 */
	public static List<Listitem> convertToListitems(Map<String, String> nonConfigurableConstants, boolean isRequired){
		List<Listitem> returnList = new ArrayList<Listitem>();
		if(!isRequired){
			returnList.add((Listitem)createNotRequiredListItem());
		}
		for(String key : nonConfigurableConstants.keySet()){
			returnList.add(new Listitem(nonConfigurableConstants.get(key), key));
		}
		return returnList;
	}

	/**
	 * Comboitem equivalent of createNotRequiredComboitem() method
	 * @return comboitem
	 */
	
	public static Comboitem createNullValueComboitem(){
		Comboitem comboitem =  new Comboitem("-");
		comboitem.setValue(null);

		return comboitem;
	}
	
	public static Comboitem createNotRequiredComboitem(){
		Comboitem comboitem =  new Comboitem("-");
		comboitem.setValue("");
		//		comboitem.setSelected(true);
		return comboitem;
	}
	/**
	 * Comboitem equivalent of createRequiredDefaultListitem() method
	 * @return comboitem
	 */
	public static Comboitem createRequiredDefaultComboitem(){
		Comboitem comboitem = new Comboitem("-- PLEASE SELECT ONE --");
		comboitem.setValue(null);
		return comboitem;
	}
	/**
	 * Comboitem equivalent of convertToListitems() method
	 * @param nonConfigurableConstants - the map in the class NonConfigurableConstants
	 * @param isRequired - if false, will add a "-" list item first.
	 * @return
	 */
	public static List<Comboitem> convertToComboitems(Map<String, String> nonConfigurableConstants, boolean isRequired){
		List<Comboitem> returnList = new ArrayList<Comboitem>();
		if(!isRequired){
			returnList.add(createNotRequiredComboitem());
		}
		for(String key : nonConfigurableConstants.keySet()){
			Comboitem item = new Comboitem(nonConfigurableConstants.get(key));
			item.setValue(key);
			returnList.add(item);
		}
		return returnList;
	}
	
	
	public static void reset(Component... comps){
		
		for(Component comp: comps){
			
			if(comp instanceof Textbox){
				Textbox c = (Textbox)comp;
				c.setRawValue(null);
				
			} else if(comp instanceof Datebox){
				Datebox c = (Datebox)comp;
				c.setRawValue(null);
				
			} else if(comp instanceof Intbox){
				Intbox c = (Intbox)comp;
				c.setRawValue(null);

				
			} else if(comp instanceof Decimalbox){
				Decimalbox c = (Decimalbox)comp;
				c.setRawValue(null);
				
			} else if(comp instanceof Listbox){
				Listbox c = (Listbox)comp;
				if(c.getMold().equals("select")){
					c.setSelectedIndex(0);
				} else {
					
					List list = c.getItems();
					if(list!=null){
						list.clear();
						if(c.getListfoot()==null){
							c.appendChild(ComponentUtil.createNoRecordsFoundListFoot(c.getListhead().getChildren().size()));
						}
					}
					
				}
			} else if(comp instanceof Combobox){
				Combobox c = (Combobox)comp;
				c.setSelectedIndex(0);
			
			} else if(comp instanceof Label){
				Label c = (Label)comp;
				c.setValue("");
				
			} else if(comp instanceof Checkbox){
				Checkbox c = (Checkbox)comp;
				c.setChecked(false);
				
			} else{
				throw new WrongValueException("Not supported component: " + comp + " during reset");
			}
		}
	}
	
	
	public static void buildCombobox(org.zkoss.zul.Combobox combobox, Map<?, String> labelValueMap, boolean nullValueAllow){
		
		if(combobox!=null){
			combobox.getItems().clear();
			
			if(nullValueAllow){
				combobox.appendChild(ComponentUtil.createNullValueComboitem());
			}
			Set<?> keys = labelValueMap.keySet();
			for(Object key : keys){
				Comboitem item = new Comboitem();
				item.setValue(key);
				item.setLabel(labelValueMap.get(key));
				combobox.appendChild(item);
			}
			combobox.setSelectedIndex(0);
		}
	}
	

	public static void buildListbox(org.zkoss.zul.Listbox listbox, Map<?, String> labelValueMap, boolean nullValueAllow){
		
		if(listbox!=null){
			listbox.getItems().clear();
			
			if(nullValueAllow){
				listbox.appendChild(ComponentUtil.createNullValueListItem());
			}
			Set<?> keys = labelValueMap.keySet();
			for(Object key : keys){
				Listitem item = new Listitem();
				item.setValue(key);
				item.setLabel(labelValueMap.get(key));
				listbox.appendChild(item);
			}
			listbox.setSelectedIndex(0);
		}
	}

	public static void fillNullValueDatebox(org.zkoss.zul.Datebox datebox1, org.zkoss.zul.Datebox datebox2){
		if(datebox1.getValue()!=null && datebox2.getValue()==null)
			datebox2.setValue(datebox1.getValue());
		else if(datebox1.getValue()==null && datebox2.getValue()!=null)
			datebox1.setValue(datebox2.getValue());
	}
	
	
	public static boolean confirmBox(String message, String title) throws InterruptedException{
		return Messagebox.show(message, title, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK;
				
	}
	
	
	public static void showBox(String message, String title) throws InterruptedException{
		Messagebox.show(message, title, Messagebox.OK, Messagebox.INFORMATION);
				
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void toggelCheckAllBox(Checkbox checkAllBox, org.zkoss.zul.Listbox listBox) throws InterruptedException{
	
		Listhead head = listBox.getListhead();
	
		List<Listheader> listheaders = head.getChildren();
		int headerSize = listheaders.size();
		int checkAllBoxIndex = 0;
		for(int i=0; i<headerSize; i++){
			Component comp = listheaders.get(i).getFirstChild();
			if(comp instanceof Checkbox && comp== checkAllBox){
				checkAllBoxIndex = i;
				break;
			}
			
		}
		
		List<Object> items= listBox.getChildren();
		for(Object item: items){
			if(item instanceof Listitem){
				Listitem listItem = (Listitem)item;
				Listcell cell = (Listcell)listItem.getChildren().get(checkAllBoxIndex);
				Component comp = cell.getFirstChild();
				if(comp instanceof Checkbox){
					if (checkAllBox.isChecked()) {
						((Checkbox)comp).setChecked(true);
					} else {
						((Checkbox)comp).setChecked(false);
					}
					
					Events.sendEvent(new Event(Events.ON_CHECK, (Checkbox)comp));
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getSelectedItems(org.zkoss.zul.Listbox listbox){
		
		Set<Listitem> listItems = (Set<Listitem>)listbox.getSelectedItems();
		List<T> lists = Lists.newArrayList();
		for(Listitem item: listItems){
			lists.add((T)item.getValue());
		}
		
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSelectedItem(org.zkoss.zul.Listbox listbox){
		
		Listitem listItem = (Listitem)listbox.getSelectedItem();
		Object value = listItem.getValue();
		if(value==null || (value instanceof String && Strings.isNullOrEmpty((String)value))){
			return null;
		} else {
			return (T) value;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSelectedItem(Combobox combobox){
		
		Comboitem comboItem = combobox.getSelectedItem();
		if(comboItem!=null){
			Object value = comboItem.getValue();
			
			if(value==null || (value instanceof String && Strings.isNullOrEmpty((String)value))){
				return null;
			} else {
				return (T) value;
			}
		}
		
		return null;
	}
	
	
	
	public static int getItemIndex(Combobox box, Object value){
		@SuppressWarnings("unchecked")
		List<Comboitem> comboItems = box.getItems();
		int comboItemSize = comboItems.size();
		for(int i=0; i<comboItemSize; i++){
			Comboitem item = comboItems.get(i);
			if(value.equals(item.getValue())){
				return i;
			}
		}
		return -1;
	}

	public static void setSelectedItem(Combobox box, Object value){
		
		int index = getItemIndex(box, value);
		if(index !=-1){
			box.setSelectedIndex(index);
			Events.sendEvent(new Event(Events.ON_SELECT, (Combobox)box));
		}
		
	}
	
	
	public static int getItemIndex(org.zkoss.zul.Listbox box, Object value){
		@SuppressWarnings("unchecked")
		List<Listitem> listItems = box.getItems();
		int listItemSize = listItems.size();
		for(int i=0; i<listItemSize; i++){
			Listitem item = listItems.get(i);
	
			if(value==item.getValue() || value.equals(item.getValue())){
				return i;
			}
		}
		return -1;
	}
	
	public static void setSelectedItem(org.zkoss.zul.Listbox box, Object value){
		
		int index = getItemIndex(box, value);
		if(index !=-1){
			ComponentUtil.setSelectedIndex(box, index);
		}
		
	}
	
	
	public static void setSelectedIndex(org.zkoss.zul.Listbox box, int index){
		box.setSelectedIndex(index);
		Events.sendEvent(new Event(Events.ON_SELECT, (Listbox)box));
		
	}
	
	
	public static void setChecked(org.zkoss.zul.Checkbox box, boolean value){
		box.setChecked(value);
		Events.sendEvent(new Event(Events.ON_CHECK, (Checkbox)box));
		
	}
	
	public static void reloadListModelList(ListModelList model){
		
		@SuppressWarnings("rawtypes")
		List list = model.getInnerList();
		int listSize = list.size();
		for(int i=0; i<listSize; i++){
			model.set(i, list.get(i));
		}

	}
	

	
	
}
