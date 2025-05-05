package com.cdgtaxi.ibs.prepaid.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.api.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbAdjustmentReq;
import com.cdgtaxi.ibs.common.model.PmtbExtBalExpDateReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonSearchByAccountWindow;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
import com.cdgtaxi.ibs.web.component.Datebox;

@SuppressWarnings("serial")
public class PrepaidRequestSearchWindow extends CommonSearchByAccountWindow implements AfterCompose {

	private Intbox reqNoField;
	private Listbox reqTypeField, reqStatusField;
	private Datebox requestDateFromField, requestDateToField;
	private Textbox requestorField;
	private Listbox resultList;

	public void afterCompose() {
		// wire variables
		Components.wireVariables(this, this);

		ComponentUtil.buildListbox(reqStatusField, NonConfigurableConstants.PREPAID_REQUEST_STATUS, true);
		ComponentUtil.buildListbox(reqTypeField, NonConfigurableConstants.PREPAID_REQUEST_TYPE, true);

	}

	public void searchRequest() throws InterruptedException {

		checkAccountNotNull();

		SearchPrepaidRequestForm form = new SearchPrepaidRequestForm();
		form.setReqNo(reqNoField.getValue());
		form.setStatus((String) reqStatusField.getSelectedItem().getValue());
		form.setAccountNo(getSelectedAccount().getAccountNo());
		form.setRequestDateFrom(requestDateFromField.getValue());
		form.setRequestDateTo(requestDateToField.getValue());
		form.setRequestor(requestorField.getValue());
		form.setRequestType((String) reqTypeField.getSelectedItem().getValue());

		this.displayProcessing();
		List<PmtbPrepaidReq> requestList = businessHelper.getPrepaidBusiness().searchPrepaidRequest(form);
		resultList.getItems().clear();
		
		if (requestList.size() > 0) {

			if (requestList.size() > ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for (PmtbPrepaidReq req : requestList) {
				Listitem item = new Listitem();
				item.setValue(req);
				
				AmtbAccount acct = req.getAmtbAccount();
				AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
				
				item.appendChild(newListcell(req.getReqNo(), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(topAcct.getCustNo()));
				item.appendChild(newListcell(topAcct.getAccountName()));
				item.appendChild(newListcell(NonConfigurableConstants.PREPAID_REQUEST_TYPE.get(req.getRequestType())));
				item.appendChild(newListcell(NonConfigurableConstants.PREPAID_REQUEST_STATUS.get(req.getStatus())));
				item.appendChild(newListcell(req.getRequestDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(req.getRequestBy().getName()));

				resultList.appendChild(item);
			}

			if (requestList.size() > ConfigurableConstants.getMaxQueryResult())
				resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

			if (resultList.getListfoot() != null)
				resultList.removeChild(resultList.getListfoot());
		} else {
			if (resultList.getListfoot() == null) {
				resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(7));
			}
		}

		resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultList.setPageSize(10);

	}

	public void newIssuanceRequest() throws InterruptedException {

		logger.info("New Issuance Request");
		this.forward(Uri.NEW_ISSUANCE_REQ, null);
	}

	public void newTopUpRequest() throws InterruptedException {

		logger.info("New Top Up Request");
		this.forward(Uri.NEW_TOP_UP_REQ, null);
	}

	public void newTransferRequest() throws InterruptedException {

		logger.info("New Transfer Request");
		this.forward(Uri.NEW_TRANSFER_REQ_SEARCH, null);
	}

	public void newExtendBalanceExpiryDateRequest() throws InterruptedException {

		logger.info("New Extend Balance Expiry Date Request");
		this.forward(Uri.NEW_EXTEND_BALANCE_EXPIRY_DATE_REQ_SEARCH, null);
	}

	public void newAdjustmentRequest() throws InterruptedException {

		logger.info("New Adjustment Request");
		this.forward(Uri.NEW_ADJUSTMENT_REQ_SEARCH, null);
	}

	public void test() {

		businessHelper.getPrepaidBusiness().getPrepaidIssuanceRequest(new BigDecimal(28));

	}

	public void selectRequest() throws InterruptedException {

		PmtbPrepaidReq request = ComponentUtil.getSelectedItem(resultList);
		String url = null;
		if (request instanceof PmtbIssuanceReq) {
			url = Uri.VIEW_ISSUANCE_REQ;
		} else if (request instanceof PmtbTopUpReq) {
			url = Uri.VIEW_TOP_UP_REQ;
		} else if (request instanceof PmtbTransferReq) {
			url = Uri.VIEW_TRANSFER_REQ;
		} else if (request instanceof PmtbExtBalExpDateReq) {
			url = Uri.VIEW_EXTEND_BALANCE_EXPIRY_DATE_REQ;
		} else if (request instanceof PmtbAdjustmentReq) {
			url = Uri.VIEW_ADJUSTMENT_REQ;
		}

		if (url == null) {
			throw new WrongValueException("Not supported request. Please contact system administrator!");
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("requestNo", request.getReqNo());
		this.forward(url, map);

	}

	public void reset() throws InterruptedException {

		ComponentUtil.reset(reqNoField, reqTypeField, reqStatusField, requestDateFromField, requestDateToField, requestorField, resultList);
		super.reset();
	}

	@Override
	public List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String name) {
		return this.businessHelper.getAccountBusiness().getTopLevelAccountsWithEntity(custNo, name);
	}

	
	@Override
	public void refresh() throws InterruptedException {
		super.refresh();
		resultList.clearSelection();
	}
}
