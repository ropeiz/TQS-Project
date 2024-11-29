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
     * Test: Verifica que `rotateClockwise` gira la pieza cuadrada 90 grados en sentido horario y esta mantiene la misma forma al girar.
     * Caja Blanca: Cobertura de condición y caminos en el método.
     */
    @Test
    public void testRotateSquareClockwise() {
        piece.rotateClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        }; // En este caso, la rotación de un cuadrado permanece igual
        assertArrayEquals(expectedShape, piece.getShape());
        
    }

    /**
     * Test: Verifica que `rotateClockwise` gira la pieza cuadrada 90 grados en sentido antihorario y esta mantiene la misma forma al girar.
     * Caja Blanca: Cobertura de condición y caminos en el método.
     */
    @Test
    public void testRotateSquareCounterClockwise() {
        piece.rotateCounterClockwise();
        boolean[][] expectedShape = {
            {true, true},
            {true, true}
        };
        assertArrayEquals(expectedShape, piece.getShape());
    }

    /**
     * Test: Verifica que el método `rotateClockwise` gira la pieza 90 grados en sentido horario de manera correcta.
     * Design by Contract - Postcondición: La matriz debe cambiar de tamaño tras la rotación.
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
     * Test: Verifica que el método `rotateClockwise` gira la pieza 90 grados en sentido antihorario de manera correcta.
     * Design by Contract - Postcondición: La matriz debe cambiar de tamaño tras la rotación.
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
     * Caja Negra: Se prueba si el método setPosition actualiza correctamente las coordenadas de la pieza.
     */
    @Test
    public void testSetPosition() {
        // Inicializamos la pieza en una posición conocida (por ejemplo, (0, 0)).
        piece.setPosition(0, 0);

        // Verificamos que la pieza está en la posición (0, 0) inicialmente
        assertEquals(0, piece.getX());  // Debería estar en x = 0
        assertEquals(0, piece.getY());  // Debería estar en y = 0

        // Ahora movemos la pieza a una nueva posición (5, 10).
        piece.setPosition(5, 10);

        // Verificamos que la pieza se ha movido correctamente a la nueva posición
        assertEquals(5, piece.getX());  // La nueva posición en x debe ser 5
        assertEquals(10, piece.getY()); // La nueva posición en y debe ser 10
    }
    
}
