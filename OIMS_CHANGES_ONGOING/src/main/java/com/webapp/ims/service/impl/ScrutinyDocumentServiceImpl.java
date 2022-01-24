package com.webapp.ims.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapp.ims.dis.model.CISDocument;
import com.webapp.ims.dis.service.DisEvaluationDocumentService;
import com.webapp.ims.model.ScrutinyDocument;
import com.webapp.ims.repository.ScrutinyDocumentRepo;
import com.webapp.ims.service.ScrutinyDocumentService;

@Service
public class ScrutinyDocumentServiceImpl implements ScrutinyDocumentService {

	private final Logger logger = LoggerFactory.getLogger(DisEvaluationDocumentService.class);

	private AtomicInteger atomicInteger = new AtomicInteger();

	@Autowired
	ScrutinyDocumentRepo scrutinyDocumentRepo;

	@Override
	public void saveAndUpdateMultipleFiles(List<ScrutinyDocument> multipartFileList, String appId) {
		String ScrutinyDoc = null;

		try {
			ScrutinyDoc = appId + "SCRU";
			System.out.println("ScrutinyDoc" + ScrutinyDoc);
			// to update files by id
			List<ScrutinyDocument> cisDocList = scrutinyDocumentRepo.getListByScruDocId(ScrutinyDoc);
			for (ScrutinyDocument scrutinyDocument : cisDocList) {
				scrutinyDocumentRepo.delete(scrutinyDocument);
			}
			List<ScrutinyDocument> list = insertScrutinyDocList(multipartFileList, appId);

		} catch (Exception e) {
			logger.error(String.format("Error in File Save", e.getMessage()));
		} finally {

		}
	}

	public List<ScrutinyDocument> insertScrutinyDocList(List<ScrutinyDocument> files, String appId) {
		String scruId = null;
		String docId = null;

		List<ScrutinyDocument> list = new ArrayList<>();
		scruId = appId + "SCRU";
		try {
			for (ScrutinyDocument objScrutinyDocument : files) {
				docId = appId + "DC" + atomicInteger.getAndIncrement();
				objScrutinyDocument.setScruDocId(scruId);
				objScrutinyDocument.setDocId(docId);
				objScrutinyDocument.setAppId(appId);
				list.add(scrutinyDocumentRepo.insert(objScrutinyDocument));
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return null;

	}

	@Override
	public List<ScrutinyDocument> getAllScrutinyDocByAppId(String appId) {

		String ScrutinyDoc = null;
//		List<List<ScrutinyDocument>> scrutinyDocumentAllList = new ArrayList<>();
		List<ScrutinyDocument> scrutinyDocumentList = new ArrayList<>();
		try {
//			while (value.hasNext()) {
//			//	System.out.println(value.next());
			ScrutinyDoc = appId + "SCRU";
//				System.out.println("ScrutinyDoc" + ScrutinyDoc);
			scrutinyDocumentList = scrutinyDocumentRepo.getListByScruDocId(ScrutinyDoc);
//				scrutinyDocumentAllList.add(scrutinyDocumentList);
//				break;
//			}
			return scrutinyDocumentList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

		return null;

	}
	
	

}
