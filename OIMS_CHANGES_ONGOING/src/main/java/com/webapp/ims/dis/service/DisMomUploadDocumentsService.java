package com.webapp.ims.dis.service;

import com.webapp.ims.dis.model.MomUploadDocumentsDis;

public interface DisMomUploadDocumentsService {

	public MomUploadDocumentsDis saveMomDocuments(MomUploadDocumentsDis momUploadDocuments);

	public void deleteMomDocuments(MomUploadDocumentsDis momUploadDocuments);
}
