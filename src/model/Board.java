package model;

public class Board {
    private int width;
    private int height;
    private boolean[][] grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new boolean[height][width]; //board inicialization
    }

    public boolean isCellOccupied(int x, int y) {
    	
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false; // out of range
        }
        return grid[y][x];
    }

    public void occupyCell(int x, int y) {

        if (y >= 0 && y < height && x >= 0 && x < width) {
            grid[y][x] = true;
        }
    }

}
