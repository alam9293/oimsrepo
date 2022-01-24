package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.MinutesOfMeeting;

public interface MinutesOfMeetingService {

	public MinutesOfMeeting saveMinutesOfMeeting(MinutesOfMeeting minutesOfMeetings);

	public List<MinutesOfMeeting> getMinutesOfMeetinguserId(String userId);

	List<MinutesOfMeeting> getByuserId(String userId);

	public List<MinutesOfMeeting> getAlluserId();
}
