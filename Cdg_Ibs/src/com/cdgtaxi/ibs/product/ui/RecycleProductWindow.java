package com.cdgtaxi.ibs.product.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
	
	public class RecycleProductWindow extends CommonWindow {
	
	private static final long serialVersionUID = -8504273935091137189L;
	private static Logger logger = Logger.getLogger(RecycleProductWindow.class);
	
	public RecycleProductWindow(){}
	
	public void getOTUCardTypes(){
		
		Listbox otuCardTypesList=(Listbox)this.getFellow("otuCardTypes");
		
			Map<String,String> data=new LinkedHashMap<String,String>();
		data=(LinkedHashMap<String,String>)this.businessHelper.getProductBusiness().getOtuCardTypes();
		if(data!=null){
			ArrayList<Listitem>  myitem=new ArrayList<Listitem>();
			for(String key : data.keySet()){
				Listitem item=new Listitem();
				//item.setId(key);
				item.setValue(key);
				item.setLabel(data.get(key));
				myitem.add(item);         
			}
			otuCardTypesList.getItems().addAll(myitem);
			otuCardTypesList.setSelectedIndex(0);
		}
	}
	public void recycle() throws InterruptedException{
		
		Decimalbox cardNoStart=(Decimalbox)this.getFellow("cardNoStart");
		String startCardNo=cardNoStart.getText();
		Decimalbox cardNoEnd=(Decimalbox) this.getFellow("cardNoEnd");
		String endCardNo=cardNoEnd.getText();
		
		logger.info("Start "+startCardNo);
		logger.info("End "+endCardNo);
		int checkProcess=0;
		try{
		
			if(startCardNo==null || startCardNo.trim().equalsIgnoreCase("")){
				Messagebox.show("Card No Start is a mandatory field.","Recycle One Time Usage", Messagebox.OK, Messagebox.ERROR);
			}
			else{
				checkProcess=this.businessHelper.getProductBusiness().recycleProducts(startCardNo,endCardNo,getUserLoginIdAndDomain());
				logger.info("Check Process"+checkProcess);
				switch(checkProcess){
				
				case 1: Messagebox.show("Recycling Process Successful.","Recycle One Time Usage", Messagebox.OK, Messagebox.INFORMATION); break;
				case 2: Messagebox.show("Recycling Process is not completed successfully.","Recycle One Time Usage", Messagebox.OK, Messagebox.ERROR);break;
				case 3: Messagebox.show("There is no valid card to recycle.","Recycle One Time Usage", Messagebox.OK, Messagebox.ERROR);break;
				default:Messagebox.show("There is no valid card to recycle.","Recycle One Time Usage", Messagebox.OK, Messagebox.ERROR);break;
				}
				
			}
			
			cardNoStart.setText("");
			cardNoEnd.setText("");
		
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void cancel() throws InterruptedException {
		this.back();
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
	
	public void proceedToIssue() throws InterruptedException {
		
		logger.info("Proceed To Issue");
		this.forward(Uri.ISSUE_PRODUCT,null);
	}
}


