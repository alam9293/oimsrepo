package com.cdgtaxi.ibs.common.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.forms.SearchNoteForm;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class NoteDaoHibernate extends GenericDaoHibernate implements NoteDao {
	@SuppressWarnings("rawtypes")
	public List searchNote(String accountNo, String noteType) {
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		DetachedCriteria accountCriteria = noteCriteria.createCriteria("amtbAccountByAccountNo", DetachedCriteria.INNER_JOIN);
		DetachedCriteria noteDetailCriteria = noteCriteria.createCriteria("bmtbInvoiceDetailByIssuedInvoiceDetailNo", DetachedCriteria.INNER_JOIN);
		DetachedCriteria noteSummaryCriteria = noteDetailCriteria.createCriteria("bmtbInvoiceSummary", DetachedCriteria.INNER_JOIN);
		DetachedCriteria noteHeaderCriteria = noteSummaryCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.INNER_JOIN);

		accountCriteria.add(Restrictions.eq("accountNo", Integer.parseInt(accountNo)));
		noteCriteria.add(Restrictions.eq("noteType", noteType));
		System.out.println("accountNo is:"+accountNo);
		System.out.println("noteType is:"+noteType);
		return this.findAllByCriteria(noteCriteria);
	}

	@SuppressWarnings("rawtypes")
	public List getAccountWithType(String customerNo) {
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.add(Restrictions.eq("custNo", customerNo));
		DetachedCriteria accountTypeCriteria = accountCriteria.createCriteria("amtbAccountType", DetachedCriteria.INNER_JOIN);
		return this.findAllByCriteria(accountCriteria);
	}

	@SuppressWarnings("rawtypes")
	public List searchNote(Long noteNo, String accountNo) {
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		DetachedCriteria noteDetailIssueCriteria = noteCriteria.createCriteria("bmtbInvoiceDetailByIssuedInvoiceDetailNo", DetachedCriteria.INNER_JOIN);
		DetachedCriteria noteDetailBillCriteria = noteCriteria.createCriteria("bmtbInvoiceDetailByBilledInvoiceDetailNo", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria noteSummaryCriteria = noteDetailIssueCriteria.createCriteria("bmtbInvoiceSummary", DetachedCriteria.INNER_JOIN);
		DetachedCriteria noteHeaderCriteria = noteSummaryCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.INNER_JOIN);
		DetachedCriteria accountCriteria = noteCriteria.createCriteria("amtbAccountByAccountNo", DetachedCriteria.INNER_JOIN);

		accountCriteria.add(Restrictions.eq("accountNo", Integer.parseInt(accountNo)));
		noteCriteria.add(Restrictions.eq("noteNo", noteNo));

		return this.findAllByCriteria(noteCriteria);
	}

	public List<AmtbAccount> getAccountNoAndName (String accNo,String name){

		logger.info("Retriving data by name "+name);
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(accNo.trim().length()>0) {
			//accountCriteria.add(Restrictions.ilike("custNo",accNo, MatchMode.ANYWHERE));
			accountCriteria.add(Restrictions.or(Restrictions.ilike("custNo",accNo, MatchMode.ANYWHERE),Restrictions.eq("custNo",new Integer(accNo).toString())));
		}
		try{


		}catch(NumberFormatException nfe){nfe.printStackTrace();}
		if(name.trim().length()>0)
		{
			accountCriteria.add(Restrictions.ilike("accountName",name, MatchMode.ANYWHERE));
		}
		//accountCriteria.add(Restrictions.isNull("amtbParentAccount"));
		accountCriteria.add(Restrictions.isNull("amtbAccount"));
		System.out.println("This is the criteria"+accountCriteria);
		return this.findDefaultMaxResultByCriteria(accountCriteria);

	}

	@SuppressWarnings("rawtypes")
	public List getDivision(int corpParentAccountNo) {
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria parentAccount = accountCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		parentAccount.add(Restrictions.eq("accountNo", corpParentAccountNo));

		//accountCriteria re : dept
		//parentAccount re : div
		//parentAccount re : id = ?

		return this.findDefaultMaxResultByCriteria(accountCriteria);
	}

	@SuppressWarnings("rawtypes")
	public List getDepartment(int parentAccountNo) {
		DetachedCriteria deptAccountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria divAccountCriteria = deptAccountCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		divAccountCriteria.add(Restrictions.eq("accountNo", parentAccountNo));
		deptAccountCriteria.add(Restrictions.eq("accountCategory", NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT));

		return this.findDefaultMaxResultByCriteria(deptAccountCriteria);
	}

	public String getGst() {
		//DetachedCriteria gstCriteria = DetachedCriteria.forClass(MstbGstDetail.class);

		return "";
	}

	@SuppressWarnings("unchecked")
	public List<BmtbNote> getNotes(Long invoiceHeaderNo) {
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		noteCriteria.createCriteria("bmtbInvoiceTxnByBilledInvoiceTxnNo", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceDetail", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceSummary", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("mstbMasterTableByReason", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("mstbMasterTableByCancellationReason", DetachedCriteria.LEFT_JOIN);
		noteCriteria.add(Restrictions.eq("bmtbInvoiceHeader.invoiceHeaderNo", invoiceHeaderNo));
		return this.findDefaultMaxResultByCriteria(noteCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbNote> searchNote(SearchNoteForm form) {
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		DetachedCriteria invoiceCriteria = noteCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("bmtbInvoiceTxnByBilledInvoiceTxnNo", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceDetail", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceSummary", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);

		DetachedCriteria accountCriteria = noteCriteria.createCriteria("amtbAccount", "account", DetachedCriteria.LEFT_JOIN);
		// To link to parent
		DetachedCriteria parentCriteria  = accountCriteria.createCriteria("amtbAccount", "parent", Criteria.LEFT_JOIN);
		parentCriteria.createCriteria("amtbAccount", "grand", Criteria.LEFT_JOIN);

		//search by account
		if(form.getDepartment()!=null) {
			accountCriteria.add(Restrictions.idEq(form.getDepartment().getAccountNo()));
		} else if(form.getDivision()!=null) {
			accountCriteria.add(Restrictions.idEq(form.getDivision().getAccountNo()));
		} else if(form.getAccount()!=null) {
	
			parentCriteria.add(Restrictions.or(Restrictions.eq("account.accountNo", form.getAccount().getAccountNo()), 
					Restrictions.or(Restrictions.eq("parent.accountNo", form.getAccount().getAccountNo()), 
							Restrictions.eq("grand.accountNo", form.getAccount().getAccountNo()))
							)
			);
			
		} else{
			if (!StringUtil.isBlank(form.getCustomerNo())) {
				String custNo = Long.valueOf(form.getCustomerNo()).toString();
				accountCriteria.add(Restrictions.like("custNo", custNo, MatchMode.ANYWHERE));
			}

			if (form.getAccountName() != null && !form.getAccountName().equals("")) {
				
				Disjunction dj = Restrictions.disjunction();
				dj.add(Restrictions.and(
						Restrictions.isNotNull("account.custNo"),
						Restrictions.ilike("account.accountName", form.getAccountName(), MatchMode.ANYWHERE)
						));
				dj.add(Restrictions.and(
						Restrictions.isNotNull("parent.custNo"),
						Restrictions.ilike("parent.accountName", form.getAccountName(), MatchMode.ANYWHERE)
						));
				dj.add(Restrictions.and(
						Restrictions.isNotNull("grand.custNo"),
						Restrictions.ilike("grand.accountName", form.getAccountName(), MatchMode.ANYWHERE)
						));
				
				accountCriteria.add(dj);		
			}
		}
		
		if (form.getNoteNo() != null) {
			noteCriteria.add(Restrictions.eq("noteNo", form.getNoteNo()));
		}

		if (form.getIssuingInvoiceNo() != null) {
			invoiceCriteria.add(Restrictions.eq("invoiceNo", form.getIssuingInvoiceNo()));
		}

		if (form.getNoteDateFrom() != null) {
			// Set the time of the datefrom to 00:00:00 so that the time is not a factor
			Timestamp dateFrom = DateUtil.convertStrToTimestamp(DateUtil.convertDateToStr(form.getNoteDateFrom(), DateUtil.GLOBAL_DATE_FORMAT) + " 00:00:00", DateUtil.TRIPS_DATE_FORMAT);
			noteCriteria.add(Restrictions.ge("createdDt", dateFrom));
		}

		if (form.getNoteDateTo() != null) {
			// Set the time of the dateTo to 23:59:59 so that time is not a factor
			Timestamp dateTo = DateUtil.convertStrToTimestamp(DateUtil.convertDateToStr(form.getNoteDateTo(), DateUtil.GLOBAL_DATE_FORMAT) + " 23:59:59", DateUtil.TRIPS_DATE_FORMAT);
			noteCriteria.add(Restrictions.le("createdDt", dateTo));
		}
		else if (form.getNoteDateFrom() != null)
		{
			Timestamp dateTo = DateUtil.convertStrToTimestamp(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT) + " 23:59:59", DateUtil.TRIPS_DATE_FORMAT);
			noteCriteria.add(Restrictions.le("createdDt", dateTo));
		}

		if (!StringUtil.isBlank(form.getNoteType())) {
			noteCriteria.add(Restrictions.eq("noteType", form.getNoteType()));
		}

		if (!StringUtil.isBlank(form.getNoteStatus())) {
			noteCriteria.add(Restrictions.eq("status", form.getNoteStatus()));
		}

		//		accountCriteria.addOrder(Order.asc("accountNo"));
		noteCriteria.addOrder(Order.asc("noteNo"));

		noteCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findDefaultMaxResultByCriteria(noteCriteria);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BmtbNote getNote(Long noteNo) {
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		DetachedCriteria noteAccountCriteria = noteCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("bmtbInvoiceTxnByBilledInvoiceTxnNo", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceDetail", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceSummary", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("fmtbTransactionCode", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("mstbMasterTableByReason", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("mstbMasterTableByCancellationReason", DetachedCriteria.LEFT_JOIN);

		DetachedCriteria invoiceCriteria = noteCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceAccountCriteria = invoiceCriteria.createCriteria("amtbAccountByAccountNo", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria billToAccountCriteria = invoiceCriteria.createCriteria("amtbAccountByDebtTo", DetachedCriteria.LEFT_JOIN);
		// Get parent
		billToAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN).createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		noteCriteria.add(Restrictions.idEq(noteNo));

		List list = findAllByCriteria(noteCriteria);
		if (list.size() > 0) {
			return (BmtbNote) list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public BmtbNote getNoteByAcquireTxn(int tmtbAcquireTxnNo) {
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		DetachedCriteria invoiceTxnCriteria = noteCriteria.createCriteria("bmtbInvoiceTxnByIssuedInvoiceTxnNo", DetachedCriteria.LEFT_JOIN);
		invoiceTxnCriteria.add(Restrictions.eq("acquireTxnNo", tmtbAcquireTxnNo));
		List list = findAllByCriteria(noteCriteria);
		if (list.size() > 0) {
			return (BmtbNote) list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<BmtbNote> getPendingNotes(){
		DetachedCriteria noteCriteria = DetachedCriteria.forClass(BmtbNote.class);
		noteCriteria.createCriteria("mstbMasterTableByCancellationReason", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceCriteria = noteCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("fmtbTransactionCode", DetachedCriteria.LEFT_JOIN);
		noteCriteria.createCriteria("bmtbInvoiceTxnByBilledInvoiceTxnNo", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceDetail", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceSummary", DetachedCriteria.LEFT_JOIN)
		.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		invoiceCriteria.createCriteria("amtbAccountByAccountNo", DetachedCriteria.LEFT_JOIN);
		invoiceCriteria.createCriteria("amtbAccountByDebtTo", DetachedCriteria.LEFT_JOIN);
		
		//Join all parents
		noteCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
				.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
				.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		
		noteCriteria.add(Restrictions.in("status", new String[] {
				NonConfigurableConstants.NOTE_STATUS_PENDING,
				NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION }));
		
		noteCriteria.addOrder(Order.asc("noteNo"));
		noteCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return this.findAllByCriteria(noteCriteria);
	}
}
