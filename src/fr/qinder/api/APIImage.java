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

import java.io.InputStream;

import fr.qinder.imageviewloader.ImageViewLoader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public class APIImage extends AsyncTask<String, Void, Bitmap> {
	ImageView _image = null;
	ImageViewLoader _imageloader = null;
	Boolean _cached = true;
	APICallback _callback = null;

	public APIImage(ImageView image, Boolean cached) {
		_image = image;
		_cached = cached;
	}

	public APIImage(View view) {
		if (view instanceof ImageView)
			_image = (ImageView) view;
		else if (view instanceof ImageViewLoader)
			_imageloader = (ImageViewLoader) view;
	}

	public void setCallback(APICallback callback) {
		_callback = callback;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (_imageloader != null)
			_imageloader.setLoader(true);
	}

	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];
		Bitmap image = null;
		if (_cached && APICache.getInstance().getCache(url) != null)
			return APICache.getInstance().getCacheImage(url);
		try {
			InputStream in = new java.net.URL(url).openStream();
			image = BitmapFactory.decodeStream(in);
			APICache.getInstance().addCache(url, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	protected void onPostExecute(Bitmap result) {
		if (_image != null)
			_image.setImageBitmap(result);
		if (_imageloader != null) {
			_imageloader.getImage().setImageBitmap(result);
			_imageloader.setLoader(false);
		}
		if (_callback != null)
			_callback.onResult();
	}
}