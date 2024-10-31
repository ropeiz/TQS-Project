// src/view/GameView.java
package view;

import model.Board;

/**
 * Clase GameView.
 * Representa la vista del juego, mostrando el estado actual del tablero en la consola.
 */
public class GameView {

    /**
     * Muestra el tablero en la consola.
     * @param board El tablero del juego a mostrar.
     */
    public void displayBoard(Board board) {
        int width = board.getWidth();
        int height = board.getHeight();

        // Dibuja el borde superior
        System.out.println("+" + "-".repeat(width) + "+");

        // Dibuja el tablero fila por fila
        for (int y = 0; y < height; y++) {
            System.out.print("|"); // Borde izquierdo
            for (int x = 0; x < width; x++) {
                // Muestra una "X" para celdas ocupadas y un espacio para celdas vacías
                System.out.print(board.isCellOccupied(x, y) ? "X" : " ");
            }
            System.out.println("|"); // Borde derecho
        }

        // Dibuja el borde inferior
        System.out.println("+" + "-".repeat(width) + "+");
    }
}
