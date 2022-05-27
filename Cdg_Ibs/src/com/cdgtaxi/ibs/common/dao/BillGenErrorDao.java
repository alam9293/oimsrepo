package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenError;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;

public interface BillGenErrorDao extends GenericDao {
	public boolean checkForError(AmtbAccount topLevelAccount);
	public List<BmtbBillGenError> get(BmtbBillGenReq billGenRequest);
}

