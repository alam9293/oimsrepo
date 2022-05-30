package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.exception.FieldMethodUnavailableException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.exception.InvalidFieldTypeException;
import com.cdgtaxi.ibs.common.exception.InvalidFieldTypeValueException;
import com.cdgtaxi.ibs.common.exception.InvalidValueException;
import com.cdgtaxi.ibs.common.exception.MandatoryArgumentException;

public class Field {
	/**
	 * Denotes the field type to be LONG.<br>
	 * Field data is a whole number without decimal places.<br>
	 * It is always RIGHT justified with leading ZEROs on the left.<br>
	 * E.g. 000010000 = 10,000 (Ten Thousand)
	 */
	public static final int LONG_LEADING_ZEROES = 1;

	/**
	 * Denotes the field type to be DECIMAL.<br>
	 * Field data is a number with 2 decimal places but with no decimal point.<br>
	 * It is always RIGHT justified with leading ZEROs on the left.<br>
	 * E.g. 000010000 = 100.00 (One Hundred)
	 */
	public static final int DECIMAL_LEADING_ZEROES = 2;

	/**
	 * Denotes the field type to be VARCHAR.<br>
	 * Field data is a free text.
	 * It is always LEFT justified with trailing spaces on the right.<br>
	 */
	public static final int VARCHAR = 3;

	/**
	 * Denotes the field type to be DATE.<br>
	 * Field data is a date with format as YYYYMMDD.<br>
	 * E.g. 20110310 = 10th March 2011
	 */
	public static final int DATE = 4;

	/**
	 * Denotes the field type to be DATE with TIME.<br>
	 * Field data is a date with format as dd/MM/yyyy kk:mm:ss.<br>
	 */
	public static final int DATE_TIME = 5;

	/**
	 * Denotes the field type to be DATE.<br>
	 * Field data is a date with format as dd/MM/yyyy.<br>
	 */
	public static final int DATE_dd_MM_yyyy = 6;
	
	/**
	 * Denotes the field type to be INTEGER.<br>
	 * Field data is a whole number without decimal places.<br>
	 * It is always LEFT justified without leading ZEROs and having right filling spaces.<br>
	 * E.g. "11          " = 11 (Eleven)
	 */
	public static final int INTEGER = 7;


	/**
	 * Denotes the field type to be DATE.<br>
	 * Field data is a date with format as yyyymmdd.<br>
	 */
	public static final int DATE_yyyymmdd = 8;
	

	/**
	 * Denotes the field type to be DATE.<br>
	 * Field data is a date with format as HH:MM:SS.<br>
	 */
	
	public static final int DATE_HHMMSS = 9;
	
	/**
	 * Denotes the field type to be DATE.<br>
	 * Field data is a date with format as HHMMSS.<br>
	 */
	
	public static final int DATE_HHMMSS2 = 10;
	
	/**
	 * Denotes the field type to be DATE.<br>
	 * Field data is a date with format as dd-MMM-yyyy.<br>
	 */
	public static final int DATE_dd_MMM_yyyy = 11;
	

	/**
	 * Denotes the field type to be AMOUNT.<br>
	 * Field data is a NUMBER with format as #.##. <br>
	 */
	public static final int AMOUNT = 12;
	
	
	private String name;
	private int type;
	private int length;
	private String data;
	private Collection<String> expectedValue;

	public Field(String name, int type, int length) throws FieldException {

		if (!(type == LONG_LEADING_ZEROES || type == DECIMAL_LEADING_ZEROES || type == VARCHAR || type == DATE || type == DATE_TIME || type == DATE_dd_MM_yyyy || type == INTEGER || type == DATE_yyyymmdd || type == DATE_HHMMSS || type == DATE_HHMMSS2 ||  type == DATE_dd_MMM_yyyy || type == AMOUNT))
			throw new InvalidFieldTypeException();

		if (name == null)
			throw new MandatoryArgumentException("Name");

		this.name = name;
		this.type = type;
		this.length = length;
	}

	public Field(String name, int type, int length, Collection<String> expectedValues) throws FieldException {

		if (!(type == LONG_LEADING_ZEROES || type == DECIMAL_LEADING_ZEROES || type == VARCHAR || type == DATE || type == DATE_TIME || type == DATE_dd_MM_yyyy || type == INTEGER || type == DATE_yyyymmdd || type == DATE_HHMMSS || type == DATE_HHMMSS2 || type == DATE_dd_MMM_yyyy || type == AMOUNT))
			throw new InvalidFieldTypeException();

		if (name == null)
			throw new MandatoryArgumentException("Name");

		if (expectedValues != null) {
			for (String value : expectedValues) {
				if (value.length() != length)
					throw new IncorrectLengthException(name, value.length() + "", length + "");
			}
		}

		this.name = name;
		this.type = type;
		this.length = length;
		this.expectedValue = expectedValues;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getData() {
		return data;
	}

	public Long getDataInLong() throws FieldException {
		if (type != LONG_LEADING_ZEROES)
			throw new FieldMethodUnavailableException(name);

		return new Long(data);
	}

	public BigDecimal getDataInBigDecimal() throws FieldException {
		if (type != DECIMAL_LEADING_ZEROES)
			throw new FieldMethodUnavailableException(name);

		return new BigDecimal(data).movePointLeft(2);
	}

	public java.util.Date getDateInDate() throws FieldException {
		if (!(type == DATE || type == DATE_TIME || type == DATE_dd_MM_yyyy))
			throw new FieldMethodUnavailableException(name);

		if (type == DATE)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				return sdf.parse(data);
			} catch (ParseException pe) {
				throw new FieldException(pe.getMessage());
			}
		else if (type == DATE_TIME)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
				return sdf.parse(data);
			} catch (ParseException pe) {
				throw new FieldException(pe.getMessage());
			}
		else if (type == DATE_dd_MM_yyyy)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.parse(data);
			} catch (ParseException pe) {
				throw new FieldException(pe.getMessage());
			}
		else if (type == DATE_dd_MMM_yyyy)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				return sdf.parse(data);
			} catch (ParseException pe) {
				throw new FieldException(pe.getMessage());
			}

		return null;
	}
	
	public Integer getDataInInteger() throws FieldException {
		if (type != INTEGER)
			throw new FieldMethodUnavailableException(name);

		return new Integer(data);
	}

	public void setData(String data) throws FieldException {

		if (data == null)
			throw new MandatoryArgumentException(name, "Data");

		if (data.length() != this.length)
			throw new IncorrectLengthException(name, data.length() + "", length + "");

		if (this.expectedValue != null)
			if (!this.expectedValue.contains(data))
				throw new InvalidValueException(name, data, this.expectedValue);

		if (type == LONG_LEADING_ZEROES) {
			try {
				Long.parseLong(data);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DECIMAL_LEADING_ZEROES) {
			try {
				new BigDecimal(data);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DATE) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DATE_TIME) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DATE_dd_MM_yyyy) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == INTEGER) {
			try {
				new Integer(data.trim());
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DATE_yyyymmdd) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DATE_HHMMSS) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DATE_HHMMSS2) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}	
		} else if (type == DATE_dd_MMM_yyyy) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				sdf.parse(data);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		} else if (type == DECIMAL_LEADING_ZEROES) {
			try {
				NumberFormat nf = new DecimalFormat("##.##");
				data = nf.format(new BigDecimal(data));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				throw new InvalidFieldTypeValueException(name);
			}
		}


		this.data = data.trim();
	}

	public String getName() {
		return name;
	}
}
