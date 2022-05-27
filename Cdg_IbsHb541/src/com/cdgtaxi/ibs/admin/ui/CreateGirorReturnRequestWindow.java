package com.cdgtaxi.ibs.admin.ui;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.GiroException;
import com.cdgtaxi.ibs.common.model.IttbGiroReturnReq;
import com.cdgtaxi.ibs.common.model.IttbGiroUobHeader;
import com.cdgtaxi.ibs.common.model.giro.UobReturn;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class CreateGirorReturnRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateGirorReturnRequestWindow.class);
	private Listbox processingTimeLB;
	private Datebox processingDateDB;
	
	private String incomingFileDirectory;
	private IttbGiroUobHeader giroUobHeader;
	private Media media;
	private UobReturn uobReturn;
	
	@SuppressWarnings("rawtypes")
	public CreateGirorReturnRequestWindow(){
		//retrieve properties bean
		Map properties = (Map)SpringUtil.getBean("giroConfigProperties");
		this.incomingFileDirectory = (String) properties.get("giro.uob.file.incoming.directory");
		
		//Retrieve parameters passed from previous window
		Map parameters = Executions.getCurrent().getArg();
		giroUobHeader = (IttbGiroUobHeader)parameters.get("giroUobHeader");
		media = (Media)parameters.get("media");
		uobReturn = (UobReturn)parameters.get("uobReturn");
	}
	
	private void populateTime(){
		for(int i=0; i<=23; i++){
			String time = ""+i;
			if(time.length()==1) time = "0"+time;
			Listitem item = new Listitem(time);
			item.setValue(time);
			processingTimeLB.appendChild(item);
		}
		if(processingTimeLB.getSelectedItem()==null) processingTimeLB.setSelectedIndex(0);
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		processingDateDB.setValue(DateUtil.getCurrentUtilDate());
		this.populateTime();
	}
	
	public void create() throws InterruptedException{
		logger.info("");
		
		try{
			//Must not upload before
			/*if(this.businessHelper.getAdminBusiness().isGiroReturnFileUploadedBefore(uobReturn.returnFileName)){
				Messagebox.show("Selected GIRO return file has been uploaded before!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}*/
			
			Date processingDate = DateUtil.convertDateTo0000Hours(processingDateDB.getValue());
			if(processingDate.compareTo(DateUtil.convertTo0000Hours(Calendar.getInstance())) == 0){
				int hourOfCurrentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				if(hourOfCurrentTime > Integer.parseInt((String) processingTimeLB.getSelectedItem().getValue())){
					Messagebox.show("Processing Date & Time has to be after current date & time", 
							"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			
			giroUobHeader.setReturnFileName(uobReturn.returnFileName);
			IttbGiroReturnReq newRequest = new IttbGiroReturnReq();
			newRequest.setIttbGiroUobHeader(giroUobHeader);
			newRequest.setProcessingDate(DateUtil.convertUtilDateToSqlDate(processingDateDB.getValue()));
			newRequest.setProcessingTime(Integer.parseInt((String) processingTimeLB.getSelectedItem().getValue()));
			newRequest.setStatus(NonConfigurableConstants.GIRO_REQUEST_STATUS_PENDING);
			
			Long requestNo = this.businessHelper.getAdminBusiness()
				.createGiroReturnRequest(newRequest, media, incomingFileDirectory, getUserLoginIdAndDomain());
			
			Messagebox.show("New request("+requestNo+") successfully created", "Create GIRO Return Request", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(GiroException ge){
			ge.printStackTrace();
			Messagebox.show(ge.getMessage(), 
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
			
			holfe.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Override
	public void refresh() {
		
	}
}
