package game.tetris.game;

import game.engine.RenderView;

public interface Dancer {
    void onInitialized(RenderView view, int width, int height);

    void onStart();

    void onPause();

    void onResume();

    void onReset();

    void onMoveLeft();

    void onMoveRight();

    void onMoveDown();

    void onTransform();

    void onQuit();

    void register(OnNextShapeOccurredListener listener);

    void unregister(OnNextShapeOccurredListener listener);

    void register(OnGameOverListener listener);

    void unregister(OnGameOverListener listener);

    void register(OnScoreChangeListener listener);

    void unregister(OnScoreChangeListener listener);

    interface OnNextShapeOccurredListener {
        void onNextShape(int idx, int color);
    }

    interface OnGameOverListener {
        void onGameOver();
    }

    interface OnScoreChangeListener {
        void onScoreChange(int level, int score);
    }
}
