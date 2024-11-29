// test/model/BoardTest.java
package model;

import static org.junit.Assert.*;

import java.util.Arrays;

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
     * Caja Blanca: Cobertura de sentencias en la función 'isCellOccupied'.
     */
    @Test
    public void testIsCellOccupiedInitialFalse() {
    	// Espera false para una celda inicialmente vacía
        assertFalse(board.isCellOccupied(0, 0));
    }

    /**
     * Test: Verifica que una celda se marca como ocupada.
     * Caja Negra: Partición equivalente - Celda ocupada.
     * Caja Blanca: Cobertura de sentencias en la función 'isCellOccupied'.
     */
    @Test
    public void testIsCellOccupiedAfterOccupyCell() {
    	board.occupyCell(0, 0);
        // Espera true después de ocupar
        assertTrue(board.isCellOccupied(0, 0));
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
     * Design by Contract - Precondición: Índices dentro de límites.
     */
    @Test
    public void testLastCellBoundary() {
        board.occupyCell(9, 19); // Última celda en un tablero 10x20
        assertTrue(board.isCellOccupied(9, 19)); // Comprueba que la última celda está ocupada
    }
    
    
    /**
     * Test: Verifica que el método isFull detecta correctamente si una fila está completamente llena.
     * Caja Negra: Caso límite - Fila completa y fila incompleta.
     */
    @Test
    public void testIsFull() {
        // Fila completamente llena (todas las celdas son true)
        boolean[] fullRow = new boolean[10];
        Arrays.fill(fullRow, true); // Llena todas las celdas con 'true'
        
        // Fila incompleta (una celda vacía)
        boolean[] incompleteRow = new boolean[10];
        Arrays.fill(incompleteRow, true); // Llena todas las celdas con 'true'
        incompleteRow[5] = false; // Hacemos que la celda 5 sea vacía (false)
        
        // Verificamos que la fila completa sea detectada como llena
        assertTrue(board.isFull(fullRow)); // Debe ser true, ya que todas las celdas están ocupadas
        
        // Verificamos que la fila incompleta sea detectada como no llena
        assertFalse(board.isFull(incompleteRow)); // Debe ser false, ya que la celda 5 está vacía
    }

    /**
     * Test para verificar que se limpia bien una fila.
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
     * Test: Verifica que el método clearFullLines elimina las filas completas y agrega filas vacías en la parte superior.
     * Caja Negra: Caso límite - Líneas completas que deben ser eliminadas.
     */
    @Test
    public void testClearFullLines() {
        // Rellenamos algunas filas con celdas ocupadas
        for (int x = 0; x < 10; x++) {
            board.occupyCell(x, 19); // Rellena la última fila
        }
        for (int x = 0; x < 10; x++) {
            board.occupyCell(x, 18); // Rellena la penúltima fila
        }        
        // Llamamos al método que debe eliminar las filas completas
        board.clearFullLines();

        // Verificamos que las filas 19 y 18 se hayan limpiado
        for (int x = 0; x < 10; x++) {
            assertFalse(board.isCellOccupied(x, 19)); // La última fila debe estar vacía
            assertFalse(board.isCellOccupied(x, 18)); // La penúltima fila debe estar vacía
        }

        // Verificamos que el tamaño del tablero se mantenga correctamente con nuevas filas vacías
        assertEquals(20, board.getHeight()); // El alto del tablero debe seguir siendo 20
    }

    /**
     * Test para asegurar que el constructor y getWidth funcionan correctamente.
     * Caja Blanca: Cobertura de sentencias en constructor y en 'getWidth()'.
     */
    @Test
    public void testGetWidth() {
        assertEquals(10, board.getWidth());
    }

    /**
     * Test para asegurar que el constructor y getHeight funcionan correctamente.
     * Caja Blanca: Cobertura de sentencias en constructor y en 'getHeight()'.
     */
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
     * Test: Verifica que el método prependRow añade una fila al inicio del tablero.
     * Caja Negra: Se prueba el comportamiento de prependRow al agregar una fila.
     * Se espera que la nueva fila esté al inicio del tablero y las filas existentes se desplacen hacia abajo.
     */
    @Test
    public void testPrependRow() {
        // Llenamos algunas celdas en la fila 19 y 18
        for (int x = 0; x < board.getWidth(); x++) {
            board.occupyCell(x, 19); // Llena la fila 19
        }
        
        board.occupyCell(1, 18);
        board.occupyCell(3, 18);
        board.occupyCell(6, 18);
        board.occupyCell(8, 18);
        
        // Llamamos al método clearFullLines para que se agregue una nueva fila vacía arriba
        board.clearFullLines(); // Debería agregar una fila vacía al principio del tablero.

        // Verificamos que la nueva fila vacía esté al principio (en la posición 0)
        for (int x = 0; x < board.getWidth(); x++) {
            assertFalse(board.isCellOccupied(x, 0)); // La fila 0 debe estar vacía
        }

        // Verificamos que las filas 18 y 19 hayan sido desplazadas
        assertFalse(board.isCellOccupied(0, 19));// La fila 19 debe ser igual que era la 18
        assertTrue(board.isCellOccupied(1, 19));
        assertFalse(board.isCellOccupied(2, 19));
        assertTrue(board.isCellOccupied(3, 19));
        assertFalse(board.isCellOccupied(4, 19));
        assertFalse(board.isCellOccupied(5, 19));
        assertTrue(board.isCellOccupied(6, 19));
        assertFalse(board.isCellOccupied(7, 19));
        assertTrue(board.isCellOccupied(8, 19));
        assertFalse(board.isCellOccupied(9, 19));

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
