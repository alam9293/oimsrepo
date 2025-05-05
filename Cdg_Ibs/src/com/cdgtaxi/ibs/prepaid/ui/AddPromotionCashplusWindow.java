package com.cdgtaxi.ibs.prepaid.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
import com.cdgtaxi.ibs.web.component.Listbox;

@SuppressWarnings("serial")
public class AddPromotionCashplusWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(AddPromotionCashplusWindow.class);
	private Listbox resultList;
	
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		List<MstbPromotionCashPlus> effectivePrepaidPromotions = this.businessHelper.getAdminBusiness().getEffectivePrepaidPromotions();
		populateData(effectivePrepaidPromotions);
		
	}
	

	private void populateData(List<MstbPromotionCashPlus> modelList){
		
		try{
			if(modelList.size()>0){
	
				for(MstbPromotionCashPlus model: modelList){
					
					Listitem item = new Listitem();
			    	item.setValue(model);
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

	
	

	public void addPrepaidPromotion() throws InterruptedException{
		
		CommonWindow window = this.back();
		List<MstbPromotionCashPlus> selectedItems = ComponentUtil.getSelectedItems(resultList);
		if(window instanceof IssuanceReqAddCardWindow){

			((IssuanceReqAddCardWindow)window).addPromotionIntoPromotionsListBox(selectedItems);
		} else if(window instanceof TopUpReqAddCardWindow){
			((TopUpReqAddCardWindow)window).addPromotionIntoPromotionsListBox(selectedItems);
		}
		
	}

	@Override
	public void refresh() throws InterruptedException {
		
	}
	
	
}
