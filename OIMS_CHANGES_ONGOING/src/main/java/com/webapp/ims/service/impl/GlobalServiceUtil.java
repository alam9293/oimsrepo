package com.webapp.ims.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GlobalServiceUtil {
	private final Logger logger = LoggerFactory.getLogger(GlobalServiceUtil.class);

	/**
	 * The responsibility of this method is to download mongodb documents.
	 */
	public void downloadMongoDBDoc(String fileName, byte[] fileData, HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.setHeader("Content-Type", "application/pdf");

		// try-with-resources statement
		try (InputStream is = new ByteArrayInputStream(fileData)) {
			try {
				IOUtils.copy(is, response.getOutputStream());
			} catch (IOException e) {
				logger.error(String.format("#### downloadMongoDBDoc exception $$$ %s", e.getMessage()));
			}
		} catch (IOException e1) {
			logger.error(String.format("@@@@@ downloadMongoDBDoc exception @@@@@ %s", e1.getMessage()));
		}
	}

	/**
	 * The responsibility of this method is to generate 6 digit random number from 0
	 * to 999999
	 */
	public static String getRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

}
