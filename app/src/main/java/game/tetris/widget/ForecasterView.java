package game.tetris.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import game.engine.drawable.KShapeData;
import game.tetris.data.Constants;
import game.tetris.data.SpriteData;

public class ForecasterView extends View {

    private static final int GAP = 1;

    private SpriteData mSpriteData;
    private int mShapeIdx;
    private Paint mPaint;

    public ForecasterView(Context context) {
        this(context, null);
    }

    public ForecasterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ForecasterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSpriteData = new SpriteData();
        mPaint = new Paint();
        mPaint.setColor(Constants.COLORS[0]);
    }

    public void setShapeIndexAndColor(int idx, int color) {
        mPaint.setColor(color);
        if (idx >= 0 && idx < mSpriteData.getAllShapes().size()) {
            mShapeIdx = idx;
            invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        int tileSize = getWidth() / 4;
        KShapeData data = mSpriteData.getAllShapes().get(mShapeIdx).get(0);

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
}