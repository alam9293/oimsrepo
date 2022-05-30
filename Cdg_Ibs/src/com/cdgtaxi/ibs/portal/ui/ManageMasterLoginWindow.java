package com.cdgtaxi.ibs.portal.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

public class ManageMasterLoginWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageMasterLoginWindow.class);
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

	}
	@SuppressWarnings("unchecked")
	public void search() throws InterruptedException{
		
		logger.info("");
		String masterLoginString = ((CapsTextbox)this.getFellow("masterLoginIdTextBox")).getText();
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		try{
			
			List<IttbCpMasterLogin> masterLoginList = this.businessHelper.getPortalBusiness().searchMasterLogin(masterLoginString);

			if(masterLoginList.size() > 0) {
				
				if(masterLoginList.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(IttbCpMasterLogin masterLogin : masterLoginList){
					Listitem acct = new Listitem();
					resultListBox.appendChild(acct);
					acct.setValue(masterLogin);
					acct.appendChild(newListcell(masterLogin.getLoginId()));
					acct.appendChild(newListcell(masterLogin.getName()));
					acct.appendChild(newListcell(masterLogin.getCompanyName()));
					acct.appendChild(newListcell(masterLogin.getTelephone()));
					
					String status = "Active";
					if(masterLogin.getRetrievedFlag() != null && masterLogin.getRetrievedFlag().equalsIgnoreCase("D"))
						status = "Disabled";
					
					acct.appendChild(newListcell(status));
					
					resultListBox.appendChild(acct);
				}
				
				if(masterLoginList.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
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

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	public void edit() throws InterruptedException{
		try{
			Listbox resultList = (Listbox)this.getFellow("resultList");
			IttbCpMasterLogin ittbCpMasterLogin = (IttbCpMasterLogin)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("masterLoginNo", ittbCpMasterLogin.getMasterLoginNo());

			if(this.checkUriAccess(Uri.EDIT_MASTER_LOGIN))
				forward(Uri.EDIT_MASTER_LOGIN, map);
			else {
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
			e.printStackTrace();
		}
	}

}
