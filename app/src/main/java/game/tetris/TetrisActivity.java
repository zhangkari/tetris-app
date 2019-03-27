package game.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.minmin.kari.tetris.R;

import game.tetris.utils.DownTimer;
import game.tetris.utils.Timer;
import game.tetris.view.SpriteView;

public class TetrisActivity extends Activity {

    private SpriteView mSpriteView;
    private View mUpView;
    private View mLeftView;
    private View mRightView;
    private View mDownView;
    private View mTransform;
    private View mResetView;
    private View mLevelView;
    private View mAudioView;
    private View mPauseView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        findViews();
        initListeners();
    }

    private void findViews() {
        mSpriteView = findViewById(R.id.spriteView);
        mUpView = findViewById(R.id.top);
        mLeftView = findViewById(R.id.left);
        mRightView = findViewById(R.id.right);
        mDownView = findViewById(R.id.down);
        mTransform = findViewById(R.id.transform);
        mResetView = findViewById(R.id.reset);
        mLeftView = findViewById(R.id.left);
        mLevelView = findViewById(R.id.level);
        mAudioView = findViewById(R.id.music);
        mPauseView = findViewById(R.id.pause);
    }

    private void initListeners() {
        mLeftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onMoveLeft();
            }
        });
        mRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onMoveRight();
            }
        });
        mDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onMoveDown();
            }
        });
        mUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onTransform();
            }
        });
        mTransform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onTransform();
            }
        });
        mResetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onReset();
            }
        });
        mPauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpriteView.onStart();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mSpriteView.onQuit();
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}