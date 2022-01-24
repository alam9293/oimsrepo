package com.webapp.ims.dis.service;

import java.util.List;

import com.webapp.ims.dis.model.MeetingSchedulerDis;

public interface DisMeetingSchedulerService {

	public MeetingSchedulerDis saveDisMeetingScheduler(MeetingSchedulerDis meetingScheduler);

	public List<MeetingSchedulerDis> getDisMeetingSchedulerByuserId(String userId);
}
