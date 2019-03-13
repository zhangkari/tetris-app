package game.tetris.collision;

import game.tetris.sprite.RectShape;
import game.tetris.sprite.Sprite;

public interface Checker {
    boolean canMoveLeft(RectShape sprite, RectShape scene);

    boolean canMoveRight(RectShape sprite, RectShape scene);

    boolean canMoveBottom(RectShape sprite, RectShape scene);

    boolean canTransform(Sprite sprite, RectShape scene);

    boolean isGameOver(RectShape sprite, RectShape scene);
}
