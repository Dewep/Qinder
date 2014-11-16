package fr.qinder.pref;

import fr.qinder.Q;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	public static boolean exist(String namespace, String key) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref == null ? false : pref.contains(key);
	}

	/* String */
	public static String get(String namespace, String key, String defValue) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref == null ? defValue : pref.getString(key, defValue);
	}

	/* Int */
	public static int get(String namespace, String key, int defValue) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref == null ? defValue : pref.getInt(key, defValue);
	}

	/* Boolean */
	public static boolean get(String namespace, String key, boolean defValue) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		return pref == null ? defValue : pref.getBoolean(key, defValue);
	}

	/* String */
	public static void set(String namespace, String key, String value) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null)
			return;
		SharedPreferences.Editor editor = pref.edit();
		if (editor == null)
			return;
		editor.putString(key, value);
		editor.commit();
	}

	/* Int */
	public static void set(String namespace, String key, int value) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null)
			return;
		SharedPreferences.Editor editor = pref.edit();
		if (editor == null)
			return;
		editor.putInt(key, value);
		editor.commit();
	}

	/* Boolean */
	public static void set(String namespace, String key, boolean value) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null)
			return;
		SharedPreferences.Editor editor = pref.edit();
		if (editor == null)
			return;
		editor.putBoolean(key, value);
		editor.commit();
	}
}
