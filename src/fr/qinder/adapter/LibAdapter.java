package fr.qinder.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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