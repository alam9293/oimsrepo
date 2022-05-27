package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.IttbGiroUobHeader;
import com.cdgtaxi.ibs.common.model.giro.UobReturn;
import com.cdgtaxi.ibs.common.model.giro.UobReturnDetail;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.StringUtil;

public class PreviewGiroReturnFileWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PreviewGiroReturnFileWindow.class);
	private IttbGiroUobHeader giroUobHeader;
	private UobReturn uobReturn;
	private Media media;
	
	//Labels
	private Label bankCodeLabel, branchCodeLabel, bankAcctNoLabel,
		bankAcctNameLabel, creationDateLabel, valueDateLabel,
		successfulCountLabel, successfulAmountLabel, rejectedCountLabel,
		rejectedAmountLabel, totalCountLabel, totalAmountLabel;
	//Listbox
	private Listbox resultList;
	
	@SuppressWarnings("rawtypes")
	public PreviewGiroReturnFileWindow(){
		//Retrieve parameters passed from previous window
		Map parameters = Executions.getCurrent().getArg();
		giroUobHeader = (IttbGiroUobHeader)parameters.get("giroUobHeader");
		uobReturn = (UobReturn)parameters.get("uobReturn");
		media = (Media)parameters.get("media");
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	public void init() throws InterruptedException{
		try {
			bankCodeLabel.setValue(uobReturn.uobReturnHeader.bankCode.getData());
			branchCodeLabel.setValue(uobReturn.uobReturnHeader.branchCode.getData());
			bankAcctNoLabel.setValue(uobReturn.uobReturnHeader.bankAccountNo.getData());
			bankAcctNameLabel.setValue(uobReturn.uobReturnHeader.bankAccountName.getData());
			creationDateLabel.setValue(uobReturn.uobReturnHeader.creationDate.getData());
			valueDateLabel.setValue(uobReturn.uobReturnHeader.valueDate.getData());
			successfulCountLabel.setValue(
					(uobReturn.uobReturnTrailer.totalDebitCount.getDataInLong() - 
					uobReturn.uobReturnTrailer.rejectionDebitCount.getDataInLong())+"");
			successfulAmountLabel.setValue(
					StringUtil.bigDecimalToString(
							uobReturn.uobReturnTrailer.totalDebitAmount.getDataInBigDecimal().subtract(
								uobReturn.uobReturnTrailer.rejectionDebitAmount.getDataInBigDecimal())
								, StringUtil.GLOBAL_DECIMAL_FORMAT));
			rejectedCountLabel.setValue(uobReturn.uobReturnTrailer.rejectionDebitCount.getDataInLong().toString());
			rejectedAmountLabel.setValue(
					StringUtil.bigDecimalToString(
							uobReturn.uobReturnTrailer.rejectionDebitAmount.getDataInBigDecimal(),
							StringUtil.GLOBAL_DECIMAL_FORMAT));
			totalCountLabel.setValue(uobReturn.uobReturnTrailer.totalDebitCount.getDataInLong().toString());
			totalAmountLabel.setValue(
					StringUtil.bigDecimalToString(
							uobReturn.uobReturnTrailer.totalDebitAmount.getDataInBigDecimal(),
							StringUtil.GLOBAL_DECIMAL_FORMAT));
			
			for(UobReturnDetail detail : uobReturn.uobReturnDetails){
				Listitem item = new Listitem();
				
				item.appendChild(newListcell(detail.reference.getData()));
				item.appendChild(newListcell(detail.bankAccountName.getData()));
				item.appendChild(newListcell(detail.bankCode.getData()));
				item.appendChild(newListcell(detail.branchCode.getData()));
				item.appendChild(newListcell(detail.bankAccountNo.getData()));
				item.appendChild(newListcell(detail.particulars.getData()));
				item.appendChild(newListcell(detail.amount.getDataInBigDecimal()));
				item.appendChild(newListcell(NonConfigurableConstants.GIRO_UOB_CLEAR_FATE.get(detail.clearFate.getData())));
				MstbMasterTable rejectionCodeMaster = ConfigurableConstants.getMasterTable(ConfigurableConstants.GIRO_UOB_REJECTION_CODE, detail.rejectionCode.getData());
				String rejectionCode = rejectionCodeMaster == null ? detail.rejectionCode.getData() : rejectionCodeMaster.getMasterValue();
				item.appendChild(newListcell(rejectionCode));
				resultList.appendChild(item);
			}
		} 
		catch (FieldException e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void confirm() throws SuspendNotAllowedException, InterruptedException{
		Map args = new HashMap();
		args.put("media", media);
		args.put("uobReturn", uobReturn);
		args.put("giroUobHeader", giroUobHeader);
		final Window win = (Window) Executions.createComponents(Uri.CREATE_GIRO_RETURN_REQUEST, null, args);
		win.setMaximizable(false);
		win.doModal();
	}
	
	@Override
	public void refresh() {
		
	}
}
