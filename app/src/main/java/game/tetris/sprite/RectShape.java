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
        KDrawable d = new KRectangle(mTileSize, mTileSize);
        if (style != null) {
            d.setStyle(style);
        }
        if (color != 0) {
            d.setColor(color);
        }
        return d;
    }
}
