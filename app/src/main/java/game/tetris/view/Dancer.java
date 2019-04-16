package game.tetris.view;

import game.engine.RenderView;

public interface Dancer {
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

    void register(OnWonderfulListener listener);

    void unregister(OnWonderfulListener listener);

    void register(OnNextShapeOccurredListener listener);

    void unregister(OnNextShapeOccurredListener listener);

    interface OnWonderfulListener {
        void onAchieve(int rows);
    }

    interface OnNextShapeOccurredListener {
        void onNextShape(int idx);
    }
}
