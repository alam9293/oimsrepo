package com.webapp.ims.service;

import com.webapp.ims.model.PrepareAgendaNotes;

import net.sf.jasperreports.engine.JasperPrint;

public interface PrepareAgendaNotesService {

	public PrepareAgendaNotes savePrepareAgendaNotes(PrepareAgendaNotes prepareAgendaNotes);

	public PrepareAgendaNotes findPrepAgendaNotesByAppliId(String appId);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	public JasperPrint generateLocReport(String applicantId);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	byte[] createMultipleReport(String applicantId);
}
