package game.engine.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class KString extends KDrawable {

    private String text;
    private int fontHeight;

    private int color;

    private boolean isDrawBorder = false;

    public KString(Context context, int x, int y, String text) {
        super(context, x, y, 0, 0);

        this.text = text;

        color = Color.BLUE;
        paint.setTextAlign(Align.LEFT);
        paint.setStyle(Style.FILL);

        setColor(color);
        setTextSize(15);

    }

    public KString drawBorder(boolean flag) {
        this.isDrawBorder = flag;
        return this;
    }

    public KString setTextSize(float size) {
        paint.setTextSize(size);

        FontMetrics fm = paint.getFontMetrics();
        fontHeight = (int) (fm.ascent - fm.descent) + 4;

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        this.width = rect.width();
        this.height = rect.height() + 10;

        return this;
    }

    public KString setColor(int color) {
        this.color = color;
        paint.setColor(color);
        return this;
    }

    public int getColor() {
        return this.color;
    }

    public KString setString(String text) {
        this.text = text;
        return this;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawText(text, x, y - fontHeight, paint);
        if (isDrawBorder) {
            canvas.drawRect(x, y, x + width, y + height, paint);
        }
    }
}
