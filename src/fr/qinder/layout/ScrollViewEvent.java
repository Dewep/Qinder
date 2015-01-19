package fr.qinder.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewEvent extends ScrollView {
    private ScrollViewEventListener scrollViewEventListener = null;
    private static int TOLERANCE_POSITION = 5;

    public ScrollViewEvent(Context context) {
        super(context);
    }

    public ScrollViewEvent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollViewEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewEventListener scrollViewListener) {
        this.scrollViewEventListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (scrollViewEventListener != null) {

            scrollViewEventListener.onScrollChanged(this, l, t, oldl, oldt);

            if (getScrollY() < TOLERANCE_POSITION) {
                scrollViewEventListener.onTop(this);
            }

            View lastChild = (View) getChildAt(getChildCount() - 1);
            int diff = (lastChild.getBottom() - (getHeight() + getScrollY()));
            if (diff < TOLERANCE_POSITION) {
                scrollViewEventListener.onBot(this);
            }

        }
    }
}
