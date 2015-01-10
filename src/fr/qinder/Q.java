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

package fr.qinder;

import android.app.Activity;

/**
 * This class stores a reference to an activity to access more easier at certain
 * information (shared preferences, string, ...).
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class Q {

    private static volatile Q sInstance = null;
    private Activity mActivity = null;

    /**
     * @param activity
     *            Reference to an activity that will be use later
     */
    private Q(Activity activity) {
        mActivity = activity;
    }

    /**
     * @return Reference the activity saved
     */
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * @param activity
     *            Reference to the activity for the first time. Can be null.
     * @return Reference to the instance of this class
     * @throws NullPointerException
     *             If activity is null and Q not initialized yet
     */
    public static Q init(Activity activity) throws IllegalArgumentException {
        if (Q.sInstance == null) {
            synchronized (Q.class) {
                if (Q.sInstance == null) {
                    if (activity == null) {
                        throw new IllegalArgumentException();
                    }
                    Q.sInstance = new Q(activity);
                }
            }
        }
        return Q.sInstance;
    }

    /**
     * @return Reference to the activity saved
     */
    public static Activity get() {
        return Q.init(null).getActivity();
    }

    /**
     * @param id
     *            Identifier of the string that you want
     * @return String corresponding to this identifier
     */
    public static String getString(int id) {
        return get().getResources().getString(id);
    }

    /**
     * @param id
     *            Identifier of the string that you want
     * @param formatArgs
     *            The format arguments that will be used for substitution.
     * @return String corresponding to this identifier
     */
    public static String getString(int id, Object... formatArgs) {
        return get().getResources().getString(id, formatArgs);
    }

}
