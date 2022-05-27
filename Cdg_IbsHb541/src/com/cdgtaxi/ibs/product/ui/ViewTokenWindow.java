package com.cdgtaxi.ibs.product.ui;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class ViewTokenWindow extends CommonWindow implements AfterCompose {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewTokenWindow.class);

//	private final BmtbInvoiceHeader invoiceHeader;
	private String tokenId;
	private Label tokenIdLabel;
	private Label tokenExpiryLabel;
	private Label creditCardNoLabel;
	private Label creditCardNoExpiryLabel;
	private Listbox acctResultList;
	private Listbox prodResultList;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		// retrieve account details
		Map params = Executions.getCurrent().getArg();
		tokenId = (String) params.get("tokenId");

		tokenIdLabel = (Label) getFellow("tokenIdLabel");
		tokenIdLabel.setValue(tokenId);

		ProductSearchCriteria searchForm = new ProductSearchCriteria();
		searchForm.setTokenId(tokenId);
		// this will always return 1 record since tokenId is unique
		List<IttbRecurringCharge> rc = this.businessHelper.getProductBusiness().searchRC(searchForm);
		
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
		tokenExpiryLabel = (Label) getFellow("tokenExpiryLabel");
		tokenExpiryLabel.setValue(simpleDateFormat.format(rc.get(0).getTokenExpiry()));
		
		creditCardNoLabel = (Label) getFellow("creditCardNoLabel");
		creditCardNoLabel.setValue(rc.get(0).getCreditCardNo());
		
		creditCardNoExpiryLabel = (Label) getFellow("creditCardNoExpiryLabel");
		creditCardNoExpiryLabel.setValue(simpleDateFormat.format(rc.get(0).getCreditCardNoExpiry()));
		
		acctResultList = (Listbox) this.getFellow("acctResultList");
		prodResultList = (Listbox) this.getFellow("prodResultList");
	}

	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException {
		
		List<IttbRecurringChargeTagAcct> rcTagAcct = this.businessHelper.getProductBusiness().getRecurringChargeTagAcct(tokenId);
		List<IttbRecurringChargeTagCard> rcTagCard = this.businessHelper.getProductBusiness().getRecurringChargeTagCard(tokenId);
		
		for (IttbRecurringChargeTagAcct tagAcct: rcTagAcct) {
			Map<String, String> acctDetails2 = new LinkedHashMap<String, String>();
			Listitem acct = new Listitem();
			acctResultList.appendChild(acct);
			AmtbAccount topLvlAccount = tagAcct.getAmtbAccount();
			AmtbAccount secondAccount = null;
			AmtbAccount thirdAccount = null;
			int x = 1;
			while(topLvlAccount.getAmtbAccount() != null)
			{
				if(x==2) 
					thirdAccount = secondAccount;
			
				secondAccount = topLvlAccount;
				topLvlAccount = topLvlAccount.getAmtbAccount();
				x++;
			}
			
			String secondAccountName = "-";
			String thirdAccountName = "-";
			if(secondAccount != null)
				secondAccountName = secondAccount.getAccountName() +" ("+ secondAccount.getCode() +")";
			if(thirdAccount != null)
				thirdAccountName = thirdAccount.getAccountName() +" ("+ thirdAccount.getCode() +")";;
				

			acctDetails2.put("acctNo", tagAcct.getAmtbAccount().getAccountNo().toString());
			acctDetails2.put("mainCustNo", topLvlAccount.getCustNo());
			acctDetails2.put("mainAcctName", topLvlAccount.getAccountName());
			acctDetails2.put("acctCategory", tagAcct.getAmtbAccount().getAccountCategory().toString());

			acct.setValue(acctDetails2);
			acct.appendChild(newListcell(topLvlAccount.getCustNo()));
			acct.appendChild(newListcell(topLvlAccount.getAccountName()));
				
			acct.appendChild(newListcell(secondAccountName));
			acct.appendChild(newListcell(thirdAccountName));
			
			Image deleteImage = new Image("/images/delete.png");
			deleteImage.setStyle("cursor:pointer");
			ZScript showInfo = ZScript.parseContent("viewTokenWindow.delete(self.getParent().getParent())");
			showInfo.setLanguage("java");
			EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			deleteImage.addEventHandler("onClick", event);
			Listcell imageCell = new Listcell();
			imageCell.appendChild(deleteImage);
			acct.appendChild(imageCell);
			acctResultList.appendChild(acct);
		}
		for (IttbRecurringChargeTagCard tagCard: rcTagCard) {
			Listitem card = new Listitem();
			prodResultList.appendChild(card);

			AmtbAccount acct = this.businessHelper.getAccountBusiness().getAccountWithParent(""+tagCard.getPmtbProduct().getAmtbAccount().getAccountNo());
			
			Map<String, String> cardDetail =  new LinkedHashMap<String, String>();
			cardDetail.put("cardNo", tagCard.getPmtbProduct().getCardNo());
			cardDetail.put("cardType", tagCard.getPmtbProduct().getPmtbProductType().getName());
			card.setValue(cardDetail);
			card.appendChild(newListcell(tagCard.getPmtbProduct().getCardNo()));
			card.appendChild(newListcell(tagCard.getPmtbProduct().getPmtbProductType().getName()));
			
			if(acct != null)
			{
				AmtbAccount topLvlAccount = acct;
				AmtbAccount secondAccount = null;
				AmtbAccount thirdAccount = null;
				int x = 1;
				while(topLvlAccount.getAmtbAccount() != null)
				{
					if(x==2) 
						thirdAccount = secondAccount;
				
					secondAccount = topLvlAccount;
					topLvlAccount = topLvlAccount.getAmtbAccount();
					x++;
				}
				
				String secondAccountName = "-";
				String thirdAccountName = "-";
				if(secondAccount != null)
					secondAccountName = secondAccount.getAccountName() +" ("+ secondAccount.getCode() +")";
				if(thirdAccount != null)
					thirdAccountName = thirdAccount.getAccountName() +" ("+ thirdAccount.getCode() +")";;
					
				card.appendChild(newListcell(topLvlAccount.getCustNo()));
				card.appendChild(newListcell(secondAccountName));
				card.appendChild(newListcell(thirdAccountName));
				
			}
			else
			{
				card.appendChild(newListcell("-"));
				card.appendChild(newListcell("-"));
				card.appendChild(newListcell("-"));
			}
			
			Image deleteImage = new Image("/images/delete.png");
			deleteImage.setStyle("cursor:pointer");
			ZScript showInfo = ZScript.parseContent("viewTokenWindow.delete(self.getParent().getParent())");
			showInfo.setLanguage("java");
			EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			deleteImage.addEventHandler("onClick", event);
			Listcell imageCell = new Listcell();
			imageCell.appendChild(deleteImage);
			card.appendChild(imageCell);

			prodResultList.appendChild(card);	
		}
	}

	public void onCreate() {

	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		 
		

	}

	public void searchAccount() throws InterruptedException {
		logger.info("");

		// Retrieve entered input
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
		Integer customerNo = ((Intbox) this.getFellow("accountNoIntBox")).getValue();

		if (customerNo == null) {
			return;
		}
		// accountName still the same as selected one, skip
		if (accountNameComboBox.getSelectedItem() != null) {
			AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			if (customerNo.toString().equals(selectedAccount.getCustNo()))
				return;
		}

		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();

		try {

			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(customerNo.toString(), "");
			if (accounts != null && !accounts.isEmpty()) {
				for (AmtbAccount account : accounts) {
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if (accounts.size() == 1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				}
			} else {
				// Clear list for every new search
				accountNameComboBox.getChildren().clear();
				((Intbox) this.getFellow("accountNoIntBox")).setValue(null);
				// Set focus back to accountText
				((Intbox) this.getFellow("accountNoIntBox")).setFocus(true);
				Messagebox.show("There is no such account in the system", "Information", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectAccountName() throws InterruptedException {
		logger.info("");

		try {
			Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox) this.getFellow("accountNoIntBox");

			// Fix to bypass IE6 issue with double spacing
			if (accountNameComboBox.getChildren().size() == 1)
				accountNameComboBox.setSelectedIndex(0);

			if (accountNameComboBox.getSelectedItem() != null) {
				AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchAccountName(String name) throws InterruptedException {
		logger.info("");

		// Retrieve entered input
		Combobox accountNameComboBox = (Combobox) this.getFellow("accountNameComboBox");

		if (name == null || "".equals(name) || (name.length() < 3)) {
			return;
		}

		if (accountNameComboBox.getSelectedItem() != null) {
			AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			if (name.equals(selectedAccount.getAccountName() + " (" + selectedAccount.getCustNo() + ")"))
				return;
		}

		((Intbox) this.getFellow("accountNoIntBox")).setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();

		try {

			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(null, name);
			if (accounts != null && !accounts.isEmpty()) {
				for (AmtbAccount account : accounts) {
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName() + " (" + account.getCustNo() + ")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if (accounts.size() == 1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				} else
					accountNameComboBox.open();
			}

		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void searchCard() throws InterruptedException {

		String cardNo = ((Decimalbox) this.getFellow("cardNoDecimalBox")).getText();

		try {
			// if data exist
			if (cardNo != null && cardNo.length() != 0) {
				// try parsing it to integer
				Long custNo = Long.parseLong(cardNo);
				// if account number is lesser than 0 = invalid. setting string to null
				if (custNo < 0) {
					Messagebox.show("Invalid number for Card No.", "Search Card No",
							Messagebox.OK, Messagebox.EXCLAMATION);
					cardNo = null;
				}
			}
			else
			{
				cardNo = null;
				Messagebox.show("Invalid number for Card No.", "Search Card No",
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} catch (NumberFormatException nfe) {
			// Shouldn't happen
			nfe.printStackTrace();
			Messagebox.show("Invalid format for Card No.", "Search Card No",
					Messagebox.OK, Messagebox.EXCLAMATION);
			cardNo = null;
		}
		
		if(cardNo != null)
		{
			PmtbProduct pmtbProduct = this.businessHelper.getProductBusiness().getProductByCard(cardNo);

			Listbox resultCardListBox = (Listbox) this.getFellow("cardResultList");
			resultCardListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultCardListBox.getItems().clear();
	
			Map<String, String> cardDetails2 = new LinkedHashMap<String, String>();
			
			if (pmtbProduct != null) {
				AmtbAccount acct = this.businessHelper.getAccountBusiness().getAccountWithParent(""+pmtbProduct.getAmtbAccount().getAccountNo());
				Listitem card = new Listitem();
				// Listitem acct = new Listitem();
				resultCardListBox.appendChild(card);
	
				card.appendChild(newListcell(pmtbProduct.getCardNo()));
				card.appendChild(newListcell(pmtbProduct.getPmtbProductType().getName()));

				if(acct != null)
				{
					AmtbAccount topLvlAccount = acct;
					AmtbAccount secondAccount = null;
					AmtbAccount thirdAccount = null;
					int x = 1;
					while(topLvlAccount.getAmtbAccount() != null)
					{
						if(x==2) 
							thirdAccount = secondAccount;
					
						secondAccount = topLvlAccount;
						topLvlAccount = topLvlAccount.getAmtbAccount();
						x++;
					}
					
					String secondAccountName = "-";
					String thirdAccountName = "-";
					if(secondAccount != null)
						secondAccountName = secondAccount.getAccountName() +" ("+ secondAccount.getCode() +")";
					if(thirdAccount != null)
						thirdAccountName = thirdAccount.getAccountName() +" ("+ thirdAccount.getCode() +")";;
						
					card.appendChild(newListcell(topLvlAccount.getCustNo()));
					card.appendChild(newListcell(secondAccountName));
					card.appendChild(newListcell(thirdAccountName));
					

					cardDetails2.put("acctNo", topLvlAccount.getCustNo());
					cardDetails2.put("div", secondAccountName);
					cardDetails2.put("dept", thirdAccountName);
				}
				else
				{
					card.appendChild(newListcell("-"));
					card.appendChild(newListcell("-"));
					card.appendChild(newListcell("-"));
					
					cardDetails2.put("acctNo", "-");
					cardDetails2.put("div", "-");
					cardDetails2.put("dept", "-");
				}
				
				
				cardDetails2.put("cardNo", pmtbProduct.getCardNo());
				cardDetails2.put("cardType", pmtbProduct.getPmtbProductType().getName());
				card.setValue(cardDetails2);
				
				Listcell plusImageCell = new Listcell();
				Image plusImage = new Image("/images/add.png");
				plusImage.setStyle("cursor: pointer");
				// returns a listitem
				ZScript showInfo = ZScript.parseContent("viewTokenWindow.plusCardRow(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				plusImage.addEventHandler("onClick", pdEvent);
				plusImageCell.appendChild(plusImage);
				card.appendChild(plusImageCell);
				// remove dummy record 'no record found'
				resultCardListBox.appendChild(card);
				if (resultCardListBox.getListfoot() != null)
					resultCardListBox.removeChild(resultCardListBox.getListfoot());
			} else {
				if (resultCardListBox.getListfoot() == null) {
					resultCardListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
					Messagebox.show("No Card Found !", "Alert", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void search() throws InterruptedException {

		String custNoString = ((Intbox) this.getFellow("accountNoIntBox")).getText();
		try {
			// if data exist
			if (custNoString != null && custNoString.length() != 0) {
				// try parsing it to integer
				Long custNo = Long.parseLong(custNoString);
				// if account number is lesser than 0 = invalid. setting string to null
				if (custNo < 0) {
					Messagebox.show("Invalid number for account no.", "Search Accounts",
							Messagebox.OK, Messagebox.EXCLAMATION);
					custNoString = null;
				}
			}
			else
			{
				custNoString = null;
				Messagebox.show("Invalid number for account no.", "Search Accounts",
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} catch (NumberFormatException nfe) {
			// Shouldn't happen
			Messagebox.show("Invalid format for account no.", "Search Accounts",
					Messagebox.OK, Messagebox.EXCLAMATION);
			custNoString = null;
		}

		if(custNoString != null)
		{
			Listbox resultListBox = (Listbox) this.getFellow("resultList");
			resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
			resultListBox.getItems().clear();
	
			try {
	
				Map<Integer, Map<String, String>> results = this.businessHelper.getAccountBusiness()
						.searchAllAccountsByParentAcct(custNoString);
	
				if (results.size() > 0) {
	
					if (results.size() > ConfigurableConstants.getMaxQueryResult())
						Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK,
								Messagebox.INFORMATION);
	
					for (Integer custNo : results.keySet()) {
						Listitem acct = new Listitem();
						resultListBox.appendChild(acct);
						Map<String, String> acctDetails = results.get(custNo);
						Map<String, String> acctDetails2 = new LinkedHashMap<String, String>();
						acctDetails2.put("acctNo", acctDetails.get("acctNo"));
						if(acctDetails.get("mainCustNo")!=null) {
							acctDetails2.put("mainCustNo", acctDetails.get("mainCustNo"));
							acct.appendChild(newListcell(acctDetails.get("mainCustNo")));
						}else {
							acct.appendChild(newListcell(""));
						}
						
						if(acctDetails.get("mainAcctName")!=null) {
							acctDetails2.put("mainAcctName", acctDetails.get("mainAcctName"));
							acct.appendChild(newListcell(acctDetails.get("mainAcctName")));
						}else {
							acct.appendChild(newListcell(""));
						}
						
						if(acctDetails.get("div") != null  || acctDetails.get("sapp") != null)
						{
							if(acctDetails.get("div") != null)
							{
								if(acctDetails.get("div")!=null) {
									acctDetails2.put("div", acctDetails.get("div"));
									acct.appendChild(newListcell(acctDetails.get("div")));
								}else {
									acct.appendChild(newListcell(""));
								}
							}
							else if(acctDetails.get("sapp") != null)
							{
								if(acctDetails.get("sapp")!=null) {
									acctDetails2.put("div", acctDetails.get("sapp"));
									acct.appendChild(newListcell(acctDetails.get("sapp")));
								}else {
									acct.appendChild(newListcell(""));
								}
							}
						}	
						if(acctDetails.get("dept")!=null) {
							acctDetails2.put("dept", acctDetails.get("dept"));
							acct.appendChild(newListcell(acctDetails.get("dept")));
						}else {
							acct.appendChild(newListcell(""));
						}
							
						acct.setValue(acctDetails2);
						Listcell plusImageCell = new Listcell();
						Image plusImage = new Image("/images/add.png");
						plusImage.setStyle("cursor: pointer");
						// returns a listitem
						ZScript showInfo = ZScript.parseContent("viewTokenWindow.plusRow(self.getParent().getParent())");
						showInfo.setLanguage("java");
						EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						plusImage.addEventHandler("onClick", pdEvent);
						plusImageCell.appendChild(plusImage);
						acct.appendChild(plusImageCell);
	
						resultListBox.appendChild(acct);
					}
	
					if (results.size() > ConfigurableConstants.getMaxQueryResult())
						resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
	
					if (resultListBox.getListfoot() != null)
						resultListBox.removeChild(resultListBox.getListfoot());
				} else {
					if (resultListBox.getListfoot() == null) {
						resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
					}
				}
	
				resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultListBox.setPageSize(10);
			} catch (Exception e) {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
						Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
			}
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		item.detach();
	}

	public void plusRow(Listitem item) throws InterruptedException {
		try {
			Map<String, String> acctDetails = (Map<String, String>) item.getValue();
			boolean found = false;
			ProductSearchCriteria searchForm = new ProductSearchCriteria();
			searchForm.setAccNo(acctDetails.get("acctNo"));
			// this will always return 1 record since tokenId is unique
			List<IttbRecurringCharge> rcForAcct = this.businessHelper.getProductBusiness().searchRC2(searchForm);
			if(rcForAcct!=null) {
				for(IttbRecurringCharge rc : rcForAcct ) {
					rc.getTokenExpiry().after(new Date());
					found = true;
				}
			}
			if(found) {
				//this means that the card is associated with an token and it should not be added to this
				Messagebox.show("Acct is already tagged to a token that is not expired, please use another", "Error", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			//init size is 2 even if it's empty, not sure why
			if(prodResultList.getChildren().size()>2) {
				Messagebox.show("Please only add in either Product or Account section", "Error", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			
			Boolean checkContinue = true;
			
			for (Object obj : acctResultList.getChildren()) {
				try {
					Listitem listacct = (Listitem) obj;
					if (listacct.getValue().equals(item.getValue())) {
						checkContinue = false;
						Messagebox.show("Have already added this account no.", "Error", Messagebox.OK,
								Messagebox.ERROR);
						break;
					}
				} catch (ClassCastException e) {
				}
			}

			if (checkContinue) {
				Listitem acct = new Listitem();
				acctResultList.appendChild(acct);

				
				Map<String, String> acctDetails2 = new LinkedHashMap<String, String>();
				
				acctDetails2.put("acctNo", acctDetails.get("acctNo"));
				if(acctDetails.get("mainCustNo")!=null) {
					acctDetails2.put("mainCustNo", acctDetails.get("mainCustNo"));
					acct.appendChild(newListcell(acctDetails.get("mainCustNo")));
				}else {
					acct.appendChild(newListcell(""));
				}
				
				if(acctDetails.get("mainAcctName")!=null) {
					acctDetails2.put("mainAcctName", acctDetails.get("mainAcctName"));
					acct.appendChild(newListcell(acctDetails.get("mainAcctName")));
				}else {
					acct.appendChild(newListcell(""));
				}
				
				if(acctDetails.get("div")!=null) {
					acctDetails2.put("div", acctDetails.get("div"));
					acct.appendChild(newListcell(acctDetails.get("div")));
				}else {
					acct.appendChild(newListcell(""));
				}
					
				if(acctDetails.get("dept")!=null) {
					acctDetails2.put("dept", acctDetails.get("dept"));
					acct.appendChild(newListcell(acctDetails.get("dept")));
				}else {
					acct.appendChild(newListcell(""));
				}
				acct.setValue(acctDetails2);
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("viewTokenWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				acct.appendChild(imageCell);

				acctResultList.appendChild(acct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void plusCardRow(Listitem item) throws InterruptedException {
		try {
			Boolean checkContinue = true;
			Map<String, String> cardDetail = (Map<String, String>) item.getValue();
			boolean found = false;
			ProductSearchCriteria searchForm = new ProductSearchCriteria();
			searchForm.setCardNoStart(cardDetail.get("cardNo"));
			// this will always return 1 record since tokenId is unique
			List<IttbRecurringCharge> rcForCard = this.businessHelper.getProductBusiness().searchRC2(searchForm);
			if(rcForCard!=null) {
				for(IttbRecurringCharge rc : rcForCard ) {
					rc.getTokenExpiry().after(new Date());
					found = true;
				}
			}
			if(found) {
				//this means that the card is associated with an token and it should not be added to this
				Messagebox.show("Card is already tagged to a token that is not expired, please use another", "Error", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			//init size is 2 even if it's empty, not sure why
			if(acctResultList.getChildren().size()>2) {
				Messagebox.show("Please only add in either Product or Account section", "Error", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			
			for (Object obj : prodResultList.getChildren()) {
				try {
					Listitem listCard = (Listitem) obj;

					if (listCard.getValue().equals(item.getValue())) {
						checkContinue = false;
						Messagebox.show("Have already added this card no.", "Error", Messagebox.OK,
								Messagebox.ERROR);
						break;
					}
				} catch (ClassCastException e) {
				}
			}

			if (checkContinue) {
				Listitem card = new Listitem();
				prodResultList.appendChild(card);

				//Map<String, String> cardDetail = (Map<String, String>) item.getValue();

				card.appendChild(newListcell(cardDetail.get("cardNo"), StringUtil.GLOBAL_STRING_FORMAT));
				card.appendChild(newListcell(cardDetail.get("cardType"), StringUtil.GLOBAL_STRING_FORMAT));
				card.appendChild(newListcell(cardDetail.get("acctNo"), StringUtil.GLOBAL_STRING_FORMAT));
				card.appendChild(newListcell(cardDetail.get("div"), StringUtil.GLOBAL_STRING_FORMAT));
				card.appendChild(newListcell(cardDetail.get("dept"), StringUtil.GLOBAL_STRING_FORMAT));
				card.setValue(cardDetail);
				Image deleteImage = new Image("/images/delete.png");
				deleteImage.setStyle("cursor:pointer");
				ZScript showInfo = ZScript.parseContent("viewTokenWindow.delete(self.getParent().getParent())");
				showInfo.setLanguage("java");
				EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
				deleteImage.addEventHandler("onClick", event);
				Listcell imageCell = new Listcell();
				imageCell.appendChild(deleteImage);
				card.appendChild(imageCell);

				prodResultList.appendChild(card);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() throws InterruptedException{
		try{
			this.displayProcessing();
			
			//get FullList of before
			List<IttbRecurringChargeTagAcct> RecurringChargeTagAcctBeforeList = this.businessHelper.getProductBusiness().getRecurringChargeTagAcct(tokenId);
			List<IttbRecurringChargeTagCard> RecurringChargeTagCardBeforeList = this.businessHelper.getProductBusiness().getRecurringChargeTagCard(tokenId);
			
			List<IttbRecurringChargeTagAcct> RecurringChargeTagAcctAfterList = new ArrayList<IttbRecurringChargeTagAcct>();
			List<IttbRecurringChargeTagAcct> RecurringChargeTagAcctDeleteList = new ArrayList<IttbRecurringChargeTagAcct>();
			
			List<IttbRecurringChargeTagCard> RecurringChargeTagCardAfterList = new ArrayList<IttbRecurringChargeTagCard>();
			List<IttbRecurringChargeTagCard> RecurringChargeTagCardDeleteList = new ArrayList<IttbRecurringChargeTagCard>();
			
			//START OF TAG ACCOUNT
			for(Object obj : acctResultList.getChildren()) {
				try{
					Map<String, String> acctDetails = (Map<String, String>)  ((Listitem)obj).getValue();
					AmtbAccount account = this.businessHelper.getAccountBusiness().getAccount(acctDetails.get("acctNo"));
					IttbRecurringChargeTagAcct rcTagAccount = new IttbRecurringChargeTagAcct();
					ProductSearchCriteria searchForm = new ProductSearchCriteria();
					searchForm.setTokenId(tokenId);
					// this will always return 1 record since tokenId is unique
					List<IttbRecurringCharge> rc = this.businessHelper.getProductBusiness().searchRC(searchForm);
					rcTagAccount.setRecurringChargeId(rc.get(0));
					rcTagAccount.setAmtbAccount(account);					
					RecurringChargeTagAcctAfterList.add(rcTagAccount);
				}catch(ClassCastException e) {}
			}
			
			//go Thru Before
			//if inside before but not in after ==delete
			//if inside both = update
			for(IttbRecurringChargeTagAcct beforeAcct : RecurringChargeTagAcctBeforeList) {
				boolean gotInBefore = false;
				for(IttbRecurringChargeTagAcct afterAcct : RecurringChargeTagAcctAfterList) {
					if(afterAcct.getAmtbAccount().getAccountNo().equals(beforeAcct.getAmtbAccount().getAccountNo())) 
					{
						gotInBefore = true;
						break;
					}
				}
				
				if(gotInBefore) {
					this.businessHelper.getGenericBusiness().update(beforeAcct, getUserLoginIdAndDomain());	
				}
				else
					RecurringChargeTagAcctDeleteList.add(beforeAcct);
				
			}
			//go thru after
			//if inside after but not in before == add 
			for(IttbRecurringChargeTagAcct afterAcct : RecurringChargeTagAcctAfterList) {
				Boolean gotInAfter = false;
				for(IttbRecurringChargeTagAcct beforeAcct : RecurringChargeTagAcctBeforeList) {
					if(beforeAcct.getAmtbAccount().getAccountNo().equals(afterAcct.getAmtbAccount().getAccountNo())) 
					{
						gotInAfter = true;
						break;
					}
				}
				
				if(!gotInAfter)
					this.businessHelper.getGenericBusiness().save(afterAcct, getUserLoginIdAndDomain());
			}
			
			if(RecurringChargeTagAcctDeleteList.size()>0) {
				for(IttbRecurringChargeTagAcct delAcct : RecurringChargeTagAcctDeleteList)
					this.businessHelper.getGenericBusiness().delete(delAcct);	
			}

			//END OF TAG ACCOUNT
			
			
			//START OF TAG CARD
			for(Object obj : prodResultList.getChildren()) {
				try{
					Map<String, String> prodDetails = (Map<String, String>)  ((Listitem)obj).getValue();
					PmtbProduct prod = this.businessHelper.getProductBusiness().getProductByCard(prodDetails.get("cardNo"));
					IttbRecurringChargeTagCard rcTagCard = new IttbRecurringChargeTagCard();
					ProductSearchCriteria searchForm = new ProductSearchCriteria();
					searchForm.setTokenId(tokenId);
					// this will always return 1 record since tokenId is unique
					List<IttbRecurringCharge> rc = this.businessHelper.getProductBusiness().searchRC(searchForm);
					rcTagCard.setRecurringChargeId(rc.get(0));
					rcTagCard.setPmtbProduct(prod);					
					RecurringChargeTagCardAfterList.add(rcTagCard);
				}catch(ClassCastException e) {}
			}
			
			//go Thru Before
			//if inside before but not in after ==delete
			//if inside both = update
			for(IttbRecurringChargeTagCard beforeCard : RecurringChargeTagCardBeforeList) {
				
				boolean gotInBefore = false;
				for(IttbRecurringChargeTagCard afterCard : RecurringChargeTagCardAfterList) {
					if(afterCard.getPmtbProduct().getCardNo().equals(beforeCard.getPmtbProduct().getCardNo())) 
					{
						gotInBefore = true;
						break;
					}
				}
				
				if(gotInBefore) {
					this.businessHelper.getGenericBusiness().update(beforeCard, getUserLoginIdAndDomain());	
				}
				else
					RecurringChargeTagCardDeleteList.add(beforeCard);
				
			}
			//go thru after
			//if inside after but not in before == add 
			for(IttbRecurringChargeTagCard afterCard : RecurringChargeTagCardAfterList) {
				
				Boolean gotInAfter = false;
				for(IttbRecurringChargeTagCard beforeCard : RecurringChargeTagCardBeforeList) {
					if(beforeCard.getPmtbProduct().getCardNo().equals(afterCard.getPmtbProduct().getCardNo())) 
					{
						gotInAfter = true;
						break;
					}
				}
				
				if(!gotInAfter)
					this.businessHelper.getGenericBusiness().save(afterCard, getUserLoginIdAndDomain());	
			}

			//END OF TAG CARD
			if(RecurringChargeTagCardDeleteList.size()>0) {
				for(IttbRecurringChargeTagCard delCard : RecurringChargeTagCardDeleteList)
					this.businessHelper.getGenericBusiness().delete(delCard);	
			}
			
			
			//update rc lastupdateddate and updatedby
			List<IttbRecurringCharge> rc = this.businessHelper.getProductBusiness().searchRConly(tokenId);
			for(IttbRecurringCharge rconly : rc)
			{
				rconly.setUpdatedBy(getUserLoginIdAndDomain());
				rconly.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
				this.businessHelper.getGenericBusiness().update(rconly, getUserLoginIdAndDomain());
			}
			
			Messagebox.show("Token Information updated", "Token", Messagebox.OK, Messagebox.INFORMATION);

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

}
