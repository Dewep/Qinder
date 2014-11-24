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

package fr.qinder.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public class LibAdapter<OBJ> extends BaseAdapter {
	LayoutInflater inflater;
	Context context;
	ArrayList<OBJ> data;
	ViewAdapter<OBJ> view_adapter = null;

	public LibAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		data = new ArrayList<OBJ>();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public OBJ getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (view_adapter == null)
			return null;
		return view_adapter.buildView(inflater, convertView, getItem(position));
	}

	public void setViewAdapter(ViewAdapter<OBJ> build) {
		view_adapter = build;
	}

	public ArrayList<OBJ> getData() {
		return data;
	}

	public void add(OBJ obj) {
		data.add(obj);
	}
}