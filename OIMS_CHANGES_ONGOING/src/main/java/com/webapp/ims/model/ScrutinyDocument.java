package com.webapp.ims.model;

import java.io.File;
import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Scrutiny_Document")
public class ScrutinyDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String scruDocId;
	private String appId;
	private File  file;

	private transient String confProvbyCTDDoc;
	private transient String confProvbyBankDoc;
	private transient String externalERDDoc;
	
	private transient String confProvbyCTDDocBase64;
	private transient String confProvbyBankDocBase64;
	private transient String externalERDDocBase64;
	
	
	public ScrutinyDocument() {
	}

	public ScrutinyDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getScruDocId() {
		return scruDocId;
	}

	public void setScruDocId(String scruDocId) {
		this.scruDocId = scruDocId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getConfProvbyCTDDoc() {
		return confProvbyCTDDoc;
	}

	public void setConfProvbyCTDDoc(String confProvbyCTDDoc) {
		this.confProvbyCTDDoc = confProvbyCTDDoc;
	}

	public String getConfProvbyBankDoc() {
		return confProvbyBankDoc;
	}

	public void setConfProvbyBankDoc(String confProvbyBankDoc) {
		this.confProvbyBankDoc = confProvbyBankDoc;
	}

	public String getExternalERDDoc() {
		return externalERDDoc;
	}

	public void setExternalERDDoc(String externalERDDoc) {
		this.externalERDDoc = externalERDDoc;
	}

	public String getConfProvbyCTDDocBase64() {
		return confProvbyCTDDocBase64;
	}

	public void setConfProvbyCTDDocBase64(String confProvbyCTDDocBase64) {
		this.confProvbyCTDDocBase64 = confProvbyCTDDocBase64;
	}

	public String getConfProvbyBankDocBase64() {
		return confProvbyBankDocBase64;
	}

	public void setConfProvbyBankDocBase64(String confProvbyBankDocBase64) {
		this.confProvbyBankDocBase64 = confProvbyBankDocBase64;
	}

	public String getExternalERDDocBase64() {
		return externalERDDocBase64;
	}

	public void setExternalERDDocBase64(String externalERDDocBase64) {
		this.externalERDDocBase64 = externalERDDocBase64;
	}

 

}
