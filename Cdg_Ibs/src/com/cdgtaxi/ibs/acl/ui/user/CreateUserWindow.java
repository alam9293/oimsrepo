package com.cdgtaxi.ibs.acl.ui.user;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ad.ADServiceException;
import com.cdgtaxi.ad.ComfortDelgroADService;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;

public class CreateUserWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateUserWindow.class);
	private static Comparator<SatbRole> roleComparator =
        new Comparator<SatbRole>() {
            public int compare(SatbRole r1, SatbRole r2) {
            	try {
	            	
            		if(r1.getName().compareTo(r2.getName())<0) return -1;
            		else if(r1.getName().compareTo(r2.getName())>0) return 1;
            		else return 0;
	                
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
                return -2;
            }
        };
    private Set<SatbRole> sortedRoles;
	private Combobox domainCB;
    
	@SuppressWarnings("rawtypes")
	public void afterCompose() {
		Components.wireVariables(this, this);

		Map adProperties = (Map) SpringUtil.getBean("adProperties");
		String disableAD = (String) adProperties.get("ad.disable");

		if (disableAD == null || !disableAD.equals("true")) {
			String[] domains = new String[]{};
			try {
				domains = ComfortDelgroADService.getAllSupportedDomains();
			} catch (ADServiceException e) {
				e.printStackTrace();
			}
			for (String domain : domains) {
				Comboitem item = new Comboitem();
				item.setLabel(domain);
				item.setValue(domain);
				domainCB.appendChild(item);
			}
		} else {
			Comboitem item = new Comboitem();
			item.setLabel("TestDomain");
			item.setValue("TestDomain");
			domainCB.appendChild(item);
		}

		if (domainCB.getChildren().size() == 1)
			domainCB.setSelectedIndex(0);
	}
	
	public CreateUserWindow(){
		sortedRoles = new TreeSet<SatbRole>(this.roleComparator);
	}
	
	public void create() throws InterruptedException{
		logger.info("");
		String loginId = ((CapsTextbox)this.getFellow("loginId")).getValue();
		Listbox roleListBox = ((Listbox)this.getFellow("role"));
		String name = ((CapsTextbox)this.getFellow("name")).getValue();
		String email = ((Textbox)this.getFellow("email")).getValue();
		RequiredConstraint.validate(domainCB);
		String domain = (String) domainCB.getSelectedItem().getValue();
		
		//Validations on the inputs before saving
		if(this.businessHelper.getUserBusiness().isLoginIdAndDomainUsed(loginId, domain))
			throw new WrongValueException(this.getFellow("loginId"), "LoginId already existed in the specified domain");
		if(roleListBox.getSelectedCount()==0)
			throw new WrongValueException(roleListBox, "Mandatory field");
		
		try{
			SatbUser newUser = this.businessHelper.getUserBusiness().createNewUser(loginId, domain,
					roleListBox.getSelectedItems(), name, email, getUserLoginIdAndDomain());

			//Show result
			Messagebox.show("New user "+name+" created", "Create user", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
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
	
	public void populateRole(Listbox listBox){
		
		List<SatbRole> roles = this.businessHelper.getRoleBusiness().getActiveRoles();
		sortedRoles.addAll(roles);
			
		if(sortedRoles.size()<10) listBox.setRows(sortedRoles.size());
		else listBox.setRows(10);
		
		for(SatbRole role : sortedRoles){
			Listitem listItem = new Listitem(role.getName());
			listItem.setAttribute("role", role);
			listBox.appendChild(listItem);
		}
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
