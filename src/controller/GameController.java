package controller;

import model.Board;
import model.Piece;

/**
 * Controlador principal del juego Tetris.
 * Gestiona la lógica del juego, como el movimiento y rotación de las piezas, y la actualización del tablero.
 */
public class GameController {
    private Board board;
    private Piece currentPiece;
    private boolean isGameOver;

    public GameController(Board board) {
        this.board = board;
        this.isGameOver = false;
        spawnNewPiece();
    }

    /**
     * Genera una nueva pieza en el tablero.
     */
    public void spawnNewPiece() {
        this.currentPiece = Piece.generateRandomPiece();  // Asume que existe un método estático para generar una pieza aleatoria
        currentPiece.setPosition(board.getWidth() / 2, 0);  // La pieza empieza en el centro superior
        if (board.checkCollision(currentPiece)) {
            this.isGameOver = true;
        }
    }

    /**
     * Mueve la pieza actual hacia abajo una posición.
     * @return true si el movimiento fue exitoso, false si no fue posible.
     */
    public boolean movePieceDown() {
        if (isGameOver) return false;

        currentPiece.move(0, 1);
        if (board.checkCollision(currentPiece)) {
            currentPiece.move(0, -1);  // Revertir movimiento
            board.lockPiece(currentPiece);
            board.clearFullLines();
            spawnNewPiece();
            return false;
        }
        return true;
    }

    /**
     * Mueve la pieza actual a la izquierda.
     */
    public void movePieceLeft() {
        if (!isGameOver) {
            currentPiece.move(-1, 0);
            if (board.checkCollision(currentPiece)) {
                currentPiece.move(1, 0);  // Revertir movimiento
            }
        }
    }

    /**
     * Mueve la pieza actual a la derecha.
     */
    public void movePieceRight() {
        if (!isGameOver) {
            currentPiece.move(1, 0);
            if (board.checkCollision(currentPiece)) {
                currentPiece.move(-1, 0);  // Revertir movimiento
            }
        }
    }

    /**
     * Rota la pieza actual en el sentido de las agujas del reloj.
     */
    public void rotatePiece() {
        if (!isGameOver) {
            currentPiece.rotateClockwise();
            if (board.checkCollision(currentPiece)) {
                currentPiece.rotateCounterClockwise();  // Revertir rotación
            }
        }
    }

    /**
     * Verifica si el juego ha terminado.
     * @return true si el juego ha terminado, false en caso contrario.
     */
    public boolean isGameOver() {
        return isGameOver;
    }
    
    
}