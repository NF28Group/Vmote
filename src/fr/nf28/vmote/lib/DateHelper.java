package fr.nf28.vmote.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final Locale LOCALE = Locale.US;
	
	public static String dateToString(Date date){
		if(date == null){
			return "";
		}
		else {
			return new SimpleDateFormat(DATE_FORMAT, LOCALE).format(date);
		}
	}
	
	public static Date stringToDate(String date_str){
		Date date = null;
		try {
			date = new SimpleDateFormat(DATE_FORMAT, LOCALE).parse(date_str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
}
