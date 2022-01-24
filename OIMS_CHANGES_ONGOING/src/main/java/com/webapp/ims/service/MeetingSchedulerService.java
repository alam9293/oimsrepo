package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.MeetingScheduler;

public interface MeetingSchedulerService {
	
	public MeetingScheduler saveMeetingScheduler(MeetingScheduler meetingScheduler);
	public List<MeetingScheduler> getMeetingSchedulerByuserId(String userId);
}
