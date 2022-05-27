package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.ImtbIssue;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.model.forms.SearchIssuanceRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemRequestForm;
import com.cdgtaxi.ibs.inventory.ItemTypeDto;


public interface InventoryDao {

	public List<ImtbItemType> getItemTypes();
	
	boolean itemTypeHasStock(Integer integer);

	List<ItemTypeDto> getItemTypeDtos();

	ImtbItemType getItemType(Integer itemTypeNo);
	
	public List<ImtbStock> getStocksByItemType(Integer itemTypeNo);

	ItemTypeDto getItemTypeDto(Integer itemTypeNo);

	BigDecimal getNextStockSerialNo(Integer itemTypeNo);

	/**
	 * Checks whether the specified range of serial number already exists / overlaps
	 * in the inventory.
	 * 
	 * @param itemTypeNo
	 * @param serialNoStart - the starting serial number of the range to check
	 * @param serialNoEnd - the ending serial number of the range to check
	 * @return exists / overlaps
	 */
	boolean serialNumberExists(Integer itemTypeNo, BigDecimal serialNoStart, BigDecimal serialNoEnd);

	/**
	 * Checks whether there is sufficient stock in the inventory for issuing. Stock
	 * that has been issued or reserved will be excluded.
	 * 
	 * @param itemTypeNo
	 * @param requestedQty - quantity of stock to be issued
	 * @return sufficient
	 */
	boolean hasSufficientStock(Integer itemTypeNo, Integer requestedQty);

	ImtbIssueReq getIssuanceRequest(Integer requestId);

	/**
	 * Gets the next available serial number. By availability, it means that
	 * the serial number must have been "stock-in" before and must not have been
	 * issued already.
	 * 
	 * @param itemTypeNo
	 * @return next available serial number
	 */
	BigDecimal getNextAvailableStockSerialNo(Integer itemTypeNo);

	/**
	 * Checks for availability of serial number. By availability, it means that
	 * the serial number must have been "stock-in" before and must not have been
	 * issued already.
	 * 
	 * @param itemTypeNo
	 * @param serialNoStart - the starting serial number of the range to check
	 * @param serialNoEnd - the ending serial number of the range to check
	 * @return availability
	 */
	boolean serialNumberAvailable(Integer itemTypeNo,
			BigDecimal serialNoStart, BigDecimal serialNoEnd);

	ImtbIssue getIssuanceByStockNo(Integer stockNo);
	public List<ImtbItemType> getAllItemTypes();
	public Set<String> getAllRedeemPoint();
	
	public List<Object[]> searchIssuanceRequest(SearchIssuanceRequestForm form);

	public List<ImtbItem> searchItem(SearchItemForm form);
	
	public ImtbItem getItemByItemNo(Long itemNo);
	
	public ImtbItem getItemBySerialNo(BigDecimal serialNo);
	
	public List<ImtbItem> getItemByItemNos(List<Long> itemNos);
	
	public List<ImtbItemReq> searchItemRequest(SearchItemRequestForm form);
	
	public ImtbItemReq getItemRequestByReqNo(Integer reqNo);
	
	public List<ImtbItemReq> getItemRequestByReqNos(List<Integer> reqNos);
}
