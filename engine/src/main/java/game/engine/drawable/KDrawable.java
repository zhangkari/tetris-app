package game.engine.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class KDrawable {

    protected int width;
    protected int height;

    protected Paint paint;

    public KDrawable(int width, int height) {
        this.width = width;
        this.height = height;
        paint = new Paint(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
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
