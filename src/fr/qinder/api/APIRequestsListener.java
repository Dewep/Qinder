package fr.qinder.api;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class APIRequestsListener {
	private APIGetter _getter;
	private List<APIRequest> _requests = new ArrayList<APIRequest>();

	public APIRequestsListener(Activity activity) {
		_getter = new APIGetter(activity);
	}

	public APIRequestsListener request(APIRequest request) {
			_requests.add(request);
			return this;
	}

	public void cancelTask() {
		_getter.cancel(true);
	}

	public void execute() {
		APIRequest[] array = new APIRequest[_requests.size()];
		_requests.toArray(array);
		_getter.execute(array);
	}
}
