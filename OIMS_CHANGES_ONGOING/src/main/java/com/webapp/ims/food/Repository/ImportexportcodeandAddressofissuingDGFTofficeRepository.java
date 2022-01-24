package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.ImportexportcodeandAddressofissuingDGFToffice;

@Repository
public interface ImportexportcodeandAddressofissuingDGFTofficeRepository
		extends CrudRepository<ImportexportcodeandAddressofissuingDGFToffice, String> {

	@Query("from ImportexportcodeandAddressofissuingDGFToffice pd where pd.unit_id =:unitid")
	public List<ImportexportcodeandAddressofissuingDGFToffice> findAllByunit_id(String unitid);

}
