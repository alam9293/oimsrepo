package com.webapp.ims.service.impl;

import static com.webapp.ims.exception.GlobalConstants.LARGE;
import static com.webapp.ims.exception.GlobalConstants.MEDIUM;
import static com.webapp.ims.exception.GlobalConstants.MEGA;
import static com.webapp.ims.exception.GlobalConstants.MEGAPLUS;
import static com.webapp.ims.exception.GlobalConstants.SMALL;
import static com.webapp.ims.exception.GlobalConstants.SUPERMEGA;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.webapp.ims.model.AgendaNoteAppIdEntity;
import com.webapp.ims.model.AgendaReportBean;
import com.webapp.ims.model.AgendaReportIndexBean;
import com.webapp.ims.model.AgendaReportPart12Bean;
import com.webapp.ims.model.AgendaReportPart13Bean;
import com.webapp.ims.model.AgendaReportPart1Bean;
import com.webapp.ims.model.AgendaReportPart2Bean;
import com.webapp.ims.model.AgendaReportPart3Bean;
import com.webapp.ims.model.AgendaReportSignBean;
import com.webapp.ims.model.DSCPdfUploadEntity;
import com.webapp.ims.model.DeptProposedEmploymentDetails;
import com.webapp.ims.model.DeptSkilledUnSkilledEmployemnt;
import com.webapp.ims.model.ExistProjDetailsDocument;
import com.webapp.ims.model.InvMeansOfFinance;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.StoreSignatureEntity;
import com.webapp.ims.repository.AgendaNoteAppIdRepo;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.DSCPdfUploadRepo;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.EvaluateCapInvestRepository;
import com.webapp.ims.repository.ExistProjDocRepository;
import com.webapp.ims.repository.PhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.repository.StoreSignatureRepo;
import com.webapp.ims.service.AgendaReportService;
import com.webapp.ims.service.DigitalSignatureService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.ProjectService;

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
public class AgendaReportServiceImpl implements AgendaReportService {

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;
	
	@Autowired
	MeansOfFinanceService meansOfFinanceService;
	
	@Autowired
	PhaseWiseInvestmentDetailsService phaseWiseInvestmentDetailsService;
	

	@Autowired
	DeptInvestmentDetailsRepository deptInvestmentDetailsRepository;

	@Autowired
	EvaluateCapInvestRepository evaluateCapInvestRepository;

	@Autowired
	DeptBusinessEntityDetailsRepository deptBusinessEntityDetailsRepository;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	PhaseWiseInvestmentDetailsRepository deptPhaseWiseInvestmentDetailsRepository;
	@Autowired
	DSCPdfUploadRepo dSCPdfUploadRepo;

	@Autowired
	AgendaNoteAppIdRepo agendaNoteAppIdRepo;

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaNoteRepository;

	@Autowired
	StoreSignatureRepo storeSignatureRepo;

	@Autowired
	DigitalSignatureService digitalSignatureService;

	@Autowired
	DeptProposedEmploymentDetailsRepository deptProposedEmploymentDetailsRepository;
	
	
	
	@Autowired
	public ExistProjDocRepository existProjDocRepository;
	
	@Autowired
	public ProjectService projectService;

	// String applicantId = "UPSWP200001502A2";
	List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
	List<JasperPrint> jasperPrintListDownload = new ArrayList<JasperPrint>();
	int count = 0;
	private Date pwPropProductDate;
	List<Date> listOfPropProductDate = new ArrayList<>();
	// public byte[] createAgendaReportList(HashSet<String> applicationIdSet) {

	@SuppressWarnings("unchecked")
	@Override
	public byte[] createAgendaReportListMega(Model model, HttpSession session, PrepareAgendaNotes prepareAgendaAppI)
			throws JRException, IOException {
		HashSet<String> set = new HashSet();

		List<PrepareAgendaNotes> prepareAgendaNotesList = new ArrayList<PrepareAgendaNotes>();
		String[] appIdArr = prepareAgendaAppI.getPrepareAgendaNote();
		for (String appId : appIdArr) {
			PrepareAgendaNotes prepareAgenda = new PrepareAgendaNotes();
			prepareAgenda.setAppliId(appId);
			prepareAgendaNotesList.add(prepareAgenda);
		}

		// prepareAgendaNotesList = (List<PrepareAgendaNotes>)
		// session.getAttribute("Mega");
		jasperPrintListDownload = new ArrayList<JasperPrint>();
		System.out.println("prepareAgendaNotesList.size()" + prepareAgendaNotesList.size());
		jasperPrintList = new ArrayList<JasperPrint>();
//		JasperPrint jasperPrintIndex = agendaReportIndex(prepareAgendaNotesList);
//		jasperPrintList.add(jasperPrintIndex);
//		jasperPrintListDownload.add(jasperPrintIndex);

		for (PrepareAgendaNotes prepareAgendaNotes : prepareAgendaNotesList) {
			System.out.println("prepareAgendaNotes.getAppliId()" + prepareAgendaNotes.getAppliId());
			set.add(prepareAgendaNotes.getAppliId());
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] output = null;

		Iterator<String> i = set.iterator();

		count = 0;
		while (i.hasNext()) {
			++count;
			String appId = i.next();
			createAgendaReport(appId);
			DSCPdfUploadEntity dbFile = new DSCPdfUploadEntity();
			if (!(jasperPrintList.isEmpty())) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				output = baos.toByteArray();

				dbFile = new DSCPdfUploadEntity();
				dbFile.setFileName("AgendaNote" + appId + ".pdf");
				dbFile.setSenderName("pramod");
				dbFile.setFile(output);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				// dbFile.(formatter.format(date));
				System.out.println("dbFile : " + dbFile);
				dbFile = dSCPdfUploadRepo.save(dbFile);

				AgendaNoteAppIdEntity agendaNoteAppIdEntity = new AgendaNoteAppIdEntity();
				agendaNoteAppIdEntity.setAppID(appId);
				agendaNoteAppIdEntity.setFileId(dbFile.getId());
				agendaNoteAppIdRepo.save(agendaNoteAppIdEntity);
				jasperPrintList = new ArrayList<JasperPrint>();
			}

		}

		if (!(jasperPrintListDownload.isEmpty())) {
			output = null;
			baos = new ByteArrayOutputStream();
			JRPdfExporter exporterMultiAppId = new JRPdfExporter();
			exporterMultiAppId.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintListDownload);
			exporterMultiAppId.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
			exporterMultiAppId.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporterMultiAppId.exportReport();
			output = baos.toByteArray();
			jasperPrintListDownload = new ArrayList<JasperPrint>();
		}
		return output;
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] createAgendaReportListLarge(Model model, HttpSession session, PrepareAgendaNotes prepareAgendaAppI)
			throws JRException, IOException {
		HashSet<String> set = new HashSet();

		List<PrepareAgendaNotes> prepareAgendaNotesList = new ArrayList<PrepareAgendaNotes>();
		String[] appIdArr = prepareAgendaAppI.getPrepareAgendaNote();
		for (String appId : appIdArr) {
			PrepareAgendaNotes prepareAgenda = new PrepareAgendaNotes();
			prepareAgenda.setAppliId(appId);
			prepareAgendaNotesList.add(prepareAgenda);
		}

		System.out.println("prepareAgendaNotesList.size()" + prepareAgendaNotesList.size());
		jasperPrintListDownload = new ArrayList<JasperPrint>();
		System.out.println("prepareAgendaNotesList.size()" + prepareAgendaNotesList.size());
		jasperPrintList = new ArrayList<JasperPrint>();
		JasperPrint jasperPrintIndex = agendaReportIndex(prepareAgendaNotesList);
		jasperPrintList.add(jasperPrintIndex);
		jasperPrintListDownload.add(jasperPrintIndex);

		for (PrepareAgendaNotes prepareAgendaNotes : prepareAgendaNotesList) {
			System.out.println("prepareAgendaNotes.getAppliId()" + prepareAgendaNotes.getAppliId());
			set.add(prepareAgendaNotes.getAppliId());
			// set.add("UPSWP200001502A2");
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] output = null;

		Iterator<String> i = set.iterator();

		count = 0;
		while (i.hasNext()) {
			++count;
			String appId = i.next();
			createAgendaReport(appId);
			DSCPdfUploadEntity dbFile = new DSCPdfUploadEntity();
			if (!(jasperPrintList.isEmpty())) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				output = baos.toByteArray();

				dbFile = new DSCPdfUploadEntity();
				dbFile.setFileName("AgendaNote" + appId + ".pdf");
				dbFile.setSenderName("pramod");
				dbFile.setFile(output);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				dbFile.setCreatedDate(formatter.format(date));
				dbFile.setAgendaCreateStatus(false);
				dbFile = dSCPdfUploadRepo.save(dbFile);

				AgendaNoteAppIdEntity agendaNoteAppIdEntity = new AgendaNoteAppIdEntity();
				agendaNoteAppIdEntity.setAppID(appId);
				agendaNoteAppIdEntity.setFileId(dbFile.getId());
				agendaNoteAppIdRepo.save(agendaNoteAppIdEntity);
				jasperPrintList = new ArrayList<JasperPrint>();
			}
		}

		if (!(jasperPrintListDownload.isEmpty())) {
			output = null;
			baos = new ByteArrayOutputStream();
			JRPdfExporter exporterMultiAppId = new JRPdfExporter();
			exporterMultiAppId.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintListDownload);
			exporterMultiAppId.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
			exporterMultiAppId.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporterMultiAppId.exportReport();
			output = baos.toByteArray();
			jasperPrintListDownload = new ArrayList<JasperPrint>();
		}
		return output;

	}

	@Override
	public DSCPdfUploadEntity sendAgendaReportListLarge(Model model, HttpSession session, String appId)
			throws JRException, IOException {

		List<PrepareAgendaNotes> prepareAgendaNotesList = new ArrayList<PrepareAgendaNotes>();
		prepareAgendaNotesList = (List<PrepareAgendaNotes>) session.getAttribute("Large");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] output = null;

		jasperPrintList = new ArrayList<JasperPrint>();
		try {
			List<PrepareAgendaNotes> prepareAgenda = new ArrayList<>();
			for (PrepareAgendaNotes prepareAgendaNotes : prepareAgendaNotesList) {
				if (prepareAgendaNotes.getAppliId().equals(appId)) {
					prepareAgenda.add(prepareAgendaNotes);
				}
			}

			jasperPrintList.add(agendaReportIndex(prepareAgenda));

			createAgendaReport(appId);
			DSCPdfUploadEntity dbFile = new DSCPdfUploadEntity();
			if (!(jasperPrintList.isEmpty())) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				output = baos.toByteArray();

				dbFile = new DSCPdfUploadEntity();
				dbFile.setFileName("AgendaNote" + appId + ".pdf");
				dbFile.setSenderName("pramod");
				dbFile.setFile(output);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				dbFile.setCreatedDate(formatter.format(date));
				dbFile.setAgendaCreateStatus(false);
				dbFile = dSCPdfUploadRepo.save(dbFile);

				AgendaNoteAppIdEntity agendaNoteAppIdEntity = new AgendaNoteAppIdEntity();
				agendaNoteAppIdEntity.setAppID(appId);
				agendaNoteAppIdEntity.setFileId(dbFile.getId());
				agendaNoteAppIdRepo.save(agendaNoteAppIdEntity);
				jasperPrintList = new ArrayList<JasperPrint>();
			}
			return dbFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DSCPdfUploadEntity sendAgendaReportListMega(Model model, HttpSession session, String appId)
			throws JRException, IOException {

		List<PrepareAgendaNotes> prepareAgendaNotesList = new ArrayList<PrepareAgendaNotes>();
		prepareAgendaNotesList = (List<PrepareAgendaNotes>) session.getAttribute("Mega");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] output = null;

		jasperPrintList = new ArrayList<JasperPrint>();
		try {
			List<PrepareAgendaNotes> prepareAgenda = new ArrayList<>();
			for (PrepareAgendaNotes prepareAgendaNotes : prepareAgendaNotesList) {
				if (prepareAgendaNotes.getAppliId().equals(appId)) {
					prepareAgenda.add(prepareAgendaNotes);
				}
			}

			jasperPrintList.add(agendaReportIndex(prepareAgenda));

			createAgendaReport(appId);
			DSCPdfUploadEntity dbFile = new DSCPdfUploadEntity();

			if (!(jasperPrintList.isEmpty())) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				output = baos.toByteArray();

				dbFile = new DSCPdfUploadEntity();
				dbFile.setFileName("AgendaNote" + appId + ".pdf");
				dbFile.setSenderName("pramod");
				dbFile.setFile(output);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				dbFile.setCreatedDate(formatter.format(date));
				dbFile.setAgendaCreateStatus(false);
				dbFile = dSCPdfUploadRepo.save(dbFile);

				AgendaNoteAppIdEntity agendaNoteAppIdEntity = new AgendaNoteAppIdEntity();
				agendaNoteAppIdEntity.setAppID(appId);
				agendaNoteAppIdEntity.setFileId(dbFile.getId());
				agendaNoteAppIdRepo.save(agendaNoteAppIdEntity);
				jasperPrintList = new ArrayList<JasperPrint>();
			}
			return dbFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createAgendaReport(String applicantId) {

		try 
		{
			Object[] row;
			// digitalSignatureService.checkDigitalSingleItem(applicantId);
			List<Object> obj = applicantDetailsRepository.findAgendaReportData(applicantId);
			AgendaReportBean agendaReportBean = new AgendaReportBean();
			AgendaReportPart2Bean agendaReportPart2Bean = new AgendaReportPart2Bean();
			List<AgendaReportPart2Bean> agendaReportPart2BeanList = new ArrayList<>();

			JasperPrint jasperPrint2 = null;
//			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("obj" + obj.size());

			DeptProposedEmploymentDetails deptProposedEmploymentDetails = deptProposedEmploymentDetailsRepository
					.findByappId(applicantId);
			long totalEmp = 0;
			if (deptProposedEmploymentDetails.getSkilledUnSkilledEmployemnt() != null) {
				List<DeptSkilledUnSkilledEmployemnt> deptSkilledUnSkilledEmployemntList = deptProposedEmploymentDetails
						.getSkilledUnSkilledEmployemnt();

				for (DeptSkilledUnSkilledEmployemnt deptSkilledUnSkilledEmployemnt : deptSkilledUnSkilledEmployemntList) {
					totalEmp += deptSkilledUnSkilledEmployemnt.getNumberOfFemaleEmployees()
							+ deptSkilledUnSkilledEmployemnt.getNumberofMaleEmployees();
				}
			}

			for (Object object : obj) {
				row = (Object[]) object;
				System.out.println(row.length);
				HashMap<String, Object> map = new HashMap<>();
				System.out.println("Element " + Arrays.toString(row));

				if (row[0] != null)
					map.put("newProject", row[0].toString());
				if (row[1] != null)
					map.put("diversification", row[1]);
				if (row[2] != null)
					map.put("resionName", row[2].toString());
				if (row[3] != null)
					map.put("districtName", row[3].toString());
				if (row[4] != null)
					map.put("natureOfProject", row[4].toString());
				if (row[5] != null)
					map.put("epdExisProducts", row[5].toString());
				if (row[6] != null)
					map.put("epdExisInstallCapacity", row[6].toString());
				if (row[7] != null)
					map.put("epdPropProducts", row[7].toString());
				if (row[8] != null)
					map.put("epdExisGrossBlock", row[8].toString());
				if (row[9] != null)
					map.put("epdPropoGrossBlock", row[9].toString());
				if (row[10] != null)
					map.put("invTotalProjCost", row[10].toString());
				if (row[11] != null)
					map.put("propCommProdDate", row[11].toString());
				if (row[12] != null)
					map.put("invPlantAndMachCost", row[12].toString());
				if (row[13] != null)
					map.put("invOtherCost", row[13].toString());
				if (row[14] != null)
					map.put("landcostFci", row[14].toString());
				if (row[15] != null)
					map.put("buildingFci", row[15].toString());
				if (row[16] != null)
					map.put("plantAndMachFci", row[16].toString());
				if (row[17] != null)
					map.put("fixedAssetFci", row[17].toString());
				if (row[18] != null)
					map.put("invLandCost", row[18].toString());
				if (row[19] != null)
					map.put("invBuildingCost", row[19].toString());
				if (row[20] != null)
					map.put("landcostIIEPP", row[20].toString());
				if (row[21] != null)
					map.put("buildingIIEPP", row[21].toString());
				if (row[22] != null)
					map.put("fixedAssetIIEPP", row[22].toString());
				if (row[23] != null)
					map.put("plantAndMachIIEPP", row[23].toString());
				if (row[24] != null)
					map.put("landcostRemarks", row[24].toString());
				if (row[25] != null)
					map.put("buildingRemarks", row[25]);
				if (row[26] != null)
					map.put("plantAndMachRemarks", row[26].toString());
				if (row[27] != null)
					map.put("fixedAssetRemarks", row[27].toString());
				if (row[28] != null)
					map.put("invGovtEquity", row[28].toString());
				if (row[29] != null)
					map.put("invIemNumber", row[29].toString());
				if (row[30] != null)
					map.put("pwApply", row[30].toString());
				if (row[31] != null)
					map.put("capInvAmt", row[31].toString());

				if (row[32] != null)
					map.put("authorisedSignatoryName", row[32].toString());
				if (row[33] != null)
					map.put("gstin", row[33].toString());
				if (row[34] != null)
					map.put("companyPanNo", row[34].toString());
				if (row[35] != null)
					map.put("createDate", row[35].toString());

				System.out.println("ISFReimSCST            " + row[36]);
				if (row[36] != null)
					map.put("ISFReimSCST", row[36]);
				if (row[37] != null)
					map.put("ISFReimFW", row[37].toString());
				if (row[38] != null)
					map.put("ISFReimBPLW", row[38].toString());
				if (row[39] != null)
					map.put("ISFTtlSGSTReim", row[39].toString());
				if (row[40] != null)
					map.put("ISFStampDutyEX", row[40]);
				if (row[41] != null)
					map.put("ISFEpfReimUW", row[41].toString());
				if (row[42] != null)
					map.put("sgstRemark", row[42].toString());
				if (row[43] != null)
					map.put("scstRemark", row[43].toString());
				if (row[44] != null)
					map.put("stampDutyExemptRemark", row[44].toString());
				if (row[45] != null)
					map.put("stampDutyReimRemark", row[45].toString());
				if (row[46] != null)
					map.put("isfSgstComment", row[46].toString());
				if (row[47] != null)
					map.put("isfScstComment", row[47].toString());
				if (row[48] != null)
					map.put("isffwComment", row[48].toString());
				if (row[49] != null)
					map.put("isfBplComment", row[49].toString());
				if (row[50] != null)
					map.put("isfElecdutyComment", row[50].toString());
				if (row[51] != null)
					map.put("businessEntityName", row[51].toString());
				if (row[52] != null)
					map.put("businessAddress", row[52].toString());
				if (row[53] != null)
					map.put("invFci", row[53].toString());
				if (row[54] != null)
					map.put("invIndType", row[54].toString());
				if (row[55] != null)
					map.put("catIndusUndtObserv", row[55].toString());
				if (row[56] != null)
					map.put("iemRegObserv", row[56].toString());
				if (row[57] != null)
					map.put("invCommenceDate", row[57].toString());
				if (row[58] != null)
					map.put("optcutofdateobserv", row[58].toString());
				if (row[59] != null)
					map.put("propsPlntMcnryCostObserv", row[59].toString());
				if (row[60] != null)
					map.put("dateofComProdObserv", row[60].toString());
				if (row[61] != null)
					map.put("appformatObserv", row[61].toString());
				if (row[62] != null)
					map.put("authSignatoryObserv", row[62].toString());
				if (row[63] != null)
					map.put("detailProjReportObserv", row[63].toString());
				if (row[64] != null)
					map.put("listAssetsObserv", row[64].toString());
				if (row[65] != null)
					map.put("listAssets", row[65].toString());
				if (row[66] != null)
					map.put("anexurUndertkObserv", row[66].toString());
				if (row[67] != null)
					map.put("eligblInvPerdMegaObserv", row[67].toString());
				if (row[68] != null)
					map.put("projPhasesObserv", row[68].toString());
				if (row[69] != null)
					map.put("totalDetailObserv", row[69].toString());

				/*
				 * if (row[70] != null) map.put("numberofMaleEmployees", row[70].toString()); if
				 * (row[71] != null) map.put("numberOfFemaleEmployees", row[71].toString());
				 */
				if (row[70] != null)
					map.put("supprtdocObserv", row[70].toString());
				if (row[71] != null)
					map.put("docAuthorized", row[71]);
				if (row[72] != null)
					map.put("ISF_Claim_Reim", row[72]);
				if (row[73] != null)
					map.put("ISFAmtStampDutyReim", row[73].toString());
				if (row[74] != null)
					map.put("ISF_Cis", row[74].toString());
				if (row[75] != null)
					map.put("capIntSubRemark", row[75].toString());
				if (row[76] != null)
					map.put("ISF_Infra_Int_Subsidy", row[76].toString());
				if (row[77] != null)
					map.put("infraIntSubRemark", row[77].toString());
				if (row[78] != null)
					map.put("ISF_Loan_Subsidy", row[78]);
				if (row[79] != null)
					map.put("inputTaxRemark", row[79].toString());

				if (row[80] != null)
					map.put("ISF_Tax_Credit_Reim", row[80].toString());
				if (row[81] != null)
					map.put("ISF_Indus_Payroll_Asst", row[81].toString());
				if (row[82] != null)
					map.put("diffAbleWorkRemark", row[82].toString());
				if (row[83] != null)
					map.put("solarCaptivePower", row[83].toString());
				if (row[84] != null)
					map.put("solarCaptivePowerObserv", row[84].toString());
				if (row[85] != null)
					map.put("expansion", row[85].toString());
				if (row[86] != null)
					map.put("diversification", row[86].toString());

				if (row[87] != null) {
					String modifyDate = row[87].toString();

					String date = modifyDate.substring(0, 10);
					map.put("modify_Date", date);
				}
				
			    if (row[88] != null)
				   map.put("proposal", row[88].toString());
			
			    if (row[89] != null)
			    	map.put("pwPhaseNo", row[89].toString());
			    
			    if (row[90] != null)
				   map.put("pwProductName", row[90].toString());
				
				if (row[91] != null)
					map.put("pwCapacity", row[91].toString());
				
				if (row[92] != null)
					map.put("pwUnit", row[92].toString());
				
				if (row[93] != null)
					map.put("pwFci", row[93].toString());
				
				if (row[94] != null)
					map.put("pwPropProductDate", row[94].toString());
				
				if (row[95] != null)
					map.put("pwApcId", row[95].toString());
				
				map.put("itemNumber", count);
				map.put("applicationId", applicantId);

				agendaReportBean = mapper.convertValue(map, AgendaReportBean.class);
			}

			List<PhaseWiseInvestmentDetails> dept_PhaseWiseList = new ArrayList<>();
			dept_PhaseWiseList = deptPhaseWiseInvestmentDetailsRepository.findByPwApcId(agendaReportBean.applicationId);
					//.findbyApplicationId(agendaReportBean.applicationId);

			if (dept_PhaseWiseList.size() > 0) {
				for (PhaseWiseInvestmentDetails dept_PhaseWiseInvestmentDetails : dept_PhaseWiseList) {
					listOfPropProductDate.add(dept_PhaseWiseInvestmentDetails.getPwPropProductDate());
				}
			}

			String pwise = null;
			String categorytype = agendaReportBean.getInvIndType();
			String ctype = null;

			if ((categorytype.equalsIgnoreCase(SMALL)) || (categorytype.equalsIgnoreCase(MEDIUM))) {
				ctype = "Yes";

			} else {
				ctype = "No";
			}

			if (categorytype.equalsIgnoreCase(LARGE)) {
				ctype = "Yes";
			} else {
				ctype = "No";
			}

			if ((categorytype.equalsIgnoreCase(MEGA)) || (categorytype.equalsIgnoreCase(MEGAPLUS))) {
				ctype = "Yes";
			} else {
				ctype = "No";
			}

			if (categorytype.equalsIgnoreCase(SUPERMEGA)) {
				ctype = "Yes";
			} else {
				ctype = "No";
			}

			String phsWsAply = agendaReportBean.getPwApply();

			if (phsWsAply.equalsIgnoreCase("Yes")) {
				pwise = "Yes";
			} else {
				pwise = "No";
			}
			/*************************** */
			String regorlic = additionalInterest.getregiOrLicense(agendaReportBean.getApplicationId());
			agendaReportPart2Bean.setEligiblityCriteria("IEM Status-\n"
					+ "Whether the company have filed LOI/IEM registration with the Department of Industrial Policy and Promotion GoI");
			if (regorlic != null || agendaReportBean.getInvIemNumber() != null) {
				agendaReportPart2Bean.setCompliance(regorlic + ", " + agendaReportBean.getInvIemNumber());
			} else {
				agendaReportPart2Bean.setCompliance("");
			}
			agendaReportPart2Bean.setObservation(agendaReportBean.getIemRegObserv());
			agendaReportPart2BeanList.add(agendaReportPart2Bean);

			/* ******************************* */

			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria(
					"Proposed Total Investment in the project and Investment in Plant & Machinery");
			agendaReportPart2Bean.setCompliance(agendaReportBean.getInvPlantAndMachCost());
			agendaReportPart2Bean.setObservation(agendaReportBean.getPropsPlntMcnryCostObserv());

			agendaReportPart2BeanList.add(agendaReportPart2Bean);

			/* ******************************* */
			
			if (agendaReportBean.getInvCommenceDate() != null) 
			{
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("Opted Cut-off date investment");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = formatter.format(agendaReportBean.getInvCommenceDate());
				agendaReportPart2Bean.setCompliance(strDate);
				agendaReportPart2Bean.setObservation(agendaReportBean.getOptcutofdateobserv());
				agendaReportPart2BeanList.add(agendaReportPart2Bean);

			} 
			/* ******************************* */
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			if (listOfPropProductDate.size() > 0) {
				pwPropProductDate = Collections.max(listOfPropProductDate);
			}
			if (pwPropProductDate != null) {

				String strDate = formatter.format(pwPropProductDate);
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("Proposed Date of Commencement of Commercial Production");
				agendaReportPart2Bean.setCompliance(strDate);
				agendaReportPart2Bean.setObservation(agendaReportBean.getDateofComProdObserv());
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}
			
			/* ******************************* */
			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria("Investment Period-\r\n"
					+ "In case of mega & mega plus industrial undertakings, the period commencing from the cut-off date falling in the effective period of these Rules, and upto 5 years, or the Proposed Date of Commencement of Commercial Production, whichever falls earlier. Such cases will also be covered in which the cut-off date is within the period immediately preceding 3 years from the effective date, subject to the condition that atleast 40% of eligible capital investment shall have to be undertaken after the effective date. In any case, the maximum eligible investment period shall not be more than 5 years");
			agendaReportPart2Bean.setCompliance(ctype);
			agendaReportPart2Bean.setObservation(agendaReportBean.getEligblInvPerdMegaObserv());

			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			

			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria("Project in Phases –\r\n"
					+ "Whether the project is planned in phases (if yes phase wise details to be added).");
			agendaReportPart2Bean.setCompliance(pwise);
			agendaReportPart2Bean.setObservation(agendaReportBean.getProjPhasesObserv());

			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			
			/***********************************************/

			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria("\r\n" + "Total Employment");

			agendaReportPart2Bean.setCompliance(Long.toString(totalEmp));
			agendaReportPart2Bean.setObservation(agendaReportBean.getTotalDetailObserv());

			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			
			/***********************************************/

			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria(
					"Detailed Project Report prepared by External Consultant/Chartered Accountants ");
			
			boolean Docexist =false;
			List<ExistProjDetailsDocument> projDocList = existProjDocRepository.getProjDocListByProjectId(applicantId.substring(0, applicantId.length()-2)+"P1");
			ProjectDetails ProjectDetail = projectService.getProjDetById(applicantId.substring(0, applicantId.length()-2)+"P1");
			for (ExistProjDetailsDocument epdDoc : projDocList) 
			{
				if(ProjectDetail.getEnclDetProRepFileName()==null)
				{
					Docexist =false;
				}
				else if (ProjectDetail.getEnclDetProRepFileName().equalsIgnoreCase(epdDoc.getFileName())) 
				{	
					agendaReportPart2Bean.setCompliance("Yes");
					Docexist =true;
					break;
				}
				
			}
			if(!Docexist)
			{
				agendaReportPart2Bean.setCompliance("No");
			}
			agendaReportPart2Bean.setObservation(agendaReportBean.getDetailProjReportObserv());
			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			
			/***********************************************/
			if(agendaReportBean.getListAssets()!=null)
			{
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("List of Assets\r\n"
						+ "Whether List of Assets (in Expansion/Diversification Cases only) certified by C.A. Submitted.");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getListAssets());
				agendaReportPart2Bean.setObservation(agendaReportBean.getListAssetsObserv());
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}
			
			/***********************************************/

			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria("Undertaking-\r\n"
					+ "Whether notarized undertaking as per format placed at Annexure I-A (as per format of IIEPP Rules- 2017) on Stamp Paper of Rs. 10/-submitted.");
			agendaReportPart2Bean.setCompliance("Yes");
			agendaReportPart2Bean.setObservation(agendaReportBean.getAnexurUndertkObserv());

			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			
			/***********************************************/
            
			if(agendaReportBean.getAuthorisedSignatoryName()!=null)
			{
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean
						.setEligiblityCriteria("Name of Authorized Signatory. (Board Resolution copy provided.)");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getAuthorisedSignatoryName());
				agendaReportPart2Bean.setObservation(agendaReportBean.getAuthSignatoryObserv());
	
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}
			
			
			/***********************************************/

			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria(
					"Application Format- (Whether application is as per prescribed format & signed by Authorized Signatory with Name, Designation and Official Seal.)");
			agendaReportPart2Bean.setCompliance("Yes");
			agendaReportPart2Bean.setObservation(agendaReportBean.getAppformatObserv());

			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			
			/***********************************************/

			if(agendaReportBean.getDocAuthorized()!=null)
			{	
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria(
						"Whether all supporting document of application authenticated by a Director-Partner-officer duly authorized by the competent authority of the applicant on its behalf.");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getDocAuthorized());
				agendaReportPart2Bean.setObservation(agendaReportBean.getSupprtdocObserv());
	
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
				
			}
			
			/***********************************************/
			
			SimpleDateFormat formatterModify_Date = new SimpleDateFormat("dd/MM/yyyy");
			String strDateModify_Date = formatterModify_Date.format(agendaReportBean.getInvCommenceDate());
			agendaReportPart2Bean = new AgendaReportPart2Bean();
			agendaReportPart2Bean.setEligiblityCriteria("Date Of Application");
			agendaReportPart2Bean.setCompliance("Online submitted on " + strDateModify_Date);

			agendaReportPart2Bean
					.setObservation("The company has provided the relevant document for processing of the proposel on "
							+ strDateModify_Date);

			agendaReportPart2BeanList.add(agendaReportPart2Bean);
			
			/***********************************************/
            
			if(agendaReportBean.getGstin()!=null)
			{	
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("GSTIN No.");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getGstin());
				agendaReportPart2Bean.setObservation(
						"The company has submitted GSTIN Registration copy duly singed by authorized signatory");
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}

            /***********************************************/
            
			if(agendaReportBean.getCompanyPanNo()!=null)
			{
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("PAN No.");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getCompanyPanNo());
				agendaReportPart2Bean
						.setObservation("The company has submitted PAN copy duly singed by authorized signatory");
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}

			/***********************************************/
			
			if(agendaReportBean.getSolarCaptivePower()!=null)
			{	
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("Solar/Captive Power.");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getSolarCaptivePower());
				agendaReportPart2Bean.setObservation(agendaReportBean.getSolarCaptivePowerObserv());
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}
            
            /***********************************************/
			
			if(agendaReportBean.getInvIndType()!=null)
			{	
				agendaReportPart2Bean = new AgendaReportPart2Bean();
				agendaReportPart2Bean.setEligiblityCriteria("Category of Industrial Undertaking.");
				agendaReportPart2Bean.setCompliance(agendaReportBean.getInvIndType());
				agendaReportPart2Bean.setObservation(agendaReportBean.getCatIndusUndtObserv());
				agendaReportPart2BeanList.add(agendaReportPart2Bean);
			}

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReport22.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			System.out.println("benlist2 size : " + agendaReportPart2BeanList.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportPart2BeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint2 = JasperFillManager.fillReport(jasperReport, parameters, datasource);

			if (agendaReportBean != null) {
				JasperPrint JasperPrintReport1 = agendaReport1(agendaReportBean);
				jasperPrintList.add(JasperPrintReport1);
				jasperPrintListDownload.add(JasperPrintReport1);
				
				
				JasperPrint JasperPrintReport12 = agendaReport12(agendaReportBean);
				jasperPrintList.add(JasperPrintReport12);
				jasperPrintListDownload.add(JasperPrintReport12);
				
				JasperPrint JasperPrintReport13 = agendaReport13(agendaReportBean);
				jasperPrintList.add(JasperPrintReport13);
				jasperPrintListDownload.add(JasperPrintReport13);
			}

			if (jasperPrint2 != null) {

				jasperPrintList.add(jasperPrint2);
				jasperPrintListDownload.add(jasperPrint2);
			}
			if (agendaReportBean != null) {
				JasperPrint JasperPrintReport3 = agendaReport3(agendaReportBean.getApplicationId());
				jasperPrintList.add(JasperPrintReport3);
				jasperPrintListDownload.add(JasperPrintReport3);
			}
			if (agendaReportBean != null) {
				JasperPrint JasperPrintReport3 = agendaReportComment(agendaReportBean.getApplicationId());
				jasperPrintList.add(JasperPrintReport3);
				jasperPrintListDownload.add(JasperPrintReport3);
			}

		} catch (Exception ex) {
			
			ex.printStackTrace();
			

		} finally {

		}
		// return bytes;

	}

	private JasperPrint agendaReport12(AgendaReportBean agendaReportBean) 
	{

		JasperPrint jasperPrint = null;
		List<AgendaReportPart13Bean> agendaReportPart13BeanList = new ArrayList<>();

		List<PhaseWiseInvestmentDetails> dept_PhaseWiseInvestmentDetailsList = new ArrayList<>();
		try {
			dept_PhaseWiseInvestmentDetailsList = deptPhaseWiseInvestmentDetailsRepository.findByPwApcId(agendaReportBean.applicationId);

			if (dept_PhaseWiseInvestmentDetailsList.size() > 0)
			{
				List<InvMeansOfFinance> mofobjects = meansOfFinanceService.getMeansOfFinanceApcById(agendaReportBean.applicationId);
		    	for(InvMeansOfFinance obj:mofobjects)
		    	{
		    		System.out.println("obj.getMofParameter() :"+obj.getMofParameter());
		    		AgendaReportPart13Bean agendaReportPart13Bean = new AgendaReportPart13Bean();
		    		agendaReportPart13Bean.setParticulars(obj.getMofParameter());
		    		agendaReportPart13Bean.setMofAmount(Double.valueOf(obj.getMofAmount()));
		    		agendaReportPart13Bean.setPhaseNo(""+obj.getPwPhaseNoMOM());
		            agendaReportPart13BeanList.add(agendaReportPart13Bean);
		    	}
			}
	    	
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReport13.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportPart13BeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

			return jasperPrint;

		} catch (Exception ex) {
			System.out.println("/----------------");
			ex.printStackTrace();
			System.out.println("/----------------");
		} finally {

		}
		return null;

	}

	private JasperPrint agendaReport13(AgendaReportBean agendaReportBean) 
	{

		JasperPrint jasperPrint = null;
		List<AgendaReportPart12Bean> agendaReportPart12BeanList = new ArrayList<>();

		List<PhaseWiseInvestmentDetails> dept_PhaseWiseInvestmentDetailsList = new ArrayList<>();
		try {
			dept_PhaseWiseInvestmentDetailsList = deptPhaseWiseInvestmentDetailsRepository.findByPwApcId(agendaReportBean.applicationId);

			if (dept_PhaseWiseInvestmentDetailsList.size() > 0)
			{
				List<PhaseWiseInvestmentDetails> mofobjects = phaseWiseInvestmentDetailsService.findByPwApcId(agendaReportBean.applicationId);
		    	for(PhaseWiseInvestmentDetails obj:mofobjects)
		    	{
		    		AgendaReportPart12Bean agendaReportPart12Bean = new AgendaReportPart12Bean();
		    		agendaReportPart12Bean.setPhaseNo(""+obj.getPwPhaseNo());
		    		agendaReportPart12Bean.setInvLandCost(Double.valueOf(obj.getInvLandCost()));
		    		agendaReportPart12Bean.setInvBuildingCost(Double.valueOf(obj.getInvBuildingCost()));
		    		agendaReportPart12Bean.setInvPlantAndMachCost(Double.valueOf(obj.getInvPlantAndMachCost()));
		    		agendaReportPart12Bean.setInvOtherCost(Double.valueOf(obj.getInvOtherCost()));
		    		agendaReportPart12Bean.setInvFci(Double.valueOf(obj.getInvFci()));
		            agendaReportPart12BeanList.add(agendaReportPart12Bean);
		    	}
			}
	    	
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReport12.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportPart12BeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

			return jasperPrint;

		} catch (Exception ex) {
			System.out.println("/----------------");
			ex.printStackTrace();
			System.out.println("/----------------");
		} finally {

		}
		return null;

	}

	JasperPrint agendaReport1(AgendaReportBean agendaReportBean) throws JRException {

		JasperPrint jasperPrint = null;
		AgendaReportPart1Bean agendaReportPart1Bean = new AgendaReportPart1Bean();
		List<AgendaReportPart1Bean> agendaReportPart1BeanList = new ArrayList<>();

//		List<Dept_InvestmentDetails> dept_InvestmentDetailsList = new ArrayList<>();
//		List<EvaluateCapitalInvestment> evaluateCapitalInvestmentList = new ArrayList<>();

		List<PhaseWiseInvestmentDetails> dept_PhaseWiseInvestmentDetailsList = new ArrayList<>();
		try {
			dept_PhaseWiseInvestmentDetailsList = deptPhaseWiseInvestmentDetailsRepository.findByPwApcId(agendaReportBean.applicationId);
					//.findbyApplicationId(agendaReportBean.applicationId);

			if (dept_PhaseWiseInvestmentDetailsList.size() > 0) {
				for (PhaseWiseInvestmentDetails dept_PhaseWiseInvestmentDetails : dept_PhaseWiseInvestmentDetailsList) {

					agendaReportPart1Bean = new AgendaReportPart1Bean();

					agendaReportPart1Bean.setPwPhaseNo(""+dept_PhaseWiseInvestmentDetails.getPwPhaseNo());
					agendaReportPart1Bean.setPwProductName(dept_PhaseWiseInvestmentDetails.getPwProductName());
					agendaReportPart1Bean.setPwCapacity(""+dept_PhaseWiseInvestmentDetails.getPwCapacity());
					agendaReportPart1Bean.setPwUnit(dept_PhaseWiseInvestmentDetails.getPwUnit());
					agendaReportPart1Bean.setPwFci(Double.valueOf(dept_PhaseWiseInvestmentDetails.getInvFci()));
					
					if (dept_PhaseWiseInvestmentDetails.getPwPropProductDate() != null)
						agendaReportPart1Bean.setPwPropProductDate(
								dept_PhaseWiseInvestmentDetails.getPwPropProductDate().toString());
					listOfPropProductDate.add(dept_PhaseWiseInvestmentDetails.getPwPropProductDate());

					pwPropProductDate = Collections.max(listOfPropProductDate);

					agendaReportPart1Bean.setCompanyName(agendaReportBean.getBusinessEntityName());
					agendaReportPart1Bean.setAddress(agendaReportBean.getBusinessAddress());

					String type = "";
					if (agendaReportBean.getNewProject() != null) {
						type = agendaReportBean.getNewProject();
					}
					if (agendaReportBean.getDiversification() != null && type == "") {
						type += agendaReportBean.getDiversification();
						if (agendaReportBean.getExpansion() != null) {
							type += "/";
							type += agendaReportBean.getExpansion();
						}

					} else if (agendaReportBean.getExpansion() != null && type == "") {
						type += agendaReportBean.getExpansion();
					}

					agendaReportPart1Bean.setType(type);
					agendaReportPart1Bean.setProductNameAndCapacity(agendaReportBean.getPwProductName());
					agendaReportPart1Bean.setTotalProposedInvestment(agendaReportBean.getInvTotalProjCost());
					agendaReportPart1Bean.setInvFci(agendaReportBean.getInvFci());
					agendaReportPart1Bean.setInvIndType(agendaReportBean.getInvIndType());
					System.out.println("agendaReportBean.getInvIndType()" + agendaReportBean.getInvIndType());
//					agendaReportPart1Bean.setProposal(
//							"	   The company has applied for various incentives under IIEPP-2017 vide its applicationdated 19.8.2020 for the proposed greenfield projectfor manufacture of Flexible Films within an investment of Rs. 953.29 crores at YEIDA, Gautam Budh Nagar. The proposal is put up as hereunder for consideration of EC: -\r\n"
//									+ "1.	M/s Surya Global Flexifilms Pvt. Ltd. (SGFPL), a new company incorporated on 22.5.2020, is a group company of M/s Surya Food &Agro Ltd., which is a leading food processing company based at Noida makes biscuits, cookies, cakes, confectionery, chocolates, fruit-based beverages, baked snacks etc. It owns brand “Priyagold” and services mass & niche markets.\r\n"
//									+ "2.	The group/company is promoted by S/Sri B.P. Agarwal, Manoj Agarwal, Navin Agarwal, Shekhar Agarwal and Mrs. Bina Agarwal, who have the sufficient experience in the business & industry.\r\n"
//									+ "\r\n"
//									+ "3.	SGFPL, vide its letter dated 19.08.2020& 26.8.2020 has informed that they propose to set up a mega project to manufacture Flexible Films (BOPET, BOPP, Metalized & other films) with following installed capacities in two phases in YEIDA, Distt. Gautam Budh Nagar under its subsidiary company i.e. SGFPL with a total investment of Rs. 953.29 crores: -\r\n"
//									+ "");
                    
					agendaReportPart1Bean.setProposal(agendaReportBean.getProposal());  // Changed by Mohd Alam
					agendaReportPart1Bean.setItemNumber(agendaReportBean.getItemNumber());

					agendaReportPart1BeanList.add(agendaReportPart1Bean);

				}
			} else {
				agendaReportPart1Bean.setCompanyName(agendaReportBean.getBusinessEntityName());
				agendaReportPart1Bean.setAddress(agendaReportBean.getBusinessAddress());
				agendaReportPart1Bean.setType(agendaReportBean.getNewProject());
				agendaReportPart1Bean.setProductNameAndCapacity(agendaReportBean.getPwProductName());
				agendaReportPart1Bean.setTotalProposedInvestment(agendaReportBean.getInvTotalProjCost());
				agendaReportPart1Bean.setInvFci(agendaReportBean.getInvFci());
				agendaReportPart1Bean.setInvIndType(agendaReportBean.getInvIndType());
				System.out.println("agendaReportBean.getInvIndType()" + agendaReportBean.getInvIndType());
				String type = "";
				if (agendaReportBean.getNewProject() != null) {
					type = agendaReportBean.getNewProject();
				}
				if (agendaReportBean.getDiversification() != null && type == "") {
					type += agendaReportBean.getDiversification();
					if (agendaReportBean.getExpansion() != null) {
						type += "/";
						type += agendaReportBean.getExpansion();
					}

				} else if (agendaReportBean.getExpansion() != null && type == "") {
					type += agendaReportBean.getExpansion();
				}
				agendaReportPart1Bean.setType(type);
//				agendaReportPart1Bean.setProposal(
//						"	   The company has applied for various incentives under IIEPP-2017 vide its applicationdated 19.8.2020 for the proposed greenfield projectfor manufacture of Flexible Films within an investment of Rs. 953.29 crores at YEIDA, Gautam Budh Nagar. The proposal is put up as hereunder for consideration of EC: -\r\n"
//								+ "1.	M/s Surya Global Flexifilms Pvt. Ltd. (SGFPL), a new company incorporated on 22.5.2020, is a group company of M/s Surya Food &Agro Ltd., which is a leading food processing company based at Noida makes biscuits, cookies, cakes, confectionery, chocolates, fruit-based beverages, baked snacks etc. It owns brand “Priyagold” and services mass & niche markets.\r\n"
//								+ "2.	The group/company is promoted by S/Sri B.P. Agarwal, Manoj Agarwal, Navin Agarwal, Shekhar Agarwal and Mrs. Bina Agarwal, who have the sufficient experience in the business & industry.\r\n"
//								+ "\r\n"
//								+ "3.	SGFPL, vide its letter dated 19.08.2020& 26.8.2020 has informed that they propose to set up a mega project to manufacture Flexible Films (BOPET, BOPP, Metalized & other films) with following installed capacities in two phases in YEIDA, Distt. Gautam Budh Nagar under its subsidiary company i.e. SGFPL with a total investment of Rs. 953.29 crores: -\r\n"
//								+ "");
                
				agendaReportPart1Bean.setProposal(agendaReportBean.getProposal());  // Changed by Mohd Alam
				agendaReportPart1Bean.setItemNumber(agendaReportBean.getItemNumber());
				
				agendaReportPart1Bean.setPwPhaseNo(agendaReportBean.getPwPhaseNo());
				agendaReportPart1Bean.setPwProductName(agendaReportBean.getPwProductName());
				agendaReportPart1Bean.setPwCapacity(agendaReportBean.getPwCapacity());
				agendaReportPart1Bean.setPwUnit(agendaReportBean.getPwUnit());
				agendaReportPart1Bean.setPwFci(Double.valueOf(agendaReportBean.getPwFci()));
				agendaReportPart1Bean.setPwPropProductDate(agendaReportBean.getPwPropProductDate());

				agendaReportPart1BeanList.add(agendaReportPart1Bean);
			}

//		evaluateCapitalInvestmentList = evaluateCapInvestRepository
//				.findByEciApplcId(agendaReportBean.getApplicationId());

//		System.out.println("evaluateCapitalInvestmentList.size()" + evaluateCapitalInvestmentList.size());
//
//		try {
//			System.out.println("agendaReportBean.getApplicationId()" + agendaReportBean.getApplicationId());
//			dept_InvestmentDetailsList = deptInvestmentDetailsRepository
//					.searchInvDetailIdByapplId(agendaReportBean.getApplicationId());
//			System.out.println("dept_InvestmentDetailsList" + dept_InvestmentDetailsList.size());
//
//			for (Dept_InvestmentDetails dept_InvestmentDetails : dept_InvestmentDetailsList) {
//
//				agendaReportPart1Bean.setCompanyName(agendaReportBean.getBusinessEntityName());
//				agendaReportPart1Bean.setAddress(agendaReportBean.getBusinessAddress());
//				agendaReportPart1Bean.setType(agendaReportBean.getNewProject());
//				agendaReportPart1Bean.setProductNameAndCapacity(agendaReportBean.getPwProductName());
//				agendaReportPart1Bean.setTotalProposedInvestment(agendaReportBean.getInvTotalProjCost());
//				agendaReportPart1Bean.setInvFci(agendaReportBean.getInvFci());
//				agendaReportPart1Bean.setInvIndType(agendaReportBean.getInvIndType());
//				System.out.println("agendaReportBean.getInvIndType()" + agendaReportBean.getInvIndType());
//				agendaReportPart1Bean.setProposal(
//						"	   The company has applied for various incentives under IIEPP-2017 vide its applicationdated 19.8.2020 for the proposed greenfield projectfor manufacture of Flexible Films within an investment of Rs. 953.29 crores at YEIDA, Gautam Budh Nagar. The proposal is put up as hereunder for consideration of EC: -\r\n"
//								+ "1.	M/s Surya Global Flexifilms Pvt. Ltd. (SGFPL), a new company incorporated on 22.5.2020, is a group company of M/s Surya Food &Agro Ltd., which is a leading food processing company based at Noida makes biscuits, cookies, cakes, confectionery, chocolates, fruit-based beverages, baked snacks etc. It owns brand “Priyagold” and services mass & niche markets.\r\n"
//								+ "2.	The group/company is promoted by S/Sri B.P. Agarwal, Manoj Agarwal, Navin Agarwal, Shekhar Agarwal and Mrs. Bina Agarwal, who have the sufficient experience in the business & industry.\r\n"
//								+ "\r\n"
//								+ "3.	SGFPL, vide its letter dated 19.08.2020& 26.8.2020 has informed that they propose to set up a mega project to manufacture Flexible Films (BOPET, BOPP, Metalized & other films) with following installed capacities in two phases in YEIDA, Distt. Gautam Budh Nagar under its subsidiary company i.e. SGFPL with a total investment of Rs. 953.29 crores: -\r\n"
//								+ "");
//
//				agendaReportPart1Bean.setParticulars(dept_InvestmentDetails.getInvLandCost().toString());
//				agendaReportPart1Bean.setPhaseI(dept_InvestmentDetails.getInvLandCost().toString());
//				agendaReportPart1Bean.setPhaseII("0.0");
//				agendaReportPart1Bean.setTotal("0");
//				agendaReportPart1Bean.setItemNumber(agendaReportBean.getItemNumber());
//				System.out.println("agendaReportBean.getItemNumber()" + agendaReportBean.getItemNumber());

//				if (agendaReportPart1Bean != null) {
//					agendaReportPart1BeanList.add(agendaReportPart1Bean);
//
//				}
			// }

//					for (EvaluateCapitalInvestment evaluateCapitalInvestment : evaluateCapitalInvestmentList) {
//						System.out.println(
//								"agendaReportBean.getItemNumber() :EvaluateCapitalInvestment" + agendaReportBean.getItemNumber());
//						agendaReportPart1Bean.setCompanyName(agendaReportBean.getBusinessEntityName());
//						agendaReportPart1Bean.setAddress(agendaReportBean.getBusinessAddress());
//						agendaReportPart1Bean.setType(agendaReportBean.getNewProject());
//						agendaReportPart1Bean.setProductNameAndCapacity(agendaReportBean.getPwProductName());
//						agendaReportPart1Bean.setTotalProposedInvestment(agendaReportBean.getInvTotalProjCost());
//						agendaReportPart1Bean.setProposal(
//								"	   M/s Hi-Tech Pipes Ltd.is an existing Public Limited company having its corporate office at 505 Pearls Omax Tower, Netaji  Subhash Place, Pitampur, New Delhi 110034.  The company has been promoted by Sri Ajay Kumar Bansal and Sri Anish Bansal. Originally, the company was incorporated on 2nd January, 1985 in the name of Ram Lal Harbans Lal Ltd.  Thereafter the name of the company was changed to M/s Hi-Tech Pipes Ltd. on 14.10.1986 with the approval of Registrar of companies, New Delhi & Haryana.  The company is engaged in the manufacture of cold rolling products i.e. ERW Tubes & Pipes for Oil, Gas & Water and Structural Tubes for Construction industries, Precision Tubes for Automobile sector and Hollow Section for large infrastructure with an installed capacity of 1.70 lakhs MPPA at Plot No. B-10, Industrial Area, Sikandrabad, Bulandshahr, U.P.   The company now proposes to enhance its existing manufacturing capacity of its existing Sikandrabad Unit for manufacture of Wider Cold Rolling Coils & Sheets, Galvanized Plain & Corrugated Sheets and Color Coated Sheets with an installed capacity of 1.2 lakhs MTPA.  The cost of the expansion scheme has been estimated at Rs. 65.00 crores to be implemented in two phases (Phase I  Rs. 34.50 crores and Phase II Rs. 30.50 crores) \r\n"
//										+ "\r\n" + "The company is also having manufacturing of Steel pipes units at:-\r\n" + "\r\n"
//										+ "(i) Plot No. 16, Sikandrabad Industrial Area (U.P.)\r\n"
//										+ "(ii) Plot No. E-6, GIDC Bol-II, Sanand, Ahmedabad Gujarat 382170\r\n"
//										+ "(iii) 41-B, Gollapuram Hindupur Andhra Pradesh 515211\r\n"
//										+ "   (A unit of HTL Metal Private Limited a wholly owned Subsidiary of     the Company)\r\n"
//										+ " \r\n"
//										+ "As per DPR (prepared by the M/s A.N. Garg & Co., Chartered Accountant Delhi), the capital investment under plant and machinery has been estimated at Rs.56.59 crores and the total investment proposed in the expansion scheme is Rs.65.00 crores which includes costs under  building & civil works, plant and machinery and Electricals & other fixed assets. As such the company falls under large category in terms of the following provision of Rules for IIEPP-2017:-\r\n"
//										+ "");
			//
//						agendaReportPart1Bean.setParticulars(evaluateCapitalInvestment.getEciItem().toString());
//						agendaReportPart1Bean.setPhaseI("0.0");
//						agendaReportPart1Bean.setPhaseII("0.0");
//						agendaReportPart1Bean.setTotal("0");
//						agendaReportPart1Bean.setItemNumber(1);
//						if (agendaReportPart1Bean != null) {
//							agendaReportPart1BeanList.add(agendaReportPart1Bean);
			//
//						}
//					}

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReport11.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			System.out.println("benlist1 size : " + agendaReportPart1BeanList.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportPart1BeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

			return jasperPrint;

		} catch (Exception ex) {
			System.out.println("/----------------");
			ex.printStackTrace();
			System.out.println("/----------------");
		} finally {

		}
		return null;

	}

	JasperPrint agendaReport3(String applicantId) throws JRException {

		JasperPrint jasperPrint = null;
		AgendaReportPart3Bean agendaReportPart3Bean = new AgendaReportPart3Bean();
		List<AgendaReportPart3Bean> agendaReportPart3BeanList = new ArrayList<>();

		try {
			Object[] row;
			List<Object> obj = applicantDetailsRepository.findAgendaReportData(applicantId);
			AgendaReportBean agendaReportBean1 = new AgendaReportBean();

			ObjectMapper mapper = new ObjectMapper();
			System.out.println("obj" + obj.size());
			for (Object object : obj) {
				row = (Object[]) object;
				System.out.println(row.length);
				HashMap<String, Object> map = new HashMap<>();
				System.out.println("Element " + Arrays.toString(row));

				if (row[0] != null)
					map.put("newProject", row[0].toString());
				if (row[1] != null)
					map.put("diversification", row[1]);
				if (row[2] != null)
					map.put("resionName", row[2].toString());
				if (row[3] != null)
					map.put("districtName", row[3].toString());
				if (row[4] != null)
					map.put("natureOfProject", row[4].toString());
				if (row[5] != null)
					map.put("epdExisProducts", row[5].toString());
				if (row[6] != null)
					map.put("epdExisInstallCapacity", row[6].toString());
				if (row[7] != null)
					map.put("epdPropProducts", row[7].toString());
				if (row[8] != null)
					map.put("epdExisGrossBlock", row[8].toString());
				if (row[9] != null)
					map.put("epdPropoGrossBlock", row[9].toString());
				if (row[10] != null)
					map.put("invTotalProjCost", row[10].toString());
				if (row[11] != null)
					map.put("propCommProdDate", row[11].toString());
				if (row[12] != null)
					map.put("invPlantAndMachCost", row[12].toString());
				if (row[13] != null)
					map.put("invOtherCost", row[13].toString());
				if (row[14] != null)
					map.put("landcostFci", row[14].toString());
				if (row[15] != null)
					map.put("buildingFci", row[15].toString());
				if (row[16] != null)
					map.put("plantAndMachFci", row[16].toString());
				if (row[17] != null)
					map.put("fixedAssetFci", row[17].toString());
				if (row[18] != null)
					map.put("invLandCost", row[18].toString());
				if (row[19] != null)
					map.put("invBuildingCost", row[19].toString());
				if (row[20] != null)
					map.put("landcostIIEPP", row[20].toString());
				if (row[21] != null)
					map.put("buildingIIEPP", row[21].toString());
				if (row[22] != null)
					map.put("fixedAssetIIEPP", row[22].toString());
				if (row[23] != null)
					map.put("plantAndMachIIEPP", row[23].toString());
				if (row[24] != null)
					map.put("landcostRemarks", row[24].toString());
				if (row[25] != null)
					map.put("buildingRemarks", row[25]);
				if (row[26] != null)
					map.put("plantAndMachRemarks", row[26].toString());
				if (row[27] != null)
					map.put("fixedAssetRemarks", row[27].toString());
				if (row[28] != null)
					map.put("invGovtEquity", row[28].toString());
				if (row[29] != null)
					map.put("invIemNumber", row[29].toString());
				if (row[30] != null)
					map.put("pwApply", row[30].toString());
				if (row[31] != null)
					map.put("capInvAmt", row[31].toString());

				if (row[32] != null)
					map.put("authorisedSignatoryName", row[32].toString());
				if (row[33] != null)
					map.put("gstin", row[33].toString());
				if (row[34] != null)
					map.put("companyPanNo", row[34].toString());
				if (row[35] != null)
					map.put("createDate", row[35].toString());

				System.out.println("ISFReimSCST            " + row[36]);
				if (row[36] != null)
					map.put("ISFReimSCST", row[36]);
				if (row[37] != null)
					map.put("ISFReimFW", row[37].toString());
				if (row[38] != null)
					map.put("ISFReimBPLW", row[38].toString());
				if (row[39] != null)
					map.put("ISFTtlSGSTReim", row[39].toString());
				if (row[40] != null)
					map.put("ISFStampDutyEX", row[40]);
				if (row[41] != null)
					map.put("ISFEpfReimUW", row[41].toString());
				if (row[42] != null)
					map.put("sgstRemark", row[42].toString());
				if (row[43] != null)
					map.put("scstRemark", row[43].toString());
				if (row[44] != null)
					map.put("stampDutyExemptRemark", row[44].toString());
				if (row[45] != null)
					map.put("stampDutyReimRemark", row[45].toString());
				if (row[46] != null)
					map.put("isfSgstComment", row[46].toString());
				if (row[47] != null)
					map.put("isfScstComment", row[47].toString());
				if (row[48] != null)
					map.put("isffwComment", row[48].toString());
				if (row[49] != null)
					map.put("isfBplComment", row[49].toString());
				if (row[50] != null)
					map.put("isfElecdutyComment", row[50].toString());
				if (row[51] != null)
					map.put("businessEntityName", row[51].toString());
				if (row[52] != null)
					map.put("businessAddress", row[52].toString());
				if (row[53] != null)
					map.put("invFci", row[53].toString());
				if (row[54] != null)
					map.put("invIndType", row[54].toString());
				if (row[55] != null)
					map.put("catIndusUndtObserv", row[55].toString());
				if (row[56] != null)
					map.put("iemRegObserv", row[56].toString());
				if (row[57] != null)
					map.put("invCommenceDate", row[57].toString());
				if (row[58] != null)
					map.put("optcutofdateobserv", row[58].toString());
				if (row[59] != null)
					map.put("propsPlntMcnryCostObserv", row[59].toString());
				if (row[60] != null)
					map.put("dateofComProdObserv", row[60].toString());
				if (row[61] != null)
					map.put("appformatObserv", row[61].toString());
				if (row[62] != null)
					map.put("authSignatoryObserv", row[62].toString());
				if (row[63] != null)
					map.put("detailProjReportObserv", row[63].toString());
				if (row[64] != null)
					map.put("listAssetsObserv", row[64].toString());
				if (row[65] != null)
					map.put("listAssets", row[65].toString());
				if (row[66] != null)
					map.put("anexurUndertkObserv", row[66].toString());
				if (row[67] != null)
					map.put("eligblInvPerdMegaObserv", row[67].toString());
				if (row[68] != null)
					map.put("projPhasesObserv", row[68].toString());
				if (row[69] != null)
					map.put("totalDetailObserv", row[69].toString());

				/*
				 * if (row[70] != null) map.put("numberofMaleEmployees", row[70].toString()); if
				 * (row[71] != null) map.put("numberOfFemaleEmployees", row[71].toString());
				 */
				if (row[70] != null)
					map.put("supprtdocObserv", row[70].toString());
				if (row[71] != null)
					map.put("docAuthorized", row[71]);
				if (row[72] != null)
					map.put("ISF_Claim_Reim", row[72].toString());
				if (row[73] != null)
					map.put("ISFAmtStampDutyReim", row[73].toString());
				if (row[74] != null)
					map.put("ISF_Cis", row[74].toString());
				if (row[75] != null)
					map.put("capIntSubRemark", row[75].toString());
				if (row[76] != null)
					map.put("ISF_Infra_Int_Subsidy", row[76].toString());
				if (row[77] != null)
					map.put("infraIntSubRemark", row[77].toString());
				if (row[78] != null)
					map.put("ISF_Loan_Subsidy", row[78]);
				if (row[79] != null)
					map.put("inputTaxRemark", row[79].toString());

				if (row[80] != null)
					map.put("ISF_Tax_Credit_Reim", row[80].toString());
				if (row[81] != null)
					map.put("ISF_Indus_Payroll_Asst", row[81].toString());
				if (row[82] != null)
					map.put("diffAbleWorkRemark", row[82].toString());
				if (row[83] != null)
					map.put("solarCaptivePower", row[83].toString());
				if (row[84] != null)
					map.put("solarCaptivePowerObserv", row[84].toString());
				if (row[85] != null)
					map.put("expansion", row[85].toString());
				if (row[86] != null)
					map.put("diversification", row[86].toString());
				if (row[87] != null)
					map.put("modify_Date", row[87].toString());

				map.put("itemNumber", count);
				map.put("applicationId", applicantId);

				agendaReportBean1 = mapper.convertValue(map, AgendaReportBean.class);
			}

			/* 1 **************************/
			

			if (agendaReportBean1.getISF_Claim_Reim() != null)
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought(
						"Amount of SGST claimed for reimbursement\r\n" + "Industrial Development/ Commercial Tax");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISF_Claim_Reim());} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"	As per Table 3 of Rules related to IIEPP-2017 (The Rules), there is a provision for reimbursement of 70% of deposited GST (Net SGST) for 10 years.");

				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getSgstRemark());
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfSgstComment());
				
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}
			
			/* 2 *****************************/

			if (agendaReportBean1.getISFReimSCST()!=null) 
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought(
						"Additional 10% GST where 25% minimum SC/ST workers employed subject to minimum of 400 total workers in industrial undertakings located in Paschimanchal and minimum of 200 total workers in B-P-M\r\n"
								+ "Industrial Development/ Commercial Tax");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISFReimSCST() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per Table 3 of The Rules, there is a provision for 75% Stamp Duty Exemption in Paschimanchal Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.");

				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getSgstRemark());
				
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfScstComment());

				agendaReportPart3BeanList.add(agendaReportPart3Bean);

			}
			

			/* 3 *****************************/
			
			if (agendaReportBean1.getISFStampDutyEX()!=null)
			{	
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought("Amount of Stamp Duty Exemption Stamp & Registration");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISFStampDutyEX());} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per Table 3 of The Rules, there is a provision for 75 % Stamp Duty Exemption in Paschimanchal Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.");
				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getStampDutyExemptRemark());
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsffwComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}

			/** 4 ****************************/
			
			if (agendaReportBean1.getISFReimSCST() !=null)
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought("Amount of Stamp Duty Reimbursement");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISFAmtStampDutyReim() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per Table 3 of The Rules, there is a provision for 75 % Stamp Duty Exemption in Paschimanchal Region. Further, as per G.O. No. 1515/77-6-18-5(M)/17, dated 1.5.2018, there is a provision for reimbursement equivalent to the paid Stamp Duty, for which company will have to apply before concerned GM, DIC.");
				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getStampDutyReimRemark());
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfBplComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}

			/** 5 ****************************/
			if (agendaReportBean1.getISF_Cis() !=null)
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean
						.setDetailsOfIncentivesSought("Capital Interest Subsidy\r\n" + "Industrial Development");
				
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISF_Cis() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(amt);
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per para 3.5.1 of The Rules, there is a provision for capital interest subsidy @ 5% p.a. or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for procurement of plant & machinery, subject to an annual ceiling of Rs. 50 lacs.");
				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getCapIntSubRemark());
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfcapisComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}
			
			
			/** 6 ****************************/
			if(agendaReportBean1.getISF_Infra_Int_Subsidy()!=null)
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean
						.setDetailsOfIncentivesSought("Infrastructure Interest Subsidy\r\n" + "Industrial Development");
				
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISF_Infra_Int_Subsidy() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per para 3.5.2 of the Rules, there is a provision for incentive of infrastructure interest subsidy @ 5% p.a. or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for development of infrastructural amenities (as defined in para 2.17) subject to an overall ceiling of Rs. 1 Crore.");
				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getInfraIntSubRemark());
                
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getisfinfComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}
			/** 7 ****************************/
			
			if (agendaReportBean1.getISF_Infra_Int_Subsidy()!=null) 
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought(
						"Interest Subsidy on loans for industrial research, quality improvement, etc.\r\n"
								+ "Industrial Development");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISF_Loan_Subsidy() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per para 3.5.3 of The Rules, there is a provision for Interest subsidy on loans for industrial research @ 5% or actual interest paid whichever is less annually for 5 years in the form of reimbursement of interest paid on outstanding loan taken for industrial research, quality improvement and development of products by incurring expenditure on procurement of plant, machinery & equipment for setting up testing labs, quality certification labs and tool rooms, subject to an overall ceiling of Rs. 1 Crore");
				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getInfraIntSubRemark());
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfloanComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}

			/** 8 ****************************/
			if (agendaReportBean1.getISF_Tax_Credit_Reim()!=null) 
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought(
						"Reimbursement of Disallowed Input Tax Credit on plant, building materials, and other capital goods.\r\n"
								+ "Industrial Development");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISF_Tax_Credit_Reim() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules(
						"As per para 3.5.7 of the Rules, it is provided - The industries which are disallowed for input tax credit under the GST regime, will be eligible for reimbursement of that amount of GST paid on purchase of plant and machinery, building material and other capital goods during construction and commissioning period and raw materials and other inputs in respect of which input tax credit has not been allowed. While calculating the overall eligible capital investment such amount will be added to the fixed capital investment.");
				agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getInputTaxRemark());
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfdisComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}
			
			
			/** 9 ****************************/
			if (agendaReportBean1.getISF_Tax_Credit_Reim()!=null)
			{
				agendaReportPart3Bean = new AgendaReportPart3Bean();
				agendaReportPart3Bean.setDetailsOfIncentivesSought(
						"Industrial units providing employment to differently abled workers will be provided payroll assistance of Rs. 500 per month for each such worker.");
				Double amt = 0.0;
				try{amt = Double.parseDouble(agendaReportBean1.getISF_Tax_Credit_Reim() + "");} catch(Exception e) {}
				agendaReportPart3Bean.setAmountOfReliefs(Double.valueOf(amt));
				agendaReportPart3Bean.setIncentiveAsPerRules("No such provision in The Rules.");
				if (agendaReportBean1.getInputTaxRemark() != null)
					agendaReportPart3Bean.setCommentsOfConcernedDepartment(agendaReportBean1.getInputTaxRemark());
				
				agendaReportPart3Bean.setjustificationComment(agendaReportBean1.getIsfdifferabilComment());
				agendaReportPart3BeanList.add(agendaReportPart3Bean);
			}
			/******************************/

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReport33.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			System.out.println(url);
			System.out.println("benlist3 size : " + agendaReportPart3BeanList.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportPart3BeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
			System.out.println("--------------------------------------------");
			return jasperPrint;

		} catch (Exception ex) {
			System.out.println("======================");
			ex.printStackTrace();

		} finally {

		}

		return jasperPrint;
	}

	JasperPrint agendaReportIndex(List<PrepareAgendaNotes> prepareAgendaNotesList) throws JRException {

		JasperPrint jasperPrint = null;
		AgendaReportIndexBean agendaReportIndexBean = new AgendaReportIndexBean();
		List<AgendaReportIndexBean> agendaReportIndexBeanList = new ArrayList<>();

		try {
			for (PrepareAgendaNotes prepareAgendaNotes : prepareAgendaNotesList) {
				agendaReportIndexBean = new AgendaReportIndexBean();
				// String appId="UPSWP200001502A2";
				Object[] row;
				AgendaReportBean agendaReportBean = new AgendaReportBean();
				List<Object> obj = deptBusinessEntityDetailsRepository
						.findByAppIdIndex(prepareAgendaNotes.getAppliId());
				// List<Object> obj =
				// deptBusinessEntityDetailsRepository.findByAppIdIndex(appId);

				ObjectMapper mapper = new ObjectMapper();
				System.out.println("obj" + obj.size());

				for (Object object : obj) {
					row = (Object[]) object;
					System.out.println(row.length);
					HashMap<String, Object> map = new HashMap<>();
					System.out.println("Element " + Arrays.toString(row));

					if (row[0] != null)
						map.put("pwPhaseNo", row[0].toString());
					if (row[1] != null)
						map.put("pwProductName", row[1].toString());
					if (row[2] != null)
						map.put("pwCapacity", row[2].toString());
					if (row[3] != null)
						map.put("pwUnit", row[3].toString());
					if (row[4] != null)
						map.put("pwFci", row[4].toString());
					if (row[5] != null) {
						map.put("pwPropProductDate", row[5].toString());
					}
					if (row[6] != null)
						map.put("authorisedSignatoryName", row[6].toString());
					if (row[7] != null)
						map.put("gstin", row[7].toString());
					if (row[8] != null)
						map.put("companyPanNo", row[8].toString());
					if (row[9] != null)
						map.put("businessEntityName", row[9].toString());
					if (row[10] != null)
						map.put("businessAddress", row[10].toString());

					map.put("itemNumber", count);
					map.put("applicationId", prepareAgendaNotes.getAppliId());

					agendaReportBean = mapper.convertValue(map, AgendaReportBean.class);
				}

				String str = "";
				if (agendaReportBean.getBusinessEntityName() != null) {
					str = agendaReportBean.getBusinessEntityName();

				}
				if (agendaReportBean.getBusinessAddress() != null) {
					str += ", ";
					str += agendaReportBean.getBusinessAddress();
				}
				if (agendaReportBean.getPwProductName() != null) {
					str += ", ";
					str += agendaReportBean.getPwProductName();
				}

				agendaReportIndexBean.setParticular(str);
				agendaReportIndexBeanList.add(agendaReportIndexBean);
			}
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReportIndex.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			// System.out.println(url);
			/// System.out.println("benlist size : " + agendaReportIndexBeanList.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportIndexBeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
			// System.out.println("inside index report");
			return jasperPrint;

		} catch (Exception ex) {
			System.out.println("======================");
			ex.printStackTrace();

		} finally {

		}

		return jasperPrint;
	}

	@Override
	public String createDigitalSignature(Model model, HttpSession session)
			throws IOException, GeneralSecurityException, org.dom4j.DocumentException {

		// DigitalSignatureServiceImpl.accessSignature();
		String str = "Succesfully verified";
		return str;

	}

	JasperPrint agendaReportComment(String appId)
			throws JRException, IOException, GeneralSecurityException, DocumentException {

		JasperPrint jasperPrint = null;
		AgendaReportSignBean agendaReportSignBean = new AgendaReportSignBean();
		List<AgendaReportSignBean> agendaReportSignBeanList = new ArrayList<>();
		// byte[] signature = digitalSignatureService.checkDigitalSingleItem();
		StoreSignatureEntity storeSignatureEntity = new StoreSignatureEntity(); // storeSignatureRepo.findSignatureById(171);

		List<StoreSignatureEntity> storeSignatureEntityList = storeSignatureRepo.findSignatureByAppId(appId);

		if (storeSignatureEntityList.size() > 0) {
			storeSignatureEntity = storeSignatureEntityList.get(0);
		}
		try {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteRepository
					.findPrepAgendaNotesListByApplId(appId);

			for (PrepareAgendaNotes prepareAgendaNotes : prepareAgendaNotesList) {
				agendaReportSignBean.setJmdcomments(prepareAgendaNotes.getJmdcomments());
				agendaReportSignBean.setJmdScrutinyDetail(prepareAgendaNotes.getJmdScrutinyDetail());
				agendaReportSignBean.setMdScrutinyDetail(prepareAgendaNotes.getMdScrutinyDetail());
				agendaReportSignBean.setComments(prepareAgendaNotes.getComments());
				agendaReportSignBean.setNotes(prepareAgendaNotes.getNotes());
				agendaReportSignBean.setPkupScrutinyDetail(prepareAgendaNotes.getPkupScrutinyDetail());
				// agendaReportSignBean.setSignature(new String(signature));
				// agendaReportSignBean.setSignature(new
				// String(storeSignatureEntity.getSignature()));

				agendaReportSignBean.setMdsignature(storeSignatureEntity.getMdsignature());
				agendaReportSignBean.setJmdsignature(storeSignatureEntity.getJmdsignature());
				if (storeSignatureEntity != null) {
					agendaReportSignBean.setSign(storeSignatureEntity.getSign());

				}
			}
			agendaReportSignBeanList.add(agendaReportSignBean);
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream url = null;
			url = classLoader.getResourceAsStream("AgendaReportSingnature.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(url);
			// System.out.println(url);
			/// System.out.println("benlist size : " + agendaReportIndexBeanList.size());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(agendaReportSignBeanList);
			java.util.Map<String, Object> parameters = new HashMap();
			parameters.put("createdBy", "java");
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
			// System.out.println("inside index report");
			return jasperPrint;

		} catch (Exception ex) {
			System.out.println("======================");
			ex.printStackTrace();

		} finally {

		}

		return jasperPrint;
	}

}
