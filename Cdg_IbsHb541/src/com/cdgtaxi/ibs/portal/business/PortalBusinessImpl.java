package com.cdgtaxi.ibs.portal.business;

import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;

public class PortalBusinessImpl extends GenericBusinessImpl implements PortalBusiness {

	public List searchPortalUser(String userLoginId, String cardNo,
			Integer contactPersonId, String contactPersonName, Integer accountNo, String accountName, String masterLoginId) {
		return this.daoHelper.getPortalDao().searchPortalUser(
				userLoginId, cardNo, contactPersonId, contactPersonName, accountNo, accountName, masterLoginId);
	}
	public List searchNewPortalUser(String userLoginId, String cardNo,
			Integer contactPersonId, String contactPersonName, Integer accountNo, String accountName, String masterLoginId, String accessId) {
		return this.daoHelper.getPortalDao().searchNewPortalUser(
				userLoginId, cardNo, contactPersonId, contactPersonName, accountNo, accountName, masterLoginId, accessId);
	}
	public List<IttbCpMasterLogin> searchMasterLogin(String masterLoginNo) {
		return this.daoHelper.getPortalDao().getCpMasterLogin(masterLoginNo);
	}
	public IttbCpMasterLogin getMasterLogin(Integer masterLoginNo) {
		return this.daoHelper.getPortalDao().getCpMasterLogin(masterLoginNo);
	}
	public List<IttbCpMasterTagAcct> getMasterLoginTagAcct(Integer masterLoginNo) {
		return this.daoHelper.getPortalDao().getMasterLoginTagAcct(masterLoginNo);
	}
	public IttbCpMasterTagAcct getMasterLoginTagAcct(Integer masterLoginNo, String accountName, Integer custNo) {
		return this.daoHelper.getPortalDao().getMasterLoginTagAcct(masterLoginNo, accountName, custNo);
	}
	
	public IttbCpMasterLogin getMasterLoginById(String loginId) {
		return this.daoHelper.getPortalDao().getCpMasterLoginById(loginId);
	}
}
