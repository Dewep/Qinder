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
import java.util.List;

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
public class LibAdapter<T> extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<T> mData;
    private ViewAdapter<T> mViewAdapter = null;

    public LibAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mViewAdapter == null) {
            return null;
        }
        return mViewAdapter.buildView(mInflater, convertView, getItem(position));
    }

    public void setViewAdapter(ViewAdapter<T> build) {
        mViewAdapter = build;
    }

    public List<T> getData() {
        return mData;
    }

    public void add(T obj) {
        mData.add(obj);
    }
}