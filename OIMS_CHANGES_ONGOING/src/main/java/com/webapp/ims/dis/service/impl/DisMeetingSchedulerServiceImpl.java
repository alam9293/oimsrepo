package com.webapp.ims.dis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.MeetingSchedulerDis;
import com.webapp.ims.dis.repository.DisMeetingSchedulerRepository;
import com.webapp.ims.dis.service.DisMeetingSchedulerService;

@Service
@Transactional
public class DisMeetingSchedulerServiceImpl implements DisMeetingSchedulerService {

	@Autowired
	DisMeetingSchedulerRepository meetingSchedulerRepository;

	@Override
	public MeetingSchedulerDis saveDisMeetingScheduler(MeetingSchedulerDis meetingScheduler) {
		return meetingSchedulerRepository.save(meetingScheduler);
	}

	@Override
	public List<MeetingSchedulerDis> getDisMeetingSchedulerByuserId(String userId) {
		return meetingSchedulerRepository.findAll();
	}
}
