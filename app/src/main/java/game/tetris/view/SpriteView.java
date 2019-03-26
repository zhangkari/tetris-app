package game.tetris.view;

import android.content.Context;
import android.util.AttributeSet;

import game.engine.RenderView;
import game.tetris.utils.DownTimer;
import game.tetris.utils.Timer;

public class SpriteView extends RenderView implements SpriteListener, Timer.OnTickListener {
    private int mInterval;
    private SpriteListener mSpriteListener;
    private Timer mTimer;

    public SpriteView(Context context) {
        this(context, null);
    }

    public SpriteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpriteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInterval = 1000;
        mSpriteListener = new SpriteHolder();
        post(new Runnable() {
            @Override
            public void run() {
                onSizeMeasured(getWidth(), getHeight());
            }
        });
        mTimer = new DownTimer(getContext());
    }

    private void onSizeMeasured(int width, int height) {
        onInitialized(this, width, height);
    }

    @Override
    public void onInitialized(RenderView view, int width, int height) {
        if (mSpriteListener != null) {
            mSpriteListener.onInitialized(this, width, height);
        }
    }

    @Override
    public void onStart() {
        if (mSpriteListener != null) {
            mSpriteListener.onStart();
        }
        mTimer.startLoop(0, mInterval, this);
    }

    @Override
    public void onPause() {
        if (mSpriteListener != null) {
            mSpriteListener.onPause();
        }
        mTimer.cancel();
    }

    @Override
    public void onReset() {
        if (mSpriteListener != null) {
            mSpriteListener.onReset();
        }
        mTimer.cancel();
    }

    @Override
    public void onAchieve(int row) {
        if (mSpriteListener != null) {
            mSpriteListener.onAchieve(row);
        }
    }

    @Override
    public void onQuit() {
        if (mSpriteListener != null) {
            mSpriteListener.onQuit();
        }
        mTimer.cancel();
    }

    @Override
    public void onMoveLeft() {
        if (mSpriteListener != null) {
            mSpriteListener.onMoveLeft();
        }
    }

    @Override
    public void onMoveRight() {
        if (mSpriteListener != null) {
            mSpriteListener.onMoveRight();
        }
    }

    @Override
    public void onMoveDown() {
        if (mSpriteListener != null) {
            mSpriteListener.onMoveDown();
        }
    }

    @Override
    public void onTransform() {
        if (mSpriteListener != null) {
            mSpriteListener.onTransform();
        }
    }

    @Override
    public void onTick(int interval) {
        if (mSpriteListener != null) {
            mSpriteListener.onMoveDown();
        }
    }
}
