package game.tetris.utils;

import android.graphics.Paint;

public class ColorUtil {

    public static int nColors = 6;

    public static void setColor(Paint paint, int color) {
        switch (color) {
            case 1:
                paint.setARGB(255, 0, 255, 255);
                break;
            case 2:
                paint.setARGB(255, 255, 0, 255);
                break;
            case 3:
                paint.setARGB(255, 255, 255, 0);
                break;
            case 4:
                paint.setARGB(255, 255, 0, 0);
                break;
            case 5:
                paint.setARGB(255, 0, 255, 0);
                break;
            case 6:
                paint.setARGB(255, 0, 0, 255);
                break;
        }
    }
}
