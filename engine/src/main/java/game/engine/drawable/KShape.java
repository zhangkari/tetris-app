package game.engine.drawable;

import android.graphics.Canvas;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public abstract class KShape extends KRectangle {
    protected KShapeData mData;
    protected List<KDrawable> mDrawables;

    protected int mTileSize;
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

    public void reset() {
        for (int i = 0; i < mData.getRows(); i++) {
            for (int j = 0; j < mData.getCols(); j++) {
                mData.setValue(i, j, 0);
            }
        }
    }

    public void setTileSize(int size) {
        mTileSize = size;
    }

    public int getTileSize() {
        return mTileSize;
    }

    private void createDrawables() {
        for (int i = 0; i < mData.getRows(); i++) {
            for (int j = 0; j < mData.getCols(); j++) {
                KDrawable drawable = onCreateShapeItem(i, j);
                drawable.setPadding(padding);
                mDrawables.add(drawable);
            }
        }
    }

    public abstract KDrawable onCreateShapeItem(int row, int cols);

    @Override
    public final void onDraw(Canvas canvas) {
        if (mData == null) {
            return;
        }
        canvas.save();
        canvas.translate(-x, -y);
        for (int i = 0; i < mData.getRows(); i++) {
            for (int j = 0; j < mData.getCols(); j++) {
                onDraw(canvas, mDrawables.get(i * mData.getCols() + j), i, j);
                canvas.translate(mDrawables.get(0).width, 0);
            }
            canvas.translate(-mDrawables.get(0).width * mData.getCols(), mDrawables.get(0).height);
        }
        canvas.restore();
    }

    protected void onDraw(Canvas canvas, KDrawable d, int row, int col) {
        if (d != null && canvas != null) {
            d.onDraw(canvas);
        }
    }
}
