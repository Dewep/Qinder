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
    private HttpsURLConnection mResponse;
    private String nData;
    private Bitmap mImage;
    private Long mTimeAdd;

    private static final int TO_SECONDS = 1000;
    private static final int TIME_CACHE_IMAGE = 120;
    private static final int TIME_CACHE_DATA = 300;

    public APICacheStock(HttpsURLConnection response, String data) {
        mResponse = response;
        nData = data;
        mImage = null;
        mTimeAdd = System.currentTimeMillis() / TO_SECONDS;
    }

    public APICacheStock(Bitmap image) {
        mResponse = null;
        nData = null;
        mImage = image;
        mTimeAdd = System.currentTimeMillis() / TO_SECONDS;
    }

    public boolean isValid() {
        return ((System.currentTimeMillis() / TO_SECONDS) - (mImage == null ? TIME_CACHE_IMAGE : TIME_CACHE_DATA) < mTimeAdd);
    }

    public HttpsURLConnection getResponse() {
        return mResponse;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public String getData() {
        return nData;
    }
}

public class APICache {

    private Map<String, APICacheStock> mMap = new HashMap<String, APICacheStock>();
    private static APICache sSingleton = null;

    public void addCache(String url, HttpsURLConnection response, String data) {
        mMap.put(url, new APICacheStock(response, data));
    }

    public void addCache(String url, Bitmap image) {
        mMap.put(url, new APICacheStock(image));
    }

    public APICacheStock getCache(String url) {
        APICacheStock stock = mMap.get(url);
        if (stock == null) {
            return null;
        }
        if (!stock.isValid()) {
            mMap.remove(url);
            return null;
        }
        return stock;
    }

    public HttpsURLConnection getCacheResponse(String url) {
        APICacheStock stock = getCache(url);
        if (stock == null) {
            return null;
        }
        return stock.getResponse();
    }

    public String getCacheData(String url) {
        APICacheStock stock = getCache(url);
        if (stock == null) {
            return null;
        }
        return stock.getData();
    }

    public Bitmap getCacheImage(String url) {
        APICacheStock stock = getCache(url);
        if (stock == null) {
            return null;
        }
        return stock.getImage();
    }

    public static APICache getInstance() {
        if (sSingleton == null) {
            sSingleton = new APICache();
        }
        return sSingleton;
    }

}
