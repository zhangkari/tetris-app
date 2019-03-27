package game.tetris.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class DownTimer implements Timer {

    private static final int MSG_TICK = 100;

    private boolean isCanceled;
    private OnTickListener onTickListener;
    private Handler mHandler;

    public DownTimer() {
        mHandler = new TimerHandler(this);
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
        onTickListener = listener;
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
        private WeakReference<DownTimer> ref;

        TimerHandler(DownTimer timer) {
            ref = new WeakReference<>(timer);
        }

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Logs.d(TAG, "++ handleMessage ++");
            if (ref == null) {
                return;
            }
            DownTimer timer = ref.get();
            if (timer == null) {
                return;
            }
            timer.onTimerTick(message.arg1);
            Logs.d(TAG, "-- handleMessage --");
        }
    }
}
