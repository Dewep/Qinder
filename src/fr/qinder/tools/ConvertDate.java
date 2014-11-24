/*
 * Copyright (C) 2014 Maigret Aurelien / Colin Julien
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.qinder.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Convert a date from one format to an another.
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class ConvertDate {

    /**
     * Constructor, not called, because this is an Utility Class.
     */
    private ConvertDate() {
    }

    /**
     * Constructs a new SimpleDateFormat using the specified non-localized
     * pattern and the DateFormatSymbols and Calendar for the locale
     * Locale.FRANCE.
     * http://developer.android.com/reference/java/text/SimpleDateFormat.html
     * 
     * @param format
     *            String of the format
     * @return New instance of SimpleDateFormat(format, Locale.FRANCE)
     * @see SimpleDateFormat
     */
    public static SimpleDateFormat formatFR(String format) {
        return new SimpleDateFormat(format, Locale.FRANCE);
    }

    /**
     * Constructs a new SimpleDateFormat using the specified non-localized
     * pattern and the DateFormatSymbols and Calendar for the locale Locale.US.
     * http://developer.android.com/reference/java/text/SimpleDateFormat.html
     * 
     * @param format
     *            String of the format
     * @return New instance of SimpleDateFormat(format, Locale.US)
     * @see SimpleDateFormat
     */
    public static SimpleDateFormat formatUS(String format) {
        return new SimpleDateFormat(format, Locale.US);
    }

    /**
     * Convert a date with an input format to an output format.
     * 
     * @param date
     *            String of the date
     * @param in
     *            Instance of SimpleDateFormat for the input format
     * @param out
     *            Instance of SimpleDateFormat for the output format
     * @return String of the date in the output format, or date if the parameter
     *         is not in the good format
     */
    public static String convert(String date, SimpleDateFormat in, SimpleDateFormat out) {
        try {
            return out.format(in.parse(date));
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * Convert a date in the format US ("yyyy-MM-dd HH:mm:ss") to the output
     * format.
     * 
     * @param date
     *            String of the date
     * @param out
     *            Instance of SimpleDateFormat for the output format
     * @return String of the date in the output format, or date if the parameter
     *         is not in the format US
     */
    public static String convert(String date, SimpleDateFormat out) {
        return convert(date, formatFR("yyyy-MM-dd HH:mm:ss"), out);
    }

    /**
     * Convert a date with an input format string to an output format string.
     * 
     * @param date
     *            String of the date
     * @param in
     *            String for the input format
     * @param out
     *            String for the output format
     * @return String of the date in the output format, or date if the parameter
     *         is not in the good format
     */
    public static String convert(String date, String in, String out) {
        return convert(date, formatFR(in), formatFR(out));
    }

    /**
     * Convert a date in the format US ("yyyy-MM-dd HH:mm:ss") to the output
     * format string.
     * 
     * @param date
     *            String of the date
     * @param out
     *            String for the output format
     * @return String of the date in the output format, or date if the parameter
     *         is not in the format US
     */
    public static String convert(String date, String out) {
        return convert(date, formatFR("yyyy-MM-dd HH:mm:ss"), formatFR(out));
    }

    /**
     * Convert a date in the format US ("yyyy-MM-dd HH:mm:ss") to the format day
     * ("EEE dd MMM")
     * 
     * @param date
     *            String of the date
     * @return String of the date in the format day, or date if the parameter is
     *         not in the format US
     */
    public static String getDay(String date) {
        return convert(date, "EEE dd MMM");
    }

    /**
     * Convert a date in the format US ("yyyy-MM-dd HH:mm:ss") to the format
     * time ("kk:mm")
     * 
     * @param date
     *            String of the date
     * @return String of the date in the format time, or date if the parameter
     *         is not in the format US
     */
    public static String getTime(String date) {
        return convert(date, "kk:mm");
    }

}
