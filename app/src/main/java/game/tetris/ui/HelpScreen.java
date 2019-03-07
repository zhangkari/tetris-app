package game.tetris.ui;

import game.engine.layer.KView;
import game.engine.layer.KBitmap;
import game.engine.layer.KString;
import game.tetris.TetrisActivity;

import android.content.Context;
import android.view.MotionEvent;

public class HelpScreen extends KView {

    private GameControl mGameControl = null;

    private KString KStringOK;


    public HelpScreen(Context context) {
        super(context);

        int xLeft = 120;
        int yTop = 150;
        int Text_Space = 30;


        addDrawableObject(new KString(context, 80, yTop, "Help"));
        addDrawableObject(new KString(context, 20, yTop + Text_Space, "1."));
        addDrawableObject(new KString(context, 20, yTop + 2 * Text_Space, "2."));
        addDrawableObject(new KString(context, 20, yTop + 3 * Text_Space, "3."));
        addDrawableObject(new KString(context, 20, yTop + 4 * Text_Space, "4."));

        KStringOK = new KString(context, xLeft, yTop + 5 * Text_Space, "OK").setTextSize(20);
        addDrawableObject(KStringOK);

        TetrisActivity activity = (TetrisActivity) context;
        mGameControl = activity.mGameControl;

        addDrawableObject(new KBitmap(context, 120, 60, 80, 80).setId(com.minmin.kari.tetris.R.drawable.logo_72x72));

    }

    @Override
    public void reCycle() {

    }

    @Override
    public void refresh() {
        this.postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (KStringOK.test(event.getRawX(), event.getRawY())) {
                mGameControl.controlView(GameDefine.Game_Menu);
            }
        }

        return true;
    }
}

