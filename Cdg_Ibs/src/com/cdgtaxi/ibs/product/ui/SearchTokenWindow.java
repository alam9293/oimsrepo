package com.cdgtaxi.ibs.product.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.AccountSearchUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class SearchTokenWindow extends CommonWindow implements AfterCompose {

	private static Logger logger = Logger.getLogger(SearchTokenWindow.class);

	private String productNo = "";
	private Listbox resultList;
	protected Listbox resultLB;
	protected Datebox tokenDateFromField;
	protected Datebox tokenDateToField;
	protected Combobox accountNameComboBox;
	protected Datebox ccDateFromField;
	protected Datebox ccDateToField;


	public void afterCompose(){

		// super.onCreate(ce);
		accountNameComboBox = (Combobox)getFellow("accountNameComboBox");
		// invoiceNo = (Longbox) getFellow("invoiceNo");
		// cardNo = (Textbox) getFellow("cardNo");
		tokenDateFromField = (Datebox) getFellow("tokenDateFromField");
		tokenDateToField = (Datebox) getFellow("tokenDateToField");

		ccDateFromField = (Datebox) getFellow("ccDateFromField");
		ccDateToField = (Datebox) getFellow("ccDateToField");
		
		resultLB = (Listbox) getFellow("resultList");
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	public void SearchTokenWindow() throws SuspendNotAllowedException, InterruptedException {

	}

	public void search() throws InterruptedException {
//		try
//		{
//			logger.info("start test");
//            logger.info("END");
//		}
//		catch(Exception e)
//		{
//			logger.info("GOT ERROR");
//			e.printStackTrace();
//			logger.info("test error : "+e.getMessage());
//			logger.info("test error : "+e.getStackTrace());
//		}
//		logger.info("end test");
		
		resultLB.getItems().clear();
		
		displayProcessing();

		String tokenId = (String) ((CapsTextbox) this.getFellow("tokenId")).getText();
		String cardNo = (String) ((CapsTextbox) this.getFellow("cardNo")).getText();
		ProductSearchCriteria form = new ProductSearchCriteria();
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		if (accountNameComboBox.getSelectedItem() != null) {
			String selectedAccountNo = (String) accountNameComboBox.getSelectedItem().getValue();
			form.setAccNo(selectedAccountNo);
		}
		if ((tokenDateFromField.getValue() == null && tokenDateToField.getValue() != null)
				|| (tokenDateFromField.getValue() != null && tokenDateToField.getValue() == null)) {
			Messagebox.show("Please fill in at both Token From and Token To Date");
			return;
		}
		tokenDateFromField = (Datebox) getFellow("tokenDateFromField");
		tokenDateToField = (Datebox) getFellow("tokenDateToField");

		if ((ccDateFromField.getValue() == null && ccDateToField.getValue() != null)
				|| (ccDateFromField.getValue() != null && ccDateToField.getValue() == null)) {
			Messagebox.show("Please fill in at both Credit Card From and Credit Card To Date");
			return;
		}
		ccDateFromField = (Datebox) getFellow("ccDateFromField");
		ccDateToField = (Datebox) getFellow("ccDateToField");
		
		form.setCardNoStart(cardNo);
		form.setTokenId(tokenId);
		form.setExpiryDateFrom(tokenDateFromField.getValue());
		form.setExpiryDateTo(tokenDateToField.getValue());
		form.setCcExpiryDateFrom(ccDateFromField.getValue());
		form.setCcExpiryDateTo(ccDateToField.getValue());

		List<IttbRecurringCharge> rcList = businessHelper.getProductBusiness().searchRC(form);
		for (IttbRecurringCharge rc : rcList) {
			Listitem item = new Listitem();
			String concateTagAcctName = "";
			String concateTagAcctNo = "";
			String concateTagCard = "";
			item.setValue(rc);
			Iterator rcI = rc.getIttbRecurringChargeTagAcct().iterator();
			
			HashMap<String, String> map = new HashMap<String,String>();
			int i = 0;
			while (rcI.hasNext()) {
				IttbRecurringChargeTagAcct rcItr = (IttbRecurringChargeTagAcct) rcI.next();
				
				AmtbAccount topLvlAccount = rcItr.getAmtbAccount();
				while(topLvlAccount.getAmtbAccount()!= null)
				{
					topLvlAccount = topLvlAccount.getAmtbAccount();
				}
				
				if(map.get(""+topLvlAccount.getCustNo()) == null)
				{
					if(i > 0)
					{
						concateTagAcctName += ",";
						concateTagAcctNo += ",";
					}
					concateTagAcctName += topLvlAccount.getAccountName();
					concateTagAcctNo += topLvlAccount.getCustNo();
					
					map.put(""+topLvlAccount.getCustNo(), topLvlAccount.getAccountName());

					i++;
				}
			}

			i = 0;
			int i2 = 0;
			Iterator rcI2 = rc.getIttbRecurringChargeTagCard().iterator();
			while (rcI2.hasNext()) {
				
				if (i>0) {
					concateTagCard += ", ";
				}
				
				IttbRecurringChargeTagCard rcItr2 = (IttbRecurringChargeTagCard) rcI2.next();
				concateTagCard += rcItr2.getPmtbProduct().getCardNo();
				
				AmtbAccount topLvlAccount = rcItr2.getPmtbProduct().getAmtbAccount();

				while(topLvlAccount.getAmtbAccount()!= null)
				{
					topLvlAccount = topLvlAccount.getAmtbAccount();
				}
				
				if(map.get(""+topLvlAccount.getCustNo()) == null)
				{
					if(i2 > 0)
					{
						concateTagAcctName += ",";
						concateTagAcctNo += ",";
					}
					
					concateTagAcctName += topLvlAccount.getAccountName();
					concateTagAcctNo += topLvlAccount.getCustNo();
					
					map.put(""+topLvlAccount.getCustNo(), topLvlAccount.getAccountName());
					i2++;
				}
				i++;
			}

			item.appendChild(newListcell(new String(concateTagAcctNo)));
			item.appendChild(newListcell(new String(concateTagAcctName)));
			item.appendChild(newListcell(new String(concateTagCard)));
			item.appendChild(newListcell(new String(rc.getTokenId())));
			item.appendChild(newListcell(new String(rc.getTokenExpiry().toString())));
			resultLB.appendChild(item);
		}
		
		if(rcList.size()>ConfigurableConstants.getMaxQueryResult())
			resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
	}

	public void displayTokenDetails() throws InterruptedException {
		try {
			Listitem selectedItem = resultLB.getSelectedItem();
			IttbRecurringCharge rc = (IttbRecurringCharge) selectedItem.getValue();

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("tokenId", rc.getTokenId());
			forward(Uri.VIEW_TOKEN, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			resultLB.clearSelection();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		((CapsTextbox) this.getFellow("tokenId")).setText(null);
		((CapsTextbox) this.getFellow("cardNo")).setText(null);
		((Combobox) this.getFellow("accountNameComboBox")).setValue(null);
		((Datebox) this.getFellow("tokenDateFromField")).setValue(null);
		((Datebox) this.getFellow("tokenDateToField")).setValue(null);
		((Datebox) this.getFellow("ccDateFromField")).setValue(null);
		((Datebox) this.getFellow("ccDateToField")).setValue(null);
	}

	public void searchAccountByAccountNo() throws InterruptedException {
		logger.info("");

		Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");
		accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		Integer accountNo = accountNoIntBox.getValue();

		if (accountNo == null)
			return;

		// accountName still the same as selected one, skip
		if (accountNameComboBox.getSelectedItem() != null) {
			String selectedAccountNo = (String) accountNameComboBox.getSelectedItem().getValue();
			AmtbAccount selectedAccount = (AmtbAccount) this.businessHelper.getGenericBusiness().get(AmtbAccount.class,
					new Integer(selectedAccountNo));
			if (accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		// Clear combobox for a new search
		accountNameComboBox.setText("");
		// Clear list for every new search
		accountNameComboBox.getChildren().clear();
		// Clear listed invoices + division + department
		// this.clear();

		try {
			AccountSearchUtil.populateAccountNameCbo(accountNameComboBox, accountNo.toString(), "");
			if (accountNameComboBox.getChildren().size() == 1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void reset() throws InterruptedException{
		((CapsTextbox) this.getFellow("tokenId")).setValue(null);
		((CapsTextbox) this.getFellow("cardNo")).setValue(null);
		((Intbox) this.getFellow("accountNoIntBox")).setValue(null);
		accountNameComboBox.setValue(null);
		
		Datebox issueDateFrom=(Datebox)this.getFellow("tokenDateFromField");
		issueDateFrom.setValue(null);
		Datebox issueDateTo=(Datebox)this.getFellow("tokenDateToField");
		issueDateTo.setValue(null);
		
		Datebox issueDateFrom2=(Datebox)this.getFellow("ccDateFromField");
		issueDateFrom2.setValue(null);
		Datebox issueDateTo2=(Datebox)this.getFellow("ccDateToField");
		issueDateTo2.setValue(null);
		
//		search();
		resultLB.getItems().clear();
	}

	public void searchAccountByAccountName(String name) throws InterruptedException {
		logger.info("");
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");

		// only begin new search if input is greater than 2
		if (name.length() < 3) {
			return;
		}

		// accountName still the same as selected one, skip
		if (accountNameComboBox.getSelectedItem() != null) {
			String selectedAccountNo = (String) accountNameComboBox.getSelectedItem().getValue();
			AmtbAccount selectedAccount = (AmtbAccount) this.businessHelper.getGenericBusiness().get(AmtbAccount.class,
					new Integer(selectedAccountNo));
			if (name.equals(selectedAccount.getAccountName() + " (" + selectedAccount.getCustNo() + ")")) {
				return;
			}
		}

		// clear textbox for a new search
		Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		// Clear list for every new search
		accountNameComboBox.getChildren().clear();
		// Clear listed invoices + division + department
		// this.clear();

		try {
			AccountSearchUtil.populateAccountNameCbo(accountNameComboBox, "", name);
			if (accountNameComboBox.getChildren().size() == 1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			} else
				accountNameComboBox.open();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectAccountName() throws InterruptedException {
		logger.debug("Account Name selected");
		accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		Intbox accountNoIntBox=(Intbox)this.getFellow("accountNoIntBox");
		// Fix to bypass IE6 issue with double spacing
		if (accountNameComboBox.getChildren().size() == 1)
			accountNameComboBox.setSelectedIndex(0);

			if(accountNameComboBox.getSelectedItem()!=null){
				String accNo = accountNameComboBox.getSelectedItem().getValue().toString();
				AmtbAccount amtbAccount = this.businessHelper.getAccountBusiness().getAccount(accNo);
				AmtbAccount topLvlAccount = amtbAccount;
				while(topLvlAccount.getAmtbAccount() != null)
					topLvlAccount = topLvlAccount.getAmtbAccount();
				
				accountNoIntBox.setText(topLvlAccount.getCustNo());
			}
	}
}