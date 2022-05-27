package com.cdgtaxi.ibs.common.model.email;

import java.io.File;

public class FileAttachment {
	public String fileName;
	public File tempFile;

	public FileAttachment(String fileName, File tempFile) {
		this.fileName = fileName;
		this.tempFile = tempFile;
	}
}
