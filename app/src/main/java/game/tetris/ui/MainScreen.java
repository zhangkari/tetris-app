package game.tetris.ui;

import android.content.Context;
import android.view.MotionEvent;

import com.minmin.kari.tetris.R;

import game.engine.layer.KSprite;
import game.engine.utils.SoundManager;
import game.tetris.TetrisActivity;
import game.tetris.physics.Collision;
import game.tetris.utils.Colors;
import game.tetris.utils.Storages;

public class MainScreen extends FullScreen implements Runnable {

    private CubeSprite sprite;
    private int nextSpriteIndex = 0;
    private CubeSprite nextSprite;
    private int nextSpriteColor = 1;

    private CubeSprite collisionSprite;

    private GameControl mGameControl;

    private OperationPanel optPanel;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;

    private GameStick gameStick;

    private boolean mMusicOn = false;

    private boolean isStart = true;
    private int nDelay = 1000;

    private boolean isOver = false;

    private float orgX;
    private float orgY;

    private Thread t;

    public MainScreen(Context context) {
        super(context);

        sprite = new CubeSprite(context);
        addDrawableObject(sprite);

        nextSpriteIndex = ((int) (Math.random() * 10)) % CubeSprite.nShapes;
        nextSpriteColor = ((int) (Math.random() * 10)) % Colors.nColors + 1;

        nextSprite = new CubeSprite(context, 360, 60, nextSpriteIndex).setColor(nextSpriteColor);
        addDrawableObject(nextSprite);

        collisionSprite = new CubeSprite(context);

        mGameControl = ((TetrisActivity) (context)).mGameControl;

        optPanel = new OperationPanel(context, 0, 300, 60, 480);
        addDrawableObject(optPanel);

        gamePanel = new GamePanel(context, 60, 60, 300, 450).setTileSize(KSprite.CUBE_SIZE);
        addDrawableObject(gamePanel);

        scorePanel = new ScorePanel(context, 360, 350, 200, 240);
        addDrawableObject(scorePanel);

        gameStick = new GameStick(context, 90, 540, 240, 240);
        addDrawableObject(gameStick);

        mMusicOn = Storages.getInstance(context).isPlayMusic();

        t = new Thread(this);
        t.start();
    }


    @Override
    public void reCycle() {

    }

    @Override
    public void refresh() {
        this.postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            orgX = event.getRawX();
            orgY = event.getRawY();

            if (optPanel.test(orgX, orgY)) {
                int ret = optPanel.onTouchEvent(orgX, orgY);

                switch (ret) {
                    case GameDefine.Game_Menu:
                        isStart = false;
                        mGameControl.controlView(GameDefine.Game_Menu);
                        break;
                    case GameDefine.Game_Start:
                        if (mMusicOn) {
                            SoundManager.getInstance(context).play(R.raw.welcome);
                        }
                        isStart = true;
                        break;
                    case GameDefine.Game_Pause:
                        isStart = false;
                        break;
                }
            } else if (gameStick.test(orgX, orgY)) {
                if (isStart) {
                    switch (gameStick.handleTouchEvent(orgX, orgY)) {
                        case GameStick.Down:
                            moveDown();
                            break;
                        case GameStick.Left:
                            moveLeft();
                            break;
                        case GameStick.Right:
                            moveRight();
                            break;
                        case GameStick.Up:
                            reshape();
                            break;
                    }
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (gamePanel.test(event.getRawX(), event.getRawY())) {
                if (isStart) {
                    if (orgX == event.getRawX() && orgY == event.getRawY()) {
                        reshape();
                    }
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (gamePanel.test(event.getRawX(), event.getRawY())) {
                if (isStart) {
                    handleMoveEvent(event.getRawX(), event.getRawY());
                }
            }
        }


        return true;
    }

    public void handleMoveEvent(float x, float y) {

        if (Math.abs(y - orgY) <= CubeSprite.CUBE_SIZE) {
            if (x >= orgX + CubeSprite.CUBE_SIZE) {
                if (collisionSprite.getX() + collisionSprite.getWidth() < x)
                    moveRight();
            } else if (x <= orgX - CubeSprite.CUBE_SIZE) {
                if (collisionSprite.getX() > x)
                    moveLeft();
            }
        } else if (Math.abs(x - orgX) <= CubeSprite.CUBE_SIZE) {
            if (y >= orgY + CubeSprite.CUBE_SIZE) {
                if (collisionSprite.getY() + collisionSprite.getHeight() < y)
                    moveDown();
            }
        }
    }

    @Override
    public void run() {
        while (!isOver) {
            try {
                Thread.sleep(nDelay - (gamePanel.getnLevel() - 1) * 100);
                //	Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!isStart)
                continue;

            if (!moveDown()) {
                if (mMusicOn) {
                    SoundManager.getInstance(context).play(com.minmin.kari.tetris.R.raw.knock);
                }


                try {
                    fillGamePanel(gamePanel, sprite);
                } catch (ArrayIndexOutOfBoundsException e) {
                    handleGameOver();
                }

                sprite.setShape(nextSpriteIndex).reset().setColor(nextSpriteColor);
                collisionSprite.copyFrom(sprite);

                nextSpriteIndex = ((int) (Math.random() * 10)) % CubeSprite.nShapes;
                nextSpriteColor = ((int) (Math.random() * 10)) % Colors.nColors + 1;
                nextSprite.setShape(nextSpriteIndex).setColor(nextSpriteColor);

                scorePanel.setScore(gamePanel.getnScore());
                scorePanel.setLevel(gamePanel.getnLevel());

                if (isGameOver()) {
                    handleGameOver();
                }

            }

        }

    }

    private void fillGamePanel(GamePanel gamePanel, CubeSprite sprite) {
        int[] data = sprite.getShapeData();
        int m, n;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (data[i * 4 + j] == 1) {
                    m = (sprite.getY() + i * CubeSprite.CUBE_SIZE - gamePanel.getY()) / CubeSprite.CUBE_SIZE;
                    n = (sprite.getX() + j * CubeSprite.CUBE_SIZE - gamePanel.getX()) / CubeSprite.CUBE_SIZE;

                    gamePanel.setInfo(m, n, sprite.getColor());
                }
            }
        }
    }

    private synchronized boolean moveLeft() {
        collisionSprite.setX(collisionSprite.getX() - CubeSprite.CUBE_SIZE);
        if (!Collision.collisionWithLeft(gamePanel, collisionSprite)) {
            sprite.setX(collisionSprite.getX());
            return true;
        } else {
            collisionSprite.setX(sprite.getX());
            return false;
        }
    }

    private synchronized boolean moveRight() {
        collisionSprite.setX(collisionSprite.getX() + CubeSprite.CUBE_SIZE);
        if (!Collision.collisionWithRight(gamePanel, collisionSprite)) {
            sprite.setX(collisionSprite.getX());
            return true;
        } else {
            collisionSprite.setX(sprite.getX());
            return false;
        }
    }

    private synchronized boolean moveDown() {
        int y = collisionSprite.getY();
        collisionSprite.setY(y + CubeSprite.CUBE_SIZE);
        if (!Collision.collisionWithBottom(gamePanel, collisionSprite)) {
            sprite.setY(collisionSprite.getY());
            return true;
        } else {
            collisionSprite.setY(sprite.getY());
            return false;
        }
    }

    private synchronized boolean reshape() {
        collisionSprite.reshape();
        try {
            if (!Collision.collisionWithBottom(gamePanel, collisionSprite) &&
                    !Collision.collisionWithLeft(gamePanel, collisionSprite) &&
                    !Collision.collisionWithRight(gamePanel, collisionSprite)) {
                sprite.reshape();
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            collisionSprite.copyFrom(sprite);
            return false;
        }

        return false;
    }

    private boolean isGameOver() {
        if (gamePanel.getInfoData(0, 3) != 0 || gamePanel.getInfoData(0, 4) != 0)
            return true;

        return false;
    }

    private void handleGameOver() {
        if (mMusicOn) {
            SoundManager.getInstance(context).play(com.minmin.kari.tetris.R.raw.game_over);
        }
        SoundManager.getInstance(context).free();

		/*
		int info[][] = gamePanel.getInfo();
		for(int i=0; i<info.length; ++i)
		{
			for(int j=0; j<info[0].length; ++j)
			{
				info[i][j] = 0;
			}
		}
		 */

        gamePanel.free();

        Storages.getInstance(context).saveHeighScore(gamePanel.getnScore());

        isOver = true;

        isStart = false;
        mGameControl.controlView(GameDefine.Game_Over);
    }

    public void onPauseGame() {
        isStart = false;
    }

    public void onContinueGame() {
        isStart = true;
    }

}

