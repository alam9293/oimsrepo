package com.cdgtaxi.ibs.report.ui;

import java.util.Properties;

import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;

public interface CSVFormat {
	public abstract StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException;
}