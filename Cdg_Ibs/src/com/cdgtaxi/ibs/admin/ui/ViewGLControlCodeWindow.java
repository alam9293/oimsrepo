package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Label;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeDetail;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ViewGLControlCodeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ViewGLControlCodeWindow.class);

	private FmtbArContCodeMaster code;
	private Label entityLbl,codeLbl;
	private Listbox detailList;

	@Override
	public void refresh() throws InterruptedException {
		detailList.getItems().clear();
		codeLbl = (Label)getFellow("codeLbl");
		entityLbl = (Label)getFellow("entityLbl");
		codeLbl.setValue(code.getArControlCode());

		List<FmtbEntityMaster> entities = Collections.emptyList();
		if(this.businessHelper.getAdminBusiness().isArContCodeBeenUsed(code.getArControlCodeNo()))
			entities = businessHelper.getAdminBusiness().getEntities();
		else entities = businessHelper.getAdminBusiness().getActiveEntities();

		for (FmtbEntityMaster entity : entities) {
			if (code.getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo())) {
				entityLbl.setValue(entity.getEntityName());
				break;
			}
		}
		detailList.getItems().clear();
		detailList = (Listbox) getFellow("detailList");
		Set<FmtbArContCodeDetail> details = code.getFmtbArContCodeDetails();
		for (FmtbArContCodeDetail detail : details) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getCostCentre()));
			item.appendChild(newListcell(detail.getDescription()));
			item.appendChild(newListcell(detail.getGlAccount(), StringUtil.GLOBAL_STRING_FORMAT));
			String taxType = "";
			if (detail.getMstbMasterTable() != null) {
				taxType = detail.getMstbMasterTable().getMasterValue();
			}
			item.appendChild(newListcell(taxType));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			detailList.appendChild(item);
		}
	}

	@SuppressWarnings("unchecked")
	public ViewGLControlCodeWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer codeNo =  (Integer) params.get("codeNo");
		logger.info("Plan No = " + codeNo);
		code = businessHelper.getAdminBusiness().getGLControlCode(codeNo);
		
	}

	public void onCreate() {
		codeLbl = (Label)getFellow("codeLbl");
		entityLbl = (Label)getFellow("entityLbl");
		codeLbl.setValue(code.getArControlCode());

		List<FmtbEntityMaster> entities = Collections.emptyList();
		if(this.businessHelper.getAdminBusiness().isArContCodeBeenUsed(code.getArControlCodeNo()))
			entities = businessHelper.getAdminBusiness().getEntities();
		else entities = businessHelper.getAdminBusiness().getActiveEntities();

		for (FmtbEntityMaster entity : entities) {
			if (code.getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo())) {
				entityLbl.setValue(entity.getEntityName());
				break;
			}
		}

		detailList = (Listbox) getFellow("detailList");
		Set<FmtbArContCodeDetail> details = code.getFmtbArContCodeDetails();
		for (FmtbArContCodeDetail detail : details) {
			Listitem item = newListitem(detail);
			item.appendChild(newListcell(detail.getCostCentre()));
			item.appendChild(newListcell(detail.getDescription()));
			item.appendChild(newListcell(detail.getGlAccount(), StringUtil.GLOBAL_STRING_FORMAT));
			String taxType = "";
			if (detail.getMstbMasterTable() != null) {
				taxType = detail.getMstbMasterTable().getMasterValue();
			}
			item.appendChild(newListcell(taxType));
			item.appendChild(newListcell(detail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			
			detailList.appendChild(item);
		}
	}

	public void editDetail() throws InterruptedException {
		try {
			Listitem selectedItem = detailList.getSelectedItem();
			FmtbArContCodeDetail detail = (FmtbArContCodeDetail) selectedItem.getValue();
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("codeDetailNo", detail.getArContCodeDetailNo());
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(detail.getEffectiveDt());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_GL_CONTROL_CODE_DETAIL))
					forward(Uri.EDIT_GL_CONTROL_CODE_DETAIL, map);
				else if(this.checkUriAccess(Uri.VIEW_GL_CONTROL_CODE_DETAIL))
					forward(Uri.VIEW_GL_CONTROL_CODE_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_GL_CONTROL_CODE_DETAIL))
					forward(Uri.VIEW_GL_CONTROL_CODE_DETAIL, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		finally {
			detailList.clearSelection();
		}
	}

}
