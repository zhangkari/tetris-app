package game.tetris;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context get() {
        return sInstance;
    }
}