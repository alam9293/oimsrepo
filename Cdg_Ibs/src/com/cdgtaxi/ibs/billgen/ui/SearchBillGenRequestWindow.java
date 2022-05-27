package com.cdgtaxi.ibs.billgen.ui;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchBillGenRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchBillGenRequestWindow.class);
	private static final String SELF = "searchBillGenRequestWindow";
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		//populate bill gen setup listbox
		Listbox setupListBox = (Listbox)this.getFellow("setupListBox");
		Listitem listItem =  new Listitem("-", null);
		listItem.setSelected(true);
		setupListBox.appendChild(listItem);
		for(Integer key : NonConfigurableConstants.BILL_GEN_SETUP.keySet()){
			setupListBox.appendChild(new Listitem(NonConfigurableConstants.BILL_GEN_SETUP.get(key), key));
		}
		
		//populate status listbox
		Listbox statusListBox = (Listbox)this.getFellow("statusListBox");
		statusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.BILL_GEN_REQUEST_STATUS.keySet()){
			statusListBox.appendChild(new Listitem(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS.get(key), key));
		}
		
		//populate entity list
		Listbox entityListBox = (Listbox)this.getFellow("entityListBox");
		Listitem listItem2 =  new Listitem("-", null);
		listItem2.setSelected(true);
		entityListBox.appendChild(listItem2);
		Map<Integer, String> ems = MasterSetup.getEntityManager().getAllMasters();
		for(Integer em : ems.keySet()){
			entityListBox.appendChild(new Listitem(ems.get(em), em));
		}
		if(entityListBox.getChildren().size()>0) entityListBox.setSelectedIndex(0);
	}
	
	public void searchRequest() throws InterruptedException{
		logger.info("");
		
		try{
			Intbox requestNoIntBox = (Intbox)this.getFellow("requestNoIntBox");
			Listbox statusListBox = (Listbox)this.getFellow("statusListBox");
			Listbox setupListBox = (Listbox)this.getFellow("setupListBox");
			Datebox requestDateFromBox = (Datebox)this.getFellow("requestDateFromBox");
			Datebox requestDateToBox = (Datebox)this.getFellow("requestDateToBox");
			Listbox entityListBox = (Listbox)this.getFellow("entityListBox");
			
			Integer requestNo = requestNoIntBox.getValue();
			String status = (String)statusListBox.getSelectedItem().getValue();
			Integer setupNo = (Integer)setupListBox.getSelectedItem().getValue();
			Date requestDateFrom = requestDateFromBox.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(requestDateFromBox.getValue());
			Date requestDateTo = requestDateToBox.getValue()==null ? null : DateUtil.convertUtilDateToSqlDate(requestDateToBox.getValue());
			Integer entityNo = (Integer)entityListBox.getSelectedItem().getValue();
			
			if(requestDateFrom!=null && requestDateTo==null)
				requestDateTo = requestDateFrom;
			
			if(requestNo!=null || (status!=null && status.length()>0) || setupNo!=null ||
					requestDateFrom!=null || requestDateTo!=null || entityNo!=null){
			}
			else{
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			if(requestDateFrom!=null && requestDateTo!=null){
				if(requestDateTo.compareTo(requestDateFrom)<0){
					Messagebox.show("Request Date To cannot be earlier than Request Date From", 
						"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
				
			this.displayProcessing();
			
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultListBox.getItems().clear();
			
			List<BmtbBillGenReq> requests = this.businessHelper.getBillGenBusiness().searchRequest(requestNo, status, setupNo, requestDateFrom, requestDateTo, entityNo);
			if(requests.size()>0){
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(BmtbBillGenReq request : requests){
					Listitem item = new Listitem();
					item.setValue(request);
					item.appendChild(newListcell(request.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS.get(request.getStatus())));
					item.appendChild(newListcell(NonConfigurableConstants.BILL_GEN_SETUP.get(request.getBmtbBillGenSetupBySetupNo().getSetupNo())));
					Listcell regenRequestNoListCell = new Listcell();
					regenRequestNoListCell.setLabel("-");
					regenRequestNoListCell.setValue(new Integer(0));
					if(request.getBmtbBillGenReq()!=null){
						regenRequestNoListCell.setLabel(request.getBmtbBillGenReq().getReqNo().toString());
						regenRequestNoListCell.setValue(request.getBmtbBillGenReq().getReqNo());
					}
					item.appendChild(regenRequestNoListCell);
					item.appendChild(newListcell(request.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(request.getBillGenTime()==null?"-":request.getBillGenTime()));
					item.appendChild(newListcell(request.getFmtbEntityMaster()!=null?request.getFmtbEntityMaster().getEntityName():"-"));
					
					resultListBox.appendChild(item);
				}
				
				if(requests.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(7));
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
	
	public void viewRequest() throws InterruptedException{
		logger.info("");
		
		try{
			//Retrieve selected value
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			Listitem selectedItem = resultListBox.getSelectedItem();
			BmtbBillGenReq request = (BmtbBillGenReq)selectedItem.getValue();
			
			HashMap map = new HashMap();
			map.put("requestNo", request.getReqNo());
			this.forward(Uri.VIEW_BILL_GEN_REQUEST, map);
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
		((Datebox)this.getFellow("requestDateFromBox")).setText("");
		((Datebox)this.getFellow("requestDateToBox")).setText("");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.searchRequest();
	}
}
