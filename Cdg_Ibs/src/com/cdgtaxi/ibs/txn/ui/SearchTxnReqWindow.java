package com.cdgtaxi.ibs.txn.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.ConcurrencyFailureException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReq;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReqFlow;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class SearchTxnReqWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(SearchTxnReqWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> txnStatus = new ArrayList<Listitem>();

	public SearchTxnReqWindow(){
		// adding all txn status
		txnStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.TXN_STATUS.keySet()){
			txnStatus.add(new Listitem(NonConfigurableConstants.TXN_STATUS.get(statusCode), statusCode));
		}
	}
	
	public List<Listitem> getProductTypes() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getAllProductTypes(), false);
		return cloneList(productTypeList);
	}
	
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
		}
		return returnList;
	}

	/**
	 * Search for all account
	 */
	public void init() throws InterruptedException{
		logger.info("init()");
			
		try
		{
			Listbox txnsReq = (Listbox)this.getFellow("txnsReq");
			// clearing any previous search
			txnsReq.getItems().clear();
			this.setRemarksVisible();
			
			// getting results from business layer
			List<TmtbTxnReviewReq> results = this.businessHelper.getTxnBusiness().searchTxnReqs();
			// for each result
			Checkbox checkAll = (Checkbox)this.getFellow("checkAll");
			checkAll.setChecked(false);
			if (results != null && !results.isEmpty())
			{
				for(TmtbTxnReviewReq txnReqItem : results)
				{
						// creating a new row and append it to rows
						Listitem txnReq = new Listitem();
						txnsReq.appendChild(txnReq);
						// setting the list as the value
						txnReq.setValue(txnReqItem);
						// User requirement to block off. Might turn on again. Do not remove
/*						if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(txnReqItem.getAmtbAccount().getAccountCategory()))
						{
							txnReq.appendChild(new Listcell(txnReqItem.getAmtbAccount().getAmtbAccount().getAmtbAccount().getCustNo()));
						}
						else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(txnReqItem.getAmtbAccount().getAccountCategory()))
						{
							txnReq.appendChild(new Listcell(txnReqItem.getAmtbAccount().getAmtbAccount().getCustNo()));			
						}
						else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(txnReqItem.getAmtbAccount().getAccountCategory()))
						{
							txnReq.appendChild(new Listcell(txnReqItem.getAmtbAccount().getCustNo()));
						}
						else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(txnReqItem.getAmtbAccount().getAccountCategory()))
						{
							txnReq.appendChild(new Listcell(txnReqItem.getAmtbAccount().getCustNo()));
						}
						else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(txnReqItem.getAmtbAccount().getAccountCategory()))
						{
							txnReq.appendChild(new Listcell(txnReqItem.getAmtbAccount().getAmtbAccount().getCustNo()));
						}*/
						
						if (txnReqItem.getTxnReviewReqNo() != null)
							txnReq.appendChild(newListcell(txnReqItem.getTxnReviewReqNo().toString()));
						else
							txnReq.appendChild(newListcell("-"));
						
						if (txnReqItem.getTmtbAcquireTxn().getJobNo() != null)
							txnReq.appendChild(newListcell(txnReqItem.getTmtbAcquireTxn().getJobNo()));
						else
							txnReq.appendChild(newListcell("-"));
						
						if (txnReqItem.getPmtbProduct() != null)
							txnReq.appendChild(newListcell(txnReqItem.getPmtbProduct().getCardNo()));
						else if (txnReqItem.getExternalCardNo() != null && !"".equals(txnReqItem.getExternalCardNo()))
							txnReq.appendChild(newListcell(txnReqItem.getExternalCardNo()));
						else
							txnReq.appendChild(newListcell("-"));
						
						if (txnReqItem.getPmtbProductType() != null)
							txnReq.appendChild(newListcell(txnReqItem.getPmtbProductType().getName()));
						else
							txnReq.appendChild(newListcell("-"));
						

						// Get status
						if (!txnReqItem.getTmtbTxnReviewReqFlows().isEmpty())
						{
							Iterator<TmtbTxnReviewReqFlow> txnReqFlows = txnReqItem.getTmtbTxnReviewReqFlows().iterator();
							// get the first record
							String txnReqStatus = NonConfigurableConstants.TRANSACTION_REQUEST_STATUS.get(txnReqFlows.next().getToStatus());
							txnReq.appendChild(newListcell(txnReqStatus));
						}
						else
							txnReq.appendChild(newListcell("-"));
						
						// Action
						if (txnReqItem.getActionTxn() != null)
							txnReq.appendChild(newListcell(txnReqItem.getActionTxn()));
						else
							txnReq.appendChild(newListcell("-"));
						
						// Get remarks
						if (!txnReqItem.getTmtbTxnReviewReqFlows().isEmpty())
						{
							Iterator<TmtbTxnReviewReqFlow> txnReqFlows = txnReqItem.getTmtbTxnReviewReqFlows().iterator();
							// get the first record
							String txnRemarks = txnReqFlows.next().getRemarks();
							txnReq.appendChild(newListcell(txnRemarks));
						}
						else
							txnReq.appendChild(newListcell("-"));
						
						//txnReq.appendChild(newListcell(txnReqItem.getTmtbAcquireTxn().getJobNo()));
						//txnReq.appendChild(newListcell(txnReqItem.getTmtbAcquireTxn().getTaxiNo()));
						//txnReq.appendChild(newListcell(txnReqItem.getTmtbAcquireTxn().getNric()));
						//if (txnReqItem.getFareAmt() != null)
						//	txnReq.appendChild(newListcell(txnReqItem.getFareAmt().setScale(2).toString()));
						//else
						//	txnReq.appendChild(newListcell("-"));
						
						//txnReq.appendChild(newListcell(DateUtil.convertDateToStr(txnReqItem.getTripEndDt(), "dd/MM/yyyy")));
						//txnReq.appendChild(newListcell(DateUtil.convertTimestampToStr(txnReqItem.getTripStartDt(),"dd/MM/yyyy")));					
						Listcell lastCell=new Listcell();
						lastCell.appendChild(new Checkbox());
						txnReq.appendChild(lastCell);
				}
				txnsReq.setVisible(true);
				
			}else{
				// show empty
				// Make the textbox disappear

				Listitem txnReq = new Listitem();
				txnsReq.appendChild(txnReq);
				Listcell newCell = new Listcell("No pending trips adjustment for approval");
				newCell.setSpan(5);
				txnReq.appendChild(newCell);
				txnsReq.setVisible(true);
				txnsReq.setDisabled(true);
				
				this.setRemarksInvisible();
				
				//Messagebox.show("No pending trips adjustment for approval", "Transaction Requests", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void selectTxnReq() throws InterruptedException{
		logger.info("selectTxn()");
		// getting the list box
		Listbox txnsReq = (Listbox)this.getFellow("txnsReq");
		// getting the selected item
		Listitem selectedTxn = txnsReq.getSelectedItem();
		// creating params
		if (selectedTxn != null && selectedTxn.getValue() != null && !"".equals(selectedTxn.getValue()))
		{
			Map<String, String> params = new LinkedHashMap<String, String>();
			// getting the selected account details
			TmtbTxnReviewReq txnReqDetails = (TmtbTxnReviewReq) selectedTxn.getValue();
			// putting txnID into the params
			params.put("txnReqID", txnReqDetails.getTxnReviewReqNo().toString());

			// Forward to view page
			this.forward(Uri.APPROVE_TXN, params);
		}
	}
	
	public void approveSelectedTxnsReq() throws InterruptedException{
		logger.info("approveSelectedTxnsReq()");

		// Iterate the checkbox to select out those that had been marked
		try
		{
			List<Integer> requestIds = new ArrayList<Integer>();
			Listbox txnsReqs = (Listbox)this.getFellow("txnsReq");
			for(Object txnsReq : txnsReqs.getItems()){
				Checkbox checkbox = (Checkbox)((Listitem)txnsReq).getLastChild().getFirstChild();
				if(checkbox.isChecked()){
					requestIds.add(((TmtbTxnReviewReq)((Listitem)txnsReq).getValue()).getTxnReviewReqNo());
				}
			}
			if(requestIds.isEmpty()){
				Messagebox.show("No selected request!", "Approve Transaction Request", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			// *************Validation
			String remarks = ((Textbox)this.getFellow("appRemarks")).getValue();
//			if (remarks == null || "".equals(remarks))
//			{
//				Messagebox.show("Please enter the remarks", "Approve Transaction Request", Messagebox.OK, Messagebox.EXCLAMATION);
//				return;				
//			}
			if(Messagebox.show("Confirm Approve?", "Approve Transaction Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK)
			{
				this.businessHelper.getTxnBusiness().updateTxns(requestIds, remarks, getUserLoginIdAndDomain(), NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED);
				Messagebox.show("Approve Transaction Request", "Approved", Messagebox.OK, Messagebox.INFORMATION);
				// Go to main screen and do a refresh
				this.init();
			}
		}
		catch (ConcurrencyFailureException e)
		{
			Messagebox.show("There exists concurrent user approves or rejects the same trip. Please check whether trip has been approved or rejected.", "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch (WrongValueException wve)
		{
			Messagebox.show("Please enter the mandatory fields", "Approve Trip", Messagebox.OK, Messagebox.INFORMATION);
		}
		catch (Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}

	}
	
	public void rejectSelectedTxnsReq() throws InterruptedException{
		logger.info("approveSelectedTxnsReq()");
		// getting the list box
		
		// getting the selected item

		// Iterate the checkbox to select out those that had been marked
		try
		{
			List<Integer> requestIds = new ArrayList<Integer>();
			Listbox txnsReqs = (Listbox)this.getFellow("txnsReq");
			for(Object txnsReq : txnsReqs.getItems()){
				Checkbox checkbox = (Checkbox)((Listitem)txnsReq).getLastChild().getFirstChild();
				if(checkbox.isChecked()){
					requestIds.add(((TmtbTxnReviewReq)((Listitem)txnsReq).getValue()).getTxnReviewReqNo());
				}
			}
			if(requestIds.isEmpty()){
				Messagebox.show("No selected request!", "Reject Transaction Request", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			// *************Validation
			String remarks = ((Textbox)this.getFellow("appRemarks")).getValue();
			if (remarks == null || "".equals(remarks))
			{
				Messagebox.show("Please enter the remarks", "Approve Transaction Request", Messagebox.OK, Messagebox.EXCLAMATION);
				return;				
			}
			if(Messagebox.show("Confirm Reject?", "Reject Transaction Request", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK)
			{
				
				this.businessHelper.getTxnBusiness().updateTxns(requestIds, remarks, getUserLoginIdAndDomain(), NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED);
				Messagebox.show("Rejected", "Reject Transaction Request", Messagebox.OK, Messagebox.INFORMATION);
				this.init();
			}
		}
		catch (ConcurrencyFailureException e)
		{
			Messagebox.show("There exists concurrent user approves or rejects the same trip. Please check whether trip has been approved or rejected.", "Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch (Exception e)
		{
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	
	private void setRemarksInvisible()
	{
		((CapsTextbox)this.getFellow("appRemarks")).setVisible(false);
		((CapsTextbox)this.getFellow("appRemarks")).setValue(null);
		((Label)this.getFellow("appRemarksLabel")).setVisible(false);
		((Div)this.getFellow("appRemarksStar")).setVisible(false);
		((Button)this.getFellow("appBtn")).setDisabled(true);
		((Button)this.getFellow("rejBtn")).setDisabled(true);
	}
	
	private void setRemarksVisible()
	{
		((CapsTextbox)this.getFellow("appRemarks")).setVisible(true);
		((CapsTextbox)this.getFellow("appRemarks")).setValue(null);
		((Label)this.getFellow("appRemarksLabel")).setVisible(true);
		((Div)this.getFellow("appRemarksStar")).setVisible(true);
		((Button)this.getFellow("appBtn")).setDisabled(false);
		((Button)this.getFellow("rejBtn")).setDisabled(false);
	}

	public void checkAll() throws InterruptedException{
		logger.info("");
		
		try{
			Checkbox checkAll = (Checkbox)this.getFellow("checkAll");
			Listbox txnsReqs = (Listbox)this.getFellow("txnsReq");
			for(Object txnsReq : txnsReqs.getItems()){
				if(checkAll.isChecked())
				{
					Checkbox checkbox = (Checkbox)((Listitem)txnsReq).getLastChild().getFirstChild();
					if (checkbox != null)
					{
						if(!checkbox.isChecked())
						{
							checkbox.setChecked(true);
						}
					}
				}
				else
				{
					Checkbox checkbox = (Checkbox)((Listitem)txnsReq).getLastChild().getFirstChild();
					if (checkbox != null)
					{
						if(checkbox.isChecked())
						{
							checkbox.setChecked(false);
						}
					}
				}
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
}
