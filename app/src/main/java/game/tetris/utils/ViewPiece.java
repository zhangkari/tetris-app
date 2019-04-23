package game.tetris.utils;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public final class ViewPiece {
    private static final int DEFAULT_CAPACITY = 32;

    private View parent;
    private SparseArray<View> cachedViews;

    public ViewPiece(View parent) {
        this.parent = parent;
        cachedViews = new SparseArray<>(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T find(int resId) {
        View view = cachedViews.get(resId);
        if (view != null) {
            return (T) view;
        }
        view = parent.findViewById(resId);
        if (view != null) {
            cachedViews.put(resId, view);
            return (T) view;
        } else {
            return null;
        }
    }

    public void setOnClickListener(int resId, View.OnClickListener listener) {
        View view = find(resId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    public void setVisibility(int resId, int visibility) {
        View view = find(resId);
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public void setOnTouchListener(int resId, View.OnTouchListener listener) {
        View view = find(resId);
        if (view != null) {
            view.setOnTouchListener(listener);
        }
    }

    public void setText(int resId, CharSequence text) {
        TextView tv = find(resId);
        if (tv != null) {
            tv.setText(text);
        }
    }
}
