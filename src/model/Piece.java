package model;

public class Piece {
    private boolean[][] shape; // shape representation
    private int x;
    private int y;

    public Piece(boolean[][] shape) {
        this.shape = shape;
        this.x = 0;
        this.y = 0;
    }

    public boolean[][] getShape() {
        return shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}