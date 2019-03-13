package game.tetris.sprite;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import game.engine.drawable.KDrawable;
import game.engine.drawable.KShapeData;

public class Sprite extends RectShape {
    private int mCurrent;
    private List<KShapeData> mShapes;

    public Sprite(int width, int height) {
        super(width, height);
        mCurrent = 0;
        mShapes = new ArrayList<>(16);
    }

    public void setShapes(List<KShapeData> shapes) {
        mShapes.clear();
        if (shapes != null) {
            mShapes.addAll(shapes);
        }
        reset();
    }

    public void addShapes(List<KShapeData> shapes) {
        if (shapes != null) {
            mShapes.addAll(shapes);
        }
        reset();
    }

    public void reset() {
        if (mShapes == null || mShapes.size() < 1) {
            return;
        }
        mCurrent = 0;
        setShapeData(mShapes.get(0));
    }

    public void next() {
        if (mShapes == null || mShapes.size() < 1) {
            return;
        }
        if (mCurrent + 1 < mShapes.size()) {
            mCurrent++;
        } else {
            mCurrent = 0;
        }
        setShapeData(mShapes.get(mCurrent));
    }

    @Override
    protected void onDraw(Canvas canvas, KDrawable d, int row, int col) {
        if (d == null || canvas == null) {
            return;
        }
        if (mData.getValue(row, col) > 0) {
            d.onDraw(canvas);
        }
    }
}
