package com.cdgtaxi.ibs.web.component;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

@SuppressWarnings("serial")
public class Listbox extends org.zkoss.zul.Listbox {
	public Listbox() {
		addEventListener("onSelect", new EventListener() {
			public void onEvent(Event event) throws Exception {
				if (event != null)
				{
					Listbox cb = ((Listbox) event.getTarget());
					if(cb != null && cb.getSelectedItem()!=null)
						cb.setTooltiptext(cb.getSelectedItem().getLabel());
				}
			}
		});
	}
}
