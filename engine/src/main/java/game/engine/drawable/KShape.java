package game.engine.drawable;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public abstract class KShape extends KRectangle {
    private KShapeData mData;
    private List<KDrawable> mDrawables;

    protected int x;
    protected int y;

    public KShape(int width, int height) {
        super(width, height);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void setShapeData(KShapeData data) {
        if (data == null) {
            return;
        }
        mData = data;
        mDrawables = new ArrayList<>(data.getRows() * data.getCols());
        createDrawables();
    }

    private void createDrawables() {
        for (int i = 0; i < mData.getRows(); i++) {
            for (int j = 0; j < mData.getCols(); j++) {
                KDrawable drawable = onCreateShapeItem();
                mDrawables.add(drawable);
            }
        }
    }

    public abstract KDrawable onCreateShapeItem();

    @Override
    public void onDraw(Canvas canvas) {
        if (mData == null) {
            return;
        }
        canvas.save();
        canvas.translate(-x, -y);
        for (int i = 0; i < mData.getRows(); i++) {
            for (int j = 0; j < mData.getCols(); j++) {
                if (mData.getValue(i, j) > 0) {
                    mDrawables.get(i * mData.getCols() + j).onDraw(canvas);
                }
                canvas.translate(mDrawables.get(0).width, 0);
            }
            canvas.translate(-mDrawables.get(0).width * mData.getCols(), mDrawables.get(0).height);
        }
        canvas.restore();
    }
}
