package game.engine.drawable;

import android.graphics.Canvas;
import android.graphics.Paint.Style;

public class KRectangle extends KDrawable {

    public KRectangle(int width, int height) {
        super(width, height);
    }

    public KRectangle setStyle(Style style) {
        paint.setStyle(style);
        return this;
    }

    public KRectangle setColor(int color) {
        paint.setColor(color);
        return this;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, width, height, paint);
    }
}
