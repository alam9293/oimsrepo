package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import com.webapp.ims.model.InvBreakupCost;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;

public interface BreakupCostService {
	
	public List<InvBreakupCost> getBreakupCostList();

	public InvBreakupCost saveBreakupCost(InvBreakupCost brkupco);

	/* public Optional<InvBreakupCost> getBreakupCostById(Integer id); */
	
	public List<InvBreakupCost> saveInvBreakupList(List<InvBreakupCost> brkupList);

	public void deleteBreakupCostById(String string);
	public InvBreakupCost getBreakupCostById (String brkupId);
	public List<InvBreakupCost> getInvBreakupCostListById(String id);
	public InvBreakupCost updateInvBreakupCost(InvBreakupCost invbrkup);

}
