package fr.qinder.pref;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	public static boolean exist(Context context, String namespace, String key) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref.contains(key);
	}

	/* String */
	public static String get(Context context, String namespace, String key, String defValue) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref.getString(key, defValue);
	}

	/* Int */
	public static int get(Context context, String namespace, String key, int defValue) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref.getInt(key, defValue);
	}

	/* Boolean */
	public static boolean get(Context context, String namespace, String key, boolean defValue) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref.getBoolean(key, defValue);
	}

	/* String */
	public static void set(Context context, String namespace, String key, String value) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/* Int */
	public static void set(Context context, String namespace, String key, int value) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/* Boolean */
	public static void set(Context context, String namespace, String key, boolean value) {
		SharedPreferences pref = context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
