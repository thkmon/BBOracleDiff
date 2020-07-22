package com.bb.dbdiff.util;

import java.util.Calendar;

public class DateUtil {

	public static String getTodayDateTime() {
		Calendar cal = Calendar.getInstance();
		StringBuffer today = new StringBuffer();
		today.append(String.format("%04d", cal.get(cal.YEAR)));
		today.append(String.format("%02d", cal.get(cal.MONTH) + 1));
		today.append(String.format("%02d", cal.get(cal.DAY_OF_MONTH)));

		today.append("_");

		today.append(String.format("%02d", cal.get(cal.HOUR_OF_DAY)));
		today.append(String.format("%02d", cal.get(cal.MINUTE)));
		today.append(String.format("%02d", cal.get(cal.SECOND)));
		return today.toString();
	}
}