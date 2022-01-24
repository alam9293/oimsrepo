package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.RegistrationCertificatefromAPEDA;

@Repository
public interface RegistrationCertificatefromAPEDARepository
		extends CrudRepository<RegistrationCertificatefromAPEDA, String> {

	@Query("from RegistrationCertificatefromAPEDA pd where pd.unit_id =:unitid")
	public List<RegistrationCertificatefromAPEDA> findAllByunit_id(String unitid);

}
