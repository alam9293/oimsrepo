package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;

public class BankPaymentInfo {
	public String txnRefNo;
	public Date creditDate;
	public BigDecimal collectionAmount = BigDecimal.ZERO;
	public BigDecimal totalTripsAmount = BigDecimal.ZERO;
	public BigDecimal rejectedAmount = BigDecimal.ZERO;
	public BigDecimal mdrPercentage = BigDecimal.ZERO;
	public BigDecimal mdrAmount = BigDecimal.ZERO;
	public BigDecimal mdrAdjustment;
	public BigDecimal chargebackAmount = BigDecimal.ZERO;
	public BigDecimal receiptAmount = BigDecimal.ZERO;
	public BigDecimal markup = BigDecimal.ZERO;
	public BigDecimal commission = BigDecimal.ZERO;
	public BigDecimal schemeFee = BigDecimal.ZERO;
	public BigDecimal interchange = BigDecimal.ZERO;
	public BigDecimal chargebackReverse = BigDecimal.ZERO;
	public BigDecimal refund =  BigDecimal.ZERO;
	public BigDecimal refundReverse = BigDecimal.ZERO;
	public BigDecimal otherCredit = BigDecimal.ZERO;
	public BigDecimal otherDebit = BigDecimal.ZERO;
	public String remarks;
	public Integer bankInCode; 
	
	public BigDecimal premiumAmount = BigDecimal.ZERO;
	public BigDecimal premiumGst = BigDecimal.ZERO;
	
	public List<BatchInfo> batchList = new ArrayList<BatchInfo>();
	
	public class BatchInfo{
		public TmtbNonBillableBatch batch;
		//BigDecimal => Txn Id, Array with 0 as BigDecimal(rejectedAmount), 1 as Remarks
		public Map<Long,Object[]> rejectedTrips = new HashMap<Long,Object[]>();
		public Integer rejectedCount = new Integer(0);
		public BigDecimal rejectedAmount = new BigDecimal(0);
		
		public void calculateRejectedCountAndAmount(){
			BigDecimal rejectedAmount = BigDecimal.ZERO;
			if(rejectedTrips.size()>0){
				Set<Long> keys = rejectedTrips.keySet();
				for(Long key : keys){
					rejectedAmount = rejectedAmount.add( (BigDecimal) rejectedTrips.get(key)[0]);
				}
			}
			this.rejectedAmount = rejectedAmount;
			rejectedCount = rejectedTrips.size();
		}
	}
}
