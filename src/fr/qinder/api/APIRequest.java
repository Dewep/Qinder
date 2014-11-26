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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public abstract class APIRequest {
    private String url;
    private List<NameValuePair> headers = new ArrayList<NameValuePair>();
    private List<NameValuePair> gets = new ArrayList<NameValuePair>();
    private List<NameValuePair> posts = new ArrayList<NameValuePair>();
    protected APIResponse response = null;

    public APIRequest(String urlPath) {
        url = urlPath;
    }

    public void preExecute() {
    }

    public void postExecute() {
    }

    public abstract void onResult();

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

	public List<NameValuePair> getHeaders() {
		return headers;
	}

	public List<NameValuePair> getGets() {
		return gets;
	}

	public List<NameValuePair> getPosts() {
		return posts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public APIResponse getResponse() {
		return response;
	}

	public void setResponse(APIResponse response) {
		this.response = response;
	}
}
