package fr.qinder.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON {
	public static JSONObject getObject(String str) {
		JSONObject obj = null;
		if (str == null) {
			return obj;
		}
		try {
			obj = new JSONObject(str);
		} catch (JSONException e) {
			// e.printStackTrace();
		}
		return obj;
	}

	public static String parse(String obj, String path, String defValue) {
		return parse(getObject(obj), path, defValue);
	}

	public static int parse(String obj, String path, int defValue) {
		return parse(getObject(obj), path, defValue);
	}

	public static double parse(String obj, String path, double defValue) {
		return parse(getObject(obj), path, defValue);
	}

	public static String parse(JSONObject obj, String path, String defValue) {
		if (obj == null)
			return defValue;
		int index = path.indexOf(">");
		try {
			if (index == -1) {
				String res = obj.getString(path);
				if (res == null)
					return (defValue);
				return res;
			}
			return parse(obj.getJSONObject(path.substring(0, index)), path.substring(index + 1), defValue);
		} catch (JSONException e) {
			// e.printStackTrace();
		}
		return defValue;
	}

	public static int parse(JSONObject obj, String path, int defValue) {
		if (obj == null)
			return defValue;
		int index = path.indexOf(">");
		try {
			if (index == -1) {
				Integer res = obj.getInt(path);
				if (res == null)
					return (defValue);
				return res;
			}
			return parse(obj.getJSONObject(path.substring(0, index)), path.substring(index + 1), defValue);
		} catch (JSONException e) {
			// e.printStackTrace();
		}
		return defValue;
	}

	public static double parse(JSONObject obj, String path, double defValue) {
		if (obj == null)
			return defValue;
		int index = path.indexOf(">");
		try {
			if (index == -1) {
				Double res = obj.getDouble(path);
				if (res == null)
					return (defValue);
				return res;
			}
			return parse(obj.getJSONObject(path.substring(0, index)), path.substring(index + 1), defValue);
		} catch (JSONException e) {
			// e.printStackTrace();
		}
		return defValue;
	}
}
