package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.PrepareAgendaNotes;

@Repository
public interface PrepareAgendaNotesRepository extends JpaRepository<PrepareAgendaNotes, String>{
	
	 public PrepareAgendaNotes findPrepAgendaNotesByAppliId(String appId);

}
