// test/model/PieceTest.java
package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PieceTest {
    private Piece piece;
    private boolean[][] shape;

    @Before
    public void setUp() {
        // Representación de una pieza en forma de cuadrado (2x2)
        shape = new boolean[][]{
            {true, true},
            {true, true}
        };
        piece = new Piece(shape);
    }

    /**
     * Test: Verifica que la forma de la pieza se inicializa correctamente.
     * Caja Negra: Partición equivalente - Inicialización de la pieza.
     */
    @Test
    public void testGetShape() {
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        };
        assertArrayEquals(expectedShape, piece.getShape());
    }

    /**
     * Test: Verifica que la posición inicial de la pieza es (0,0).
     * Caja Negra: Partición equivalente - Posición inicial de la pieza.
     */
    @Test
    public void testInitialPosition() {
        assertEquals(0, piece.getX());
        assertEquals(0, piece.getY());
    }

    /**
     * Test: Verifica que la pieza se mueve correctamente a una nueva posición.
     * Caja Negra: Partición equivalente - Movimiento de la pieza.
     * Caja Blanca: Cobertura de declaración en el método `move`.
     */
    @Test
    public void testMovePiece() {
        piece.move(2, 3);
        assertEquals(2, piece.getX()); // Verifica nueva posición en X
        assertEquals(3, piece.getY()); // Verifica nueva posición en Y
    }

    /**
     * Test: Verifica que el método `move` maneja los valores negativos.
     * Caja Negra: Caso límite - Movimiento con valores negativos.
     */
    @Test
    public void testMovePieceNegativeValues() {
        piece.move(-1, -1);
        assertEquals(-1, piece.getX()); // Permite valores negativos
        assertEquals(-1, piece.getY());
    }

    /**
     * Test: Verifica que `rotateClockwise` gira la pieza 90 grados en el sentido de las agujas del reloj.
     * Caja Blanca: Cobertura de condición y caminos en el método `rotateClockwise`.
     */
    @Test
    public void testRotateClockwise() {
        piece.rotateClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        }; // En este caso, la rotación de un cuadrado permanece igual
        assertArrayEquals(expectedShape, piece.getShape());
    }

    /**
     * Test: Verifica que `rotateCounterClockwise` gira la pieza 90 grados en sentido antihorario.
     * Caja Blanca: Cobertura de condición y caminos en el método `rotateCounterClockwise`.
     */
    @Test
    public void testRotateCounterClockwise() {
        piece.rotateCounterClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        };
        assertArrayEquals(expectedShape, piece.getShape());
    }

    /**
     * Test: Verifica que el método `move` cumpla con el contrato de diseño.
     * Design by Contract - Precondición: Los valores de desplazamiento deben estar dentro de los límites del tablero.
     */
    @Test
    public void testMovePieceDesignByContract() {
        piece.move(5, 5);
        assertEquals(5, piece.getX());
        assertEquals(5, piece.getY());
    }

    /**
     * Test: Verifica que el método `rotateClockwise` cumple con el contrato de diseño.
     * Design by Contract - Postcondición: La matriz debe cambiar de tamaño tras la rotación.
     */
    @Test
    public void testRotateClockwiseDesignByContract() {
        piece = new Piece(new boolean[][]{
            {true, false},
            {true, true}
        });
        piece.rotateClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, false}
        };
        assertArrayEquals(expectedShape, piece.getShape());
    }
    
    @Test
    public void testGenerateRandomPiece() {
        Piece randomPiece = Piece.generateRandomPiece();
        assertNotNull(randomPiece);
        assertNotNull(randomPiece.getShape());
    }
    
    
    @Test
    public void testError {
    	assertEquals(5, 6);
    }
}
