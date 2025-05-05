package com.cdgtaxi.ibs.acl.ui.role;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("unchecked")
public class SearchRoleWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchRoleWindow.class);
	
	public void searchRole() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		
		logger.info("");
		CapsTextbox nameTextBox = (CapsTextbox)this.getFellow("name");
		Listbox statusListBox = (Listbox)this.getFellow("status");
		String name = nameTextBox.getValue();
		String status = (String)statusListBox.getSelectedItem().getValue();
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		List<SatbRole> roles = this.businessHelper.getRoleBusiness().searchRole(name,status);
		
		if(roles.size()>0){
			
			if(roles.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for(SatbRole role : roles){
				Listitem item = new Listitem();
				item.setValue(role);
				item.appendChild(new Listcell(role.getName()));
				item.appendChild(new Listcell(com.cdgtaxi.ibs.acl.Constants.ROLE_STATUS_MAP.get(role.getStatus())));
				
				resultListBox.appendChild(item);
			}
			
			if(roles.size()>ConfigurableConstants.getMaxQueryResult())
				resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultListBox.getListfoot()!=null)
				resultListBox.removeChild(resultListBox.getListfoot());
		}
		else{
			if(resultListBox.getListfoot()==null){
				resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
			}
		}
		
		resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultListBox.setPageSize(10);
	}
	
	public void viewOrEditRole() throws InterruptedException{
		logger.info("");
		
		//Retrieve selected value
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Listitem selectedItem = resultListBox.getSelectedItem();
		SatbRole role = (SatbRole)selectedItem.getValue();
		
		HashMap map = new HashMap();
		map.put("roleId", role.getRoleId());
		
		if(this.checkUriAccess(Uri.EDIT_ROLE))
			this.forward(Uri.EDIT_ROLE, map);
		else if(this.checkUriAccess(Uri.VIEW_ROLE))
			this.forward(Uri.VIEW_ROLE, map);
		else{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}
	
	public void populateStatusList(Listbox statusListBox){
		statusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		
		Set statusSet = com.cdgtaxi.ibs.acl.Constants.ROLE_STATUS_MAP.keySet();
		Iterator statusIterator = statusSet.iterator();
		while(statusIterator.hasNext()){
			String statusKey = (String)statusIterator.next();
			Listitem listItem = new Listitem(com.cdgtaxi.ibs.acl.Constants.ROLE_STATUS_MAP.get(statusKey), statusKey);
			statusListBox.appendChild(listItem);
		}
	}
	
	public void refresh() throws SuspendNotAllowedException, InterruptedException{
		this.searchRole();
	}
}
