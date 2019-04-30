package game.tetris.game;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.engine.RenderView;
import game.engine.drawable.KShapeData;
import game.tetris.collision.Checker;
import game.tetris.collision.SpriteChecker;
import game.tetris.data.Constants;
import game.tetris.data.GameScore;
import game.tetris.data.SceneData;
import game.tetris.data.SpriteData;
import game.tetris.sprite.GridLayer;
import game.tetris.sprite.RectShape;
import game.tetris.sprite.SceneLayer;
import game.tetris.sprite.Sprite;
import game.tetris.utils.HandlerTimer;
import game.tetris.utils.Logs;
import game.tetris.utils.Timer;

class DancerProxy implements Dancer, Timer.OnTickListener {
    private static final String TAG = "DancerProxy";

    private RenderView mView;
    private RectShape mScene;
    private Sprite mSprite;
    private Checker mChecker;
    private Timer mTimer;
    private int mInterval;

    private List<OnNextShapeOccurredListener> mNextShapeListeners;
    private List<OnGameOverListener> mGameOverListeners;
    private List<OnScoreChangeListener> mScoreChangeListener;

    private static final int[] COLORS = Constants.COLORS;
    private SpriteData mSpriteData;
    private int mNextShapeIdx;
    private int mNextColor;
    private int mScore;
    private int mLevel;

    private Audio mAudio;

    public DancerProxy() {
        mNextShapeListeners = new ArrayList<>();
        mGameOverListeners = new ArrayList<>();
        mScoreChangeListener = new ArrayList<>();
        mAudio = new AudioEffect();

        mScore = GameScore.getCurrentScore();
        mLevel = GameScore.getCurrentLevel();
    }

    @Override
    public void onInitialized(RenderView view, int width, int height) {
        mView = view;

        GridLayer bgGrid = new GridLayer(width, height);
        bgGrid.setInterval(width / Constants.SCENE_COLS);
        view.addDrawable(bgGrid);

        mScene = new SceneLayer(width, height);
        mScene.setShapeData(SceneData.getInstance().getShapeData());
        view.addDrawable(mScene);

        mSprite = new Sprite(width / Constants.SCENE_COLS * 4, height / Constants.SCENE_ROWS * 4);
        mSprite.setTileSize(width / Constants.SCENE_COLS);
        mSprite.setStyle(Paint.Style.FILL);
        mSprite.setPadding(4);
        view.addDrawable(mSprite);

        mChecker = new SpriteChecker();
        mTimer = new HandlerTimer();
        mInterval = calculateInterval(GameScore.getCurrentLevel());
        mView.setRefreshHZ(5);
        mSpriteData = new SpriteData();

        generateNextShape();
        notifyScoreChanged();
    }

    private void resetSprite() {
        int x = (Constants.SCENE_COLS / 2 - 2) * mSprite.getTileSize();
        mSprite.setXY(x, 0);

        mSprite.setShapes(mSpriteData.getAllShapes().get(mNextShapeIdx));
        mSprite.setColor(mNextColor);
        generateNextShape();
        notifyNextShapeChanged();
        mView.invalidate();
    }

    private void generateNextShape() {
        mNextShapeIdx = ((int) (Math.random() * 1000)) % mSpriteData.getAllShapes().size();
        mNextColor = COLORS[((int) (Math.random() * 1000)) % COLORS.length];
    }

    private void notifyNextShapeChanged() {
        for (OnNextShapeOccurredListener listener : mNextShapeListeners) {
            listener.onNextShape(mNextShapeIdx, mNextColor);
        }
    }

    private void notifyScoreChanged() {
        int level = GameScore.getCurrentLevel();
        int score = GameScore.getCurrentScore();
        for (OnScoreChangeListener listener : mScoreChangeListener) {
            listener.onScoreChange(level, score);
        }
    }

    @Override
    public void onStart() {
        mScene.reset();
        resetSprite();
        mTimer.schedule(mInterval, this);
    }

    @Override
    public void onPause() {
        mTimer.cancelAll();
    }

    @Override
    public void onResume() {
        mTimer.resumeAll();
    }

    @Override
    public void onReset() {
        mTimer.cancelAll();
        mScene.reset();
        GameScore.reset();
        notifyScoreChanged();
    }

    @Override
    public void onQuit() {
        mTimer.destroy();
        mView.exit();
        mAudio.destroy();
    }

    @Override
    public void register(OnNextShapeOccurredListener listener) {
        if (listener != null && !mNextShapeListeners.contains(listener)) {
            mNextShapeListeners.add(listener);
        }
    }

    @Override
    public void unregister(OnNextShapeOccurredListener listener) {
        if (listener != null) {
            mNextShapeListeners.remove(listener);
        }
    }

    @Override
    public void register(OnGameOverListener listener) {
        if (listener != null) {
            mGameOverListeners.add(listener);
        }
    }

    @Override
    public void unregister(OnGameOverListener listener) {
        if (listener != null) {
            mGameOverListeners.remove(listener);
        }
    }

    @Override
    public void register(OnScoreChangeListener listener) {
        if (listener != null) {
            mScoreChangeListener.add(listener);
        }
    }

    @Override
    public void unregister(OnScoreChangeListener listener) {
        if (listener != null) {
            mScoreChangeListener.remove(listener);
        }
    }

    @Override
    public void onMoveLeft() {
        if (mChecker.canMoveLeft(mSprite, mScene)) {
            mSprite.moveLeft();
            mView.invalidate();
            mAudio.playMove();
        } else {
            mAudio.playBlocked();
        }
    }

    @Override
    public void onMoveRight() {
        if (mChecker.canMoveRight(mSprite, mScene)) {
            mSprite.moveRight();
            mView.invalidate();
            mAudio.playMove();
        } else {
            mAudio.playBlocked();
        }
    }

    private int formatScore(int row) {
        /*
        int score = 0;
        switch (row) {
            case 1:
                score = 100;
                break;

            case 2:
                score = 300;
                break;

            case 3:
                score = 500;
                break;

            case 4:
                score = 700;
                break;
        }
        return score;
        */
        return (row * 2 - 1) * 100;
    }

    private void onAchieveRows(int rows) {
        Logs.d(TAG, "onAchieveRows:" + rows);
        int score = formatScore(rows);
        mScore += score;
        mLevel = mScore / 4000 + 1;

        GameScore.saveCurrentLevel(mLevel);
        GameScore.saveCurrentScore(mScore);
        mInterval = calculateInterval(mLevel);
        notifyScoreChanged();
        playScoreAudio(rows);
    }

    private int calculateInterval(int level) {
        return 800 - (level - 1) * 50;
    }

    private void playScoreAudio(int rows) {
        if (rows >= 4) {
            mAudio.playScore4();
        } else if (rows >= 3) {
            mAudio.playScore2();
        } else if (rows >= 2) {
            mAudio.playScore1();
        }
    }

    @Override
    public void onMoveDown() {
        if (mChecker.canMoveBottom(mSprite, mScene)) {
            mSprite.moveDown();
            mView.invalidate();
            return;
        }
        if (mChecker.isGameOver(mSprite, mScene)) {
            onGameOver();
            mAudio.playGameOver();
            return;
        }

        mAudio.playPlaced();

        fillSceneWithSprite(mSprite);
        List<Integer> score = mChecker.checkScore(mSprite, mScene);
        if (score.size() > 0) {
            onAchieveRows(score.size());
            Collections.sort(score); // natural ordering
            for (int row : score) {
                resetSceneRowValue(mScene.getShapeData(), row);
                shiftSceneFilledRow(mScene.getShapeData(), row);
            }
        }
        resetSprite();
    }

    private void shiftSceneFilledRow(KShapeData data, int row) {
        for (int i = row - 1; i >= 0; i--) {
            data.copyRow(i, i + 1);
        }
        data.resetRow(0);
    }

    private void fillSceneWithSprite(Sprite sprite) {
        int row = sprite.getY() / sprite.getTileSize();
        int col = sprite.getX() / sprite.getTileSize();
        int color = sprite.getColor();
        fillSceneValueAt(row, col, color);
    }

    private void fillSceneValueAt(int row, int col, int value) {
        for (int i = 0; i < mSprite.getShapeData().getRows(); i++) {
            for (int j = 0; j < mSprite.getShapeData().getCols(); j++) {
                if (row + i < mScene.getHeight() / mScene.getTileSize() &&
                        col + j < mScene.getWidth() / mScene.getTileSize() &&
                        mSprite.getShapeData().getValue(i, j) >= 1) {
                    mScene.getShapeData().setValue(row + i, col + j, value);
                }
            }
        }
    }

    private void resetSceneRowValue(KShapeData data, int row) {
        resetSceneRowValue(data, row, 0);
    }

    private void resetSceneRowValue(KShapeData data, int row, int value) {
        final int size = data.getCols();
        for (int i = 0; i < size; i++) {
            data.setValue(row, i, value);
        }
    }

    @Override
    public void onTransform() {
        if (!mView.hasDrawable(mSprite)) {
            return;
        }
        if (mChecker.canTransform(mSprite, mScene)) {
            mSprite.next();
            mView.invalidate();
            mAudio.playTransform();
        } else {
            mAudio.playBlocked();
        }
    }

    private void onGameOver() {
        mTimer.cancelAll();
        for (OnGameOverListener listener : mGameOverListeners) {
            listener.onGameOver();
        }
    }

    @Override
    public void onTick(Object argument) {
        onMoveDown();
    }
}
