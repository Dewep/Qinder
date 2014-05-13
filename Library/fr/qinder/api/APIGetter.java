package fr.qinder.api;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import fr.qinder.tools.URL;

public class APIGetter extends AsyncTask<APIRequest, APIResponse, Void> {
	private DefaultHttpClient _httpClient;
	private APIListener _listener;
	private ProgressDialog _progressDialog;
	//private int _nb_running;

	public APIGetter(Fragment context, boolean dialog) {
		_listener = (APIListener) context;
		initGetter(context.getActivity(), dialog);
	}

	public APIGetter(Activity context, boolean dialog) {
		_listener = (APIListener) context;
		initGetter(context, dialog);
	}

	private void initGetter(Activity context, boolean dialog) {
		_httpClient = new DefaultHttpClient();
		_progressDialog = null;
		//_nb_running = 0;
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
			String url = response.request.getHost();
			for (int j = 0; j < response.request.gets.size(); j++) {
				url = URL.addParameter(url, response.request.gets.get(j).getName(), response.request.gets.get(j).getValue());
			}
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