package com.cdgtaxi.ibs.billgen.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBillGenError;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ViewBillGenRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewBillGenRequestWindow.class);
	private static final String SELF = "viewBillGenRequestWindow";
	private BmtbBillGenReq request;
	
	public ViewBillGenRequestWindow(){
		Map params = Executions.getCurrent().getArg();
		Integer requestNo = (Integer)params.get("requestNo");
		List<BmtbBillGenReq> requests = this.businessHelper.getBillGenBusiness().searchRequest(requestNo, null, null, null, null, null);
		request = requests.get(0);
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		((Label)this.getFellow("requestNoLabel")).setValue(request.getReqNo().toString());
		((Label)this.getFellow("statusLabel")).setValue(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS.get(request.getStatus()));
		((Label)this.getFellow("billGenSetupLabel")).setValue(NonConfigurableConstants.BILL_GEN_SETUP.get(request.getBmtbBillGenSetupBySetupNo().getSetupNo()));
		if(request.getBmtbBillGenReq()!=null)((Label)this.getFellow("regenRequestNoLabel")).setValue(request.getBmtbBillGenReq().getReqNo().toString());
		((Label)this.getFellow("billGenMonthLabel")).setValue(NonConfigurableConstants.CALENDAR_MONTH.get(new Integer(request.getBillGenMonth())));
		((Label)this.getFellow("requestDateLabel")).setValue(DateUtil.convertDateToStr(request.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
		String billGenTime = request.getBillGenTime();
		billGenTime = billGenTime==null?"-":billGenTime;
		((Label)this.getFellow("billGenTimeLabel")).setValue(billGenTime);
		
		if(request.getStatus().equals(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_ERROR)){
			if(request.getBmtbBillGenSetupBySetupNo().getSetupNo().intValue()!=NonConfigurableConstants.BILL_GEN_SETUP_DRAFT)
				this.getFellow("regenButton").setVisible(true);
			this.getFellow("errorTableDiv").setVisible(true);
			Listbox errorList = (Listbox)this.getFellow("errorList");
			
			List<BmtbBillGenError> accountWithErrorList = this.businessHelper.getBillGenBusiness().listAccountsWithError(request);
			if(accountWithErrorList.size()>0){
				if(errorList.getListfoot()!=null)
					errorList.removeChild(errorList.getListfoot());
				
				for(BmtbBillGenError error : accountWithErrorList){
					Listitem item = new Listitem();
					item.setValue(error);
					item.appendChild(newListcell(new Integer(error.getAmtbAccountByTopLevelAccountNo().getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(error.getAmtbAccountByBillableAccountNo().getAccountName()));
					item.appendChild(newListcell(error.getErrorMsg()));
					item.appendChild(newListcell(error.getVdFlag()));
					item.appendChild(newListcell(error.getRwdFlag()));
					errorList.appendChild(item);
				}
			}
		}
		
		if(!request.getStatus().equals(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING)) ((Button)this.getFellow("deleteButton")).setVisible(false);
		if(request.getBmtbBillGenReq()!=null) ((Button)this.getFellow("deleteButton")).setVisible(false);
		if(!request.getStatus().equals(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING)) ((Button)this.getFellow("rescheduleButton")).setVisible(false);
		if(request.getStatus().equals(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING))
			if(request.getBmtbBillGenSetupBySetupNo().getSetupNo().intValue()==NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC.intValue() ||
					request.getBmtbBillGenSetupBySetupNo().getSetupNo().intValue()==NonConfigurableConstants.BILL_GEN_SETUP_DRAFT.intValue() )
				((Button)this.getFellow("rescheduleButton")).setVisible(false);
		
		if(request.getFmtbEntityMaster()!=null){
			Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
			((Label)this.getFellow("entityLabel")).setValue(ems.get(request.getFmtbEntityMaster().getEntityNo()));
		}
		else ((Label)this.getFellow("entityLabel")).setValue("-");
		
		if(!this.checkUriAccess(Uri.RESCHEDULE_BILL_GEN_REQUEST))
			((Button)this.getFellow("rescheduleButton")).setDisabled(true);
	}
	
	public void reschedule() throws InterruptedException{
		logger.info("");
		
		try{
			Map args = new HashMap();
			args.put("request", request);
			final Window win = (Window) Executions.createComponents(Uri.RESCHEDULE_BILL_GEN_REQUEST, null, args);
			win.setMaximizable(false);
			win.doModal();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void clear(){
		((Intbox)this.getFellow("requestNoIntBox")).setText("");
		((Listbox)this.getFellow("statusListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("setupListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("forAccountListBox")).setSelectedIndex(0);
		((Datebox)this.getFellow("requestDateFromBox")).setText("");
		((Datebox)this.getFellow("requestDateToBox")).setText("");
	}
	
	public void delete() throws InterruptedException{
		logger.info("");
		
		try{
			request.setStatus(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_DELETED);
			this.businessHelper.getGenericBusiness().update(request, getUserLoginIdAndDomain());
			Messagebox.show("Request has successfully marked as deleted", 
					"Delete Request", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
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
	
	@SuppressWarnings("unchecked")
	public void regen() throws InterruptedException{
		logger.info("");
		
		try{
			List<Integer> requestNos = new ArrayList<Integer>();
			if(Messagebox.show("You are about to create an ad hoc request to regen the current viewing request. Those listed accounts under the error list will be affected. Are you sure?", 
					"Regen Request", 
					Messagebox.OK | Messagebox.CANCEL, 
					Messagebox.QUESTION)==Messagebox.OK){
				Listbox errorList = (Listbox)this.getFellow("errorList");
				requestNos = this.businessHelper.getBillGenBusiness().regen(request, errorList.getItems(), getUserLoginIdAndDomain());
			}
			else return;
			Messagebox.show("Regen request("+requestNos+") successfully created", "Regen Request", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
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
