package com.cdgtaxi.ibs.inventory.business;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.ConcurrencyFailureException;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.exception.CDGEInventoryInterfaceException;
import com.cdgtaxi.ibs.common.exception.InsufficientInventoryStockException;
import com.cdgtaxi.ibs.common.exception.InventoryHasStockException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoExistsException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoReservedException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoUnavailableException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.ImtbIssue;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbIssueReqFlow;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.model.ImtbItemReqFlow;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.model.email.ItemRequestEmail;
import com.cdgtaxi.ibs.common.model.forms.SearchIssuanceRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.cdge.CdgeInterfaceClient;
import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.cdgtaxi.ibs.inventory.ItemTypeDto;
import com.cdgtaxi.ibs.inventory.ui.MassSubmissionWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class InventoryBusinessImpl extends GenericBusinessImpl implements InventoryBusiness {

	public List<ImtbItemType> getItemTypes() {
		return daoHelper.getInventoryDao().getItemTypes();
	}

	public List<ItemTypeDto> getItemTypeDtos() {
		return daoHelper.getInventoryDao().getItemTypeDtos();
	}

	public void deleteItemType(Integer itemTypeNo) throws InventoryHasStockException {
		boolean hasStock = daoHelper.getInventoryDao().itemTypeHasStock(itemTypeNo);
		if (hasStock) {
			throw new InventoryHasStockException();
		}
		ImtbItemType itemType = (ImtbItemType) daoHelper.getGenericDao().load(ImtbItemType.class,
				itemTypeNo);
		daoHelper.getGenericDao().delete(itemType);
	}

	public void createItemType(ImtbItemType itemType) {
		daoHelper.getGenericDao().save(itemType);
	}

	public ImtbItemType getItemType(Integer itemTypeNo) {
		return daoHelper.getInventoryDao().getItemType(itemTypeNo);
	}
	
	public List<ImtbStock> getStocksByItemType(Integer itemTypeNo){
		return daoHelper.getInventoryDao().getStocksByItemType(itemTypeNo);
	}


	public ItemTypeDto getItemTypeDto(Integer itemTypeNo) {
		return daoHelper.getInventoryDao().getItemTypeDto(itemTypeNo);
	}

	public void createStock(ImtbStock stock) throws CDGEInventoryInterfaceException,
			InventorySerialNoExistsException {
		// Check for overlapping serial no.
		boolean serialNoExists = daoHelper.getInventoryDao().serialNumberExists(
				stock.getImtbItemType().getItemTypeNo(), stock.getSerialNoStart(),
				stock.getSerialNoEnd());
		if (serialNoExists) {
			throw new InventorySerialNoExistsException();
		}

		stock.setTxnType(NonConfigurableConstants.STOCK_TXN_TYPE_IN);
		stock.setTxnDt(DateUtil.getCurrentTimestamp());
		daoHelper.getGenericDao().save(stock);

		// TODO: Interface new stock to CDGE
		if (false) {
			throw new CDGEInventoryInterfaceException("Interface to CDGE failed. Stock not added");
		}
	}

	public BigDecimal getNextStockSerialNo(Integer itemTypeNo) {
		return daoHelper.getInventoryDao().getNextStockSerialNo(itemTypeNo);
	}

	public void requestIssuance(ImtbIssueReq request) throws InsufficientInventoryStockException,
			InventorySerialNoReservedException, InventorySerialNoUnavailableException {
		// Check whether serial no issued and available in stock
		if (this.daoHelper.getInventoryDao().serialNumberAvailable(
				request.getImtbItemType().getItemTypeNo(), request.getSerialNoStart(),
				request.getSerialNoEnd()) == false)
			throw new InventorySerialNoUnavailableException();

		// //Check whether sufficient stock
		// Integer itemTypeNo = request.getImtbItemType().getItemTypeNo();
		// Integer quantity = request.getQuantity();
		// boolean hasSufficientStock = daoHelper.getInventoryDao().hasSufficientStock(
		// itemTypeNo, quantity);
		// if (!hasSufficientStock) {
		// throw new InsufficientInventoryStockException();
		// }

		// Check whether serial no reserved
		SearchIssuanceRequestForm form = new SearchIssuanceRequestForm();
		form.itemTypeNo = request.getImtbItemType().getItemTypeNo();
		form.serialNoStart = request.getSerialNoStart();
		form.serialNoEnd = request.getSerialNoEnd();
		form.maxResult = 1;
		form.requestStatus = NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING;
		if (this.searchIssuanceRequest(form).size() > 0)
			throw new InventorySerialNoReservedException();

		form.requestStatus = NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED;
		if (this.searchIssuanceRequest(form).size() > 0)
			throw new InventorySerialNoReservedException();

		request.setRequestDt(DateUtil.getCurrentTimestamp());
		daoHelper.getGenericDao().save(request);

		ImtbIssueReqFlow requestFlow = new ImtbIssueReqFlow();
		requestFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		requestFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_NEW);
		requestFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		requestFlow.setImtbIssueReq(request);
		String[] userIdAndDomain = request.getCreateBy().split("\\\\");
		String userId = userIdAndDomain[1];
		String domain = userIdAndDomain[0];
		requestFlow.setSatbUser(daoHelper.getUserDao().getUser(userId, domain));
		daoHelper.getGenericDao().save(requestFlow);

		request.setImtbIssueReqFlow(requestFlow);
		daoHelper.getGenericDao().update(request);

		// Email is sent to the approver upon submitting
		List<SatbUser> recipients = daoHelper.getUserDao().searchUser(
				Uri.APPROVE_INVENTORY_REQUEST_ISSUANCE);
		if (!recipients.isEmpty())
			sendEmailNotification(request,
					ConfigurableConstants.EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_SUBMIT,
					recipients.toArray(new SatbUser[] {}), new SatbUser[] {});
	}

	/**
	 * Send email notification
	 * 
	 * @param request
	 */
	private void sendEmailNotification(ImtbIssueReq request, String emailType, SatbUser[] toList,
			SatbUser[] ccList) {
		List<String> toEmails = new ArrayList<String>();
		StringBuffer recipientNames = new StringBuffer();
		for (SatbUser recipient : toList) {
			toEmails.add(recipient.getEmail());
			recipientNames.append(recipient.getName() + ",");
		}
		recipientNames.delete(recipientNames.length() - 1, recipientNames.length());

		List<String> ccEmails = new ArrayList<String>();
		for (SatbUser recipient : ccList) {
			ccEmails.add(recipient.getEmail());
		}

		String accountCategory = request.getAmtbAccount().getAccountCategory();
		String custNo = "";
		if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
				|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
			custNo = request.getAmtbAccount().getCustNo();
		} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
				|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
			custNo = request.getAmtbAccount().getAmtbAccount().getCustNo();
		} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
			custNo = request.getAmtbAccount().getAmtbAccount().getAmtbAccount().getCustNo();
		}
		// looks ridiculous, but simply replaces '$' with '\$'
		String escapedItemTypeName = request.getImtbItemType().getTypeName()
				.replaceAll("\\$", "\\\\\\$");
		EmailUtil.sendEmail(
				toEmails.toArray(new String[] {}),
				ccEmails.toArray(new String[] {}),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
						.replaceAll("#submitter#", request.getCreateBy())
						.replaceAll("#custNo#", custNo)
						.replaceAll("#acctName#", request.getAmtbAccount().getAccountName())
						.replaceAll("#userName#", recipientNames.toString())
						.replaceAll("#itemType#", escapedItemTypeName)
						.replaceAll("#quantity#", request.getQuantity().toString()));
	}

	public ImtbIssueReq getIssuanceRequest(Integer requestId) {
		return daoHelper.getInventoryDao().getIssuanceRequest(requestId);
	}

	public void approveRequest(ImtbIssueReqFlow requestFlow) throws ConcurrencyFailureException{
		requestFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		requestFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED);
		requestFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		daoHelper.getGenericDao().save(requestFlow);

		ImtbIssueReq request = requestFlow.getImtbIssueReq();
		SatbUser requestor = request.getImtbIssueReqFlow().getSatbUser();
		request.setImtbIssueReqFlow(requestFlow);
		daoHelper.getGenericDao().update(request);

		requestor = (SatbUser) daoHelper.getUserDao().get(SatbUser.class, requestor.getUserId());
		List<SatbUser> emailList = this.daoHelper.getUserDao().searchUser(
				Uri.ISSUE_INVENTORY_INVOICE);
		if (emailList.contains(requestor))
			emailList.remove(requestor);

		sendEmailNotification(request,
				ConfigurableConstants.EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_APPROVED,
				new SatbUser[] { requestor }, emailList.toArray(new SatbUser[] {}));
	}

	public void rejectRequest(ImtbIssueReqFlow requestFlow) throws ConcurrencyFailureException {
		requestFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		requestFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED);
		requestFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		daoHelper.getGenericDao().save(requestFlow);

		ImtbIssueReq request = requestFlow.getImtbIssueReq();
		SatbUser requestor = request.getImtbIssueReqFlow().getSatbUser();
		request.setImtbIssueReqFlow(requestFlow);
		daoHelper.getGenericDao().update(request);

		requestor = (SatbUser) daoHelper.getUserDao().get(SatbUser.class, requestor.getUserId());
		List<SatbUser> emailList = this.daoHelper.getUserDao().searchUser(
				Uri.ISSUE_INVENTORY_INVOICE);
		if (emailList.contains(requestor))
			emailList.remove(requestor);

		sendEmailNotification(request,
				ConfigurableConstants.EMAIL_TYPE_INVENTORY_ISSUE_REQUEST_REJECTED,
				new SatbUser[] { requestor }, emailList.toArray(new SatbUser[] {}));
	}

	public BigDecimal getNextAvailableStockSerialNo(Integer itemTypeNo) {
		return daoHelper.getInventoryDao().getNextAvailableStockSerialNo(itemTypeNo);
	}

	public void issueStock(BmtbInvoiceHeader invoiceHeader, ImtbIssue issue,
			BigDecimal serialNoStart, BigDecimal serialNoEnd, String userLoginId) throws Exception {
		ImtbIssueReq request = issue.getImtbIssueReq();
		ImtbItemType itemType = request.getImtbItemType();
		Integer itemTypeNo = itemType.getItemTypeNo();
		Timestamp now = DateUtil.getCurrentTimestamp();

		// Check for overlapping serial no.
		boolean serialNoAvailable = daoHelper.getInventoryDao().serialNumberAvailable(itemTypeNo,
				serialNoStart, serialNoEnd);
		if (!serialNoAvailable) {
			throw new InventorySerialNoUnavailableException();
		}

		// Request Flow
		ImtbIssueReqFlow requestFlow = new ImtbIssueReqFlow();
		requestFlow.setImtbIssueReq(request);
		requestFlow.setFromStatus(request.getImtbIssueReqFlow().getToStatus());
		requestFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_ISSUED);
		requestFlow.setFlowDt(now);
		requestFlow.setSatbUser(issue.getSatbUser());
		request.setImtbIssueReqFlow(requestFlow);

		// Stock
		ImtbStock stock = new ImtbStock();
		stock.setImtbItemType(itemType);
		stock.setTxnType(NonConfigurableConstants.STOCK_TXN_TYPE_ISSUED);
		stock.setSerialNoStart(serialNoStart);
		stock.setSerialNoEnd(serialNoEnd);
		stock.setTxnDt(now);

		// Issuance
		issue.setIssueNo(request.getIssueReqNo());
		issue.setImtbStock(stock);

		// Issued Items
		List<ImtbItem> items = new ArrayList<ImtbItem>();
		ImtbItem item;
		for (BigDecimal serialNo = serialNoStart; serialNo.compareTo(serialNoEnd) <= 0; serialNo = serialNo
				.add(BigDecimal.ONE)) {
			item = new ImtbItem();
			item.setImtbItemType(itemType);
			item.setSerialNo(serialNo);
			item.setImtbIssue(issue);
			item.setStatus(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED);
			item.setCreatedDt(now);
			item.setCreatedBy(userLoginId);
			item.setUpdateDt(now);
			item.setExpiryDate(issue.getExpiryDate());
			items.add(item);
		}
		
		//Buffer in the grace period for expiry date
		MstbMasterTable gracePeriodMaster = ConfigurableConstants.getMasterTable(
				ConfigurableConstants.INVENTORY_EXPIRY_GRACE_PERIOD, "GP");
		Integer gracePeriod = new Integer(gracePeriodMaster.getMasterValue());
		Calendar expiryCalendar = Calendar.getInstance();
		expiryCalendar.setTimeInMillis(issue.getExpiryDate().getTime());
		expiryCalendar.add(Calendar.MONTH, gracePeriod);
		
		// Webservice update to CDGE
		BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
				"IBS_CDGE_SQ1");
		CdgeInterfaceClient.syncVoucher(nextRunningNo.intValue(), serialNoStart.toString(),
				serialNoEnd.toString(), NonConfigurableConstants.STATUS_ACTIVE,
				StringUtil.bigDecimalToString(itemType.getPrice(), "0.00"),
				expiryCalendar.getTime());

		daoHelper.getGenericDao().save(requestFlow);
		daoHelper.getGenericDao().update(request);
		daoHelper.getGenericDao().save(stock);
		daoHelper.getGenericDao().save(issue);
		daoHelper.getGenericDao().saveOrUpdateAll(items);

		invoiceHeader.setImtbIssue(issue);
		invoiceHeader.setInvoiceType(NonConfigurableConstants.INVOICE_TYPE_TAXI_VOUCHER);
		
		this.generateMiscInvoice(invoiceHeader);
	}

	public ImtbIssue getIssuanceByStockNo(Integer stockNo) {
		return daoHelper.getInventoryDao().getIssuanceByStockNo(stockNo);
	}

	public AmtbAcctStatus getAccountLatestStatus(String accountNo) {
		return daoHelper.getAccountDao().getAccountLatestStatus(accountNo);
	}

	public List<Object[]> searchIssuanceRequest(SearchIssuanceRequestForm form) {
		return this.daoHelper.getInventoryDao().searchIssuanceRequest(form);
	}

	public List<ImtbItem> searchItem(SearchItemForm form) {
		return this.daoHelper.getInventoryDao().searchItem(form);
	}

	public ImtbItem getItemByItemNo(Long itemNo) {
		return this.daoHelper.getInventoryDao().getItemByItemNo(itemNo);
	}

	public ImtbItem getItemBySerialNo(BigDecimal serialNo) {
		return this.daoHelper.getInventoryDao().getItemBySerialNo(serialNo);
	}

	public List<ImtbItem> getItemByItemNos(List<Long> itemNos) {
		return this.daoHelper.getInventoryDao().getItemByItemNos(itemNos);
	}
	/* removed from Yvonne Yap
	 * due to consolidation of email for mass submission
	 * previously was one item request one email
	public void createApprovalRequest(ImtbItem item, MstbMasterTable reason, String remarks,
			String requestor, String mode, String redeemPoint, String batchNo, java.sql.Date batchDate, Timestamp redemptionDate,
			String cashierId, BigDecimal serialNo, Date newExpiryDate) {

		String newStatus = null;
		String emailType = null;
		if (mode.equals(MassSubmissionWindow.MODE_SUSPENSION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_SUSPENSION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_CHANGE_OF_REDEMPTION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_REACTIVATION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REACTIVATION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_VOID)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_VOID_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_REMOVE_REDEMPTION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_EDIT_EXPIRY_DATE)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_SUBMIT;
		}
		ImtbItemReq newRequest = new ImtbItemReq();
		newRequest.setImtbItem(item);
		newRequest.setMstbMasterTableByReason(reason);
		newRequest.setRemarks(remarks);
		newRequest.setRequestor(requestor);
		newRequest.setRequestDt(DateUtil.getCurrentTimestamp());
		newRequest.setRedeemPoint(redeemPoint);
		newRequest.setCashierId(cashierId);
		newRequest.setRedeemTime(redemptionDate);
		newRequest.setBatchNo(batchNo);
		newRequest.setBatchDate(batchDate);
		newRequest.setSerialNo(serialNo);
		newRequest.setCurrentStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		newRequest.setAction(newStatus);
		newRequest.setNewExpiryDate(newExpiryDate);
		this.save(newRequest);

		ImtbItemReqFlow newFlow = new ImtbItemReqFlow();
		newFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_NEW);
		newFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		newFlow.setRemarks(newRequest.getRemarks());
		newFlow.setFlowDt(newRequest.getRequestDt());
		newFlow.setImtbItemReq(newRequest);
		newFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		this.save(newFlow);

		item.setStatus(newStatus);
		this.update(item, requestor);
		
	}
	*/
	public void createApprovalRequest(List<ImtbItem> items,
			MstbMasterTable reason, String remarks, String requestor,
			String mode,  BigDecimal serialNo,
			Date newExpiryDate) {
		
		String newStatus = null;
		String emailType = null;
		
		if (mode.equals(MassSubmissionWindow.MODE_SUSPENSION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_SUSPENSION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_CHANGE_OF_REDEMPTION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_REACTIVATION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REACTIVATION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_VOID)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_VOID_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_REMOVE_REDEMPTION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_SUBMIT;
		}else if (mode.equals(MassSubmissionWindow.MODE_EDIT_EXPIRY_DATE)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_SUBMIT;
		}
		
		Map<ItemRequestEmail,ImtbItemReq> emailGrouping =  new HashMap<ItemRequestEmail, ImtbItemReq>();
		String cashierId = null; // if mode != change of redemption, set cashier Id to null
		for(ImtbItem item:items){
			if (!mode.equals(MassSubmissionWindow.MODE_CHANGE_OF_REDEMPTION)){
				cashierId = null;
			}else{
				cashierId = item.getCashierId();
			}
			ImtbItemReq newRequest = new ImtbItemReq();
			newRequest.setImtbItem(item);
			newRequest.setMstbMasterTableByReason(reason);
			newRequest.setRemarks(remarks);
			newRequest.setRequestor(requestor);
			newRequest.setRequestDt(DateUtil.getCurrentTimestamp());
			newRequest.setRedeemPoint(item.getRedeemPoint());
			newRequest.setCashierId(item.getCashierId());
			newRequest.setRedeemTime(item.getRedeemTime());
			newRequest.setBatchNo(item.getBatchNo());
			newRequest.setBatchDate(item.getBatchDate());
			newRequest.setSerialNo(serialNo);
			newRequest.setCurrentStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
			newRequest.setAction(newStatus);
			newRequest.setNewExpiryDate(newExpiryDate);
			this.save(newRequest);

			ImtbItemReqFlow newFlow = new ImtbItemReqFlow();
			newFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_NEW);
			newFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
			newFlow.setRemarks(newRequest.getRemarks());
			newFlow.setFlowDt(newRequest.getRequestDt());
			newFlow.setImtbItemReq(newRequest);
			newFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
			this.save(newFlow);

			item.setStatus(newStatus);
			this.update(item, requestor);
			
			// create new itemRequestEmail
			ItemRequestEmail newEmailGroupKey = new ItemRequestEmail();
			newEmailGroupKey.setEmailType(emailType);
			newEmailGroupKey.setItemType(item.getImtbItemType());
			
			// get requestor
			newEmailGroupKey.setRequestor(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
			newEmailGroupKey.setSubmitter(newRequest.getRequestor());
			
			// get account
			AmtbAccount account = item.getImtbIssue().getImtbIssueReq().getAmtbAccount();
			String accountCategory = account.getAccountCategory();
			String custNo = "";
			if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
					|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
				custNo = account.getCustNo();
			} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
					|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				custNo = account.getAmtbAccount().getCustNo();
			} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				custNo = account.getAmtbAccount().getAmtbAccount().getCustNo();
			}
			newEmailGroupKey.setCustNo(custNo);
			newEmailGroupKey.setAccount(account);
			
			// if the key already in the email group, skip
			boolean newEmailRequired = true;
			for(ItemRequestEmail emailKey :emailGrouping.keySet()){
				if(emailKey.equals(newEmailGroupKey)){
					newEmailRequired = false;
					break;
				}
			}
			if (newEmailRequired){
				emailGrouping.put(newEmailGroupKey, newRequest);
			}
		}
		
		// Email is sent to the approver upon submitting, by grouping
		for(Entry<ItemRequestEmail, ImtbItemReq> email :emailGrouping.entrySet()){
			List<SatbUser> recipients = daoHelper.getUserDao().searchUser(
					Uri.APPROVE_INVENTORY_ITEM_REQUEST);
			if (!recipients.isEmpty())
				sendEmailNotification(email.getKey() ,
						emailType,recipients.toArray(new SatbUser[] {}), new SatbUser[] {});
		}
	}
	public List<ImtbItemReq> searchItemRequest(SearchItemRequestForm form) {
		return this.daoHelper.getInventoryDao().searchItemRequest(form);
	}

	public ImtbItemReq getItemRequestByReqNo(Integer reqNo) {
		return this.daoHelper.getInventoryDao().getItemRequestByReqNo(reqNo);
	}

	public List<ImtbItemReq> getItemRequestByReqNos(List<Integer> reqNos) {
		return this.daoHelper.getInventoryDao().getItemRequestByReqNos(reqNos);
	}
	
	/* removed from Yvonne Yap
	 * due to consolidation of email for mass approval
	 * previously was one item request one email
	public void reject(ImtbItemReq itemReq, String remarks, String userLoginId) {
		
		// get requestor
		SatbUser requestor = null;
		Iterator<ImtbItemReqFlow> iter = itemReq.getImtbItemReqFlows().iterator();
		if (iter != null) {
			ImtbItemReqFlow imtbItemReqFlow = iter.next();
			requestor = imtbItemReqFlow.getSatbUser();
		}
				
		// Update Current status of request
		itemReq.setCurrentStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED);
		super.update(itemReq);

		// Revert status of item voucher
		ImtbItem itemVoucher = itemReq.getImtbItem();

		String previousStatus = itemVoucher.getStatus();
		String newStatus = null;
		String emailType = null;
		if (previousStatus
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_REJECTED;
		}else if (previousStatus
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REACTIVATION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_REJECTED;
		}else if (previousStatus
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_SUSPENSION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_REJECTED;
		}else if (previousStatus.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_VOID_REQUEST_REJECTED;
		}else if (previousStatus.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_REJECTED;
		}else if (previousStatus.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE)){
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_REJECTED;
		}
		itemVoucher.setStatus(newStatus);
		super.update(itemVoucher, userLoginId);

		// Save the rejection flow information
		ImtbItemReqFlow newFlow = new ImtbItemReqFlow();
		newFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		newFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED);
		newFlow.setRemarks(remarks);
		newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		newFlow.setImtbItemReq(itemReq);
		newFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		this.save(newFlow);
	}
	*/
	public void reject(List<ImtbItemReq> itemReqs, String remarks, String userLoginId) {
		
		Map<ItemRequestEmail, ImtbItemReq> emailGrouping = new HashMap<ItemRequestEmail, ImtbItemReq>();
		for(ImtbItemReq itemReq:itemReqs){
			// Update Current status of request
			itemReq.setCurrentStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED);
			super.update(itemReq);

			// Revert status of item voucher
			ImtbItem itemVoucher = itemReq.getImtbItem();

			String previousStatus = itemVoucher.getStatus();
			String newStatus = null;
			String emailType = null;
			if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION)){
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_REJECTED;
			}else if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REACTIVATION)){
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_REJECTED;
			}else if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_SUSPENSION)){
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_REJECTED;
			}else if (previousStatus.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID)){
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_VOID_REQUEST_REJECTED;
			}else if (previousStatus.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION)){
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_REJECTED;
			}else if (previousStatus.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE)){
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_REJECTED;
			}
			itemVoucher.setStatus(newStatus);
			super.update(itemVoucher, userLoginId);

			// Save the rejection flow information
			ImtbItemReqFlow newFlow = new ImtbItemReqFlow();
			newFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
			newFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_REJECTED);
			newFlow.setRemarks(remarks);
			newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
			newFlow.setImtbItemReq(itemReq);
			newFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
			this.save(newFlow);

			// create new email group key
			ItemRequestEmail newEmailGroupKey = new ItemRequestEmail();
			newEmailGroupKey.setEmailType(emailType);
			newEmailGroupKey.setItemType(itemReq.getImtbItem().getImtbItemType());
			
			// get requestor
			SatbUser requestor = null;
			Iterator<ImtbItemReqFlow> iter = itemReq.getImtbItemReqFlows().iterator();
			if (iter != null) {
				ImtbItemReqFlow imtbItemReqFlow = iter.next();
				newEmailGroupKey.setRequestor(imtbItemReqFlow.getSatbUser());
				newEmailGroupKey.setSubmitter(itemReq.getRequestor());
			}
			
			// get account
			AmtbAccount account = itemReq.getImtbItem().getImtbIssue().getImtbIssueReq().getAmtbAccount();
			String accountCategory = account.getAccountCategory();
			String custNo = "";
			if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
					|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
				custNo = account.getCustNo();
			} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
					|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				custNo = account.getAmtbAccount().getCustNo();
			} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				custNo = account.getAmtbAccount().getAmtbAccount().getCustNo();
			}
			newEmailGroupKey.setCustNo(custNo);
			newEmailGroupKey.setAccount(account);
			
			// if the key already in the email group, skip
			boolean newEmailRequired = true;
			for(ItemRequestEmail emailKey :emailGrouping.keySet()){
				if(emailKey.equals(newEmailGroupKey)){
					newEmailRequired = false;
					break;
				}
			}
			if (newEmailRequired){
				emailGrouping.put(newEmailGroupKey, itemReq);
			}
		}
		
		// Email is sent to the requestor upon rejection
		// looping the email group list
		for(Entry<ItemRequestEmail, ImtbItemReq> email :emailGrouping.entrySet()){
			sendEmailNotification(email.getKey(), email.getKey().emailType, new SatbUser[] { email.getKey().getRequestor() }, new SatbUser[] {});
		}
	}
		
	/* removed from Yvonne Yap
	 * due to consolidation of email for mass approval
	 * previously was one item request one email
	public String approve(ImtbItemReq itemReq, String remarks, String userLoginId) throws Exception {
		String reason = null;
		
		// get requestor
		SatbUser requestor = null;
		Iterator<ImtbItemReqFlow> iter = itemReq.getImtbItemReqFlows().iterator();
		if (iter != null) {
			ImtbItemReqFlow imtbItemReqFlow = iter.next();
			requestor = imtbItemReqFlow.getSatbUser();
		}
		
		// Update Current status of request
		itemReq.setCurrentStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED);
		super.update(itemReq);

		// Save the approve flow information
		ImtbItemReqFlow newFlow = new ImtbItemReqFlow();
		newFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		newFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED);
		newFlow.setRemarks(remarks);
		newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		newFlow.setImtbItemReq(itemReq);
		newFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		this.save(newFlow);

		// update new status of item voucher
		ImtbItem itemVoucher = itemReq.getImtbItem();

		String previousStatus = itemVoucher.getStatus();
		String newStatus = null;
		String emailType = null;
		if (previousStatus
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION)) {
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_APPROVED;
			
			//Change of Redemption
			ImtbItem itemVoucherChangeOfRedemption = 
				this.daoHelper.getInventoryDao().getItemBySerialNo(itemReq.getSerialNo());
			if(!itemVoucherChangeOfRedemption.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED))
				throw new ExpectedException("Item (Serial No. "+itemReq.getSerialNo()
						+") is no longer in ISSUED status therefore not applicable to change of redemption.");
			
			// Webservice update to CDGE
			BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
					"IBS_CDGE_SQ1");
			reason = CdgeInterfaceClient.amendVoucher(nextRunningNo.intValue(), itemVoucher
					.getSerialNo().toString(), itemReq.getSerialNo().toString());
			
			if(reason!=null && reason.length()>0)
				throw new ExpectedException(reason);
			
			itemVoucherChangeOfRedemption.setStatus(NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED);
			itemVoucherChangeOfRedemption.setBatchNo(itemVoucher.getBatchNo());
			itemVoucherChangeOfRedemption.setCashierId(itemVoucher.getCashierId());
			itemVoucherChangeOfRedemption.setRedeemPoint(itemVoucher.getRedeemPoint());
			itemVoucherChangeOfRedemption.setRedeemTime(itemVoucher.getRedeemTime());
			itemVoucherChangeOfRedemption.setBatchDate(itemVoucher.getBatchDate());
			super.update(itemVoucherChangeOfRedemption, userLoginId);
			
			//Clear redemption information
			itemVoucher.setBatchNo(null);
			itemVoucher.setCashierId(null);
			itemVoucher.setRedeemPoint(null);
			itemVoucher.setRedeemTime(null);
			itemVoucher.setBatchDate(null);
		} else if (previousStatus
				.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION)) {
			newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
			emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_APPROVED;
			
			// Webservice update to CDGE
			BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
					"IBS_CDGE_SQ1");
			reason = CdgeInterfaceClient.amendVoucher(nextRunningNo.intValue(), itemVoucher
					.getSerialNo().toString(), "");
			
			if(reason!=null && reason.length()>0)
				throw new ExpectedException(reason);
			
			// Clear redemption information
			itemVoucher.setBatchNo(null);
			itemVoucher.setCashierId(null);
			itemVoucher.setRedeemPoint(null);
			itemVoucher.setRedeemTime(null);
			itemVoucher.setBatchDate(null);
		}else {
			String cdgeStatus = null;
			if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REACTIVATION)) {
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
				cdgeStatus = NonConfigurableConstants.STATUS_ACTIVE;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_APPROVED;
			} else if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_SUSPENSION)) {
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED;
				cdgeStatus = NonConfigurableConstants.STATUS_INACTIVE;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_APPROVED;
			} else if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID)) {
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_VOID;
				cdgeStatus = NonConfigurableConstants.STATUS_INACTIVE;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_VOID_REQUEST_APPROVED;
			} else if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE)) {
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
				cdgeStatus = NonConfigurableConstants.STATUS_ACTIVE;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_APPROVED;
				// update taxi voucher to new expiry date
				itemVoucher.setExpiryDate(itemReq.getNewExpiryDate());
			}

			//Buffer in the grace period for expiry date
			MstbMasterTable gracePeriodMaster = ConfigurableConstants.getMasterTable(
					ConfigurableConstants.INVENTORY_EXPIRY_GRACE_PERIOD, "GP");
			Integer gracePeriod = new Integer(gracePeriodMaster.getMasterValue());
			Calendar expiryCalendar = Calendar.getInstance();
			expiryCalendar.setTimeInMillis(itemVoucher.getExpiryDate().getTime());
			expiryCalendar.add(Calendar.MONTH, gracePeriod);
			
			// Webservice update to CDGE
			BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
					"IBS_CDGE_SQ1");
			reason = CdgeInterfaceClient
					.syncVoucher(nextRunningNo.intValue(), itemVoucher.getSerialNo().toString(),
							itemVoucher.getSerialNo().toString(), cdgeStatus, StringUtil
									.bigDecimalToString(itemVoucher.getImtbItemType().getPrice(),
											"0.00"), expiryCalendar.getTime());
			
			if(reason!=null && reason.length()>0)
				throw new ExpectedException(reason);
		}
		itemVoucher.setStatus(newStatus);
		super.update(itemVoucher, userLoginId);
		
		return reason;
	}
	 */
	public String approve(List<ImtbItemReq> itemReqs, String remarks, String userLoginId) throws Exception {
		String reason = null;
		Map<ItemRequestEmail, ImtbItemReq> emailGrouping = new HashMap<ItemRequestEmail, ImtbItemReq>();
		
		for(ImtbItemReq itemReq:itemReqs){
			
			// Update Current status of request
			itemReq.setCurrentStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED);
			super.update(itemReq);

			// Save the approve flow information
			ImtbItemReqFlow newFlow = new ImtbItemReqFlow();
			newFlow.setFromStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
			newFlow.setToStatus(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED);
			newFlow.setRemarks(remarks);
			newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
			newFlow.setImtbItemReq(itemReq);
			newFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
			this.save(newFlow);

			// update new status of item voucher
			ImtbItem itemVoucher = itemReq.getImtbItem();

			String previousStatus = itemVoucher.getStatus();
			String newStatus = null;
			String emailType = null;
			if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION)) {
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_CHANGE_OF_REDEMPTION_REQUEST_APPROVED;
				
				//Change of Redemption
				ImtbItem itemVoucherChangeOfRedemption = 
					this.daoHelper.getInventoryDao().getItemBySerialNo(itemReq.getSerialNo());
				if(!itemVoucherChangeOfRedemption.getStatus().equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED))
					throw new ExpectedException("Item (Serial No. "+itemReq.getSerialNo()
							+") is no longer in ISSUED status therefore not applicable to change of redemption.");
				
				// Webservice update to CDGE
				BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
						"IBS_CDGE_SQ1");
				reason = CdgeInterfaceClient.amendVoucher(nextRunningNo.intValue(), itemVoucher
						.getSerialNo().toString(), itemReq.getSerialNo().toString());
				
				if(reason!=null && reason.length()>0)
					throw new ExpectedException(reason);
				
				itemVoucherChangeOfRedemption.setStatus(NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED);
				itemVoucherChangeOfRedemption.setBatchNo(itemVoucher.getBatchNo());
				itemVoucherChangeOfRedemption.setCashierId(itemVoucher.getCashierId());
				itemVoucherChangeOfRedemption.setRedeemPoint(itemVoucher.getRedeemPoint());
				itemVoucherChangeOfRedemption.setRedeemTime(itemVoucher.getRedeemTime());
				itemVoucherChangeOfRedemption.setBatchDate(itemVoucher.getBatchDate());
				super.update(itemVoucherChangeOfRedemption, userLoginId);
				
				//Clear redemption information
				itemVoucher.setBatchNo(null);
				itemVoucher.setCashierId(null);
				itemVoucher.setRedeemPoint(null);
				itemVoucher.setRedeemTime(null);
				itemVoucher.setBatchDate(null);
			} else if (previousStatus
					.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION)) {
				newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
				emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REMOVE_REDEMPTION_REQUEST_APPROVED;
				
				// Webservice update to CDGE
				BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
						"IBS_CDGE_SQ1");
				reason = CdgeInterfaceClient.amendVoucher(nextRunningNo.intValue(), itemVoucher
						.getSerialNo().toString(), "");
				
				if(reason!=null && reason.length()>0)
					throw new ExpectedException(reason);
				
				// Clear redemption information
				itemVoucher.setBatchNo(null);
				itemVoucher.setCashierId(null);
				itemVoucher.setRedeemPoint(null);
				itemVoucher.setRedeemTime(null);
				itemVoucher.setBatchDate(null);
			}else {
				String cdgeStatus = null;
				if (previousStatus
						.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REACTIVATION)) {
					newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
					cdgeStatus = NonConfigurableConstants.STATUS_ACTIVE;
					emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_REACTIVATE_REQUEST_APPROVED;
				} else if (previousStatus
						.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_SUSPENSION)) {
					newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_SUSPENDED;
					cdgeStatus = NonConfigurableConstants.STATUS_INACTIVE;
					emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_SUSPEND_REQUEST_APPROVED;
				} else if (previousStatus
						.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID)) {
					newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_VOID;
					cdgeStatus = NonConfigurableConstants.STATUS_INACTIVE;
					emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_VOID_REQUEST_APPROVED;
				} else if (previousStatus
						.equals(NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_EXPIRY_DATE)) {
					newStatus = NonConfigurableConstants.INVENTORY_ITEM_STATUS_ISSUED;
					cdgeStatus = NonConfigurableConstants.STATUS_ACTIVE;
					emailType = ConfigurableConstants.EMAIL_TYPE_INVENTORY_EDIT_EXPIRY_REQUEST_APPROVED;
					// update taxi voucher to new expiry date
					itemVoucher.setExpiryDate(itemReq.getNewExpiryDate());
				}

				//Buffer in the grace period for expiry date
				MstbMasterTable gracePeriodMaster = ConfigurableConstants.getMasterTable(
						ConfigurableConstants.INVENTORY_EXPIRY_GRACE_PERIOD, "GP");
				Integer gracePeriod = new Integer(gracePeriodMaster.getMasterValue());
				Calendar expiryCalendar = Calendar.getInstance();
				expiryCalendar.setTimeInMillis(itemVoucher.getExpiryDate().getTime());
				expiryCalendar.add(Calendar.MONTH, gracePeriod);
				
				// Webservice update to CDGE
				BigDecimal nextRunningNo = this.daoHelper.getGenericDao().getNextSequenceNo(
						"IBS_CDGE_SQ1");
				reason = CdgeInterfaceClient
						.syncVoucher(nextRunningNo.intValue(), itemVoucher.getSerialNo().toString(),
								itemVoucher.getSerialNo().toString(), cdgeStatus, StringUtil
										.bigDecimalToString(itemVoucher.getImtbItemType().getPrice(),
												"0.00"), expiryCalendar.getTime());
				
				if(reason!=null && reason.length()>0)
					throw new ExpectedException(reason);
			}
			itemVoucher.setStatus(newStatus);
			super.update(itemVoucher, userLoginId);
			
			// create email grouping key
			ItemRequestEmail newEmailGroupKey = new ItemRequestEmail();
			newEmailGroupKey.setEmailType(emailType);
			newEmailGroupKey.setItemType(itemReq.getImtbItem().getImtbItemType());
			
			// get requestor
			SatbUser requestor = null;
			Iterator<ImtbItemReqFlow> iter = itemReq.getImtbItemReqFlows().iterator();
			if (iter != null) {
				ImtbItemReqFlow imtbItemReqFlow = iter.next();
				newEmailGroupKey.setRequestor(imtbItemReqFlow.getSatbUser());
				newEmailGroupKey.setSubmitter(itemReq.getRequestor());
			}
			
			// get account
			AmtbAccount account = itemReq.getImtbItem().getImtbIssue().getImtbIssueReq().getAmtbAccount();
			String accountCategory = account.getAccountCategory();
			String custNo = "";
			if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
					|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
				custNo = account.getCustNo();
			} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)
					|| accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)) {
				custNo = account.getAmtbAccount().getCustNo();
			} else if (accountCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				custNo = account.getAmtbAccount().getAmtbAccount().getCustNo();
			}
			newEmailGroupKey.setCustNo(custNo);
			newEmailGroupKey.setAccount(account);
			
			// if the key already in the email group, skip
			boolean newEmailRequired = true;
			for(ItemRequestEmail emailKey :emailGrouping.keySet()){
				if(emailKey.equals(newEmailGroupKey)){
					newEmailRequired = false;
					break;
				}
			}
			if (newEmailRequired){
				emailGrouping.put(newEmailGroupKey, itemReq);
			}
		}

		// Email is sent to the requestor upon approval
		// looping the email grouping list
		for(Entry<ItemRequestEmail, ImtbItemReq> email :emailGrouping.entrySet()){
			sendEmailNotification(email.getKey(), email.getKey().emailType, new SatbUser[] { email.getKey().getRequestor() }, new SatbUser[] {});
		}
		
		return reason;
	}
	private void sendEmailNotification( ItemRequestEmail request, String emailType, SatbUser[] toList,
			SatbUser[] ccList) {
		List<String> toEmails = new ArrayList<String>();
		StringBuffer recipientNames = new StringBuffer();
		for (SatbUser recipient : toList) {
			toEmails.add(recipient.getEmail());
			recipientNames.append(recipient.getName() + ",");
		}
		recipientNames.delete(recipientNames.length() - 1, recipientNames.length());

		List<String> ccEmails = new ArrayList<String>();
		for (SatbUser recipient : ccList) {
			ccEmails.add(recipient.getEmail());
		}
		
		// looks ridiculous, but simply replaces '$' with '\$'
		String escapedItemTypeName = request.getItemType().getTypeName()
				.replaceAll("\\$", "\\\\\\$");
		EmailUtil.sendEmail(
				toEmails.toArray(new String[] {}),
				ccEmails.toArray(new String[] {}),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
						.replaceAll("#submitter#", request.getSubmitter())
						.replaceAll("#custNo#", request.getCustNo())
						.replaceAll("#acctName#", request.getAccount().getAccountName())
						.replaceAll("#userName#", recipientNames.toString())
						.replaceAll("#itemType#", escapedItemTypeName));
	}
}
