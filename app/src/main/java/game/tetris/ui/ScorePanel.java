package game.tetris.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;

import com.minmin.kari.tetris.R;

import game.engine.layer.KDrawable;
import game.engine.layer.KString;
import game.tetris.utils.Strings;

public class ScorePanel extends KDrawable {


    private KString stringScore;
    private KString stringLevel;

    public ScorePanel(Context context, int x, int y, int width, int height) {
        super(context, x, y, width, height);

        paint.setARGB(255, 20, 20, 0);
        paint.setStyle(Style.STROKE);

        stringScore = new KString(context, this.x, this.y, Strings.get(R.string.score)).setTextSize(30);
        stringLevel = new KString(context, this.x, this.y + 40, Strings.get(R.string.level)).setTextSize(30);
    }

    public ScorePanel setScore(int score) {
        this.stringScore.setString(Strings.get(R.string.score_v, score));
        return this;
    }

    public ScorePanel setLevel(int level) {
        this.stringLevel.setString(Strings.get(R.string.level_v, level));
        return this;
    }

    @Override
    public void Draw(Canvas canvas) {
        stringScore.Draw(canvas);
        stringLevel.Draw(canvas);
    }

}
