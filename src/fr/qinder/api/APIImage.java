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
    private ImageView mImage = null;
    private ImageViewLoader mImageloader = null;
    private Boolean mCached = true;
    private APICallback mCallback = null;

    public APIImage(ImageView image, Boolean cached) {
        mImage = image;
        mCached = cached;
    }

    public APIImage(View view) {
        if (view instanceof ImageView) {
            mImage = (ImageView) view;
        } else if (view instanceof ImageViewLoader) {
            mImageloader = (ImageViewLoader) view;
        }
    }

    public void setCallback(APICallback callback) {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mImageloader != null) {
            mImageloader.setLoader(true);
        }
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;
        if (mCached && APICache.getInstance().getCache(url) != null) {
            return APICache.getInstance().getCacheImage(url);
        }
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
        if (mImage != null) {
            mImage.setImageBitmap(result);
        }
        if (mImageloader != null) {
            mImageloader.getImage().setImageBitmap(result);
            mImageloader.setLoader(false);
        }
        if (mCallback != null) {
            mCallback.onResult();
        }
    }
}