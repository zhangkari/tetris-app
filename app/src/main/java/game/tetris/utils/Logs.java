package game.tetris.utils;

import android.util.Log;

import com.minmin.kari.tetris.BuildConfig;

public final class Logs {
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }
}
