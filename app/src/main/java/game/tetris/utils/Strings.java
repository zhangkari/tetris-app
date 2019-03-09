package game.tetris.utils;

import game.tetris.App;

public class Strings {
    public static String get(int resId) {
        return App.get().getString(resId);
    }

    public static String get(int resId, Object... args) {
        return App.get().getString(resId, args);
    }
}
