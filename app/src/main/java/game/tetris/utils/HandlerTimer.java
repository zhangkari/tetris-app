package game.tetris.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HandlerTimer implements Timer {
    private static final String TAG = "HandlerTimer";

    private static final int MSG_TICK = 100;
    private static final int MSG_QUIT = 800;

    private Handler mHandler;
    private boolean mNewThread;
    private boolean mDestroyed;
    private List<TickListenerWrapper> mOnTickListeners;

    public HandlerTimer() {
        this(false);
    }

    public HandlerTimer(boolean newThread) {
        mDestroyed = false;
        mNewThread = newThread;
        mOnTickListeners = new ArrayList<>(4);

        if (newThread) {
            HandlerThread thread = new HandlerThread(TAG);
            thread.start();
            mHandler = new TimerHandler(this, thread.getLooper());
        } else {
            mHandler = new TimerHandler(this);
        }
    }

    @Override
    public int schedule(int interval, OnTickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null !");
        }
        return schedule(0, interval, null, listener);
    }

    @Override
    public int schedule(int delay, int interval, Object argument, OnTickListener listener) {
        if (delay < 0) {
            delay = 0;
        }

        if (interval <= 0) {
            throw new IllegalArgumentException("interval must be positive !");
        }

        if (interval < 10) {
            throw new IllegalArgumentException("interval must not less than 10 milliseconds !");
        }

        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null !");
        }

        int idx = retrieveTickListenerWrapper(listener, argument);
        if (idx < 0) {
            idx = addTickListener(listener, argument);
        }
        mOnTickListeners.get(idx).mCanceled = false;
        startLoopInner(idx, delay, interval, argument);

        return idx;
    }

    /**
     * 检索指定的OnTickListener是否在mOnTickListeners队列中
     *
     * @param listener 待检索的listener
     * @return 若不存在，返回-1, 如果存在， 返回在mOnTickListeners中的索引
     */
    private int retrieveTickListenerWrapper(OnTickListener listener, Object argument) {
        for (int i = 0; i < mOnTickListeners.size(); i++) {
            TickListenerWrapper wrapper = mOnTickListeners.get(i);
            if (isRecycledWrapper(wrapper, listener, argument)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isRecycledWrapper(TickListenerWrapper wrapper, OnTickListener listener, Object arg) {
        if (wrapper.mListener != listener) {
            return false;
        }

        if (arg == null) {
            return wrapper.mArgument == null;
        } else {
            return arg.equals(wrapper.mArgument);
        }
    }

    private int addTickListener(OnTickListener listener, Object arg) {
        if (mNewThread) {
            return addTickListenerLock(listener, arg);
        } else {
            return addTickListenerFast(listener, arg);
        }
    }

    private int addTickListenerFast(OnTickListener listener, Object arg) {
        mOnTickListeners.add(new TickListenerWrapper(listener, arg));
        return mOnTickListeners.size() - 1;
    }

    private int addTickListenerLock(OnTickListener listener, Object arg) {
        synchronized (this) {
            return addTickListenerFast(listener, arg);
        }
    }

    @Override
    public void cancel(int id) {
        if (mNewThread) {
            cancelSafe(id);
        } else {
            cancelFast(id);
        }
    }

    @Override
    public void cancelAll() {
        if (mNewThread) {
            cancelAllSafe();
        } else {
            cancelAllFast();
        }
    }

    @Override
    public void resume(int id) {
        if (mNewThread) {
            resumeSafe(id);
        } else {
            resumeFast(id);
        }
    }

    private void resumeFast(int id) {
        if (id >= 0 && id < mOnTickListeners.size()) {
            mOnTickListeners.get(id).mCanceled = false;
        }
    }

    private void resumeSafe(int id) {
        synchronized (this) {
            resumeFast(id);
        }
    }

    @Override
    public void resumeAll() {
        if (mNewThread) {
            resumeAllSafe();
        } else {
            resumeAllFast();
        }
    }

    private void resumeAllFast() {
        for (TickListenerWrapper wrapper : mOnTickListeners) {
            wrapper.mCanceled = false;
        }
    }

    private void resumeAllSafe() {
        synchronized (this) {
            resumeAllFast();
        }
    }

    private void cancelFast(int id) {
        if (id >= 0 && id < mOnTickListeners.size()) {
            mOnTickListeners.get(id).mCanceled = true;
        }
    }

    private void cancelSafe(int id) {
        synchronized (this) {
            cancelFast(id);
        }
    }

    private void cancelAllFast() {
        for (TickListenerWrapper wrapper : mOnTickListeners) {
            wrapper.mCanceled = true;
        }
    }

    private void cancelAllSafe() {
        synchronized (this) {
            cancelAllFast();
        }
    }

    /**
     * 开始轮询
     *
     * @param idx      轮询任务索引
     * @param delay    第一次执行延迟时间
     * @param interval 轮询间隔
     * @param argument 用户参数
     */
    private void startLoopInner(int idx, int delay, int interval, Object argument) {
        Message msg = Message.obtain();
        msg.what = MSG_TICK;
        msg.arg1 = idx;
        msg.arg2 = interval;
        msg.obj = argument;
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public void destroy() {
        mDestroyed = true;
        mHandler.removeMessages(MSG_TICK);
        mHandler.sendEmptyMessage(MSG_QUIT);
        mOnTickListeners.clear();
    }

    private void onTimerTick(int idx, int interval) {
        if (!mDestroyed) {
            if (mNewThread) {
                onTimerTickLock(idx);
            } else {
                onTimerTickFast(idx);
            }

            TickListenerWrapper wrapper = mOnTickListeners.get(idx);
            startLoopInner(idx, interval, interval, wrapper.mArgument);
        }
    }

    private void onTimerTickFast(int idx) {
        if (idx >= 0 && idx < mOnTickListeners.size()) {
            TickListenerWrapper listener = mOnTickListeners.get(idx);
            if (!listener.mCanceled) {
                listener.onTick(listener.mArgument);
            }
        }
    }

    private void onTimerTickLock(int idx) {
        synchronized (this) {
            onTimerTickFast(idx);
        }
    }

    static class TimerHandler extends Handler {
        private WeakReference<HandlerTimer> mRef;

        TimerHandler(HandlerTimer timer) {
            super();
            mRef = new WeakReference<>(timer);
        }

        TimerHandler(HandlerTimer timer, Looper looper) {
            super(looper);
            mRef = new WeakReference<>(timer);
        }

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (mRef == null) {
                return;
            }
            HandlerTimer timer = mRef.get();
            if (timer == null) {
                return;
            }
            switch (message.what) {
                case MSG_TICK:
                    timer.onTimerTick(message.arg1, message.arg2);
                    break;

                case MSG_QUIT:
                    if (getLooper() != Looper.getMainLooper()) {
                        getLooper().quit();
                    }
                    break;
            }
        }
    }

    static class TickListenerWrapper implements OnTickListener {
        boolean mCanceled;
        OnTickListener mListener;
        Object mArgument;

        public TickListenerWrapper(OnTickListener listener, Object argument) {
            mCanceled = false;
            mListener = listener;
            mArgument = argument;
        }

        @Override
        public void onTick(Object argument) {
            mListener.onTick(argument);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof TickListenerWrapper) {
                TickListenerWrapper wrapper = (TickListenerWrapper) obj;
                if (mListener != wrapper.mListener) {
                    return false;
                }
                if (mArgument == null) {
                    return wrapper.mArgument == null;
                } else {
                    return mArgument.equals(wrapper.mArgument);
                }
            }
            return false;
        }
    }
}
