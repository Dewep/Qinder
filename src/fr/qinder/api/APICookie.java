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
public class APICookie {

	private static volatile APICookie instance = null;
	private CookieManager _cookiemanager;

    private APICookie() {
		_cookiemanager = new CookieManager();
		CookieHandler.setDefault(_cookiemanager);
    }

    public final static APICookie getInstance() {
        if (APICookie.instance == null) {
           synchronized(APICookie.class) {
             if (APICookie.instance == null) {
            	 APICookie.instance = new APICookie();
             }
           }
        }
        return APICookie.instance;
    }

	public final static void add(String host, String key, String value, String path) {
		HttpCookie cookie = new HttpCookie(key, value);
		cookie.setPath(path);
		cookie.setDomain(host);
		try {
			APICookie.getInstance()._cookiemanager.getCookieStore().add(new URI(host), cookie);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public final static void add(String host, String key, String value) {
		APICookie.add(host, key, value, "/");
	}

	public final static List<HttpCookie> get(String host) {
		try {
			return APICookie.getInstance()._cookiemanager.getCookieStore().get(new URI(host));
		} catch (URISyntaxException e) {
			// e.printStackTrace();
		}
		return null;
	}

	public final static void remove(String host) {
		try {
			URI uri = new URI(host);
			for (HttpCookie cookie : APICookie.getInstance()._cookiemanager.getCookieStore().get(uri)) {
				APICookie.getInstance()._cookiemanager.getCookieStore().remove(uri, cookie);
			}
		} catch (URISyntaxException e) {
			// e.printStackTrace();
		}
	}

	public final static void removeAll() {
		APICookie.getInstance()._cookiemanager.getCookieStore().removeAll();
	}
}
