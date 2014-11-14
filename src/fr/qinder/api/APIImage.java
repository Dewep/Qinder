package fr.qinder.api;

import java.io.InputStream;

import fr.qinder.imageviewloader.ImageViewLoader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

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