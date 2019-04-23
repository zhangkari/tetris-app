package game.tetris.utils;

import game.tetris.App;

public final class Prefs {
    private static final String TAG = "Prefs";

    public static void save(String key, boolean value) {
        App.get().getSharedPreferences(TAG, 0).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return App.get().getSharedPreferences(TAG, 0).getBoolean(key, defValue);
    }

    public static void save(String key, int value) {
        App.get().getSharedPreferences(TAG, 0).edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return App.get().getSharedPreferences(TAG, 0).getInt(key, defValue);
    }
}
