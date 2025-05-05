package com.cdgtaxi.ibs.web.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabs;

public class CustomTabs extends Tabs{
	public void onChildRemoved(Component child){
		super.onChildRemoved(child);
		
		if(this.getChildren().size()==0){
			Tabbox functionTab = (Tabbox)this.getParent();
			functionTab.removeChild(functionTab.getTabs());
    		functionTab.removeChild(functionTab.getTabpanels());
		}
	}
}
