package SnakeGame;

public class Apple extends Unit {
    private int eatenApples = 0;

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public int getX() {
        return super.getX();
    }

    public void eatApple() {
        eatenApples++;
    }

    public int getEatenApples() {
        return eatenApples;
    }

    public void resetApples() {
        this.eatenApples = 0;
    }
}