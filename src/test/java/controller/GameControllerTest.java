
package controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Board;
import model.Piece;
import org.mockito.Mockito;

public class GameControllerTest {
    private GameController gameController;
    private Piece piece;
    private Board board;

    @Before
    public void setUp() {
        board = new Board(10, 20); // Tablero de 10x20
        gameController = new GameController(board);
        piece = gameController.getCurrentPiece();
    }

    /**
     * Test: Verifica que una nueva pieza se genere en la posición inicial esperada.
     * Caja Negra: Partición equivalente - Generación de nueva pieza.
     */
    @Test
    public void testSpawnNewPiecePosition() {
        assertEquals(board.getWidth() / 2, piece.getX());
        assertEquals(0, piece.getY());
    }

    /**
     * Test: Verifica que el movimiento hacia abajo de la pieza funcione
     * correctamente. Caja Negra: Partición equivalente - Movimiento hacia abajo.
     * Caja Blanca: Cobertura de declaración en el método movePieceDown.
     */
    @Test
    public void testMovePieceDown() {
        boolean moved = gameController.movePieceDown();
        assertTrue(moved);
        assertEquals(1, piece.getY());
    }
    
    /**
     * Test: Verifica que todos los paths de la función
     * MovePieceDown funcionen correctamente.
     * correctamente.
     * Caja Negra: Partición equivalente - Movimiento hacia abajo.
     * Caja Blanca: Cobertura de caminos en el método movePieceDown.
     */
    @Test
    public void testMovePieceDownPathCoverage() {
        // 1. Caso: El juego ha terminado
    	gameController.getBoard().occupyCell(2, 0); // Simula un bloqueo inmediato
    	gameController.getBoard().occupyCell(3, 0); // Simula un bloqueo inmediato
    	gameController.getBoard().occupyCell(4, 0); // Simula un bloqueo inmediato
        gameController.getBoard().occupyCell(5, 0); // Simula un bloqueo inmediato
        gameController.getBoard().occupyCell(6, 0); // Simula un bloqueo inmediato
        gameController.getBoard().occupyCell(7, 0); // Simula un bloqueo inmediato
        gameController.spawnNewPiece();
        assertTrue("SpawnNewPiece no cree que haya acabado", gameController.getIsGameOver());
        boolean result = gameController.movePieceDown(); // La primera pieza genera colisión
        assertFalse("El juego debe estar terminado después de colisión inicial", result);

        // Reiniciar el estado del juego
        board = new Board(10, 20); // Nuevo tablero vacío
        gameController = new GameController(board);

        // 2. Caso: Movimiento exitoso
        Piece piece = new Piece(new boolean[][]{{true, true}, {true, true}}); // Pieza cuadrada
        gameController.setPiece(piece);
        piece.setPosition(4, 5); // Ubicación inicial segura

        result = gameController.movePieceDown();
        assertTrue("La pieza debe moverse hacia abajo con éxito", result);
        assertEquals("La posición Y de la pieza debe incrementarse", 6, piece.getY());

        // 3. Caso: Movimiento con colisión
        piece.setPosition(4, board.getHeight() - piece.getHeight()); // Pieza cerca del fondo
        result = gameController.movePieceDown();
        assertFalse("El movimiento debe fallar debido a colisión con el fondo", result);
        }

    /**
     * Test: Verifica que el movimiento a la izquierda de la pieza funcione
     * correctamente. Caja Negra: Partición equivalente - Movimiento a la izquierda.
     */
    @Test
    public void testMovePieceLeft() {
        gameController.movePieceLeft();
        assertEquals(board.getWidth() / 2 - 1, piece.getX());
    }

    /**
     * Test: Verifica que el movimiento a la izquierda se detenga en el borde. Caja
     * Negra: Caso límite - Movimiento hacia el borde izquierdo.
     */
    @Test
    public void testMovePieceLeftBoundary() {
        for (int i = 0; i < board.getWidth(); i++) {
            gameController.movePieceLeft();
        }
        assertEquals(0, piece.getX());
    }

    /**
     * Test: Verifica que el movimiento a la derecha de la pieza funcione
     * correctamente. Caja Negra: Partición equivalente - Movimiento a la derecha.
     */
    @Test
    public void testMovePieceRight() {
        gameController.movePieceRight();
        assertEquals(board.getWidth() / 2 + 1, piece.getX());
    }

    /**
     * Test: Verifica que el movimiento a la derecha se detenga en el borde. Caja
     * Negra: Caso límite - Movimiento hacia el borde derecho.
     */
    @Test
    public void testMovePieceRightBoundary() {
        for (int i = 0; i < board.getWidth(); i++) {
            gameController.movePieceRight();
        }
        assertEquals(board.getWidth() - piece.getWidth(), piece.getX());
    }

    /**
     * Test: Verifica que el método `rotatePiece` funcione y revierta la rotación si
     * hay una colisión. Caja Blanca: Cobertura de caminos - Rotación y deshacer
     * rotación.
     */
    @Test 
    public void testRotatePieceWithCollision() { 
        // Crea un mock de Board
        Board mockBoard = Mockito.mock(Board.class);

        // Crea un mock de Piece
        Piece mockPiece = Mockito.mock(Piece.class);

        // Crea la instancia de GameController con el mock del tablero
        GameController gameController = new GameController(mockBoard);

        // Configura el mock de Piece
        boolean[][] shape = {
            { false, true, false }, 
            { true, true, true }
        };
        Mockito.when(mockPiece.getShape()).thenReturn(shape);

        // Configura el tablero para simular una colisión
        Mockito.doReturn(true).when(mockBoard).checkCollision(mockPiece);

        // Establece la pieza actual como el mock de Piece
        gameController.setPiece(mockPiece);

        // Llama al método que deseas probar
        gameController.rotatePiece();

        // Verifica que los métodos correctos fueron llamados
        Mockito.verify(mockPiece).rotateClockwise();
        Mockito.verify(mockPiece).rotateCounterClockwise(); 
    }



    /**
     * Test: Verifica que el juego termine si no hay espacio para una nueva pieza.
     * Design by Contract - Postcondición: El juego debe estar en estado de Game
     * Over.
     */
      @Test public void testGameOverWhenNoSpaceForNewPiece() { 
    	  	board = Mockito.mock(Board.class);
            Mockito.when(board.checkCollision(Mockito.any())).thenReturn(true);
            gameController = new GameController(board);
            assertTrue(gameController.getIsGameOver()); 
            }
     
}
