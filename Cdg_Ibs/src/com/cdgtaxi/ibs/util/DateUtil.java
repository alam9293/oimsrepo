package com.cdgtaxi.ibs.util;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String GLOBAL_DATE_FORMAT = "dd/MM/yyyy";
	public static final String GLOBAL_DATE_TIME_MINUTE_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String GLOBAL_CNII_SEQ_FORMAT = "ddMMyyyy";
	public static final String GLOBAL_EXPIRY_DATE_FORMAT = "MM/yy";
	//public static final String INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT = "MMyy";
	public static final String INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT = "yyMM";
	public static final String GLOBAL_TIME_FORMAT = "HH:mm:ss";
	public static final String REPORT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String TRIPS_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String LAST_UPDATED_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String EMBOSS_FILE_DATE_FORMAT = "yyyyMMddhhmmss";
	public static final String FMS_TRIPS_DATE_FORMAT = "dd/MM/yyyy HH:mm";
	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public static java.sql.Date getSqlDateForNullComparison(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(1800, 1, 1, 0, 0, 0);
		return new java.sql.Date(calendar.getTimeInMillis());
	}
	public static java.util.Date getUtilDateForNullComparison(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(1800, 1, 1, 0, 0, 0);
		return new java.util.Date(calendar.getTimeInMillis());
	}
	public static Timestamp getCurrentTimestamp(){
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		ts.setNanos(0);
		return ts;
	}
	public static java.sql.Date getCurrentDate(){
		return new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	}
	public static java.util.Date getCurrentUtilDate(){
		return new java.util.Date(Calendar.getInstance().getTimeInMillis());
	}
	public static String convertTimestampToStr(Timestamp timestamp, String format){
		if(timestamp==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timestamp);
	}

	public static String convertDateToStrWithGDFormat(Date utilDate){
		return convertDateToStr(utilDate, DateUtil.GLOBAL_DATE_FORMAT);
	}

	public static String convertDateToStr(Date utilDate, String format){
		if(utilDate==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(utilDate);
	}

	public static Timestamp convertStrToTimestamp(String str, String format){
		if(str!=null && str.length()!=0){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try{
				return new Timestamp(sdf.parse(str).getTime());
			}catch(ParseException pe){
				logger.warn("Error in parsing date format",pe);
				return null;
			}
		}else{
			return null;
		}
	}

	public static Date convertStrToDate(String str, String format){
		if(str!=null && str.length()!=0 && !str.equalsIgnoreCase("-")){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try{
				return sdf.parse(str);
			}catch(ParseException pe){
				logger.warn("Error in parsing date format",pe);
				return null;
			}
		}else{
			return null;
		}
	}

	public static Timestamp convertDateToTimestamp(java.util.Date date){
		if(date==null){
			return null;
		}else{
			return new Timestamp(date.getTime());
		}
	}

	public static Timestamp convertDateToTimestamp(java.sql.Date date){
		if(date==null){
			return null;
		}else{
			return new Timestamp(date.getTime());
		}
	}

	public static String convertDateToStr(java.sql.Date sqlDate, String format){
		if(sqlDate==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(sqlDate);
	}

	public static String convertUtilDateToStr(Date utilDate, String format){
		if(utilDate==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(utilDate);
	}
	public static java.util.Date convertSqlDateToUtilDate(java.sql.Date date){
		if(date==null) return null;
		return new java.util.Date(date.getTime());
	}

	public static java.sql.Date convertUtilDateToSqlDate(java.util.Date date){
		if(date==null) return null;
		return new java.sql.Date(date.getTime());
	}

	/**
	 * To retrieve current system date.
	 * @return Date in YYYYMMDD String format
	 */
	public static String getYearMonthDay(){
		Calendar calendar = Calendar.getInstance();
		String year = (""+calendar.get(Calendar.YEAR));
		String month = ""+(calendar.get(Calendar.MONTH)+1);
		String day = ""+calendar.get(Calendar.DAY_OF_MONTH);

		if(month.length()==1) month = "0"+month;
		if(day.length()==1) day = "0"+day;

		return year+month+day;
	}
	public static Calendar getRewardsCutoffCalendar(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}
	public static Calendar getRewardsExpiryDt(Calendar rewardsCutoffCalendar, Integer gracePeriod){
		rewardsCutoffCalendar.add(Calendar.MONTH, gracePeriod);
		return rewardsCutoffCalendar;
	}
	public static java.sql.Date convertTo0000Hours(Calendar calendar){
		Calendar cloneCalendar = (Calendar)calendar.clone();
		cloneCalendar.set(Calendar.AM_PM, Calendar.AM);
		cloneCalendar.set(Calendar.HOUR, 0);
		cloneCalendar.set(Calendar.MINUTE, 0);
		cloneCalendar.set(Calendar.SECOND, 0);
		cloneCalendar.set(Calendar.MILLISECOND, 0);

		return new java.sql.Date(cloneCalendar.getTimeInMillis());
	}

	public static java.sql.Date convertTo2359Hours(Calendar calendar){
		Calendar cloneCalendar = (Calendar)calendar.clone();
		cloneCalendar.set(Calendar.AM_PM, Calendar.PM);
		cloneCalendar.set(Calendar.HOUR, 11);
		cloneCalendar.set(Calendar.MINUTE, 59);
		cloneCalendar.set(Calendar.SECOND, 59);
		cloneCalendar.set(Calendar.MILLISECOND, 999);
		return new java.sql.Date(cloneCalendar.getTimeInMillis());
	}

	public static java.sql.Date convertDateTo2359Hours(Date tempDate){
		if(tempDate!=null){
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(tempDate);
			return convertTo2359Hours(calendar);
		}
		else
			return null;
	}

	public static java.sql.Date convertDateTo0000Hours(Date tempDate){
		if(tempDate!=null){
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(tempDate);
			return convertTo0000Hours(calendar);
		}
		else
			return null;
	}

	/**
	 * To retrive the last day of the month by mm-yyyy
	 *
	 */

	public static java.sql.Date getLastDateOfMonth(Date currentMonthYear){
		if(currentMonthYear!=null){
			Date returnDate=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentMonthYear);
			int lastDate = calendar.getActualMaximum(Calendar.DATE);
			calendar.set(Calendar.DATE, lastDate);
			returnDate=calendar.getTime();
			return new java.sql.Date(returnDate.getTime());
		}
		else return null;
	}

	public static Date getLastUtilDateOfMonth(Date currentMonthYear){
		if(currentMonthYear!=null){
			Date returnDate=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentMonthYear);
			int lastDate = calendar.getActualMaximum(Calendar.DATE);
			calendar.set(Calendar.DATE, lastDate);
			returnDate=calendar.getTime();
			return returnDate;
		}
		else return null;
	}
	public static java.sql.Date getFirstDateOfMonth(Date currentMonthYear){
		if(currentMonthYear!=null){
			Date returnDate=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentMonthYear);
			int lastDate = calendar.getActualMinimum(Calendar.DATE);
			calendar.set(Calendar.DATE, lastDate);
			returnDate=calendar.getTime();
			return new java.sql.Date(returnDate.getTime());
		}
		else return null;
	}
	public static Date getFirstUtilDateOfMonth(Date currentMonthYear){
		if(currentMonthYear!=null){
			Date returnDate=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentMonthYear);
			int firstDate = calendar.getActualMinimum(Calendar.DATE);
			calendar.set(Calendar.DATE, firstDate);
			returnDate=calendar.getTime();
			return returnDate;
		}
		else return null;
	}

	//To retrieve the current date
	public static String getStrCurrentDate(){

		Date currentDate=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(GLOBAL_DATE_FORMAT);
		return (String)sdf.format(currentDate);
	}

	//To retrieve the current date
	public static String getStrCurrentDate(String format){

		Date currentDate=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return (String)sdf.format(currentDate);
	}

	public static boolean isToday(Date date){
		if(convertDateToStr(date, GLOBAL_DATE_FORMAT).equals(convertDateToStr(getCurrentDate(), GLOBAL_DATE_FORMAT))){
			return true;
		}
		return false;
	}
	public static Date addMonthsToDate(int months, Date startDate){
		Date returnDate=null;
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.MONTH,months);
		returnDate=c.getTime();
		return returnDate;
	}
	public static Date addDaysToDate(int days, Date startDate){
		Date returnDate=null;
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, days);
		returnDate=c.getTime();
		return returnDate;
	}

	public static int getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH)+1;
	}

	public static java.sql.Date convertTimestampToSQLDate(Timestamp timestamp)
	{
		if(timestamp==null){
			return null;
		}else{
			return new java.sql.Date(timestamp.getTime());
		}
	}

	public static java.util.Date convertTimestampToUtilDate(Timestamp timestamp)
	{
		if(timestamp==null){
			return null;
		}else{
			return new java.util.Date(timestamp.getTime());
		}
	}

	public static Timestamp convertTimestampTo0000Hours(Timestamp timestamp){
		if(timestamp!=null){
			Calendar calendar =Calendar.getInstance();
			calendar.setTimeInMillis(timestamp.getTime());
			calendar.set(Calendar.AM_PM, Calendar.AM);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return new Timestamp(calendar.getTimeInMillis());
		}
		else
			return null;
	}

	public static Timestamp convertTimestampTo2359Hours(Timestamp timestamp){
		if(timestamp!=null){
			Calendar calendar =Calendar.getInstance();
			calendar.setTimeInMillis(timestamp.getTime());
			calendar.set(Calendar.AM_PM, Calendar.PM);
			calendar.set(Calendar.HOUR, 11);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return new Timestamp(calendar.getTimeInMillis());
		}
		else
			return null;
	}

	public static String getDayInStr(int day){
		if(day == 1 || day == 21 || day == 31)
			return day+"st";
		else if(day == 2 || day == 22)
			return day+"nd";
		else if(day == 3 || day == 23)
			return day+"rd";
		else
			return day+"th";
	}



	public static Date getPrevDate(long time, int i) {

		return new Date(time - (i * 1000 * 60 * 60 * 24));
	}
}