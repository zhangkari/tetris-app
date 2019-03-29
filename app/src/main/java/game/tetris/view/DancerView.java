package game.tetris.view;

import android.content.Context;
import android.util.AttributeSet;

import game.engine.RenderView;

public class DancerView extends RenderView implements Dancer, Dancer.OnWonderfulListener {
    private Dancer mDancer;
    private OnAchieveRowListener mListener;

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

    public void setOnAchieveRowListener(OnAchieveRowListener listener) {
        mListener = listener;
    }

    private void init() {
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
        }
    }

    @Override
    public void onStart() {
        if (mDancer != null) {
            mDancer.onStart();
            mDancer.register(this);
        }
    }

    @Override
    public void onPause() {
        if (mDancer != null) {
            mDancer.onPause();
        }
    }

    @Override
    public void onReset() {
        if (mDancer != null) {
            mDancer.onReset();
        }
    }

    @Override
    public void onQuit() {
        if (mDancer != null) {
            mDancer.onQuit();
            mDancer.unregister(this);
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
        }
    }

    @Override
    public void onAchieve(int rows) {
        if (mListener != null) {
            mListener.onAchieveRows(rows);
        }
    }

    public interface OnAchieveRowListener {
        void onAchieveRows(int rows);
    }
}
