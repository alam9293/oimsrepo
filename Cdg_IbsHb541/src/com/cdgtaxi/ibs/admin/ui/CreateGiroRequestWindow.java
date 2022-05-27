package com.cdgtaxi.ibs.admin.ui;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

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

import com.cdgtaxi.ibs.common.exception.GiroRequestExistanceException;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class CreateGiroRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateGiroRequestWindow.class);
	private Listbox setupLB, requestTimeLB;
	private Label giroBankAcctLabel;
	private Datebox requestDateDB, valueDateDB, cutoffDateDB;
	
	private void populateTime(){
		if(setupLB.getSelectedItem()!=null){
			requestTimeLB.getChildren().clear();
			IttbGiroSetup setup = (IttbGiroSetup) setupLB.getSelectedItem().getValue();
			
			for(int i=0; i<=23; i++){
				String time = ""+i;
				if(time.length()==1) time = "0"+time;
				Listitem item = new Listitem(time);
				item.setValue(time);
				
				if(setup.getRequestTime() == Integer.parseInt(time))
					item.setSelected(true);
				
				requestTimeLB.appendChild(item);
			}
			if(requestTimeLB.getSelectedItem()==null) requestTimeLB.setSelectedIndex(0);
		}
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		//Populate setup listbox
		List<IttbGiroSetup> setups = this.businessHelper.getAdminBusiness().getActiveGiroSetup();
		for(IttbGiroSetup setup : setups){
			Listitem item = new Listitem(setup.getFmtbEntityMaster().getEntityName());
			item.setValue(setup);
			setupLB.appendChild(item);
		}
		if(setupLB.getChildren().size()>0)
			if(setupLB.getSelectedItem()==null) setupLB.setSelectedIndex(0);
		
		this.populateTime();
		this.populateGiroBankAccount();
		this.populateDates();
	}
	
	private void populateGiroBankAccount(){
		if(setupLB.getSelectedItem()!=null){
			IttbGiroSetup setup = (IttbGiroSetup) setupLB.getSelectedItem().getValue();
			FmtbBankCode entityBank = this.businessHelper.getAdminBusiness().getLatestEntityBank(setup.getFmtbEntityMaster());
			if(entityBank == null){
				giroBankAcctLabel.setValue("NO BANK ACCOUNT");
				giroBankAcctLabel.setStyle("Color:Red");
			}
			else{
				String value = entityBank.getBankAcctName();
				value += " ("+entityBank.getBankAcctNo()+")";
				giroBankAcctLabel.setValue(value);
				giroBankAcctLabel.setStyle("Color:Black");
			}
		}
	}
	
	private void populateDates(){
		if(setupLB.getSelectedItem()!=null){
			IttbGiroSetup setup = (IttbGiroSetup) setupLB.getSelectedItem().getValue();
			Calendar currentCalendar = Calendar.getInstance();
			
			//Request Day > Value Day = Value Day next month
			//Cutoff Day < Value Day = Cutoff Day next month
			//********************************************************
			int requestDay = setup.getRequestDay();
			int valueDay = setup.getValueDay();
			int cutoffDay = setup.getCutOffDay();
			
			//Request Date
			Calendar requestCalendar = Calendar.getInstance();
			
			if(currentCalendar.get(Calendar.DAY_OF_MONTH) > requestDay)
				requestCalendar.add(Calendar.MONTH, 1);
			int lastDayOfMonth = requestCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(lastDayOfMonth < requestDay) requestCalendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			else requestCalendar.set(Calendar.DAY_OF_MONTH, requestDay);
			
			requestDateDB.setValue(requestCalendar.getTime());
			
			
			Calendar valueCalendar = Calendar.getInstance();
			valueCalendar.setTime(requestCalendar.getTime());
			Calendar cutoffCalendar = Calendar.getInstance();
			cutoffCalendar.setTime(requestCalendar.getTime());
			
			if(cutoffDay < valueDay && requestDay > valueDay){
				valueCalendar.add(Calendar.MONTH, 1);
				cutoffCalendar.add(Calendar.MONTH, 2);
			}
			else if(cutoffDay < valueDay)
				cutoffCalendar.add(Calendar.MONTH, 1);
			else if(requestDay > valueDay){
				valueCalendar.add(Calendar.MONTH, 1);
				cutoffCalendar.add(Calendar.MONTH, 1);
			}
			
			//Calculate Value Date
			if(lastDayOfMonth < valueDay) valueCalendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			else valueCalendar.set(Calendar.DAY_OF_MONTH, valueDay);
			
			//Calculate Cutoff Date
			if(lastDayOfMonth < cutoffDay) cutoffCalendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			else cutoffCalendar.set(Calendar.DAY_OF_MONTH, cutoffDay);
			
			valueDateDB.setValue(valueCalendar.getTime());
			cutoffDateDB.setValue(cutoffCalendar.getTime());
		}
	}
	
	
	public void onSelectSetup(){
		this.populateGiroBankAccount();
		this.populateDates();
		this.populateTime();
	}
	
	public void createRequest() throws InterruptedException{
		logger.info("");
		
		try{
			if(setupLB.getSelectedItem()==null){
				Messagebox.show("GIRO Setup is mandatory!", "Create GIRO Request", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			IttbGiroSetup setup = (IttbGiroSetup) this.setupLB.getSelectedItem().getValue();
			
			FmtbBankCode entityBank = this.businessHelper.getAdminBusiness().getLatestEntityBank(setup.getFmtbEntityMaster());
			if(entityBank == null || entityBank.getBankAcctNo() == null || entityBank.getBankAcctNo().length()==0){
				Messagebox.show("GIRO Bank Account must be setup first before the request can be requested!", "Create GIRO Request", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Date requestDate = DateUtil.convertUtilDateToSqlDate(requestDateDB.getValue());
			int requestTime = Integer.parseInt((String)requestTimeLB.getSelectedItem().getValue());
			Date valueDate = DateUtil.convertUtilDateToSqlDate(valueDateDB.getValue());
			Date cutoffDate = DateUtil.convertUtilDateToSqlDate(cutoffDateDB.getValue());
			
			// value date cannot be before request date
			if(valueDate.before(requestDate))
				throw new WrongValueException(valueDateDB, "Value Date cannot be before Request Date!");
			
			Long requestNo = this.businessHelper.getAdminBusiness().createGiroRequest(setup, requestDate, requestTime, valueDate, cutoffDate, getUserLoginIdAndDomain());
			
			Messagebox.show("New request("+requestNo+") successfully created", "Create GIRO Request", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
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
