package fr.qinder.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class APIGetter extends AsyncTask<APIRequest, APIResponse, Void> {
	private HttpClient _httpClient;
	private CookieManager _cookiemanager;
	private APIListener _listener;
	private ProgressDialog _progressDialog;

	@SuppressLint("NewApi") // @TODO: Corriger ca
	public APIGetter(Fragment context, boolean dialog) {
		_listener = (APIListener) context;
		initGetter(context.getActivity(), dialog);
	}

	public APIGetter(Activity context, boolean dialog) {
		_listener = (APIListener) context;
		initGetter(context, dialog);
	}

	public HttpClient getNewHttpClient() {
		_cookiemanager = new CookieManager();
		HttpCookie cookie = new HttpCookie("language", "fr");
		cookie.setPath("/");
		cookie.setDomain("https://intra.epitech.eu");
		try {
			_cookiemanager.getCookieStore().add(new URI("https://intra.epitech.eu"), cookie);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		CookieHandler.setDefault(_cookiemanager);
		return new DefaultHttpClient();
	}

	private void initGetter(Activity context, boolean dialog) {
		_httpClient = getNewHttpClient();
		_progressDialog = null;
		if (dialog) {
			_progressDialog = new ProgressDialog(context);
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
			
			HttpsURLConnection request_post = (HttpsURLConnection) url.openConnection();
			request_post.setRequestMethod("POST");
			request_post.setDoInput(true);
			request_post.setDoOutput(true);

			List<HttpCookie> list = _cookiemanager.getCookieStore().get(new URI(url.getHost()));
			for (HttpCookie httpCookie : list) {
				Log.d("cookie", httpCookie.getName());
			}

			if (request.posts != null) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(request.posts);
				OutputStream post = request_post.getOutputStream();
				 entity.writeTo(post);
				 post.flush();
			}

			request_post.connect();
			return request_post;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(APIResponse... response) {
		super.onProgressUpdate(response);
		for (int i = 0; i < response.length; i++)
			_listener.onResult(response[i]);
	}

	protected InputStream getInputStream(HttpsURLConnection request) throws IOException {
		if (request.getResponseCode() == 200)
			return request.getInputStream();
		return request.getErrorStream();
	}

	@Override
	protected Void doInBackground(APIRequest... requests) {
		for (int i = 0; i < requests.length; i++) {
			APIResponse response = new APIResponse();
			response.request = requests[i];
			String url = response.request.getHost();
			for (int j = 0; j < response.request.gets.size(); j++) {
				url = fr.qinder.tools.URL.addParameter(url, response.request.gets.get(j).getName(), response.request.gets.get(j).getValue());
			}
			if (response.request.isCached() && response.request.posts.size() == 0 && APICache.getInstance().getCache(url) != null) {
				response.response = APICache.getInstance().getCacheResponse(url);
				response.data = APICache.getInstance().getCacheData(url);
				response.code = 200;
			} else {
				response.response = post(url, requests[0]);
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(response.response), "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;)
						builder.append(line).append("\n");
					response.data = builder.toString();
					if (response.request.isCached() && response.request.posts.size() == 0 && response.code == 200)
						APICache.getInstance().addCache(url, response.response, response.data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			publishProgress(response);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void response) {
		super.onPostExecute(response);
		try {
			if (_progressDialog != null && _progressDialog.isShowing())
				_progressDialog.cancel();
		} catch (Exception e) {
			// nothing
		}
	}
}