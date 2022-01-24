package com.webapp.ims.dis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.MinutesOfMeetingDis;
import com.webapp.ims.dis.repository.DisMinutesOfMeetingRepository;
import com.webapp.ims.dis.service.DisMinutesOfMeetingService;

@Service
@Transactional
public class DisMinutesOfMeetingServiceImpl implements DisMinutesOfMeetingService {

	@Autowired
	DisMinutesOfMeetingRepository disMinutesOfMeetingRepository;

	@Override
	public MinutesOfMeetingDis saveMinutesOfMeeting(MinutesOfMeetingDis minutesOfMeetings) {

		return disMinutesOfMeetingRepository.saveAndFlush(minutesOfMeetings);
	}

	@Override
	public List<MinutesOfMeetingDis> getByuserId(String userId) {

		return disMinutesOfMeetingRepository.getByuserId(userId);
	}

	@Override
	public List<MinutesOfMeetingDis> getAlluserId() {

		return disMinutesOfMeetingRepository.findAll();
	}

	@Override
	public List<MinutesOfMeetingDis> getMinutesOfMeetinguserId(String userId) {
		return disMinutesOfMeetingRepository.findAll();
	}

}
