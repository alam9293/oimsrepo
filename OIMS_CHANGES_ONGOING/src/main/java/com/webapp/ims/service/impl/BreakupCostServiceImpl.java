package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.InvBreakupCost;
import com.webapp.ims.repository.BreakupCostRepository;
import com.webapp.ims.service.BreakupCostService;

@Service
@Transactional
public class BreakupCostServiceImpl implements BreakupCostService {
	@Autowired
	private BreakupCostRepository brkupRepostry;

	@Override
	public InvBreakupCost getBreakupCostById(String brkupId) {
		return brkupRepostry.getInvBreakupCostById(brkupId);
	}

	@Override
	public List<InvBreakupCost> getBreakupCostList() {
		return brkupRepostry.findAll();
	}

	@Override
	public InvBreakupCost saveBreakupCost(InvBreakupCost brkupco) {
		return brkupRepostry.saveAndFlush(brkupco);
	}

	/*
	 * @Override public Optional<InvBreakupCost> getBreakupCostById(Integer id) {
	 * return brkupRepostry.findById(id); }
	 */
	@Override
	public List<InvBreakupCost> saveInvBreakupList(List<InvBreakupCost> brkupList) {
		return brkupRepostry.saveAll(brkupList);
	}

	@Override
	public List<InvBreakupCost> getInvBreakupCostListById(String id) {
		return brkupRepostry.findAll();
	}

	@Override
	public void deleteBreakupCostById(String id) {
		brkupRepostry.deleteById(id);

	}

	@Override
	public InvBreakupCost updateInvBreakupCost(InvBreakupCost invbrkup) {
		return brkupRepostry.saveAndFlush(invbrkup);
	}

}
