package game.tetris.collision;

import java.util.List;

import game.tetris.sprite.RectShape;
import game.tetris.sprite.Sprite;

public interface Checker {
    boolean canMoveLeft(RectShape sprite, RectShape scene);

    boolean canMoveRight(RectShape sprite, RectShape scene);

    boolean canMoveBottom(RectShape sprite, RectShape scene);

    boolean canTransform(Sprite sprite, RectShape scene);

    boolean isGameOver(RectShape sprite, RectShape scene);

    /**
     * Return score when can not move down
     *
     * @param sprite Animating sprite
     * @param scene  Game scene
     * @return rows in list that are filled
     */
    List<Integer> checkScore(RectShape sprite, RectShape scene);
}
