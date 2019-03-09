package game.engine.layer;

import android.content.Context;

public abstract class KSprite extends KDrawable {

    public final static int CUBE_SIZE = 30;

    public KSprite(Context context) {
        super(context, 200, 30, CUBE_SIZE, CUBE_SIZE);
    }

    public KSprite(Context context, int x, int y) {
        super(context, x, y, CUBE_SIZE, CUBE_SIZE);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changePosition(int x, int y) {
        this.x += x;
        this.y += y;
    }
}

