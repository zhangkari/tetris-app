package game.engine.layer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import game.engine.layer.KDrawable;

public abstract class KView extends View {

    protected List<KDrawable> drawList;
    protected Context context;

    public KView(Context context) {
        super(context);
        this.context = context;
        drawList = new ArrayList<>();
    }

    protected int addDrawableObject(KDrawable drawObj) {
        int pos = drawList.size();
        drawList.add(drawObj);
        return pos;
    }

    public void add(KDrawable object) {
        addDrawableObject(object);
    }

    public void onDraw(Canvas canvas) {
        for (int i = 0; i < drawList.size(); ++i) {
            drawList.get(i).Draw(canvas);
        }

    }

    public abstract boolean onTouchEvent(MotionEvent event);

    public abstract void reCycle();

    public abstract void refresh();
}
