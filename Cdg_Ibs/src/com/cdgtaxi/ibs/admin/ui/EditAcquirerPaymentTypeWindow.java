package com.cdgtaxi.ibs.admin.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
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
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtComm;
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtType;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;

@SuppressWarnings("serial")
public class EditAcquirerPaymentTypeWindow extends CommonWindow implements AfterCompose {

	MstbAcquirerPymtType acquirerPymtType;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Listbox acquirerTypeListBox,valueTypeListBox;
	private List<Listitem> commissionTypeList;
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		Map map = Executions.getCurrent().getArg();
		Integer pymtTypeNo = (Integer)map.get("pymtTypeNo");
		acquirerPymtType = this.businessHelper.getAdminBusiness().getAcquirerPaymentType(pymtTypeNo);

		if(acquirerPymtType.getCreatedBy()!=null)createdByLabel.setValue(acquirerPymtType.getCreatedBy());
		else createdByLabel.setValue("-");
		if(acquirerPymtType.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(acquirerPymtType.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(acquirerPymtType.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(acquirerPymtType.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(acquirerPymtType.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(acquirerPymtType.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(acquirerPymtType.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(acquirerPymtType.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(acquirerPymtType.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(acquirerPymtType.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		effDateFromDateBox.setValue(DateUtil.convertTimestampToUtilDate(acquirerPymtType.getEffectiveDtFrom()));
		if(effDateFromDateBox.getValue().compareTo(DateUtil.getCurrentDate()) <= 0){
			effDateFromDateBox.setDisabled(true);
			acquirerTypeListBox.setDisabled(true);
			valueTypeListBox.setDisabled(true);
		}
		else
			effDateFromDateBox.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		
		effDateToDateBox.setValue(DateUtil.convertTimestampToUtilDate(acquirerPymtType.getEffectiveDtTo()));
		List<MstbAcquirer> acquirerList = this.businessHelper.getAdminBusiness().getAcquirer();
		for(MstbAcquirer acquirer : acquirerList){
			Listitem item = new Listitem();
			item.setValue(acquirer);
			item.setLabel(acquirer.getName());
			
			if(acquirerPymtType.getMstbAcquirer().getName()!=null)
				if(acquirer.getAcquirerNo().equals(acquirerPymtType.getMstbAcquirer().getAcquirerNo()))
					item.setSelected(true);
			
			acquirerTypeListBox.appendChild(item);
		}
		//default selection if none is found
		if(acquirerTypeListBox.getSelectedItem()==null) acquirerTypeListBox.setSelectedIndex(0);
		
		List<Entry<String, String>> list = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
		for (Map.Entry<String, String> me : list) {
	    	Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			
			if(acquirerPymtType.getMstbMasterTable().getMasterCode()!=null)
				if(acquirerPymtType.getMstbMasterTable().getMasterCode().equals(me.getKey()))
					item.setSelected(true);
			
			valueTypeListBox.appendChild(item);
	    }
		//default selection if none is found
		if(valueTypeListBox.getSelectedItem()==null) valueTypeListBox.setSelectedIndex(0);
		
		List<Entry<String, String>> commissionlist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.COMMISSION_TYPE).entrySet());
		commissionTypeList =  new ArrayList<Listitem>();
		for (Map.Entry<String, String> me : commissionlist) {
			commissionTypeList.add(new Listitem(me.getValue(), me.getKey()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{
		
		// commission
		Rows commissionRows = (Rows)this.getFellow("commission");
		while(commissionRows.getChildren().size()!=1){
			commissionRows.removeChild(commissionRows.getFirstChild());
		}
		
		List<MstbAcquirerPymtComm> pymtCommList = new ArrayList<MstbAcquirerPymtComm>();
		pymtCommList.addAll(acquirerPymtType.getMstbAcquirerPymtComm());
		Collections.sort(pymtCommList, new Comparator<MstbAcquirerPymtComm>() {
			public int compare(MstbAcquirerPymtComm o1,	MstbAcquirerPymtComm o2) {
				// TODO Auto-generated method stub
				return o1.getEffectiveDtFrom().compareTo(o2.getEffectiveDtFrom());
			}
		});
		
		for(MstbAcquirerPymtComm mstbAcquirerPymtComm : pymtCommList)
		{			
			boolean disabled = true;
			if(DateUtil.isToday(mstbAcquirerPymtComm.getEffectiveDtFrom()))
				disabled = true;
			else if(DateUtil.getCurrentDate().before(mstbAcquirerPymtComm.getEffectiveDtFrom()))
				disabled = false;

			Row commissionRow = new Row();
			commissionRow.setValue(mstbAcquirerPymtComm.getCommissionNo());
			commissionRow.appendChild(new Label(""+commissionRows.getChildren().size()));
			
			Decimalbox commissionValueBox = new Decimalbox();
			commissionValueBox.setWidth("90%");
			commissionValueBox.setFormat("#,##0.00");
			commissionValueBox.setValue(mstbAcquirerPymtComm.getCommissionValue());
			commissionValueBox.setDisabled(disabled);
			commissionRow.appendChild(commissionValueBox);
			
			Listbox commissionBox = new Listbox();
			commissionBox.setWidth("90%"); commissionBox.setRows(1); commissionBox.setMold("select");
			commissionBox.getItems().addAll(cloneList(commissionTypeList));
			commissionBox.setSelectedIndex(0);
			commissionBox.setDisabled(disabled);
			commissionRow.appendChild(commissionBox);
			if(mstbAcquirerPymtComm.getCommissionType()!=null){
				for(Object commissionItem : commissionBox.getItems()){
					if(mstbAcquirerPymtComm.getCommissionType().equals(((Listitem)commissionItem).getValue())){
						((Listitem)commissionItem).setSelected(true);
						break;
					}
				}
			}
			
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("90%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			effectiveDate.setValue(mstbAcquirerPymtComm.getEffectiveDtFrom());
			if(!disabled)
				effectiveDate.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
			effectiveDate.setDisabled(disabled);
			commissionRow.appendChild(effectiveDate);
			
			// adding x to row
			if(!disabled)
			{
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor: pointer");
				ZScript showInfo = ZScript.parseContent("editAcquirerPaymentTypeWindow.deleteRow(self.getParent())");
				showInfo.setLanguage("java");
				EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", pdEvent);
				commissionRow.appendChild(deleteImage);
			}
			else{
				Label emptyImageLabel = new Label();
				emptyImageLabel.setValue("");
				commissionRow.appendChild(emptyImageLabel);
			}
			commissionRows.insertBefore(commissionRow, commissionRows.getLastChild());
		}
	}
	
	public void save() throws InterruptedException{
		try{
			this.displayProcessing();
			
			MstbAcquirer acquirer = (MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue();
			String valueKey = (String)valueTypeListBox.getSelectedItem().getValue();
			Date effDateFrom = effDateFromDateBox.getValue();
			Date effDateTo = effDateToDateBox.getValue();
			
			if(effDateFrom==null)
				throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be empty.");
			if(effDateTo!=null)
			if(effDateFrom.compareTo(effDateTo) == 1)
				throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
				
			acquirerPymtType.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACQUIRER_PYMT_TYPE, valueKey));
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
					
					//Impossible for Commission Effective Date's Effective Date to be Earlier then Acquirer Pymt Effective Date.
					
					if(commissionValueBox.getValue() == null){
						Messagebox.show("Please fill in all Commission value for Commission Details", "Edit Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if(effectiveDate.getValue()==null){
						Messagebox.show("Please fill in all Commission effective dates for Commission Details", "Edit Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if(effectiveDate.getValue().before(effDateFrom)){
						Messagebox.show("Commission Effective Date cannot be earlier than Payment Type Effective Date", "Edit Commission", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if(commissionMap.containsKey(effectiveDate.getValue())){
						Messagebox.show("Duplicate Commission effective dates in Commission Details", "Edit Commission", Messagebox.OK, Messagebox.EXCLAMATION);
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
				Messagebox.show("Effective date range is not allowed.", "Edit Acquirer Payment Type", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				//nid split then wun tio the session object cant save & del
				this.businessHelper.getGenericBusiness().update(acquirerPymtType, getUserLoginIdAndDomain());
				this.businessHelper.getAdminBusiness().updateAcquirerPaymentType(acquirerPymtType, commissionMapSubmit, getUserLoginIdAndDomain());
				//Show result
				Messagebox.show("Acquirer Payment Type updated.", "Edit Acquirer Payment Type", Messagebox.OK, Messagebox.INFORMATION);
				
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
		ZScript showInfo = ZScript.parseContent("editAcquirerPaymentTypeWindow.deleteRow(self.getParent())");
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
