package SnakeGame;

public class Snake {
    private int snakeLength = 6;

    public void increaseLength() {
        snakeLength++;
    }

    public void resetLength() {
        snakeLength = 6;
    }

    public int getSnakeLength() {
        return snakeLength;
    }
}