/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.model.EPFComputationAndEligibility;
import com.webapp.ims.dis.model.EPFDocument;
import com.webapp.ims.dis.model.StampDutyExemption;
import com.webapp.ims.dis.repository.DisEPFCompAndElgblRepository;
import com.webapp.ims.dis.repository.EPFDocumentRepository;
import com.webapp.ims.dis.service.EPFDocumentService;
import com.webapp.ims.dis.service.impl.DisEPFserviceImpl;
import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.service.BusinessEntityDetailsService;

@Controller
public class DisEpfController {
	private final Logger logger = LoggerFactory.getLogger(DisEpfController.class);
	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	DisEPFserviceImpl disEPFserviceImpl;

	@Autowired
	EPFDocumentRepository epfDocRepository;
	
	@Autowired
	DisEPFCompAndElgblRepository disEPFCompAndElgblRepository;

	@Autowired
	EPFDocumentService epfDocumentService;

	@GetMapping("/disepfincentive")
	public ModelAndView getEPF(@ModelAttribute("epfincentiveDeatilsForm") Disepfriem disEpfRiem, String appId,
			Model model, HttpSession session) {

		logger.info(" Start Disbursment, disApplicantDetails get Method ");
		

		try {
			appId = (String) session.getAttribute("appId");

			businessService.commonDetails(appId, session, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<EPFComputationAndEligibility> epfClaimList=disEPFCompAndElgblRepository.findAllByEPFApcId(appId);
		if(epfClaimList.size()>0)
		{
		model.addAttribute("epfClaimList", epfClaimList);
		}
		String epfId = appId + "EPF";
		EPFDocFromMongoDB(disEpfRiem, epfId);
		model.addAttribute("epfincentiveDeatilsForm", disEpfRiem);
		return new ModelAndView("/Disbursement/epfincentive");

	}

	@PostMapping("/epfReimSave")
	public ModelAndView setEPF(@ModelAttribute("epfincentiveDeatilsForm") Disepfriem disepfriem,
			@RequestParam("affidavitDoc") MultipartFile affidavitDoc,
			@RequestParam("copyFormDoc") MultipartFile copyFormDoc,
			@RequestParam("monthwiseDoc") MultipartFile monthwiseDoc, Model model, String appId, HttpSession session) {

		appId = (String) session.getAttribute("appId");
		String epfId = appId + "EPF";
		try {
			List<EPFDocument> multipartFileList = new LinkedList<EPFDocument>();

			if ((!affidavitDoc.getOriginalFilename().isEmpty() && affidavitDoc.getOriginalFilename() != null)
					&& affidavitDoc.getBytes() != null) {

				String fileName = affidavitDoc.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EPFDocument dbFile = new EPFDocument(affidavitDoc.getOriginalFilename(), affidavitDoc.getContentType(),
						affidavitDoc.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("affidavitDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);

			}
			if ((!copyFormDoc.getOriginalFilename().isEmpty() && copyFormDoc.getOriginalFilename() != null)
					&& copyFormDoc.getBytes() != null) {
				String fileName = copyFormDoc.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EPFDocument dbFile = new EPFDocument(copyFormDoc.getOriginalFilename(), copyFormDoc.getContentType(),
						copyFormDoc.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("copyFormDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			if ((!monthwiseDoc.getOriginalFilename().isEmpty() && monthwiseDoc.getOriginalFilename() != null)
					&& monthwiseDoc.getBytes() != null) {
				String fileName = monthwiseDoc.getOriginalFilename();
				if (fileName.contains("..")) {
					throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
				}

				EPFDocument dbFile = new EPFDocument(monthwiseDoc.getOriginalFilename(), monthwiseDoc.getContentType(),
						monthwiseDoc.getBytes());

				dbFile.setDocCreatedBy("User");
				dbFile.setFieldName("monthwiseDoc");
				dbFile.setDocUpdateDate(new Date());
				dbFile.setDocCreatedDate(new Date());
				multipartFileList.add(dbFile);
			}

			// For the saving of docs in MONgo DB
			epfDocumentService.saveAndUpdateMultipleFiles(multipartFileList, session);

		} catch (Exception e) {
			logger.error(String.format("***** EPF Document exception  **** %s", e.getMessage()));
		}
		
		
		disepfriem.setAppId(appId);
		disepfriem.setId("EPF" + appId);
		disepfriem.setStatus("ADMIN");
		disepfriem.setCreateBy("ADMIN");
		disepfriem.setCreateDate(new Date());

		disEPFserviceImpl.saveEPF(disepfriem);
		
		return new ModelAndView("redirect:/disincentivetype");

	}

	private void EPFDocFromMongoDB(Disepfriem disepfriem, String epfId) {

		try {
			List<EPFDocument> epfDocList = epfDocRepository.getListBydisEpfId(epfId);
			if (epfDocList != null) {

				for (int i = 0; i < epfDocList.size(); i++) {
					EPFDocument epfDoc = epfDocList.get(i);
					if (epfDoc != null) {
						if (i == 0) {
							byte[] encodeBase64 = Base64.encodeBase64(epfDoc.getData());
							String affidavitDocBase64File = new String(encodeBase64, StandardCharsets.UTF_8);
							disepfriem.setAffidavitDocBase64File(affidavitDocBase64File);
							disepfriem.setAffidavitDoc(epfDoc.getFileName());
						}
						if (i == 1) {
							byte[] encodeBase64 = Base64.encodeBase64(epfDoc.getData());
							String copyFormDocBase64File = new String(encodeBase64, StandardCharsets.UTF_8);
							disepfriem.setCopyFormDocBase64File(copyFormDocBase64File);
							disepfriem.setCopyFormDoc(epfDoc.getFileName());
						}

						if (i == 2) {
							byte[] encodeBase64 = Base64.encodeBase64(epfDoc.getData());
							String monthwiseDocBase64File = new String(encodeBase64, StandardCharsets.UTF_8);
							disepfriem.setMonthwiseDocBase64File(monthwiseDocBase64File);
							disepfriem.setMonthwiseDoc(epfDoc.getFileName());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ EPF Document $$$$ %s", e.getMessage()));
		}

	}
	
	@RequestMapping(value = "/saveEPFClaimTbl", method = RequestMethod.POST)
	public ModelAndView saveEPfClaimTable(@ModelAttribute("stampDisbursement") Disepfriem disepfriem, Model model,
			HttpSession session)
	{
		String appId = (String) session.getAttribute("appId");

		EPFComputationAndEligibility epfComputAndElig=new EPFComputationAndEligibility();
	
		
		epfComputAndElig.setEpfApcId(appId);
		epfComputAndElig.setEpfComputFinYr(disepfriem.getEpfComputFinYr());
		epfComputAndElig.setDateFrom(disepfriem.getDateFrom());
		epfComputAndElig.setDateTo(disepfriem.getDateTo());
		epfComputAndElig.setEmployerContributionEPF(disepfriem.getEmployerContributionEPF());
		
		
		String editEPFId=(String) session.getAttribute("editEPFId");
		if(editEPFId == null || editEPFId.isEmpty() || editEPFId == "")
		{
		Random random = new Random();   
		// Generates random integers 0 to 49  
		int x = random.nextInt(50);  
		 
		epfComputAndElig.setEpfComputeId(appId + "EPF" + x);
		}
		else
		{
			epfComputAndElig.setEpfComputeId(editEPFId);
			session.setAttribute("editEPFId", null);
		}
		disEPFCompAndElgblRepository.save(epfComputAndElig);
		
		return new ModelAndView("redirect:/disepfincentive");
		
	}
	
	@RequestMapping(value="/removeEpfDetailsById", method=RequestMethod.GET)
	public ModelAndView deleteEpfDetails(@RequestParam("removeEPFItem") String epfId, HttpSession session, Model model)

	{
		System.out.println("EPF data"+epfId);
		disEPFCompAndElgblRepository.deleteById(epfId);
		
		return new ModelAndView("redirect:/disepfincentive");
		
	}
	
	@RequestMapping(value="/editEPFDetailsById", method=RequestMethod.GET)
	public ModelAndView editEPFDetails(@RequestParam("editEPFDetails") String editEPFId, HttpSession session, Model model,
			Disepfriem disepfriem, EPFComputationAndEligibility epfComputationAndEligibility, String appId  )
	
	{
		
		
		epfComputationAndEligibility=disEPFCompAndElgblRepository.findByEPFId(editEPFId);
		
		disepfriem.setEpfComputFinYr(epfComputationAndEligibility.getEpfComputFinYr());
		disepfriem.setDateFrom(epfComputationAndEligibility.getDateFrom());
		disepfriem.setDateTo(epfComputationAndEligibility.getDateTo());
		disepfriem.setEmployerContributionEPF(epfComputationAndEligibility.getEmployerContributionEPF());
		model.addAttribute("finYr", epfComputationAndEligibility.getEpfComputFinYr());
		
		model.addAttribute("epfincentiveDeatilsForm", disepfriem);
		
		session.setAttribute("editEPFId", editEPFId);
		
		try {
			appId = (String) session.getAttribute("appId");

			businessService.commonDetails(appId, session, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String epfId = appId + "EPF";
		EPFDocFromMongoDB(disepfriem, epfId);
		model.addAttribute("epfincentiveDeatilsForm", disepfriem);
		
		
		List<EPFComputationAndEligibility> epfClaimList=disEPFCompAndElgblRepository.findAllByEPFApcId(appId);
		if(epfClaimList.size()>0)
		{
		model.addAttribute("epfClaimList", epfClaimList);
		}
		model.addAttribute("edit", "edit");
		return new ModelAndView("/Disbursement/epfincentive");
		
	}
	
}

