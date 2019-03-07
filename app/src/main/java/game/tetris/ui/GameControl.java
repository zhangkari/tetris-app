package game.tetris.ui;

import game.engine.layer.KView;
import game.tetris.TetrisActivity;

import android.content.Context;

public class GameControl {

    public static KView m_KView = null;
    private int m_status;
    private Context context;

    private KScreen preGameScreen = null;

    public GameControl(Context context) {
        m_KView = null;
        m_status = GameDefine.Game_Menu;
        this.context = context;
    }

    public void setStatus(int status) {
        this.m_status = status;
    }

    public void freeGameView(KView gview) {
        if (gview != null) {
            gview = null;
        }
    }

    public void controlView(int status) {
        if (status != m_status) {
            if (m_KView != null) {
                m_KView.reCycle();
                System.gc();
            }
        }

        freeGameView(m_KView);

        switch (status) {
            case GameDefine.Game_Menu:
                if (preGameScreen != null) {
                    preGameScreen.onPauseGame();
                }
                m_KView = new MenuScreen(context);
                break;
            case GameDefine.Game_New:
                if (preGameScreen != null) {
                    preGameScreen = null;
                    System.gc();
                }
                preGameScreen = new KScreen(context);
                m_KView = preGameScreen;
                break;
            case GameDefine.Game_Continue:
                if (preGameScreen != null) {
                    preGameScreen.onContinueGame();
                    m_KView = preGameScreen;
                }
                break;
            case GameDefine.Game_Setting:
                m_KView = new SettingScreen(context);
                break;
            case GameDefine.Game_Record:
                m_KView = new RecordScreen(context);
                break;
            case GameDefine.Game_Help:
                m_KView = new HelpScreen(context);
                break;
            case GameDefine.Game_Over:
                m_KView = new KOverScreen(context, m_KView);
                break;
            case GameDefine.Game_About:
                m_KView = new AboutScreen(context);
                break;
            case GameDefine.Game_Exit:
                TetrisActivity activity = (TetrisActivity) context;
                activity.finish();
        }

        setStatus(status);
    }

    public static KView getMainView() {
        return m_KView;
    }


}
