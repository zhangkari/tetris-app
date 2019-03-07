package game.tetris.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;

import game.engine.layer.KDrawable;
import game.engine.layer.KString;

public class OperationPanel extends KDrawable {


    private KString stringStart;
    private KString stringPause;
    private KString stringMenu;

    public OperationPanel(Context context, int x, int y, int width, int height) {
        super(context, x, y, width, height);

        paint.setARGB(255, 255, 0, 0);
        paint.setStyle(Style.STROKE);

        stringStart = new KString(context, this.x, this.y, "��ʼ").setTextSize(30);
        stringPause = new KString(context, this.x, this.y + 100, "��ͣ").setTextSize(30);
        stringMenu = new KString(context, this.x, this.y + 200, "�˵�").setTextSize(30);

    }


    @Override
    public void Draw(Canvas canvas) {
        stringStart.Draw(canvas);
        stringPause.Draw(canvas);
        stringMenu.Draw(canvas);

        //	canvas.drawRect(x, y, x+width, y+height, paint);
    }


    public int onTouchEvent(float x, float y) {
        int ret = -1;
        if (stringStart.test(x, y)) {
            ret = GameDefine.Game_Start;
        } else if (stringPause.test(x, y)) {
            ret = GameDefine.Game_Pause;
        } else if (stringMenu.test(x, y)) {
            ret = GameDefine.Game_Menu;
        }
        return ret;
    }

}
