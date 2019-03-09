package game.tetris.ui;

import game.engine.layer.KView;
import game.engine.layer.KBitmap;
import game.engine.layer.KString;
import game.tetris.TetrisActivity;
import game.tetris.utils.Strings;

import android.content.Context;
import android.view.MotionEvent;

import com.minmin.kari.tetris.R;

public class AboutScreen extends FullScreen {
    private GameControl mGameControl;
    private KString kstrOK;

    public AboutScreen(Context context) {
        super(context);

        int xLeft = 120;
        int yTop = 150;
        int Text_Space = 30;


        addDrawableObject(new KString(context, 80, yTop, Strings.get(R.string.app_name)));

        kstrOK = new KString(context, xLeft, yTop + 5 * Text_Space, Strings.get(R.string.ok)).setTextSize(20);
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

