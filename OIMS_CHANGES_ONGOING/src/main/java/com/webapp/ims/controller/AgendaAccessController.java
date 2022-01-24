package com.webapp.ims.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.webapp.ims.dis.service.DisViewEvaluateService;
import com.webapp.ims.model.DigitalSignatureEntity;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ScrutinyDocument;
import com.webapp.ims.repository.ScrutinyDocumentRepo;
import com.webapp.ims.service.AgendaReportService;
import com.webapp.ims.service.DigitalSignatureService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;

import net.sf.jasperreports.engine.JRException;

@Controller
@SessionAttributes("appId")
public class AgendaAccessController {

	@Autowired
	AgendaReportService agendareport;

	@Autowired
	DigitalSignatureService digitalSignatureService;

	@Autowired
	DisViewEvaluateService disViewEvaluateService;

	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;

	@Autowired
	ScrutinyDocumentRepo scrutinyDocumentRepo;

	// @GetMapping(value = "/reportDisplay")
	// public String reportDisplay(Model model) {
	// model.addAttribute("fileName", "Pramod");
	// return "agendaReportDisplay";
	// }

	@GetMapping(value = "/ ")
	public String reportDisplay(@ModelAttribute("DigitalSignatureEntity") DigitalSignatureEntity digitalSignatureEntity,
			Model model) throws IOException, GeneralSecurityException, DocumentException {
		/// model.addAttribute("fileName", "Pramod");

		System.out.println("inside agenda : reportDisplay");
		HttpSession session = null;
		String str = agendareport.createDigitalSignature(model, session);
		model.addAttribute("DigitalSignatureEntity", digitalSignatureEntity);
		return "agendaReportDisplay";
	}

	// public void downloadBusinessEntityDoc(@RequestParam(value = "fileName",
	// required = false) String fileName,
	// HttpServletResponse response,Model model) throws JRException, IOException {

	@GetMapping(value = "/downloadReport")
	public void downloadBusinessEntityDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, Model model, HttpSession session) throws JRException, IOException {

		// byte[] output = agendareport.createAgendaReport("UPSWP200001502A2");
		// byte[] output = agendareport.createAgendaReportListMega(model, session,
		// prepareAgendaNotes)
		// downloadReport("report.pdf", output, response);
	}

	@PostMapping(value = "/downloadMega")
	public void downloadBusinessEntityMega(@RequestParam(value = "applicantID", required = false) String applicantID,
			@Validated PrepareAgendaNotes prepareAgendaNotes, HttpServletResponse response, Model model,
			HttpSession session) throws JRException, IOException {

		response.setHeader("Content-Disposition", "attachment; filename=reportWithAnnexure.zip");
		response.setHeader("Content-Type", "application/zip");
		// byte[] output = agendareport.createAgendaReport("UPSWP200001502A2");
		byte[] output = agendareport.createAgendaReportListMega(model, session, prepareAgendaNotes);

		ZipOutputStream zipOutputStream;
		try {
			zipOutputStream = new ZipOutputStream(response.getOutputStream());
			zipOutputStream.putNextEntry(new ZipEntry("report.pdf"));
			InputStream is = new ByteArrayInputStream(output);
			IOUtils.copy(is, zipOutputStream);

			String[] appIdArr = prepareAgendaNotes.getPrepareAgendaNote();
			for (String appId : appIdArr) {
				String ScruDocId = appId + "SCRU";
				List<ScrutinyDocument> scrutinyDocumentList = scrutinyDocumentRepo.getListByScruDocId(ScruDocId);
				if (!scrutinyDocumentList.isEmpty()) {
					for (ScrutinyDocument scruDoc : scrutinyDocumentList) {
						zipOutputStream.putNextEntry(new ZipEntry(scruDoc.getFileName()));
						is = new ByteArrayInputStream(scruDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}
			zipOutputStream.closeEntry();
			zipOutputStream.close();
		} catch (Exception e) {
		}

		// disViewEvaluateService.searchEvaluateDisList();
		// downloadReport("report.pdf", output, response);
	}

	@PostMapping(value = "/downloadLarge")
	public void downloadBusinessEntityLarge(@RequestParam(value = "fileName", required = false) String fileName,
			@Validated PrepareAgendaNotes prepareAgendaNotes, HttpServletResponse response, Model model,
			HttpSession session) throws JRException, IOException {

		response.setHeader("Content-Disposition", "attachment; filename=reportWithAnnexure.zip");
		response.setHeader("Content-Type", "application/zip");
		// byte[] output = agendareport.createAgendaReport("UPSWP200001502A2");
		byte[] output = agendareport.createAgendaReportListLarge(model, session, prepareAgendaNotes);

		ZipOutputStream zipOutputStream;
		try {
			zipOutputStream = new ZipOutputStream(response.getOutputStream());
			zipOutputStream.putNextEntry(new ZipEntry("report.pdf"));
			InputStream is = new ByteArrayInputStream(output);
			IOUtils.copy(is, zipOutputStream);

			String[] appIdArr = prepareAgendaNotes.getPrepareAgendaNote();
			for (String appId : appIdArr) {
				String ScruDocId = appId + "SCRU";
				List<ScrutinyDocument> scrutinyDocumentList = scrutinyDocumentRepo.getListByScruDocId(ScruDocId);
				if (!scrutinyDocumentList.isEmpty()) {
					for (ScrutinyDocument scruDoc : scrutinyDocumentList) {
						zipOutputStream.putNextEntry(new ZipEntry(scruDoc.getFileName()));
						is = new ByteArrayInputStream(scruDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}
			zipOutputStream.closeEntry();
			zipOutputStream.close();
		} catch (Exception e) {
		}

		// downloadReport("report.pdf", output, response);
	}

	// public void downloadBusinessEntityDisMega() {
	@GetMapping(value = "/downloadDisMega")
	public void downloadBusinessEntityDisMega(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, Model model, HttpSession session) throws JRException, IOException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		byte[] output = disViewEvaluateService.searchEvaluateDisList();
		downloadReport("report.pdf", output, response);

	}

	@GetMapping(value = "/downloadDisLarge")
	public void downloadBusinessEntityDisLarge(@RequestParam(value = "fileName", required = false) String fileName,
			@Validated PrepareAgendaNotes prepareAgendaNotes, HttpServletResponse response, Model model,
			HttpSession session) throws JRException, IOException {

		// byte[] output = agendareport.createAgendaReport("UPSWP200001502A2");
		byte[] output = agendareport.createAgendaReportListLarge(model, session, prepareAgendaNotes);
		downloadReport("report.pdf", output, response);
	}

	@GetMapping(value = "/verifySignature")
	public String verifyDigitalSignature(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, Model model, HttpSession session) throws JRException, IOException,
			GeneralSecurityException, DocumentException, com.itextpdf.text.DocumentException {
		System.out.println("inside /verifySignature");
		String str = agendareport.createDigitalSignature(model, session);

		model.addAttribute("str", str);
		// digitalSignatureService.checkDigitalSingleItem(appId);
		// digitalSignatureService.checkDigitalSignature();
		// digitalSignatureService.multipleSignature();
		// digitalSignatureService.cratePdf();
		return "agendaReportDisplay";

	}

	// @PostMapping(value = "/digitalSingVerify")
	// public String digitalSingVerify(
	// @ModelAttribute("DigitalSignatureEntity") DigitalSignatureEntity
	// digitalSignatureEntity,
	// HttpServletResponse response, Model model, HttpSession session) throws
	// JRException, IOException,
	// GeneralSecurityException, DocumentException,
	// com.itextpdf.text.DocumentException {
	// ModelMapper mapper = new ModelMapper();
	// DigitalSignatureBean digitalSignatureBean = new DigitalSignatureBean();
	//// String str = agendareport.createDigitalSignature(model, session);
	//// model.addAttribute("str", str);
	//// model.addAttribute("DigitalSignatureEntity", digitalSignatureEntity);
	////
	// digitalSignatureBean = mapper.map(digitalSignatureEntity,
	// DigitalSignatureBean.class);
	// //digitalSignatureService.createProfile(digitalSignatureBean);
	// return "agendaReportDisplay";
	//
	// }

	private byte[] createAgendaReportList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void downloadReport(String fileName, byte[] fileData, HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.setHeader("Content-Type", "application/pdf");

		// try-with-resources statement
		try (InputStream is = new ByteArrayInputStream(fileData)) {
			try {
				IOUtils.copy(is, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
