package game.engine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collection;

import game.engine.drawable.KDrawable;

public class RenderView extends SurfaceView implements SurfaceHolder.Callback {
    private RenderThread mThread;

    public RenderView(Context context) {
        this(context, null);
    }

    public RenderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void addDrawable(KDrawable drawable) {
        mThread.addDrawable(drawable);
    }

    public void setDrawables(Collection<KDrawable> drawables) {
        mThread.setDrawables(drawables);
    }

    private void init() {
        mThread = new RenderThread(getHolder());
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mThread.isAlive()) {
            mThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread.isAlive()) {
            mThread.exit();
        }
    }
}
