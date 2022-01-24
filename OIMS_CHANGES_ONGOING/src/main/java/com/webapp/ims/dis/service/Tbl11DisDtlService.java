package com.webapp.ims.dis.service;

import java.util.Date;
import java.util.List;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl11DisDtl;

public interface Tbl11DisDtlService 
{
	public void saveDisAmountDtlTbl11(EvaluateApplicationDisTbl11DisDtl Tbl11DisDtl);
	public void removeDisAmountDtlTbl11(String id);
	public List<EvaluateApplicationDisTbl11DisDtl> getDataDisDtlTbl11byAppid(String AppId);
	public EvaluateApplicationDisTbl11DisDtl getDataDisDtlTbl11byId(String Id);
	public Date getCurrentTime();
}
