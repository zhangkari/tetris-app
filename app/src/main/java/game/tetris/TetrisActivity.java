package game.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.minmin.kari.tetris.R;

import game.tetris.utils.Accelerator;
import game.tetris.utils.MoveAccelerator;
import game.tetris.view.Dancer;
import game.tetris.view.DancerView;
import game.tetris.view.ForecasterView;

public class TetrisActivity extends Activity {
    final static String TAG = "TetrisActivity";

    private DancerView mDancerView;
    private View mLeftView;
    private View mRightView;
    private View mDownView;
    private View mTransform;
    private View mResetView;
    private View mLevelView;
    private View mAudioView;
    private View mPauseView;
    private TextView mScoreView;
    private ForecasterView mForecaster;
    private int mScore;

    private Accelerator mAccelerator;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        findViews();
        initListeners();
        mScore = 0;
        mAccelerator = new MoveAccelerator(mDancerView);
    }

    private void findViews() {
        mDancerView = findViewById(R.id.spriteView);
        mLeftView = findViewById(R.id.left);
        mRightView = findViewById(R.id.right);
        mDownView = findViewById(R.id.down);
        mTransform = findViewById(R.id.transform);
        mResetView = findViewById(R.id.reset);
        mLeftView = findViewById(R.id.left);
        mLevelView = findViewById(R.id.level);
        mAudioView = findViewById(R.id.music);
        mPauseView = findViewById(R.id.pause);
        mScoreView = findViewById(R.id.tv_score);
        mForecaster = findViewById(R.id.forecaster);
    }

    private void initListeners() {
        mLeftView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mDancerView.getStatus() == DancerView.STATUS_RUNNING) {
                            mAccelerator.setDirection(Accelerator.LEFT);
                            mAccelerator.startAccelerate();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        mAccelerator.stopAccelerate();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        mRightView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mDancerView.getStatus() == DancerView.STATUS_RUNNING) {
                            mAccelerator.setDirection(Accelerator.RIGHT);
                            mAccelerator.startAccelerate();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        mAccelerator.stopAccelerate();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        mDownView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mDancerView.getStatus() == DancerView.STATUS_RUNNING) {
                            mAccelerator.setDirection(Accelerator.DOWN);
                            mAccelerator.startAccelerate();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        mAccelerator.stopAccelerate();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        mTransform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDancerView.getStatus() == DancerView.STATUS_RUNNING) {
                    mDancerView.onTransform();
                }
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
                formatScore(rows);
            }
        });
        mDancerView.register(new Dancer.OnNextShapeOccurredListener() {
            @Override
            public void onNextShape(int idx) {
                mForecaster.setShapeIndex(idx);
            }
        });
    }

    private void formatScore(int row) {
        int score = 0;
        switch (row) {
            case 1:
                score = 100;
                break;

            case 2:
                score = 200;
                break;

            case 3:
                score = 500;
                break;

            case 4:
                score = 800;
                break;

            case 5:
                score = 1200;
                break;

            case 6:
                score = 1600;
                break;
        }
        mScore += score;
        mScoreView.setText(String.valueOf(mScore));
    }

    @Override
    public void onBackPressed() {
        mDancerView.onQuit();
        finish();
    }

    @Override
    public void onDestroy() {
        mAccelerator.destroy();
        super.onDestroy();
    }
}