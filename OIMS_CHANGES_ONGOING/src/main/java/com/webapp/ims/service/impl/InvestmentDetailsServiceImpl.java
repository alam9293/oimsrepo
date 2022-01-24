package com.webapp.ims.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.Dept_InvestmentDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.repository.DeptInvestmentDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.service.InvestmentDetailsService;

@Service
@Transactional
public class InvestmentDetailsServiceImpl implements InvestmentDetailsService {

	@Autowired
	InvestmentDetailsRepository invDtlRepos;

	@Autowired
	DeptInvestmentDetailsRepository deptinvDtlRepos;

	@Override
	public List<InvestmentDetails> getAllInvestmentDetails() {
		return invDtlRepos.findAll();
	}

	@Override
	public InvestmentDetails saveInvestmentDetails(InvestmentDetails investmentDetails) {
		Dept_InvestmentDetails obj = new Dept_InvestmentDetails();
		BeanUtils.copyProperties(investmentDetails, obj);

		deptinvDtlRepos.saveAndFlush(obj);
		return invDtlRepos.saveAndFlush(investmentDetails);
	}

	@Override
	public Optional<InvestmentDetails> getInvestmentDetailsById(Long id) {
		return invDtlRepos.findById(id);
	}

	@Override
	public void deleteInvestmentDetailsById(Long id) {
		invDtlRepos.deleteById(id);
	}

	@Override
	public void deleteInvestmentDetails(InvestmentDetails investmentDetails) {
		invDtlRepos.delete(investmentDetails);
	}

	@Override
	public InvestmentDetails updateInvestmentDetails(InvestmentDetails investmentDetails) {
		return invDtlRepos.saveAndFlush(investmentDetails);
	}

	@Override
	public InvestmentDetails getInvestmentDetailsById(String id) {
		return invDtlRepos.getInvestmentDetailsById(id);
	}

	@Override
	@Query("from InvestmentDetails where applId = :appId ")
	public InvestmentDetails getInvestmentDetailsByapplId(String appId) {
		return invDtlRepos.getInvestmentDetailsByapplId(appId);
	}

	@Override
	public String getInvDetailIdByapplId(String appId) {
		return invDtlRepos.getInvDetailIdByapplId(appId);
	}

	public void updateInvestmentBasicDetails(String invIndType, Date invCommenceDate, Date propCommProdDate,
			String regiOrLicense) {
		invDtlRepos.updateInvestmentBasicDetails(invIndType, invCommenceDate, propCommProdDate, regiOrLicense);
	}

}
