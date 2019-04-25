package game.tetris.game;

public interface Audio {
    void playMove();

    void playTransform();

    void playBlocked();

    void playPlaced();

    void playScore1();

    void playScore2();

    void playScore3();

    void playScore4();

    void playGameOver();

    void destroy();
}
