package com.cdgtaxi.ibs.web.component;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

@SuppressWarnings("serial")
public class Combobox extends org.zkoss.zul.Combobox {
	public Combobox() {
		addEventListener("onSelect", new EventListener() {
			public void onEvent(Event event) throws Exception {
				if (event != null)
				{
					Combobox cb = ((Combobox) event.getTarget());
					if (cb.getSelectedItem() != null)
						cb.setTooltiptext(cb.getSelectedItem().getLabel());
				}
			}
		});
	}
}
