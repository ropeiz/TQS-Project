// src/TetrisGame.java
import controller.GameController;
import model.Board;
import model.Piece;
import view.GameView;

public class TetrisGame {

    public static void main(String[] args) {
        // Tamaño del tablero
        int width = 10;
        int height = 20;

        // Inicializar componentes del MVC
        Board board = new Board(width, height);
        GameView view = new GameView();
        GameController controller = new GameController(board);

        // Ejemplo de cómo mostrar el tablero en la vista
        view.displayBoard(board.getGrid());

        // Ciclo básico del juego (puedes expandir esta lógica)
        // Aquí podrías programar cómo añadir piezas, moverlas y actualizar el tablero.
    }
}
