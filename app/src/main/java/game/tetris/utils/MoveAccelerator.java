package game.tetris.utils;

import game.tetris.game.Dancer;

public class MoveAccelerator implements Accelerator, Timer.OnTickListener {
    private static final String TAG = "MoveAccelerator";
    private Dancer dancer;
    private Timer timer;
    private int direction;

    private int taskId;

    public MoveAccelerator(Dancer dancer) {
        this.dancer = dancer;
        timer = new HandlerTimer();
        taskId = timer.schedule(200, this);
        timer.cancel(taskId);
    }

    @Override
    public void startAccelerate() {
        timer.resume(taskId);
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void stopAccelerate() {
        timer.cancel(taskId);
    }

    @Override
    public void destroy() {
        timer.destroy();
    }

    @Override
    public void onTick(Object argument) {
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
