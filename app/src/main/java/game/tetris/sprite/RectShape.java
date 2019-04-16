package game.tetris.sprite;

import game.engine.drawable.KDrawable;
import game.engine.drawable.KRectangle;
import game.engine.drawable.KShape;

public class RectShape extends KShape {
    public RectShape(int width, int height) {
        super(width, height);
    }

    @Override
    public KDrawable onCreateShapeItem(int row, int col) {
        return new KRectangle(mTileSize, mTileSize);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        for (KDrawable d : mDrawables) {
            d.setColor(color);
        }
    }
}
