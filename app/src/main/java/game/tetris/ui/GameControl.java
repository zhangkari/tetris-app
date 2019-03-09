package game.tetris.ui;

import android.content.Context;
import android.view.ViewGroup;

import game.engine.layer.KView;
import game.tetris.TetrisActivity;
import game.tetris.utils.Views;

public class GameControl {

    private ViewGroup mContainer;
    private KView mView;
    private int mStatus;
    private Context context;

    private MainScreen preGameScreen = null;

    public GameControl(Context context) {
        mView = null;
        mStatus = GameDefine.Game_Menu;
        this.context = context;
        controlView(mStatus);
    }

    public void setContainer(ViewGroup parent) {
        mContainer = parent;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public void freeGameView(KView view) {
        if (view != null) {
            view.reCycle();
        }
    }

    public void controlView(int status) {
        if (status != mStatus) {
            if (mView != null) {
                mView.reCycle();
                System.gc();
            }
        }

        freeGameView(mView);

        switch (status) {
            case GameDefine.Game_Menu:
                if (preGameScreen != null) {
                    preGameScreen.onPauseGame();
                }
                mView = new MenuScreen(context);
                break;
            case GameDefine.Game_New:
                if (preGameScreen != null) {
                    preGameScreen = null;
                    System.gc();
                }
                preGameScreen = new MainScreen(context);
                mView = preGameScreen;
                break;
            case GameDefine.Game_Continue:
                if (preGameScreen != null) {
                    preGameScreen.onContinueGame();
                    mView = preGameScreen;
                }
                break;
            case GameDefine.Game_Setting:
                mView = new SettingScreen(context);
                break;
            case GameDefine.Game_Record:
                mView = new RecordScreen(context);
                break;
            case GameDefine.Game_Help:
                mView = new HelpScreen(context);
                break;
            case GameDefine.Game_Over:
                mView = new OverScreen(context, mView);
                break;
            case GameDefine.Game_About:
                mView = new AboutScreen(context);
                break;
            case GameDefine.Game_Exit:
                TetrisActivity activity = (TetrisActivity) context;
                activity.finish();
                return;
        }
        setStatus(status);

        Views.relayout(mContainer, mView);
    }

    public KView getMainView() {
        return mView;
    }
}
