package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.MeetingSchedulerDis;

@Repository
public interface DisMeetingSchedulerRepository extends JpaRepository<MeetingSchedulerDis, String> {

}
