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

package fr.qinder.imageviewloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * TODO: Comments this class
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public class ImageViewLoader extends FrameLayout {
    private RelativeLayout _bloc;
    private ImageView _image;
    private ProgressBar _progressbar;

    public ImageViewLoader(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public ImageViewLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public ImageViewLoader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    public void initView(Context context, AttributeSet attrs, int defStyle) {
        _bloc = new RelativeLayout(context, attrs, defStyle);
        _image = new ImageView(context);
        _progressbar = new ProgressBar(context);

        int size_px = (int) (25 * this.getContext().getResources().getDisplayMetrics().density);
        int margin_px = (int) (5 * this.getContext().getResources().getDisplayMetrics().density);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(size_px, size_px);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        lp.setMargins(margin_px, margin_px, margin_px, margin_px);
        _progressbar.setLayoutParams(lp);
        _progressbar.setVisibility(View.INVISIBLE);
        _image.setAdjustViewBounds(true);

        _bloc.addView(_image);
        _bloc.addView(_progressbar);

        if (attrs != null) {
            int[] attrsArray = new int[] { android.R.attr.layout_width, android.R.attr.layout_height, android.R.attr.maxWidth, android.R.attr.maxHeight };
            TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
            int width = ta.getInt(0, -2);
            int height = ta.getInt(1, -2);
            int maxWidth = ta.getDimensionPixelSize(2, -1);
            int maxHeight = ta.getDimensionPixelSize(3, -1);
            int scaleType = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "scaleType", -1);
            _bloc.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            _image.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            if (scaleType != -1)
                _image.setScaleType(ImageView.ScaleType.values()[scaleType]);
            if (maxWidth != -1)
                _image.setMaxWidth(maxWidth);
            if (maxHeight != -1)
                _image.setMaxHeight(maxHeight);
            ta.recycle();
        }

        addView(_bloc);
    }

    public ImageView getImage() {
        return _image;
    }

    public ProgressBar getProgressBar() {
        return _progressbar;
    }

    public void setLoader(Boolean status) {
        if (status)
            _progressbar.setVisibility(ProgressBar.VISIBLE);
        else
            _progressbar.setVisibility(ProgressBar.GONE);
    }
}
