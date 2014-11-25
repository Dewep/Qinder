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

    private static SharedPreferences getSharedPreferences(String namespace) {
        return Q.get().getSharedPreferences(namespace, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(String namespace) {
        SharedPreferences.Editor editor = null;
        SharedPreferences pref = getSharedPreferences(namespace);
        if (pref != null) {
            editor = pref.edit();
        }
        return editor;
    }

    /**
     * @param namespace
     *            NameSpace where the information is stocked.
     * @param key
     *            Key of the information.
     * @return True if the information exists in the NameSpace, False else.
     */
    public static boolean exist(String namespace, String key) {
        boolean res;

        SharedPreferences pref = getSharedPreferences(namespace);
        if (pref == null) {
            res = false;
        } else {
            res = pref.contains(key);
        }
        return res;
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
        String res;

        SharedPreferences pref = getSharedPreferences(namespace);
        if (pref == null) {
            res = defValue;
        } else {
            res = pref.getString(key, defValue);
        }
        return res;
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
        int res;

        SharedPreferences pref = getSharedPreferences(namespace);
        if (pref == null) {
            res = defValue;
        } else {
            res = pref.getInt(key, defValue);
        }
        return res;
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
        boolean res;

        SharedPreferences pref = getSharedPreferences(namespace);
        if (pref == null) {
            res = defValue;
        } else {
            res = pref.getBoolean(key, defValue);
        }
        return res;
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
        SharedPreferences.Editor editor = getEditor(namespace);
        if (editor != null) {
            editor.putString(key, value);
            editor.commit();
        }
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
        SharedPreferences.Editor editor = getEditor(namespace);
        if (editor != null) {
            editor.putInt(key, value);
            editor.commit();
        }
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
        SharedPreferences.Editor editor = getEditor(namespace);
        if (editor != null) {
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

}
