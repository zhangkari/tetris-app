package game.tetris.utils;

import android.view.View;
import android.view.ViewGroup;

public class Views {
    public static void relayout(ViewGroup parent, View view) {
        if (parent == null || view == null) {
            return;
        }
        boolean attached = false;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            v.setVisibility(View.GONE);
            if (v == view) {
                attached = true;
                v.setVisibility(View.VISIBLE);
                v.invalidate();
                break;
            }
        }

        if (!attached) {
            parent.addView(view);
            view.setVisibility(View.VISIBLE);
        }
    }
}
