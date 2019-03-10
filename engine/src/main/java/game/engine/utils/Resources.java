package game.engine.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Resources {
    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String getString(Context context, int id, Object... args) {
        return context.getResources().getString(id, args);
    }

    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }
}
