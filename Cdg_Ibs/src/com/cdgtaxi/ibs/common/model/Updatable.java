package com.cdgtaxi.ibs.common.model;

import java.sql.Timestamp;

public interface Updatable {
	public void setUpdatedBy(String updatedBy);
	public void setUpdatedDt(Timestamp timestamp);
}
