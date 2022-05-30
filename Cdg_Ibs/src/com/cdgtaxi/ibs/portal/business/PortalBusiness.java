package com.cdgtaxi.ibs.portal.business;

import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;

public interface PortalBusiness extends GenericBusiness {
	public List searchPortalUser(String userLoginId, String cardNo, Integer contactPersonId, 
			String contactPersonName, Integer accountNo, String accountName, String masterLoginId);
	public List searchNewPortalUser(String userLoginId, String cardNo, Integer contactPersonId, 
			String contactPersonName, Integer accountNo, String accountName, String masterLoginId, String accessId);
	
	public List<IttbCpMasterLogin> searchMasterLogin(String masterLoginNo);
	public IttbCpMasterLogin getMasterLogin(Integer masterLoginNo);
	public List<IttbCpMasterTagAcct> getMasterLoginTagAcct(Integer masterLoginNo);
	public IttbCpMasterTagAcct getMasterLoginTagAcct(Integer masterLoginNo, String accountName, Integer custNo); 
	
	public IttbCpMasterLogin getMasterLoginById(String loginId);
}
