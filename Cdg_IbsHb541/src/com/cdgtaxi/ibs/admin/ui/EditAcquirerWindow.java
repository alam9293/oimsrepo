package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class EditAcquirerWindow extends CommonWindow implements AfterCompose {
	
	private MstbAcquirer acquirer;
	private CapsTextbox nameTextBox;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
	lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;
	
	@SuppressWarnings("unchecked")
	public EditAcquirerWindow(){
		Map map = Executions.getCurrent().getArg();
		Integer acquirerNo = (Integer)map.get("acquirerNo");		
		acquirer = (MstbAcquirer)this.businessHelper.getGenericBusiness().get(MstbAcquirer.class, acquirerNo);
	}
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		nameTextBox.setValue(acquirer.getName());
		
		if(acquirer.getCreatedDt()!=null)createdByLabel.setValue(acquirer.getCreatedBy());
		else createdByLabel.setValue("-");
		if(acquirer.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(acquirer.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(acquirer.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(acquirer.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(acquirer.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(acquirer.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(acquirer.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(acquirer.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(acquirer.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(acquirer.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	@SuppressWarnings("unchecked")
	public void saveAcquirer() throws InterruptedException{
		try{
			this.displayProcessing();
			
			String name = nameTextBox.getValue();
			
			MstbAcquirer acquirerExample = new MstbAcquirer();
			acquirerExample.setName(name);
			List<MstbAcquirer> result = this.businessHelper.getGenericBusiness().getByExample(acquirerExample);
			if(result.isEmpty()==false)
				if(result.get(0).getAcquirerNo().intValue() != acquirer.getAcquirerNo().intValue())
					throw new WrongValueException(nameTextBox, "Name has been used.");
			
			acquirer.setName(name);		
			this.businessHelper.getGenericBusiness().update(acquirer, getUserLoginIdAndDomain());
			
			//Show result
			Messagebox.show("Acquirer updated.", "Edit Acquirer", Messagebox.OK, Messagebox.INFORMATION);
			
			//Executions.getCurrent().sendRedirect("");
			this.back();
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
