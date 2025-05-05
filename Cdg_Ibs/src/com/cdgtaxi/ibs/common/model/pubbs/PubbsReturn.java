package com.cdgtaxi.ibs.common.model.pubbs;

import java.util.LinkedList;
import java.util.List;

public class PubbsReturn {

	public PubbsControlHeader pubbsControlHeader;
	public List<PubbsInvoiceHeader> pubbsInvoiceHeaders = new LinkedList<PubbsInvoiceHeader>();
	public PubbsReturnTrailer pubbsReturnTrailer;

	public String toString() {
		String returnString = "";
		returnString += pubbsControlHeader.toString();
		returnString += "\n";
		for (PubbsInvoiceHeader pubbsInvoiceHeader : pubbsInvoiceHeaders) {
			returnString += pubbsInvoiceHeader.toString();
			returnString += "\n";
		}
		returnString += pubbsReturnTrailer.toString();
		return returnString;
	}
}
