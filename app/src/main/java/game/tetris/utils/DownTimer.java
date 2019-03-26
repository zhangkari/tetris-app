package game.tetris.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;

public class DownTimer extends BroadcastReceiver implements Timer {

    private static final int REQUEST_ALARM_CODE = 100;
    private static final int WINDOW_LENGTH = 50;
    private static final String ACTION_ALARM = "Action_DownTimer_Alarm";

    private boolean canceled;
    private Context context;
    private PendingIntent pendingIntent;
    private OnTickListener onTickListener;
    private AlarmManager alarmManager;

    public DownTimer(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        IntentFilter filter = new IntentFilter(ACTION_ALARM);
        context.registerReceiver(this, filter);
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
        delay += SystemClock.elapsedRealtime();
        Intent intent = new Intent(context, DownTimer.class);
        intent.setAction(ACTION_ALARM);
        pendingIntent = PendingIntent.getBroadcast(context, REQUEST_ALARM_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra("repeat", true);
            intent.putExtra("interval", interval);
            alarmManager.setWindow(AlarmManager.ELAPSED_REALTIME, delay, WINDOW_LENGTH, pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, delay, interval, pendingIntent);
        }
    }

    @Override
    public void cancel() {
        canceled = true;
        context.unregisterReceiver(this);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!canceled && ACTION_ALARM.equals(intent.getAction())) {
            boolean repeat = intent.getBooleanExtra("repeat", false);
            int interval = intent.getIntExtra("interval", 0);
            if (onTickListener != null) {
                onTickListener.onTick(interval);
            }
            if (repeat) {
                startLoop(0, interval, onTickListener);
            }
        }
    }
}
