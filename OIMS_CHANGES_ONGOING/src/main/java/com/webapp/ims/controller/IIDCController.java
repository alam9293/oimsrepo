/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.model.MomCirculateNote;
import com.webapp.ims.model.MomUploadDocuments;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.repository.MomUploadDocumentsRepository;
import com.webapp.ims.service.MinutesOfMeetingService;

/**
 * @author dell
 *
 */
@Controller
public class IIDCController {
	@Autowired
	MinutesOfMeetingService minutesOfMeetingService;
	@Autowired
	MomUploadDocumentsRepository momUploadDocumentsRepository;
	@Autowired
	MinutesOfMeetingRepository minutesOfMeetingRepository;
	
	@GetMapping(value = "/iIDCMomgo")
	public String iIDCMomgo(ModelMap model, HttpSession session) {
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MomCirculateNote> minutesOfMeetingEmpowerCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingSactionCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingGosCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingList = new ArrayList<>();
			// List<MinutesOfMeeting> minutesOfMeetingList =
			// minutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			// List<MinutesOfMeeting> minutesOfMeetingList = minutesOfMeetingService.getAlluserId();
			List<Object> minutesOfMeetingObjectList = minutesOfMeetingRepository.findAllMOMbyStatus("CS");

			Object[] row;
			ObjectMapper mapper = new ObjectMapper();
			MomCirculateNote momNote = new MomCirculateNote();
			for (Object object : minutesOfMeetingObjectList) {
				momNote = new MomCirculateNote();
				row = (Object[]) object;
				System.out.println(row.length);
				HashMap<String, Object> map = new HashMap<>();
				System.out.println("Element " + Arrays.toString(row));

				if (row[0] != null)
					map.put("id", row[0].toString());
				if (row[1] != null)
					map.put("gosAppID", row[1].toString());
				if (row[2] != null)
					map.put("minutesOfMeetingOrGos", row[2]);
				if (row[3] != null)
					map.put("committeeDepartments", row[3].toString());
				if (row[4] != null)
					map.put("dateOfMeeting", row[4]);
				if (row[5] != null)
					map.put("committeeName", row[5].toString());
				if (row[6] != null)
					map.put("gosName", row[6].toString());
				if (row[7] != null)
					map.put("deptName", row[7].toString());
				if (row[8] != null)
					map.put("noteReportStatus", row[8].toString());

				momNote = mapper.convertValue(map, MomCirculateNote.class);
				minutesOfMeetingList.add(momNote);
			}

			if (minutesOfMeetingList.size() > 0) {
				for (MomCirculateNote list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee") && list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs") && list.getNoteReportStatus().equalsIgnoreCase("Go Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return "iIDCMomgo";
		
	}
}
