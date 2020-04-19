package game.tetris;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.minmin.kari.tetris.R;

import game.tetris.data.Settings;
import game.tetris.dialog.BaseDialog;
import game.tetris.dialog.BottomDialog;
import game.tetris.game.Dancer;
import game.tetris.game.DancerView;
import game.tetris.utils.Accelerator;
import game.tetris.utils.Logs;
import game.tetris.utils.MoveAccelerator;
import game.tetris.utils.ViewPiece;
import game.tetris.widget.ForecasterView;

public class TetrisActivity extends Activity {
    private final static String TAG = "TetrisActivity";

    private ViewPiece mViewPiece;
    private Accelerator mAccelerator;
    private RewardedAd mRewardAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        findViews();
        initListeners();
        DancerView dancerView = mViewPiece.find(R.id.spriteView);
        mAccelerator = new MoveAccelerator(dancerView);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRewardAd = loadRewardAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logLoadAdError(int code) {
        /*
        ERROR_CODE_INTERNAL_ERROR - 内部出现问题；例如，收到广告服务器的无效响应。
        ERROR_CODE_INVALID_REQUEST - 广告请求无效；例如，广告单元 ID 不正确。
        ERROR_CODE_NETWORK_ERROR - 由于网络连接问题，广告请求失败。
        ERROR_CODE_NO_FILL - 广告请求成功，但由于缺少广告资源，未返回广告。
        */
        switch (code) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                Logs.d(TAG, "internal error");
                break;

            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                Logs.d(TAG, "invalid request");
                break;

            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                Logs.d(TAG, "network error");
                break;

            case AdRequest.ERROR_CODE_NO_FILL:
                Logs.d(TAG, "no fill");
                break;
        }
    }

    private RewardedAd loadRewardAd() {
        RewardedAd ad = new RewardedAd(this, getString(R.string.REWARD_AD_ID));
        ad.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                Logs.d(TAG, "load reward ad success");
            }

            @Override
            public void onRewardedAdFailedToLoad(int code) {
                logLoadAdError(code);
            }
        });
        return ad;
    }

    private void findViews() {
        mViewPiece = new ViewPiece(findViewById(android.R.id.content));
        mViewPiece.setVisibility(R.id.start, View.VISIBLE);
        mViewPiece.setVisibility(R.id.pause, View.GONE);
        mViewPiece.setVisibility(R.id.resume, View.GONE);

        initAudioView();
        initAdView();
    }

    private void initAdView() {
        AdView adView = mViewPiece.find(R.id.adView);
        if (adView != null) {
            adView.loadAd(new AdRequest.Builder().build());
        }
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
                        v.setPressed(true);
                        if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
                            mAccelerator.setDirection(Accelerator.LEFT);
                            mAccelerator.startAccelerate();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
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
                        v.setPressed(true);
                        if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
                            mAccelerator.setDirection(Accelerator.RIGHT);
                            mAccelerator.startAccelerate();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
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
                        v.setPressed(true);
                        if (dancerView.getStatus() == DancerView.STATUS_RUNNING) {
                            mAccelerator.setDirection(Accelerator.DOWN);
                            mAccelerator.startAccelerate();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
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
                if (dancerView.getStatus() != DancerView.STATUS_RUNNING) {
                    dancerView.onStart();
                }
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

        dancerView.register(new Dancer.OnNextShapeOccurredListener() {
            @Override
            public void onNextShape(int idx, int color) {
                ForecasterView view = mViewPiece.find(R.id.forecaster);
                if (view != null) {
                    view.setShapeIndexAndColor(idx, color);
                }
            }
        });

        dancerView.register(new Dancer.OnGameOverListener() {
            @Override
            public void onGameOver() {
                FragmentManager manager = getFragmentManager();
                BaseDialog dialog = new BottomDialog();
                dialog.setOnDismissListener(new Runnable() {
                    @Override
                    public void run() {
                        showRewardAd();
                    }
                });
                dialog.show(manager, TAG);
            }
        });

        dancerView.register(new Dancer.OnScoreChangeListener() {
            @Override
            public void onScoreChange(int level, int score) {
                refreshScore(score);
            }
        });
    }

    private void refreshScore(int score) {
        mViewPiece.setText(R.id.tv_score, String.valueOf(score));
    }

    private void showRewardAd() {
        if (mRewardAd.isLoaded()) {
            mRewardAd.show(this, new RewardedAdCallback() {
                @Override
                public void onRewardedAdClosed() {
                    mRewardAd = loadRewardAd();
                }
            });
        }
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