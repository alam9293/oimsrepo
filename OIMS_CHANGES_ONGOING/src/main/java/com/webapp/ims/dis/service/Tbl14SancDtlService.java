package com.webapp.ims.dis.service;

import java.util.Date;
import java.util.List;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl14SancdDtl;

public interface Tbl14SancDtlService 
{
	public void saveSancdAmountDtlTbl14(EvaluateApplicationDisTbl14SancdDtl Tbl14SancdDtl);
	public void removeSancdAmountDtlTbl14(String id);
	public List<EvaluateApplicationDisTbl14SancdDtl> getDataSancdDtlTbl14byAppid(String AppId);
	public Date getCurrentTime();
}
