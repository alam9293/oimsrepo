package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.NiveshSoapData;

@Repository
public interface NiveshSoapDataRepository extends JpaRepository<NiveshSoapData, String> {

	@Query("select nsd.unit_id , nsd.appid , nsd.policy_name, nsd.company_name , ind.totalamountoftermloansanctioned, nsd.divison ,nsd.occupier_district_name , nsd.policy_id,  p.artifact_id,   p.policy_name from NiveshSoapData nsd join Policy p on p.policy_id  = nsd.policy_id and nsd.appid IS NOT NULL join InvestmentDetailsFood ind on ind.unit_id  = nsd.unit_id")
	public List<NiveshSoapData> getAllbyappid();
	// public List<NiveshSoapData> findNiveshSoapDataListByappid(@Param(value =
	// "appid") String appid);

}
