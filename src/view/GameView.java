package view;

public class GameView {
    public void displayBoard(boolean[][] grid) {
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                System.out.print(cell ? "X" : "."); // 'X' Ocupied, '.' Empty
            }
            System.out.println();
        }
    }

}