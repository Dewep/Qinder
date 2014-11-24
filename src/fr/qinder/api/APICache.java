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

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.Bitmap;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
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
		return ((System.currentTimeMillis() / 1000) - (image == null ? 2 : 5) * 60 < time_add);
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
		//map.put(url, new APICacheStock(image));
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
