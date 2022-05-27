package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class ManageSalesPersonWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageSalesPersonWindow.class);

	@SuppressWarnings("unused")
	private Listitem itemTemplate;
	private Listbox resultList;
	private Button createBtn;

	public void onCreate(CreateEvent ce) throws Exception {
		Components.wireVariables(this, this);
		//resultList = (Listbox) getFellow("resultList");
		//itemTemplate = (Listitem) resultList.getItems().get(0);
		//itemTemplate.detach();
		refresh();
		createBtn = (Button) getFellow("createBtn");
		if(!this.checkUriAccess(Uri.CREATE_SALES_PERSON)){
			createBtn.setDisabled(true);
		}
	}

	public void edit() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			MstbSalesperson salesPerson = (MstbSalesperson)selectedItem.getValue();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("salesPersonNo", salesPerson.getSalespersonNo());
			
			if(this.checkUriAccess(Uri.EDIT_SALES_PERSON))
				forward(Uri.EDIT_SALES_PERSON, map);
			else if(this.checkUriAccess(Uri.VIEW_SALES_PERSON))
				forward(Uri.VIEW_SALES_PERSON, map);
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
			resultList.clearSelection();
		}
	}

	public void create() throws InterruptedException {
		try {
			forward(Uri.CREATE_SALES_PERSON, null);
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_SALES_PERSON)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this sales person?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getAdminBusiness().deleteSalesPerson((MstbSalesperson) item.getValue());
			MasterSetup.getSalespersonManager().refresh();
			item.detach();
			Messagebox.show("Sales Person has been successfully deleted", "Delete Sales Person",
					Messagebox.OK, Messagebox.INFORMATION);
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
		List<MstbSalesperson> codes = businessHelper.getAdminBusiness().getSalesPersons();

		resultList.getItems().clear();

		if (codes.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(5));
		} else {
			for (MstbSalesperson code : codes) {
				Listitem item = new Listitem();
				item.setValue(code);
				item.appendChild(newListcell(code.getSalespersonNo()));
				item.appendChild(newListcell(code.getName()));
				item.appendChild(newListcell(code.getEmail()));
				item.appendChild(newListcell(DateUtil.convertDateToStr(code.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT)));
				if(code.getEffectiveDtTo()!=null)
					item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(code.getEffectiveDtTo())));
				else
					item.appendChild(newEmptyListcell(DateUtil.getSqlDateForNullComparison(), "-"));
				
				if(DateUtil.getCurrentDate().compareTo(code.getEffectiveDtFrom()) <= -1){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageSalesPersonWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);
				}else{
					item.appendChild(new Listcell());
				}
				
				resultList.appendChild(item);
			}
			if(codes.size()>ConfigurableConstants.getMaxQueryResult())
				resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultList.getListfoot()!=null)
				resultList.removeChild(resultList.getListfoot());
		}
	}
}
