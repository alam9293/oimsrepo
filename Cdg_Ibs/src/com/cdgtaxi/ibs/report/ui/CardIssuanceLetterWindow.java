package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.ers2.client.Parameter;
import com.elixirtech.net.NetException;

public class CardIssuanceLetterWindow extends ReportWindow{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CardIssuanceLetterWindow.class);
	
	private String report;
	private String reportCategory;
	private ERSClient client;
	private Parameter[] parameters;
	
	//Report Server Properties
	private static String ip;
	private static String port;
	private static String username;
	private static String password;
	private static String repository;
	
	public CardIssuanceLetterWindow() throws IOException{
		super("Card Issuance Letter", "Account");
	}
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		logger.info("");
		this.displayProcessing();
		
		//Retrieve report parameters
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
		}
		String productType=reportParamsProperties.getProperty("2. Product Type");
		logger.info("Product Type ID : "+productType);
		boolean issueByBatch= this.businessHelper.getReportBusiness().checkProductTypeByID(productType);
		String report = super.getReport();
		ERSClient client = super.getClient();
		String repository = super.getRepository();
		String reportCategory = super.getReportCategory();
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();
		//retrieve format
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		logger.info("Report : "+report );
		logger.info("issueByBatch : "+issueByBatch );
		if(issueByBatch)
		   client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+" By Batch.rml", format, os, reportParamsProperties);
		else
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+" Not By Batch.rml", format, os, reportParamsProperties);
		AMedia media = new AMedia(report.replaceAll(" ", "_"), extension, format, os.toByteArray());
		if(format.equals(Constants.FORMAT_EXCEL) || format.equals(Constants.FORMAT_RTF)){
			Filedownload.save(media);
		}
		else if(format.equals(Constants.FORMAT_PDF)){
			HashMap params = new HashMap();
			params.put("report", media);
			this.forward(Uri.REPORT_RESULT, params);
		}
		os.close();
		client.close();
	}
}