package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import com.webapp.ims.model.INVInstalledCapacities;

public interface INVInstalledCapacitiesService {

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
	public List<INVInstalledCapacities> getAllByINV_ID(String INV_ID);

	public void save(INVInstalledCapacities iNVInstalledCapacities);

	public Optional<INVInstalledCapacities> findById(String ID);

	public void deleteById(String ID);
}
