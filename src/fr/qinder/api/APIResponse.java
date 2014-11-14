package fr.qinder.api;

import javax.net.ssl.HttpsURLConnection;

public class APIResponse {
	public APIRequest request;
	public HttpsURLConnection response;
	public String data;
	public int code;
}
