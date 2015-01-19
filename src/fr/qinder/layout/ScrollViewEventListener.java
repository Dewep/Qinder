package fr.qinder.layout;

public interface ScrollViewEventListener {
    public void onScrollChanged(ScrollViewEvent scrollView, int x, int y, int oldx, int oldy);
    public void onTop(ScrollViewEvent scrollView);
    public void onBot(ScrollViewEvent scrollView);
}
