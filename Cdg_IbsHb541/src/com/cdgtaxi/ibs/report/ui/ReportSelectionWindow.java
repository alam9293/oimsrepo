package com.cdgtaxi.ibs.report.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Br;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ReportSelectionWindow extends CommonWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReportSelectionWindow.class);
	private static final String SELF = "reportSelectionWindow";
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}

	public void afterCompose() {

		List<SatbResource> reportCategories = this.businessHelper.getReportBusiness().searchReportCategories(getUserLoginIdAndDomain());

		for(SatbResource reportCategory : reportCategories){
			Label categoryLabel = new Label(reportCategory.getDisplayName());
			categoryLabel.setStyle("font-weight: bold");
			this.appendChild(categoryLabel);
			
			Grid grid = new Grid();
			this.appendChild(grid);
			Rows rows = new Rows();
			grid.appendChild(rows);
			Row row = new Row();

			List<SatbResource> reports = this.businessHelper.getReportBusiness().searchReports(reportCategory, getUserLoginIdAndDomain());
			for(SatbResource report : reports){
				if(row.getChildren().size()==4){
					rows.appendChild(row);
					row = new Row();
				}

				Label label = new Label(report.getDisplayName());
				label.setStyle("cursor:pointer;");
				label.setAttribute("uri", report.getUri());
				label.setAttribute("category", report.getSatbResource().getDisplayName());
				label.setAttribute("report", report.getDisplayName());
				label.setAttribute("rsrcId", report.getRsrcId());
				
				label.addEventListener(Events.ON_CLICK, new EventListener() {
					public boolean isAsap() {
						return true;
					}

					@SuppressWarnings("unchecked")
					public void onEvent(Event event) throws Exception {
						Label self = (Label)event.getTarget();
						String reportUri = (String)self.getAttribute("uri");
						String reportCategory = (String)self.getAttribute("category");
						String report = (String)self.getAttribute("report");
						Long rsrcId = (Long)self.getAttribute("rsrcId");
						
						for(int i=0; i<reportUri.length(); i++){
							if(reportUri.charAt(i)=='?'){
//								report = reportUri.substring(i+1);
								reportUri = reportUri.substring(0,i);
								break;
							}
						}
						
//						String[] encodedString2 = report.split("=");
//						report = encodedString2[1];

						ReportSelectionWindow window = (ReportSelectionWindow)self.getPage().getFellow(ReportSelectionWindow.SELF);
						Map map = new HashMap();
						map.put("report", report);
						map.put("reportCategory", reportCategory);
						map.put("rsrcId", rsrcId);
						window.forward(reportUri, map);
					}
				});

				
				row.appendChild(label);
			}
			rows.appendChild(row);

			
			//line break
			this.appendChild(new Br());
		}
	}
}
