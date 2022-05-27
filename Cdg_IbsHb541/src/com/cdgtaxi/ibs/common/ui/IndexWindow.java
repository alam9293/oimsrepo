package com.cdgtaxi.ibs.common.ui;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.web.component.CustomTabs;

public class IndexWindow extends CommonWindow{

	private static final Logger logger = Logger.getLogger(IndexWindow.class);
	private static final String MENU_BAR = "menuBar";
	private static final String FUNCTION_TAB = "functionTab";
	private static final String SELF = "indexWindow";
	private Menubar menuBar;
	private Tabbox functionTab;
	private static Comparator<SatbResource> resourceComparator =
		new Comparator<SatbResource>() {
		public int compare(SatbResource r1, SatbResource r2) {

			try {
				if (r1.getSatbResource() != null && r2.getSatbResource() == null) {
					return 1;
				}
				if (r1.getSatbResource() == null && r2.getSatbResource() != null) {
					return -1;
				}
				if (r1.getSatbResource() != null && r2.getSatbResource() != null) {
					if (r1.getSatbResource().getSequence() != null && r2.getSatbResource().getSequence() == null) {
						return 1;
					}
					if (r1.getSatbResource().getSequence() == null && r2.getSatbResource().getSequence() != null) {
						return -1;
					}
					if (r1.getSatbResource().getSequence() != null && r2.getSatbResource().getSequence() != null) {
						if (r1.getSatbResource().getSequence() > r2.getSatbResource().getSequence()) {
							return 1;
						}
						if (r1.getSatbResource().getSequence() < r2.getSatbResource().getSequence()) {
							return -1;
						}
					}
				}

				if (r1.getSequence() != null && r2.getSequence() == null) {
					return 1;
				}
				if (r1.getSequence() == null && r2.getSequence() != null) {
					return -1;
				}
				if (r1.getSequence() != null && r2.getSequence() != null) {
					if (r1.getSequence() > r2.getSequence()) {
						return 1;
					}
					if (r1.getSequence() < r2.getSequence()) {
						return -1;
					}
					if (r1.getSequence() == r2.getSequence()) {
						return 0;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return -2;
		}
	};

	public void initMenuBar() throws Exception {
		try{
			Page page = this.getPage();
			/*while(page.getAttributes().isEmpty())
			{
				Thread.sleep(10);
				logger.info("wait for 10 ms");
			}*/
			logger.info("authorities :"+page.getAttributes());
			List<String> authorities = (List<String>)page.getAttribute(Constants.GRANTED_AUTHORITIES, Page.SESSION_SCOPE);
             logger.info("authorities :"+authorities);
			this.findPanelBarComponents(page);
			this.createMenuBar(authorities);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	private void createMenuBar(List<String> authorities){
		List<SatbResource> resources =
			this.businessHelper.getResourceBusiness().retrieveMenu(Constants.ROOT_ID, authorities);

		for(SatbResource resource : resources){
			// WILSON
			if (!resource.getSatbResource().getRsrcId().equals(Constants.ROOT_ID)) {
				continue;
			}

			Menu menu = new Menu(resource.getDisplayName());
			Menupopup menuPopup = new Menupopup();
			menuPopup.setId(resource.getDisplayName());
			menu.appendChild(menuPopup);

			//Create Subsequence MenuItems
			TreeSet<SatbResource> resourceChilds = new TreeSet<SatbResource>(resourceComparator);

			// WILSON
			for(SatbResource childResource : resources){
				
				if (childResource.getSatbResource().getRsrcId().equals(resource.getRsrcId())) {
					//logger.info("childResource.getSatbResource().getRsrcId()"+childResource.getSatbResource().getRsrcId());
					resourceChilds.add(childResource);
				}
			}
			//			resourceChilds.addAll(this.businessHelper.getResourceBusiness().retrieveMenu(resource.getRsrcId(), authorities));

			for(SatbResource resourceChild: resourceChilds){
				if(resource.getDisplay().equals(Constants.BOOLEAN_NO)) {
					continue;
				}
				//logger.info("resourceChild.getDisplayName()"+resourceChild.getDisplayName());
				createMenuItem(menuPopup, resourceChild, resources, authorities);
			}

			menuBar.appendChild(menu);
		}
	}

	private void createMenuItem(Menupopup menuPopup, SatbResource resource, List<SatbResource> resources, List<String> authorities){
		TreeSet<SatbResource> resourceChilds = new TreeSet<SatbResource>(resourceComparator);
		// WILSON
		for(SatbResource childResource : resources){
			if (childResource.getSatbResource().getRsrcId().equals(resource.getRsrcId())) {
				resourceChilds.add(childResource);
			}
		}
		//		resourceChilds.addAll(this.businessHelper.getResourceBusiness().retrieveMenu(resource.getRsrcId(), authorities));

		if(resourceChilds.size()>0){
			Menu menu = new Menu(resource.getDisplayName());
			Menupopup menuPopup2 = new Menupopup();
			menu.appendChild(menuPopup2);

			for(SatbResource resourceChild : resourceChilds){
				createMenuItem(menuPopup2, resourceChild, resources, authorities);
			}

			menuPopup.appendChild(menu);
		}
		else{
			Menuitem menuItem = new Menuitem(resource.getDisplayName());
			menuItem.addEventListener(Events.ON_CLICK, new EventListener() {
				public boolean isAsap() {
					return true;
				}

				public void onEvent(Event event) throws Exception {
					Menuitem self = (Menuitem)event.getTarget();

					IndexWindow window = (IndexWindow)self.getPage().getFellow(IndexWindow.SELF);
					window.createFunctionTab(self.getLabel(), (String)self.getAttribute("uri"));
					//	            	window.createFunctionTab(((Menu)(self.getParent().getParent())).getLabel(), (String)self.getAttribute("uri"));
				}
			});

			menuItem.setId(resource.getDisplayName());
			menuItem.setAttribute("uri", resource.getUri());
			menuPopup.appendChild(menuItem);
		}
	}

	private void createFunctionTab(String functionName, String uri){
		try{
			if(functionTab.getTabs()!=null && functionTab.getTabs().getChildren().size()==5) {
				Messagebox.show("Only a maximum of 5 Function tabs can be opened!","Alert",Messagebox.OK,Messagebox.ERROR);
			} else{
				if(functionTab.getTabs()==null){
					functionTab.appendChild(new CustomTabs());
					functionTab.appendChild(new Tabpanels());
				}

				//create new tab and panel
				Tab newTab = new Tab(functionName);
				newTab.setClosable(true);
				newTab.setSelected(true);
				//				this.getHttpServletRequest().getSession().setAttribute("selectedTab", newTab.getUuid());
				//				newTab.addEventListener(Events.ON_CLICK, new EventListener() {
				//					public boolean isAsap() {
				//					    return true;
				//					}
				//
				//		            public void onEvent(Event event) throws Exception {
				//		            	Tab self = (Tab)event.getTarget();
				//		            	self.getDesktop().getSession().setAttribute("selectedTab", self.getUuid());
				//		            }
				//		        });
				functionTab.getTabs().appendChild(newTab);

				Tabpanel newTabPanel = new Tabpanel();
				newTabPanel.setHeight("100%");
				newTabPanel.setWidth("99%");
				Iframe newIFrame = new Iframe(uri);
				newIFrame.setHeight("100%");
				newIFrame.setWidth("100%");
				newTabPanel.appendChild(newIFrame);
				functionTab.getTabpanels().appendChild(newTabPanel);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void findPanelBarComponents(Page page){
		Collection components = page.getDesktop().getComponents();
		Iterator iterator = components.iterator();
		while(iterator.hasNext()){
			Component c = (Component)iterator.next();

			if(c.getId().equals(MENU_BAR)) {
				menuBar = (Menubar)c;
			}
			if(c.getId().equals(FUNCTION_TAB)){
				functionTab = (Tabbox)c;
			}
		}
	}

	public void changePassword(){
		this.createFunctionTab("Change Password", Uri.CHANGE_PASSWORD);
	}

	@Override
	public void refresh() {

	}
}
