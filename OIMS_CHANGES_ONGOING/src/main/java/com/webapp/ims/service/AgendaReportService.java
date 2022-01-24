package com.webapp.ims.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpSession;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webapp.ims.model.DSCPdfUploadEntity;
import com.webapp.ims.model.PrepareAgendaNotes;

import net.sf.jasperreports.engine.JRException;

@Service
public interface AgendaReportService {

	void createAgendaReport(String applicantId);

	String createDigitalSignature(Model model, HttpSession session)
			throws IOException, GeneralSecurityException, DocumentException;

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	DSCPdfUploadEntity sendAgendaReportListLarge(Model model, HttpSession session, String appId)
			throws JRException, IOException;

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	DSCPdfUploadEntity sendAgendaReportListMega(Model model, HttpSession session, String appId)
			throws JRException, IOException;

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	byte[] createAgendaReportListMega(Model model, HttpSession session, PrepareAgendaNotes prepareAgendaNotes)
			throws JRException, IOException;

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	byte[] createAgendaReportListLarge(Model model, HttpSession session, PrepareAgendaNotes prepareAgendaAppI)
			throws JRException, IOException;

}