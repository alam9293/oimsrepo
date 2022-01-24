package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.model.MeetingScheduler;

@Repository
public interface MeetingSchedulerRepository extends JpaRepository<MeetingScheduler, String>{

	
}
