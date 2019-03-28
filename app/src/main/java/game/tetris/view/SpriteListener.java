package game.tetris.view;

import game.engine.RenderView;

public interface SpriteListener {
    void onInitialized(RenderView view, int width, int height);

    void onStart();

    void onPause();

    void onReset();

    void onMoveLeft();

    void onMoveRight();

    void onMoveDown();

    void onTransform();

    void onGameOver();

    void onQuit();
}
