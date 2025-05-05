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
import com.cdgtaxi.ibs.common.model.IttbGiroReq;
import com.cdgtaxi.ibs.common.model.IttbGiroReturnReq;
import com.cdgtaxi.ibs.common.model.forms.SearchGiroReturnRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class ManageGiroReturnRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageGiroRequestWindow.class);
	private Longbox requestNoLongBox;
	private Listbox processingTimeLB, statusLB, resultList;
	private Datebox processingDateDB;
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		//populate status listbox
		statusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.GIRO_REQUEST_STATUS.keySet()){
			statusLB.appendChild(new Listitem(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(key), key));
		}
		
		this.populateTime();
	}
	
	private void populateTime(){
		processingTimeLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			
			processingTimeLB.appendChild(item);
		}
		if(processingTimeLB.getSelectedItem()==null) processingTimeLB.setSelectedIndex(0);
	}
	
	public void searchRequest() throws InterruptedException{
		logger.info("");
		
		try{
			SearchGiroReturnRequestForm form = new SearchGiroReturnRequestForm();
			form.requestNo = requestNoLongBox.getValue();
			form.processingDate = processingDateDB.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(processingDateDB.getValue());
			form.processingTime = ((String)processingTimeLB.getSelectedItem().getValue()).length() == 0 ? null : Integer.valueOf((String)processingTimeLB.getSelectedItem().getValue());
			form.status = ((String)statusLB.getSelectedItem().getValue()).length() == 0 ? null : (String)statusLB.getSelectedItem().getValue();
			
			this.displayProcessing();
			
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultListBox.getItems().clear();
			
			List<IttbGiroReturnReq> requests = this.businessHelper.getAdminBusiness().searchGiroReturnRequest(form);
			if(requests.size()>0){
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(IttbGiroReturnReq request : requests){
					Listitem item = new Listitem();
					item.setValue(request);
					item.appendChild(newListcell(request.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(request.getProcessingDate()));
					item.appendChild(newListcell(StringUtil.appendLeft(request.getProcessingTime()+"", 2, "0")+":00"));
					item.appendChild(newListcell(NonConfigurableConstants.GIRO_REQUEST_STATUS.get(request.getStatus())));
					item.appendChild(newListcell(request.getIttbGiroUobHeader().getReturnFileName()));
					
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
			IttbGiroReturnReq request = (IttbGiroReturnReq)selectedItem.getValue();
			
			HashMap map = new HashMap();
			map.put("requestNo", request.getReqNo());
			this.forward(Uri.VIEW_GIRO_RETURN_REQUEST, map);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void clear(){
		requestNoLongBox.setText("");
		processingTimeLB.setSelectedIndex(0);
		statusLB.setSelectedIndex(0);
		processingDateDB.setText("");
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		if(resultListBox.getListfoot()==null){
			resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
		}
		resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultListBox.setPageSize(5);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.searchRequest();
	}
}
