
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
        // Crea el mock de Piece
        Piece mockPiece = Mockito.mock(Piece.class);

        // Configura los métodos del mock
        boolean[][] shape = {
            { false, true, false }, 
            { true, true, true }
        };
        Mockito.when(mockPiece.getShape()).thenReturn(shape);

        // Crea el mock de Board y configúralo
        Board board = Mockito.mock(Board.class);
        gameController.setBoard(board);
        gameController.setPiece(mockPiece);

        // Configura la colisión en el tablero
        Mockito.doReturn(true).when(board).checkCollision(mockPiece);

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
            assertTrue(gameController.isGameOver()); 
            }
     
}
