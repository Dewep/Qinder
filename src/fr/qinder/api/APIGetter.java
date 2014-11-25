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

import org.apache.http.HttpStatus;
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
    private HttpClient mHttpClient;
    private ProgressDialog mProgressDialog;

    public APIGetter(Activity dialog) {
        APICookie.getInstance();
        mHttpClient = new DefaultHttpClient();
        mProgressDialog = null;
        if (dialog != null) {
            mProgressDialog = new ProgressDialog(dialog);
            mProgressDialog.setMessage("Chargement...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mHttpClient.getConnectionManager().shutdown();
                }
            });
        }
    }

    protected HttpsURLConnection post(String sUrl, APIRequest request) {
        HttpsURLConnection urlConnection;
        URL url;
        try {
            url = new URL(sUrl);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            if (request.posts.size() != 0) {
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(request.posts);
                OutputStream post = urlConnection.getOutputStream();
                entity.writeTo(post);
                post.flush();
            }
            urlConnection.connect();
        } catch (IOException e) {
            urlConnection = null;
        }
        return urlConnection;
    }

    @Override
    protected void onProgressUpdate(APIRequest... requests) {
        super.onProgressUpdate(requests);
        for (int i = 0; i < requests.length; i++) {
            requests[i].onResult();
        }
    }

    protected InputStream getInputStream(HttpsURLConnection request) throws IOException {
        if (request.getResponseCode() == HttpStatus.SC_OK) {
            return request.getInputStream();
        }
        return request.getErrorStream();
    }

    @Override
    protected Void doInBackground(APIRequest... requests) {
        for (int i = 0; i < requests.length; i++) {
            requests[i].response = new APIResponse();
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
                response.code = HttpStatus.SC_OK;
                response.isCache = true;
            } else {
                response.response = post(requests[i].url, requests[0]);
                response.isCache = false;
                try {
                    if (response.response != null) {
                        response.code = response.response.getResponseCode();
                    }
                } catch (IOException e) {
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(response.response), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        builder.append(line).append("\n");
                        line = reader.readLine();
                    }
                    response.data = builder.toString();
                    if (requests[i].isCached() && requests[i].posts.size() == 0 && response.code == HttpStatus.SC_OK) {
                        APICache.getInstance().addCache(requests[i].url, response.response, response.data);
                    }
                } catch (Exception e) {
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
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        } catch (Exception e) {
        }
    }
}