// src/TetrisGame.java
import controller.GameController;
import model.Board;
import view.GameView;
import java.util.Scanner;

/**
 * Inicializa el tablero, el controlador y la vista.
 * Contiene el bucle de juego que actualiza y procesa los movimientos.
 */
public class TetrisGame {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private Board board;
    private GameController controller;
    private GameView view;
    private boolean running;

    public TetrisGame() {
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        controller = new GameController(board);
        view = new GameView();
        running = true;
    }

    /**
     * Inicia el bucle principal del juego.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a Tetris! Controles:");
        System.out.println("a = Izquierda, d = Derecha, s = Abajo, " +
                "w = Rotar, q = Salir");

        while (running) {
            view.displayBoard(board); // Muestra el tablero

            System.out.print("Introduce movimiento: ");
            String input = scanner.nextLine();

            processInput(input);

            // Actualiza el estado del juego moviendo la pieza hacia abajo
            if (!controller.movePieceDown()) {
                if (controller.isGameOver()) {
                    System.out.println("¡Juego terminado!");
                    running = false;
                }
            }
        }

        scanner.close();
    }

    /**
     * Procesa la entrada del usuario y hace los movimientos correspondientes.
     */
    private void processInput(String input) {
        switch (input) {
            case "a":
                controller.movePieceLeft();
                break;
            case "d":
                controller.movePieceRight();
                break;
            case "s":
                controller.movePieceDown();
                break;
            case "w":
                controller.rotatePiece();
                break;
            case "q":
                running = false;
                System.out.println("Saliendo del juego...");
                break;
            default:
            	System.out.println("Entrada no válida. Usa a (izq), d (der), s (abajo), " +
                        "w (rotar), q (salir).");
                break;
        }
    }

    public static void main(String[] args) {
        TetrisGame game = new TetrisGame();
        game.start();
    }
}
