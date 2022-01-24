package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.APPLICATION_ID;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.model.DisLogin;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.ResponseQuery;
import com.webapp.ims.model.ResponseQueryDocument;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.RaiseQueryRepository;
import com.webapp.ims.service.RaiseQueryService;
import com.webapp.ims.service.ResponseQueryDocService;
import com.webapp.ims.service.ResponseQueryService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.service.impl.GlobalServiceUtil;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
public class ResponseQueryController {
	private final Logger logger = LoggerFactory.getLogger(ResponseQueryController.class);
	private ResponseQueryService respService;
	private RaiseQueryService rqService;
	private List<RaiseQuery> rqList;
	private ResponseQueryDocService respQueryDocService;
	private GlobalServiceUtil globalServiceUtil;

	SoapConsumeEx soapdetails = null;

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;
	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;
	@Autowired
	RaiseQueryService raiseQueryService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	RaiseQueryRepository raiseQueryRepository;

	@Autowired
	public ResponseQueryController(ResponseQueryService respService, RaiseQueryService rqService,
			ResponseQueryDocService respQueryDocService, GlobalServiceUtil globalServiceUtil) {
		super();
		this.respService = respService;
		this.rqService = rqService;
		this.respQueryDocService = respQueryDocService;
		this.globalServiceUtil = globalServiceUtil;
	}

	@GetMapping(value = "/queryResponseOld")
	public ModelAndView queryResponseOld(Model model, HttpSession session) {
		try {
			model.addAttribute("queryResponse", new ResponseQuery());
			String applId = (String) session.getAttribute("appId");
			String raiseQueryid = null;
			String respQueryid = null;
			if (applId != null) {
				raiseQueryid = rqService.findQueryIdByApplId(applId);
			}
			if (raiseQueryid != null) {
				respQueryid = raiseQueryid.substring(0, raiseQueryid.length() - 2) + "RS";
			}
			if (respQueryid != null) {
				model.addAttribute("raiseQueryIdList", rqService.getQueryIdListByApplId(applId));
				rqList = respService.findRaisedQueryListByQueryId(raiseQueryid);
				model.addAttribute("raisedQueryList", rqList);
			}
		} catch (Exception e) {
			logger.error(String.format("55555 queryResponse exception 55555 %s", e.getMessage()));
		}
		return new ModelAndView("track-application");
	}

	@GetMapping(value = "/queryResponse")
	public ModelAndView queryResponse(Model model, @RequestParam Map<String, String> params, HttpSession session) {
		logger.debug("queryResponses form initilization..");
		try {

			Map<String, String> param = (Map<String, String>) session.getAttribute("params");

			System.out.println("param for query response" + param);
			SoapDataModel niveshSoapData = new SoapDataModel();
			// soapdetails = new SoapConsumeEx(params);
			Map<String, String> niveshResponse = param; // new HashMap<>(soapdetails.soapwebservice());
			// System.out.println("params.getappId" + param.get("TxtUnitID"));

			niveshResponse.put("appID", niveshResponse.get("TxtUnitID") + "A1");
			niveshSoapData.setNiveshSoapResponse(niveshResponse);
			model.addAttribute("niveshSoapResponse", niveshSoapData);
			String raiseQueryid = null;
			String respQueryid = null;
			String applId = niveshResponse.get("appID");
			List<RaiseQuery> raiseQueryList = new ArrayList<>();
			System.out.println("appIsd" + applId);
			if (niveshResponse.get("TxtUnitID") != null) {

				// raiseQueryList = rqService.getRaiseQueryList();

			}
			// raiseQueryList = raiseQueryService.searchByApplicantId(applId);
			raiseQueryList = rqService.getRaiseQueryList();
			List<String> idList = new ArrayList<>();
			for (RaiseQuery raiseQuery : raiseQueryList) {
				idList.add(raiseQuery.getId());
			}
//			if (applId != null) {
//				raiseQueryid = rqService.findQueryIdByApplId(applId);
//			}
//			if (raiseQueryid != null) {
//				respQueryid = raiseQueryid.substring(0, raiseQueryid.length() - 2) + "RS";
//			}
//			if (respQueryid != null) {
//			
//				rqList = respService.findRaisedQueryListByQueryId(raiseQueryid);
//				model.addAttribute("raisedQueryList", rqList);
//			}

//			List<String> idList=	rqService.getQueryIdListByApplId(applId);
			model.addAttribute("raiseQueryIdList", idList);
			model.addAttribute("raisedQueryList", raiseQueryList);

		} catch (Exception e) {
			logger.error(String.format("***** queryResponse exception ***** %s", e.getMessage()));
		}
		model.addAttribute("queryResponse", new ResponseQuery());
		return new ModelAndView("track-application");
	}

	private void searchByApplicantId(String applId) {
		// TODO Auto-generated method stub

	}

	/**
	 * The purpose of this method is to save QueryResponse records in table and
	 * document in MongoDB.
	 */
	public void commonSaveQueryResponse(ResponseQuery respQuery, Model model, HttpSession session,
			MultipartFile multipartFile, String appliId) {

		try {

			// String applId = (String) session.getAttribute("appId");
			String applId = (String) session.getAttribute(APPLICATION_ID);
			String respQueryid = null;
			// appId.substring(0, appId.length() - 2) + "RS";
			String respRqid = null;
			// appId.substring(0, appId.length() - 2) + "RQ";
			// respQuery.setRespId(respQueryid);

			respQuery.setReqId(respQuery.getReqId());
			respQuery.setRespApplId(appliId);
			respQuery.setRespRqid(respRqid);
			respQuery.setRespStatus("active");
			respQuery.setRespCreatedBy("User");
			respQuery.setRespModifiyDate(new Date());
			respQuery.setRespFilename(multipartFile.getOriginalFilename());

			model.addAttribute("queryResponse", respQuery);
			respService.saveResponseQuery(respQuery);
			respQueryDocService.saveAndUpdateRespQueryDoc(multipartFile, session);// save document in MongoDB

			respQuery.setRespClarifyDtl(null);
			respQuery.setRespFilename(null);
			if (!respQueryid.isEmpty()) {
				model.addAttribute("raiseQueryIdList", rqService.getQueryIdListByApplId(appliId));
				rqList = respService.findRaisedQueryListByQueryId(respRqid);
				model.addAttribute("raisedQueryList", respService.findRaisedQueryListByQueryId(respRqid));
			}

			if (appliId != null) {
				String status = "active";
				List<RaiseQuery> raiseQuery = raiseQueryRepository.updateStatusByApplicantId(appliId, status);
			}

		} catch (Exception e) {
			logger.error(String.format("@@@@@ commonSaveQueryResponse exception @@@@@ %s", e.getMessage()));
		}
	}

	/**
	 * This method saves query response record filled by Investor in the table
	 * Response_Query and returns track-application.jsp view page.
	 */
	@PostMapping(value = "/saveQueryResponse")
	public ModelAndView saveQueryResponse(@Validated @ModelAttribute("queryResponse") ResponseQuery respQuery,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("respFilename") MultipartFile multipartFile) throws ParseException {

		Map<String, String> param = (Map<String, String>) session.getAttribute("params");

		System.out.println("param for query response" + param);
		SoapDataModel niveshSoapData = new SoapDataModel();

		Map<String, String> niveshResponse = param;
		System.out.println(niveshResponse.get("TxtUnitID"));
		System.out.println("params.getappId" + niveshResponse.get("TxtUnitID"));
		String appId = niveshResponse.get("TxtUnitID") + "A1";
		session.setAttribute("appId", niveshResponse.get("TxtUnitID") + "A1");
		session.setAttribute("userId", respQuery.getRespUserId());
		
		String besiId = "";
		String appliId = "";
		
		Object niveshResponse1 = session.getAttribute("niveshSoapResponse");

		Map<String, String> businessResponce = ((SoapDataModel) niveshResponse1).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
			
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				besiId = entry.getValue() + "B1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appliId = entry.getValue() + "A1";
			}
		}

		commonSaveQueryResponse(respQuery, model, session, multipartFile, appliId);
		return new ModelAndView("track-application");
	}

	@PostMapping(value = "/saveQueryResponseDIC")
	public ModelAndView saveQueryResponseDIC(@Validated @ModelAttribute("queryResponse") ResponseQuery respQuery,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("respFilename") MultipartFile multipartFile) {
		Map<String, String> param = (Map<String, String>) session.getAttribute("params");

		System.out.println("param for query response" + param);
		SoapDataModel niveshSoapData = new SoapDataModel();
		// soapdetails = new SoapConsumeEx(params);
		Map<String, String> niveshResponse = param; // new HashMap<>(soapdetails.soapwebservice());
		System.out.println(niveshResponse.get("TxtUnitID"));
		String appId = niveshResponse.get("TxtUnitID") + "A1";
		commonSaveQueryResponse(respQuery, model, session, multipartFile, appId);
		return new ModelAndView("track-application");
	}

	public void commonViewQueryResponse(Model model, HttpSession session) {
		try {
			String userid = (String) session.getAttribute("userId");
			List<String> appIdList = respService.getApplicationIdList(userid);
			model.addAttribute("appIdList", appIdList);
			List<ResponseQuery> respQueryList = respService.getResponseQueryListByUserId(userid);
			String applId = (String) session.getAttribute("appId");
			ResponseQuery rqFromdb = null;
			if (respService.findResponseQueryByApplId(applId) != null) {
				rqFromdb = respService.findResponseQueryByApplId(applId);
			}
			model.addAttribute("viewQueryResponse", rqFromdb);
			model.addAttribute("responseQueryList", respQueryList);
		} catch (Exception e) {
			logger.error(String.format("+++++ commonViewQueryResponse exception +++++ %s", e.getMessage()));
		}
	}

	/**
	 * This method is reponsible to fetch applicationId list and QueryResponse List
	 * from table Response_Query and returns data in query-response.jsp view page.
	 */
	@GetMapping(value = "/viewQueryResponse")
	public ModelAndView viewQueryResponse(Model model, HttpSession session) {
		commonViewQueryResponse(model, session);
		return new ModelAndView("query-response");
	}

	@GetMapping(value = "/viewQueryResponseDIC")
	public ModelAndView viewQueryResponseDIC(Model model, HttpSession session) {
		commonViewQueryResponse(model, session);
		return new ModelAndView("query-response-dic");
	}

	/**
	 * This method is responsible to download ResponseQuery PDF file from MongoDB
	 * collection.
	 */
	@GetMapping(value = "/downloadRespQueryDoc")
	public void downloadRespQueryDoc(@RequestParam(value = "fileName", required = false) String fileName, Model model,
			HttpServletResponse response, HttpSession session) {

		List<ResponseQueryDocument> respDocList = respQueryDocService.getResponseQueryDocList();
		if (!respDocList.isEmpty()) {
			for (ResponseQueryDocument rqDoc : respDocList) {
				if (fileName.equals(rqDoc.getFileName())) {
					String beDocName = rqDoc.getFileName();
					byte[] beDocData = rqDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(beDocName, beDocData, response);
					break;
				}
			}
		}

	}

}
