package com.cdgtaxi.ibs.common.model.govteinv;

import java.util.LinkedList;
import java.util.List;

public class GovtEInvReturn {

	public ControlHeader controlHeader;
	public List<InvoiceHeader> invoiceHeaders = new LinkedList<InvoiceHeader>();
	public Trailer trailer;

	public String toString() {
		String returnString = "";
		returnString += controlHeader.toString();
		returnString += "\n";
		for (InvoiceHeader invoiceHeader : invoiceHeaders) {
			returnString += invoiceHeader.toString();
			returnString += "\n";
		}
		returnString += trailer.toString();
		return returnString;
	}
}
