package com.cdgtaxi.ibs.report.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zul.Iframe;

import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ReportResultWindow extends CommonWindow{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReportResultWindow.class);
	private AMedia media;
	private String desktopHeight = "1";
	private Iframe iframe;
	
	public ReportResultWindow(){
		Map params = Executions.getCurrent().getArg();
		media = (AMedia)params.get("report");
	}
	
	public void loadContent(Iframe iframe){
		iframe.setWidth("100%");
		iframe.setContent(media);
		this.iframe = iframe;
	}
	
	public void onClientInfo(ClientInfoEvent event) {
		int height = event.getDesktopHeight();
		if(height <= 100) height = 100;
		else height = height-80;
		iframe.setHeight(height+"px");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}
