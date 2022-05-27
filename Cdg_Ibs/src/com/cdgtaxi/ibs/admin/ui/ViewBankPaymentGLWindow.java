package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ViewBankPaymentGLWindow extends CommonWindow implements AfterCompose {

	private Integer masterNo;
	private FmtbBankPaymentMaster master;
	private Listbox detailList;
	private Label acquirerLbl;
	private static final Logger logger = Logger.getLogger(EditBankPaymentGLWindow.class);
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		try{
			//wire variables
			Components.wireVariables(this, this);

			Map map = Executions.getCurrent().getArg();
			masterNo = (Integer)map.get("masterNo");
			master = this.businessHelper.getAdminBusiness().getBankPaymentMaster(masterNo);
			List<MstbAcquirer> acquirers = this.businessHelper.getAdminBusiness().getAcquirer();
			for(MstbAcquirer acquirer : acquirers){
				Listitem item = new Listitem();
				item.setValue(acquirer);
				item.setLabel(acquirer.getName());
				if(master.getMstbAcquirer()!=null)
					if(master.getMstbAcquirer().getAcquirerNo().equals(acquirer.getAcquirerNo())){
						acquirerLbl.setValue(acquirer.getName());
						break;
					}
			}
			this.displayDetails(false);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void displayDetails(boolean refresh) throws InterruptedException{
		if(refresh)
			master = this.businessHelper.getAdminBusiness().getBankPaymentMaster(masterNo);
		if(master.getFmtbBankPaymentDetails().size()>0){
			if(master.getFmtbBankPaymentDetails().size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			for(FmtbBankPaymentDetail detail : master.getFmtbBankPaymentDetails()){
				Listitem item = new Listitem();

				item.setValue(detail);
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName()));
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getArControlCode()));
				item.appendChild(newListcell(detail.getMdrTxnCode()));
				item.appendChild(newListcell(detail.getMdrAdjTxnCode()));
				item.appendChild(newListcell(detail.getEffectiveDate()));
				
				detailList.appendChild(item);
			}
			if(master.getFmtbBankPaymentDetails().size()>ConfigurableConstants.getMaxQueryResult())
				detailList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

			if(detailList.getListfoot()!=null)
				detailList.removeChild(detailList.getListfoot());
		}else{
			if(detailList.getListfoot()==null){
				detailList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
			}
		}
		detailList.setMold(Constants.LISTBOX_MOLD_PAGING);
		detailList.setPageSize(10);
	}
	@SuppressWarnings("unchecked")
	public void edit() throws InterruptedException{
		try{
			FmtbBankPaymentDetail detail = (FmtbBankPaymentDetail)detailList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("detailNo", detail.getDetailNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(detail.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_BANK_PAYMENT_DETAIL_GL))
					this.forward(Uri.EDIT_BANK_PAYMENT_DETAIL_GL, map);
				else if(this.checkUriAccess(Uri.VIEW_BANK_PAYMENT_DETAIL_GL))
					this.forward(Uri.VIEW_BANK_PAYMENT_DETAIL_GL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_BANK_PAYMENT_DETAIL_GL))
					this.forward(Uri.VIEW_BANK_PAYMENT_DETAIL_GL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		}
		catch(WrongValueException wve){
			throw wve;
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
	public void refresh() throws InterruptedException {
		this.detailList.clearSelection();
		this.detailList.getItems().clear();
		this.displayDetails(true);
	}
}
