package fr.qinder.api;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import fr.qinder.conf.Configurations;

public class APIGetter extends AsyncTask<APIRequest, APIResponse, Void> {
	private DefaultHttpClient _httpClient;
	private APIListener _listener;
	private ProgressDialog _progressDialog;
	//private int _nb_running;

	public APIGetter(Fragment context, boolean dialog) {
		_httpClient = new DefaultHttpClient();
		_listener = (APIListener) context;
		_progressDialog = null;
		//_nb_running = 0;
		if (dialog) {
			_progressDialog = new ProgressDialog(context.getActivity());
			_progressDialog.setMessage("Chargement...");
			_progressDialog.setCanceledOnTouchOutside(false);
			_progressDialog.show();
		}
	}

	protected HttpResponse post(String url, APIRequest request) {
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(request.posts));
			for (int i = 0; i < request.headers.size(); i++) {
				httpPost.addHeader(request.headers.get(i).getName(), request.headers.get(i).getValue());
			}
			return _httpClient.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//_nb_running++;
	}

	@Override
	protected void onProgressUpdate(APIResponse... response) {
		super.onProgressUpdate(response);
		//_nb_running--;
		for (int i = 0; i < response.length; i++)
			_listener.onResult(response[i]);
	}

	@Override
	protected Void doInBackground(APIRequest... requests) {
		for (int i = 0; i < requests.length; i++) {
			APIResponse response = new APIResponse();
			response.request = requests[i];
			Uri.Builder url_builder = new Uri.Builder();
			url_builder.scheme(Configurations.Schema).authority(Configurations.Host).path(response.request.getPath());
			for (int j = 0; j < response.request.gets.size(); j++) {
				url_builder.appendQueryParameter(response.request.gets.get(j).getName(), response.request.gets.get(j).getValue());
			}
			String url = url_builder.build().toString();
			if (response.request.isCached() && response.request.posts.size() == 0 && APICache.getInstance().getCache(url) != null) {
				response.response = APICache.getInstance().getCacheResponse(url);
				response.data = APICache.getInstance().getCacheData(url);
				response.code = 200;
			} else {
				response.response = post(url, requests[0]);
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.response.getEntity().getContent(), "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;)
						builder.append(line).append("\n");
					response.data = builder.toString();
					if (response.response.getEntity() != null) {
						response.response.getEntity().consumeContent();
					}
					response.code = response.response.getStatusLine().getStatusCode();
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