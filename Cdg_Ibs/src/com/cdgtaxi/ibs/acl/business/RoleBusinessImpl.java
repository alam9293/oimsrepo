package com.cdgtaxi.ibs.acl.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Group;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.security.CustomFilterInvocationDefinitionSource;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;

public class RoleBusinessImpl extends GenericBusinessImpl implements RoleBusiness {
	
	private static final Logger logger = Logger.getLogger(RoleBusinessImpl.class);
	
	/**
	 * To retrieve all roles sorted asc by name
	 */
	public List<SatbRole> getRoles(){
		return this.daoHelper.getRoleDao().getRoles();
	}
	
	/**
	 * To retrieve all active roles sorted asc by name
	 */
	public List<SatbRole> getActiveRoles(){
		return this.daoHelper.getRoleDao().getActiveRoles();
	}
	
	public boolean isNameUsed(String name){
		return this.daoHelper.getRoleDao().isNameUsed(name);
	}
	
	public SatbRole createNewRole(String name, String createdBy){
		SatbRole newRole = new SatbRole();
		newRole.setName(name);
		newRole.setStatus(Constants.ROLE_STATUS_ACTIVE);
		
		//Give Default Common Public Resources
		SatbResource commonPublicResource = this.daoHelper.getResourceDao().get(Constants.COMMON_PUBLIC);
		newRole.getSatbResources().add(commonPublicResource);
		newRole.getSatbResources().addAll(commonPublicResource.getSatbResources());
		newRole.getSatbResources().add((SatbResource)this.daoHelper.getGenericDao().get(SatbResource.class, Constants.ROOT_ID));
		
		this.daoHelper.getGenericDao().save(newRole, createdBy);
		
		//Reload RequestMap
		CustomFilterInvocationDefinitionSource definitionSource =
			(CustomFilterInvocationDefinitionSource) SpringUtil.getBean("objectDefinitionSource");
		definitionSource.reload();
		
		return newRole;
	}
	
	public List<SatbRole> searchRole(String name, String status){
		return this.daoHelper.getRoleDao().searchRole(name, status);
	}

	public SatbRole get(Long roleId) {
		return this.daoHelper.getRoleDao().get(roleId);
	}
	
	public void deactivateRole(SatbRole role, String updatedBy){
		role.setStatus(Constants.ROLE_STATUS_INACTIVE);
		this.daoHelper.getGenericDao().update(role, updatedBy);
	}
	
	public void activateRole(SatbRole role, String updatedBy){
		role.setStatus(Constants.ROLE_STATUS_ACTIVE);
		this.daoHelper.getGenericDao().update(role, updatedBy);
	}
	
	public void updateRole(SatbRole role, List resourceRows, String updatedBy){
		Set<SatbResource> resources = new HashSet<SatbResource>();
		retrieveSatbResource(resources, resourceRows);
		role.setSatbResources(resources);
		
		this.daoHelper.getGenericDao().update(role, updatedBy);
		
		//Reload RequestMap
		CustomFilterInvocationDefinitionSource definitionSource =
			(CustomFilterInvocationDefinitionSource) SpringUtil.getBean("objectDefinitionSource");
		definitionSource.reload();
	}
	
	private void retrieveSatbResource(Set<SatbResource> resources, List resourceRows){
		for(int i=0; i<resourceRows.size(); i++){
			if(resourceRows.get(i).getClass().equals(Group.class)){
//				Group group = (Group)resourceRows.get(i);
//				resources.add((AclResource)group.getValue());
				continue;
			}
			else if(resourceRows.get(i).getClass().equals(Row.class)){
				Row rowItem = (Row)resourceRows.get(i);
				retrieveSatbResource(resources, rowItem.getChildren());
			}
			else if(resourceRows.get(i).getClass().equals(Label.class))
				continue;
			else if(resourceRows.get(i).getClass().equals(Checkbox.class)){
				Checkbox checkbox = (Checkbox)resourceRows.get(i);
				if(checkbox.isChecked()){
					SatbResource resourceToBeAdded = (SatbResource)checkbox.getAttribute("resource");
					resources.add(resourceToBeAdded);
					SatbResource parentResource = resourceToBeAdded.getSatbResource();
					while(parentResource!=null){
						if(!resources.contains(parentResource))
							resources.add(parentResource);
						else
							parentResource = parentResource.getSatbResource();
					}
				}
			}
			else{
				logger.debug("Unknown item:"+resourceRows.get(i).toString());
			}
		}
	}
	
	public boolean isDuplicateRole(String name)
	{
		if (this.daoHelper.getRoleDao().getActiveRole(name) != null)
		{
			return true;
		}
		else
			return false;
	}
}
