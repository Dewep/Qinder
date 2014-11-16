package fr.qinder;

import android.app.Activity;

public class Q {
	private static volatile Q instance = null;
	private Activity _activity = null;

	private Q(Activity activity) {
		_activity = activity;
	}

	public Activity getActivity() {
		return _activity;
	}

	public final static Q init(Activity activity) throws NullPointerException {
		if (Q.instance == null) {
			synchronized (Q.class) {
				if (Q.instance == null) {
					if (activity == null) {
						throw new NullPointerException();
					}
					Q.instance = new Q(activity);
				}
			}
		}
		return Q.instance;
	}

	public final static Activity get() {
		return Q.init(null).getActivity();
	}
}
