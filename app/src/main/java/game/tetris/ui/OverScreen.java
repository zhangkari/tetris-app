package game.tetris.ui;

import game.engine.layer.KView;
import game.engine.layer.KShape;
import game.engine.layer.KString;
import game.tetris.TetrisActivity;
import game.tetris.utils.Strings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.minmin.kari.tetris.R;

public class OverScreen extends FullScreen {

    private GameControl mGameControl;

    private KString stringNotice;
    private KString stringOK;
    private KShape rectBorder;

    private KView kView;

    public OverScreen(Context context, KView kView) {
        super(context);

        mGameControl = ((TetrisActivity) (context)).mGameControl;

        this.kView = kView;

        stringNotice = new KString(context, 150, 350, Strings.get(R.string.notice)).setColor(Color.WHITE).setTextSize(30);
        stringOK = new KString(context, 170, 430, "OK").setColor(Color.WHITE).setTextSize(30);
        rectBorder = new KShape(context, 100, 340, 200, 160).setColor(Color.WHITE);

        addDrawableObject(stringNotice);
        addDrawableObject(stringOK);
        addDrawableObject(rectBorder);
    }

    @Override
    public void onDraw(Canvas canvas) {
        kView.onDraw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (stringOK.test(event.getRawX(), event.getRawY())) {
            mGameControl.controlView(GameDefine.Game_Menu);
        }
        return true;
    }

    @Override
    public void reCycle() {

    }

    @Override
    public void refresh() {

    }
}
