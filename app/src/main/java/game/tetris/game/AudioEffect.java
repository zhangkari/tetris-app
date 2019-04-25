package game.tetris.game;

import com.minmin.kari.tetris.R;

import game.tetris.data.Settings;
import game.tetris.utils.SoundManager;

public class AudioEffect implements Audio {
    private static final SoundManager audio = new SoundManager();

    @Override
    public void playMove() {
        if (isDisable()) {
            return;
        }
        audio.playOnce(R.raw.audio_move);
    }

    @Override
    public void playTransform() {
        if (isDisable()) {
            return;
        }
        audio.playOnce(R.raw.audio_transform);
    }

    @Override
    public void playBlocked() {
        if (isDisable()) {
            return;
        }
        audio.playOnce(R.raw.audio_blocked);
    }

    @Override
    public void playPlaced() {
        if (isDisable()) {
            return;
        }
        audio.playOnce(R.raw.audio_placed);
    }

    @Override
    public void playScore1() {
        if (isDisable()) {
            return;
        }
        audio.playOnce(R.raw.audio_score);
    }

    @Override
    public void playScore2() {
        if (isDisable()) {
            return;
        }
        audio.playTwice(R.raw.audio_score);
    }

    @Override
    public void playScore3() {
        if (isDisable()) {
            return;
        }
        audio.playThrice(R.raw.audio_score);
    }

    @Override
    public void playScore4() {
        if (isDisable()) {
            return;
        }
        audio.playOnce(R.raw.audio_cheer);
    }

    @Override
    public void playGameOver() {
        if (isDisable()) {
            return;
        }
        audio.playThrice(R.raw.audio_blocked);
    }

    @Override
    public void destroy() {
        audio.destroy();
    }

    private boolean isDisable() {
        return !Settings.isSoundOn();
    }
}
