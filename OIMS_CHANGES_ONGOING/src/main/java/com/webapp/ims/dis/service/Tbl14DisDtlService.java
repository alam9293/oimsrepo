package com.webapp.ims.dis.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl14DisDtl;

public interface Tbl14DisDtlService 
{
	public void saveDisAmountDtlTbl14(EvaluateApplicationDisTbl14DisDtl Tbl14DisDtl);
	public void removeDisAmountDtlTbl14(String id);
	public List<EvaluateApplicationDisTbl14DisDtl> getDataDisDtlTbl14byAppid(String AppId);
	public EvaluateApplicationDisTbl14DisDtl getDataDisDtlTbl14byId(String Id);
	public Date getCurrentTime();
}
