package game.tetris.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;

import game.engine.layer.KDrawable;
import game.engine.layer.KString;

public class ScorePanel extends KDrawable {


    private KString stringScore;
    private KString stringLevel;

    public ScorePanel(Context context, int x, int y, int width, int height) {
        super(context, x, y, width, height);

        paint.setARGB(255, 20, 20, 0);
        paint.setStyle(Style.STROKE);

        stringScore = new KString(context, this.x, this.y, "�÷�:0").setTextSize(30);
        stringLevel = new KString(context, this.x, this.y + 40, "�ȼ�:1").setTextSize(30);
    }

    public ScorePanel setScore(int score) {
        this.stringScore.setString("�÷�:" + score);
        return this;
    }

    public ScorePanel setLevel(int level) {
        this.stringLevel.setString("�ȼ�:" + level);
        return this;
    }

    @Override
    public void Draw(Canvas canvas) {
        stringScore.Draw(canvas);
        stringLevel.Draw(canvas);
    }

}
