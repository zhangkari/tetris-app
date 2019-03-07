package game.tetris.ui;

import game.engine.layer.KView;
import game.engine.layer.KBitmap;
import game.engine.layer.KString;
import game.tetris.utils.StorageUtil;
import game.tetris.TetrisActivity;

import android.content.Context;
import android.view.MotionEvent;

public class RecordScreen extends KView {

    private GameControl mGameControl;

    private KString kstrOK;

    public RecordScreen(Context context) {
        super(context);

        int xLeft = 120;
        int yTop = 150;
        int Text_Space = 30;


        addDrawableObject(new KString(context, 80, yTop, "Score"));
        addDrawableObject(new KString(context, 180, yTop + 2 * Text_Space, StorageUtil.getInstance(context).getHeightScore() + ""));

        kstrOK = new KString(context, xLeft, yTop + 3 * Text_Space, "OK").setTextSize(20);
        addDrawableObject(kstrOK);

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
            if (kstrOK.test(event.getRawX(), event.getRawY())) {
                mGameControl.controlView(GameDefine.Game_Menu);
            }
        }

        return true;
    }
}

