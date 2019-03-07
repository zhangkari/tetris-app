package game.tetris.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;

import game.engine.layer.KDrawable;
import game.engine.layer.KShape;

public class GameStick extends KDrawable {

    public static final int Up = 0;
    public static final int Down = 1;
    public static final int Left = 2;
    public static final int Right = 3;

    private KShape rectLeft;
    private KShape rectRight;
    private KShape rectUp;
    private KShape rectDown;

    public GameStick(Context context, int x, int y, int width, int height) {
        super(context, x, y, width, height);
        // TODO Auto-generated constructor stub
		
		/*
		rectLeft.x = x;
		rectLeft.y = y + height/3;
		rectLeft.width = width/3;
		rectLeft.height = height/3;
		*/
        rectLeft = new KShape(context, x, y + height / 3, width / 3, height / 3).setColor(Color.GRAY).setStyle(Style.FILL);
		
		/*
		rectRight.x = x + width*2/3;
		rectRight.y = y + height/3;
		rectRight.width = width/3;
		rectRight.height = height/3;
		*/
        rectRight = new KShape(context, x + width * 2 / 3, y + height / 3, width / 3, height / 3).setColor(Color.GRAY).setStyle(Style.FILL);
		
		
		/*
		rectUp.x = x + width/3;
		rectUp.y = y ;
		rectUp.width = width/3;
		rectUp.height = height/3;
		*/
        rectUp = new KShape(context, x + width / 3, y, width / 3, height / 3).setColor(Color.GRAY).setStyle(Style.FILL);
		
		/*
		rectDown.x = x + width/3;
		rectDown.y = y + height*2/3;
		rectDown.width = width/3;
		rectDown.height = height/3;
		*/
        rectDown = new KShape(context, x + width / 3, y + height * 2 / 3, width / 3, height / 3).setColor(Color.GRAY).setStyle(Style.FILL);
    }

    @Override
    public void Draw(Canvas canvas) {
        // TODO Auto-generated method stub
        rectLeft.Draw(canvas);
        rectRight.Draw(canvas);
        rectUp.Draw(canvas);
        rectDown.Draw(canvas);
    }

    private void setFocus(int i) {
        switch (i) {
            case Left:
                rectLeft.setColor(Color.WHITE);
                rectRight.setColor(Color.GRAY);
                rectUp.setColor(Color.GRAY);
                rectDown.setColor(Color.GRAY);
                break;
            case Right:
                rectRight.setColor(Color.WHITE);
                rectLeft.setColor(Color.GRAY);
                rectUp.setColor(Color.GRAY);
                rectDown.setColor(Color.GRAY);
                break;
            case Up:
                rectUp.setColor(Color.WHITE);
                rectRight.setColor(Color.GRAY);
                rectLeft.setColor(Color.GRAY);
                rectDown.setColor(Color.GRAY);
                break;
            case Down:
                rectDown.setColor(Color.WHITE);
                rectRight.setColor(Color.GRAY);
                rectUp.setColor(Color.GRAY);
                rectLeft.setColor(Color.GRAY);
                break;
        }
    }

    public int handleTouchEvent(float x, float y) {
        int ret = -1;

        if (rectLeft.test(x, y))
            ret = Left;
        else if (rectRight.test(x, y))
            ret = Right;
        else if (rectUp.test(x, y))
            ret = Up;
        else if (rectDown.test(x, y))
            ret = Down;

        if (ret != -1)
            setFocus(ret);

        return ret;
    }
}
