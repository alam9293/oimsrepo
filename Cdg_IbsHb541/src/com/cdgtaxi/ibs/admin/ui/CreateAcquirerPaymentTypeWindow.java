package com.cdgtaxi.ibs.admin.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtType;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;

@SuppressWarnings("serial")
public class CreateAcquirerPaymentTypeWindow extends CommonWindow implements AfterCompose {
	private Listbox acquirerTypeListBox, valueTypeListBox;
	private List<Listitem> commissionTypeList;
	private Datebox effDateFromDateBox, effDateToDateBox;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		List<MstbAcquirer> acquirerList = this.businessHelper.getAdminBusiness().getAcquirer();
		for(MstbAcquirer acquirer : acquirerList){
			Listitem item = new Listitem();
			item.setValue(acquirer);
			item.setLabel(acquirer.getName());

			acquirerTypeListBox.appendChild(item);
		}
		acquirerTypeListBox.setSelectedIndex(0);
		List<Entry<String, String>> list = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
		for (Map.Entry<String, String> me : list) {
	    	Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			
			valueTypeListBox.appendChild(item);
	    }
		valueTypeListBox.setSelectedIndex(0);
		
		List<Entry<String, String>> commissionlist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.COMMISSION_TYPE).entrySet());
		commissionTypeList =  new ArrayList<Listitem>();
		for (Map.Entry<String, String> me : commissionlist) {
			commissionTypeList.add(new Listitem(me.getValue(), me.getKey()));
		}
	}
	public void create() throws InterruptedException{
		try{
			this.displayProcessing();
			
			MstbAcquirer acquirer = (MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue();
			String masterCode = (String)valueTypeListBox.getSelectedItem().getValue();
			MstbMasterTable mstbmt = ConfigurableConstants.getMasterTable(ConfigurableConstants.ACQUIRER_PYMT_TYPE, masterCode);
			
			Date effDateFrom = effDateFromDateBox.getValue();
			Date effDateTo = effDateToDateBox.getValue();
			
			if(effDateFrom==null)
				throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be empty.");
			if(effDateTo!=null)
			if(effDateFrom.compareTo(effDateTo) == 1)
				throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			
			MstbAcquirerPymtType acquirerPymtType = new MstbAcquirerPymtType();
			acquirerPymtType.setMstbMasterTable(mstbmt);
			acquirerPymtType.setMstbAcquirer(acquirer);
			acquirerPymtType.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(effDateFrom)));
			acquirerPymtType.setEffectiveDtTo(DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(effDateTo)));

			// Commission
			// Extracting inputs
			Rows commissionRows = (Rows)this.getFellow("commission");
			Map<Date, Map<String, Object>> commissionMap = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
				public int compare(Date o2, Date o1) {
					return o1.compareTo(o2);
				}
			});
			Map<Date, Map<String, Object>> commissionMapSubmit = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
				public int compare(Date o1, Date o2) {
					return o1.compareTo(o2);
				}
			});
			
			for(Object row : commissionRows.getChildren()){
				if(!row.equals(commissionRows.getLastChild())){
					Row commissionRow = (Row)row;
					Map<String, Object> commissionDetail = new HashMap<String, Object>();
					
					Datebox effectiveDate = (Datebox)commissionRow.getChildren().get(3);
					Decimalbox commissionValueBox = (Decimalbox)commissionRow.getChildren().get(1);
					Listbox commissionBox = (Listbox)commissionRow.getChildren().get(2);
					Listitem commissionTypeItem = commissionBox.getSelectedItem();
					
					if(commissionValueBox.getValue() == null){
						Messagebox.show("Please fill in all Commission value for Commission Details", "Create Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if(effectiveDate.getValue()==null){
						Messagebox.show("Please fill in all Commission effective dates for Commission Details", "Create Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if(effectiveDate.getValue().before(effDateFrom)){
						Messagebox.show("Commission Effective Date cannot be earlier than Payment Type Effective Date", "Create Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if(commissionMap.containsKey(effectiveDate.getValue())){
						Messagebox.show("Duplicate Commission effective dates in Commission Details", "Create Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
				
					commissionDetail.put("effectiveDateFrom", effectiveDate.getValue());
					commissionDetail.put("commissionValue", commissionValueBox.getValue());
					commissionDetail.put("commissionNo", commissionRow.getValue());
					commissionDetail.put("commissionType", commissionTypeItem.getValue());
					commissionMap.put(effectiveDate.getValue(), commissionDetail);
				}
			}
			
			Date prevDate = null;
			for(Map.Entry<Date, Map<String, Object>> entry :  commissionMap.entrySet())
			{
				Map<String, Object> commissionDetail = entry.getValue();
				
				if(prevDate != null)
					commissionDetail.put("effectiveDateTo", new java.sql.Date((DateUtil.addDaysToDate( -1 , prevDate)).getTime())); //minus 1 date of prev date
				
				prevDate = entry.getKey();
				commissionMapSubmit.put(entry.getKey(), commissionDetail);
			}
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(acquirerPymtType)){
				Messagebox.show("This payment type is already acquired by another bank for this effective date range.", "Create Acquirer Payment Type", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				this.businessHelper.getGenericBusiness().save(acquirerPymtType, getUserLoginIdAndDomain());
				this.businessHelper.getAdminBusiness().updateAcquirerPaymentType(acquirerPymtType, commissionMapSubmit, getUserLoginIdAndDomain());
				
				Messagebox.show("New Acquirer Payment Type created.", "Create Acquirer Payment Type", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
				
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
		
	}

	@SuppressWarnings("unchecked")
	public void addCommission(){
		Rows commissionRows = (Rows)this.getFellow("commission");
		Row commissionRow = new Row();
		commissionRow.appendChild(new Label(""+commissionRows.getChildren().size()));
	
		Decimalbox commissionValueBox = new Decimalbox();
		commissionValueBox.setWidth("90%");
		commissionValueBox.setFormat("#,##0.00");
		commissionRow.appendChild(commissionValueBox);
		
		Listbox commissionBox = new Listbox();
		commissionBox.setWidth("90%"); commissionBox.setRows(1); commissionBox.setMold("select");
		commissionBox.getItems().addAll(cloneList(commissionTypeList));
		commissionBox.setSelectedIndex(0);
		commissionRow.appendChild(commissionBox);
		
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("90%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		commissionRow.appendChild(effectiveDate);
		
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		ZScript showInfo = ZScript.parseContent("createAcquirerPaymentTypeWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		commissionRow.appendChild(deleteImage);
		
		commissionRows.insertBefore(commissionRow, commissionRows.getLastChild());
	}
	public void deleteRow(Row row){
		Rows rows = (Rows)row.getParent();
		rows.removeChild(row);
		// now renumber the rows
		for(int i=1;i<rows.getChildren().size();i++){
			Row tempRow = (Row)rows.getChildren().get(i-1);
			((Label)tempRow.getFirstChild()).setValue(i + "");
		}
	}
	private java.sql.Date convertToSQLDate(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(calendar.getTimeInMillis());
	}
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			if(listitem.isSelected()){
				Listitem newListitem = new Listitem(listitem.getLabel(), listitem.getValue());
				newListitem.setSelected(true);
				returnList.add(newListitem);
			}else{
				returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
			}
		}
		return returnList;
	}
}
