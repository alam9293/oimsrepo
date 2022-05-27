package com.cdgtaxi.ibs.acl.ui.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("unchecked")
public class SearchUserWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchUserWindow.class);
	private Combobox domainCB;
	
	public void afterCompose() {
		Components.wireVariables(this, this);

		domainCB.appendChild(ComponentUtil.createNotRequiredComboitem());
		List<String> domains = this.businessHelper.getUserBusiness().getDomains();
		for (String domain : domains) {
			Comboitem item = new Comboitem();
			item.setLabel(domain);
			item.setValue(domain);
			domainCB.appendChild(item);
		}
		domainCB.setSelectedIndex(0);
	}
	
	public void searchUser() throws InterruptedException{
		this.displayProcessing();
		
		logger.info("");
		CapsTextbox loginIdTextBox = (CapsTextbox)this.getFellow("loginId");
		CapsTextbox nameTextBox = (CapsTextbox)this.getFellow("name");
		Textbox emailTextBox = (Textbox)this.getFellow("email");
		Listbox statusListBox = (Listbox)this.getFellow("status");
		Listbox lockedListBox = (Listbox)this.getFellow("locked");
		String loginId = loginIdTextBox.getValue();
		String name = nameTextBox.getValue();
		String email = emailTextBox.getValue();
		String status = (String)statusListBox.getSelectedItem().getValue();
		String locked = (String)lockedListBox.getSelectedItem().getValue();
		String domain = null;
		if(domainCB.getSelectedItem()!=null)
			domain = (String) domainCB.getSelectedItem().getValue();
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		try{
			
			List<SatbUser> users = this.businessHelper.getUserBusiness().searchUser(loginId, domain, name, email, status, locked);
			if(users.size()>0){
				
				if(users.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(SatbUser user : users){
					Listitem item = new Listitem();
					item.setValue(user);
					item.appendChild(new Listcell(user.getLoginId()));
					item.appendChild(new Listcell(user.getDomain()));
					item.appendChild(new Listcell(user.getName()));
					item.appendChild(new Listcell(user.getEmail()));
					item.appendChild(new Listcell(com.cdgtaxi.ibs.acl.Constants.STATUS_MAP.get(user.getStatus())));
					
					String userLocked = com.cdgtaxi.ibs.acl.Constants.YES_NO_MAP.get(com.cdgtaxi.ibs.acl.Constants.BOOLEAN_NO);
					if(user.getLocked().equals(com.cdgtaxi.ibs.acl.Constants.BOOLEAN_YES))
						userLocked = com.cdgtaxi.ibs.acl.Constants.YES_NO_MAP.get(com.cdgtaxi.ibs.acl.Constants.BOOLEAN_YES);
					
					item.appendChild(new Listcell(userLocked));
					
					resultListBox.appendChild(item);
				}
				
				if(users.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
				}
			}
			
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(10);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void viewOrEditUser() throws InterruptedException{
		logger.info("");
		//Retrieve selected value
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		Listitem selectedItem = resultListBox.getSelectedItem();
		SatbUser user = (SatbUser)selectedItem.getValue();
		
		HashMap map = new HashMap();
		map.put("userId", user.getUserId());
		
		if(this.checkUriAccess(Uri.EDIT_USER))
			this.forward(Uri.EDIT_USER, map);
		else if(this.checkUriAccess(Uri.VIEW_USER))
			this.forward(Uri.VIEW_USER, map);
		else{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}
	
	public void populateStatusList(Listbox statusListBox){
		statusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		
		Set statusSet = com.cdgtaxi.ibs.acl.Constants.STATUS_MAP.keySet();
		Iterator statusIterator = statusSet.iterator();
		while(statusIterator.hasNext()){
			String statusKey = (String)statusIterator.next();
			Listitem listItem = new Listitem(com.cdgtaxi.ibs.acl.Constants.STATUS_MAP.get(statusKey), statusKey);
			statusListBox.appendChild(listItem);
		}
	}
	
	public void populateLockedList(Listbox lockedListBox){
		lockedListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		
		Set statusSet = com.cdgtaxi.ibs.acl.Constants.YES_NO_MAP.keySet();
		Iterator statusIterator = statusSet.iterator();
		while(statusIterator.hasNext()){
			String statusKey = (String)statusIterator.next();
			Listitem listItem = new Listitem(com.cdgtaxi.ibs.acl.Constants.YES_NO_MAP.get(statusKey), statusKey);
			lockedListBox.appendChild(listItem);
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.searchUser();
	}
}
