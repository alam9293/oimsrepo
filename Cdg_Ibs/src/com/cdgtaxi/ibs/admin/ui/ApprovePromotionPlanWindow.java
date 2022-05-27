package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.IBSException;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

public class ApprovePromotionPlanWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApprovePromotionPlanWindow.class);

	private Listbox resultLB;
	private Checkbox checkAll;
	private CapsTextbox remarksTextBox;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	
	public void init() throws InterruptedException{
		remarksTextBox.setValue("");
		search();
	}

	public void search() throws InterruptedException {
		try {
			resultLB.getItems().clear();
			checkAll.setChecked(false);

	
			this.displayProcessing();
			List<MstbPromoReq> pendingRequest = this.businessHelper.getAdminBusiness().getPendingPromoReq();
			if (pendingRequest.size() > 0) {

				if (pendingRequest.size() > ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert",
							Messagebox.OK, Messagebox.INFORMATION);

				MstbPromoReqFlow lastPromoReqFlow = null;
				MstbPromoDetail promoDetail = null;
				
				for (MstbPromoReq req : pendingRequest) {
					Listitem item = new Listitem();
					item.setValue(req);
					
					Listcell checkboxCell = new Listcell();
					checkboxCell.appendChild(new Checkbox());
					item.appendChild(checkboxCell);

					//For pending request,there is only one request flow exists
					lastPromoReqFlow =  req.getLastPromoReqFlow();
					promoDetail = lastPromoReqFlow.getToPromoDetail();
					
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
		
					item.appendChild(newListcell(NonConfigurableConstants.PROMOTION_STATUS.get(lastPromoReqFlow.getToStatus())));
					
					resultLB.appendChild(item);
				}

				if (pendingRequest.size() > ConfigurableConstants.getMaxQueryResult())
					resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());

				if (resultLB.getListfoot() != null)
					resultLB.removeChild(resultLB.getListfoot());
			} else {
				if (resultLB.getListfoot() == null) {
					resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(9));
				}
			}

			resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultLB.setPageSize(10);
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	public void checkAll() {
		List<Listitem> listItems = resultLB.getItems();
		for (Listitem item : listItems) {
			Listcell checkboxCell = (Listcell) item.getFirstChild();
			Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
			if (!checkAll.isChecked())
				checkBox.setChecked(false);
			else
				checkBox.setChecked(true);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		this.search();
	}
	
	
	public List<MstbPromoReq> getCheckBoxResult(){
		
		List<MstbPromoReq> reqs = new ArrayList<MstbPromoReq>();
		List<Listitem> listItems = resultLB.getItems();
		for (Listitem item : listItems) {
			Listcell checkboxCell = (Listcell) item.getFirstChild();
			Checkbox checkBox = (Checkbox) checkboxCell.getFirstChild();
			if (checkBox.isChecked()) {
				MstbPromoReq req = (MstbPromoReq) item.getValue();
				reqs.add(req);
			}
		}
		return reqs;
	}
	

	public void approve() throws InterruptedException {
		
		logger.info("approve()");
		try {
			List<MstbPromoReq> selectedReqs = getCheckBoxResult();;
			if (!selectedReqs.isEmpty()) {
				MstbPromotion promo =null;
				MstbPromoDetail currentPromoDetail;
				Timestamp effectiveTime = null;
				Timestamp currentTime = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
				logger.info("current time: " + currentTime);
				for(MstbPromoReq req: selectedReqs){
					promo = req.getMstbPromotion();
					currentPromoDetail = promo.getCurrentPromoDetail();
					if(currentPromoDetail!=null){
						effectiveTime = DateUtil.convertTimestampTo0000Hours(currentPromoDetail.getEffectiveDtFrom());
						logger.info("current promotion effective time: " + effectiveTime);
						if(currentTime.compareTo(effectiveTime) >= 0){
							throw new IBSException("Unable to approve. Effective Date of promotion plan " + currentPromoDetail.getName() + " has been reached!");
						}
					}
				}
				
				if (Messagebox.show("Are you sure you want to proceed to approve?",
						"Promotion Plan Approval", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION) == Messagebox.OK) {

					this.businessHelper.getAdminBusiness().approvePromoReq(selectedReqs,remarksTextBox.getValue());

					Messagebox.show("Promotion Plan approved!", "Promotion Plan Approval",
							Messagebox.OK, Messagebox.INFORMATION);
					
					// Refresh the listing
					this.init();
				}
			}
		}
		catch (IBSException e) {
			Messagebox.show(e.getMessage(),
					"Promotion Plan Approval", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	
	}

	@SuppressWarnings("unchecked")
	public void reject() throws InterruptedException {
		
		logger.info("reject()");
		try {
			List<MstbPromoReq> selectedReqs = getCheckBoxResult();;
			if (!selectedReqs.isEmpty()) {

				String remarks = remarksTextBox.getValue();
				if(remarks==null || "".equals(remarks))
					throw new IBSException("Approval remarks is mandatory for rejected");
				
				if (Messagebox.show("Are you sure you want to proceed to reject?",
						"Promotion Plan Approval", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION) == Messagebox.OK) {

					this.businessHelper.getAdminBusiness().rejectPromoReq(selectedReqs,remarks);

					Messagebox.show("Promotion Plan rejected!", "Promotion Plan Approval",
							Messagebox.OK, Messagebox.INFORMATION);
					
					// Refresh the listing
					this.init();
				}
			}
		} 
		catch (IBSException e) {
			Messagebox.show(e.getMessage(),
					"Promotion Plan Approval", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	
	public void view() throws InterruptedException{
		try{
			MstbPromoReq req = (MstbPromoReq)resultLB.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("promoReqNo", req.getPromoReqNo());
			map.put("type", ViewPromotionPlanDetailWindow.DetailType.APPROVE);
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
	
	
	public void viewHistory() throws InterruptedException{
		forward(Uri.SEARCH_PROMOTION_PLAN_HISTORY, null);
		
	}
	
	
}
