package game.engine.drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KShapeData {
    private int mRows;
    private int mCols;
    private int mOccupiedRows;
    private int mOccupiedCols;
    private List<Integer> mData;

    public KShapeData(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new RuntimeException("rows and cols must be positive");
        }
        mRows = rows;
        mCols = cols;
        mData = new ArrayList<>(rows * cols);
    }

    public void setData(Collection<Integer> data) {
        if (data == null || data.size() != mRows * mCols) {
            throw new RuntimeException("data size must be rows * cols");
        }
        mData.clear();
        mData.addAll(data);
        calculateRealOccupied();
    }

    public void setData(int[] data) {
        if (data == null || data.length != mRows * mCols) {
            throw new RuntimeException("data size must be rows * cols");
        }
        mData.clear();
        for (int d : data) {
            mData.add(d);
        }
        calculateRealOccupied();
    }

    private void calculateRealOccupied() {
        int realRow = mRows;
        for (int i = mRows - 1; i >= 1; i--) {
            int count = 0;
            for (int j = 0; j < mCols; j++) {
                count += getValue(i, j);
            }
            if (count > 0) {
                break;
            }
            realRow--;
        }

        int realCols = mCols;
        for (int i = mCols - 1; i >= 1; i--) {
            int count = 0;
            for (int j = 0; j < mRows; j++) {
                count += getValue(j, i);
            }
            if (count > 0) {
                break;
            }
            realCols--;
        }

        mOccupiedCols = realCols;
        mOccupiedRows = realRow;
    }

    public int getRows() {
        return mRows;
    }

    public int getOccupiedRows() {
        return mOccupiedRows;
    }

    public int getOccupiedCols() {
        return mOccupiedCols;
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
            resetRow(i);
        }
    }

    public void resetRow(int row) {
        setRowValue(row, 0);
    }

    public void setRowValue(int row, int value) {
        if (row < 0 || row >= mRows) {
            return;
        }
        for (int i = 0; i < mCols; i++) {
            setValue(row, i, value);
        }
    }

    public void copyRow(int rowSrc, int rowDst) {
        if (rowSrc < 0 || rowSrc >= mRows || rowDst < 0 || rowDst >= mRows) {
            throw new IllegalArgumentException("rowSrc or rowDst is invalid !");
        }
        if (rowSrc == rowDst) {
            return;
        }

        for (int i = 0; i < mCols; i++) {
            setValue(rowDst, i, getValue(rowSrc, i));
        }
    }
}
