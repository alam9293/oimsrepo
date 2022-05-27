package com.cdgtaxi.ibs.web.constraint;


public class NoteAmountConstraint extends NumberLessOrGreaterConstraint{

	public NoteAmountConstraint()
	{
		super(true, 9999.99, false, 0 ,true);
	}

	
}
