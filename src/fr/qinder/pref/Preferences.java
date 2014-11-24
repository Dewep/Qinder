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

package fr.qinder.pref;

import fr.qinder.Q;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Access to the Shared Preferences more earlier. This class required the class
 * fr.qinder.Q initialized.
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class Preferences {

	/**
	 * Constructor, not called, because this is an Utility Class.
	 */
	private Preferences() {
	}

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @return True if the information exists in the NameSpace, False else.
	 */
	public static boolean exist(String namespace, String key) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null) {
			return false;
		}
		return pref.contains(key);
	}

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @param defValue
	 *            Default value if the key not exists in the NameSpace.
	 * @return String of the value or defValue.
	 */
	public static String get(String namespace, String key, String defValue) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null) {
			return defValue;
		}
		return pref.getString(key, defValue);
	}

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @param defValue
	 *            Default value if the key not exists in the NameSpace.
	 * @return Int of the value or defValue.
	 */
	public static int get(String namespace, String key, int defValue) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null) {
			return defValue;
		}
		return pref.getInt(key, defValue);
	}

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @param defValue
	 *            Default value if the key not exists in the NameSpace.
	 * @return Boolean of the value or defValue.
	 */
	public static boolean get(String namespace, String key, boolean defValue) {
		SharedPreferences pref = Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
		if (pref == null) {
			return defValue;
		}
		return pref.getBoolean(key, defValue);
	}

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @param value
	 *            Value of the key in the NameSpace to saved.
	 */
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

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @param value
	 *            Value of the key in the NameSpace to saved.
	 */
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

	/**
	 * @param namespace
	 *            NameSpace where the information is stocked.
	 * @param key
	 *            Key of the information.
	 * @param value
	 *            Value of the key in the NameSpace to saved.
	 */
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
