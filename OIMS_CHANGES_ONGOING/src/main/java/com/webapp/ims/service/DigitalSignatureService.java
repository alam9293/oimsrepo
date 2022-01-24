package com.webapp.ims.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.itextpdf.text.DocumentException;
import com.webapp.ims.model.DigitalSignatureBean;

 

@Service
public interface DigitalSignatureService {

 	DigitalSignatureBean createProfile(DigitalSignatureBean digitalSignatureBean);

	void checkDigitalSignature() throws IOException, GeneralSecurityException, DocumentException;

//	byte[] checkDigitalSingleItem() throws IOException, GeneralSecurityException, DocumentException;

	void cratePdf() throws FileNotFoundException;

	byte[] checkDigitalSingleItem(String applicantId ,Model model) throws IOException, GeneralSecurityException, DocumentException;

	



}