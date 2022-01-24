package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;

@Repository
public interface SkillUnSkilledRepository extends JpaRepository<SkilledUnSkilledEmployemnt, String>{

	@Override
	public void deleteById(String id);
}
