package com.cdgtaxi.ibs.common.exception;

import java.math.BigDecimal;

import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class ExcessiveNoteAmountException extends Exception {
	private final String txnType;
	private final BigDecimal txnAmt;
	private final BigDecimal noteAmt;

	public ExcessiveNoteAmountException(String txnType, BigDecimal txnAmt, BigDecimal noteAmt) {
		this.txnType = txnType;
		this.txnAmt = txnAmt;
		this.noteAmt = noteAmt;
	}

	@Override
	public String getMessage() {

		if (txnAmt.signum() == 0) {
			return "Issuance of Credit Note not allowed as no previous transactions have been " +
			"made for the selected Transaction Type.";
		} else {
			return "The amount $" + StringUtil.bigDecimalToString(noteAmt, StringUtil.GLOBAL_DECIMAL_FORMAT) +
			" exceeds the total transacted amount of $" + StringUtil.bigDecimalToString(txnAmt, StringUtil.GLOBAL_DECIMAL_FORMAT) +
			" made for the selected Transaction Type, less any previously credited amount.";
		}
	}
}
