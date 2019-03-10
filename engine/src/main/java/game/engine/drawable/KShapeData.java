package game.engine.drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KShapeData {
    private int mRows;
    private int mCols;
    private List<Integer> mData;

    public KShapeData(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new RuntimeException("rows and cols must be positive");
        }
        mRows = rows;
        mCols = cols;
        mData = new ArrayList<>(rows * cols);
        reset();
    }

    public void setData(Collection<Integer> data) {
        if (data == null || data.size() != mRows * mCols) {
            throw new RuntimeException("data size must be rows * cols");
        }
        mData.clear();
        mData.addAll(data);
    }

    public void setData(int[] data) {
        if (data == null || data.length != mRows * mCols) {
            throw new RuntimeException("data size must be rows * cols");
        }
        mData.clear();
        for (int d : data) {
            mData.add(d);
        }
    }

    public int getRows() {
        return mRows;
    }

    public int getCols() {
        return mCols;
    }

    public List<Integer> getData() {
        return mData;
    }

    public int getValue(int row, int col) {
        if (row < 0 || row >= mRows || col < 0 || col >= mCols) {
            return -1;
        }
        return mData.get(row * mCols + col);
    }

    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= mRows || col < 0 || col >= mCols) {
            return;
        }
        mData.set(row * mCols + col, value);
    }

    public void reset() {
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mCols; j++) {
                mData.add(0);
            }
        }
    }
}
