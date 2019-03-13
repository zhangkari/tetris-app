package game.engine.drawable;

import android.graphics.Canvas;

public class KRectangle extends KDrawable {

    public KRectangle(int width, int height) {
        super(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(padding, padding, width - padding, height - padding, paint);
    }
}