package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.MomUploadDocumentsDis;
import com.webapp.ims.dis.repository.DisMomUploadDocumentsRepository;
import com.webapp.ims.dis.service.DisMomUploadDocumentsService;

@Service
@Transactional
public class DisMomUploadDocumentsServiceImpl implements DisMomUploadDocumentsService {

	@Autowired
	DisMomUploadDocumentsRepository momUploadDocumentsRepository;

	@Override
	public MomUploadDocumentsDis saveMomDocuments(MomUploadDocumentsDis momUploadDocuments) {

		return momUploadDocumentsRepository.save(momUploadDocuments);
	}

	@Override
	public void deleteMomDocuments(MomUploadDocumentsDis momUploadDocuments) {

		momUploadDocumentsRepository.delete(momUploadDocuments);
	}

}
