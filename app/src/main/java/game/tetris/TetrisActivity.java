package game.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.minmin.kari.tetris.R;

import game.tetris.view.DancerView;

public class TetrisActivity extends Activity {

    private DancerView mDancerView;
    private View mUpView;
    private View mLeftView;
    private View mRightView;
    private View mDownView;
    private View mTransform;
    private View mResetView;
    private View mLevelView;
    private View mAudioView;
    private View mPauseView;
    private TextView mScore;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        findViews();
        initListeners();
    }

    private void findViews() {
        mDancerView = findViewById(R.id.spriteView);
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
        mScore = findViewById(R.id.tv_score);
    }

    private void initListeners() {
        mLeftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onMoveLeft();
            }
        });
        mRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onMoveRight();
            }
        });
        mDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onMoveDown();
            }
        });
        mUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onTransform();
            }
        });
        mTransform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onTransform();
            }
        });
        mResetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onReset();
            }
        });
        mPauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDancerView.onStart();
            }
        });
        mDancerView.setOnAchieveRowListener(new DancerView.OnAchieveRowListener() {
            @Override
            public void onAchieveRows(int rows) {
                mScore.setText(rows + "");
            }
        });
    }

    @Override
    public void onBackPressed() {
        mDancerView.onQuit();
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}