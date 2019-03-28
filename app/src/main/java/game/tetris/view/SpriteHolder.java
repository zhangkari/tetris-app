package game.tetris.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import game.engine.RenderView;
import game.tetris.Constants;
import game.tetris.collision.Checker;
import game.tetris.collision.SpriteChecker;
import game.tetris.data.SceneData;
import game.tetris.data.SpriteData;
import game.tetris.sprite.GridLayer;
import game.tetris.sprite.RectShape;
import game.tetris.sprite.SceneLayer;
import game.tetris.sprite.Sprite;
import game.tetris.utils.DownTimer;
import game.tetris.utils.Timer;

class SpriteHolder implements SpriteListener, Timer.OnTickListener {
    private RenderView mView;
    private RectShape mScene;
    private Sprite mSprite;
    private Checker mChecker;
    private Timer mTimer;
    private int mInterval;

    private static final int[] COLORS = {
            Color.YELLOW,
            Color.RED,
            Color.BLUE,
            Color.CYAN
    };

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
        mSprite.setColor(Color.RED);
        mSprite.setStyle(Paint.Style.FILL);
        mSprite.setPadding(4);

        mChecker = new SpriteChecker();
        mTimer = new DownTimer();
        mInterval = 800;
        mView.setRefreshHZ(2);
    }

    private void resetSprite() {
        int x = (Constants.SCENE_COLS / 2 - 2) * mSprite.getTileSize();
        mSprite.setXY(x, 0);
        mSprite.setShapes(new SpriteData().getSpriteData());
        int idx = ((int) (Math.random() * 100)) % COLORS.length;
        mSprite.setColor(COLORS[idx]);
        mView.addDrawable(mSprite);
    }

    @Override
    public void onAchieve(int row) {

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
        mTimer.cancel();
        mView.exit();
    }

    @Override
    public void onMoveLeft() {
        if (mChecker.canMoveLeft(mSprite, mScene)) {
            mSprite.moveLeft();
        }
    }

    @Override
    public void onMoveRight() {
        if (mChecker.canMoveRight(mSprite, mScene)) {
            mSprite.moveRight();
        }
    }

    @Override
    public void onMoveDown() {
        if (mChecker.canMoveBottom(mSprite, mScene)) {
            mSprite.moveDown();
        } else {
            if (mChecker.isGameOver(mSprite, mScene)) {
                onGameOver();
            } else {
                fillScene();
                mView.removeDrawable(mSprite);
                resetSprite();
            }
        }
    }

    private void fillScene() {
        int row = mSprite.getY() / mSprite.getTileSize();
        int col = mSprite.getX() / mSprite.getTileSize();
        int color = mSprite.getColor();
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

    @Override
    public void onTransform() {
        if (mView.hasDrawable(mSprite) && mChecker.canTransform(mSprite, mScene)) {
            mSprite.next();
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
