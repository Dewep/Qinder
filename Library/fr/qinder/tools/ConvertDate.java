package fr.qinder.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertDate {

	/*
	 * Format date :
	 * http://developer.android.com/reference/java/text/SimpleDateFormat.html
	 */

	public static SimpleDateFormat formatFR(String format) {
		return new SimpleDateFormat(format, Locale.FRANCE);
	}

	public static SimpleDateFormat formatUS(String format) {
		return new SimpleDateFormat(format, Locale.US);
	}

	public static String convert(String date, SimpleDateFormat in, SimpleDateFormat out) {
		try {
			return out.format(in.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String convert(String date, SimpleDateFormat out) {
		return convert(date, formatFR("yyyy-MM-dd HH:mm:ss"), out);
	}

	public static String convert(String date, String in, String out) {
		return convert(date, formatFR(in), formatFR(out));
	}

	public static String convert(String date, String out) {
		return convert(date, formatFR("yyyy-MM-dd HH:mm:ss"), formatFR(out));
	}

	public static String getDay(String date) {
		return convert(date, "EEE dd MMM");
	}

	public static String getTime(String date) {
		return convert(date, "kk:mm");
	}
}
