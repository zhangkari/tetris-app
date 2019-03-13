package game.tetris.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import game.engine.drawable.KDrawable;

public class GridLayer extends KDrawable {
    private static final int PADDING = 1;
    private static final int GRID_COLOR = Color.parseColor("#eebbaa");
    private static final int BORDER_COLOR = Color.parseColor("#dd4455");

    private int mInterval;

    public GridLayer(int width, int height) {
        super(width, height);
    }

    public void setInterval(int interval) {
        mInterval = interval;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int rows = height / mInterval;
        int cols = width / mInterval;

        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(BORDER_COLOR);
        canvas.drawRect(0, 0, width, height, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(GRID_COLOR);
        for (int i = 1; i < rows; i++) {
            canvas.drawLine(PADDING, i * mInterval,
                    width - PADDING, i * mInterval,
                    paint);
        }

        for (int i = 1; i < cols; i++) {
            canvas.drawLine(i * mInterval, PADDING,
                    i * mInterval, height - PADDING,
                    paint);
        }
    }
}
