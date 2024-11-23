// test/model/BoardTest.java
package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private Board board;
    private Piece piece;
    private boolean[][] shape;

    @Before
    public void setUp() {
        board = new Board(10, 20); // Inicializa un tablero de 10x20
        shape = new boolean[][]{
            {true, true},
            {true, true}
        };
        piece = new Piece(shape);
    }

    /**
     * Test: Verifica que una celda esté desocupada por defecto.
     * Caja Negra: Partición equivalente - Celda no ocupada.
     */
    @Test
    public void testIsCellOccupiedInitialFalse() {
        assertFalse(board.isCellOccupied(0, 0)); // Espera false para una celda inicialmente vacía
    }

    /**
     * Test: Verifica que una celda se marca como ocupada.
     * Caja Negra: Partición equivalente - Celda ocupada.
     */
    @Test
    public void testIsCellOccupiedAfterOccupyCell() {
        board.occupyCell(0, 0);
        assertTrue(board.isCellOccupied(0, 0)); // Espera true después de ocupar
    }


    /**
     * Test: Verifica que varias celdas ocupadas se detectan correctamente.
     * Caja Blanca: Cobertura de declaraciones y caminos.
     * Caja Negra: Partición equivalente - Varias celdas ocupadas.
     */
    @Test
    public void testMultipleCellsOccupied() {
        board.occupyCell(0, 0);
        board.occupyCell(5, 5);
        assertTrue(board.isCellOccupied(0, 0));
        assertTrue(board.isCellOccupied(5, 5));
        assertFalse(board.isCellOccupied(1, 1)); // Celda no ocupada
    }

    /**
     * Test: Verifica que la celda en el límite inferior derecho se ocupa correctamente.
     * Caja Negra: Caso límite - Última celda en el tablero.
     */
    @Test
    public void testLastCellBoundary() {
        board.occupyCell(9, 19); // Última celda en un tablero 10x20
        assertTrue(board.isCellOccupied(9, 19)); // Comprueba que la última celda está ocupada
    }


    /**
     * Test: Comprueba que al ocupar una celda se cumple el contrato.
     * Design by Contract - Precondición: Índices dentro de límites.
     */
    @Test
    public void testOccupyCellWithinBoundsContract() {
        board.occupyCell(4, 4);
        assertTrue(board.isCellOccupied(4, 4));
    }

    /**
     * Test de cobertura de loops en el método que limpia una línea completa (Ejemplo).
     * Caja Blanca: Prueba de loop simple y cobertura de declaraciones.
     */
    @Test
    public void testClearLineLoop() {
        for (int x = 0; x < 10; x++) {
            board.occupyCell(x, 19); // Llena la última fila
        }
        board.clearLine(19); // Método que debe limpiar la fila
        for (int x = 0; x < 10; x++) {
            assertFalse(board.isCellOccupied(x, 19)); // Verifica que cada celda esté vacía
        }
    }

    /**
     * Test para asegurar que se cumple la cobertura de decisiones y condiciones.
     * Caja Blanca: Cobertura de decisión en el método `isCellOccupied`.
     */
    @Test
    public void testDecisionCoverageIsCellOccupied() {
        assertFalse(board.isCellOccupied(5, 5)); // Decisión: celda vacía
        board.occupyCell(5, 5);
        assertTrue(board.isCellOccupied(5, 5));  // Decisión: celda ocupada
    }
    
    @Test
    public void testGetWidth() {
        assertEquals(10, board.getWidth());
    }
    
    @Test
    public void testGetHeight() {
        assertEquals(20, board.getHeight());
    }

    /**
     * Test: Verifica que checkCollision detecta colisiones con los bordes del tablero.
     * Caja Negra: Caso límite - Colisión en los bordes.
     */
    @Test
    public void testCheckCollisionWithBoardEdges() {

        piece.setPosition(-1, 0); // Colocar fuera del borde izquierdo
        assertTrue(board.checkCollision(piece));

        piece.setPosition(9, 19); // Colocar fuera del borde derecho
        assertTrue(board.checkCollision(piece));

        piece.setPosition(0, -1); // Colocar fuera del borde superior
        assertTrue(board.checkCollision(piece));

        piece.setPosition(0, 19); // Colocar en el borde inferior
        assertTrue(board.checkCollision(piece));
    }

    /**
     * Test: Verifica que lockPiece marca las celdas de la pieza en el tablero como ocupadas.
     * Caja Negra: Partición equivalente - Bloquear una pieza en el tablero.
     */
    @Test
    public void testLockPiece() {
        piece.setPosition(0, 0); // Coloca la pieza en la esquina superior izquierda
        board.lockPiece(piece);

        assertTrue(board.isCellOccupied(0, 0));
        assertTrue(board.isCellOccupied(0, 1));
        assertTrue(board.isCellOccupied(1, 0));
        assertTrue(board.isCellOccupied(1, 1));
    }

    /**
     * Test: Verifica que checkCollision detecta colisiones con piezas ya bloqueadas.
     */
    @Test
    public void testCheckCollisionWithLockedPieces() {
    	
        // Bloquea una pieza en la esquina superior izquierda
        board.lockPiece(piece);

        // Coloca otra pieza en la misma posición y verifica que haya colisión
        Piece newPiece = new Piece(new boolean[][]{
            {true, true},
            {true, true}
        });
        newPiece.setPosition(0, 0);
        assertTrue(board.checkCollision(newPiece));
    }
}
