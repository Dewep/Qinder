package fr.qinder.api;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class APIRequest {
	private String identifier;
	private String path;
	private Boolean cached;
	ArrayList<NameValuePair> headers = new ArrayList<NameValuePair>();
	ArrayList<NameValuePair> gets = new ArrayList<NameValuePair>();
	ArrayList<NameValuePair> posts = new ArrayList<NameValuePair>();

	// @TODO: Il faudra penser a gerer les cookies, ca peut etre utile

	public APIRequest(String _identifier, String _path, Boolean _cached) {
		identifier = _identifier;
		path = _path;
		cached = _cached;
	}

	public APIRequest(String _identifier, String _path) {
		identifier = _identifier;
		path = _path;
		cached = true;
	}

	public String getId() {
		return identifier;
	}

	public String getPath() {
		return path;
	}

	public Boolean isCached() {
		return cached;
	}

	public void addGet(String key, String value) {
		gets.add(new BasicNameValuePair(key, value));
	}

	public void addPost(String key, String value) {
		posts.add(new BasicNameValuePair(key, value));
	}

	public void addHeader(String key, String value) {
		headers.add(new BasicNameValuePair(key, value));
	}
}
