package com.webapp.ims.dis.service;

import java.util.Date;
import java.util.List;

import com.webapp.ims.dis.model.EvaluateApplicationDisTbl11SancdDtl;

public interface Tbl11SancDtlService 
{
	public void saveSancdAmountDtlTbl11(EvaluateApplicationDisTbl11SancdDtl Tbl11SancdDtl);
	public void removeSancdAmountDtlTbl11(String id);
	public List<EvaluateApplicationDisTbl11SancdDtl> getDataSancdDtlTbl11byAppid(String AppId);
	public Date getCurrentTime();
	
}
