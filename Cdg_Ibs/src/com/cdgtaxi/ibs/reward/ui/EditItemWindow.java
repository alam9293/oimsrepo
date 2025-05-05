package com.cdgtaxi.ibs.reward.ui;

import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.lob.BlobImpl;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class EditItemWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditItemWindow.class);

	private final Integer giftItemNo;
	private LrtbGiftItem giftItem;

	private Intbox itemPointsIB;
	private Image itemImage;
	private Label itemNoImageLabel;
	private CapsTextbox itemNameTextBox;

	private Label createdByLabel, createdTimeLabel, createdDateLabel, 
	lastUpdatedDateLabel, lastUpdatedByLabel, lastUpdatedTimeLabel;
	
	private Button stockUp;
	
	@SuppressWarnings("unchecked")
	public EditItemWindow() {
		Map params = Executions.getCurrent().getArg();
		giftItemNo =  (Integer) params.get("giftItemNo");
		logger.info("Gift Item No = " + giftItemNo);
		giftItem = businessHelper.getRewardBusiness().getItem(giftItemNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		
		Label itemCodeLabel = (Label) getFellow("itemCodeLabel");
		itemCodeLabel.setValue(giftItem.getItemCode());

		itemNameTextBox.setValue(giftItem.getItemName());

		Label itemPriceLabel = (Label) getFellow("itemPriceLabel");
		itemPriceLabel.setValue(StringUtil.numberToString(
				giftItem.getPrice(), StringUtil.GLOBAL_DECIMAL_FORMAT));

		Label itemStockLabel = (Label) getFellow("itemStockLabel");
		itemStockLabel.setValue(StringUtil.numberToString(
				giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));

		itemPointsIB.setValue(giftItem.getPoints());

		if(giftItem.getCreatedBy()!=null && giftItem.getCreatedBy().length()>0)
			createdByLabel.setValue(giftItem.getCreatedBy());
		else createdByLabel.setValue("-");
		
		if(giftItem.getCreatedDt()!=null)
			createdDateLabel.setValue(DateUtil.convertDateToStr(giftItem.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		
		if(giftItem.getCreatedDt()!=null)
			createdTimeLabel.setValue(DateUtil.convertDateToStr(giftItem.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		
		if(giftItem.getUpdatedBy()!=null && giftItem.getUpdatedBy().length()>0)
			lastUpdatedByLabel.setValue(giftItem.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		
		if(giftItem.getUpdatedDt()!=null)
			lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(giftItem.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		
		if(giftItem.getUpdatedDt()!=null)
			lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(giftItem.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
		
		try {
			if (giftItem.getImage() != null) {
				InputStream is = giftItem.getImage().getBinaryStream();
				itemImage.setContent(new AImage(null, is));
				itemNoImageLabel.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!this.checkUriAccess(Uri.STOCKUP_GIFT_ITEM)){
			stockUp.setDisabled(true);
		}
	}

	public void updateItem() throws InterruptedException {
		try {
			giftItem.setPoints(itemPointsIB.getValue());
			if (itemImage.getContent() != null) {
				Blob blob = new BlobImpl(itemImage.getContent().getByteData());
				giftItem.setImage(blob);
			}
			giftItem.setItemName(this.itemNameTextBox.getValue());

			businessHelper.getGenericBusiness().update(giftItem, getUserLoginIdAndDomain());
			Messagebox.show(
					"Gift Item has been successfully saved.", "Save Gift Item",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
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

	public void uploadImage() throws InterruptedException {
		try {
			Media media = Fileupload.get(true);
			if (media == null) {
				return;
			}
			itemNoImageLabel.setVisible(false);
			itemImage.setContent(new AImage(null, media.getByteData()));
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

	public void stockupItem() throws InterruptedException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("giftItem", giftItem);
		args.put("owner", this);

		final Window win = (Window) Executions.createComponents(Uri.STOCKUP_GIFT_ITEM, null, args);
		win.setMaximizable(false);
		win.doModal();
	}

	@Override
	public void refresh() throws InterruptedException {
		giftItem = businessHelper.getRewardBusiness().getItem(giftItemNo);

		Label itemStockLabel = (Label) getFellow("itemStockLabel");
		itemStockLabel.setValue(StringUtil.numberToString(
				giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));
		
		if(giftItem.getUpdatedBy()!=null && giftItem.getUpdatedBy().length()>0)
			lastUpdatedByLabel.setValue(giftItem.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		
		if(giftItem.getUpdatedDt()!=null)
			lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(giftItem.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		
		if(giftItem.getUpdatedDt()!=null)
			lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(giftItem.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
}
