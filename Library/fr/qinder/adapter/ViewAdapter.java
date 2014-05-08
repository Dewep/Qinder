package fr.qinder.adapter;

import android.view.LayoutInflater;
import android.view.View;

public interface ViewAdapter<OBJ> {
	public View buildView(LayoutInflater inflater, View view, OBJ data);
}
