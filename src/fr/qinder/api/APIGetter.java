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

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.entity.UrlEncodedFormEntity;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public class APIGetter extends AsyncTask<APIRequest, APIRequest, Void> {

    private static final int HTTP_CODE_SUCCESS = 200;

    public APIGetter() {
        APICookie.getInstance();
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
        InputStream res;

        if (request.getResponseCode() == HTTP_CODE_SUCCESS) {
            res = request.getInputStream();
        } else {
            res = request.getErrorStream();
        }
        return res;
    }

    private void readResponse(APIRequest request) {
        APIResponse response = request.response;

        try {
            response.code = response.response.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(response.response), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                line = reader.readLine();
            }
            response.data = builder.toString();
            if (request.isCached() && request.posts.size() == 0 && response.code == HTTP_CODE_SUCCESS) {
                APICache.getInstance().addCache(request.url, response.response, response.data);
            }
        } catch (IOException e) {
            response.code = 0;
            response.data = null;
        }
    }

    private void executeRequest(APIRequest request) {
        APIResponse response = request.response;

        if (request.isCached() && request.posts.size() == 0 && APICache.getInstance().getCache(request.url) != null) {
            response.response = APICache.getInstance().getCacheResponse(request.url);
            response.data = APICache.getInstance().getCacheData(request.url);
            response.code = HTTP_CODE_SUCCESS;
            response.isCache = true;
        } else {
            response.response = post(request.url, request);
            response.isCache = false;
            if (response.response != null) {
                readResponse(request);
            }
        }
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
            executeRequest(requests[i]);
            requests[i].postExecute();
            publishProgress(requests[i]);
        }
        return null;
    }

}