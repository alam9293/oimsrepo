package com.cdgtaxi.ibs.reward.ui;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.hibernate.HibernateUtil;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

@SuppressWarnings("serial")
public class CreateItemWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateItemWindow.class);

	private final LrtbGiftCategory giftCategory;
	private LrtbGiftItem giftItem;

	private Textbox itemCodeTB, itemNameTB;
	private Decimalbox itemPriceDB;
	private Intbox itemPointsIB, itemStockIB;
	private Image itemImage;
	private Label itemNoImageLabel;

	@SuppressWarnings("unchecked")
	public CreateItemWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer categoryNo =  (Integer) params.get("categoryNo");
		logger.info("Gift Category No = " + categoryNo);
		giftCategory = businessHelper.getRewardBusiness().getCategory(categoryNo);
	}

	public void onCreate() {
		itemCodeTB = (Textbox) getFellow("itemCodeTB");
		itemNameTB = (Textbox) getFellow("itemNameTB");
		itemPriceDB = (Decimalbox) getFellow("itemPriceDB");
//		itemPriceDB.setValue(BigDecimal.ZERO);
		itemStockIB = (Intbox) getFellow("itemStockIB");
//		itemStockIB.setValue(0);
		itemPointsIB = (Intbox) getFellow("itemPointsIB");
//		itemPointsIB.setValue(0);
		itemImage = (Image) getFellow("itemImage");
		itemNoImageLabel = (Label) getFellow("itemNoImageLabel");

		itemCodeTB.focus();
	}

	public void saveItem() throws InterruptedException {
		try {
			this.checkItemCodeUsed();
			
			giftItem = new LrtbGiftItem();
			giftItem.setLrtbGiftCategory(giftCategory);
			giftItem.setItemCode(itemCodeTB.getValue());
			giftItem.setItemName(itemNameTB.getValue());
			giftItem.setPrice(itemPriceDB.getValue());
			giftItem.setStock(itemStockIB.getValue());
			giftItem.setPoints(itemPointsIB.getValue());
			if (itemImage.getContent() != null) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				Blob blob = Hibernate.getLobCreator(session).createBlob(itemImage.getContent().getByteData());
				//Blob blob = new BlobImpl(itemImage.getContent().getByteData());
				giftItem.setImage(blob);
			}

			businessHelper.getGenericBusiness().save(giftItem, getUserLoginIdAndDomain());
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
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.toString());
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
	
	private void checkItemCodeUsed(){
		LrtbGiftItem example = new LrtbGiftItem();
		example.setItemCode(itemCodeTB.getValue());
		if(this.businessHelper.getGenericBusiness().isExists(example))
			throw new WrongValueException(itemCodeTB, "Item code has been used");

	}
	
	public void updatePoints() throws InterruptedException{
		
		MstbMasterTable master = ConfigurableConstants.getMasterTable(
				ConfigurableConstants.REWARDS_PRICE_PTS_RATIO,
				NonConfigurableConstants.REWARDS_PRICE_PTS_RATIO_MASTER_CODE);
		
		if(master==null || master.getMasterValue()==null || master.getMasterValue().length()==0){
			Messagebox.show(
					"Price/Points Ration setting not found. Please contact the administrator.", "New Gift Item",
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		BigDecimal ratio = new BigDecimal(master.getMasterValue());
		
		itemPointsIB.setValue(itemPriceDB.getValue().divide(ratio, 0, BigDecimal.ROUND_CEILING).intValue());
	}
}
