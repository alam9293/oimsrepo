package com.webapp.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.MomUploadDocuments;
import com.webapp.ims.repository.MomUploadDocumentsRepository;
import com.webapp.ims.service.MomUploadDocumentsService;

@Service
@Transactional
public class MomUploadDocumentsServiceImpl implements MomUploadDocumentsService{

	@Autowired
	MomUploadDocumentsRepository momUploadDocumentsRepository;
	@Override
	public MomUploadDocuments saveMomDocuments(MomUploadDocuments momUploadDocuments) {

		return momUploadDocumentsRepository.save(momUploadDocuments);
	}
	
	@Override
	public void deleteMomDocuments(MomUploadDocuments momUploadDocuments) {

		momUploadDocumentsRepository.delete(momUploadDocuments);
	}

}
