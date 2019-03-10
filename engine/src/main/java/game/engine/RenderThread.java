package game.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.engine.drawable.KDrawable;

public class RenderThread extends Thread {
    private static final String TAG = "RenderThread";

    private SurfaceHolder mHolder;
    private boolean mExitFlag;
    private List<KDrawable> mDrawables;
    private int mRefreshHZ = 10;

    public RenderThread(SurfaceHolder holder) {
        super(TAG);
        mHolder = holder;
        init();
    }

    public void addDrawable(KDrawable drawable) {
        if (drawable != null && !mDrawables.contains(drawable)) {
            mDrawables.add(drawable);
        }
    }

    public void setDrawables(Collection<KDrawable> drawables) {
        mDrawables.clear();
        if (drawables != null) {
            mDrawables.addAll(drawables);
        }
    }

    public void setRefreshHZ(int hz) {
        if (hz >= 1 && hz <= 60) {
            mRefreshHZ = hz;
        }
    }

    @Override
    public void start() {
        mExitFlag = false;
        super.start();
    }

    public void exit() {
        if (isAlive()) {
            mExitFlag = true;
            interrupt();
        }
    }

    @Override
    public void run() {
        final int interval = 1000 / mRefreshHZ;
        while (!mExitFlag) {
            Canvas canvas = mHolder.lockCanvas();
            onDraw(canvas);
            mHolder.unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(interval);
            } catch (Exception e) {
                mExitFlag = true;
            }
        }
    }

    private void onDraw(Canvas canvas) {
        for (KDrawable d : mDrawables) {
            d.onDraw(canvas);
        }
    }

    private void init() {
        mExitFlag = true;
        mDrawables = new ArrayList<>(16);
    }
}
