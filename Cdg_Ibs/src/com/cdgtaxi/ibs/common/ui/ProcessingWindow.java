package com.cdgtaxi.ibs.common.ui;
import org.zkoss.lang.Threads;

import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class ProcessingWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	public ProcessingWindow(){}
	
	@Override
	public void refresh() throws InterruptedException {
		Threads.sleep(100);
		this.detach();
	}
}