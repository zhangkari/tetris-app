package game.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.engine.drawable.KDrawable;

public class RenderThread extends Thread {
    private static final String TAG = "RenderThread";

    // indicate whether exit RenderThread
    private boolean mExitFlag;

    private SurfaceHolder mHolder;
    private List<KDrawable> mDrawables;
    private Paint mClearPaint;

    // Refresh frequency
    private int mRefreshHZ = 10;

    private int mMaxWidth;
    private int mMaxHeight;

    public RenderThread(SurfaceHolder holder) {
        super(TAG);
        mHolder = holder;
        init();
    }

    public void addDrawable(KDrawable drawable) {
        if (drawable != null && !mDrawables.contains(drawable)) {
            mDrawables.add(drawable);
        }
        retrieveMaxSize();
    }

    public void setDrawables(Collection<KDrawable> drawables) {
        mDrawables.clear();
        if (drawables != null) {
            mDrawables.addAll(drawables);
        }
        retrieveMaxSize();
    }

    public boolean removeDrawable(KDrawable d) {
        return mDrawables.remove(d);
    }

    public boolean hasDrawable(KDrawable d) {
        if (d == null) {
            throw new NullPointerException("Argument must not be null !");
        }
        return mDrawables.contains(d);
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
        onDestroy();
    }

    public void invalidate() {
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        final int interval = 1000 / mRefreshHZ;
        while (!mExitFlag) {
            Canvas canvas = mHolder.lockCanvas();
            clearCanvas(canvas);
            onDraw(canvas);
            mHolder.unlockCanvasAndPost(canvas);
            try {
                synchronized (this) {
                    wait(interval);
                }
            } catch (Exception e) {
                mExitFlag = true;
            }
        }
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawRect(0, 0, mMaxWidth, mMaxHeight, mClearPaint);
    }

    private void onDraw(Canvas canvas) {
        for (KDrawable d : mDrawables) {
            d.onDraw(canvas);
        }
    }

    private void onDestroy() {
        if (mDrawables.size() > 0) {
            for (KDrawable d : mDrawables) {
                d.onDestroy();
            }
            mDrawables.clear();
        }
    }

    private void init() {
        mExitFlag = true;
        mDrawables = new ArrayList<>();
        mClearPaint = new Paint(Color.WHITE);
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    private void retrieveMaxSize() {
        if (mDrawables == null || mDrawables.size() < 1) {
            return;
        }
        for (KDrawable d : mDrawables) {
            if (d.getWidth() > mMaxWidth) {
                mMaxWidth = d.getWidth();
            }
            if (d.getHeight() > mMaxHeight) {
                mMaxHeight = d.getHeight();
            }
        }
    }
}
