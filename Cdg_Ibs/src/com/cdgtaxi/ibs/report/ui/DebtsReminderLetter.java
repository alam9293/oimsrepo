package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.web.constraint.EmailConstraint;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class DebtsReminderLetter extends ReportWindow{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DebtsReminderLetter.class);
	public DebtsReminderLetter() throws IOException {
		super("Debts Reminder Letter Report", "Aging");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	/**
	 * method to make staff name and staff email case sensitive.
	 */
	public void init(){
		Textbox name = (Textbox)this.getFellow("11. Staff Name");
		Component nameParent = name.getParent();
		nameParent.removeChild(name);
		Textbox newName = new Textbox();
		newName.setId(name.getId());
		if(name.getValue()!=null && name.getValue().length()!=0){
			newName.setValue(name.getValue());
		}
		nameParent.appendChild(newName);
		Textbox email = (Textbox)this.getFellow("13. Staff Email");
		Component emailParent = email.getParent();
		emailParent.removeChild(email);
		Textbox newEmail = new Textbox();
		newEmail.setId(email.getId());
		if(email.getValue()!=null && email.getValue().length()!=0){
			newEmail.setValue(email.getValue());
		}
		newEmail.setConstraint(new EmailConstraint());
		emailParent.appendChild(newEmail);
		
		// oengh 29/01/2011 CR/0111/016, show invoice due date instead of system due date
		Map pdfGenProperties = (Map)SpringUtil.getBean("pdfGenProperties");
		int bufferVal = Integer.parseInt((String)pdfGenProperties.get("pdfgen.buffer.duedate"));
		Intbox buffer = (Intbox)this.getFellow("14. Due Date Buffer");
		buffer.setValue(bufferVal);
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
				throw new FormatNotSupportedException();
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