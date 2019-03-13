package game.engine.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class KBitmap extends KDrawable {

    private Bitmap mBitmap;
    private Rect mDestRect;

    public KBitmap(Bitmap bitmap) {
        this(bitmap, bitmap.getWidth(), bitmap.getHeight());
    }

    public KBitmap(Bitmap bitmap, int width, int height) {
        super(width, height);
        mBitmap = bitmap;
        mDestRect = new Rect(padding, padding, width - padding, height - padding);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(
                mBitmap,
                null,
                mDestRect,
                paint);
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
