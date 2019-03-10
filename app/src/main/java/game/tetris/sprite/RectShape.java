package game.tetris.sprite;

import android.graphics.Color;
import android.graphics.Paint;

import game.engine.drawable.KDrawable;
import game.engine.drawable.KRectangle;
import game.engine.drawable.KShape;

public class RectShape extends KShape {
    protected int mTileSize;

    public RectShape(int width, int height) {
        super(width, height);
    }

    public void setTileSize(int size) {
        mTileSize = size;
    }

    @Override
    public KDrawable onCreateShapeItem() {
        return new KRectangle(mTileSize, mTileSize)
                .setStyle(Paint.Style.FILL)
                .setColor(Color.BLACK);
    }
}
