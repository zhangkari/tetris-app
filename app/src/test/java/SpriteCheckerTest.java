import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import game.engine.drawable.KShapeData;
import game.tetris.collision.SpriteChecker;
import game.tetris.sprite.SceneLayer;
import game.tetris.sprite.Sprite;

public class SpriteCheckerTest {
    private static final int TITLE_SIZE = 20;

    @Test
    public void testCanMove() {
        int[] sceneData1 = {
                1, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 1,
                1, 1, 1, 1, 1, 1
        };
        KShapeData sceneShape = new KShapeData(6, 6);
        sceneShape.setData(sceneData1);
        SceneLayer sceneLayer = new SceneLayer(TITLE_SIZE * 6, TITLE_SIZE * 6);
        sceneLayer.setShapeData(sceneShape);

        int[] data1 = {
                1, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        };
        KShapeData d1 = new KShapeData(4, 4);
        d1.setData(data1);

        int[] data2 = {
                0, 0, 1, 0,
                0, 1, 1, 0,
                0, 1, 0, 0,
                0, 0, 0, 0
        };
        KShapeData d2 = new KShapeData(4, 4);
        d2.setData(data2);

        List<KShapeData> shapes = new ArrayList<>();
        shapes.add(d1);
        shapes.add(d2);

        Sprite sprite = new Sprite(TITLE_SIZE * 4, TITLE_SIZE * 4);
        sprite.setTileSize(TITLE_SIZE);
        sprite.setShapes(shapes);
        sprite.setXY(TITLE_SIZE, 0);

        SpriteChecker checker = new SpriteChecker();
        Assert.assertFalse(checker.canMoveLeft(sprite, sceneLayer));
        Assert.assertTrue(checker.canMoveRight(sprite, sceneLayer));
        Assert.assertTrue(checker.canMoveBottom(sprite, sceneLayer));

        sprite.moveRight();
        Assert.assertTrue(checker.canMoveLeft(sprite, sceneLayer));
        Assert.assertFalse(checker.canMoveRight(sprite, sceneLayer));
        Assert.assertTrue(checker.canMoveBottom(sprite, sceneLayer));

        sprite.moveDown();
        sprite.moveDown();
        sprite.moveDown();
        Assert.assertTrue(checker.canMoveLeft(sprite, sceneLayer));
        Assert.assertFalse(checker.canMoveRight(sprite, sceneLayer));
        Assert.assertFalse(checker.canMoveBottom(sprite, sceneLayer));
    }

    @Test
    public void testCollision() {
        int[] sceneData1 = {
                1, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 1,
                1, 0, 1, 0, 0, 1,
                1, 0, 1, 0, 0, 1,
                1, 0, 1, 0, 0, 1,
                1, 1, 1, 1, 1, 1
        };
        KShapeData sceneShape = new KShapeData(6, 6);
        sceneShape.setData(sceneData1);
        SceneLayer sceneLayer = new SceneLayer(TITLE_SIZE * 6, TITLE_SIZE * 6);
        sceneLayer.setShapeData(sceneShape);

        int[] data1 = {
                1, 1, 0, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        };
        KShapeData d1 = new KShapeData(4, 4);
        d1.setData(data1);
        List<KShapeData> shapes = new ArrayList<>();
        shapes.add(d1);
        Sprite sprite = new Sprite(TITLE_SIZE * 4, TITLE_SIZE * 4);
        sprite.setTileSize(TITLE_SIZE);
        sprite.setShapes(shapes);
        sprite.setXY(2 * TITLE_SIZE, 0);

        SpriteChecker checker = new SpriteChecker();
        Assert.assertTrue(checker.canMoveBottom(sprite, sceneLayer));
        sprite.moveDown();
        Assert.assertFalse(checker.canMoveBottom(sprite, sceneLayer));
    }
}
