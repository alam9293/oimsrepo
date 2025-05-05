package com.cdgtaxi.ibs.admin.ui;

import java.sql.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.GiroRequestExistanceException;
import com.cdgtaxi.ibs.common.model.IttbGiroReturnReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class EditGiroReturnRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditGiroReturnRequestWindow.class);
	private IttbGiroReturnReq request;
	private Label requestNoLabel, statusLabel, returnFileLabel; 
	private Listbox processingTimeLB;
	private Datebox processingDateDB;
	
	@SuppressWarnings("rawtypes")
	public EditGiroReturnRequestWindow(){
		Map params = Executions.getCurrent().getArg();
		request = (IttbGiroReturnReq)params.get("request");
	}
	
	private void populateTime(){
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			
			if(request.getProcessingTime() == Integer.parseInt(time))
				item.setSelected(true);
			
			processingTimeLB.appendChild(item);
		}
		if(processingTimeLB.getSelectedItem()==null) processingTimeLB.setSelectedIndex(0);
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		statusLabel.setValue(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(request.getStatus()));
		requestNoLabel.setValue(request.getReqNo().toString());
		
		this.populateTime();
		
		processingDateDB.setValue(DateUtil.convertSqlDateToUtilDate(request.getProcessingDate()));
		returnFileLabel.setValue(request.getIttbGiroUobHeader().getReturnFileName());
	}
	
	public void save() throws InterruptedException{
		logger.info("");
		
		try{
			
			Date processingDate = DateUtil.convertUtilDateToSqlDate(processingDateDB.getValue());
			int processingTime = Integer.parseInt((String)processingTimeLB.getSelectedItem().getValue());
			
			request.setProcessingDate(processingDate);
			request.setProcessingTime(processingTime);
			
			this.businessHelper.getGenericBusiness().update(request, getUserLoginIdAndDomain());
			
			Messagebox.show("Request("+request.getReqNo()+") successfully updated", "Edit GIRO Return Request", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(GiroRequestExistanceException bree){
			Messagebox.show(bree.getMessage(), 
					"Giro Request Existance Error", Messagebox.OK, Messagebox.ERROR);
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
