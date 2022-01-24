package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;
import com.webapp.ims.dis.model.DisStampDeauty;

@Service
public interface StampDutyService {
 

	String findDataById(String id);

	public DisStampDeauty getDetailsByStampApcId(String stampApcId);

}
