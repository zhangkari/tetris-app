package game.tetris.data;

import game.engine.drawable.KShapeData;
import game.tetris.Constants;

public class SceneData {
    // initial scene
    private static final int[] INITIAL_VALUE =
            {
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 0, 0, 1, 0, 0, 1,
                    1, 0, 0, 1, 0, 1, 0, 0, 0, 1,
                    1, 0, 0, 0, 1, 0, 0, 0, 0, 1,
                    1, 0, 0, 1, 0, 1, 0, 0, 0, 1,
                    1, 0, 1, 0, 0, 0, 1, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            };

    private static final SceneData sInstance = new SceneData();
    private KShapeData mShapeData;

    private SceneData() {
        mShapeData = new KShapeData(Constants.SCENE_ROWS, Constants.SCENE_COLS);
        mShapeData.setData(INITIAL_VALUE);
    }

    public static SceneData getInstance() {
        return sInstance;
    }

    public KShapeData getShapeData() {
        return mShapeData;
    }

    public void reset() {
        mShapeData.reset();
    }
}