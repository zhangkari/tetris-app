package game.tetris.data;

import game.tetris.utils.Prefs;

public final class Settings {
    private static final String KEY_SOUND_ON = "key_sound_on";

    public static boolean isSoundOn() {
        return Prefs.getBoolean(KEY_SOUND_ON, false);
    }

    public static void setSoundOn(boolean on) {
        Prefs.save(KEY_SOUND_ON, on);
    }
}