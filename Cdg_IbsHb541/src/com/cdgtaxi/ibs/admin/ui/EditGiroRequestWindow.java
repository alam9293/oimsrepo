package com.cdgtaxi.ibs.admin.ui;

import java.sql.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
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
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.IttbGiroReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class EditGiroRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditGiroRequestWindow.class);
	private IttbGiroReq request;
	private Label entityLabel, giroBankAcctLabel, requestNoLabel, statusLabel; 
	private Listbox requestTimeLB;
	private Datebox requestDateDB, valueDateDB, cutoffDateDB;
	
	@SuppressWarnings("rawtypes")
	public EditGiroRequestWindow(){
		Map params = Executions.getCurrent().getArg();
		request = (IttbGiroReq)params.get("request");
	}
	
	private void populateTime(){
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			
			if(request.getRequestTime() == Integer.parseInt(time))
				item.setSelected(true);
			
			requestTimeLB.appendChild(item);
		}
		if(requestTimeLB.getSelectedItem()==null) requestTimeLB.setSelectedIndex(0);
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		FmtbEntityMaster entity = request.getIttbGiroSetup().getFmtbEntityMaster();
		entityLabel.setValue(entity.getEntityName());
		FmtbBankCode entityBank = this.businessHelper.getAdminBusiness().getLatestEntityBank(entity);
		if(entityBank!=null){
			String value = entityBank.getBankName();
			if(entityBank.getBranchName().length()>0)
				value += (" - " + entityBank.getBranchName());
			value += " ("+entityBank.getBankAcctNo()+")";
			giroBankAcctLabel.setValue(value);
			giroBankAcctLabel.setStyle("Color:Black");
		}
		
		statusLabel.setValue(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(request.getStatus()));
		requestNoLabel.setValue(request.getReqNo().toString());
		
		this.populateTime();
		
		requestDateDB.setValue(DateUtil.convertSqlDateToUtilDate(request.getRequestDate()));
		valueDateDB.setValue(DateUtil.convertSqlDateToUtilDate(request.getValueDate()));
		cutoffDateDB.setValue(DateUtil.convertSqlDateToUtilDate(request.getCutoffDate()));
	}
	
	public void save() throws InterruptedException{
		logger.info("");
		
		try{
			
			Date requestDate = DateUtil.convertUtilDateToSqlDate(requestDateDB.getValue());
			int requestTime = Integer.parseInt((String)requestTimeLB.getSelectedItem().getValue());
			Date valueDate = DateUtil.convertUtilDateToSqlDate(valueDateDB.getValue());
			Date cutoffDate = DateUtil.convertUtilDateToSqlDate(cutoffDateDB.getValue());
			
			// value date cannot be before request date
			if(valueDate.before(requestDate))
				throw new WrongValueException(valueDateDB, "Value Date cannot be before Request Date!");
			
			this.businessHelper.getAdminBusiness().updateGiroRequest(request, requestDate, requestTime, valueDate, cutoffDate, getUserLoginIdAndDomain());
			
			Messagebox.show("Request("+request.getReqNo()+") successfully updated", "Edit GIRO Request", Messagebox.OK, Messagebox.INFORMATION);
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
