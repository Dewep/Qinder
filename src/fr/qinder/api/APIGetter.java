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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public class APIGetter extends AsyncTask<APIRequest, APIRequest, Void> {
	private HttpClient _httpClient;
	private ProgressDialog _progressDialog;

	public APIGetter(Activity dialog) {
		APICookie.getInstance();
		_httpClient = new DefaultHttpClient();
		_progressDialog = null;
		if (dialog != null) {
			_progressDialog = new ProgressDialog(dialog);
			_progressDialog.setMessage("Chargement...");
			_progressDialog.setCanceledOnTouchOutside(false);
			_progressDialog.show();
			_progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					_httpClient.getConnectionManager().shutdown();
				}
			});
		}
	}

	protected HttpsURLConnection post(String s_url, APIRequest request)
	{
		try {
			URL url = new URL(s_url);
			HttpsURLConnection url_connection = (HttpsURLConnection) url.openConnection();
			url_connection.setRequestMethod("GET");
			if (request.posts.size() != 0) {
				url_connection.setRequestMethod("POST");
				url_connection.setDoInput(true);
				url_connection.setDoOutput(true);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(request.posts);
				OutputStream post = url_connection.getOutputStream();
				 entity.writeTo(post);
				 post.flush();
			}
			url_connection.connect();
			return url_connection;
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(APIRequest... requests) {
		super.onProgressUpdate(requests);
		for (int i = 0; i < requests.length; i++) {
			requests[i].onResult();
		}
	}

	protected InputStream getInputStream(HttpsURLConnection request) throws IOException {
		if (request.getResponseCode() == 200) {
			return request.getInputStream();
		}
		return request.getErrorStream();
	}

	@Override
	protected Void doInBackground(APIRequest... requests) {
		for (int i = 0; i < requests.length; i++) {
			requests[i].response =  new APIResponse();
			APIResponse response = requests[i].response;
			response.code = 0;
			response.data = null;
			response.response = null;
			requests[i].preExecute();
			for (int j = 0; j < requests[i].gets.size(); j++) {
				requests[i].url = fr.qinder.tools.URL.addParameter(requests[i].url, requests[i].gets.get(j).getName(), requests[i].gets.get(j).getValue());
			}
			if (requests[i].isCached() && requests[i].posts.size() == 0 && APICache.getInstance().getCache(requests[i].url) != null) {
				response.response = APICache.getInstance().getCacheResponse(requests[i].url);
				response.data = APICache.getInstance().getCacheData(requests[i].url);
				response.code = 200;
				response.isCache = true;
			} else {
				response.response = post(requests[i].url, requests[0]);
				response.isCache = false;
				try {
					if (response.response != null) {
						response.code = response.response.getResponseCode();
					}
				} catch (IOException e) {
					// e.printStackTrace();
				}
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(response.response), "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;) {
						builder.append(line).append("\n");
					}
					response.data = builder.toString();
					if (requests[i].isCached() && requests[i].posts.size() == 0 && response.code == 200) {
						APICache.getInstance().addCache(requests[i].url, response.response, response.data);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			requests[i].postExecute();
			publishProgress(requests[i]);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void response) {
		super.onPostExecute(response);
		try {
			if (_progressDialog != null && _progressDialog.isShowing()) {
				_progressDialog.cancel();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}