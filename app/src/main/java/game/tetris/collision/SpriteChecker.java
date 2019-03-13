package game.tetris.collision;

import java.util.ArrayList;
import java.util.List;

import game.engine.drawable.KShapeData;
import game.tetris.sprite.RectShape;
import game.tetris.sprite.Sprite;

public class SpriteChecker implements Checker {

    @Override
    public boolean canMoveLeft(RectShape sprite, RectShape scene) {
        return !hasCollided(0, sprite.getShapeData(), scene.getShapeData(),
                sprite.getY() / sprite.getTileSize(), sprite.getX() / sprite.getTileSize());
    }

    @Override
    public boolean canMoveRight(RectShape sprite, RectShape scene) {
        return !hasCollided(1, sprite.getShapeData(), scene.getShapeData(),
                sprite.getY() / sprite.getTileSize(), sprite.getX() / sprite.getTileSize());
    }

    @Override
    public boolean canMoveBottom(RectShape sprite, RectShape scene) {
        return !hasCollided(2, sprite.getShapeData(), scene.getShapeData(),
                sprite.getY() / sprite.getTileSize(), sprite.getX() / sprite.getTileSize());
    }

    @Override
    public boolean canTransform(Sprite sprite, RectShape scene) {
        return !hasCollided(3, sprite.peekNext(), scene.getShapeData(),
                sprite.getY() / sprite.getTileSize(), sprite.getX() / sprite.getTileSize());
    }

    @Override
    public boolean isGameOver(RectShape sprite, RectShape scene) {
        // initial position of sprite is (3, 0)
        int x = 3, y = 0;
        List<Integer> list = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            list.add(scene.getShapeData().getValue(y, x + i));
        }
        return checkCollision(list, list);
    }

    /**
     * Collision check
     *
     * @param direction the direction will check, 0 left, 1 right, 2 bottom, 3 transform
     * @param sprite    sprite
     * @param scene     scene
     * @param row       y coordinate of sprite, range in [0, 3]
     * @param cols      x coordinate of sprite, range in [0, 3]
     * @return Whether collision occurred
     */
    private boolean hasCollided(int direction, KShapeData sprite, KShapeData scene, int row, int cols) {
        if (row < 0 || cols < 0 || row >= scene.getRows() || cols >= scene.getCols()) {
            throw new IllegalArgumentException("invalid row or cols or scene");
        }
        switch (direction) {
            // left
            case 0:
                cols -= 1;
                if (cols < 0) {
                    return true;
                }
                return checkLeft(sprite, scene, row, cols);

            // right
            case 1:
                cols += 1;
                if (cols + sprite.getOccupiedCols() > scene.getCols()) {
                    return true;
                }
                return checkRight(sprite, scene, row, cols);

            // bottom
            case 2:
                row += 1;
                if (row + sprite.getOccupiedRows() > scene.getRows()) {
                    return true;
                }
                return checkBottom(sprite, scene, row, cols);

            // transform
            case 3:
                return checkTransform(sprite, scene, row, cols);

            default:
                return true;
        }
    }

    private boolean checkRight(KShapeData sprite, KShapeData scene, int row, int cols) {
        int idx = sprite.getOccupiedCols() - 1;
        List<Integer> src = new ArrayList<>(sprite.getRows());
        for (int i = 0; i < sprite.getRows(); i++) {
            src.add(sprite.getValue(i, idx));
        }

        List<Integer> dst = new ArrayList<>(sprite.getRows());
        for (int i = 0; i < sprite.getRows(); i++) {
            dst.add(scene.getValue(row + i, cols));
        }
        return checkCollision(src, dst);
    }

    private boolean checkLeft(KShapeData sprite, KShapeData scene, int row, int cols) {
        int idx = 0;
        List<Integer> src = new ArrayList<>(sprite.getRows());
        for (int i = 0; i < sprite.getRows(); i++) {
            src.add(sprite.getValue(i, idx));
        }

        List<Integer> dst = new ArrayList<>(sprite.getRows());
        for (int i = 0; i < sprite.getRows(); i++) {
            dst.add(scene.getValue(row + i, cols));
        }
        return checkCollision(src, dst);
    }

    private boolean checkBottom(KShapeData sprite, KShapeData scene, int row, int cols) {
        int idx = sprite.getOccupiedRows() - 1;
        List<Integer> src = new ArrayList<>(sprite.getCols());
        for (int i = 0; i < sprite.getCols(); i++) {
            src.add(sprite.getValue(idx, i));
        }

        List<Integer> dst = new ArrayList<>(sprite.getCols());
        for (int i = 0; i < sprite.getCols(); i++) {
            dst.add(scene.getValue(row, cols + i));
        }
        return checkCollision(src, dst);
    }

    /**
     * check all of the 3 directions
     *
     * @param sprite
     * @param scene
     * @param row
     * @param cols
     * @return
     */
    private boolean checkTransform(KShapeData sprite, KShapeData scene, int row, int cols) {
        return checkLeft(sprite, scene, row, cols) ||
                checkRight(sprite, scene, row, cols) ||
                checkBottom(sprite, scene, row, cols);
    }

    /**
     * Check collision
     *
     * @param src
     * @param dst
     * @return true collision occurred, false not occurred
     */
    private boolean checkCollision(List<Integer> src, List<Integer> dst) {
        if (src == null || dst == null || src.size() != dst.size()) {
            throw new IllegalArgumentException("invalid arguments !");
        }
        int count = 0;
        for (int i = 0; i < src.size(); i++) {
            count += (src.get(i) * dst.get(i));
        }
        return count >= 1;
    }
}