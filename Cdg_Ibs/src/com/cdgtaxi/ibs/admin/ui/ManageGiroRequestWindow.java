package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.IttbGiroReq;
import com.cdgtaxi.ibs.common.model.forms.SearchGiroRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class ManageGiroRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageGiroRequestWindow.class);
	private static final String SELF = "searchGiroRequestWindow";
	private Longbox requestNoLongBox;
	private Listbox entityLB, timeLB, statusLB, resultList;
	private Datebox requestDateDB, valueDateDB, cutoffDateDB;
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		List<FmtbEntityMaster> entities = this.businessHelper.getAdminBusiness().getEntities();
		entityLB.appendChild(ComponentUtil.createNotRequiredListItem(null));
		for(FmtbEntityMaster entity: entities){
			entityLB.appendChild(new Listitem(entity.getEntityName(), entity));
		}
		if(entityLB.getSelectedItem()==null) entityLB.setSelectedIndex(0);
		
		//populate status listbox
		statusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.GIRO_REQUEST_STATUS.keySet()){
			statusLB.appendChild(new Listitem(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(key), key));
		}
		
		this.populateTime();
	}
	
	private void populateTime(){
		timeLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			
			timeLB.appendChild(item);
		}
		if(timeLB.getSelectedItem()==null) timeLB.setSelectedIndex(0);
	}
	
	public void searchRequest() throws InterruptedException{
		logger.info("");
		
		try{
			SearchGiroRequestForm form = new SearchGiroRequestForm();
			form.requestNo = requestNoLongBox.getValue();
			form.entity = (FmtbEntityMaster)entityLB.getSelectedItem().getValue();
			form.requestDate = requestDateDB.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(requestDateDB.getValue());
			form.requestTime = ((String)timeLB.getSelectedItem().getValue()).length() == 0 ? null : Integer.valueOf((String)timeLB.getSelectedItem().getValue());
			form.valueDate = valueDateDB.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(valueDateDB.getValue());
			form.cutoffDate = cutoffDateDB.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(cutoffDateDB.getValue());
			form.status = ((String)statusLB.getSelectedItem().getValue()).length() == 0 ? null : (String)statusLB.getSelectedItem().getValue();
			
			this.displayProcessing();
			
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultListBox.getItems().clear();
			
			List<IttbGiroReq> requests = this.businessHelper.getAdminBusiness().searchGiroRequest(form);
			if(requests.size()>0){
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(IttbGiroReq request : requests){
					
					FmtbBankCode entityBank = this.businessHelper.getAdminBusiness().getLatestEntityBank(request.getIttbGiroSetup().getFmtbEntityMaster());
					String giroBankAccount = "";
					if(entityBank!=null){
						giroBankAccount = entityBank.getBankAcctName();
						giroBankAccount += " ("+entityBank.getBankAcctNo()+")";
					}
					
					Listitem item = new Listitem();
					item.setValue(request);
					item.appendChild(newListcell(request.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(request.getIttbGiroSetup().getFmtbEntityMaster().getEntityName()));
					item.appendChild(newListcell(giroBankAccount));
					item.appendChild(newListcell(request.getRequestDate()));
					item.appendChild(newListcell(StringUtil.appendLeft(request.getRequestTime()+"", 2, "0")+":00"));
					item.appendChild(newListcell(request.getValueDate()));
					item.appendChild(newListcell(request.getCutoffDate()));
					item.appendChild(newListcell(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(request.getStatus())));
					
					resultListBox.appendChild(item);
				}
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
				}
			}
			
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(10);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void viewRequest() throws InterruptedException{
		logger.info("");
		
		try{
			//Retrieve selected value
			Listitem selectedItem = resultList.getSelectedItem();
			IttbGiroReq request = (IttbGiroReq)selectedItem.getValue();
			
			HashMap map = new HashMap();
			map.put("requestNo", request.getReqNo());
			this.forward(Uri.VIEW_GIRO_REQUEST, map);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void clear(){
		requestNoLongBox.setText("");
		entityLB.setSelectedIndex(0);
		timeLB.setSelectedIndex(0);
		statusLB.setSelectedIndex(0);
		requestDateDB.setText("");
		valueDateDB.setText("");
		cutoffDateDB.setText("");
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		if(resultListBox.getListfoot()==null){
			resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
		}
		resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultListBox.setPageSize(10);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.searchRequest();
	}
}
