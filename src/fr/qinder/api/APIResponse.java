package fr.qinder.api;

import org.apache.http.HttpResponse;

public class APIResponse {
	public APIRequest request;
	public HttpResponse response;
	public String data;
	public int code;
}
