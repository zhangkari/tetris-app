package game.tetris.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import game.engine.drawable.KDrawable;
import game.tetris.Constants;
import game.tetris.data.SceneData;

public class SceneLayer extends RectShape {
    public SceneLayer(int width, int height) {
        super(width, height);
        setTileSize(width / Constants.SCENE_COLS);
        setPadding(4);
        setShapeData(SceneData.getInstance().getShapeData());
    }

    @Override
    protected void onDraw(Canvas canvas, KDrawable d, int row, int col) {
        if (d == null || canvas == null) {
            return;
        }
        d.setStyle(Paint.Style.FILL);
        if (mData.getValue(row, col) == 1) {
            d.setColor(Color.WHITE);
        } else {
            d.setColor(Color.BLACK);
        }
        d.onDraw(canvas);
    }
}
