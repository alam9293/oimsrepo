package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import com.webapp.ims.model.InvMeansOfFinance;

public interface MeansOfFinanceService {

	public List<InvMeansOfFinance> getMeansOfFinanceList();

	public InvMeansOfFinance saveMeansOfFinance(InvMeansOfFinance mof);

	/* public Optional<InvMeansOfFinance> getMeansOfFinanceById(Integer id); */

	public List<InvMeansOfFinance> saveMeansOfFinanceList(List<InvMeansOfFinance> mofList);

	public void deleteMeansOfFinanceById(String string);

	public InvMeansOfFinance getMeansOfFinanceById(String mofId);

	public List<InvMeansOfFinance> getInvMeansOfFinanceListById(String id);

	public Optional<InvMeansOfFinance> findById(String ID);

	public void deleteById(String ID);

//	
public List<InvMeansOfFinance> getAllByINV_ID(String INV_ID);

public List<InvMeansOfFinance> getMeansOfFinanceApcById(String pwApcId);
}
