package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditAcquirerMdrWindow extends CommonWindow implements AfterCompose {

	private Listbox acquirerTypeListBox;
	private Decimalbox rateDecimalBox;
	private Datebox effDateDateBox;
	MstbAcquirerMdr acquirerMdr;
	private Label createdByLabel, createdDateLabel,createdTimeLabel, lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Map map = Executions.getCurrent().getArg();
		Integer mdrNo = (Integer)map.get("mdrNo");
		acquirerMdr = this.businessHelper.getAdminBusiness().getAcquirerMdr(mdrNo);

		List<MstbAcquirer> acquirerTypes = this.businessHelper.getAdminBusiness().getAcquirer();
		for(MstbAcquirer acquirerType : acquirerTypes){
			Listitem item = new Listitem();
			item.setValue(acquirerType);
			item.setLabel(acquirerType.getName());
			
			if(acquirerMdr.getMstbAcquirer()!=null)
				if(acquirerType.getAcquirerNo().equals(acquirerMdr.getMstbAcquirer().getAcquirerNo()))
					item.setSelected(true);
			acquirerTypeListBox.appendChild(item);
		}
		//default selection if none is found
		if(acquirerTypeListBox.getSelectedItem()==null) acquirerTypeListBox.setSelectedIndex(0);
		rateDecimalBox.setValue(acquirerMdr.getRate());
		effDateDateBox.setValue(DateUtil.convertSqlDateToUtilDate(acquirerMdr.getEffectiveDate()));
		
		if(acquirerMdr.getCreatedBy()!=null) createdByLabel.setValue(acquirerMdr.getCreatedBy());
		else createdByLabel.setValue("-");
		if(acquirerMdr.getCreatedDt()!=null) createdDateLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(acquirerMdr.getCreatedDt()!=null) createdTimeLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		
		if(acquirerMdr.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(acquirerMdr.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(acquirerMdr.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(acquirerMdr.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(acquirerMdr.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	public void save() throws InterruptedException{
		try{
			this.displayProcessing();
			
			acquirerMdr.setMstbAcquirer((MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue());
			acquirerMdr.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effDateDateBox.getValue()));
			acquirerMdr.setRate(rateDecimalBox.getValue());
			
			if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(acquirerMdr)){
				Messagebox.show("Duplicate record founded.", "Edit Acquirer MDR", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				this.businessHelper.getGenericBusiness().update(acquirerMdr, getUserLoginIdAndDomain());
				//Show result
				Messagebox.show("Acquirer MDR updated.", "Edit Acquirer MDR", Messagebox.OK, Messagebox.INFORMATION);
				
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
}
