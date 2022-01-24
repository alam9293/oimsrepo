package com.webapp.ims.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ExistProjDetailsDocument;
@Repository
public interface ExistProjDocRepository extends MongoRepository<ExistProjDetailsDocument, String> {
	
	@Query("Select pdDoc from ExistProjDetailsDocument pdDoc where pdDoc.projectId=:projectId")
	ExistProjDetailsDocument getProjDocByProjectId(@Param(value = "projectId")String projectId);
	@Query("Select pdDoc from ExistProjDetailsDocument pdDoc where pdDoc.docId=:docId")
	ExistProjDetailsDocument getProjDocByDocId(@Param(value = "docId")String docId);
	
	@Query("Select pdDoc from ExistProjDetailsDocument pdDoc where pdDoc.projectId=:projectId")
	List<ExistProjDetailsDocument> getProjDocListByProjectId(@Param(value = "projectId")String projectId);
	
	ExistProjDetailsDocument findByProjectId(String projectId);
	ExistProjDetailsDocument findByDocId(String docId);
}
