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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Utility class for edit an URL.
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class URL {

	/**
	 * Constructor, not called, because this is an Utility Class.
	 */
	private URL() {
	}

	/**
	 * Add a parameter to an URL.
	 * 
	 * @param url
	 *            String of the URL
	 * @param key
	 *            String of the key
	 * @param value
	 *            String of the value for this key
	 * @return The URL with this additional parameter
	 */
	public static String addParameter(String url, String key, String value) {
		int indexQuery = url.indexOf('?');
		int indexHash = url.indexOf('#');
		try {
			String newParam;
			if (indexQuery == -1) {
				newParam = "?";
			} else {
				newParam = "&";
			}
			newParam += URLEncoder.encode(key, "UTF-8");
			newParam += '=';
			newParam += URLEncoder.encode(value, "UTF-8");
			if (indexHash == -1) {
				return url + newParam;
			} else {
				return url.substring(0, indexHash) + newParam + url.substring(indexHash);
			}
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

}
