package com.cdgtaxi.ibs.billgen.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.BillGenRequestExistanceException;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class RescheduleBillGenRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RescheduleBillGenRequestWindow.class);
	private BmtbBillGenReq request;
	private Datebox newRequestDateBox;
	private Listbox billGenTimeListbox;
	
	public RescheduleBillGenRequestWindow(){
		Map params = Executions.getCurrent().getArg();
		BmtbBillGenReq request = (BmtbBillGenReq)params.get("request");
		this.request = request;
	}
	
	public void afterCompose() {
		newRequestDateBox = (Datebox)this.getFellow("newRequestDateBox");
		newRequestDateBox.setValue(request.getRequestDate());
		
		//Populate time list
		billGenTimeListbox = (Listbox)this.getFellow("billGenTimeListbox");
		Integer selectedTime = new Integer(request.getBmtbBillGenSetupBySetupNo().getBillGenTime().substring(0, 2));
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			if(i==selectedTime.intValue()) item.setSelected(true);
			billGenTimeListbox.appendChild(item);
		}
		if(billGenTimeListbox.getSelectedItem()==null) billGenTimeListbox.setSelectedIndex(0);
	}
	
	public void cancel(){
		this.detach();
	}
	
	public void ok() throws InterruptedException{
		logger.info("");
		
		try{
			Date newRequestDate = DateUtil.convertUtilDateToSqlDate(newRequestDateBox.getValue());
			Calendar currentCalendar = Calendar.getInstance();
			Date currentDate = DateUtil.convertDateTo0000Hours(DateUtil.getCurrentDate());
			
			String time = (String)billGenTimeListbox.getSelectedItem().getValue();
			int selectedHours = new Integer(time);
			
			if(newRequestDate.compareTo(currentDate) < 0){
				Messagebox.show("New request date must be a future date", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			if(newRequestDate.compareTo(currentDate) == 0){
				//same date then check for time
				int hours = currentCalendar.get(Calendar.HOUR_OF_DAY);
				if(selectedHours <= hours){
					Messagebox.show("New bill gen time must be a future time", 
							"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			
			List<BmtbBillGenSetup> billGenSetups = new ArrayList<BmtbBillGenSetup>();
			BmtbBillGenSetup billGenSetupAdHoc = (BmtbBillGenSetup)this.businessHelper.getGenericBusiness().get(BmtbBillGenSetup.class, NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC);
			if(billGenSetupAdHoc==null)
				throw new Exception("Unable to find ad hoc setup object");
			billGenSetups.add(billGenSetupAdHoc);
			billGenSetups.add(request.getBmtbBillGenSetupBySetupNo());
			
			boolean isRequestExist = this.businessHelper.getBillGenBusiness().checkRequestExist(billGenSetups, newRequestDate, this.request.getFmtbEntityMaster()!=null?this.request.getFmtbEntityMaster().getEntityNo():null, request.getReqNo());
			if(isRequestExist)
				throw new BillGenRequestExistanceException();
			
			request.setRequestDate(newRequestDate);
			request.setBillGenTime(time+":00");
			this.businessHelper.getGenericBusiness().update(request, getUserLoginIdAndDomain());
			
			Messagebox.show("Request has successfully rescheduled", 
					"Reschedule Request", Messagebox.OK, Messagebox.INFORMATION);
			
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(BillGenRequestExistanceException bree){
			Messagebox.show(bree.getMessage(), 
					"Bill Gen Request Existance Error", Messagebox.OK, Messagebox.ERROR);
			return;
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
	public void refresh() {
		
	}
}
