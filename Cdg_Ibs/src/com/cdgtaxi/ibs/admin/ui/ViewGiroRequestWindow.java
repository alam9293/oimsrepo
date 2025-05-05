package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.IttbGiroReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewGiroRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewGiroRequestWindow.class);
	private Label entityLabel, giroBankAcctLabel, requestDateLabel, 
		requestTimeLabel, valueDateLabel, cutoffDateLabel, statusLabel, 
		requestNoLabel;
	private IttbGiroReq request;
	private Button editBtn, deleteBtn;
	
	@SuppressWarnings("rawtypes")
	public ViewGiroRequestWindow(){
		Map params = Executions.getCurrent().getArg();
		Long requestNo = (Long)params.get("requestNo");
		request = this.businessHelper.getAdminBusiness().searchGiroRequest(requestNo);
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		FmtbEntityMaster entity = request.getIttbGiroSetup().getFmtbEntityMaster();
		entityLabel.setValue(entity.getEntityName());
		FmtbBankCode entityBank = this.businessHelper.getAdminBusiness().getLatestEntityBank(entity);
		if(entityBank!=null){
			String value = entityBank.getBankAcctName();
			value += " ("+entityBank.getBankAcctNo()+")";
			giroBankAcctLabel.setValue(value);
			giroBankAcctLabel.setStyle("Color:Black");
		}
		
		requestNoLabel.setValue(request.getReqNo().toString());
		requestDateLabel.setValue(DateUtil.convertDateToStr(request.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
		valueDateLabel.setValue(DateUtil.convertDateToStr(request.getValueDate(), DateUtil.GLOBAL_DATE_FORMAT));
		cutoffDateLabel.setValue(DateUtil.convertDateToStr(request.getCutoffDate(), DateUtil.GLOBAL_DATE_FORMAT));
		requestTimeLabel.setValue(StringUtil.appendLeft(request.getRequestTime()+"", 2, "0")+":00");
		statusLabel.setValue(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(request.getStatus()));
		
		//Created By Section
		Label createdByLabel = (Label)this.getFellow("createdByLabel");
		Label createdDateLabel = (Label)this.getFellow("createdDateLabel");
		Label createdTimeLabel = (Label)this.getFellow("createdTimeLabel");
		
		createdByLabel.setValue(request.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertTimestampToStr(request.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertTimestampToStr(request.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		
		//Last Updated Section
		Label lastUpdatedByLabel = (Label)this.getFellow("lastUpdatedByLabel");
		Label lastUpdatedDateLabel = (Label)this.getFellow("lastUpdatedDateLabel");
		Label lastUpdatedTimeLabel = (Label)this.getFellow("lastUpdatedTimeLabel");
		
		if(request.getUpdatedDt()==null){
			lastUpdatedByLabel.setValue("-");
			lastUpdatedDateLabel.setValue("-");
			lastUpdatedTimeLabel.setValue("-");
		}
		else{
			lastUpdatedByLabel.setValue(request.getUpdatedBy());
			lastUpdatedDateLabel.setValue(DateUtil.convertTimestampToStr(request.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
			lastUpdatedTimeLabel.setValue(DateUtil.convertTimestampToStr(request.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		}
		
		if(request.getStatus().equals(NonConfigurableConstants.GIRO_REQUEST_STATUS_PENDING)){
			editBtn.setVisible(true);
			deleteBtn.setVisible(true);
		}
		if(!this.checkUriAccess(Uri.EDIT_GIRO_REQUEST))
			editBtn.setVisible(false);
		if(!this.checkUriAccess(Uri.DELETE_GIRO_REQUEST))
			deleteBtn.setVisible(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void edit() throws InterruptedException{
		logger.info("");
		
		try{
			//Retrieve selected value
			HashMap map = new HashMap();
			map.put("request", request);
			this.forward(Uri.EDIT_GIRO_REQUEST, map);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void delete() throws InterruptedException{
		try{
			if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
					Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
				return;
			}
			
			request.setStatus(NonConfigurableConstants.GIRO_REQUEST_STATUS_DELETED);
			this.businessHelper.getAdminBusiness().update(request, getUserLoginIdAndDomain());
			
			Messagebox.show("The GIRO request has been successfully deleted", "Delete GIRO Request",
					Messagebox.OK, Messagebox.INFORMATION);
			
			this.back();
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
		request = this.businessHelper.getAdminBusiness().searchGiroRequest(request.getReqNo());
		this.afterCompose();
	}
}
