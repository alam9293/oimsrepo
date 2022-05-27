package com.cdgtaxi.ibs.acl.ui.role;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class EditRoleWindow extends CommonWindow {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditRoleWindow.class);
	private static Comparator<SatbResource> resourceComparator =
        new Comparator<SatbResource>() {
            public int compare(SatbResource r1, SatbResource r2) {
            	
            	try {
	            	if (r1.getSatbResource() != null && r2.getSatbResource() == null) return 1;
	            	if (r1.getSatbResource() == null && r2.getSatbResource() != null) return -1;
	            	if (r1.getSatbResource() != null && r2.getSatbResource() != null) {
		            	if (r1.getSatbResource().getSequence() != null && r2.getSatbResource().getSequence() == null) return 1;
		            	if (r1.getSatbResource().getSequence() == null && r2.getSatbResource().getSequence() != null) return -1;
		            	if (r1.getSatbResource().getSequence() != null && r2.getSatbResource().getSequence() != null) {
		                    if (r1.getSatbResource().getSequence() > r2.getSatbResource().getSequence()) return 1;
		                    if (r1.getSatbResource().getSequence() < r2.getSatbResource().getSequence()) return -1;
		            	}
	            	}
	            	
	            	if (r1.getSequence() != null && r2.getSequence() == null) return 1;
	            	if (r1.getSequence() == null && r2.getSequence() != null) return -1;
	            	if (r1.getSequence() != null && r2.getSequence() != null) {
	                	if (r1.getSequence() > r2.getSequence()) return 1;
		                if (r1.getSequence() < r2.getSequence()) return -1;
		            	if (r1.getSequence() == r2.getSequence()) return 0;
	            	}
	                
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
                return -2;
            }
        };
	
	//entity for ui to display the values
	private SatbRole role;
	
	public EditRoleWindow(){
		//retreive parameters from request
		Map map = Executions.getCurrent().getArg();
		Long roleId = (Long)map.get("roleId");
		role = (SatbRole)this.businessHelper.getRoleBusiness().get(roleId);
		if(role==null)
			throw new NullPointerException("RoldId["+roleId+"] not found!"); //This should not happen at all
	}
	
	public void save() throws InterruptedException{
		logger.info("");
		String name = ((Textbox)this.getFellow("name")).getValue();
		List resourceRows = ((Rows)this.getFellow("resourceRows")).getChildren();
		
		if(!role.getName().equals(name)){
			if (this.businessHelper.getRoleBusiness().isNameUsed(name)){
				Messagebox.show("Duplicate Role name found. Please enter a different name", "Edit Role", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		
		try{
			this.displayProcessing();
			role.setName(name);
			this.businessHelper.getRoleBusiness().updateRole(role, resourceRows, getUserLoginIdAndDomain());
			Messagebox.show("Changes saved", "Edit Role", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
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
	
	public void activateRole() throws InterruptedException{
		logger.info("");
		
		try{
			int response = Messagebox.show("Are you sure you want to activate the role?", "Activate Role", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
			if(response == Messagebox.YES) this.businessHelper.getRoleBusiness().activateRole(this.role, getUserLoginIdAndDomain());
			this.back();
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
	
	public void deactivateRole() throws InterruptedException{
		logger.info("");
		
		try{
			int response = Messagebox.show("Are you sure you want to deactivate the role?", "Deactivate Role", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
			if(response == Messagebox.YES) this.businessHelper.getRoleBusiness().deactivateRole(this.role, getUserLoginIdAndDomain());
			this.back();
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
	
	public void populateResource(Rows rows){
		List<SatbResource> resources = this.businessHelper.getResourceBusiness().get(Constants.ROOT_ID);
		
		//1st Tier
		for(SatbResource resource : resources){
			//Do not show out common access rights
			if(resource.getRsrcName().equals(Constants.COMMON)) continue;
				
			Group group = new Group(resource.getDisplayName(), resource);
			rows.appendChild(group);
			Row row = new Row();
			generateResourceRow(rows, row, resource);
		}
	}
	
	private void generateResourceRow(Rows rows, Row row, SatbResource resource){
		if(resource.getSatbResources().size()>0){
			//Do sorting for children resources using their sequence no.
			Set<SatbResource> resources = new TreeSet<SatbResource>(resourceComparator);
			resources.addAll(resource.getSatbResources());
			
			for(SatbResource resourceChild : resources){
				if(resourceChild.getSatbResources().size()>0){
					if(row.getChildren().size()>0){
						int remainingSpace = 6-row.getChildren().size();
						for(int i=0; i<remainingSpace; i++){
							row.appendChild(new Label());
						}
						rows.appendChild(row);
					}
					row = new Row();
					Label label = new Label(resourceChild.getRsrcName());
					label.setStyle("white-space:pre;font-weight: bold");
					row.appendChild(label);
					row.setSpans("6");
					rows.appendChild(row);
					row = new Row();
				}
				generateResourceRow(rows, row, resourceChild);
				
				if(row.getChildren().size()==6){
					rows.appendChild(row);
					row = new Row();
				}
			}
			
			if(row.getChildren().size()>0){
				int remainingSpace = 6-row.getChildren().size();
				for(int i=0; i<remainingSpace; i++){
					row.appendChild(new Label());
				}
				rows.appendChild(row);
				row = new Row();
			}
		}
		else{
			Checkbox checkbox = new Checkbox(resource.getRsrcName());
			checkbox.setAttribute("resource", resource);
			if(role.getSatbResources().contains(resource)) checkbox.setChecked(true);
			
			//Disable those with resource type "A" because those resource is only for SUPERADMIN role
			if(!role.getRoleId().equals(Constants.SUPERADMIN_ROLE_ID) 
					&& resource.getRsrcType().equals(Constants.RESOURCE_TYPE_ADMIN)) checkbox.setDisabled(true);
			
			row.appendChild(checkbox);
		}
	}
	
	public SatbRole getRole(){
		return role;
	}

	@Override
	public void refresh() {
		
	}
}
