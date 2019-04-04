package game.tetris.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class UniversalTimer implements Timer {
    private static final String TAG = "UniversalTimer";

    private static final int MSG_TICK = 100;
    private static final int MSG_QUIT = 800;

    private boolean isCanceled;
    private OnTickListener onTickListener;
    private Handler mHandler;

    public UniversalTimer() {
        mHandler = new TimerHandler(this);
    }

    public UniversalTimer(boolean newThread) {
        if (newThread) {
            HandlerThread thread = new HandlerThread(TAG);
            thread.start();
            mHandler = new TimerHandler(this, thread.getLooper());
        } else {
            mHandler = new TimerHandler(this);
        }
    }

    @Override
    public void startLoop(int delay, int interval, OnTickListener listener) {
        if (interval <= 0) {
            throw new IllegalArgumentException("interval must be positive !");
        }
        if (interval < 10) {
            throw new IllegalArgumentException("interval must not less than 10 !");
        }
        if (delay < 0) {
            delay = 0;
        }
        isCanceled = false;
        onTickListener = listener;
        mHandler.removeMessages(MSG_TICK);
        startLoopInner(delay, interval);
    }

    private void startLoopInner(int delay, int interval) {
        Message msg = Message.obtain();
        msg.what = MSG_TICK;
        msg.arg1 = interval;
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public void cancel() {
        isCanceled = true;
        mHandler.removeMessages(MSG_TICK);
    }

    @Override
    public void destroy() {
        cancel();
        onTickListener = null;
    }

    private void onTimerTick(int interval) {
        if (onTickListener != null) {
            onTickListener.onTick(interval);
        }
        if (!isCanceled) {
            startLoopInner(interval, interval);
        }
    }

    static class TimerHandler extends Handler {
        private static final String TAG = "TimerHandler";
        private WeakReference<UniversalTimer> ref;

        TimerHandler(UniversalTimer timer) {
            super();
            ref = new WeakReference<>(timer);
        }

        TimerHandler(UniversalTimer timer, Looper looper) {
            super(looper);
            ref = new WeakReference<>(timer);
        }

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (ref == null) {
                return;
            }
            UniversalTimer timer = ref.get();
            if (timer == null) {
                return;
            }
            switch (message.what) {
                case MSG_TICK:
                    timer.onTimerTick(message.arg1);
                    break;

                case MSG_QUIT:
                    if (getLooper() != Looper.getMainLooper()) {
                        getLooper().quit();
                    }
                    break;
            }
        }
    }
}
