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
     * Test: Verifica que la pieza se mueve correctamente a una nueva posición.
     */
    @Test
    public void testMovePiece() {
        piece.move(2, 3);
        assertEquals(2, piece.getX());
        assertEquals(3, piece.getY());
    }

    /**
     * Test: Verifica que el método `move` maneja los valores negativos.
     */
    @Test
    public void testMovePieceNegativeValues() {
        piece.move(-1, -1);
        assertEquals(-1, piece.getX()); // Permite valores negativos
        assertEquals(-1, piece.getY());
        
    }

    /**
     * Test: Verifica que `rotateClockwise` gira la pieza cuadrada 90 grados 
     * en sentido horario y esta mantiene la misma forma al girar.
     */
    @Test
    public void testRotateSquareClockwise() {
        piece.rotateClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        }; 
        // En este caso, la rotación de un cuadrado permanece igual
        assertArrayEquals(expectedShape, piece.getShape());
        
    }

    /**
     * Test: Verifica que `rotateClockwise` gira la pieza cuadrada 90 grados 
     * en sentido antihorario y esta mantiene la misma forma al girar.
     */
    @Test
    public void testRotateSquareCounterClockwise() {
        piece.rotateCounterClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        };
        // En este caso, la rotación de un cuadrado permanece igual
        assertArrayEquals(expectedShape, piece.getShape());
    }

    /**
     * Test: Verifica que el método `rotateClockwise` gira la pieza 90 grados 
     * en sentido horario de manera correcta.
     */
    @Test
    public void testRotateClockwise() {
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
    
    /**
     * Test: Verifica que el método `rotateClockwise` gira la pieza 90 grados 
     * en sentido antihorario de manera correcta.
     */
    @Test
    public void testRotateCounterClockwise() {
        piece = new Piece(new boolean[][]{
            {true, false},
            {true, true}
        });
        piece.rotateCounterClockwise();
        boolean[][] expectedShape = {
            {false, true},
            {true, true}
        };
        assertArrayEquals(expectedShape, piece.getShape());
    }
    
    /**
     * Test: Verifica que la pieza que se genera no esté vacía.
     */
    @Test
    public void testGenerateRandomPiece() {
        Piece randomPiece = Piece.generateRandomPiece();
        assertNotNull(randomPiece);
        assertNotNull(randomPiece.getShape());
    }
    
    /**
     * Test: Verifica que el método setPosition coloca la pieza en la posición correcta.
     */
    @Test
    public void testSetPosition() {
        piece.setPosition(0, 0);

        assertEquals(0, piece.getX());
        assertEquals(0, piece.getY());

        piece.setPosition(5, 10);

        assertEquals(5, piece.getX());
        assertEquals(10, piece.getY());
    }
    
}
