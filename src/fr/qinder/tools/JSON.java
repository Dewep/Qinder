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
        JSONObject obj;
        try {
            if (str != null) {
                obj = new JSONObject(str);
            } else {
                obj = null;
            }
        } catch (JSONException e) {
            obj = null;
        }
        return obj;
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
        T res = defValue;
        if (defValue instanceof Double) {
            res = (T) ((Double) obj.getDouble(key));
        } else if (defValue instanceof Integer) {
            res = (T) ((Integer) obj.getInt(key));
        } else if (defValue instanceof String) {
            res = (T) obj.getString(key);
        }
        return res;
    }

    /**
     * @param obj
     *            JSONObject for getting the value
     * @param path
     *            String of the path
     * @param defValue
     *            Default value if key not exists
     * @return The value if path and key exist, defValue else
     * @throws JSONException
     */
    private static <T> T getValueRec(JSONObject obj, String path, T defValue) throws JSONException {
        int index = path.indexOf('>');
        T res;

        if (index == -1) {
            res = getValue(obj, path, defValue);
        } else {
            JSONObject objNext = obj.getJSONObject(path.substring(0, index));
            res = getValueRec(objNext, path.substring(index + 1), defValue);
        }
        return res;
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
        T res = defValue;
        try {
            if (obj != null) {
                res = getValueRec(obj, path, defValue);
            }
        } catch (JSONException e) {
            res = defValue;
        }
        return res;
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
