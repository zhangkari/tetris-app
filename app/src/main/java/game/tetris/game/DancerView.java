package game.tetris.game;

import android.content.Context;
import android.util.AttributeSet;

import game.engine.RenderView;

public class DancerView extends RenderView implements Dancer {
    private Dancer mDancer;

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

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void init() {
        mStatus = STATUS_INIT;
        mDancer = new DancerProxy();
        initListeners();
        post(new Runnable() {
            @Override
            public void run() {
                onSizeMeasured(getWidth(), getHeight());
            }
        });
    }

    private void initListeners() {
        register(new OnGameOverListener() {
            @Override
            public void onGameOver() {
                mStatus = STATUS_OVER;
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
    public void onResume() {
        if (mDancer != null) {
            mDancer.onResume();
            mStatus = STATUS_RUNNING;
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
            mStatus = STATUS_STOP;
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
    public void register(OnGameOverListener listener) {
        if (mDancer != null) {
            mDancer.register(listener);
        }
    }

    @Override
    public void unregister(OnGameOverListener listener) {
        if (mDancer != null) {
            mDancer.unregister(listener);
        }
    }

    @Override
    public void register(OnScoreChangeListener listener) {
        if (mDancer != null) {
            mDancer.register(listener);
        }
    }

    @Override
    public void unregister(OnScoreChangeListener listener) {
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
        if (mDancer != null && mStatus == STATUS_RUNNING) {
            mDancer.onMoveDown();
        }
    }

    @Override
    public void onTransform() {
        if (mDancer != null) {
            mDancer.onTransform();
        }
    }
}
