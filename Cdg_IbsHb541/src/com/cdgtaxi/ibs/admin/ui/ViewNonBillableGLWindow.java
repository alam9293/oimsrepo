package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ViewNonBillableGLWindow extends CommonWindow implements AfterCompose {
	
	private FmtbNonBillableMaster master;
	private Listbox detailList;
	private Label svcProviderLbl,cardTypeLbl;
	private Integer masterNo;
	private Button createBtn;
	private static final Logger logger = Logger.getLogger(ViewNonBillableGLWindow.class);
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		Map map = Executions.getCurrent().getArg();
		masterNo = (Integer)map.get("masterNo");
		master = this.businessHelper.getAdminBusiness().getNonBillableMaster(masterNo);

		try{
			List<Entry<String, String>> splist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.SERVICE_PROVIDER).entrySet());
			for (Map.Entry<String, String> me : splist) {
				Listitem item = new Listitem();
				item.setValue(me.getKey());
				item.setLabel(me.getValue());

				MstbMasterTable mstbSpMaster = ConfigurableConstants.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER, me.getKey());

				if(master.getMstbMasterTableByServiceProvider()!=null)
					if(master.getMstbMasterTableByServiceProvider().getMasterNo().equals(mstbSpMaster.getMasterNo())){
						svcProviderLbl.setValue(me.getValue());
						break;
					}
			}

			List<Entry<String, String>> cplist = new LinkedList(ConfigurableConstants.getMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
			for (Map.Entry<String, String> cp : cplist) {
				Listitem item = new Listitem();
				item.setValue(cp.getKey());
				item.setLabel(cp.getValue());

				if(master.getMstbMasterTableByPymtTypeMasterNo()!=null)
					if(master.getMstbMasterTableByPymtTypeMasterNo().getMasterCode().equals(cp.getKey())){
						cardTypeLbl.setValue(cp.getValue());
						break;
					}
			}
			this.displayDetails(false);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!this.checkUriAccess(Uri.CREATE_NONBILLABLE_GL_DETAIL)){
			createBtn.setDisabled(true);
		}

	}
	
	private void displayDetails(boolean refresh) throws InterruptedException{
		if(refresh)
			master = this.businessHelper.getAdminBusiness().getNonBillableMaster(masterNo);
		
		if(master.getFmtbNonBillableDetails().size()>0){
			if(master.getFmtbNonBillableDetails().size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for(FmtbNonBillableDetail detail : master.getFmtbNonBillableDetails()){
				Listitem item = new Listitem();

				item.setValue(detail);
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName()));
				item.appendChild(newListcell(detail.getFmtbArContCodeMaster().getArControlCode()));
				item.appendChild(newListcell(detail.getFareAmountTxnCode()));
				item.appendChild(newListcell(detail.getAdminFeeTxnCode()));
				item.appendChild(newListcell(detail.getPremiumAmountTxnCode()));
				item.appendChild(newListcell(detail.getEffectiveDate()));

				detailList.appendChild(item);
			}

			if(master.getFmtbNonBillableDetails().size()>ConfigurableConstants.getMaxQueryResult())
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
			FmtbNonBillableDetail detail = (FmtbNonBillableDetail)detailList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("detailNo", detail.getDetailNo());
			
			if(DateUtil.getCurrentDate().compareTo(detail.getEffectiveDate()) <= -1)
				this.forward(Uri.EDIT_NONBILLABLE_GL_DETAIL, map);
			else
				this.forward(Uri.VIEW_NONBILLABLE_GL_DETAIL, map);
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
		this.detailList.clearSelection();
		this.detailList.getItems().clear();
		this.displayDetails(true);
	}
}
