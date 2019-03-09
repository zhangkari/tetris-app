package game.tetris.ui;

import game.engine.layer.KDrawable;
import game.tetris.utils.Storages;
import game.tetris.utils.Colors;
import game.engine.utils.SoundManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;

public class GamePanel extends KDrawable implements Runnable {

    private int tileSize;

    private Bitmap mBitmap;

    private int[][] info;

    private int nScore = 0;
    private int nLevel = 1;

    private boolean mHeartOn = false;
    private boolean mMusicOn = false;
    private boolean isRun = true;

    public GamePanel(Context context, int x, int y, int width, int height) {
        super(context, x, y, width, height);

        paint.setARGB(80, 80, 80, 0);
        paint.setStyle(Style.STROKE);

        mBitmap = ((BitmapDrawable) (context.getResources().getDrawable(com.minmin.kari.tetris.R.drawable.red_heart))).getBitmap();
        info = new int[height / CubeSprite.CUBE_SIZE][width / CubeSprite.CUBE_SIZE];
        for (int i = 0; i < height / CubeSprite.CUBE_SIZE; ++i) {
            for (int j = 0; j < width / CubeSprite.CUBE_SIZE; ++j) {
                info[i][j] = 0;
            }
        }

        mHeartOn = Storages.getInstance(context).isHeartOn();
        mMusicOn = Storages.getInstance(context).isPlayMusic();

        Thread t = new Thread(this);
        t.start();
    }

    public GamePanel setTileSize(int tileSize) {
        this.tileSize = tileSize;
        return this;
    }

    public void resetPaint() {
        paint.setARGB(255, 20, 20, 0);
        paint.setStyle(Style.STROKE);
    }

    @Override
    public void Draw(Canvas canvas) {
        for (int i = y; i < y + height; i += tileSize) {
            for (int j = x; j < x + width; j += tileSize) {
                resetPaint();
                canvas.drawRect(new Rect(j, i, j + tileSize, i + tileSize), paint);

                int color = info[(i - y) / CubeSprite.CUBE_SIZE][(j - x) / CubeSprite.CUBE_SIZE];
                if (color != 0) {
                    if (mHeartOn) {
                        canvas.drawBitmap(mBitmap, null, new Rect(j + 2, i + 2, j + tileSize - 2, i + tileSize - 2), paint);
                    } else {
                        Colors.setColor(paint, color);
                        paint.setStyle(Style.FILL);
                        canvas.drawRect(j + 1, i + 1, j - 1 + tileSize, i - 1 + tileSize, paint);
                    }
                }
            }
        }
    }

    public int[][] getInfo() {
        return this.info;
    }

    public void setInfo(int row, int cols, int value) {
        info[row][cols] = value;
    }

    public int getInfoData(int row, int cols) {
        return info[row][cols];
    }

    public int getnScore() {
        return nScore;
    }

    public int getnLevel() {
        return nLevel;
    }

    @Override
    public void run() {
        while (isRun) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = info.length - 1; i >= 0; --i) {
                if (isFullRow(i)) {
                    if (mMusicOn) {
                        SoundManager.getInstance(context).play(com.minmin.kari.tetris.R.raw.good);
                    }

                    nScore += 10;
                    nLevel = nScore / 100 + 1;
                    resetRow(i);
                }
            }

        }
    }

    private boolean isFullRow(int row) {
        for (int i = 0; i < info[0].length; ++i) {
            if (info[row][i] == 0)
                return false;
        }
        return true;
    }


    private void resetRow(int row) {
        int k = 0;

        for (int i = 0; i < info[0].length; ++i) {
            info[row][i] = 0;
        }

        for (int i = 0; i < info[0].length; ++i) {
            for (int j = row; j < info.length; ++j) {
                if (j == info.length - 1) {
                    k = 1;
                    break;
                } else if (info[j + 1][i] != 0) {
                    k = j + 1 - row;
                    break;
                }
            }

            for (int j = row - 1; j >= 0; --j) {
                info[j + k][i] = info[j][i];
            }
        }

    }

    public void free() {
        isRun = false;
    }
}
