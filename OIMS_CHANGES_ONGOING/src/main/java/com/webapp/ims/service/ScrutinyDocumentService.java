package com.webapp.ims.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.ScrutinyDocument;

@Service
public interface ScrutinyDocumentService {
	void saveAndUpdateMultipleFiles(List<ScrutinyDocument> multipartFileList, String appId);

	List<ScrutinyDocument> getAllScrutinyDocByAppId(String appId);
}