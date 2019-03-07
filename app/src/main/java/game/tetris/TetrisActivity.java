package game.tetris;

import game.tetris.ui.GameControl;
import game.tetris.ui.GameDefine;
import game.tetris.ui.ThreadCanvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public class TetrisActivity extends Activity {
    private ThreadCanvas mThreadCanvas = null;
    public GameControl mGameControl = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameControl = new GameControl(this);
        mGameControl.controlView(GameDefine.Game_Menu);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mThreadCanvas = new ThreadCanvas(this);
        setContentView(mThreadCanvas);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mThreadCanvas.requestFocus();
        mThreadCanvas.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mThreadCanvas.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            mGameControl.controlView(GameDefine.Game_Menu);
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }


}