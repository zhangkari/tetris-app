package game.tetris.view;

import android.graphics.Color;
import android.graphics.Paint;

import game.engine.RenderView;
import game.tetris.Constants;
import game.tetris.data.SpriteData;
import game.tetris.sprite.GridLayer;
import game.tetris.sprite.RectShape;
import game.tetris.sprite.SceneLayer;
import game.tetris.sprite.Sprite;

class SpriteHolder implements SpriteListener {
    private RenderView mView;
    private GridLayer mBgGrid;
    private RectShape mScene;
    private Sprite mSprite;

    @Override
    public void onInitialized(RenderView view, int width, int height) {
        mView = view;
        mBgGrid = new GridLayer(width, height);
        mBgGrid.setInterval(width / Constants.SCENE_COLS);
        view.addDrawable(mBgGrid);

        mScene = new SceneLayer(width, height);
        view.addDrawable(mScene);

        mSprite = new Sprite(width / Constants.SCENE_COLS * 4, height / Constants.SCENE_ROWS * 4);
        mSprite.setTileSize(width / Constants.SCENE_COLS);
        mSprite.setColor(Color.RED);
        mSprite.setStyle(Paint.Style.FILL);
        mSprite.setPadding(4);
    }

    private void resetSpritePosition() {
        mSprite.setXY(Constants.SCENE_COLS / 2 - 2, 0);
        mSprite.setShapes(new SpriteData().getSpriteData());
    }

    @Override
    public void onAchieve(int row) {

    }

    @Override
    public void onStart() {
        mScene.reset();
        resetSpritePosition();
        if (!mView.hasDrawable(mSprite)) {
            mView.addDrawable(mSprite);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onReset() {

    }

    @Override
    public void onQuit() {
        mView.exit();
    }

    @Override
    public void onMoveLeft() {
        mSprite.translate(mSprite.getTileSize(), 0);
    }

    @Override
    public void onMoveRight() {
        mSprite.translate(-mSprite.getTileSize(), 0);
    }

    @Override
    public void onMoveDown() {
        mSprite.translate(0, -mSprite.getTileSize());
    }

    @Override
    public void onTransform() {
        mSprite.next();
    }
}
