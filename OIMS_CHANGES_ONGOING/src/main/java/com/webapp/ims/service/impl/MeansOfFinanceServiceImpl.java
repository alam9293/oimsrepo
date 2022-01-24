package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.InvMeansOfFinance;
import com.webapp.ims.repository.MeansOfFinanceRepository;
import com.webapp.ims.service.MeansOfFinanceService;

@Service
@Transactional
public class MeansOfFinanceServiceImpl implements MeansOfFinanceService {
	@Autowired
	private MeansOfFinanceRepository mofRepository;

	@Override
	public List<InvMeansOfFinance> getMeansOfFinanceList() {
		return mofRepository.findAll();
	}

	@Override
	public InvMeansOfFinance saveMeansOfFinance(InvMeansOfFinance mof) {
		return mofRepository.save(mof);
	}
	/*
	 * @Override public Optional<InvMeansOfFinance> getMeansOfFinanceById(Integer
	 * id) { return mofRepository.findById(id); }
	 */

	@Override
	public List<InvMeansOfFinance> saveMeansOfFinanceList(List<InvMeansOfFinance> mofList) {
		return mofRepository.saveAll(mofList);
	}

	@Override
	public InvMeansOfFinance getMeansOfFinanceById(String mofId) {
		return mofRepository.getInvMeansOfFinanceById(mofId);
	}

	@Override
	public List<InvMeansOfFinance> getInvMeansOfFinanceListById(String id) {
		return mofRepository.findAll();
	}

	@Override
	public void deleteMeansOfFinanceById(String id) {
		mofRepository.deleteById(id);

	}

	@Override
	public Optional<InvMeansOfFinance> findById(String ID) {
		return mofRepository.findById(ID);

	}

	@Override
	public void deleteById(String ID) {
		mofRepository.deleteById(ID);
	}
	public List<InvMeansOfFinance> getAllByINV_ID(String INV_ID)
	{
		return mofRepository.findByMofInvId(INV_ID);
		
	}

	@Override
	public List<InvMeansOfFinance> getMeansOfFinanceApcById(String pwApcId) {
		// TODO Auto-generated method stub
		return mofRepository.getInvMeansOfFinanceByApcId(pwApcId);
	}

}
