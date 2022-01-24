package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.service.MinutesOfMeetingService;

@Service
@Transactional
public class MinutesOfMeetingServiceImpl implements MinutesOfMeetingService {

	@Autowired
	MinutesOfMeetingRepository minutesOfMeetingRepository;

	@Override
	public MinutesOfMeeting saveMinutesOfMeeting(MinutesOfMeeting minutesOfMeetings) {

		return minutesOfMeetingRepository.save(minutesOfMeetings);
	}

	public List<MinutesOfMeeting> getByuserId(String userId) {

		return minutesOfMeetingRepository.getByuserId(userId);
	}

	public List<MinutesOfMeeting> getAlluserId() {

		return minutesOfMeetingRepository.findAll();
	}

	@Override
	public List<MinutesOfMeeting> getMinutesOfMeetinguserId(String userId) {
		// TODO Auto-generated method stub
		return minutesOfMeetingRepository.findAll();
	}

}
