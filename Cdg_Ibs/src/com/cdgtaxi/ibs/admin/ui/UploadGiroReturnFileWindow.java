package com.cdgtaxi.ibs.admin.ui;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.GiroException;
import com.cdgtaxi.ibs.common.model.IttbGiroUobDetail;
import com.cdgtaxi.ibs.common.model.IttbGiroUobHeader;
import com.cdgtaxi.ibs.common.model.giro.UobReturn;
import com.cdgtaxi.ibs.common.model.giro.UobReturnDetail;
import com.cdgtaxi.ibs.common.model.giro.UobReturnHeader;
import com.cdgtaxi.ibs.common.model.giro.UobReturnTrailer;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class UploadGiroReturnFileWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UploadGiroReturnFileWindow.class);
	private Label fileNameLabel;
	private Media media;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	public void chooseFile() throws InterruptedException {
		try {
			media = Fileupload.get(true);
			if (media == null) {
				fileNameLabel.setValue("");
				return;
			}
			
			fileNameLabel.setValue(media.getName());
		}
		catch(Exception e){
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void uploadAndValidate() throws InterruptedException {
		this.displayProcessing();
		
		try {
			if (media == null) {
				Messagebox.show("You need to choose a file to upload!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			if(!media.getContentType().equals("text/plain")){
				Messagebox.show("The file uploaded must be a plain text file!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			//Check for file name
			UobReturn uobReturn = null;
			try{
				uobReturn = new UobReturn(media.getName());
			}
			catch(FieldException ge){
				ge.printStackTrace();
				throw new GiroException("Invalid filename, expected filename format is UITOddmmNNx.txt");
			}
			
			//Must not upload before
			/*if(this.businessHelper.getAdminBusiness().isGiroReturnFileUploadedBefore(uobReturn.returnFileName)){
				Messagebox.show("Selected GIRO return file has been uploaded before!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}*/
			
			//Must have corresponding GIRO outgoing file
			IttbGiroUobHeader giroUobHeader = this.businessHelper.getAdminBusiness().getCorrespondingGiroOutgoingFile(uobReturn.expectedOutgoingFileName);
			if(giroUobHeader == null){
				Messagebox.show("There is no corresponding GIRO outgoing file!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			BufferedReader reader = new BufferedReader(new StringReader(new String(media.getByteData())));
			
			//Read everything in the line and store first
			List<String> linesOfData = new ArrayList<String>();
			String line;
			while((line = reader.readLine()) != null){
				//in case last line is just a carriage return
				if(line.length()>0) linesOfData.add(line);
			}
			
			//From lines of data and transform to entities
			//With the lines of data in a collection, we can determine first and last line
			//Over here the file format is checked as well
			Long rejectedCount = new Long(0);
			BigDecimal rejectedAmount = BigDecimal.ZERO;
			for(int i=0; i< linesOfData.size(); i++){
				//first record
				if(i == 0)
					uobReturn.uobReturnHeader = new UobReturnHeader(linesOfData.get(i));
				//last record
				else if(i == (linesOfData.size() - 1))
					uobReturn.uobReturnTrailer = new UobReturnTrailer(linesOfData.get(i));
				//the rest are details
				else{
					UobReturnDetail newDetail = new UobReturnDetail(linesOfData.get(i));
					uobReturn.uobReturnDetails.add(newDetail);
					if(newDetail.clearFate.getData().equals(NonConfigurableConstants.GIRO_UOB_CLEAR_FATE_REJECTED)){
						logger.info("CLEAR FATE [Rejected], Amount: "+newDetail.amount.getDataInBigDecimal());
						rejectedCount++;
						rejectedAmount = rejectedAmount.add(newDetail.amount.getDataInBigDecimal());
					}
				}
			}
			
			//Trailer record count check
			if(uobReturn.uobReturnTrailer.totalDebitCount.getDataInLong().longValue() != giroUobHeader.getOutgoingCount()){
				Messagebox.show("Return File Trailer record count is not the same as Giro Outgoing File record count!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			else if(uobReturn.uobReturnTrailer.totalDebitAmount.getDataInBigDecimal()
					.compareTo(giroUobHeader.getOutgoingAmount()) != 0){
				Messagebox.show("Return File Trailer record amount is not the same as Giro Outgoing File record amount!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			else if(uobReturn.uobReturnTrailer.rejectionDebitCount.getDataInLong().longValue() != rejectedCount.longValue()){
				logger.info("rejectionDebitCount: "+uobReturn.uobReturnTrailer.rejectionDebitCount.getDataInLong()+
						", rejectedCount: "+rejectedCount);
				Messagebox.show("Rejected count is different from the Return File Trailer rejection debit count!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			else if(uobReturn.uobReturnTrailer.rejectionDebitAmount.getDataInBigDecimal()
					.compareTo(rejectedAmount) != 0){
				logger.info("rejectionDebitAmount: "+uobReturn.uobReturnTrailer.rejectionDebitAmount.getDataInBigDecimal()+
						", rejectedAmount: "+rejectedAmount);
				Messagebox.show("Total Rejected amount is different from the Return File Trailer rejection debit amount!", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			int totalCount = 0;
			BigDecimal totalAmount = BigDecimal.ZERO;
			for(IttbGiroUobDetail giroUobDetail : giroUobHeader.getIttbGiroUobDetails()){
				totalCount++;
				totalAmount = totalAmount.add(giroUobDetail.getGiroAmount());
			}
			
			//proceed to next page
			HashMap map = new HashMap();
			map.put("uobReturn", uobReturn);
			map.put("giroUobHeader", giroUobHeader);
			map.put("media", media);
			this.forward(Uri.PREVIEW_GIRO_RETURN_FILE, map);
		}
		catch(GiroException ge){
			ge.printStackTrace();
			Messagebox.show(ge.getMessage(), 
					"Error", Messagebox.OK, Messagebox.ERROR);
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
