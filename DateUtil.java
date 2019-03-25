package org.dms.DMS.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.exceptions.DMSException;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class DateUtil {
	
	private static final Logger LOGGER = Logger.getLogger(DateUtil.class);

	static Calendar cal = Calendar.getInstance();

	public static int getDate(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	public static int getMonth(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int getYear(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static String setDateFormat(String datetime) {
		if (StringUtils.isBlank(datetime)) {
			return StringUtils.EMPTY;
		} else {
			return setDateFormat(datetime, "yyyy-MM-dd", "dd-MM-yyyy");
		}
	}
	
	public static Date getDate(XMLGregorianCalendar xmlCal){
		if (xmlCal==null)
			return null;
		GregorianCalendar cal = xmlCal.toGregorianCalendar();
		return cal.getTime();	
	}
	
	/**
	 * This method is used to change date format
	 * 
	 * @param datetime
	 * @param fromDateFormat
	 * @param toDateFormat
	 * @return
	 */
	public static String setDateFormat(String datetime, String fromDateFormat, String toDateFormat) {
		Date dt = new Date();
		SimpleDateFormat sdf;
		String newDate = null;

		if (datetime == null || "".equals(datetime)) {
			return null;
		}

		try {
			sdf = new SimpleDateFormat(fromDateFormat);
			// String to Date
			dt = (Date) sdf.parse(datetime);

			// Convert to new format and parse to string
			sdf = new SimpleDateFormat(toDateFormat); 
			newDate = sdf.format(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * @return
	 */
	public static XMLGregorianCalendar getCurrDate() {
		Date billDate = new Date();

		XMLGregorianCalendar cal = new XMLGregorianCalendarImpl();
		cal.setDay(DateUtil.getDate(billDate));
		cal.setMonth(DateUtil.getMonth(billDate));
		cal.setYear(DateUtil.getYear(billDate));
		return cal;
	}

	/**
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendarDate(String date, String dateFormat) {
		Date billDate = null;

		SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormat);

		try {
			billDate = dtFormat.parse(date);
		} catch (ParseException e) {
			billDate = new Date();
		}
		XMLGregorianCalendar cal = new XMLGregorianCalendarImpl();
		cal.setDay(getDate(billDate));
		cal.setMonth(getMonth(billDate));
		cal.setYear(getYear(billDate));

		return cal;
	}

	/**
	 * This method will convert String in dd-mm-yyyy format to date. 
	 * If incorrect input string is received then return null
	 * @param dtStr
	 * @return Date
	 */
	public static Date parseDate(String dtStr){

		Date dt;
		try {
			if(dtStr == null) {
				return null;
			}
			dt = Constants.DATE_FORMATTER.parse(dtStr);
		} catch (ParseException e) {
			return null;
		}

		return dt;
	}

	/**
	 * This method will convert String to date time format. 
	 * If incorrect input string is received then return null
	 * @param dtStr
	 * @return Date
	 */
	public static Date parseDate(String dtStr, String pattern){

		Date dt;
		if (StringUtils.isNotBlank(dtStr)) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				dt = formatter.parse(dtStr);
			} catch (ParseException e) {
				return null;
			}
		} else {
			return null;
		}

		return dt;
	}

	/**
	 * This method will convert String to date time format. 
	 * If incorrect input string is received then return null
	 * @param dtStr
	 * @return Date
	 */
	public static Date parseDateTime(String dtStr){

		Date dt;
		try {
			dt = Constants.DATE_TIME_SEC_FORMATTER.parse(dtStr);
		} catch (ParseException e) {
			return null;
		}

		return dt;
	}

	/**
	 * This method will convert Date to String dd-mm-yyyy format. 
	 * If date is null then return null
	 * @param dt
	 * @return String
	 */
	public static String formatDate(Date dt){

		if (dt == null) {
			return null;
		}
		String dtStr;
		dtStr = Constants.DATE_FORMATTER.format(dt);

		return dtStr;
	}

	/**
	 * This method will convert Date to String dd-mm-yyyy format. 
	 * If date is null then return null
	 * @param dt
	 * @return String
	 */
	public static String formatDate(Date dt, String pattern){

		if (dt == null) {
			return null;
		}
		String dtStr;
		SimpleDateFormat dtFormat = new SimpleDateFormat(pattern);
		dtStr = dtFormat.format(dt);

		return dtStr;
	}

	/**
	 * This method will convert String in dd-mm-yyyy HH:mm format to date. 
	 * If incorrect input string is received then return null
	 * @param dtStr
	 * @return Date
	 */
	public static Date parseDateAndTimeInHrMin(String dtStr) {

		Date dt = null;
		try {
			dt = Constants.DATE_FORMAT_PATTERN_DD_MM_YYYY_HH_MM_FORMATTER.parse(dtStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;


	}

	/**
	 * @param Date date
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar getCalDtFromDate(Date date) {
		XMLGregorianCalendar cal = new XMLGregorianCalendarImpl();
		cal.setDay(DateUtil.getDate(date));
		cal.setMonth(DateUtil.getMonth(date));
		cal.setYear(DateUtil.getYear(date));
		return cal;
	}

	/**
	 * This method will convert Date to String dd-mm-yyyy HH:mm format. 
	 * If date is null then return null
	 * @param dt
	 * @return String
	 */
	public static String formatDateAndTimeInHrMin(Date dt) {

		if (dt == null) {
			return null;
		}
		String dtStr;
		dtStr = Constants.DATE_FORMAT_PATTERN_DD_MM_YYYY_HH_MM_FORMATTER.format(dt);

		return dtStr;
	}

	/**
	 * This method will convert Date to String dd-mm-yyyy HH:mm:ss format.
	 * @param String dt
	 * @param String flag
	 * @return String
	 * @throws DMSException 
	 */
	public static Date formatDateAndTimeInHrMinSec(String dt, String flag) throws DMSException {
		Date dtStr = null;

		try {
			if (dt != null) {

				if (flag == Constants.START) {
					dtStr = Constants.DATE_TIME_SEC_FORMATTER_ddmmyyyyHHmmss.parse(dt + Constants.START_TIME);
				} else {
					dtStr = Constants.DATE_TIME_SEC_FORMATTER_ddmmyyyyHHmmss.parse(dt + Constants.END_TIME);
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
			throw new DMSException("An error occurred while processing your request, please contact administrator.",
					ErrorCodes.GENERAL_EXCEPTION_KEY);
		}

		return dtStr;
	}


	/**
	 * Method to format Date Time In HrMinSec and returns string
	 * @param String dt
	 * @param String flag
	 * @return String
	 */
	public static String formatDateTimeInHrMinSec(String dt, String flag) {
		String dtStr = null;

		if (dt != null) {

			if (flag.equals(Constants.START)) {
				dtStr = dt + " " + Constants.START_TIME;
			} else {
				dtStr = dt + " " + Constants.END_TIME;
			}
		}

		return dtStr;
	}
	
	public static Date parseDate(XMLGregorianCalendar calDate) {
		
		if(calDate == null) {
			return null;
		} 
		
		Date dt = calDate.toGregorianCalendar().getTime();
		return dt;
	}

	public static Date removeTimeFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// Set time fields to zero
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
	
/*	*//**
	 * @param String date
	 * @return Calendar
	 *//*
	public static Calendar genXMLCalendar(String date) {

		if (StringUtils.isEmpty(date)) {
			return null;
		}

		Date dt = parseDate(date);
		Calendar cal = new XmlCalendar();
		cal.set(Calendar.YEAR, DateUtil.getYear(dt));
		cal.set(Calendar.MONTH, DateUtil.getMonth(dt) - 1);
		cal.set(Calendar.DATE, DateUtil.getDate(dt));
		return cal;
	}
*/
	/**
	 * @param String date
	 * @return long
	 */
	public static long calculateDateDifInHours(String fromDt, String toDt) {
		long diffInHour = 0L;
		long diff = 0L;
		Date d1 = null;
		Date d2 = null;
		if ((toDt == "" || toDt == null) || (fromDt == "" || fromDt == null)) {
			diffInHour = 0;
		} else {
			d1 = DateUtil.parseDate(fromDt);
			d2 = DateUtil.parseDate(toDt);
			diff = d2.getTime() - d1.getTime();
			diffInHour = diff / (60 * 60 * 1000);
		}
		return diffInHour;
	}
	
	/**
	 * @Method to get Date difference in hh:mm format
	 * @param String date
	 * @return String
	 */
	public static String calculateDateDifInHoursAndMins(String fromDt, String toDt) {
		long diff = 0L;
		Date d1 = null;
		Date d2 = null;
		long second = 1000l;
		long minute = 60l * second;
		long hour = 60l * minute;
		String diffInHourMin = StringUtils.EMPTY;

		if (StringUtils.isNotBlank(toDt) && StringUtils.isNotBlank(fromDt)) {
			d1 = DateUtil.parseDateAndTimeInHrMin(fromDt);
			d2 = DateUtil.parseDateAndTimeInHrMin(toDt);

			diff = d2.getTime() - d1.getTime();
			diffInHourMin = String.format("%02d", diff / hour) + Constants.COLON + String.format("%02d", (diff % hour) / minute);

		} else {
			diffInHourMin = "00:00";
		}
		return diffInHourMin;
	}
	
	/**
	 * @method will convert String to date time format. If incorrect input string is received then return null
	 * @param dtStr
	 * @return Date
	 */
	public static Date parseDateStrictFormat(String dtStr, String pattern){

		Date dt;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			formatter.setLenient(false);
			dt = formatter.parse(dtStr);
		} catch (ParseException e) {
			return null;
		}
		
		return dt;
		
	}
	
	/**
	 * @method to compare Date
	 * @param String dt1
	 * @param String dt2
	 * @param String pattern
	 * @return Integer
	 */
	public static Integer compareDate(String dt1, String dt2, String pattern) {
		Date date1 = DateUtil.parseDate(dt1, pattern);
		Date date2 = DateUtil.parseDate(dt2, pattern);
		
		return date1.compareTo(date2);
	}
	
	/**
	 * @method to compare Time using joda time
	 * @param LocalTime time1
	 * @param LocalTime time2
	 * @return Integer
	 */
	public static Integer compareTime(LocalTime time1, LocalTime time2) {
		Integer compareVal = time1.compareTo(time2);
		return compareVal;
	}

	/**
	 * @param Date fromDt
	 * @param Date toDt
	 * @return String
	 */
	public static String calculateDateDifInHoursAndMinsAndSec(Date fromDt, Date toDt) {
		String diffInHourMinSec = StringUtils.EMPTY;
		if (toDt != null && fromDt != null) {

			long diff = 0L;
			Date d1 = fromDt;
			Date d2 = toDt;
			diff = d2.getTime() - d1.getTime();
			long second = diff / 1000 % 60;
			long minute = diff / (60 * 1000) % 60;
			long hour = diff / (60 * 60 * 1000) % 24;

			diffInHourMinSec = hour + Constants.COLON + minute + Constants.COLON + second;
		} else {
			diffInHourMinSec = "00:00:00";
		}

		return diffInHourMinSec;
	}

	/**
	 * @method to convert Date to GMT String in given pattern format . If date is null then return null
	 * @param Date dt
	 * @param String pattern
	 * @return String
	 */
	public static String formatDateGMT(Date dt, String pattern) {
		
		if (dt == null) {
			return null;
		}
		
	    DateFormat gmtFormat = new SimpleDateFormat(pattern);
	    gmtFormat.setTimeZone(TimeZone.getTimeZone(Constants.GMT_TIME_ZONE));
	    
	    return gmtFormat.format(dt);
	}

	/**
	 * @method for calculating day difference between two days
	 * @param fromDt
	 * @param toDt
	 * @return
	 */
	public static Long calculateDateDifInDays(String fromDt, String toDt) {
		Long diffInDays = null;
		try {
			if (StringUtils.isNotBlank(toDt) && StringUtils.isNotBlank(fromDt)) {
				diffInDays = ((DateUtil.parseDate(toDt)).getTime() - (DateUtil.parseDate(fromDt)).getTime()) / 86400000; // diff / (24 * 60 * 60 * 1000)
			}
		} catch (Exception e) {
			LOGGER.error("Error in calculateDateDifInDays returning difference as null. " + Constants.EXCEPTION, e);
			diffInDays = null;
		}
		return diffInDays;
	}

	/**
	 * @Method used for calculate time between two days excluding weekends 
	 * @param String fromDt
	 * @param String toDt
	 * @return String
	 */
	public static String calculateTimeAsPerCompWorkingHrs(String fromDt, String toDt, String officeStart, String officeClosed, boolean isWeekendsOff) {

		String[] startTime = officeStart.split(":");
		String[] endTime = officeClosed.split(":");
		int officeStartHrs = Integer.parseInt(startTime[0]);
		int officeClosedHrs = Integer.parseInt(endTime[0]);
		int officeStartMin = 0;
		int officeClosedMin = 0;
		if (startTime[1] != null) {
			officeStartMin = Integer.parseInt(startTime[1]);
		}
		if (endTime[1] != null) {
			officeClosedMin = Integer.parseInt(endTime[1]);
		}
		int diff = getTotalTimeDIffInDay(officeStartHrs, officeClosedHrs, officeStartMin, officeClosedMin);

		int h = diff/60;
		int m = diff % 60;
		int totMinsInBusinessHrs = (h) * 60;
		totMinsInBusinessHrs = totMinsInBusinessHrs + m; 

		return dateDifference(fromDt, toDt, totMinsInBusinessHrs, officeStartHrs, officeClosedHrs, officeStartMin, officeClosedMin, isWeekendsOff);
	}

	/**
	 * @param start
	 * @param end
	 * @param totMinsInBusinessHrs
	 * @param officeStartHrs
	 * @param officeClosedHrs
	 * @param officeStartMin
	 * @param officeClosedMin
	 * @param isWeekendsOff
	 * @return
	 */
	private static String dateDifference(String start, String end, int totMinsInBusinessHrs, int officeStartHrs, int officeClosedHrs,
			int officeStartMin, int officeClosedMin, boolean isWeekendsOff) {

		Date startDate = parseDate(start, Constants.DATE_TIME_FORMAT_PATTERN_ddMMyyyyHHmm);
		Date endDate = parseDate(end, Constants.DATE_TIME_FORMAT_PATTERN_ddMMyyyyHHmm);
		Calendar startDay = convertDateToCalendar(startDate, officeStartHrs, officeClosedHrs, officeStartMin, officeClosedMin, isWeekendsOff);
		Calendar tempDay = (Calendar) startDay.clone();
		Calendar endDay = convertDateToCalendar(endDate, officeStartHrs, officeClosedHrs, officeStartMin, officeClosedMin, isWeekendsOff);
		int workDays = 0;
		int startDayDifference = 0;
		int endDayDifference = 0;
		int hours = 0;
		int minsRemainder = 0;

		if (!(startDay.get(Calendar.DAY_OF_YEAR) == endDay.get(Calendar.DAY_OF_YEAR) && startDay.get(Calendar.YEAR) == endDay.get(Calendar.YEAR))) {
			if (startDay.get(Calendar.HOUR_OF_DAY) == officeStartHrs && startDay.get(Calendar.MINUTE) == officeStartMin
					&& endDay.get(Calendar.HOUR_OF_DAY) == officeClosedHrs && endDay.get(Calendar.MINUTE) == officeClosedMin) {
				workDays = 1;
			}
			do {
				tempDay.add(Calendar.DAY_OF_MONTH, 1);
				if ((!isWeekendsOff || (tempDay.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && tempDay.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY))
						&& tempDay.before(endDay)) {
					workDays++;
				}
			} while (tempDay.getTimeInMillis() <= endDay.getTimeInMillis());
		} else if ((startDay.get(Calendar.DAY_OF_YEAR) == endDay.get(Calendar.DAY_OF_YEAR) && startDay.get(Calendar.YEAR) == endDay.get(Calendar.YEAR))
				&& startDay.get(Calendar.HOUR_OF_DAY) == officeStartHrs && startDay.get(Calendar.MINUTE) == officeStartMin
				&& endDay.get(Calendar.HOUR_OF_DAY) == officeClosedHrs && endDay.get(Calendar.MINUTE) == officeClosedMin) {
			int h = totMinsInBusinessHrs / 60;
			int m = totMinsInBusinessHrs % 60;
			
			String totalHrs = String.valueOf(h);
			String totalMin = String.valueOf(m);
			if (m < 10) {
				totalMin = "0" + totalMin;
			}
			if (h < 10) {
				totalHrs = "0" + totalHrs;
			}
			return totalHrs + ":" + totalMin;
			
		}

		startDayDifference = hourDifferenceInMinutesOfStartDay(startDay, officeClosedHrs, officeClosedMin);
		endDayDifference = hourDifferenceInMinutesOfEndDay(endDay, officeStartHrs, officeStartMin);

		minsRemainder = (startDayDifference + endDayDifference) % totMinsInBusinessHrs;
		// workDays = workDays + ((startDayDifference + endDayDifference) / totMinsInBusinessHrs);
		hours = minsRemainder / 60;
		minsRemainder = minsRemainder % 60;
		int difference = getTotalTimeDIffInDay(officeStartHrs, officeClosedHrs, officeStartMin, officeClosedMin);

		int h = difference / 60;
		int m = difference % 60;
		int totHrs = (workDays * h) + hours;
		minsRemainder = minsRemainder + (workDays * m);
		if (minsRemainder >= 60) {
			h = minsRemainder / 60;
			m = minsRemainder % 60;
			totHrs = totHrs + h;
			minsRemainder = m;
		}

		String totalHrs = String.valueOf(totHrs);
		String totalMin = String.valueOf(minsRemainder);
		if (minsRemainder < 10) {
			totalMin = "0" + totalMin;
		}
		if (totHrs < 10) {
			totalHrs = "0" + totalHrs;
		}
		return totalHrs + ":" + totalMin;
	}

	/**
	 * @param officeStartHrs
	 * @param officeClosedHrs
	 * @param officeStartMin
	 * @param officeClosedMin
	 * @return
	 */
	private static int getTotalTimeDIffInDay(int officeStartHrs, int officeClosedHrs, int officeStartMin, int officeClosedMin) {

		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, officeStartHrs);
		cal1.set(Calendar.MINUTE, officeStartMin);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, officeClosedHrs);
		cal2.set(Calendar.MINUTE, officeClosedMin);
		long startMin = cal1.getTimeInMillis();
		long endMin = cal2.getTimeInMillis();
		int difference = (int) ((endMin - startMin) / 1000) / 60;

		return difference;
	}

	/**
	 * @param date
	 * @param officeStartHrs
	 * @param officeClosedHrs
	 * @param officeStartMin
	 * @param officeClosedMin
	 * @param isWeekendsOff
	 * @return
	 */
	private static Calendar convertDateToCalendar(Date date, int officeStartHrs, int officeClosedHrs, int officeStartMin, int officeClosedMin,
			boolean isWeekendsOff) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (isWeekendsOff && (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			calendar = handleActivityOnAfterWorkHoursOrWeekendOrHolidays(calendar, officeStartHrs, officeStartMin);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) >= officeClosedHrs && calendar.get(Calendar.MINUTE) >= officeClosedMin) {
			calendar.set(Calendar.HOUR_OF_DAY, officeClosedHrs);
			calendar.set(Calendar.MINUTE, officeClosedMin);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) > officeClosedHrs) {
			calendar.set(Calendar.HOUR_OF_DAY, officeClosedHrs);
			calendar.set(Calendar.MINUTE, officeClosedMin);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) <= officeStartHrs && calendar.get(Calendar.MINUTE) < officeStartMin) {
			calendar.set(Calendar.HOUR_OF_DAY, officeStartHrs);
			calendar.set(Calendar.MINUTE, officeStartMin);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) < officeStartHrs) {
			calendar.set(Calendar.HOUR_OF_DAY, officeStartHrs);
			calendar.set(Calendar.MINUTE, officeStartMin);
		}

		return calendar;
	}

	/**
	 * @param calendar
	 * @param officeStartHrs
	 * @param officeStartMin
	 * @return
	 */
	private static Calendar handleActivityOnAfterWorkHoursOrWeekendOrHolidays(Calendar calendar, int officeStartHrs, int officeStartMin) {
		do {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		} while (isHoliday(calendar));
		calendar.set(Calendar.HOUR_OF_DAY, officeStartHrs);
		calendar.set(Calendar.MINUTE, officeStartMin);
		return calendar;
	}

	/**
	 * @param calendar
	 * @return
	 */
	private static boolean isHoliday(Calendar calendar) {
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	/**
	 * @param startDay
	 * @param officeClosedHrs
	 * @param officeClosedMin
	 * @return
	 */
	private static int hourDifferenceInMinutesOfStartDay(Calendar startDay, int officeClosedHrs, int officeClosedMin) {
		long starttimestamp = startDay.getTimeInMillis();
		startDay.set(Calendar.HOUR_OF_DAY, officeClosedHrs);
		startDay.set(Calendar.MINUTE, officeClosedMin);
		long startDayOfficeCloseTimestamp = startDay.getTimeInMillis();
		int difference = (int) ((startDayOfficeCloseTimestamp - starttimestamp) / 1000) / 60;
		if (difference < 0) {
			difference = 0;
		}
		return difference;
	}

	/**
	 * @param endDay
	 * @param officeStartHrs
	 * @param officeStartMin
	 * @return
	 */
	private static int hourDifferenceInMinutesOfEndDay(Calendar endDay, int officeStartHrs, int officeStartMin) {
		long endTimestamp = endDay.getTimeInMillis();
		endDay.set(Calendar.HOUR_OF_DAY, officeStartHrs);
		endDay.set(Calendar.MINUTE, officeStartMin);
		long endDayOfficeStartTimestamp = endDay.getTimeInMillis();
		int difference = (int) ((endTimestamp - endDayOfficeStartTimestamp) / 1000) / 60;
		if (difference < 0) {
			difference = 0;
		}
		return difference;
	}
	
	public static Date addDate(Date dt, int days) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, days);
		return dt = c.getTime();
	}

}