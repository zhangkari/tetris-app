package game.tetris.utils;

import game.engine.utils.Sounds;
import game.tetris.App;

public class SoundManager {
    private static final Sounds sound = Sounds.getInstance();

    public void playTimes(int resId, int times) {
        sound.play(App.get(), resId, times);
    }

    public void playOnce(int resId) {
        playTimes(resId, 0);
    }

    public void playTwice(int resId) {
        playTimes(resId, 1);
    }

    public void playThrice(int resId) {
        playTimes(resId, 2);
    }

    public void playLoop(int resId) {
        playTimes(resId, -1);
    }

    public void destroy() {
        sound.destroy();
    }
}
