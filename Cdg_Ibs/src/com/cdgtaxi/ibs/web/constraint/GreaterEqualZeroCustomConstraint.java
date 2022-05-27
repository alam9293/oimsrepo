package com.cdgtaxi.ibs.web.constraint;


public class GreaterEqualZeroCustomConstraint extends NumberLessOrGreaterConstraint{

	public GreaterEqualZeroCustomConstraint(int lessAmount, boolean isLessOrEqual) {
		super(false, lessAmount, isLessOrEqual, 0, true);
	}

	public GreaterEqualZeroCustomConstraint(double lessAmount, boolean isLessOrEqual) {
		super(false, lessAmount, isLessOrEqual, 0, true);
	}
	
	public GreaterEqualZeroCustomConstraint(boolean isMandantory, int lessAmount, boolean isLessOrEqual) {
		super(isMandantory, lessAmount, isLessOrEqual, 0, true);
	}

	public GreaterEqualZeroCustomConstraint(boolean isMandantory, double lessAmount, boolean isLessOrEqual) {
		super(isMandantory, lessAmount, isLessOrEqual, 0, true);
	}
}
