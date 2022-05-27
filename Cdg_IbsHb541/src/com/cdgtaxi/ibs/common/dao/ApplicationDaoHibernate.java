package com.cdgtaxi.ibs.common.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbApplication;
import com.cdgtaxi.ibs.common.model.AmtbApplicationFlow;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;


public class ApplicationDaoHibernate extends GenericDaoHibernate implements ApplicationDao{
	private static Logger logger = Logger.getLogger(ApplicationDaoHibernate.class);
	@SuppressWarnings("unchecked")
	public List<AmtbApplication> searchApplication(String appNo, String appName, String appStatus){
		logger.info("searchApplication(Integer appNo, String appName, String appStatus)");
		logger.info("appNo = " + appNo + ", appName = "+appName+",appStatus = "+appStatus);
		DetachedCriteria applicationCriteria = DetachedCriteria.forClass(AmtbApplication.class);
		applicationCriteria.createCriteria("amtbApplicationProducts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbAcctType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria appStatusCriteria = applicationCriteria.createCriteria("amtbApplicationFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(appStatus!=null && appStatus.length()!=0){
			appStatusCriteria.add(Restrictions.eq("toStatus", appStatus));
			applicationCriteria.add(Subqueries.propertyIn("applicationNo", this.createMaxResultSubquery(applicationCriteria, "APPLICATION_NO", String.class)));
		}
		if(appName != null && appName.length()!=0){
			applicationCriteria.add(Restrictions.ilike("applicantName", appName, MatchMode.ANYWHERE));
		}
		if(appNo!=null && appNo.length()!=0){
			if(appNo.equals(Integer.parseInt(appNo)+"")){
				applicationCriteria.add(Restrictions.like("applicationNo", appNo, MatchMode.ANYWHERE));
			}else{
				applicationCriteria.add(Restrictions.or(Restrictions.like("applicationNo", appNo, MatchMode.ANYWHERE), Restrictions.eq("applicationNo", ""+Integer.parseInt(appNo))));
			}
		}
		return(this.findDefaultMaxResultByCriteria(applicationCriteria));
	}
	@SuppressWarnings("unchecked")
	public List<AmtbApplication> searchApplication(List<String> appStatuses){
		logger.info("searchApplication(List<String> appStatuses)");
		logger.info("appStatuses = " + appStatuses);
		DetachedCriteria applicationCriteria = DetachedCriteria.forClass(AmtbApplication.class);
		applicationCriteria.createCriteria("amtbApplicationFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			.createCriteria("satbUser", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbApplicationProducts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbAcctType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbApplication> apps = this.findAllByCriteria(applicationCriteria);
		List<AmtbApplication> returnList = new ArrayList<AmtbApplication>();
		if(appStatuses!=null && !appStatuses.isEmpty()){
			for(AmtbApplication app : apps){
				TreeSet<AmtbApplicationFlow> arrangedStatus = new TreeSet<AmtbApplicationFlow>(new Comparator<AmtbApplicationFlow>(){
					public int compare(AmtbApplicationFlow af1, AmtbApplicationFlow af2) {
						return af1.getFlowDt().compareTo(af2.getFlowDt());
					}
				});
				arrangedStatus.addAll(app.getAmtbApplicationFlows());
				if(appStatuses.contains(arrangedStatus.last().getToStatus())){
					returnList.add(app);
				}
			}
		}
		return returnList;
	}
	public List<AmtbApplication> searchApplicationChunk(List<String> appNo){
		logger.info("searchApplication(List<String> appNo)");
		DetachedCriteria applicationCriteria = DetachedCriteria.forClass(AmtbApplication.class);
		applicationCriteria.createCriteria("amtbApplicationFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN).createCriteria("satbUser", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbApplicationProducts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbAcctType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		applicationCriteria.add(Restrictions.in("applicationNo", appNo));
		
		return this.findAllByCriteria(applicationCriteria);
	}
	public List<Object[]> searchApplicationObject(List<String> appStatuses){
	
		//super.getActiveDBTransaction();
		List<Object[]> returnList =null;
		Session session =null;
		try
		{
			this.getActiveDBTransaction();
			session = this.currentSession();
			SQLQuery query = session.createSQLQuery("select a.application_no, a.applicant_name "
					    +" from amtb_application a inner join ( "
						+" select distinct application_no, "
						+" first_value(TO_STATUS) over (Partition by (APPLICATION_NO) order by FLOW_DT desc) as ACCT_STATUS , "
						+" first_value(APPLICATION_FLOW_NO) over (Partition by (APPLICATION_NO) order by FLOW_DT desc) as APPLICATION_FLOW_NO "
						+" from amtb_application_flow "
						+" where FLOW_DT <= sysdate "
						+" ) latest_status on a.APPLICATION_NO = latest_status.application_no "
						+" and latest_status.ACCT_STATUS in ('D', 'P1', 'P2') ");
			returnList = query.list();
		}
		catch(Exception e){logger.info(e);}
		finally{if(session.isConnected()){session.close();}}
		return returnList;
	}
	public boolean createApplication(AmtbApplication newApplication){
		logger.info("createApplication(Application newApplication)");
		//BigDecimal nextValue = this.getNextSequenceNo(Sequence.AMTB_APPLICATION_SQ1);
		//newApplication.setApplicationNo(StringUtil.appendLeft(nextValue.toString(), 8, "0"));
		return this.save(newApplication)!=null;
	}
	@SuppressWarnings("unchecked")
	public AmtbApplication getApplication(String appNo){
		logger.info("getApplication(String appNo)");
		DetachedCriteria applicationCriteria = DetachedCriteria.forClass(AmtbApplication.class);
		applicationCriteria.add(Restrictions.idEq(appNo));
		applicationCriteria.createCriteria("amtbApplicationProducts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbApplicationFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			.createCriteria("satbUser", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.createCriteria("amtbAcctType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		applicationCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbApplication> application = this.findAllByCriteria(applicationCriteria);
		if(application.size()!=0){
			return application.get(0);
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public MstbSalesperson getApplicationSalesperson(String appNo){
		logger.info("getApplication(String appNo)");
		DetachedCriteria personCriteria = DetachedCriteria.forClass(MstbSalesperson.class);
		personCriteria.createCriteria("amtbApplications", DetachedCriteria.FULL_JOIN)
			.add(Restrictions.eq("applicationNo", appNo));
		personCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<MstbSalesperson> persons = this.findAllByCriteria(personCriteria);
		return persons.isEmpty() ? null : persons.get(0);
	}
	public void saveApplication(AmtbApplication newApplication){
		logger.info("saveApplication(AmtbApplication newApplication)");
		this.update(newApplication);
	}
	public boolean checkRCBNo(String rcbNo){
		String sql = "select app.application_no from amtb_application app" +
				" INNER JOIN" +
				" (select flow.application_no, max(flow.flow_dt) as flow_dt" +
				" from amtb_application_flow flow" +
				" where flow.flow_dt < SYSTIMESTAMP group by flow.application_no) v" +
				" ON v.application_no = app.application_no" +
				" inner join amtb_application_flow flow" +
				" on flow.application_no = app.application_no" +
				" and flow.flow_dt = v.flow_dt" +
				" and (flow.to_status = ? or flow.to_status = ?)" +
				" where app.rcb_no = ?";
		Session session =null;
		boolean isempty =false;
		try
		{
			this.getActiveDBTransaction();
			session = this.currentSession();
			Query sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setString(1, NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL);
			sqlQuery.setString(2, NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL);
			sqlQuery.setString(3, rcbNo);
			isempty = !sqlQuery.list().isEmpty();
			return isempty;
		}
		catch(Exception e)
		{
			logger.info(e);
		}
		finally{if(session.isConnected()){session.close();}}
		
		return isempty;
	}
	
	public boolean checkCorporateName(String name){
		boolean result  = false;
		Session session = null;
		try
		{
			String sql = "select app.application_no from amtb_application app" +
			" inner join" +
			" (select flow.application_no, max(flow.flow_dt) as flow_dt" +
			" from amtb_application_flow flow" +
			" where flow.flow_dt < SYSTIMESTAMP group by flow.application_no) v" +
			" ON v.application_no = app.application_no" +
			" inner join amtb_application_flow flow" +
			" on flow.application_no = app.application_no" +
			" and flow.flow_dt = v.flow_dt" +
			" and (flow.to_status = ? or flow.to_status = ?)" +
			" inner join amtb_acct_type type" +
			" on app.acct_type_no = type.acct_type_no and type.acct_template = ?" +
			" where app.rcb_no = ?";
			this.getActiveDBTransaction();
			session = this.currentSession();
			Query sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setString(1, NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL);
			sqlQuery.setString(2, NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL);
			sqlQuery.setString(3, NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE);
			sqlQuery.setString(4, name);
			result =!sqlQuery.list().isEmpty();
			return result;
		}
		catch(Exception e){logger.info(e);}
		finally{if(session.isConnected()){session.close();}}
		return result;
	}
}