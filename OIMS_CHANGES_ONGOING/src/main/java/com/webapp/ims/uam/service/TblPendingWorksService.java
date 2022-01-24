package com.webapp.ims.uam.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.webapp.ims.uam.model.TblApprovalWorks;

@Service
public interface TblPendingWorksService {
	
	public void saveDatatobeApprove(TblApprovalWorks tblApprovalWorks);

	public List<TblApprovalWorks> getListofPendingDataTobeApproved(String creator);

	public LinkedHashMap<String, String> getAprovalDataMap(long taskId);

	public void approveDisplayDataDeatil(TblApprovalWorks tblApprovalWorks,HttpSession session);
	
	public void disApproveDisplayDataDeatil(TblApprovalWorks tblApprovalWorks,HttpSession session);

	public TblApprovalWorks getApprovalDataDetailsBytaskId(long taskId);

}