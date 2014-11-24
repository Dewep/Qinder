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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class for parse JSON.
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class JSON {

	/**
	 * Constructor, not called, because this is an Utility Class.
	 */
	private JSON() {
	}

	/**
	 * Convert string to a JSONObject.
	 * 
	 * @param str
	 *            String of the JSON to parse
	 * @return JSONObject of the string or null if not parsable
	 */
	public static JSONObject getObject(String str) {
		if (str == null) {
			return null;
		}
		try {
			return new JSONObject(str);
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Get value of the key from a JSONObject. If the key not exists in the
	 * JSONObject, defValue is return.
	 * 
	 * @param obj
	 *            JSONObject for getting the value
	 * @param key
	 *            String of the key
	 * @param defValue
	 *            Default value if key not exists
	 * @return The value if key exist, defValue else
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getValue(JSONObject obj, String key, T defValue) throws JSONException {
		if (defValue instanceof Double) {
			return (T) ((Double) obj.getDouble(key));
		} else if (defValue instanceof Integer) {
			return (T) ((Integer) obj.getInt(key));
		} else if (defValue instanceof String) {
			return (T) obj.getString(key);
		}
		return defValue;
	}

	/**
	 * Get value of the path from a JSONObject. If the key not exists in the
	 * JSONObject, defValue is return. The path is a key, which can to be in
	 * sub-objects. Each sub-object must be separated by the char '>'. If you
	 * want get the key 'id' in the object 'user', which is in the object
	 * 'dashboard', the path is 'dashboard>user>id'.
	 * 
	 * @param obj
	 *            JSONObject for getting the value
	 * @param path
	 *            String of the path
	 * @param defValue
	 *            Default value if key not exists
	 * @return The value if path and key exist, defValue else
	 */
	public static <T> T parse(JSONObject obj, String path, T defValue) {
		if (obj == null) {
			return defValue;
		}
		int index = path.indexOf('>');
		try {
			if (index == -1) {
				return getValue(obj, path, defValue);
			}
			obj = obj.getJSONObject(path.substring(0, index));
			return parse(obj, path.substring(index + 1), defValue);
		} catch (JSONException e) {
			return defValue;
		}
	}

	/**
	 * Get value of the path from a JSON string. If the key not exists in the
	 * JSONObject, defValue is return. The path is a key, which can to be in
	 * sub-objects. Each sub-object must be separated by the char '>'. If you
	 * want get the key 'id' in the object 'user', which is in the object
	 * 'dashboard', the path is 'dashboard>user>id'.
	 * 
	 * @param str
	 *            String of the JSON to parse
	 * @param path
	 *            String of the path
	 * @param defValue
	 *            Default value if key not exists
	 * @return The value if path and key exist, defValue else
	 */
	public static <T> T parse(String str, String path, T defValue) {
		return parse(getObject(str), path, defValue);
	}
}
