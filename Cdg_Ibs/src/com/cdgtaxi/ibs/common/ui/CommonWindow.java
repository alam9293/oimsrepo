package com.cdgtaxi.ibs.common.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.security.CustomFilterInvocationDefinitionSource;
import com.cdgtaxi.ibs.common.business.BusinessHelper;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

import sun.misc.BASE64Decoder;

public abstract class CommonWindow extends Window{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CommonWindow.class);
	protected BusinessHelper businessHelper;
	private CommonWindow previousPage;
	private final CustomFilterInvocationDefinitionSource objectDefinitionSource;
	private static Random rand = new Random((new Date()).getTime());

	public CommonWindow(){
		businessHelper = (BusinessHelper)SpringUtil.getBean("businessHelper");
		objectDefinitionSource = (CustomFilterInvocationDefinitionSource)SpringUtil.getBean("objectDefinitionSource");
	}

	public List<String> getUserGrantedRoles(){
		return (List<String>) this.getHttpServletRequest().getSession().getAttribute(Constants.GRANTED_ROLES);
	}

	public static String getUserLoginIdAndDomain(){
		return (String) Sessions.getCurrent().getAttribute(Constants.DOMAIN) + "\\" + (String) Sessions.getCurrent().getAttribute(Constants.LOGINID);
	}
	
	public static Long getUserId(){
		return (Long) Sessions.getCurrent().getAttribute(Constants.USERID);
	}

	public HttpServletRequest getHttpServletRequest(){
		HttpServletRequest request = (HttpServletRequest)Executions.getCurrent().getNativeRequest();
		return request;
	}

	public HttpServletResponse getHttpServletResponse(){
		HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
		return response;
	}

	public String getURI(){
		HttpServletRequest request = this.getHttpServletRequest();
		String uriWithContextPath = request.getRequestURI();
		return uriWithContextPath.replace(request.getContextPath(), "");
	}
	
	public String getURL(){
		
		HttpServletRequest request = this.getHttpServletRequest();
		 StringBuffer requestURL = request.getRequestURL();
		return requestURL.toString();
	}
	
	public String getParams(){
		HttpServletRequest request = this.getHttpServletRequest();
		return request.getQueryString();
	}

	public static String decrypt(String encstr) {
		if (encstr.length() > 12) {
			String cipher = encstr.substring(12);
			BASE64Decoder decoder = new BASE64Decoder();
			try {
			    return new String(decoder.decodeBuffer(cipher));
			} catch (IOException e) {
			    //Fail
			}
		}
		return null;
	}
	
	
	public void forward(String uri, Map params) throws InterruptedException{
		logger.info("");

		if(!this.checkUriAccess(uri)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		this.setVisible(false);
		CommonWindow newPage = (CommonWindow)Executions.createComponents(uri, null, params);
		newPage.setPreviousPage(this);
	}
	
	public void forwardWithoutAccessCheck(String uri, Map params) throws InterruptedException{
		logger.info("");

		this.setVisible(false);
		CommonWindow newPage = (CommonWindow)Executions.createComponents(uri, null, params);
		newPage.setPreviousPage(this);
	}

	public CommonWindow back() throws InterruptedException{
		logger.info("");
		CommonWindow previousPage = this.getPreviousPage();
		previousPage.setVisible(true);
		previousPage.refresh();
		this.detach();
		return previousPage;
	}

	public CommonWindow getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(CommonWindow previousPage) {
		this.previousPage = previousPage;
	}

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public boolean checkUriAccess(String uri){
		logger.info(this.getUserLoginIdAndDomain()+"|"+uri);

		ConfigAttributeDefinition config = objectDefinitionSource.lookupAttributes(uri, "GET");

		List<String> grantedAuthorities
		= (List<String>)this.getHttpServletRequest().getSession().getAttribute(Constants.GRANTED_AUTHORITIES);

		boolean result = false; // abstain

		//The url has no roles tied to it, this means everyone can access
		if(config==null || config.getConfigAttributes().size()==0){
			logger.info(this.getUserLoginIdAndDomain()+"|"+uri+"|no roles tied to this function, everyone can access");
			return true;
		}

		// Will abstain if this ConfigAttributeDefinition (uri) has no
		// ConfigAttributes (roles) associated with it
		for (Iterator iter = config.getConfigAttributes().iterator(); iter.hasNext();) {
			ConfigAttribute attribute = (ConfigAttribute) iter.next();

			logger.debug("ConfigAttribute: "+attribute.getAttribute());

			if(attribute.getAttribute().equals("")) {
				return true;
			}

			for (int i = 0; i < grantedAuthorities.size(); i++){
				if(attribute.getAttribute().equals(grantedAuthorities.get(i))) {
					return true;
				}
			}
		}

		logger.info(this.getUserLoginIdAndDomain()+"|"+uri+"|"+result);
		return result;
	}
	public void displayProcessing() throws SuspendNotAllowedException, InterruptedException{
		Window win = (Window) Executions.createComponents(Uri.PROCESSING, null, null);
		win.setClosable(true);
		win.setWidth("200px");
		win.doModal();
	}
	public abstract void refresh() throws InterruptedException;

	////////////////////////////////////////////////
	// Convenience Component Methods
	////////////////////////////////////////////////

	protected Comboitem newComboitem(Object value, String label) {
		Comboitem item = new Comboitem();
		item.setLabel(label);
		item.setValue(value);

		return item;
	}

	protected Listitem newListitem(Object value) {
		return newListitem(value, null);
	}

	protected Listitem newListitem(Object value, String label) {
		Listitem item = new Listitem();
		if (label != null) {
			item.setLabel(label);
		}
		item.setValue(value);

		return item;
	}

	protected Listcell newListcell(Object value) {
		return newListcell(value, null);
	}
	
	protected Listcell newCheckListCell() {
		Listcell checkboxListCell = new Listcell();
    	checkboxListCell.appendChild(new Checkbox());
		return checkboxListCell;
	}

	protected Listcell newListcell(Object value, String format) {
		String label;
		if (format!=null &&  format.equals(StringUtil.GLOBAL_STRING_FORMAT)) {
			label = value.toString();
		} 
		else if (format!=null &&  format.equals(StringUtil.DECIMAL_IN_INTEGER_FORMAT)) {
			label = StringUtil.numberToString((Number) value, format);
		}
		else if (value instanceof BigDecimal) {
			label = StringUtil.numberToString(
					(BigDecimal) value,
					(format==null) ? StringUtil.GLOBAL_DECIMAL_FORMAT : format);
		} else if (value instanceof Integer) {
			label = StringUtil.numberToString(
					(Integer) value,
					(format==null) ? StringUtil.GLOBAL_INTEGER_FORMAT : format);
		} else if (value instanceof Long) {
			label = StringUtil.numberToString(
					(Long) value,
					(format==null) ? StringUtil.GLOBAL_INTEGER_FORMAT : format);
		} else if (value instanceof Timestamp) {
			label = DateUtil.convertDateToStr((Timestamp) value,
					(format==null) ? DateUtil.LAST_UPDATED_DATE_FORMAT : format);
		} else if (value instanceof java.util.Date) {
			label = DateUtil.convertDateToStr((Date) value,
					(format==null) ? DateUtil.GLOBAL_DATE_FORMAT : format);
		} else if (value instanceof java.sql.Date) {
			label = DateUtil.convertDateToStr((Date) value,
					(format==null) ? DateUtil.GLOBAL_DATE_FORMAT : format);
		} else {
			label = value==null ? "" : value.toString();
		}

		Listcell cell = new Listcell();
		cell.setLabel(label);
		cell.setValue(value);

		return cell;
	}
	
	/**
	 * To create a list cell with value that match the datatype of other result but having an "empty" label.
	 * This is to allow sorting.
	 * e.g. label = "-", value = "-1"
	 * @param value
	 * @param label
	 * @return
	 */
	protected Listcell newEmptyListcell(Object value, String label) {
		Listcell cell = new Listcell();
		cell.setLabel(label);
		cell.setValue(value);
		return cell;
	}
}
