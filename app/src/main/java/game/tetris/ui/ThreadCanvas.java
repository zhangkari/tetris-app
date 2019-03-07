package game.tetris.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThreadCanvas extends View implements Runnable {

    public ThreadCanvas(Context context) {
        super(context);

    }

    protected void onDraw(Canvas canvas) {
        if (GameControl.getMainView() != null) {
            GameControl.getMainView().onDraw(canvas);
        } else {
            Log.e("ThreadCanvas", "mGameView:null");
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void refresh() {
        if (GameControl.getMainView() != null) {
            GameControl.getMainView().refresh();
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refresh();

            postInvalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return GameControl.getMainView().onTouchEvent(event);
    }

}
