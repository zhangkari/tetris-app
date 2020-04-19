package game.tetris.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import game.engine.drawable.KShapeData;
import game.tetris.data.Constants;

public class MatrixView extends View {

    private static final int GAP = 1;

    private KShapeData mData;
    private Paint mPaint;

    private float initX;
    private float initY;

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mData = new KShapeData(4, 4);
        mPaint = new Paint();
        mPaint.setColor(Constants.COLORS[0]);

        post(new Runnable() {
            @Override
            public void run() {
                initX = getX();
                initY = getY();
            }
        });
    }

    public void resetLocation() {
        setX(initX);
        setY(initY);
    }

    public void setShapeData(KShapeData data) {
        mData = data;
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int tileSize = getWidth() / mData.getCols();
        KShapeData data = mData;

        if (data == null) {
            return;
        }
        canvas.save();
        for (int i = 0; i < data.getRows(); i++) {
            for (int j = 0; j < data.getCols(); j++) {
                if (data.getValue(i, j) != 0) {
                    canvas.drawRect(j * tileSize + GAP, i * tileSize + GAP, (j + 1) * tileSize - GAP, (i + 1) * tileSize - GAP, mPaint);
                }
            }
        }
        canvas.restore();
    }

    public float getInitX() {
        return initX;
    }

    public float getInitY() {
        return initY;
    }
}