package com.cdgtaxi.ibs.web.constraint;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Decimalbox;

public class ReportAmountConstraint implements Constraint {
	private int whole, decimal;
	public ReportAmountConstraint(int whole, int decimal){
		this.whole = whole;
		this.decimal = decimal;
	}
	public void validate(Component arg0, Object arg1) throws WrongValueException {
		if(!(arg0 instanceof Decimalbox)){
			throw new ClassCastException("Error: This constraint only works for decimalbox");
		}
		if(arg1!=null){
			BigDecimal amount = (BigDecimal)arg1;
			if(amount.compareTo(new BigDecimal(Math.pow(10, whole)-Math.pow(10, -decimal)))>0){
				String display = "";
				for(int i=0;i<whole;i++){
					display += "9";
				}
				display += ".";
				for(int i=0;i<decimal;i++){
					display += "9";
				}
				throw new WrongValueException(arg0, "* Must be lesser than "+display);
			}else if(amount.compareTo(new BigDecimal(0))<=0){
				throw new WrongValueException(arg0, "* Must be greater than 0");
			}
		}
	}
}