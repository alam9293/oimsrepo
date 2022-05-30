package com.cdgtaxi.ibs.admin.ui;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.cdgtaxi.ibs.common.model.forms.SearchPromotionForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ManagePromotionPlanWindow extends CommonWindow implements AfterCompose {

	private CapsTextbox nameTextBox;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Listbox resultList, typeListBox, productTypeListBox, 
		promotionTypeListBox, jobTypeListBox, vehicleModelListBox,statusListBox;
	private static final Logger logger = Logger.getLogger(ManagePromotionPlanWindow.class);
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
		Set<Entry<String, String>> statusEntries = NonConfigurableConstants.PROMOTION_STATUS.entrySet();
		for(Entry<String, String> entry : statusEntries){
				statusListBox.appendItem(entry.getValue(), entry.getKey());
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
	
	public void populateData(ArrayList<PromotionPlanModel> modelList){
		
		try{
		
			logger.debug("model list size: " + modelList.size());
			
			if(modelList.size()>0){
	
				for(PromotionPlanModel model: modelList){
					
					Listitem item = new Listitem();
			    	item.setValue(model.promoNo);
			    	item.appendChild(newListcell(model.name));
			    	item.appendChild(newListcell(model.type));
			    	item.appendChild(newListcell(model.productTypeName));
			    	item.appendChild(newListcell(model.promoType));
			    	String promoValue = StringUtil.bigDecimalToString(model.promoValue, StringUtil.GLOBAL_DECIMAL_FORMAT);
			    	item.appendChild(newListcell(promoValue));
			    	item.appendChild(newListcell(model.effDateFrom));
			    	item.appendChild(newListcell(model.effDateTo));
			    	item.appendChild(newListcell(model.jobType));
			    	item.appendChild(newListcell(model.vehicleModel));
			    	item.appendChild(newListcell(model.status));
			    	resultList.appendChild(item);
				}
				
				if(modelList.size()>ConfigurableConstants.getMaxQueryResult()) {
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				}
				
				if(resultList.getListfoot()!=null) {
					resultList.removeChild(resultList.getListfoot());
				}
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
				}
			}
			
			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public class PromotionPlanModel{
			
		 	public Integer promoNo;
			public String name;
			public String type;
			public String productTypeName="ALL";
			public String promoType;
			public BigDecimal promoValue;
			public Date effDateFrom;
			public Date effDateTo;
			public String jobType="ALL";
			public String vehicleModel="ALL";
			public String status;
			
			public PromotionPlanModel() {
			}
			
			@Override
			public boolean equals(Object obj) {
				
				if (obj instanceof PromotionPlanModel){
					
					PromotionPlanModel model = (PromotionPlanModel) obj;
					if(this.promoNo.equals(model.promoNo))
						return true;
				}
				return false;
			}
			
			
	}
	
	
	private PromotionPlanModel constructModel(Integer promoNo, MstbPromoDetail promoDetail, String status){
		
		PromotionPlanModel model = new PromotionPlanModel();
		model.promoNo = promoNo;
		model.name = promoDetail.getName();
		model.type = NonConfigurableConstants.PROMO_ACCT_TYPE.get(promoDetail.getType());
		PmtbProductType productType = promoDetail.getPmtbProductType();
		if(productType!=null)
			model.productTypeName = productType.getName();
		model.promoType = NonConfigurableConstants.PROMO_TYPE.get(promoDetail.getPromoType());
		model.promoValue = promoDetail.getPromoValue();
		model.effDateFrom = DateUtil.convertTimestampToUtilDate(promoDetail.getEffectiveDtFrom());
		model.effDateTo = DateUtil.convertTimestampToUtilDate(promoDetail.getEffectiveDtTo());
		MstbMasterTable masterJobType = promoDetail.getMstbMasterTableByJobType();
		if(masterJobType!=null)
			model.jobType = masterJobType.getMasterValue();
		MstbMasterTable masterVehicleModel = promoDetail.getMstbMasterTableByVehicleModel();
		if(masterVehicleModel!=null)
			model.vehicleModel = masterVehicleModel.getMasterValue();
		model.status = NonConfigurableConstants.PROMOTION_STATUS.get(status);
		return model;
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
			
			SearchPromotionForm form = this.buildSearchForm();
			
			if(form.isAtLeastOneCriteriaSelected==false){
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			
			ArrayList<PromotionPlanModel> modelList = new ArrayList<PromotionPlanModel>();
			List<MstbPromotion> latestPromotions = this.businessHelper.getAdminBusiness().searchLastPromoReq(form);
			
			for(MstbPromotion promotion : latestPromotions){
				MstbPromoReq lastPromoReq = promotion.getLastPromoReq();
				MstbPromoReqFlow lastPromoReqFlow = lastPromoReq.getLastPromoReqFlow();
				MstbPromoDetail promoDetail = lastPromoReqFlow.getToPromoDetail();
				String status = lastPromoReqFlow.getToStatus();
				
				if(!NonConfigurableConstants.PROMOTION_STATUS_DELETED.equals(form.status)
						&& NonConfigurableConstants.PROMOTION_STATUS_DELETED.equals(status)){
					continue;
				}
				
				PromotionPlanModel model = constructModel(promotion.getPromoNo(), promoDetail, status);
				modelList.add(model);
			}
						
			populateData(modelList);
				
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

	
	public void create() throws InterruptedException{
		this.forward(Uri.CREATE_PROMOTION_PLAN, null);
	}
	
	@SuppressWarnings("unchecked")
	public void edit() throws InterruptedException{
		try{
			Integer promoNo = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("promoNo", promoNo);
			
			MstbPromotion promotion = (MstbPromotion)this.businessHelper.getAdminBusiness().getPromotion(promoNo);
			MstbPromoReq lastPromoReq = promotion.getLastPromoReq();
			MstbPromoReqFlow lastPromoReqFlow =lastPromoReq.getLastPromoReqFlow();
			
			String status = lastPromoReqFlow.getToStatus();
			MstbPromoDetail promoDetail = lastPromoReqFlow.getToPromoDetail();
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(promoDetail.getEffectiveDtFrom());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			
			boolean isPending=false;
			logger.info("promoNo: " + promoNo + " status:" + status);
			if(status!=null && (NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_CREATE.equals(status)
							|| NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_EDIT.equals(status)
							|| NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_DELETE.equals(status)))
			{
				isPending = true;
			}
			map.put("pending", isPending);
			
			if(NonConfigurableConstants.PROMOTION_STATUS_DELETED.equals(status)){
				forward(Uri.VIEW_PROMOTION_PLAN, map);
			}
			else {
				
				if(isPending) {
					map.put("message", "Pending Approval status are not allowed to Edit or Delete");
					forward(Uri.VIEW_PROMOTION_PLAN, map);
				}
				else {
					
					if(t1.compareTo(t2) >= 0) {
						if(this.checkUriAccess(Uri.EDIT_PROMOTION_PLAN)) {
							forward(Uri.EDIT_PROMOTION_PLAN, map);
						}
						else if(this.checkUriAccess(Uri.VIEW_PROMOTION_PLAN))
							forward(Uri.VIEW_PROMOTION_PLAN, map);
						else{
							Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
									"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}
					}
					else {
						if(this.checkUriAccess(Uri.VIEW_PROMOTION_PLAN)) {
							map.put("message", "Plan has taken effect, no changes allowed.");
							forward(Uri.VIEW_PROMOTION_PLAN, map);
						}
						else{
							Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
									"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
							return;
						}
					}
				}
			}
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
		SearchPromotionForm form = this.buildSearchForm();
		if(form.isAtLeastOneCriteriaSelected)
			this.search();
	}
	
	private SearchPromotionForm buildSearchForm(){
		SearchPromotionForm form = new SearchPromotionForm();
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
		form.status = (String)statusListBox.getSelectedItem().getValue();
		if(form.status!=null && form.status.length()>0) form.isAtLeastOneCriteriaSelected = true;
		return form;
	}
}
