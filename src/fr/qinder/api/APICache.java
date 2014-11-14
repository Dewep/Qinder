package fr.qinder.api;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.Bitmap;

class APICacheStock {
	private HttpsURLConnection response;
	private String data;
	private Bitmap image;
	private Long time_add;

	public APICacheStock(HttpsURLConnection _response, String _data) {
		response = _response;
		data = _data;
		image = null;
		time_add = System.currentTimeMillis() / 1000;
	}

	public APICacheStock(Bitmap _image) {
		response = null;
		data = null;
		image = _image;
		time_add = System.currentTimeMillis() / 1000;
	}

	public boolean isValid() {
		return ((System.currentTimeMillis() / 1000) - (image == null ? 1 : 5) * 60 < time_add);
	}

	public HttpsURLConnection getResponse() {
		return response;
	}

	public Bitmap getImage() {
		return image;
	}

	public String getData() {
		return data;
	}
}

public class APICache {

	private Map<String, APICacheStock> map = new HashMap<String, APICacheStock>();

	public void addCache(String url, HttpsURLConnection response, String data) {
		map.put(url, new APICacheStock(response, data));
	}
	public void addCache(String url, Bitmap image) {
		map.put(url, new APICacheStock(image));
	}

	public APICacheStock getCache(String url) {
		APICacheStock stock = map.get(url);
		if (stock == null)
			return null;
		if (!stock.isValid())
		{
			map.remove(url);
			return null;
		}
		return stock;
	}

	public HttpsURLConnection getCacheResponse(String url) {
		APICacheStock stock = getCache(url);
		if (stock == null)
			return null;
		return stock.getResponse();
	}

	public String getCacheData(String url) {
		APICacheStock stock = getCache(url);
		if (stock == null)
			return null;
		return stock.getData();
	}

	public Bitmap getCacheImage(String url) {
		APICacheStock stock = getCache(url);
		if (stock == null)
			return null;
		return stock.getImage();
	}

	private static APICache Singleton = null;

	public static APICache getInstance() {
		if (Singleton == null)
			Singleton = new APICache();
		return Singleton;
	}

}
