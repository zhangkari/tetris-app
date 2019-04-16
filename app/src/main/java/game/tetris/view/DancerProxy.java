package game.tetris.view;

import android.graphics.Paint;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.engine.RenderView;
import game.engine.drawable.KShapeData;
import game.tetris.Constants;
import game.tetris.collision.Checker;
import game.tetris.collision.SpriteChecker;
import game.tetris.data.SceneData;
import game.tetris.data.SpriteData;
import game.tetris.sprite.GridLayer;
import game.tetris.sprite.RectShape;
import game.tetris.sprite.SceneLayer;
import game.tetris.sprite.Sprite;
import game.tetris.utils.Logs;
import game.tetris.utils.Timer;
import game.tetris.utils.UniversalTimer;

class DancerProxy implements Dancer, Timer.OnTickListener {
    private static final String TAG = "DancerProxy";

    private RenderView mView;
    private RectShape mScene;
    private Sprite mSprite;
    private Checker mChecker;
    private Timer mTimer;
    private int mInterval;

    private List<OnWonderfulListener> mScoreListeners;
    private List<OnNextShapeOccurredListener> mNextShapeListeners;

    private static final int[] COLORS = Constants.COLORS;
    private SpriteData mSpriteData;
    private int mNextShapeIdx;

    public DancerProxy() {
        mScoreListeners = new ArrayList<>();
        mNextShapeListeners = new ArrayList<>();
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

        mChecker = new SpriteChecker();
        mTimer = new UniversalTimer();
        mInterval = 800;
        mView.setRefreshHZ(5);
        mSpriteData = new SpriteData();
        generateNextShape();
    }

    private void resetSprite() {
        int x = (Constants.SCENE_COLS / 2 - 2) * mSprite.getTileSize();
        mSprite.setXY(x, 0);

        mSprite.setShapes(mSpriteData.getAllShapes().get(mNextShapeIdx));
        generateNextShape();
        notifyNextShapeChanged();

        int idx = ((int) (Math.random() * 100)) % COLORS.length;
        mSprite.setColor(COLORS[idx]);
        mView.invalidate();
    }

    private void generateNextShape() {
        mNextShapeIdx = ((int) (Math.random() * 1000)) % mSpriteData.getAllShapes().size();
    }

    private void notifyNextShapeChanged() {
        for (OnNextShapeOccurredListener listener : mNextShapeListeners) {
            listener.onNextShape(mNextShapeIdx);
        }
    }

    @Override
    public void onStart() {
        mScene.reset();
        resetSprite();
        mView.addDrawable(mSprite);
        mTimer.startLoop(0, mInterval, this);
    }

    @Override
    public void onPause() {
        mTimer.cancel();
    }

    @Override
    public void onReset() {
        mTimer.cancel();
    }

    @Override
    public void onQuit() {
        mTimer.destroy();
        mView.exit();
    }

    @Override
    public void register(OnWonderfulListener listener) {
        if (listener != null && !mScoreListeners.contains(listener)) {
            mScoreListeners.add(listener);
        }
    }

    @Override
    public void unregister(OnWonderfulListener listener) {
        if (listener != null) {
            mScoreListeners.remove(listener);
        }
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
    public void onMoveLeft() {
        if (mChecker.canMoveLeft(mSprite, mScene)) {
            mSprite.moveLeft();
            mView.invalidate();
        }
    }

    @Override
    public void onMoveRight() {
        if (mChecker.canMoveRight(mSprite, mScene)) {
            mSprite.moveRight();
            mView.invalidate();
        }
    }

    private void onAchieveRows(int rows) {
        Logs.d(TAG, "onAchieveRows:" + rows);
        for (OnWonderfulListener listener : mScoreListeners) {
            if (listener != null) {
                listener.onAchieve(rows);
            }
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
            return;
        }

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
        if (mView.hasDrawable(mSprite) && mChecker.canTransform(mSprite, mScene)) {
            mSprite.next();
            mView.invalidate();
        }
    }

    @Override
    public void onGameOver() {
        mTimer.cancel();
        Toast.makeText(mView.getContext(), "Game Over", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTick(int interval) {
        onMoveDown();
    }
}
