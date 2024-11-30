
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
        board = new Board(10, 20);
        gameController = new GameController(board);
        piece = gameController.getCurrentPiece();
    }

    /**
     * Test: Verifica que una nueva pieza se genere en la posición inicial esperada.
     */
    @Test
    public void testSpawnNewPiecePosition() {
        assertEquals(board.getWidth() / 2, piece.getX());
        assertEquals(0, piece.getY());
    }

    /**
     * Test: Verifica que el movimiento hacia abajo de la pieza funcione.
     * correctamente. 
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
     */
    @Test
    public void testMovePieceDownPathCoverage() {
        // 1. Caso: El juego ha terminado
    	for (int x = 0; x < 10; x++) { 
    	    gameController.getBoard().occupyCell(x, 0); // Ocupa todas las celdas de la primera fila (y = 0)
    	}
        gameController.spawnNewPiece();
        boolean result = gameController.movePieceDown(); // La primera pieza genera colisión
        assertFalse("El juego debe estar terminado después de colisión inicial", result);

        // Reiniciar el estado del juego
        board = new Board(10, 20);
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
     * Test: Verifica que el movimiento a la izquierda de la pieza funcione correctamente.
     */
    @Test
    public void testMovePieceLeft() {
        gameController.movePieceLeft();
        assertEquals(board.getWidth() / 2 - 1, piece.getX());
    }

    /**
     * Test: Verifica que el movimiento a la izquierda se detenga en el borde.
     */
    @Test
    public void testMovePieceLeftLoop() {
        // Caso 1: Sin iteraciones (ya en el borde)
        piece.setPosition(0, 0);
        gameController.movePieceLeft();
        assertEquals(0, piece.getX());

        // Caso 2: Una sola iteración
        piece.setPosition(1, 0); // Una posición desde el borde
        gameController.movePieceLeft();
        assertEquals(0, piece.getX()); // Debe moverse una celda a la izquierda

        // Caso 3: Varias iteraciones (caso general)
        piece.setPosition(5, 0); // Más lejos del borde
        for (int i = 0; i < 5; i++) {
            gameController.movePieceLeft();
        }
        assertEquals(0, piece.getX()); // Debe llegar al borde

        // Caso 4: Condición límite superior (máximo permitido)
        for (int i = 0; i < 10; i++) { // Intentar mover más allá del borde
            gameController.movePieceLeft();
        }
        assertEquals(0, piece.getX()); // Debe quedarse en el borde
    }


    /**
     * Test: Verifica que el movimiento a la derecha de la pieza funcione
     * correctamente.
     */
    @Test
    public void testMovePieceRight() {
        gameController.movePieceRight();
        assertEquals(board.getWidth() / 2 + 1, piece.getX());
    }

    /**
     * Test: Verifica que el movimiento a la derecha se detenga en el borde.
     */
    @Test
    public void testMovePieceRightLoop() {
    	// Caso 1: Sin iteraciones (ya en el borde derecho)
        piece.setPosition(board.getWidth() - piece.getWidth(), 0); // Coloca la pieza en el borde derecho
        gameController.movePieceRight();
        assertEquals(board.getWidth() - piece.getWidth(), piece.getX()); // No debería moverse más allá del borde

        // Caso 2: Una sola iteración
        piece.setPosition(board.getWidth() - piece.getWidth() - 1, 0); // Justo antes del borde
        gameController.movePieceRight();
        assertEquals(board.getWidth() - piece.getWidth(), piece.getX()); // Debería moverse exactamente una celda a la derecha

        // Caso 3: Varias iteraciones
        piece.setPosition(board.getWidth() / 2, 0); // En algún lugar intermedio
        for (int i = 0; i < (board.getWidth() / 2); i++) {
            gameController.movePieceRight();
        }
        assertEquals(board.getWidth() - piece.getWidth(), piece.getX()); // Debe alcanzar el borde derecho

        // Caso 4: Condición límite superior
        for (int i = 0; i < board.getWidth() * 2; i++) { // Intentar mover más allá del borde
            gameController.movePieceRight();
        }
        assertEquals(board.getWidth() - piece.getWidth(), piece.getX()); // No debe pasar el límite derecho
    }

    /**
     * Test: Verifica que el método rotatePiece funcione y revierta la rotación si
     * hay una colisión.
     */
    @Test 
    public void testRotatePieceWithCollision() { 
    	
        Board mockBoard = Mockito.mock(Board.class);

        Piece mockPiece = Mockito.mock(Piece.class);

        GameController gameController = new GameController(mockBoard);

        boolean[][] shape = {
            { false, true, false }, 
            { true, true, true }
        };
        Mockito.when(mockPiece.getShape()).thenReturn(shape);

        Mockito.doReturn(true).when(mockBoard).checkCollision(mockPiece);

        gameController.setPiece(mockPiece);

        gameController.rotatePiece(true);

        Mockito.verify(mockPiece).rotateClockwise();
        Mockito.verify(mockPiece).rotateCounterClockwise(); 
    }

    /**
     * Test: Verifica que el juego termine si no hay espacio para una nueva pieza.
     */
      @Test public void testGameOverWhenNoSpaceForNewPiece() { 
    	  	board = Mockito.mock(Board.class);
            Mockito.when(board.checkCollision(Mockito.any())).thenReturn(true);
            gameController = new GameController(board);
            assertTrue(gameController.getIsGameOver()); 
            }
     
}
