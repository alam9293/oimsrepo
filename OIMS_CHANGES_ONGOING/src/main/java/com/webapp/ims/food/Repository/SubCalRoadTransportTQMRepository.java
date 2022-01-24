package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.SubsidyCalForRoadTransportTQM;

@Repository
public interface SubCalRoadTransportTQMRepository extends JpaRepository<SubsidyCalForRoadTransportTQM, String>{

	public List<SubsidyCalForRoadTransportTQM> getDetailsByunitId(String unitId);
}
