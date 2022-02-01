package SnakeGame;

public abstract class Unit {
    private int y;
    private int x;

    public Unit() {
        this.setY(y);
        this.setX(x);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}