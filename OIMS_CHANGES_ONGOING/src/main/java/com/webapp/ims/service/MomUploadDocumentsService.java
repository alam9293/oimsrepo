package com.webapp.ims.service;

import com.webapp.ims.model.MomUploadDocuments;

public interface MomUploadDocumentsService {

	public MomUploadDocuments saveMomDocuments(MomUploadDocuments momUploadDocuments);
	
	public void deleteMomDocuments(MomUploadDocuments momUploadDocuments);
}
