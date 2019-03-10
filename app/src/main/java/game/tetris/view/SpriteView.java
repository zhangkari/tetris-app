package game.tetris.view;

import android.content.Context;
import android.util.AttributeSet;

import game.engine.RenderView;
import game.tetris.Constants;
import game.tetris.data.SceneData;
import game.tetris.sprite.BackgroundGrid;
import game.tetris.sprite.RectShape;

public class SpriteView extends RenderView {
    public SpriteView(Context context) {
        this(context, null);
    }

    public SpriteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpriteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                onSizeMeasured(getWidth(), getHeight());
            }
        });
    }

    private void onSizeMeasured(int width, int height) {
        BackgroundGrid grid = new BackgroundGrid(width, height);
        grid.setInterval(width / Constants.SCENE_COLS);
        addDrawable(grid);

        RectShape scene = new RectShape(width, height);
        scene.setTileSize(width / Constants.SCENE_COLS);
        scene.setShapeData(SceneData.getInstance().getShapeData());
        addDrawable(scene);

        RectShape sprite = new RectShape(width / Constants.SCENE_COLS, height / Constants.SCENE_ROWS);
        sprite.setTileSize(width / Constants.SCENE_COLS);
        addDrawable(sprite);
    }
}
