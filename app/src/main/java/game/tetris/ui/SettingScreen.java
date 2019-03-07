package game.tetris.ui;

import game.engine.layer.KView;
import game.engine.layer.KBitmap;
import game.engine.layer.KString;
import game.tetris.utils.StorageUtil;
import game.engine.utils.SoundManager;
import game.tetris.TetrisActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

public class SettingScreen extends KView {

    private GameControl mGameControl = null;

    private StorageUtil userDataMgr;

    private KString kstrMusicOn;
    private KString kstrMusicOff;
    private KString kstrHeartMode;
    private KString kstrRectMode;
    private KString kstrOK;

    public SettingScreen(Context context) {
        super(context);

        TetrisActivity activity = (TetrisActivity) context;
        mGameControl = activity.mGameControl;

        addDrawableObject(new KBitmap(context, 120, 100, 160, 160).setId(com.minmin.kari.tetris.R.drawable.logo_72x72));

        userDataMgr = StorageUtil.getInstance(context);

        int xLeft = 120;
        int yTop = 300;
        int Text_Space = 80;

        KString kstrMusicMode = new KString(context, xLeft, yTop, "��������").setTextSize(40);
        addDrawableObject(kstrMusicMode);

        kstrMusicOn = new KString(context, xLeft, yTop + Text_Space, "��").setTextSize(30);
        addDrawableObject(kstrMusicOn);

        kstrMusicOff = new KString(context, xLeft + Text_Space, yTop + Text_Space, "��").setTextSize(30);
        addDrawableObject(kstrMusicOff);

        if (userDataMgr.isPlayMusic()) {
            kstrMusicOn.setColor(Color.WHITE);
            kstrMusicOff.setColor(Color.GRAY);
        } else {
            kstrMusicOff.setColor(Color.WHITE);
            kstrMusicOn.setColor(Color.GRAY);
        }


        KString kstrGameMode = new KString(context, xLeft, yTop + 2 * Text_Space, "��Ϸģʽ").setTextSize(40);
        addDrawableObject(kstrGameMode);

        kstrHeartMode = new KString(context, xLeft, yTop + 3 * Text_Space, "������ӡ").setTextSize(30);
        addDrawableObject(kstrHeartMode);

        kstrRectMode = new KString(context, xLeft + 140, yTop + 3 * Text_Space, "��׼ģʽ").setTextSize(30);
        addDrawableObject(kstrRectMode);

        if (userDataMgr.isHeartOn()) {
            kstrHeartMode.setColor(Color.WHITE);
            kstrRectMode.setColor(Color.GRAY);
        } else {
            kstrRectMode.setColor(Color.WHITE);
            kstrHeartMode.setColor(Color.GRAY);
        }

        kstrOK = new KString(context, xLeft, yTop + 4 * Text_Space, "ȷ��").setTextSize(40);
        addDrawableObject(kstrOK);

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
            float x = event.getRawX();
            float y = event.getRawY();

            if (kstrOK.test(x, y)) {
                mGameControl.controlView(GameDefine.Game_Menu);
            } else if (kstrMusicOn.test(x, y)) {
                userDataMgr.saveMusicMode(true);
                kstrMusicOff.setColor(Color.GRAY);
                kstrMusicOn.setColor(Color.WHITE);
                SoundManager.getInstance(context).play(com.minmin.kari.tetris.R.raw.ok);
            } else if (kstrMusicOff.test(x, y)) {
                userDataMgr.saveMusicMode(false);
                kstrMusicOff.setColor(Color.WHITE);
                kstrMusicOn.setColor(Color.GRAY);
            } else if (kstrHeartMode.test(x, y)) {
                userDataMgr.saveHeartMode(true);
                kstrHeartMode.setColor(Color.WHITE);
                kstrRectMode.setColor(Color.GRAY);
            } else if (kstrRectMode.test(x, y)) {
                userDataMgr.saveHeartMode(false);
                kstrHeartMode.setColor(Color.GRAY);
                kstrRectMode.setColor(Color.WHITE);
            }
        }
        return true;
    }

}