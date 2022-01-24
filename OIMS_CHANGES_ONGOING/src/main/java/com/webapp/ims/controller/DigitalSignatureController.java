package com.webapp.ims.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.itextpdf.text.DocumentException;
import com.webapp.ims.model.DigitalSignatureBean;
import com.webapp.ims.model.DigitalSignatureEntity;

import net.sf.jasperreports.engine.JRException;

@Controller
@SessionAttributes("appId")
public class DigitalSignatureController {
//
//	@Autowired
//	DigitalSignatureService digitalSignatureService;

	@GetMapping(value = "/create")
	public void downloadBusinessEntityDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, Model model, HttpSession session)
			throws JRException, IOException, GeneralSecurityException, DocumentException {

		System.out.println("Inside Digital Signature controller");
		// byte[] output = agendareport.createAgendaReport("UPSWP200001502A2");
//		byte[] output =agendareport.createAgendaReportListMega(model,session);
//		downloadReport("report.pdf",output,response);
		// DigitalSignatureServiceImpl.checkDigitalSignature();

	}

	@PostMapping(value = "/digitalSingVerify")
	public String digitalSingVerify(
			@ModelAttribute("DigitalSignatureEntity") DigitalSignatureEntity digitalSignatureEntity,
			HttpServletResponse response, Model model, HttpSession session) throws JRException, IOException,
			GeneralSecurityException, DocumentException, com.itextpdf.text.DocumentException {
		ModelMapper mapper = new ModelMapper();
		DigitalSignatureBean digitalSignatureBean = new DigitalSignatureBean();
		String str = ""; // agendareport.createDigitalSignature(model, session);
		model.addAttribute("str", str);
		model.addAttribute("DigitalSignatureEntity", digitalSignatureEntity);
		digitalSignatureBean = mapper.map(digitalSignatureEntity, DigitalSignatureBean.class);
		// digitalSignatureService.createProfile(digitalSignatureBean);
		return "agendaReportDisplay";
}
	

}
