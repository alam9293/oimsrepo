package com.cdgtaxi.ibs.inventory.business;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.exception.CDGEInventoryInterfaceException;
import com.cdgtaxi.ibs.common.exception.InsufficientInventoryStockException;
import com.cdgtaxi.ibs.common.exception.InventoryHasStockException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoExistsException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoReservedException;
import com.cdgtaxi.ibs.common.exception.InventorySerialNoUnavailableException;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.ImtbIssue;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbIssueReqFlow;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.model.forms.SearchIssuanceRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemRequestForm;
import com.cdgtaxi.ibs.inventory.ItemTypeDto;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public interface InventoryBusiness extends GenericBusiness {

	List<ImtbItemType> getItemTypes();

	void deleteItemType(Integer itemTypeNo) throws InventoryHasStockException;

	void createItemType(ImtbItemType itemType);

	List<ItemTypeDto> getItemTypeDtos();

	ItemTypeDto getItemTypeDto(Integer itemTypeNo);

	ImtbItemType getItemType(Integer itemTypeNo);
	
	public List<ImtbStock> getStocksByItemType(Integer itemTypeNo);

	void createStock(ImtbStock stock) throws CDGEInventoryInterfaceException, InventorySerialNoExistsException;

	BigDecimal getNextStockSerialNo(Integer itemTypeNo);

	void requestIssuance(ImtbIssueReq request) throws InsufficientInventoryStockException, InventorySerialNoReservedException, InventorySerialNoUnavailableException;

	ImtbIssueReq getIssuanceRequest(Integer requestId);

	void approveRequest(ImtbIssueReqFlow requestFlow);

	void rejectRequest(ImtbIssueReqFlow requestFlow);

	BigDecimal getNextAvailableStockSerialNo(Integer itemTypeNo);

	void issueStock(BmtbInvoiceHeader invoiceHeader, ImtbIssue issue, BigDecimal serialNoStart, BigDecimal serialNoEnd, String userLoginId) throws Exception;

	ImtbIssue getIssuanceByStockNo(Integer stockNo);
	
	public AmtbAcctStatus getAccountLatestStatus(String accountNo);
	
	public List<Object[]> searchIssuanceRequest(SearchIssuanceRequestForm form);
	
	public List<ImtbItem> searchItem(SearchItemForm form);
	
	public ImtbItem getItemByItemNo(Long itemNo);
	
	public ImtbItem getItemBySerialNo(BigDecimal serialNo);
	
	public List<ImtbItem> getItemByItemNos(List<Long> itemNos);
	/*
	public void createApprovalRequest(ImtbItem item, MstbMasterTable reason, String remarks,
			String requestor, String mode, String redeemPoint, String batchNo, java.sql.Date batchDate, Timestamp redemptionDate,
			String cashierId, BigDecimal serialNo, Date newExpiryDate) ;
	*/
	public void createApprovalRequest(List<ImtbItem> item, MstbMasterTable reason, String remarks,
			String requestor, String mode, 
			BigDecimal serialNo, Date newExpiryDate) ;
	
	public List<ImtbItemReq> searchItemRequest(SearchItemRequestForm form);
	
	public ImtbItemReq getItemRequestByReqNo(Integer reqNo);
	
	public List<ImtbItemReq> getItemRequestByReqNos(List<Integer> reqNos);
	/*
	public String approve(ImtbItemReq itemReq, String remarks, String userLoginId) throws Exception ;
	
	public void reject(ImtbItemReq itemReq, String remarks, String userLoginId) ;
	*/
	public String approve(List<ImtbItemReq> itemReqs, String remarks, String userLoginId) throws Exception ;
	
	public void reject(List<ImtbItemReq> itemReqs, String remarks, String userLoginId) ;
}
