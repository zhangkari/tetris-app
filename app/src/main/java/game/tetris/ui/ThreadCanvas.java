package game.tetris.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import game.engine.layer.KView;
import game.tetris.utils.Views;

public class ThreadCanvas extends FrameLayout implements Runnable {

    private boolean mExitFlag;
    private Thread mThread;
    private GameControl mGameControl;

    public ThreadCanvas(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public void setGameControl(GameControl control) {
        mGameControl = control;
    }

    @Override
    public void onDraw(Canvas canvas) {
        KView view = mGameControl.getMainView();
        if (view == null) {
            return;
        }
        Views.relayout(this, view);
    }

    public void start() {
        mThread = new Thread(this);
        mThread.start();
    }

    public void stop() {
        mExitFlag = true;
        mThread.interrupt();
    }

    public void refresh() {
        KView view = mGameControl.getMainView();
        if (view != null) {
            view.refresh();
        }
    }

    @Override
    public void run() {
        while (!mExitFlag) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            refresh();
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KView view = mGameControl.getMainView();
        if (view != null) {
            return view.onTouchEvent(event);
        }
        return false;
    }
}
