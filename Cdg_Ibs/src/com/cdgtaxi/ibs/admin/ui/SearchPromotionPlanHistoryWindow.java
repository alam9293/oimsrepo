package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchPromoPlanHistoryForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class SearchPromotionPlanHistoryWindow extends CommonWindow implements AfterCompose {

	private CapsTextbox nameTextBox;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Listbox resultList, typeListBox, productTypeListBox, 
		promotionTypeListBox, jobTypeListBox, vehicleModelListBox,statusListBox;
	private static final Logger logger = Logger.getLogger(SearchPromotionPlanHistoryWindow.class);
	private Button createBtn;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Listitem emptyItem = new Listitem("-", null);
		
		productTypeListBox.appendChild((Listitem)emptyItem.clone());
		List<PmtbProductType> productTypes = this.businessHelper.getProductTypeBusiness().getAllProductType();
		for(PmtbProductType productType : productTypes){
			Listitem item = new Listitem();
			item.setValue(productType);
			item.setLabel(productType.getName());
			productTypeListBox.appendChild(item);
		}
		productTypeListBox.setSelectedIndex(0);
		
		typeListBox.appendChild((Listitem)emptyItem.clone());
		Set<String> promoAcctTypeKeys = NonConfigurableConstants.PROMO_ACCT_TYPE.keySet();
		for(String key : promoAcctTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.PROMO_ACCT_TYPE.get(key));
			typeListBox.appendChild(item);
		}
		typeListBox.setSelectedIndex(0);
		
		promotionTypeListBox.appendChild((Listitem)emptyItem.clone());
		Set<String> promoTypeKeys = NonConfigurableConstants.PROMO_TYPE.keySet();
		for(String key : promoTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.PROMO_TYPE.get(key));
			promotionTypeListBox.appendChild(item);
		}
		promotionTypeListBox.setSelectedIndex(0);
		
		jobTypeListBox.appendChild((Listitem)emptyItem.clone());
		Map<String, String> jobTypes = ConfigurableConstants.getAllMasters(ConfigurableConstants.JOB_TYPE);
		Set<String> jobTypeKeys = jobTypes.keySet();
		for(String key : jobTypeKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(jobTypes.get(key));
			jobTypeListBox.appendChild(item);
		}
		jobTypeListBox.setSelectedIndex(0);
		
		vehicleModelListBox.appendChild((Listitem)emptyItem.clone());
		Map<String, String> vehicleModels = ConfigurableConstants.getAllMasters(ConfigurableConstants.VEHICLE_MODEL);
		Set<String> vehicleModelKeys = vehicleModels.keySet();
		for(String key : vehicleModelKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(vehicleModels.get(key));
			vehicleModelListBox.appendChild(item);
		}
		vehicleModelListBox.setSelectedIndex(0);
		
		if(!this.checkUriAccess(Uri.CREATE_PROMOTION_PLAN)){
			createBtn.setDisabled(true);
		}
		statusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<Entry<String, String>> statusEntries = NonConfigurableConstants.PROMOTION_REQUEST_STATUS.entrySet();
		for(Entry<String, String> entry : statusEntries){
			
			if(!NonConfigurableConstants.PROMOTION_REQUEST_STATUS_NEW.equals(entry.getKey())
				&&!NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING.equals(entry.getKey())){
				statusListBox.appendItem(entry.getValue(), entry.getKey());
			}
		}
	}
	
	public void reset(){
		nameTextBox.setValue("");
		effDateFromDateBox.setText("");
		effDateToDateBox.setText("");
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(9));
		typeListBox.setSelectedIndex(0);
		productTypeListBox.setSelectedIndex(0);
		promotionTypeListBox.setSelectedIndex(0);
		jobTypeListBox.setSelectedIndex(0);
		vehicleModelListBox.setSelectedIndex(0);
	}
	
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{
			if(effDateFromDateBox.getValue()!=null && effDateToDateBox.getValue()==null)
				effDateToDateBox.setValue(effDateFromDateBox.getValue());
			else if(effDateFromDateBox.getValue()==null && effDateToDateBox.getValue()!=null)
				effDateFromDateBox.setValue(effDateToDateBox.getValue());
			
			if(effDateFromDateBox.getValue()!=null && effDateToDateBox.getValue()!=null)
				if(effDateFromDateBox.getValue().compareTo(effDateToDateBox.getValue()) == 1)
					throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			
			SearchPromoPlanHistoryForm form = this.buildSearchForm();
			
			if(form.isAtLeastOneCriteriaSelected==false){
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			List<MstbPromoReq> promoReqs = this.businessHelper.getAdminBusiness().searchPromoPlanHistory(form);
			if(promoReqs.size()>0){
				
				if(promoReqs.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(MstbPromoReq req : promoReqs){
					
					
					MstbPromoReqFlow lastPromoReqFlow = req.getLastPromoReqFlow();
					String status = lastPromoReqFlow.getReqToStatus();
					
					//should only return one records, which its from_status='N'
					Set<MstbPromoReqFlow> mstbPromoReqFlows = req.getMstbPromoReqFlows();
					MstbPromoDetail promoDetail = null;
					
					MstbPromoReqFlow flow = mstbPromoReqFlows.iterator().next();
					promoDetail = flow.getToPromoDetail();
					
					Listitem item = new Listitem();
					item.setValue(req.getPromoReqNo());
					
					if(promoDetail!=null){
						item.appendChild(newListcell(promoDetail.getName()));
						item.appendChild(newListcell(NonConfigurableConstants.PROMO_ACCT_TYPE.get(promoDetail.getType())));
						if(promoDetail.getPmtbProductType()!=null) item.appendChild(newListcell(promoDetail.getPmtbProductType().getName()));
						else item.appendChild(newListcell("ALL"));
						item.appendChild(newListcell(NonConfigurableConstants.PROMO_TYPE.get(promoDetail.getPromoType())));
						item.appendChild(newListcell(promoDetail.getPromoValue()));
						item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(promoDetail.getEffectiveDtFrom())));
						item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(promoDetail.getEffectiveDtTo())));
						item.appendChild(newListcell(promoDetail.getEffectiveCutoffDate()));
						if(promoDetail.getMstbMasterTableByJobType()!=null) item.appendChild(newListcell(promoDetail.getMstbMasterTableByJobType().getMasterValue()));
						else item.appendChild(newListcell("ALL"));
						if(promoDetail.getMstbMasterTableByVehicleModel()!=null) item.appendChild(newListcell(promoDetail.getMstbMasterTableByVehicleModel().getMasterValue()));
						else item.appendChild(newListcell("ALL"));
						
						item.appendChild(newListcell(NonConfigurableConstants.PROMOTION_REQUEST_STATUS.get(status)));
					}
					resultList.appendChild(item);
				}
				
				if(promoReqs.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
				}
			}
			
			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	

	@SuppressWarnings("unchecked")
	public void view() throws InterruptedException{
		
		try{
			Integer reqNo = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("promoReqNo", reqNo);
			map.put("type", ViewPromotionPlanDetailWindow.DetailType.HISTORY);
			forward(Uri.VIEW_PROMOTION_PLAN_DETAIL, map);
			
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void refresh() throws InterruptedException {
		SearchPromoPlanHistoryForm form = this.buildSearchForm();
		if(form.isAtLeastOneCriteriaSelected)
			this.search();
	}
	
	private SearchPromoPlanHistoryForm buildSearchForm(){
		SearchPromoPlanHistoryForm form = new SearchPromoPlanHistoryForm();
		form.name = nameTextBox.getValue();
		if(form.name!=null && form.name.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.type = (String)typeListBox.getSelectedItem().getValue();
		if(form.type!=null && form.type.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.productType = (PmtbProductType)productTypeListBox.getSelectedItem().getValue();
		if(form.productType!=null) form.isAtLeastOneCriteriaSelected = true;
		form.promoType = (String)promotionTypeListBox.getSelectedItem().getValue();
		if(form.promoType!=null && form.promoType.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.effDateFrom = effDateFromDateBox.getValue();
		if(form.effDateFrom!=null) form.isAtLeastOneCriteriaSelected = true;
		form.effDateTo = effDateToDateBox.getValue();
		if(form.effDateTo!=null) form.isAtLeastOneCriteriaSelected = true;
		form.jobType = (String)jobTypeListBox.getSelectedItem().getValue();
		if(form.jobType!=null && form.jobType.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.vehicleModel = (String)vehicleModelListBox.getSelectedItem().getValue();
		if(form.vehicleModel!=null && form.vehicleModel.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.reqToStatus = (String)statusListBox.getSelectedItem().getValue();
		if(form.reqToStatus!=null && form.reqToStatus.length()>0) form.isAtLeastOneCriteriaSelected = true;
		return form;
	}
}
