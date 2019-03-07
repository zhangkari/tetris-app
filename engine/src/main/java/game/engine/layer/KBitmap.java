package game.engine.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class KBitmap extends KDrawable {

    private int id;

    public KBitmap(Context context, int x, int y, int width, int height) {
        super(context, x, y, width, height);

    }

    public KBitmap setId(int id) {
        this.id = id;
        return this;
    }

    public void Draw(Canvas canvas) {
        canvas.drawBitmap(
                ((BitmapDrawable) (context.getResources().getDrawable(id))).getBitmap(),
                null,
                new Rect(x, y, x + width, y + height),
                paint);
    }

}
