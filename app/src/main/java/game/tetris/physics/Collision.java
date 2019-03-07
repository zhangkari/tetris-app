package game.tetris.physics;

import android.graphics.Rect;

import game.tetris.ui.CubeSprite;
import game.tetris.ui.GamePanel;

public class Collision {

    public static boolean collisionWithLeft(GamePanel gamePanel, CubeSprite sprite) {
        Rect rect = new Rect(gamePanel.getX(), gamePanel.getY(), gamePanel.getX() + gamePanel.getWidth(), gamePanel.getY() + gamePanel.getHeight());
        int[][] info = gamePanel.getInfo();

        if (sprite.getX() < rect.left)
            return true;

        int data[] = sprite.getShapeData();
        int n = 0;
        int m = 0;
        int w = sprite.getWidth() / CubeSprite.CUBE_SIZE;
        int h = sprite.getHeight() / CubeSprite.CUBE_SIZE;

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (data[i * 4 + j] == 1) {
                    m = (sprite.getY() + i * CubeSprite.CUBE_SIZE - rect.top) / CubeSprite.CUBE_SIZE;
                    n = (sprite.getX() + j * CubeSprite.CUBE_SIZE - rect.left) / CubeSprite.CUBE_SIZE;
                    if (info[m][n] != 0) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;

    }

    public static boolean collisionWithRight(GamePanel gamePanel, CubeSprite sprite) {
        Rect rect = new Rect(gamePanel.getX(), gamePanel.getY(), gamePanel.getX() + gamePanel.getWidth(), gamePanel.getY() + gamePanel.getHeight());
        int[][] info = gamePanel.getInfo();

        if (sprite.getX() + sprite.getWidth() > rect.right)
            return true;

        int data[] = sprite.getShapeData();
        int n = 0;
        int m = 0;
        int w = sprite.getWidth() / CubeSprite.CUBE_SIZE;
        int h = sprite.getHeight() / CubeSprite.CUBE_SIZE;

        for (int i = 0; i < h; ++i) {
            for (int j = w - 1; j >= 0; --j) {
                if (data[i * 4 + j] == 1) {
                    m = (sprite.getY() + i * CubeSprite.CUBE_SIZE - rect.top) / CubeSprite.CUBE_SIZE;
                    n = (sprite.getX() + j * CubeSprite.CUBE_SIZE - rect.left) / CubeSprite.CUBE_SIZE;
                    if (info[m][n] != 0) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;

    }

    public static boolean collisionWithBottom(GamePanel gamePanel, CubeSprite sprite) {
        Rect rect = new Rect(gamePanel.getX(), gamePanel.getY(), gamePanel.getX() + gamePanel.getWidth(), gamePanel.getY() + gamePanel.getHeight());
        int[][] info = gamePanel.getInfo();

        if (sprite.getY() + sprite.getHeight() > rect.bottom)
            return true;

        int data[] = sprite.getShapeData();
        int m = 0;
        int n = 0;

        int h = sprite.getHeight() / CubeSprite.CUBE_SIZE;
        int w = sprite.getWidth() / CubeSprite.CUBE_SIZE;

        for (int i = 0; i < w; ++i) {
            for (int j = h - 1; j >= 0; --j) {
                if (data[4 * j + i] == 1) {
                    m = (sprite.getY() + j * CubeSprite.CUBE_SIZE - rect.top) / CubeSprite.CUBE_SIZE;
                    n = (sprite.getX() + i * CubeSprite.CUBE_SIZE - rect.left) / CubeSprite.CUBE_SIZE;

                    if (info[m][n] != 0) {
                        return true;
                    }
                    break;
                }
            }

        }

        return false;

    }

}
