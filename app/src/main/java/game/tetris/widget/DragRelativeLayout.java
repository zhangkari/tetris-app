package game.tetris.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.minmin.kari.tetris.R;

public class DragRelativeLayout extends RelativeLayout {

    private int[] mId = {
            R.id.left, R.id.center, R.id.right
    };

    private MatrixView target;
    private Rect rect = new Rect();

    private MatrixView holeView;
    private Rect holeRect = new Rect();

    public DragRelativeLayout(Context context) {
        this(context, null);
    }

    public DragRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        holeView = findViewById(R.id.hole);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                target = retrieveChildView(event);
                return target != null;

            case MotionEvent.ACTION_MOVE:
                return moveChildView(event);


            case MotionEvent.ACTION_UP:
                return locateChildView(event);

            default:
                return false;
        }
    }

    private boolean moveChildView(MotionEvent event) {
        if (target == null) {
            return false;
        }
        target.setX(event.getX() - target.getWidth() / 2f);
        target.setY(event.getY() - target.getHeight() / 2f);
        return true;
    }

    private boolean locateChildView(MotionEvent event) {
        if (target == null) {
            return false;
        }
        if (!canFillInHole()) {
            target.resetLocation();
        }

        return true;
    }

    private MatrixView retrieveChildView(MotionEvent event) {
        for (int id : mId) {
            MatrixView view = findViewById(id);
            view.getHitRect(rect);
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return view;
            }
        }
        return null;
    }

    private boolean canFillInHole() {
        target.getHitRect(rect);
        holeView.getHitRect(holeRect);
        return holeRect.contains(rect);
    }
}