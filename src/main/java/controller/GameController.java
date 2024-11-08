package controller;

import model.Board;
import model.Piece;

public class GameController {
    
    // Representa el tablero en el que se juega.
    public Board board;
    
    //La pieza actualmente en juego.
    public Piece currentPiece;

    //Indica si el juego ha terminado.
    public boolean isGameOver;

    /**
     * Constructor de la clase GameController.
     *
     * Inicializa el controlador con el tablero proporcionado 
     * y establece el estado del juego como activo.
     * También genera una nueva pieza para comenzar el juego.
     *
     * @param board El tablero en el que se jugará el juego.
     */
    public GameController(Board board) {
        this.board = board;
        this.isGameOver = false;
        spawnNewPiece();
    }

    /**
     * Genera una nueva pieza en el tablero.
     * La pieza empieza en el centro superior.
     */
    public void spawnNewPiece() {
        this.currentPiece = Piece.generateRandomPiece(); 
        currentPiece.setPosition(board.getWidth() / 2, 0);  
        if (board.checkCollision(currentPiece)) {
            this.isGameOver = true;
        }
    }
    
    /**
     * Cambiamos la pieza actual por otra recibida por parametro.
     *     */
    public void setPiece(final Piece piece) {
        this.currentPiece = piece;
    }

    /**
     * Mueve la pieza actual hacia abajo una posición.
     * @return true si el movimiento fue exitoso, false si no fue posible.
     */
    public boolean movePieceDown() {
        if (isGameOver) {
        	return false;
        }

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
