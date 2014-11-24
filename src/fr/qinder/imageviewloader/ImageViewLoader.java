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
    private RelativeLayout mBloc;
    private ImageView mImage;
    private ProgressBar mProgressbar;

    private static final int SIDE_PIXEL = 25;
    private static final int MARGIN_PIXEL = 5;

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

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        mBloc = new RelativeLayout(context, attrs, defStyle);
        mImage = new ImageView(context);
        mProgressbar = new ProgressBar(context);

        int sizePx = (int) (SIDE_PIXEL * this.getContext().getResources().getDisplayMetrics().density);
        int marginPx = (int) (MARGIN_PIXEL * this.getContext().getResources().getDisplayMetrics().density);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(sizePx, sizePx);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        lp.setMargins(marginPx, marginPx, marginPx, marginPx);
        mProgressbar.setLayoutParams(lp);
        mProgressbar.setVisibility(View.INVISIBLE);
        mImage.setAdjustViewBounds(true);

        mBloc.addView(mImage);
        mBloc.addView(mProgressbar);

        if (attrs != null) {
            int[] attrsArray = new int[] { android.R.attr.layout_width, android.R.attr.layout_height, android.R.attr.maxWidth, android.R.attr.maxHeight };
            TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
            int width = ta.getInt(0, -2);
            int height = ta.getInt(1, -2);
            int maxWidth = ta.getDimensionPixelSize(2, -1);
            int maxHeight = ta.getDimensionPixelSize(3, -1);
            int scaleType = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "scaleType", -1);
            mBloc.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            mImage.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            if (scaleType != -1) {
                mImage.setScaleType(ImageView.ScaleType.values()[scaleType]);
            }
            if (maxWidth != -1) {
                mImage.setMaxWidth(maxWidth);
            }
            if (maxHeight != -1) {
                mImage.setMaxHeight(maxHeight);
            }
            ta.recycle();
        }

        addView(mBloc);
    }

    public ImageView getImage() {
        return mImage;
    }

    public ProgressBar getProgressBar() {
        return mProgressbar;
    }

    public void setLoader(Boolean status) {
        if (status) {
            mProgressbar.setVisibility(ProgressBar.VISIBLE);
        } else {
            mProgressbar.setVisibility(ProgressBar.GONE);
        }
    }
}
