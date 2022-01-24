/**
 * Author:: Gyan
* Created on:: 
* Created date::03/02/2021
 */

package com.webapp.ims.dis.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.ims.dis.model.AgendaReportDisBean;
import com.webapp.ims.dis.model.DisEligibleAmtCIS;
import com.webapp.ims.dis.model.DisEligibleAmtIIS;
import com.webapp.ims.dis.model.DisViewEvaluate;
import com.webapp.ims.dis.model.ReportOneDisIncentiveBean;
import com.webapp.ims.dis.repository.DisEligibleAmtCISRepository;
import com.webapp.ims.dis.repository.DisEligibleAmtIISRepository;
import com.webapp.ims.dis.repository.DisViewEvaluateRepository;
import com.webapp.ims.dis.service.DisViewEvaluateService;
import com.webapp.ims.model.AgendaNoteAppIdEntity;
import com.webapp.ims.model.DSCPdfUploadEntity;
import com.webapp.ims.model.NewProjectDetails;
import com.webapp.ims.repository.DSCPdfUploadRepo;
import com.webapp.ims.service.NewProjectDetailsService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@Service
public class DisViewEvaluateServiceImpl implements DisViewEvaluateService {

	@Autowired
	DisViewEvaluateRepository disViewEvaluateRepository;

	@Autowired
	DisEligibleAmtCISRepository disEligibleAmtCISRepository;

	@Autowired
	DisEligibleAmtIISRepository disEligibleAmtIISRepository;

	@Autowired
	DSCPdfUploadRepo dSCPdfUploadRepo;

	@Autowired
	NewProjectDetailsService newProjectService;

	@Override
	public void saveViewEvaluateDetails(DisViewEvaluate disViewEvaluate) {
		disViewEvaluateRepository.save(disViewEvaluate);

	}

// Pankaj 
	@Override
	public DisViewEvaluate getDetailsByEvaluateId(String evaluateId) {
		return disViewEvaluateRepository.getDetailsByEvaluateId(evaluateId);

	}

	// By pramod
	List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();

	@Override
	public byte[] searchEvaluateDisList() {
		byte[] output = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DSCPdfUploadEntity dbFile = new DSCPdfUploadEntity();
		try {

			System.out.println("inside searchEvaluateDisList()");

			AgendaReportDisBean agendaBean = new AgendaReportDisBean();
			String apcId = "UPSWP20000192402A1";
			String projId = apcId + "P1";
			DisViewEvaluate disViewEvaluate = disViewEvaluateRepository.findDisViewEvaluateToAppId(apcId);
			DisEligibleAmtCIS disEligibleAmtCIS = disEligibleAmtCISRepository.findDisEligibleAmtCISByAppId(apcId);
			DisEligibleAmtIIS disEligibleAmtIIS = disEligibleAmtIISRepository.findDisEligibleAmtIISByAPPId(apcId);
		

			BeanUtils.copyProperties(disViewEvaluate, agendaBean);
			BeanUtils.copyProperties(disEligibleAmtCIS, agendaBean);
			BeanUtils.copyProperties(disEligibleAmtIIS, agendaBean);

			if (disViewEvaluate != null) {
				createIncentiveReport1(disViewEvaluate);
			}

			if (!(jasperPrintList.isEmpty())) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				output = baos.toByteArray();

				dbFile = new DSCPdfUploadEntity();
				dbFile.setFileName("AgendaNoteDis" + apcId + ".pdf");
				dbFile.setSenderName("pramod");
				dbFile.setFile(output);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				dbFile.setCreatedDate(formatter.format(date));
				dbFile.setAgendaCreateStatus(false);
				dbFile = dSCPdfUploadRepo.save(dbFile);

				AgendaNoteAppIdEntity agendaNoteAppIdEntity = new AgendaNoteAppIdEntity();
				agendaNoteAppIdEntity.setAppID(apcId);
				agendaNoteAppIdEntity.setFileId(dbFile.getId());

				jasperPrintList = new ArrayList<JasperPrint>();
				return output;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

		return null;

	}

	JasperPrint createIncentiveReport1(DisViewEvaluate disViewEvaluate) {
		JasperPrint jasperPrint = null;
		List<ReportOneDisIncentiveBean> reportOneDisIncentiveBeanList = new ArrayList<>();
		ReportOneDisIncentiveBean reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();
		try {
			List<NewProjectDetails> newProjList = newProjectService.getNewProjListById(disViewEvaluate.getApcId()+"P1");
			reportOneDisIncentiveBean.setEligibility("Address of Registered Office of the Industrial Company:");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getAddOfRegisCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getAddOfRegisObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();
			/**************************/
			reportOneDisIncentiveBean.setEligibility("Address of Factory of the Industrial Unit");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getAddOfFactoryCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getAddOfFactoryObserv());
			reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();

			/**************************/
			reportOneDisIncentiveBean.setEligibility("Constitution of the Concern");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getConstiOfCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getConstiOfObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();

			/**************************/
			reportOneDisIncentiveBean.setEligibility("Date of Start of Commercial Production");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getDateOfStartCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getDateOfStartObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();

			/**************************/
			reportOneDisIncentiveBean.setEligibility("New Unit/Expansion/Diversification Project");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getNewUnitExpanCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getNewUnitExpanObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();

			/**************************/
			reportOneDisIncentiveBean.setEligibility("Product-Wise Installed Capacity for New Unit");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getProductWiseCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getProductWiseObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();

			/**************************/
			reportOneDisIncentiveBean.setEligibility("Whether product is being set up in phases?");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getWheProdSetupPhs());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getWheProdSetupPhsObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();
			/**************************/
			reportOneDisIncentiveBean.setEligibility("GSTN No. of the Industrial Unit/Firm/ Company");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getGstnNoOfCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getGstnNoOfObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();

			/**************************/
			reportOneDisIncentiveBean.setEligibility("PAN No. of the Industrial Unit/Firm/Company");
			reportOneDisIncentiveBean.setCompliance(disViewEvaluate.getPanNoOfCompl());
			reportOneDisIncentiveBean.setObservation(disViewEvaluate.getPanNoOfObserv());
			//reportOneDisIncentiveBean.setNewProjList(newProjList);
			reportOneDisIncentiveBeanList.add(reportOneDisIncentiveBean);
			reportOneDisIncentiveBean = new ReportOneDisIncentiveBean();
				
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgndaReportOneDis.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			System.out.println("benlist size : " + reportOneDisIncentiveBeanList.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(reportOneDisIncentiveBeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

			if (jasperPrint != null) {
				jasperPrintList.add(jasperPrint);
			}
			return jasperPrint;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

		return null;

	}

}
