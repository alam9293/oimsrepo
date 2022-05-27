package com.cdgtaxi.ibs.web.constraint;

public class GreaterThanZeroCustomConstraint extends NumberLessOrGreaterConstraint{

	public GreaterThanZeroCustomConstraint(int lessAmount, boolean isLessOrEqual) {
		
		super(false, lessAmount, isLessOrEqual, 0, false);
	}
	
	public GreaterThanZeroCustomConstraint(double lessAmount, boolean isLessOrEqual) {
		super(false, lessAmount, isLessOrEqual, 0, false);
	}
	
	public GreaterThanZeroCustomConstraint(boolean isMandantory, int lessAmount, boolean isLessOrEqual) {
		super(isMandantory, lessAmount, isLessOrEqual, 0, false);
	}

	public GreaterThanZeroCustomConstraint(boolean isMandantory, double lessAmount, boolean isLessOrEqual) {
		super(isMandantory, lessAmount, isLessOrEqual, 0, false);
	}
}
