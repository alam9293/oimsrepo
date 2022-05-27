package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Rows;
import org.zkoss.zul.api.Intbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CardholderListWindow extends ReportWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CardholderListWindow.class);
	public CardholderListWindow() throws IOException {
		super("Card Holder List", "Product");
		// removing new from card status
	}
	@Override
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
		}
		
		
		//validate
		Intbox checkNoUsageField = (Intbox)this.getFellow("10. Check No Usage (within x months)");
		if(checkNoUsageField.getValue() < 0){
			throw new WrongValueException("Check no usage cannot be in negative value!");
		}
		
		
		//retrieve format
		String report = super.getReport();
		ERSClient client = super.getClient();
		String repository = super.getRepository();
		String reportCategory = super.getReportCategory();
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();
		if(format.equals(Constants.FORMAT_CSV)){
			throw new FormatNotSupportedException();
		}else{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+".rml", format, os, reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
			if(format.equals(Constants.FORMAT_EXCEL)){
				Filedownload.save(media);
			}else if(format.equals(Constants.FORMAT_PDF)){
				HashMap params = new HashMap();
				params.put("report", media);
				this.forward(Uri.REPORT_RESULT, params);
			}
			os.close();
			client.close();
		}
	}
	public void removeNewCardStatus(){
		Listbox statuses = (Listbox)this.getFellow("03. Card Status");
		Listitem newItem = null;
		for(Object status : statuses.getChildren()){
			Listitem statusItem = (Listitem)status;
			if(statusItem.getValue().equals("N")){
				newItem = statusItem;
				break;
			}
		}
		if(newItem!=null){
			statuses.removeChild(newItem);
		}
	}
	
	public void populateReportParameters(Rows rows) throws NetException{
		
		super.populateReportParameters(rows);
		
		Intbox checkNoUsageField = (Intbox)this.getFellow("10. Check No Usage (within x months)");
		//default value to 3
		checkNoUsageField.setValue(3);
		
		
	}
	
	
}
