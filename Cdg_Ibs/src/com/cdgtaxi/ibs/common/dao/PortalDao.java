package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cdgtaxi.ibs.common.model.IttbCpCustCardIssuance;
import com.cdgtaxi.ibs.common.model.IttbCpLogin;
import com.cdgtaxi.ibs.common.model.IttbCpLoginNew;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;

public interface PortalDao extends GenericDao {
	public List<IttbCpLogin> searchPortalUser(String userLoginId, String cardNo,
			Integer contactPersonId, String contactPersonName, Integer accountNo, String accountName, String masterLoginId);
	public List<IttbCpLoginNew> searchNewPortalUser(String userLoginId, String cardNo,
			Integer contactPersonId, String contactPersonName, Integer accountNo, String accountName, String masterLoginId, String accessId);
	
	public List<IttbCpLogin> getPortalUser(BigDecimal productNo);

	public List<IttbCpLoginNew> getPortalUserNew(BigDecimal productNo);
	
	public List<IttbCpCustCardIssuance> getCustomerCardIssuance(String cardNo);

	public List<IttbCpMasterLogin> getCpMasterLogin(String masterLoginNo);
	public IttbCpMasterLogin getCpMasterLogin(Integer masterLoginNo);
	public IttbCpMasterTagAcct getMasterLoginTagAcct(Integer masterLoginNo, String acctName, Integer acctNo);
	public List<IttbCpMasterTagAcct> getMasterLoginTagAcct(Integer masterLoginNo);
	
	public IttbCpMasterLogin getCpMasterLoginById(String loginId);
}