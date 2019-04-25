package game.tetris.utils;

import game.tetris.game.Dancer;

public class MoveAccelerator implements Accelerator, Timer.OnTickListener {
    private static final String TAG = "MoveAccelerator";
    private Dancer dancer;
    private Timer timer;
    private int direction;

    public MoveAccelerator(Dancer dancer) {
        this.dancer = dancer;
        timer = new UniversalTimer();
    }

    @Override
    public void startAccelerate() {
        timer.startLoop(0, 100, this);
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void stopAccelerate() {
        timer.cancel();
    }

    @Override
    public void destroy() {
        timer.destroy();
    }

    @Override
    public void onTick(int interval) {
        Logs.d(TAG, "onTick");
        switch (direction) {
            case DOWN:
                dancer.onMoveDown();
                break;

            case LEFT:
                dancer.onMoveLeft();
                break;

            case RIGHT:
                dancer.onMoveRight();
                break;
        }
    }
}
