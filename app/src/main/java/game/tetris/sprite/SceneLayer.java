package game.tetris.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import game.engine.drawable.KDrawable;
import game.tetris.Constants;

public class SceneLayer extends RectShape {
    public SceneLayer(int width, int height) {
        super(width, height);
        setTileSize(width / Constants.SCENE_COLS);
        setPadding(4);
    }

    @Override
    protected void onDraw(Canvas canvas, KDrawable d, int row, int col) {
        if (d == null || canvas == null) {
            return;
        }
        d.setStyle(Paint.Style.FILL);
        int value = mData.getValue(row, col);
        if (value == 0) {
            d.setColor(Color.BLACK);
        } else {
            d.setColor(value);
        }
        d.onDraw(canvas);
    }
}
