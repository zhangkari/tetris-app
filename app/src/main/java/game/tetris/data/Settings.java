package game.tetris.data;

import game.tetris.utils.Prefs;

public final class Settings {
    private static final String KEY_SOUND_ON = "key_sound_on";
    private static final String KEY_CURRENT_LEVEL = "key_current_level";

    public static boolean isSoundOn() {
        return Prefs.getBoolean(KEY_SOUND_ON, false);
    }

    public static void setSoundOn(boolean on) {
        Prefs.save(KEY_SOUND_ON, on);
    }

    public static int getCurrentLevel() {
        return Prefs.getInt(KEY_CURRENT_LEVEL, 1);
    }

    public static void setCurrentLevel(int level) {
        Prefs.save(KEY_CURRENT_LEVEL, level);
    }
}