package game.tetris.ui;

import android.content.Context;
import android.view.MotionEvent;

import game.engine.layer.KView;
import game.tetris.utils.Screens;

public class FullScreen extends KView {
    public FullScreen(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        widthSpec = MeasureSpec.makeMeasureSpec(Screens.getWidth(getContext()), MeasureSpec.EXACTLY);
        heightSpec = MeasureSpec.makeMeasureSpec(Screens.getHeight(getContext()), MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void reCycle() {

    }

    @Override
    public void refresh() {
    }
}
