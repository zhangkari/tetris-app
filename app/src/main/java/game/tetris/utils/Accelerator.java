package game.tetris.utils;

public interface Accelerator {
    int DOWN = 0;
    int LEFT = 1;
    int RIGHT = 2;

    void startAccelerate();

    void setDirection(int direction);

    void stopAccelerate();

    void destroy();
}
