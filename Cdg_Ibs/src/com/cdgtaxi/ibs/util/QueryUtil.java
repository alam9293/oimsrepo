package com.cdgtaxi.ibs.util;

import java.util.Collection;

public class QueryUtil {

	/**
	 * Returns "%" if the string is empty or %str% otherwise.
	 * 
	 * @param str
	 * @return
	 */
	public static String formatContains(String str) {
		return (str == null || str.equals("")) ? "%" : "%" + str + "%";
	}

	/**
	 * Returns "%" if the string is empty or str% otherwise.
	 * 
	 * @param str
	 * @return
	 */
	public static String formatStartsWith(String str) {
		return (str == null || str.equals("")) ? "%" : str + "%";
	}

	/**
	 * Returns "%" if the string is empty or %str otherwise.
	 * 
	 * @param str
	 * @return
	 */
	public static String formatEndsWith(String str) {
		return (str == null || str.equals("")) ? "%" : "%" + str;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object object) {
		if (object == null)
			return true;
		if (object instanceof String && ((String) object).trim().equals(""))
			return true;
		if (object instanceof Collection && ((Collection) object).isEmpty()) {
			return true;
		}
		return false;
	}

}
