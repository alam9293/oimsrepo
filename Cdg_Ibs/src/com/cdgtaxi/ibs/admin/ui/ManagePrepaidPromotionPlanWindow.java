package com.cdgtaxi.ibs.admin.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidPromotionForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class ManagePrepaidPromotionPlanWindow extends CommonWindow implements AfterCompose {

	private CapsTextbox promoCodeTextBox;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Listbox resultList;
	private static final Logger logger = Logger.getLogger(ManagePrepaidPromotionPlanWindow.class);
	private Button createBtn;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		if(!this.checkUriAccess(Uri.CREATE_PREPAID_PROMOTION_PLAN)){
			createBtn.setDisabled(true);
		}
		
	}
	
	public void reset(){
		
		ComponentUtil.reset(
				promoCodeTextBox, 
				effDateFromDateBox, 
				effDateToDateBox, 
				resultList
		);
	}
	
	public void populateData(List<MstbPromotionCashPlus> modelList){
		
		try{
			logger.debug("Promotion model list size: " + modelList.size());
			
			if(modelList.size()>0){
	
				for(MstbPromotionCashPlus model: modelList){
					
					Listitem item = new Listitem();
			    	item.setValue(model.getPromoCode());
			    	item.appendChild(newListcell(model.getPromoCode()));
			    	item.appendChild(newListcell(StringUtil.bigDecimalToStringWithGDFormat(model.getCashplus())));
			    	item.appendChild(newListcell(model.getEffectiveDtFrom()));
			    	item.appendChild(newListcell(model.getEffectiveDtTo()));
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
	

	public void search() throws SuspendNotAllowedException, InterruptedException{
		
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{
			
			ComponentUtil.fillNullValueDatebox(effDateFromDateBox, effDateToDateBox);
			
			Date effDateFrom = effDateFromDateBox.getValue();
			Date effDateTo = effDateToDateBox.getValue();
			String promoCode = promoCodeTextBox.getValue();
			
			if(effDateFrom!=null && effDateTo!=null)
				if(effDateFrom.after(effDateTo))
					throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			
			if(effDateFrom==null && effDateTo==null && Strings.isNullOrEmpty(promoCode)){
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			SearchPrepaidPromotionForm form = this.buildSearchForm();
			List<MstbPromotionCashPlus> promotions = this.businessHelper.getAdminBusiness().searchPrepaidPromotion(form);	
			populateData(promotions);
				
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
		this.forward(Uri.CREATE_PREPAID_PROMOTION_PLAN, null);
	}
	
	public void selectPromotionPlan() throws InterruptedException{
		try{
			String promoCode = (String)resultList.getSelectedItem().getValue();
			Map<Object, Object> map = Maps.newHashMap();
			map.put("promoCode", promoCode);
			MstbPromotionCashPlus promotionCashPlus = this.businessHelper.getAdminBusiness().getPromotionCashPlus(promoCode);
	
			Date effectiveDtTo = promotionCashPlus.getEffectiveDtTo();
			Date currentDt = DateUtil.getCurrentTimestamp();
			
			boolean promotionEditable = currentDt.before(effectiveDtTo);
			
			if(this.checkUriAccess(Uri.EDIT_PREPAID_PROMOTION_PLAN)) {
				if(!promotionEditable){
					map.put("message", "Plan effective period ended, no changes allowed.");
					forward(Uri.VIEW_PREPAID_PROMOTION_PLAN, map);
				} else {
					forward(Uri.EDIT_PREPAID_PROMOTION_PLAN, map);
				}
				
				
			} else if (this.checkUriAccess(Uri.VIEW_PREPAID_PROMOTION_PLAN)){
				forward(Uri.VIEW_PREPAID_PROMOTION_PLAN, map);
			} else {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
						"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		search();
	}
	
	private SearchPrepaidPromotionForm buildSearchForm(){
		SearchPrepaidPromotionForm form = new SearchPrepaidPromotionForm();
		form.promoCode = promoCodeTextBox.getValue();
		form.effDateFrom = effDateFromDateBox.getValue();
		form.effDateTo = effDateToDateBox.getValue();
		return form;
	}
}
