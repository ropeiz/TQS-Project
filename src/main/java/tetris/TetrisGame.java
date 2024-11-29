package tetris;
// src/TetrisGame.java
import controller.GameController;
import model.Board;
import view.GameView;
import java.util.Scanner;

/**
 * Inicializa el tablero, el controlador y la vista. Contiene el bucle de juego
 * que actualiza y procesa los movimientos.
 */
public class TetrisGame {
    /** El ancho del tablero de juego. */
    private static final int BOARD_WIDTH = 10;

    /** La altura del tablero de juego. */
    private static final int BOARD_HEIGHT = 20;

    /** La instancia del tablero que maneja la lógica del juego. */
    private Board board;

    /** El controlador del juego que gestiona acciones/eventos del juego. */
    private GameController controller;

    /** La instancia de la vista responsable de renderizar el juego. */
    private GameView view;

    /** Indica si el juego se está ejecutando actualmente. */
    private boolean running;

    /**
     * Constructor de la clase TetrisGame.
     * Inicializa el tablero del juego, el
     * controlador del juego, la vista del
     * juego y establece el estado del juego a
     * ejecución.
     */

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
        System.out.println("Entrada no válida. " + "Usa a (izq), "
                + " d (der), s (abajo), e (rotar izquierda), r (rotar derecha), q (salir).");

        while (running) {
            view.displayBoard(board, controller.getCurrentPiece());

            System.out.print("Introduce movimiento: ");
            String input = scanner.nextLine();

            processInput(input);

            // Actualiza el estado del juego moviendo la pieza hacia abajo
            if (!controller.movePieceDown()) {
                if (controller.getIsGameOver()) {
                    System.out.println("¡Juego terminado!");
                    running = false;
                }
            }
        }

        scanner.close();
    }

    /**
     * Procesa la entrada del usuario.
     *
     * @param input La cadena de texto que introduce del usuario.
     */
    private void processInput(final String input) {
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
        case "r":
            controller.rotatePiece(true);
            break;
        case "e":
        	controller.rotatePiece(false);
        	break;
        case "q":
            running = false;
            System.out.println("Saliendo del juego...");
            break;
        default:
            System.out.println("Entrada no válida. " + "Usa a (izq), "
            + " d (der), s (abajo), e (rotar izquierda), r (rotar derecha), q (salir).");
            break;
        }
    }

    /**
     * Método principal que inicia la aplicación Tetris.
     *
     * @param args Argumentos de línea de comandos, proporcionados al iniciar el
     *             programa.
     */
    public static void main(final String[] args) {
        TetrisGame game = new TetrisGame();
        game.start();
    }
}
