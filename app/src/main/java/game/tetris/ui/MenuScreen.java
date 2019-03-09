package game.tetris.ui;

import game.engine.layer.KView;
import game.engine.layer.KBitmap;
import game.engine.layer.KString;
import game.tetris.TetrisActivity;
import game.tetris.utils.Strings;

import android.content.Context;
import android.view.MotionEvent;

import com.minmin.kari.tetris.R;

public class MenuScreen extends FullScreen {

    private GameControl mGameControl;

    private String[] textOptions = new String[]
            {
                    Strings.get(R.string.new_game),
                    Strings.get(R.string.continue_game),
                    Strings.get(R.string.rank),
                    Strings.get(R.string.setting),
                    Strings.get(R.string.help),
                    Strings.get(R.string.about),
                    Strings.get(R.string.exit),
            };

    private KString[] KStringOptions = new KString[textOptions.length];


    public MenuScreen(Context context) {
        super(context);

        int xLeft = 150;
        int yTop = 250;
        int Text_Space = 70;


        for (int i = 0; i < textOptions.length; ++i) {
            KStringOptions[i] = new KString(context,
                    xLeft,
                    yTop + i * Text_Space,
                    textOptions[i]).setTextSize(40);

            addDrawableObject(KStringOptions[i]);
        }

        TetrisActivity activity = (TetrisActivity) context;
        mGameControl = activity.mGameControl;

        addDrawableObject(new KBitmap(context, 150, 60, 160, 160).setId(com.minmin.kari.tetris.R.drawable.logo_72x72));
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
            int idx = getClickItem(event.getX(), event.getY());

            switch (idx) {
                case 0:
                    mGameControl.controlView(GameDefine.Game_New);
                    break;
                case 1:
                    mGameControl.controlView(GameDefine.Game_Continue);
                    break;
                case 2:
                    mGameControl.controlView(GameDefine.Game_Record);
                    break;
                case 3:
                    mGameControl.controlView(GameDefine.Game_Setting);
                    break;
                case 4:
                    mGameControl.controlView(GameDefine.Game_Help);
                    break;
                case 5:
                    mGameControl.controlView(GameDefine.Game_About);
                    break;
                case 6:
                    mGameControl.controlView(GameDefine.Game_Exit);
                    break;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    private int getClickItem(float x, float y) {
        for (int i = 0; i < KStringOptions.length; ++i) {
            if (KStringOptions[i].test(x, y)) {
                return i;
            }
        }
        return -1;
    }

}