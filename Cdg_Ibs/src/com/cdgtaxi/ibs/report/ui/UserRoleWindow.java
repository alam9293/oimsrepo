/**
 * Added by CDG
 */
package com.cdgtaxi.ibs.report.ui;

/**
 * This class is for User Role (UAM) Report
 * 
 * @author vani
 * @version 28/10/2010
 *
 * Modification History
 *
 * Modified by    Date            Description:
 * -----------    -----------     -----------
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class UserRoleWindow extends ReportWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserRoleWindow.class);
	public UserRoleWindow() throws IOException {
		super("UserRole", "UAM");		
	}
	@Override
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
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
}
