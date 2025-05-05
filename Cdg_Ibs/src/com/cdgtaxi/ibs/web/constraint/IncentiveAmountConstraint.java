package com.cdgtaxi.ibs.web.constraint;

public class IncentiveAmountConstraint extends NumberRangeConstraint{

	public IncentiveAmountConstraint()
	{
		super(0, 99.99);
	}
}
