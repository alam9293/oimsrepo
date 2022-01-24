package com.webapp.ims.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.webapp.ims.model.InvestmentDetails;

public interface InvestmentDetailsService {

	public List<InvestmentDetails> getAllInvestmentDetails();

	public InvestmentDetails saveInvestmentDetails(InvestmentDetails investmentDetails);

	public Optional<InvestmentDetails> getInvestmentDetailsById(Long id);

	public InvestmentDetails getInvestmentDetailsById(String id);

	public void deleteInvestmentDetailsById(Long id);

	public void deleteInvestmentDetails(InvestmentDetails investmentDetails);

	public InvestmentDetails updateInvestmentDetails(InvestmentDetails investmentDetails);

	public InvestmentDetails getInvestmentDetailsByapplId(String appId);
	
	public String getInvDetailIdByapplId(String appId);
	
	public void updateInvestmentBasicDetails(String invIndType ,Date invCommenceDate , Date propCommProdDate ,String regiOrLicense);
}
