package game.tetris;

import android.app.Activity;
import android.os.Bundle;

import com.minmin.kari.tetris.R;

import game.tetris.view.SpriteView;

public class TetrisActivity extends Activity {

    private SpriteView mSpriteView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        init();
    }

    private void init() {
        mSpriteView = findViewById(R.id.spriteView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}