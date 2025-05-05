package com.cdgtaxi.ibs.billing.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.exception.ExcessiveNoteAmountException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDepositTxn;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.BmtbNoteFlow;
import com.cdgtaxi.ibs.common.model.forms.SearchNoteForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;

@SuppressWarnings("rawtypes")
public class NoteBusinessImpl extends GenericBusinessImpl implements NoteBusiness {
	private static final Logger logger = Logger.getLogger(NoteBusinessImpl.class);
	private static final BigDecimal ZERO = new BigDecimal("0.00");

	public NoteBusinessImpl() {
	}

	public AmtbAccount getAccount(String accountNo) {
		return this.daoHelper.getAccountDao().getAccount(accountNo);
	}

	public Long issueNote(BmtbNote note) throws ExcessiveNoteAmountException {
		note.setStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
		note.setCreatedDt(DateUtil.getCurrentTimestamp());
		note.setNoteTxnType(NonConfigurableConstants.NOTE_TXN_TYPE_MISC_INV);
		note.setAdminFee(ZERO); // N.A. for Misc Invoice Txn Type
		note.setProdDis(ZERO); // N.A. for Misc Invoice Txn Type
		note.setPromoDis(ZERO); // N.A. for Misc Invoice Txn Type
		
		Long noteNo = (Long) daoHelper.getGenericDao().save(note, note.getCreatedBy()); 
		
		BmtbNoteFlow noteFlow = new BmtbNoteFlow();
		noteFlow.setBmtbNote(note);
		noteFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		noteFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_NEW);
		noteFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
		noteFlow.setRemarks(note.getRemarks());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		noteFlow.setSatbUser(requestor);
		daoHelper.getGenericDao().save(noteFlow, note.getCreatedBy()); 
		
		List<SatbUser> toList = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_NOTE);
		List<SatbUser> ccList = new ArrayList<SatbUser>();
		this.sendEmailNotification(note, ConfigurableConstants.EMAIL_TYPE_NOTE_REQUEST_SUBMIT, requestor.getUsername(), toList, ccList);
		
		return noteNo;
	}

	public List searchNote(String accountNo, String noteType) {
		return this.daoHelper.getNoteDao().searchNote(accountNo, noteType);
	}

	public List searchNote(Long noteNo, String accountNo) {
		return this.daoHelper.getNoteDao().searchNote(noteNo, accountNo);
	}

	// cancel note
	public void cancelNote(BmtbNote note) {
		note.setStatus(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION);
		this.update(note, CommonWindow.getUserLoginIdAndDomain());
		
		BmtbNoteFlow noteFlow = new BmtbNoteFlow();
		noteFlow.setBmtbNote(note);
		noteFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		noteFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
		noteFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION);
		noteFlow.setRemarks(note.getMstbMasterTableByCancellationReason().getMasterValue());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		noteFlow.setSatbUser(requestor);
		daoHelper.getGenericDao().save(noteFlow, CommonWindow.getUserLoginIdAndDomain()); 
		
		List<SatbUser> toList = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_NOTE);
		List<SatbUser> ccList = new ArrayList<SatbUser>();
		this.sendEmailNotification(note, ConfigurableConstants.EMAIL_TYPE_NOTE_REQUEST_SUBMIT, requestor.getUsername(), toList, ccList);
	}

	public List<BmtbNote> getNotes(Long invoiceHeaderNo) {
		return this.daoHelper.getNoteDao().getNotes(invoiceHeaderNo);
	}

	public List<BmtbNote> searchNote(SearchNoteForm form) {
		return this.daoHelper.getNoteDao().searchNote(form);
	}

	public BmtbNote getNote(Long noteNo) {
		return this.daoHelper.getNoteDao().getNote(noteNo);
	}

	public List<BmtbNote> getPendingNotes(){
		return this.daoHelper.getNoteDao().getPendingNotes();
	}
	
	public void approveNotes(List<BmtbNote> notes, String remarks){
		for(BmtbNote note : notes){
			
			String[] requestorIdAndDomain = note.getCreatedBy().split("\\\\");
			String requestorId = requestorIdAndDomain[1];
			String requestorDomain = requestorIdAndDomain[0];
			SatbUser requestor = this.daoHelper.getUserDao().getUser(requestorId, requestorDomain);
			
			if(note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_PENDING)){
				
				String txnType = note.getFmtbTransactionCode().getTxnType();
				String noteType = note.getNoteType();
				
				// When issuing a CREDIT not, check that the note amount does not exceed the sum of
				// the invoice's transacted + CREDIT note amounts for the selected transaction type.
				// To block off the logic - confirmed by IT Comfort
				/*if (noteType.equals(NonConfigurableConstants.NOTE_TYPE_CREDIT)
						&& (txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_LATE_PAYMENT)
								|| txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_EARLY_PAYMENT)
								|| txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_REPLACEMENT_FEE)
								|| txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_VOLUME_DISCOUNT)
								|| txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_SUBSCRIPTION_FEE)
								|| txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_DEPOSIT))) {
					BigDecimal limitAmt =
						daoHelper.getInvoiceDao().getTransactedAmountByType(
								invoiceNo, txnType);

					BigDecimal noteAmt = note.getNoteAmount();

					logger.debug("limitAmt = " + limitAmt);
					logger.debug("noteAmt = " + noteAmt);

					if (noteAmt.compareTo(limitAmt) > 0) {
						throw new ExcessiveNoteAmountException(txnType, limitAmt, noteAmt);
					}
				}*/

				//If issue credit note for deposit, need to create refund deposit txn record
				if(noteType.equals(NonConfigurableConstants.NOTE_TYPE_CREDIT) &&
						txnType.equals(NonConfigurableConstants.TRANSACTION_TYPE_DEPOSIT)){
					BmtbInvoiceDepositTxn refundDepositTxn = new BmtbInvoiceDepositTxn();
					refundDepositTxn.setAmtbAccount(note.getAmtbAccount());
					refundDepositTxn.setTxnType(NonConfigurableConstants.DEPOSIT_TXN_TYPE_REFUND);
					refundDepositTxn.setAmount(note.getNoteAmount());
					refundDepositTxn.setTxnDate(DateUtil.getCurrentDate());
					refundDepositTxn.setCreatedBy(note.getCreatedBy());
					refundDepositTxn.setCreatedDt(DateUtil.getCurrentTimestamp());
					refundDepositTxn.setBmtbNote(note);
					this.save(refundDepositTxn, CommonWindow.getUserLoginIdAndDomain());
				}
				
				note.setStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
				this.update(note, CommonWindow.getUserLoginIdAndDomain());
				
				BmtbNoteFlow noteFlow = new BmtbNoteFlow();
				noteFlow.setBmtbNote(note);
				noteFlow.setFlowDt(DateUtil.getCurrentTimestamp());
				noteFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
				noteFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
				noteFlow.setRemarks(remarks);
				noteFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				daoHelper.getGenericDao().save(noteFlow, CommonWindow.getUserLoginIdAndDomain()); 
			}
			else if(note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION)){
				note.setStatus(NonConfigurableConstants.NOTE_STATUS_CANCELLED);
				note.setCancelDt(new Timestamp(new Date().getTime()));
				this.update(note, CommonWindow.getUserLoginIdAndDomain());
				
				BmtbNoteFlow noteFlow = new BmtbNoteFlow();
				noteFlow.setBmtbNote(note);
				noteFlow.setFlowDt(DateUtil.getCurrentTimestamp());
				noteFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION);
				noteFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_CANCELLED);
				noteFlow.setRemarks(remarks);
				noteFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				daoHelper.getGenericDao().save(noteFlow, CommonWindow.getUserLoginIdAndDomain()); 
			}
			
			List<SatbUser> toList = new ArrayList<SatbUser>();
			toList.add(requestor);
			List<SatbUser> ccList = new ArrayList<SatbUser>();
			this.sendEmailNotification(note, ConfigurableConstants.EMAIL_TYPE_NOTE_REQUEST_APPROVED, requestor.getUsername(), toList, ccList);
		}
	}
	
	public void rejectNotes(List<BmtbNote> notes, String remarks){
		for(BmtbNote note : notes){
			
			String[] requestorIdAndDomain = note.getCreatedBy().split("\\\\");
			String requestorId = requestorIdAndDomain[1];
			String requestorDomain = requestorIdAndDomain[0];
			SatbUser requestor = this.daoHelper.getUserDao().getUser(requestorId, requestorDomain);
			
			if(note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_PENDING)){
				note.setStatus(NonConfigurableConstants.NOTE_STATUS_REJECTED);
				this.update(note, CommonWindow.getUserLoginIdAndDomain());
				
				BmtbNoteFlow noteFlow = new BmtbNoteFlow();
				noteFlow.setBmtbNote(note);
				noteFlow.setFlowDt(DateUtil.getCurrentTimestamp());
				noteFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
				noteFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_REJECTED);
				noteFlow.setRemarks(remarks);
				noteFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				daoHelper.getGenericDao().save(noteFlow, CommonWindow.getUserLoginIdAndDomain()); 
			}
			else if(note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION)){
				note.setStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
				note.setMstbMasterTableByCancellationReason(null);
				this.update(note, CommonWindow.getUserLoginIdAndDomain());
				
				BmtbNoteFlow noteFlow = new BmtbNoteFlow();
				noteFlow.setBmtbNote(note);
				noteFlow.setFlowDt(DateUtil.getCurrentTimestamp());
				noteFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION);
				noteFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
				noteFlow.setRemarks(remarks);
				noteFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				daoHelper.getGenericDao().save(noteFlow, CommonWindow.getUserLoginIdAndDomain()); 
			}
			
			List<SatbUser> toList = new ArrayList<SatbUser>();
			toList.add(requestor);
			List<SatbUser> ccList = new ArrayList<SatbUser>();
			this.sendEmailNotification(note, ConfigurableConstants.EMAIL_TYPE_NOTE_REQUEST_REJECTED, requestor.getUsername(), toList, ccList);
		}
	}

	private void sendEmailNotification(BmtbNote note, String emailType, String submitter, List<SatbUser> toList, List<SatbUser> ccList) {
		List<String> toEmails = new ArrayList<String>();
		StringBuffer recipientNames = new StringBuffer();
		for(SatbUser recipient : toList){
			toEmails.add(recipient.getEmail());
			recipientNames.append(recipient.getName() + ",");
		}
		recipientNames.delete(recipientNames.length()-1, recipientNames.length());

		List<String> ccEmails = new ArrayList<String>();
		for(SatbUser recipient : ccList){
			ccEmails.add(recipient.getEmail());
		}
		
		AmtbAccount topLevelAccount = note.getAmtbAccount();
		while (topLevelAccount.getAmtbAccount() != null)
			topLevelAccount = topLevelAccount.getAmtbAccount();
		
		EmailUtil.sendEmail(
				toEmails.toArray(new String[]{}),
				ccEmails.toArray(new String[]{}),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
				.replaceAll("#custNo#", topLevelAccount.getCustNo())
				.replaceAll("#acctName#", topLevelAccount.getAccountName())
				.replaceAll("#userName#", recipientNames.toString())
				.replaceAll("#requestNo#", note.getNoteNo().toString())
				.replaceAll("#submiter#", submitter)
			);
	}
}
