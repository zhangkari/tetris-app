package game.tetris.data;

import game.tetris.utils.Prefs;

public final class GameScore {
    private static final String KEY_CURRENT_LEVEL = "key_current_level";
    private static final String KEY_CURRENT_SCORE = "key_current_score";

    public static int getCurrentLevel() {
        return Prefs.getInt(KEY_CURRENT_LEVEL, 1);
    }

    public static void saveCurrentLevel(int level) {
        Prefs.save(KEY_CURRENT_LEVEL, level);
    }

    public static int getCurrentScore() {
        return Prefs.getInt(KEY_CURRENT_SCORE, 0);
    }

    public static void saveCurrentScore(int score) {
        Prefs.save(KEY_CURRENT_SCORE, score);
    }

    public static void reset() {
        saveCurrentScore(0);
        saveCurrentLevel(1);
    }
}