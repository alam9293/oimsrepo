package com.cdgtaxi.ibs.common.model.recurring;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zkplus.spring.SpringUtil;


public class RecurringCsvHelper {

	private Map<String, List<RecurringCsvData>> csvDataList = new HashMap<String, List<RecurringCsvData>>();

	
	public synchronized void writeToFile( RecurringCsvData recurringCsvData, String labelDateTime, String monthDateTime, String yearDateTime, String csvFilePath) throws IOException {
		try{
			Map recurringConfigProperties = (Map)SpringUtil.getBean("recurringConfigProperties");
			String recurringDirectory = (String)recurringConfigProperties.get("recurring.directory");
			
			CSVUtil.writeCsvFileRecDtl(csvFilePath, recurringCsvData.getHeadertitle(),	recurringCsvData.getRecordData(), recurringDirectory, "", labelDateTime, recurringDirectory, ',', recurringCsvData);
		}
		catch(IOException e) {
			throw new IOException();
		}
	}
	
	
	public synchronized int getDataSize() {
		return csvDataList.size();
	}
	public void replaceDelimter(String csvFilePath, String c, String string) {
		// TODO Auto-generated method stub
		try {
			CSVUtil.replaceDelimter(csvFilePath, c, string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
