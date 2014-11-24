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

import android.app.Activity;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
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
