package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.MeetingScheduler;
import com.webapp.ims.repository.MeetingSchedulerRepository;
import com.webapp.ims.service.MeetingSchedulerService;

@Service
@Transactional
public class MeetingSchedulerServiceImpl  implements MeetingSchedulerService{

	@Autowired
	MeetingSchedulerRepository meetingSchedulerRepository;
	@Override
	public MeetingScheduler saveMeetingScheduler(MeetingScheduler meetingScheduler) {
		
		return meetingSchedulerRepository.save(meetingScheduler);
	}
	@Override
	public List<MeetingScheduler> getMeetingSchedulerByuserId(String userId) {
		
		return meetingSchedulerRepository.findAll();
	}
}
