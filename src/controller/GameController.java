package controller;

import model.Board;
import model.Piece;

public class GameController {
    private Board board;
    private Piece currentPiece;

    public GameController(Board board) {
        this.board = board;
    }

    public void spawnPiece(Piece piece) {
        currentPiece = piece;
        // Colocar pieza en el tablero
        // Implementar lógica para verificar si la posición inicial está ocupada
    }

    
}