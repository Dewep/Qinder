package fr.qinder.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URL {

	public static String addParameter(String url, String key, String value) {
		int index_query = url.indexOf('?');
		int index_hash = url.indexOf('#');
		try {
			String new_param = (index_query == -1 ? "?" : "&") + URLEncoder.encode(key, "UTF-8") + '=' + URLEncoder.encode(value, "UTF-8");
			return index_hash == -1 ? (url + new_param) : (url.substring(0, index_hash) + new_param + url.substring(index_hash));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

}
