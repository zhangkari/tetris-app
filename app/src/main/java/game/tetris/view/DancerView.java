package game.tetris.view;

import android.content.Context;
import android.util.AttributeSet;

import game.engine.RenderView;

public class DancerView extends RenderView implements Dancer, Dancer.OnWonderfulListener {
    private Dancer mDancer;
    private OnAchieveRowListener mScoreListener;

    public static final int STATUS_INIT = 0;
    public static final int STATUS_PAUSE = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_OVER = 3;
    public static final int STATUS_STOP = 4;
    private int mStatus;

    public DancerView(Context context) {
        this(context, null);
    }

    public DancerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DancerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getStatus() {
        return mStatus;
    }

    public void setOnAchieveRowListener(OnAchieveRowListener listener) {
        mScoreListener = listener;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void init() {
        mStatus = STATUS_INIT;
        mDancer = new DancerProxy();
        post(new Runnable() {
            @Override
            public void run() {
                onSizeMeasured(getWidth(), getHeight());
            }
        });
    }

    private void onSizeMeasured(int width, int height) {
        onInitialized(this, width, height);
    }

    @Override
    public void onInitialized(RenderView view, int width, int height) {
        if (mDancer != null) {
            mDancer.onInitialized(this, width, height);
            mStatus = STATUS_PAUSE;
        }
    }

    @Override
    public void onStart() {
        if (mDancer != null) {
            mDancer.onStart();
            mDancer.register(this);
            mStatus = STATUS_RUNNING;
        }
    }

    @Override
    public void onPause() {
        if (mDancer != null) {
            mDancer.onPause();
            mStatus = STATUS_PAUSE;
        }
    }

    @Override
    public void onReset() {
        if (mDancer != null) {
            mDancer.onReset();
            mStatus = STATUS_PAUSE;
        }
    }

    @Override
    public void onQuit() {
        if (mDancer != null) {
            mDancer.onQuit();
            mDancer.unregister(this);
            mStatus = STATUS_STOP;
        }
    }

    @Override
    public void register(OnWonderfulListener listener) {
        if (mDancer != null) {
            mDancer.register(listener);
        }
    }

    @Override
    public void unregister(OnWonderfulListener listener) {
        if (mDancer != null) {
            mDancer.unregister(listener);
        }
    }

    @Override
    public void register(OnNextShapeOccurredListener listener) {
        if (mDancer != null) {
            mDancer.register(listener);
        }
    }

    @Override
    public void unregister(OnNextShapeOccurredListener listener) {
        if (mDancer != null) {
            mDancer.unregister(listener);
        }
    }

    @Override
    public void onMoveLeft() {
        if (mDancer != null) {
            mDancer.onMoveLeft();
        }
    }

    @Override
    public void onMoveRight() {
        if (mDancer != null) {
            mDancer.onMoveRight();
        }
    }

    @Override
    public void onMoveDown() {
        if (mDancer != null) {
            mDancer.onMoveDown();
        }
    }

    @Override
    public void onTransform() {
        if (mDancer != null) {
            mDancer.onTransform();
        }
    }

    @Override
    public void onGameOver() {
        if (mDancer != null) {
            mDancer.onGameOver();
            mStatus = STATUS_OVER;
        }
    }

    @Override
    public void onAchieve(int rows) {
        if (mScoreListener != null) {
            mScoreListener.onAchieveRows(rows);
        }
    }

    public interface OnAchieveRowListener {
        void onAchieveRows(int rows);
    }
}
