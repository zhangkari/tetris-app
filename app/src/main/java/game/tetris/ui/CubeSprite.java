package game.tetris.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;

import game.engine.layer.KSprite;
import game.tetris.utils.StorageUtil;
import game.tetris.utils.ColorUtil;

public class CubeSprite extends KSprite {

    private int nColor = 1;
    private final int nColors = 4;
    private int nShapeIndex;
    public static final int nShapes = 19;

    private Bitmap mBitmap;

    private boolean mHeartOn = false;

    private int[] shapeData = CubeSprite.shapeDatas[nShapeIndex];

    public CubeSprite(Context context) {
        this(context, 150, 60, 0);
    }

    public CubeSprite(Context context, int nShapeIndex) {
        this(context, 150, 60, nShapeIndex);
    }

    public CubeSprite(Context context, int x, int y) {
        this(context, x, y, 0);
    }

    public CubeSprite(Context context, int x, int y, int nShapeIndex) {
        super(context);

        ColorUtil.setColor(paint, nColor);
        paint.setStyle(Style.FILL);

        this.x = x;
        this.y = y;
        this.nShapeIndex = nShapeIndex;
        mBitmap = ((BitmapDrawable) (context.getResources().getDrawable(com.minmin.kari.tetris.R.drawable.red_heart))).getBitmap();

        mHeartOn = StorageUtil.getInstance(context).isHeartOn();

        initData();
    }


    public void reshape() {
        ++nShapeIndex;

        if (nShapeIndex == 1)
            nShapeIndex = 0;

        switch (nShapeIndex) {
            case 1:
                nShapeIndex = 0;
                break;
            case 3:
                nShapeIndex = 1;
                break;
            case 7:
                nShapeIndex = 3;
                break;
            case 9:
                nShapeIndex = 7;
                break;
            case 13:
                nShapeIndex = 9;
                break;
            case 15:
                nShapeIndex = 13;
                break;
            case 19:
                nShapeIndex = 15;
                break;
        }

        initData();
    }


    public CubeSprite reset() {
        x = 150;
        y = 60;

        nColor = ((int) (Math.random() * 10)) % nColors;

        setColor(nColor);
        initData();

        return this;
    }

    public CubeSprite setColor(int color) {
        this.nColor = color;
        ColorUtil.setColor(paint, color);
        return this;
    }

    public int getColor() {
        return this.nColor;
    }

    public CubeSprite setShape(int nShapeIndex) {
        this.nShapeIndex = nShapeIndex;
        initData();
        return this;
    }

    @Override
    public void Draw(Canvas canvas) {

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (shapeData[i * 4 + j] == 1) {
                    Rect rect = new Rect();
                    rect.top = y + i * CubeSprite.CUBE_SIZE + 1;
                    rect.left = x + j * CubeSprite.CUBE_SIZE + 1;
                    rect.bottom = rect.top + CubeSprite.CUBE_SIZE - 2;
                    rect.right = rect.left + CubeSprite.CUBE_SIZE - 2;

                    if (mHeartOn) {
                        canvas.drawBitmap(mBitmap, null, rect, paint);
                    } else {
                        canvas.drawRect(rect, paint);
                    }

                }
            }
        }
    }

    private void initData() {
        shapeData = shapeDatas[nShapeIndex];

        boolean flag = false;

        for (int j = 3; j >= 0; --j) {
            for (int i = 0; i < 4; ++i) {
                if (shapeData[i * 4 + j] == 1) {
                    this.width = (j + 1) * CubeSprite.CUBE_SIZE;
                    flag = true;
                    break;
                }
            }

            if (flag)
                break;
        }

        flag = false;

        for (int i = 3; i >= 0; --i) {
            for (int j = 0; j < 4; ++j) {
                if (shapeData[i * 4 + j] == 1) {
                    this.height = (i + 1) * CubeSprite.CUBE_SIZE;
                    flag = true;
                    break;
                }
            }
            if (flag)
                break;
        }

    }


    public void copyFrom(CubeSprite sprite) {
        this.x = sprite.x;
        this.y = sprite.y;
        this.width = sprite.width;
        this.nColor = sprite.nColor;
        this.height = sprite.height;
        this.shapeData = sprite.shapeData;
        this.nShapeIndex = sprite.nShapeIndex;
    }

    public int[] getShapeData() {
        return this.shapeData;
    }

    public static int[][] shapeDatas =
            {
                    /*
                     * No.0
                     *
                     00
                     00
                     */
                    {
                            1, 1, 0, 0,
                            1, 1, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.1
                     *
                     00
                    00
                     */
                    {
                            0, 1, 1, 0,
                            1, 1, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.2
                     *
                    0
                    00
                     0
                    */
                    {
                            1, 0, 0, 0,
                            1, 1, 0, 0,
                            0, 1, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.3
                     *
                     0
                     00
                     0
                     */
                    {
                            1, 0, 0, 0,
                            1, 1, 0, 0,
                            1, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.4
                     *
                     000
                      0
                     */
                    {
                            1, 1, 1, 0,
                            0, 1, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.5
                      0
                     00
                      0
                     */
                    {
                            0, 1, 0, 0,
                            1, 1, 0, 0,
                            0, 1, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.6
                     *

                      0
                     000

                     */
                    {
                            0, 1, 0, 0,
                            1, 1, 1, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.7
                     *
                     0000
                     */
                    {
                            1, 1, 1, 1,
                            0, 0, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.8
                     *
                     0
                     0
                     0
                     0
                     */
                    {
                            1, 0, 0, 0,
                            1, 0, 0, 0,
                            1, 0, 0, 0,
                            1, 0, 0, 0
                    },

                    /*
                     * No.9
                     *
                     0
                     0
                     00
                     */
                    {
                            1, 0, 0, 0,
                            1, 0, 0, 0,
                            1, 1, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.10
                     *
                       000
                       0
                    */
                    {
                            1, 1, 1, 0,
                            1, 0, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.11
                     *
                       00
                        0
                        0
                    */
                    {
                            1, 1, 0, 0,
                            0, 1, 0, 0,
                            0, 1, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.12
                     *
                       0
                     000
                     */
                    {
                            0, 0, 1, 0,
                            1, 1, 1, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.13
                     *
                     00
                      00
                     */
                    {
                            1, 1, 0, 0,
                            0, 1, 1, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.14
                     *
                     0
                    00
                    0
                    */
                    {
                            0, 1, 0, 0,
                            1, 1, 0, 0,
                            1, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.15
                     *
                     0
                     0
                    00
                     */
                    {
                            0, 1, 0, 0,
                            0, 1, 0, 0,
                            1, 1, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.16
                     *
                       0
                       000
                    */
                    {
                            1, 0, 0, 0,
                            1, 1, 1, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.17
                     *
                        00
                        0
                        0
                    */
                    {
                            0, 1, 1, 0,
                            0, 1, 0, 0,
                            0, 1, 0, 0,
                            0, 0, 0, 0
                    },

                    /*
                     * No.18
                     *
                     000
                       0
                     */
                    {
                            1, 1, 1, 0,
                            0, 0, 1, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                    }
            };


}

