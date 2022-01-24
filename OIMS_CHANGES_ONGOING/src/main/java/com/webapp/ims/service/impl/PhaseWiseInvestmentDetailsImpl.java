package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DIS_PhaseWiseInvestmentDetails;
import com.webapp.ims.dis.repository.DISPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.PhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;

@Service
@Transactional
public class PhaseWiseInvestmentDetailsImpl implements PhaseWiseInvestmentDetailsService {

	@Autowired
	PhaseWiseInvestmentDetailsRepository pwInvRepo;
	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPwInvRepository;

	@Autowired
	DISPhaseWiseInvestmentDetailsRepository disRepository;

	@Override
	public List<PhaseWiseInvestmentDetails> getAllpwInvDetails() {
		return pwInvRepo.findAll();
	}

	@Override
	public PhaseWiseInvestmentDetails savePwInvDetails(PhaseWiseInvestmentDetails pwInvestmentDetails) {
		return pwInvRepo.saveAndFlush(pwInvestmentDetails);
	}

	/*
	 * @Override public Optional<PhaseWiseInvestmentDetails>
	 * getPwInvDetailsById(Long id) { return pwInvRepo.findById(id); }
	 */

	@Override
	public void deletePwInvDetails(PhaseWiseInvestmentDetails pwInvestmentDetails) {
		pwInvRepo.delete(pwInvestmentDetails);
	}

	@Override
	public PhaseWiseInvestmentDetails updatePwInvDetails(PhaseWiseInvestmentDetails pwInvestmentDetails) {
		return pwInvRepo.saveAndFlush(pwInvestmentDetails);
	}

	@Override
	public List<PhaseWiseInvestmentDetails> savePwInvListDetails(List<PhaseWiseInvestmentDetails> pwInvList) {
		for (PhaseWiseInvestmentDetails phaseWiseInvestmentDetails : pwInvList) {
			Dept_PhaseWiseInvestmentDetails obj = new Dept_PhaseWiseInvestmentDetails();
			BeanUtils.copyProperties(phaseWiseInvestmentDetails, obj);
			deptPwInvRepository.save(obj);
		}

		return pwInvRepo.saveAll(pwInvList);
	}

	@Override
	public List<PhaseWiseInvestmentDetails> getPwInvDetailListById(String id) {
		return pwInvRepo.getPwInvDetailsById(id);
	}

	@Override
	public void deletePwInvDetailsById(String id) {
		deptPwInvRepository.deleteById(id);
		pwInvRepo.deleteById(id);

	}

	@Override
	public List<DIS_PhaseWiseInvestmentDetails> saveDpetPwInvListDetails(
			List<DIS_PhaseWiseInvestmentDetails> pwInvList) {
		// TODO Auto-generated method stub
		return disRepository.saveAll(pwInvList);
	}

	@Override
	public PhaseWiseInvestmentDetails getPwInvDetailById(String id) {
		return pwInvRepo.getPwInvDetailsByPwId(id);
	}

	@Override
	public List<String> getPhasesById(String id) {
		return pwInvRepo.getPhasesBypwInvId(id);
	}

	public List<PhaseWiseInvestmentDetails> findByPwApcId(String apcId) {
	
		return pwInvRepo.findByPwApcId(apcId);
	}
	
	public void deleteBypwId(String pwId)
	{
		pwInvRepo.deleteById(pwId);	
	}
    
	public List<PhaseWiseInvestmentDetails> getPhaseNoObj(String pwInvId) {
		return pwInvRepo.getPhaseNoObj(pwInvId);
	}

	@Override
	public PhaseWiseInvestmentDetails getPwInvDetailByPwApcId(String pwApcId) {
		
		return pwInvRepo.findPWDetailsByPwApcId(pwApcId);
	}
}
