package com.DateFormating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	// read a date string and parse/convert to a date
	public static Date parseDate(String strDate) throws ParseException {
		
		Date theDate = formatter.parse(strDate);
		
		return theDate;
	}

	// read a date and format/convert to a string
	public static String formateDate(Date theDate) {

		String result = null;

		if (theDate != null) {
			
			result = formatter.format(theDate);
		}
		
		return result;
	}

}
