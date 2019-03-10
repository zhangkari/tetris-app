package game.tetris.sprite;

import android.graphics.Bitmap;

import game.engine.drawable.KBitmap;
import game.engine.drawable.KDrawable;

public class BitmapShape extends RectShape {
    private Bitmap mBitmap;

    public BitmapShape(int width, int height) {
        super(width, height);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        if (mTileSize == 0) {
            mTileSize = bitmap.getWidth();
        }
    }

    @Override
    public KDrawable onCreateShapeItem() {
        return new KBitmap(mBitmap, mTileSize, mTileSize);
    }

    @Override
    public void onDestroy() {
        if (mBitmap != null) {
            if (!mBitmap.isRecycled()) {
                mBitmap.recycle();
            }
            mBitmap = null;
        }
    }
}
