package game.engine.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class KDrawable {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Context context;
    protected Paint paint;

    public KDrawable(Context context, int x, int y, int width, int height) {
        this.context = context;
        paint = new Paint();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


    }

    public boolean test(float x, float y) {
        boolean ret = false;
        if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height) {
            ret = true;
        }

        return ret;
    }

    public abstract void Draw(Canvas canvas);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
