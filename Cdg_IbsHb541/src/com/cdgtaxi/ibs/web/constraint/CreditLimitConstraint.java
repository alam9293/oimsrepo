package com.cdgtaxi.ibs.web.constraint;


public class CreditLimitConstraint extends NumberLessOrGreaterConstraint{

	public CreditLimitConstraint()
	{
		super(true, 9999999.99, false, 0 ,false);
	}
	
}
