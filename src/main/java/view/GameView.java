package view;

import model.Board;
import model.Piece;

public class GameView {

     /**
	 * Displays the game board with the current piece.
     * @param board the game board to display
     * @param currentPiece the current piece to display on the board
     */
    public void displayBoard(final Board board, final Piece currentPiece) {
        char[][] renderedBoard = board.renderWithPiece(currentPiece);
        int width = board.getWidth();
        int height = board.getHeight();

        System.out.println("+" + "-".repeat(width) + "+");

        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                System.out.print(renderedBoard[y][x]);
            }
            System.out.println("|");
        }

        System.out.println("+" + "-".repeat(width) + "+");
    }
}
