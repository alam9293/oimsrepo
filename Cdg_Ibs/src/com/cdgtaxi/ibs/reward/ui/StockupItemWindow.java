package com.cdgtaxi.ibs.reward.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;


@SuppressWarnings("serial")
public class StockupItemWindow extends CommonWindow implements AfterCompose {
	private static final Logger logger = Logger.getLogger(StockupItemWindow.class);
	private final LrtbGiftItem giftItem;
	private final CommonWindow owner;

	private Intbox stockupQtyIB;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public StockupItemWindow() {
		Map params = Executions.getCurrent().getArg();
		giftItem = (LrtbGiftItem) params.get("giftItem");

		logger.info("Stocking up gift item " + giftItem.getItemCode());

		owner = (CommonWindow) params.get("owner");
	}

	public void afterCompose() {
		Label currentStockLabel = (Label) getFellow("currentStockLabel");
		currentStockLabel.setValue(StringUtil.numberToString(
				giftItem.getStock(), StringUtil.GLOBAL_INTEGER_FORMAT));

		stockupQtyIB = (Intbox) getFellow("stockupQtyIB");
	}

	public void ok() throws InterruptedException{
		Integer stockupQty = stockupQtyIB.getValue();

		try {
			businessHelper.getRewardBusiness().stockupItem(giftItem.getGiftItemNo(), stockupQty, getUserLoginIdAndDomain());
			detach();
			Messagebox.show("Item has been successfully stocked up", "Stock Up Gift Item",
					Messagebox.OK, Messagebox.INFORMATION);
			owner.refresh();
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
}
