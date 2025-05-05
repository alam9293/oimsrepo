package com.cdgtaxi.ibs.common.model.forms;

import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;

import java.util.ArrayList;
import java.util.List;

public class SearchNonBillableTxnsForm extends SearchNonBillableTxnForm {
    public List<TmtbNonBillableBatch> txnBatches = new ArrayList<TmtbNonBillableBatch>();
    public String jobNo;
    public String batchNo;
    public String aydenMerchantId;
    public List<String> recordType;
    public List<String> sourceList;
    public List<String> acquirerList;

    public void addTmtbNonBillableBatches(List<NonBillableBatchDto> batchList) {
        for (NonBillableBatchDto dto : batchList){
            txnBatches.add(new TmtbNonBillableBatch(dto));
        }
    }

    public void setTxnBatches(List<TmtbNonBillableBatch> batchList) {
        txnBatches = batchList;
    }
}

