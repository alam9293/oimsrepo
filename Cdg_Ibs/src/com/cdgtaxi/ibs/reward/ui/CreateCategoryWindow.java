package com.cdgtaxi.ibs.reward.ui;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

@SuppressWarnings("serial")
public class CreateCategoryWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateCategoryWindow.class);

	private Textbox categoryTB;

	public void onCreate() {
		categoryTB = (Textbox) getFellow("categoryTB");
		categoryTB.focus();
	}

	public void createCategory() throws InterruptedException {
		try {
			this.checkCategoryNameUsed();
			
			String categoryName = categoryTB.getValue();
			businessHelper.getRewardBusiness().createCategory(categoryName);
			Messagebox.show("Gift Category has been successfully created", "Create Gift Category",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		}
		catch (WrongValueException wve){
			throw wve;
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Override
	public void refresh() throws InterruptedException {
	}
	
	private void checkCategoryNameUsed(){
		LrtbGiftCategory example = new LrtbGiftCategory();
		example.setCategoryName(categoryTB.getValue());
		if(this.businessHelper.getGenericBusiness().isExists(example))
			throw new WrongValueException(categoryTB, "Category Name has been used");
	}
}
