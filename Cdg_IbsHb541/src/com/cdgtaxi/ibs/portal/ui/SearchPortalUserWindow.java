package com.cdgtaxi.ibs.portal.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.IttbCpLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchPortalUserWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchPortalUserWindow.class);
	
	@SuppressWarnings("unchecked")
	public void search() throws InterruptedException{
		this.displayProcessing();
		
		logger.info("");
		String userLoginId = ((CapsTextbox)this.getFellow("userLoginIdTextBox")).getValue();
		String cardNo = ((Decimalbox)this.getFellow("cardNoDecimalBox")).getText();
		Integer contactPersonId = ((Intbox)this.getFellow("contactPersonIdIntBox")).getValue();
		String contactPersonName = ((CapsTextbox)this.getFellow("contactPersonNameTextBox")).getValue();
		Integer accountNo = ((Intbox)this.getFellow("accountNoIntBox")).getValue();
		String accountName = ((CapsTextbox)this.getFellow("accountNameTextBox")).getValue();
		String masterLoginId = ((CapsTextbox)this.getFellow("masterLoginIdTextBox")).getValue();
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		try{
			
			List<IttbCpLogin> logins = this.businessHelper.getPortalBusiness().searchPortalUser(
					userLoginId, cardNo, contactPersonId, contactPersonName, accountNo, accountName, masterLoginId);
			
			HashMap<String, String> mapCheckString = new HashMap<String, String>();
			String noMaster = "";
			
			if(logins.size()>0){
				
				if(logins.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(IttbCpLogin login : logins){
					Listitem item = new Listitem();
					item.appendChild(newListcell(login.getComp_id().getLoginId()));
					item.appendChild(newListcell(login.getComp_id().getLoginType()));
					
					AmtbAccount account;
					if(login.getAmtbContactPerson()!=null){
//						item.appendChild(newListcell(login.getAmtbContactPerson().getContactPersonNo(), StringUtil.GLOBAL_STRING_FORMAT));
						item.appendChild(newListcell(login.getAmtbContactPerson().getContactPersonNo().toString()));
						item.appendChild(newListcell(login.getAmtbContactPerson().getMainContactName()));
						item.appendChild(newListcell(null));
						
						account = login.getAmtbContactPerson().getAmtbAccount();
						while(account.getAmtbAccount()!=null)
							account = account.getAmtbAccount();
					}
					else{
						item.appendChild(newListcell(null, StringUtil.GLOBAL_EMPTY_NUMERIC_FORMAT));
						item.appendChild(newListcell(null));
						item.appendChild(newListcell(login.getPmtbProduct().getCardNo()));
						
						account = login.getPmtbProduct().getAmtbAccount();
						while(account.getAmtbAccount()!=null)
							account = account.getAmtbAccount();
					}
					
					String checkString = "";
					String masterTagAccount = "";
					if(!account.getIttbCpMasterTagAcct().isEmpty())
					{
						if(account.getIttbCpMasterTagAcct().size() > 0)
						{
							for(IttbCpMasterTagAcct cpMasterTag : account.getIttbCpMasterTagAcct())
							{
								if(masterTagAccount.trim().equals(""))
									masterTagAccount = cpMasterTag.getMasterLoginNo().getLoginId();
								else
									masterTagAccount += ", "+cpMasterTag.getMasterLoginNo().getLoginId();
							}
						}
					}
					checkString = account.getAccountNo()+masterTagAccount;
					if(mapCheckString.get(checkString) == null) 
					{
						Listitem item2 = new Listitem();
						
						if(!masterTagAccount.trim().equals(""))
							item2.appendChild(newListcell(masterTagAccount));
						else if(noMaster != "Y2"){
							item2.appendChild(newListcell("No Master Account"));
							noMaster = "Y";
						}
						
						if(noMaster != "Y2")
						{
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							item2.appendChild(newListcell(""));
							resultListBox.appendChild(item2);
							mapCheckString.put(checkString, checkString);
							
							if(noMaster == "Y")
								noMaster = "Y2";
						}
					}
					
					
//					item.appendChild(newListcell(new Integer(account.getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(new Integer(account.getCustNo()).toString()));
					item.appendChild(newListcell(account.getAccountName()));
//					item.appendChild(newListcell(login.getCreatedDt(), DateUtil.TRIPS_DATE_FORMAT));
					if(login.getCreatedDt() != null)
						item.appendChild(newListcell(login.getCreatedDt().toString()));
					else
						item.appendChild(newListcell(""));
					
					item.appendChild(newListcell(login.getCreatedBy()));
					item.appendChild(new Listcell(com.cdgtaxi.ibs.acl.Constants.STATUS_MAP.get(login.getStatus())));
					
					resultListBox.appendChild(item);
				}
				
				if(logins.size()>ConfigurableConstants.getMaxQueryResult())
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
	
	public void reset(){
		logger.info("");
		((CapsTextbox)this.getFellow("userLoginIdTextBox")).setText("");
		((Decimalbox)this.getFellow("cardNoDecimalBox")).setText("");
		((Intbox)this.getFellow("contactPersonIdIntBox")).setText("");
		((CapsTextbox)this.getFellow("contactPersonNameTextBox")).setText("");
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		((CapsTextbox)this.getFellow("accountNameTextBox")).setText("");
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		if(resultListBox.getListfoot()==null){
			resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
		}
		
		resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultListBox.setPageSize(10);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
