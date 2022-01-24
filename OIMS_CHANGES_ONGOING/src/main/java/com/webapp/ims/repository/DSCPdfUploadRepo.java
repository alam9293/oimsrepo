package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.model.DSCPdfUploadEntity;

public interface DSCPdfUploadRepo extends JpaRepository<DSCPdfUploadEntity, Integer>  {

	@Query("select a from DSCPdfUploadEntity a where a.id =(:id)")
	DSCPdfUploadEntity downloadFileById(int id);


}
