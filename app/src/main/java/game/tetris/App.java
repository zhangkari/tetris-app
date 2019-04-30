package game.tetris;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.minmin.kari.tetris.R;

public class App extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID));
    }

    public static Context get() {
        return sInstance;
    }
}