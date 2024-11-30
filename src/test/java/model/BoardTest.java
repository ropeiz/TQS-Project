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
    
    class MockPiece extends Piece {
        private int mockX;
        private int mockY;

        public MockPiece(int mockX, int mockY) {
        	// Forma de la pieza
            super(new boolean[][]{{false, true, false}, {true, true, true}});

            this.mockX = mockX;
            this.mockY = mockY;
        }

        @Override
        public int getX() {
            return mockX;
        }

        @Override
        public int getY() {
            return mockY;
        }

        @Override
        public boolean[][] getShape() {
            return super.getShape();
        }
    }

    /**
     * Test: Verifica que una celda esté desocupada por defecto.
     */
    @Test
    public void testIsCellOccupiedInitialFalse() {
        assertFalse(board.isCellOccupied(0, 0));
    }

    /**
     * Test: Verifica que una celda se marca como ocupada.
     */
    @Test
    public void testIsCellOccupiedAfterOccupyCell() {
        // Caso 1: Todas las condiciones son verdaderas (dentro del tablero, celda no ocupada).
        assertFalse("Caso 1: Celda libre dentro del tablero", board.isCellOccupied(5, 5));

        // Caso 2: Todas las condiciones son verdaderas (dentro del tablero, celda ocupada).
        board.occupyCell(3, 3);
        assertTrue("Caso 2: Celda ocupada dentro del tablero", board.isCellOccupied(3, 3));

        // Caso 3: y < 0 (fuera del borde superior).
        assertFalse("Caso 3: Fuera del borde superior", board.isCellOccupied(5, -1));

        // Caso 4: y >= height (fuera del borde inferior).
        assertFalse("Caso 4: Fuera del borde inferior", board.isCellOccupied(5, 20));

        // Caso 5: x < 0 (fuera del borde izquierdo).
        assertFalse("Caso 5: Fuera del borde izquierdo", board.isCellOccupied(-1, 5));

        // Caso 6: x >= width (fuera del borde derecho).
        assertFalse("Caso 6: Fuera del borde derecho", board.isCellOccupied(10, 5));

        // Caso 7: Múltiples condiciones falsas (fuera del tablero en ambas dimensiones).
        assertFalse("Caso 7: Fuera del tablero completamente", board.isCellOccupied(-1, 20));
    }

    /**
     * Test: Verifica que se marquen como ocupadas las celdas que estan dentro del tablero.
     */
    @Test
    public void testOccupyCellWithinBoard() {
        // Caso 1: y >= 0 y y < height (ambas condiciones de y positivas)
        board.occupyCell(5, 5);
        assertTrue("Error en caso 1",board.isCellOccupied(5, 5));

        // Caso 2: y >= 0 pero y >= height (y fuera del rango)
        board.occupyCell(5, 15);
        assertFalse("Error en caso 2",board.isCellOccupied(5, 20));

        // Caso 3: y < 0 pero y < height (y fuera del rango por ser negativo)
        board.occupyCell(5, -1);
        assertFalse("Error en caso 3",board.isCellOccupied(5, -1));

        // Caso 4: x >= 0 y x < width (ambas condiciones de x positivas)
        board.occupyCell(9, 5);
        assertTrue("Error en caso 4",board.isCellOccupied(9, 5));

        // Caso 5: x >= 0 pero x >= width (x fuera del rango)
        board.occupyCell(25, 5);
        assertFalse("Error en caso 5",board.isCellOccupied(25, 5));

        // Caso 6: x < 0 pero x < width (x fuera del rango por ser negativo)
        board.occupyCell(-5, 5);
        assertFalse("Error en caso 6",board.isCellOccupied(-5, 5));

        // Caso 7: Ambas condiciones de x y y son negativas
        board.occupyCell(-5, -1);
        assertFalse("Error en caso 7",board.isCellOccupied(-5, -1));

        // Caso 8: Ambas condiciones de x y y son mayores que width/height
        board.occupyCell(25, 15);
        assertFalse("Error en caso 8",board.isCellOccupied(25, 25));
    }
    
    /**
     * Test: Verifica que la celda en el límite inferior derecho se ocupa correctamente.
     */
    @Test
    public void testCellBoundary() {
        board.occupyCell(9, 19);
        board.occupyCell(9, 0);
        board.occupyCell(0, 19);
        board.occupyCell(0, 0);
       
        assertTrue(board.isCellOccupied(9, 19));
        assertTrue(board.isCellOccupied(9, 0));
        assertTrue(board.isCellOccupied(0, 19));
        assertTrue(board.isCellOccupied(0, 0));
    }
    
    
    /**
     * Test: Verifica que el método isFull detecta correctamente si una fila está completamente llena.
     */
    @Test
    public void testIsFull() {
        // Fila completamente llena (todas las celdas son true)
        boolean[] fullRow = new boolean[10];
        Arrays.fill(fullRow, true);
        
        // Fila incompleta (una celda vacía)
        boolean[] incompleteRow = new boolean[10];
        Arrays.fill(incompleteRow, true);
        incompleteRow[5] = false; // Hacemos que la celda 5 sea vacía (false)
        
        // Verificamos que la fila completa sea detectada como llena
        assertTrue(board.isFull(fullRow));
        
        // Verificamos que la fila incompleta sea detectada como no llena
        assertFalse(board.isFull(incompleteRow));
    }
    
    /**
     * Test: Verifica que el método clearFullLines elimina las filas completas 
     * y agrega filas vacías en la parte superior.
     */
    @Test
    public void testClearFullLines() {
        // Rellenamos algunas filas con celdas ocupadas
        for (int x = 0; x < 10; x++) {
            board.occupyCell(x, 19);
        }
        for (int x = 0; x < 10; x++) {
            board.occupyCell(x, 18);
        }        

        board.clearFullLines();

        // Verificamos que las filas 19 y 18 se hayan limpiado
        for (int x = 0; x < 10; x++) {
            assertFalse(board.isCellOccupied(x, 19));
            assertFalse(board.isCellOccupied(x, 18));
        }

        // Verificamos que el tamaño del tablero se mantenga correctamente con nuevas filas vacías
        assertEquals(20, board.getHeight());
    }

    /**
     * Test para asegurar que el constructor y getWidth funcionan correctamente.
     */
    @Test
    public void testGetWidth() {
        assertEquals(10, board.getWidth());
    }

    /**
     * Test para asegurar que el constructor y getHeight funcionan correctamente.
     */
    @Test
    public void testGetHeight() {
        assertEquals(20, board.getHeight());
    }
    
    /**
     * Test: Verifica que lockPiece marca las celdas de la pieza en el tablero como ocupadas.
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
     * Test: Verifica que checkCollision detecta colisiones.
     */
    @Test
    public void testCheckCollisionConditionCoverage() {

    	// Colocar fuera del borde izquierdo
        MockPiece mockPiece = new MockPiece(-1, 0);
        assertTrue("Error en caso 1",board.checkCollision(mockPiece));

        // Colocar fuera del borde derecho
        mockPiece = new MockPiece(10, 0);
        assertTrue("Error en caso 2",board.checkCollision(mockPiece));
        
        // Colocar fuera del borde superior
        mockPiece = new MockPiece(0, -1);
        assertTrue("Error en caso 3",board.checkCollision(mockPiece));

        // Colocar en el borde inferior
        mockPiece = new MockPiece(0, 20);
        assertTrue("Error en caso 4",board.checkCollision(mockPiece));
        
        //Colocar dentro del tablero, pero en una celda ocupada
        board.occupyCell(5, 1); 
        mockPiece = new MockPiece(5, 0);
        assertTrue("Error en caso 5",board.checkCollision(mockPiece));

        // Caso 6: Colocar dentro del tablero en una celda libre (sin colisión)
        mockPiece = new MockPiece(2, 2);
        assertFalse("Error en caso 6",board.checkCollision(mockPiece));
        

    }
    
    /**
     * Test: Verifica que checkCollision detecta colisiones con piezas ya bloqueadas.
     */
    @Test
    public void testCheckCollisionWithLockedPieces() {
    	
        // Crear un mock de la pieza bloqueada en la esquina superior izquierda
        MockPiece mockPiece = new MockPiece(0, 0);
        board.lockPiece(mockPiece);

        // Crear un mock de una nueva pieza
        MockPiece newMockPiece = new MockPiece(0, 1);
        assertTrue(board.checkCollision(newMockPiece));
    }

    
    
    /**
     * Test: Verifica que el método prependRow añade una fila al inicio del tablero.
     * Se espera que la nueva fila esté al inicio del tablero y las filas existentes 
     * se desplacen hacia abajo.
     */
    @Test
    public void testPrependRow() {

        for (int x = 0; x < board.getWidth(); x++) {
            board.occupyCell(x, 19); // Llena la fila 19
        }
        
        board.occupyCell(1, 18);
        board.occupyCell(3, 18);
        board.occupyCell(6, 18);
        board.occupyCell(8, 18);
        
        board.clearFullLines();

        // Verificamos que la nueva fila vacía esté al principio (en la posición 0)
        for (int x = 0; x < board.getWidth(); x++) {
            assertFalse(board.isCellOccupied(x, 0));
        }

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
     * Test: Verifica que el método renderWithPiece renderiza correctamente el tablero con la pieza.
     */
    @Test
    public void testRenderWithPiece() {
        // Inicializamos un tablero de 5x5.
        Board board = new Board(5, 5);

        // Creamos una pieza de tamaño 2x2 (forma de cuadrado).
        boolean[][] shape = new boolean[][]{
            {true, true},
            {true, true}
        };
        Piece piece = new Piece(shape);
        
        piece.setPosition(1, 1);

        char[][] rendered = board.renderWithPiece(piece);

        char[][] expected = new char[][]{
            {' ', ' ', ' ', ' ', ' '},
            {' ', 'O', 'O', ' ', ' '},
            {' ', 'O', 'O', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' '}
        };


        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                assertEquals("Error en la celda (" + y + ", " + x + ")", expected[y][x], rendered[y][x]);
            }
        }
    }
    
    /**
     * Test: Verifica que el método renderWithPiece funcione correctamente en todos los casos.
     */
    @Test
    public void testRenderWithPieceConditionCoverage() {
    	// Caso 1: Cuando la pieza se coloca dentro del board y su forma es de 1x1.
        Piece piece = new Piece(new boolean[][] { { true } });
        piece.setPosition(5, 5); // pieza en (5,5)
        char[][] rendered = board.renderWithPiece(piece);
        assertEquals("Error en caso 1",'O', rendered[5][5]);

        // Caso 2: Cuando la pieza se coloca fuera del board.
        piece = new Piece(new boolean[][] { { true } });
        piece.setPosition(30, 30); // pieza fuera del board (30,30)
        rendered = board.renderWithPiece(piece);
        for (int i = 0; i < rendered.length; i++) {
            for (int j = 0; j < rendered[i].length; j++) {
                assertEquals("Error en caso 2", ' ', rendered[i][j]);
            }
        }

        // Caso 3: Cuando la pieza tiene una forma que no afecta al board.
        piece = new Piece(new boolean[][] { { false } });
        piece.setPosition(5, 5); // pieza sin forma
        rendered = board.renderWithPiece(piece);
        assertEquals("Error en caso 3",' ', rendered[5][5]);

        // Caso 4: Cuando la pieza se coloca parcialmente fuera del board.
        piece = new Piece(new boolean[][] { { true, true }, { true, true } });
        piece.setPosition(19, 9);
        rendered = board.renderWithPiece(piece);
        
    }
}
