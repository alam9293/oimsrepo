package com.cdgtaxi.ibs.web.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;

@SuppressWarnings("serial")
public class Datebox extends org.zkoss.zul.Datebox {
	private static final Calendar epoch;

	static {
		epoch = Calendar.getInstance();
		epoch.set(1999, 10, 29, 0, 0, 0); // magic date
	}

	public Datebox() {
		addEventHandler("onBlur", new EventHandler(ZScript.parseContent(""), ConditionImpl.getInstance(null, null)));
	}

	@Override
	protected Object coerceFromString(String value) throws WrongValueException {
		Date date = (Date) super.coerceFromString(value);

		if (date == null) {
			return null;
		}

		// A hack to automatically convert an input like 14122009 to 14/12/2009
		// Has to be done this way because ZK uses javascript to "intelligently"
		// but erroneously guess the date. This code "corrects" this error to
		// calculate the correct date from the erroneous date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.YEAR) > 3000) {
			epoch.set(1999, 10, 29, 0, 0, 0);
			long dayDiff = (date.getTime() - epoch.getTimeInMillis()) / 86400000; // 24 * 60 * 60 * 1000
			String dateString = String.format("%1$8d", dayDiff).replace(" ", "0");
			try {
				String df = getFormat().replace("/", "");
				return new SimpleDateFormat(df).parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}
}
