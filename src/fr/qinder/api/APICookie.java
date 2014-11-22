package fr.qinder.api;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
