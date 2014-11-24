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

package fr.qinder.api;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class APICookie {

    private static volatile APICookie sInstance = null;
    private CookieManager mCookieManager;

    private APICookie() {
        mCookieManager = new CookieManager();
        CookieHandler.setDefault(mCookieManager);
    }

    public static final APICookie getInstance() {
        if (APICookie.sInstance == null) {
            synchronized (APICookie.class) {
                if (APICookie.sInstance == null) {
                    APICookie.sInstance = new APICookie();
                }
            }
        }
        return APICookie.sInstance;
    }

    public static final void add(String host, String key, String value, String path) {
        HttpCookie cookie = new HttpCookie(key, value);
        cookie.setPath(path);
        cookie.setDomain(host);
        try {
            APICookie.getInstance().mCookieManager.getCookieStore().add(new URI(host), cookie);
        } catch (URISyntaxException e) {
        }
    }

    public static final void add(String host, String key, String value) {
        APICookie.add(host, key, value, "/");
    }

    public static final List<HttpCookie> get(String host) {
        try {
            return APICookie.getInstance().mCookieManager.getCookieStore().get(new URI(host));
        } catch (URISyntaxException e) {
        }
        return null;
    }

    public static final void remove(String host) {
        try {
            URI uri = new URI(host);
            for (HttpCookie cookie : APICookie.getInstance().mCookieManager.getCookieStore().get(uri)) {
                APICookie.getInstance().mCookieManager.getCookieStore().remove(uri, cookie);
            }
        } catch (URISyntaxException e) {
        }
    }

    public static final void removeAll() {
        APICookie.getInstance().mCookieManager.getCookieStore().removeAll();
    }
}
