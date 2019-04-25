package game.tetris;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.minmin.kari.tetris.R;

import game.tetris.data.Settings;
import game.tetris.utils.Accelerator;
import game.tetris.utils.MoveAccelerator;
import game.tetris.utils.ViewPiece;
import game.tetris.game.Dancer;
import game.tetris.game.DancerView;
import game.tetris.widget.ForecasterView;

public class TetrisActivity extends Activity {
    private final static String TAG = "TetrisActivity";

    private ViewPiece mViewPiece;
    private int mScore;
    private Accelerator mAccelerator;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        mScore = 0;
        findViews();
        initListeners();
        DancerView dancerView = mViewPiece.find(R.id.spriteView);
        mAccelerator = new MoveAccelerator(dancerView);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    private void findViews() {
        mViewPiece = new ViewPiece(findViewById(android.R.id.content));
        mViewPiece.setVisibility(R.id.start, View.VISIBLE);
        mViewPiece.setVisibility(R.id.pause, View.GONE);
        mViewPiece.setVisibility(R.id.resume, View.GONE);

        initAudioView();
    }

    private void initAudioView() {
        int resId;
        if (Settings.isSoundOn()) {
            resId = R.drawable.btn_blue;
            mViewPiece.setVisibility(R.id.iv_audio, View.VISIBLE);
        } else {
            resId = R.drawable.btn_yellow;
            mViewPiece.setVisibility(R.id.iv_audio, View.INVISIBLE);
        }
        Drawable d = getResources().getDrawable(resId);
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        TextView audioView = mViewPiece.find(R.id.music);
        if (audioView != null) {
            audioView.setCompoundDrawables(null, d, null, null);
        }
    }

    private void initListeners() {
        mViewPiece.setOnClickListener(R.id.music, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.setSoundOn(!Settings.isSoundOn());
                initAudioView();
            }
        });

        final DancerView dancerView = mViewPiece.find(R.id.spriteView);
        if (dancerView == null) {
            return;
        }

        mViewPiece.setOnTouchListener(R.id.left, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
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

        mViewPiece.setOnTouchListener(R.id.right, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
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

        mViewPiece.setOnTouchListener(R.id.down, new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
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

        mViewPiece.setOnClickListener(R.id.transform, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
                    dancerView.onTransform();
                }
            }
        });

        mViewPiece.setOnClickListener(R.id.reset, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dancerView.getStatus() != DancerView.STATUS_RUNNING) {
                    dancerView.onReset();
                }
            }
        });

        mViewPiece.setOnClickListener(R.id.pause, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dancerView.onPause();
                mViewPiece.setVisibility(R.id.pause, View.GONE);
                mViewPiece.setVisibility(R.id.resume, View.VISIBLE);
            }
        });

        mViewPiece.setOnClickListener(R.id.start, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dancerView.onStart();
                mViewPiece.setVisibility(R.id.start, View.GONE);
                mViewPiece.setVisibility(R.id.pause, View.VISIBLE);
            }
        });

        mViewPiece.setOnClickListener(R.id.resume, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dancerView.onResume();
                mViewPiece.setVisibility(R.id.resume, View.GONE);
                mViewPiece.setVisibility(R.id.pause, View.VISIBLE);
            }
        });

        dancerView.setOnAchieveRowListener(new DancerView.OnAchieveRowListener() {
            @Override
            public void onAchieveRows(int rows) {
                formatScore(rows);
            }
        });
        dancerView.register(new Dancer.OnNextShapeOccurredListener() {
            @Override
            public void onNextShape(int idx, int color) {
                ForecasterView view = mViewPiece.find(R.id.forecaster);
                if (view != null) {
                    view.setShapeIndexAndColor(idx, color);
                }
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
        mViewPiece.setText(R.id.tv_score, String.valueOf(mScore));
    }

    @Override
    public void onBackPressed() {
        DancerView dancerView = mViewPiece.find(R.id.spriteView);
        if (dancerView != null) {
            dancerView.onQuit();
        }
        finish();
    }

    @Override
    public void onDestroy() {
        mAccelerator.destroy();
        super.onDestroy();
    }
}