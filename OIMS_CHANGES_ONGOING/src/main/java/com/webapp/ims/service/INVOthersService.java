package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import com.webapp.ims.model.INVOthers;

public interface INVOthersService {

//	public List<InvMeansOfFinance> getMeansOfFinanceList();
//
//	public InvMeansOfFinance saveMeansOfFinance(InvMeansOfFinance mof);
//
//	/* public Optional<InvMeansOfFinance> getMeansOfFinanceById(Integer id); */
//	
//	public List<InvMeansOfFinance> saveMeansOfFinanceList(List<InvMeansOfFinance> mofList);
//
//	public void deleteMeansOfFinanceById(String string);
//	public InvMeansOfFinance getMeansOfFinanceById (String mofId);
//	public List<InvMeansOfFinance> getInvMeansOfFinanceListById(String id);
//	
	public List<INVOthers> getAllByINV_ID(String INV_ID);

	public void save(INVOthers invOthers);

	public Optional<INVOthers> findById(String ID);

	public void deleteById(String ID);
}
