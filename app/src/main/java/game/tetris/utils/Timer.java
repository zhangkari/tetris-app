package game.tetris.utils;

public interface Timer {
    void startLoop(int delay, int interval, OnTickListener listener);

    void cancel();

    void destroy();

    interface OnTickListener {
        void onTick(int interval);
    }
}
