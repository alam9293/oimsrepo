package com.webapp.ims.dis.service;

import java.util.List;

import com.webapp.ims.dis.model.MinutesOfMeetingDis;

public interface DisMinutesOfMeetingService {

	public MinutesOfMeetingDis saveMinutesOfMeeting(MinutesOfMeetingDis minutesOfMeetings);

	public List<MinutesOfMeetingDis> getMinutesOfMeetinguserId(String userId);

	List<MinutesOfMeetingDis> getByuserId(String userId);

	public List<MinutesOfMeetingDis> getAlluserId();
}
