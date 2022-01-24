package com.webapp.ims.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.GenerateLocBean1;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.repository.PrepareAgendaNotesRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.PrepareAgendaNotesService;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@Service
@Transactional
public class PrepareAgendaNotesServiceImpli implements PrepareAgendaNotesService {

	@Autowired
	PrepareAgendaNotesRepository prepareAgendaNotesRepository;

	@Autowired
	BusinessEntityDetailsService businessService;

	List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();

	@Override
	public PrepareAgendaNotes savePrepareAgendaNotes(PrepareAgendaNotes prepareAgendaNotes) {

		return prepareAgendaNotesRepository.save(prepareAgendaNotes);

	}

	@Override
	// @Query("Select from PrepareAgendaNotes where appId=:appId")
	public PrepareAgendaNotes findPrepAgendaNotesByAppliId(String appId) {

		return prepareAgendaNotesRepository.findPrepAgendaNotesByAppliId(appId);
	}

	@Override
	public byte[] createMultipleReport(String applicantId) {

		byte[] output = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			if (generateLocReport(applicantId) != null) {
				jasperPrintList.add(generateLocReport(applicantId));
			}

			if (!(jasperPrintList.isEmpty())) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				output = baos.toByteArray();
			}
			return output;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return output;

	}

	@Override
	public JasperPrint generateLocReport(String applicantId) {
		JasperPrint jasperPrint = null;
		try {
			GenerateLocBean1 generateLocBean1 = new GenerateLocBean1();
			List<GenerateLocBean1> GenerateLocBean1List = new ArrayList<>();

			generateLocBean1.setApplicationid(applicantId);

			BusinessEntityDetails businessEntityDetails = businessService
					.getBusinessEntityByapplicantDetailId(applicantId);

			generateLocBean1.setBusinessEntityName(businessEntityDetails.getBusinessEntityName());

			generateLocBean1.setFacilities(
					"Reimbursement of deposited GST (after deduction of Input Tax Credit) As per para 3.2 of Rules-2017");
			generateLocBean1.setId("1");
			GenerateLocBean1List.add(generateLocBean1);

			generateLocBean1 = new GenerateLocBean1();
			generateLocBean1.setApplicationid(applicantId);
			generateLocBean1.setBusinessEntityName("Hardcode");
			generateLocBean1.setFacilities("Stamp Duty Exemption As per para 3.1 of Rules-2017");
			generateLocBean1.setId("2");
			GenerateLocBean1List.add(generateLocBean1);

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("generateLoc.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			System.out.println("benlist1 size : " + GenerateLocBean1List.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(GenerateLocBean1List);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

			return jasperPrint;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jasperPrint;

	}

}
