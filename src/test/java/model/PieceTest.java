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
     * Test: Verifica que el método funciona correctamente 
     * para toda combinación de iteraciones de los loops
     */
    @Test
    public void testRotateClockwiseLoopCases() {

        // Caso 2: Una iteración del bucle externo y una del interno
        boolean[][] shape2 = {
            {true}
        };
        Piece piece2 = new Piece(shape2);
        piece2.rotateClockwise();
        boolean[][] expected2 = {
            {true}
        };
        assertArrayEquals(expected2, piece2.getShape());

        // Caso 3: Una iteración del bucle externo y dos del interno
        boolean[][] shape3 = {
            {true, false}
        };
        Piece piece3 = new Piece(shape3);
        piece3.rotateClockwise();
        boolean[][] expected3 = {
            {true},
            {false}
        };
        assertArrayEquals(expected3, piece3.getShape());

        // Caso 4: Una iteración del bucle externo y más de dos y menos que el máximo de iteraciones -1 del interno
        boolean[][] shape4 = {
            {true, false, false}
        };
        Piece piece4 = new Piece(shape4);
        piece4.rotateClockwise();
        boolean[][] expected4 = {
            {true},
            {false},
            {false}
        };
        assertArrayEquals(expected4, piece4.getShape());

        // Caso 5: Una iteración del bucle externo y el máximo de iteraciones -1 del interno
        boolean[][] shape5 = {
            {true, false, true, true}
        };
        Piece piece5 = new Piece(shape5);
        piece5.rotateClockwise();
        boolean[][] expected5 = {
            {true},
            {false},
            {true},
            {true}
        };
        assertArrayEquals(expected5, piece5.getShape());

        // Caso 6: Una iteración del bucle externo y el máximo de iteraciones del interno
        boolean[][] shape6 = {
            {true, false, true, false, false},
        };
        Piece piece6 = new Piece(shape6);
        piece6.rotateClockwise();
        boolean[][] expected6 = {
            {true},
            {false},
            {true},
            {false},
            {false},
        };
        assertArrayEquals(expected6, piece6.getShape());


        // Caso 9: Dos iteraciones del bucle externo y el máximo de iteraciones del interno
        boolean[][] shape9 = {
            {true, false, false, true, true},
            {false, true, false, true, true}
        };
        Piece piece9 = new Piece(shape9);
        piece9.rotateClockwise();
        boolean[][] expected9 = {
            {false, true},
            {true, false},
            {false, false},
            {true, true},
            {true, true}
        };
        assertArrayEquals(expected9, piece9.getShape());

        // Caso 10: Más de dos y menos que el máximo de iteraciones -1 iteraciones del bucle externo y el máximo de iteraciones del interno
        boolean[][] shape10 = {
            {true, false, true, false, false},
            {false, true, true, false, false},
            {true, true, true, false, false}
        };
        Piece piece10 = new Piece(shape10);
        piece10.rotateClockwise();
        boolean[][] expected10 = {
            {true, false, true},
            {true, true, false},
            {true, true, true},
            {false, false, false},
            {false, false, false}
        };
        assertArrayEquals(expected10, piece10.getShape());

        // Caso 11: El máximo de iteraciones -1 del bucle externo y el máximo de iteraciones del interno
        boolean[][] shape11 = {
            {true, false, true, true},
            {false, true, false, true},
            {false, false, true, false},
            {false, false, true, false},
            {false, false, true, false}
            
        };
        Piece piece11 = new Piece(shape11);
        piece11.rotateClockwise();
        boolean[][] expected11 = {
            {false, false, false, false, true},
            {false, false, false, true, false},
            {true, true, true, false, true},
            {false, false, false, true, true}
        };
        assertArrayEquals(expected11, piece11.getShape());

        // Caso 12: El máximo de iteraciones del bucle externo y el máximo de iteraciones del interno
        boolean[][] shape12 = {
            {true, false, true, false, false},
            {false, true, false, false, true},
            {true, true, false, true, false},
            {true, false, true, false, false},
            {true, false, true, false, false}
        };
        Piece piece12 = new Piece(shape12);
        piece12.rotateClockwise();
        boolean[][] expected12 = {
            {true, true, true, false, true},
            {false, false, true, true, false},
            {true, true, false, false, true},
            {false, false, true, false, false},
            {false, false, false, true, false}
            
        };
        assertArrayEquals(expected12, piece12.getShape());
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
