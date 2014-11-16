package fr.qinder.api;

import javax.net.ssl.HttpsURLConnection;

public class APIResponse {
	public HttpsURLConnection response;
	public String data;
	public int code;
	public Boolean isCache;
}
