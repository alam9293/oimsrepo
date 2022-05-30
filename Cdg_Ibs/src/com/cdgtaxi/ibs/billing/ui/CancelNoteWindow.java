package com.cdgtaxi.ibs.billing.ui;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;


public class CancelNoteWindow extends CommonWindow implements AfterCompose {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CancelNoteWindow.class);
	private final BmtbNote note;
	private final CommonWindow owner;

	private Combobox cancellationReasonCB;

	@Override
	public void refresh() throws InterruptedException {
	}

	public CancelNoteWindow() {
		Map params = Executions.getCurrent().getArg();
		note = (BmtbNote) params.get("note");
		owner = (CommonWindow) params.get("owner");
	}

	public void afterCompose() {
		cancellationReasonCB = (Combobox) getFellow("cancellationReasonCB");
		List<Comboitem> items =
			ComponentUtil.convertToComboitems(ConfigurableConstants.getCancellationReasons(), true);
		for (Comboitem item : items) {
			cancellationReasonCB.appendChild(item);
		}
	}

	public void ok() throws InterruptedException{
		cancellationReasonCB.getValue(); // just for firing the validation

		String cancellationReason = (String) cancellationReasonCB.getSelectedItem().getValue();
		note.setMstbMasterTableByCancellationReason(
				ConfigurableConstants.getMasterTable(
						ConfigurableConstants.CANCELLATION_REASON, cancellationReason));

		try {
			businessHelper.getNoteBusiness().cancelNote(note);
			detach();
			Messagebox.show("Note has been successfully cancelled", "Cancel Note",
					Messagebox.OK, Messagebox.INFORMATION);
			owner.back();
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
}
