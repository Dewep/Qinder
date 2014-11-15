package fr.qinder.api;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public abstract class APIRequest {
	public String url;
	public ArrayList<NameValuePair> headers = new ArrayList<NameValuePair>();
	public ArrayList<NameValuePair> gets = new ArrayList<NameValuePair>();
	public ArrayList<NameValuePair> posts = new ArrayList<NameValuePair>();
	public APIResponse response = null;

	public APIRequest(String _url) {
		url = _url;
	}

	public void preExecute(APIResponse response) {
	}

	public void postExecute(APIResponse response) {
	}

	public abstract void onResult(APIResponse response);

	public Boolean isCached() {
		return true;
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

