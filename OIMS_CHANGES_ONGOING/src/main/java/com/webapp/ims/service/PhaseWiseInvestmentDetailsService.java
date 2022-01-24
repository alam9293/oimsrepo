package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.dis.model.DIS_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;

public interface PhaseWiseInvestmentDetailsService {
	public List<PhaseWiseInvestmentDetails> getAllpwInvDetails();

	public PhaseWiseInvestmentDetails savePwInvDetails(PhaseWiseInvestmentDetails pwInvestmentDetails);

	public List<PhaseWiseInvestmentDetails> savePwInvListDetails(List<PhaseWiseInvestmentDetails> pwInvList);

	public List<DIS_PhaseWiseInvestmentDetails> saveDpetPwInvListDetails(
			List<DIS_PhaseWiseInvestmentDetails> pwInvList);

	/* public Optional<PhaseWiseInvestmentDetails> getPwInvDetailsById(Long id); */

	public List<PhaseWiseInvestmentDetails> getPwInvDetailListById(String id);

	public PhaseWiseInvestmentDetails getPwInvDetailById(String id);
	
	public PhaseWiseInvestmentDetails getPwInvDetailByPwApcId(String pwApcId);

	public void deletePwInvDetailsById(String string);

	public void deletePwInvDetails(PhaseWiseInvestmentDetails pwInvestmentDetails);

	public PhaseWiseInvestmentDetails updatePwInvDetails(PhaseWiseInvestmentDetails pwInvestmentDetails);

	public List<String> getPhasesById(String id);
	
	List<PhaseWiseInvestmentDetails> findByPwApcId(String apcId);

	public void deleteBypwId(String pwId);
	
	List<PhaseWiseInvestmentDetails> getPhaseNoObj(String pwInvId);

}
