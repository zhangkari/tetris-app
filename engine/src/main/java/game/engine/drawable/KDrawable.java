package game.engine.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class KDrawable {

    protected int width;
    protected int height;
    protected int padding;

    protected Paint paint;
    protected int color;
    protected Paint.Style style;

    public KDrawable(int width, int height) {
        this.width = width;
        this.height = height;
        paint = new Paint();
    }

    public void setStyle(Paint.Style style) {
        if (style != null) {
            paint.setStyle(style);
            this.style = style;
        }
    }

    public void setColor(int color) {
        paint.setColor(color);
        this.color = color;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public abstract void onDraw(Canvas canvas);

    public void onDestroy() {

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
