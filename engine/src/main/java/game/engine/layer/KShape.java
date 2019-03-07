package game.engine.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;

public class KShape extends KDrawable {

    public KShape(Context context, int x, int y, int width, int height) {
        this(context,
                x,
                y,
                width,
                height,
                Color.argb(255, 200, 0, 200),
                Style.STROKE);
    }

    public KShape(Context context, int x, int y, int width, int height, int color, Style style) {
        super(context, x, y, width, height);

        paint.setStyle(style);
        paint.setColor(color);
    }

    public KShape setStyle(Style style) {
        paint.setStyle(style);
        return this;
    }

    public KShape setColor(int color) {
        paint.setColor(color);
        return this;
    }

    public void Draw(Canvas canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

}
